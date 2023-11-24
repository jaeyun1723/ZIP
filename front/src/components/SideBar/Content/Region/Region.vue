<template>
  <Nav :goBack="goBack">{{ dong.gugun }} {{ dong.dong }}</Nav>
  <Title
    v-show="isSuccess && !apts?.length"
    class="w-full text-center !ml-0 !p-0"
  >
    😢 검색 결과가 없습니다.</Title
  >
  <section v-show="isSuccess && apts?.length" class="py-4">
    <h2 class="text-violet-700 text-sm px-4 pb-2">아파트 / 오피스텔</h2>
    <SearchItem v-for="apt in apts" :key="apt.no" @click="handleClick(apt)">
      {{ apt.apartmentName }}</SearchItem
    >
  </section>
</template>

<script setup>
  import { useRouter } from 'vue-router';
  import Nav from '../../Nav/Nav.vue';
  import Title from '../../Title.vue';
  import { useDongStore, useMapStore } from '@/stores';
  import { useSearchDongCode } from '@/queries/map';
  import SearchItem from '../Search/SearchItem.vue';

  const router = useRouter();
  const { dong } = useDongStore();
  const { morph } = useMapStore();
  const { data: apts, isSuccess } = useSearchDongCode(dong.code);

  const goBack = () => {
    router.go(-1);
  };

  const handleClick = (apt) => {
    morph(new window.naver.maps.LatLng(apt.lat, apt.lng));
    router.push(`/apt?aptCode=${apt.aptCode}`);
  };
</script>

<style scoped></style>
