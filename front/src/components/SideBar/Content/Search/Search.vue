<template>
  <Nav :goBack="goBack">
    {{ keyword }}
    검색 결과</Nav
  >
  <Title
    v-show="
      isSuccessRegion && isSuccessApt && !regions?.length && !apts?.length
    "
    class="w-full text-center !ml-0 !p-0"
  >
    😢 검색 결과가 없습니다.</Title
  >
  <section
    v-show="isSuccessRegion && regions.length"
    class="py-4 border-b border-slate-200"
  >
    <h2 class="text-violet-700 text-sm px-4 pb-2">지역</h2>
    <ul>
      <SearchItem
        v-for="region in regions"
        :key="region.dongCode"
        @click="clickRegion(region)"
      >
        {{ region.sidoName }}
        {{ region.gugunName }}
        {{ region.dongName }}
      </SearchItem>
    </ul>
  </section>
  <section
    v-show="isSuccessApt && apts?.length"
    class="py-4 border-b border-slate-200"
  >
    <h2 class="text-violet-700 text-sm px-4 pb-2">아파트 / 오피스텔</h2>
    <ul>
      <SearchItem v-for="apt in apts" :key="apt.aptCode" @click="clickApt(apt)">
        {{ apt.apartmentName }}
      </SearchItem>
    </ul>
  </section>
</template>

<script setup>
  import { ref, watch } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import Title from '../../Title.vue';
  import Nav from '@/components/Sidebar/Nav/Nav.vue';
  import { useMapStore, useSearchStore, useDongStore } from '@/stores';
  import SearchItem from './SearchItem.vue';
  import { useSearchRegion, useSearchApt } from '@/queries/map';

  const route = useRoute();
  const router = useRouter();
  const keyword = ref(route.params.keyword);
  const { resetKeyword } = useSearchStore();
  const { morph } = useMapStore();
  const { setDong } = useDongStore();

  const {
    data: apts,
    refetch: refetchApt,
    isSuccess: isSuccessApt,
  } = useSearchApt(keyword);

  const {
    data: regions,
    refetch: refetchRegion,
    isSuccess: isSuccessRegion,
  } = useSearchRegion(keyword);

  watch(
    () => route.params.keyword,
    () => {
      if (!route.params.keyword) return;
      keyword.value = route.params.keyword;
      refetchApt.value();
      refetchRegion.value();
    }
  );

  const clickRegion = (region) => {
    let address = `${region.sidoName} ${region.gugunName}`;
    if (region.dongName) address += ` ${region.dongName}`;

    searchAddressToCorrdinate(address, moveMaps);

    setDong(region);
    router.push({
      path: `/region`,
    });
  };

  const clickApt = (apt) => {
    moveMaps({ y: apt.lat, x: apt.lng });
    router.push({
      path: '/apt',
      query: {
        aptCode: apt.aptCode,
      },
    });
  };

  const moveMaps = (pos) => {
    morph(new window.naver.maps.LatLng(pos.y, pos.x));
  };

  const searchAddressToCorrdinate = (address, callback) => {
    window.naver.maps.Service.geocode(
      { query: address },
      (status, response) => {
        callback({
          y: response.v2.addresses[0].y,
          x: response.v2.addresses[0].x,
        });
      }
    );
  };

  const goBack = () => {
    resetKeyword();
    router.replace('/');
  };
</script>

<style scoped></style>
