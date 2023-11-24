<template>
  <article>
    <fwb-card class="p-3 m-4 cursor-pointer" @click="handleClick">
      <div class="flex justify-between">
        <h2
          class="max-w-[14rem] font-semibold text-violet-700 flex flex-wrap items-center gap-1"
        >
          {{ deal.apartmentName }}
          <span class="text-sm text-black font-normal">
            {{ deal.floor }}층 {{ (deal.area * 0.3205).toFixed(1) }}평</span
          >
        </h2>
        <fwb-badge type="purple" class="m-0 h-6">{{
          getDealDate(deal)
        }}</fwb-badge>
      </div>
      <div>
        <span class="text-xl text-black font-bold">{{
          formatAmount(deal.dealAmount)
        }}</span>
      </div>
    </fwb-card>
  </article>
</template>

<script setup>
  import { useRouter } from 'vue-router';
  import { FwbCard, FwbBadge } from 'flowbite-vue';
  import { useMapStore } from '@/stores';
  import { formatAmount } from '@/util';

  const props = defineProps(['deal']);
  const router = useRouter();

  const { morph } = useMapStore();

  const getDealDate = (deal) => {
    const year = String(deal.dealYear % 100).padStart(2, '0');
    const month = String(deal.dealMonth).padStart(2, '0');
    const day = String(deal.dealDay).padStart(2, '0');

    return `${year}.${month}.${day}`;
  };

  const moveMaps = (pos) => {
    morph(new window.naver.maps.LatLng(pos.lat, pos.lng));
  };

  const handleClick = () => {
    moveMaps(props.deal);
    router.push({ path: '/apt', query: { aptCode: props.deal.aptCode } });
  };
</script>

<style scoped></style>
