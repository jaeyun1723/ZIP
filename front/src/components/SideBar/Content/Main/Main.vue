<script setup>
  import { ref } from 'vue';
  import DealCard from './DealCard.vue';
  import Title from '@/components/Sidebar/Title.vue';
  import { ChevronDownIcon, ChevronUpIcon } from '@heroicons/vue/24/outline';
  import { useLocationDealsStore } from '@/stores';
  import { Carousel, Slide, Pagination } from 'vue3-carousel';
  import { FwbCard } from 'flowbite-vue';
  import 'vue3-carousel/dist/carousel.css';
  import useNoticeList from '@/queries/notice/useNoticeList';
  import { dateForToday } from '@/util';
  import Swiper from './Swiper.vue';

  const cnt = ref(0);

  const { data: notices } = useNoticeList();
  const { getLocationDeals } = useLocationDealsStore();

  const handleFold = () => {
    if (cnt.value < Math.ceil((getLocationDeals().length - 4) / 4)) cnt.value++;
    else cnt.value = 0;
  };
</script>

<template>
  <section>
    <Title v-if="getLocationDeals().length">📌 주변 실거래가 정보</Title>
    <Title
      v-if="!getLocationDeals().length"
      class="w-full text-center !m-0 !my-8 !pr-2 !p-0"
      >📌 주변에 조회가능한 아파트가 없어요</Title
    >
    <deal-card
      v-for="deal in getLocationDeals().slice(0, 4 + cnt * 10)"
      :key="deal.no"
      :deal="deal"
    />
    <div v-if="getLocationDeals().length <= 4" class="h-4" />
    <div class="line" v-if="getLocationDeals().length > 4">
      <button
        class="!text-gray-500 text-sm flex items-center pl-1"
        @click="handleFold"
      >
        {{
          `${
            cnt !== Math.ceil((getLocationDeals().length - 4) / 4)
              ? '더보기'
              : '접기'
          }`
        }}
        <template v-if="cnt !== Math.ceil((getLocationDeals().length - 4) / 4)">
          <ChevronDownIcon class="w-4 ml-1" />
        </template>
        <template v-if="cnt === Math.ceil((getLocationDeals().length - 4) / 4)">
          <ChevronUpIcon class="w-4 ml-1" />
        </template>
      </button>
    </div>
  </section>
  <section class="py-1" :key="cnt">
    <Title class="mb-2">📢 공지사항</Title>
    <Carousel :items-to-show="1" :autoplay="3000" :wrap-around="true">
      <Slide v-for="notice in notices" :key="notice?.noticeId">
        <FwbCard
          class="carousel__item w-11/12 h-36 max-h-36 p-4 hover:bg-white"
        >
          <div class="w-full h-full my-auto flex flex-col gap-1 text-left">
            <h3 class="font-bold text-violet-500">
              {{ notice?.noticeTitle }}
            </h3>
            <div class="whitespace-nowrap overflow-hidden text-ellipsis grow">
              {{ notice?.noticeContent }}
            </div>
            <span class="ml-auto mt-0 text-sm text-gray-500 grow">{{
              dateForToday(notice?.postDate)
            }}</span>
          </div>
        </FwbCard>
      </Slide>
      <template #addons>
        <Pagination />
      </template>
    </Carousel>
  </section>
  <section>
    <Swiper />
  </section>
</template>

<style scoped></style>
