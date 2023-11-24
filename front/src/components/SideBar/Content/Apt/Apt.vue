<script setup>
  import { ref, watchEffect, watch } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import {
    FwbTable,
    FwbTableBody,
    FwbTableCell,
    FwbTableHead,
    FwbTableHeadCell,
    FwbTableRow,
    FwbCard,
    FwbRating,
    FwbTextarea,
    FwbButton,
    FwbPagination,
  } from 'flowbite-vue';
  import { ChevronDownIcon, ChevronUpIcon } from '@heroicons/vue/24/outline';
  import Nav from '../../Nav/Nav.vue';
  import Title from '../../Title.vue';
  import useHouseData from '@/queries/house/useHouseData';
  import {
    formatAmountDot,
    getDealDate,
    m2ToPyung,
    dateForToday,
  } from '@/util';
  import { useAptReviewData, usePostReview } from '@/queries/review';
  import { useMemberStore, useLoginModal } from '@/stores';
  import Rate from '@/components/Rate.vue';

  const dummyList = [
    {
      id: 2,
      memberId: 'uuser1',
      title: '나름 살만한 아파트',
      content: '여기 그냥 그럭 저럭인데 주변 공원이 예뻐서 맘에 듬',
      score: 4,
      postDate: '2023-11-21 10:45:28',
    },
    {
      id: 1,
      memberId: 'nonoEva',
      title: '여기 더러워요',
      content: '바퀴벌레 나옴 ㅠㅠ 가지마셈 ㅠㅠ',
      score: 1,
      postDate: '2023-11-21 10:45:28',
    },
    {
      id: 1,
      memberId: 'goodHome',
      title: '리뷰하나',
      content: '이런 아파트 좋다',
      score: 5,
      postDate: '2023-11-21 10:45:28',
    },
  ];

  const route = useRoute();
  const router = useRouter();

  const { showModal } = useLoginModal();
  const { getMember } = useMemberStore();

  const curPage = ref(1);
  const cnt = ref(0);
  const message = ref('');
  const score = ref(3);

  const {
    data: houseData,
    isSuccess: isSuccessHouse,
    refetch: refetchHouse,
  } = useHouseData(route.query.aptCode);

  const {
    data: reviewData,
    isSuccess: isSuccessReview,
    refetch: refetchReview,
  } = useAptReviewData(route.query.aptCode, curPage);

  const { mutate: submitReview } = usePostReview({
    onSuccess: () => {
      score.value = 3;
      message.value = '';
      refetchHouse.value();
      refetchReview.value();
    },
  });

  const setScore = (n) => {
    score.value = n;
  };

  const goBack = () => {
    router.go(-1);
  };

  const handleFold = () => {
    if (cnt.value < Math.ceil((houseData.value.list.length - 5) / 5))
      cnt.value++;
    else cnt.value = 0;
  };

  const handleSubmit = () => {
    submitReview({
      aptCode: route.query.aptCode,
      content: message.value,
      title: message.value,
      score: score.value,
    });
  };

  watchEffect(() => {
    route.params.aptCode;
  });

  watch(
    () => curPage.value,
    () => {
      refetchReview.value();
    }
  );
</script>

<template>
  <Nav :goBack="goBack">{{ houseData?.apt.apartmentName }}</Nav>
  <h3 class="w-full z-10 text-center sticky top-[3rem] bg-slate-50 py-2">
    <span>
      {{ houseData?.dong?.sidoName }} {{ houseData?.dong?.gugunName }}
      {{ houseData?.apt.roadName }}
      {{ Number(houseData?.apt.roadNameBonbun) }}
    </span>
  </h3>
  <section class="w-full flex flex-col gap-4 items-center px-4">
    <Title class="w-full">📈 실거래가 정보</Title>
    <fwb-table class="w-full">
      <fwb-table-head class="text-center">
        <fwb-table-head-cell>계약일</fwb-table-head-cell>
        <fwb-table-head-cell>가격</fwb-table-head-cell>
        <fwb-table-head-cell>평형</fwb-table-head-cell>
        <fwb-table-head-cell>층</fwb-table-head-cell>
      </fwb-table-head>
      <fwb-table-body class="text-center" v-show="isSuccessHouse">
        <fwb-table-row
          class="text-center"
          v-for="deal in houseData?.list.slice(0, 5 * (1 + cnt))"
          :key="deal?.no"
        >
          <fwb-table-cell class="!m-auto !text-center !p-1.5">{{
            getDealDate(deal)
          }}</fwb-table-cell>
          <fwb-table-cell class="!m-auto !text-center !p-1.5">{{
            formatAmountDot(deal?.dealAmount)
          }}</fwb-table-cell>
          <fwb-table-cell class="!m-auto !text-center !p-1.5"
            >{{ m2ToPyung(deal?.area) }}평</fwb-table-cell
          >
          <fwb-table-cell class="!m-auto !text-center !p-1.5">{{
            deal?.floor
          }}</fwb-table-cell>
        </fwb-table-row>
      </fwb-table-body>
    </fwb-table>
    <div class="line" v-show="houseData?.list.length > 5">
      <button
        class="!text-gray-500 text-sm flex items-center pl-1"
        @click="handleFold"
      >
        {{
          `${
            cnt !== Math.ceil((houseData?.list.length - 5) / 5)
              ? '더보기'
              : '접기'
          }`
        }}
        <template v-if="cnt !== Math.ceil((houseData?.list.length - 5) / 5)">
          <ChevronDownIcon class="w-4 ml-1" />
        </template>
        <template v-if="cnt === Math.ceil((houseData?.list.length - 5) / 5)">
          <ChevronUpIcon class="w-4 ml-1" />
        </template>
      </button>
    </div>
  </section>
  <section class="mt-1" v-if="!getMember()?.memberId">
    <Title
      >🙌
      <button
        @click="showModal"
        data-modal-target="loginModal"
        class="cursor-pointer font-extrabold text-violet-600 ml-0.5 underline underline-offset-4"
      >
        로그인
      </button>
      하고 리뷰를 확인하세요!</Title
    >
    <div class="blur-sm relative w-full h-full">
      <fwb-card
        class="m-3 p-3 flex flex-col bg-white hover:bg-white"
        v-for="dummy in dummyList"
        :key="dummy.id"
      >
        <div class="flex justify-between text-gray-600 text-sm">
          <span class="flex gap-1">
            {{ dummy?.memberId }}
            <fwb-rating size="sm" :rating="dummy?.score" />
          </span>
          <span>
            {{ dateForToday(dummy.registDate) }}
          </span>
        </div>
        <div>
          <h2 class="font-bold text-violet-700 text-base">
            {{ dummy?.title }}
          </h2>
          <span class="text-sm">
            {{ dummy?.content }}
          </span>
        </div>
      </fwb-card>
    </div>
  </section>
  <section class="mt-1" v-if="getMember()?.memberId && isSuccessReview">
    <div class="w-full flex justify-between items-center">
      <Title v-if="reviewData?.pageList.length"
        >💬 {{ reviewData?.pagination.totalRecordCount }}개의 리뷰가
        있어요</Title
      >
      <Title v-if="!reviewData?.pageList.length">
        😢 작성된 리뷰가 없어요
      </Title>
      <span
        v-if="reviewData?.pageList.length"
        class="text-yellow-400 mx-3 mt-3 font-semibold flex justify-end items-center text-lg"
      >
        {{
          (
            houseData?.rating?.totalRating / houseData?.rating?.reviewCount
          ).toFixed(1)
        }}
        <svg
          class="w-6 h-6"
          fill="currentColor"
          viewBox="0 0 20 20"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"
          ></path>
        </svg>
      </span>
    </div>

    <form class="mx-3" @submit.prevent="handleSubmit">
      <fwb-textarea
        v-model="message"
        :rows="2"
        custom
        label=""
        placeholder="이 아파트를 어떻게 생각하시나요?"
      >
        <template #footer>
          <div class="flex items-center justify-between">
            <div class="flex gap-1">
              <Rate :score="score" :setScore="setScore" />
            </div>
            <fwb-button
              type="submit"
              class="text-white !py-1.5 bg-violet-600 hover:bg-violet-700 focus:!ring-0"
              >확인</fwb-button
            >
          </div>
        </template>
      </fwb-textarea>
    </form>

    <fwb-card
      class="m-3 p-3 flex flex-col hover:bg-white"
      v-for="review in reviewData?.pageList"
      :key="review.id"
    >
      <div class="flex justify-between text-gray-600 text-sm">
        <span class="flex gap-1">
          {{ review?.nickname }}
          <fwb-rating size="sm" :rating="review?.score" />
        </span>
        <span>
          {{ dateForToday(review.registDate) }}
        </span>
      </div>
      <div>
        <h2 class="font-bold text-violet-600 text-base mt-1">
          {{ review?.content }}
        </h2>
      </div>
    </fwb-card>

    <div
      class="w-full flex justify-center mb-6"
      v-if="
        getMember()?.memberId &&
        isSuccessReview &&
        reviewData?.pagination?.endPage > 1
      "
    >
      <fwb-pagination
        v-model="curPage"
        :total-pages="reviewData?.pagination?.endPage"
      />
    </div>
  </section>
</template>

<style scoped></style>
