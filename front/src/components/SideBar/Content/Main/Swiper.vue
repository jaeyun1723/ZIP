<template>
  <swiper
    :direction="'vertical'"
    :modules="modules"
    :loop="true"
    :autoplay="{
      delay: 3000,
      disableOnInteraction: false,
    }"
    class="w-full h-8 bg-slate-100 my-4"
  >
    <swiper-slide
      v-for="data in datas"
      :key="data.name"
      class="flex items-center px-3 gap-4"
    >
      <span class="font-bold text-gray-900"> 💡 바로가기 </span>
      <a
        :href="data.link"
        target="_blank"
        class="underline underline-offset-2 text-violet-700 font-normal cursor-pointer"
      >
        {{ data.name }}
      </a>
    </swiper-slide>
  </swiper>
</template>

<script setup>
  import { Swiper, SwiperSlide } from 'swiper/vue';
  import { Autoplay } from 'swiper/modules';
  import 'swiper/css';

  const datas = [
    {
      name: '부동산거래관리시스템',
      link: 'https://rtms.molit.go.kr/index.do',
    },
    {
      name: '국토교통부 실거래가 공개시스템',
      link: 'https://rt.molit.go.kr/',
    },
    {
      name: '인터넷등기소',
      link: 'http://www.iros.go.kr/PMainJ.jsp',
    },
    {
      name: 'SEE:REAL 씨리얼',
      link: 'https://seereal.lh.or.kr/',
    },
    {
      name: '부동산통계정보시스템',
      link: 'https://www.reb.or.kr/r-one/main.do',
    },
  ];

  const modules = [Autoplay];
</script>

<style scoped></style>
