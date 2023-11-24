<template>
  <header class="flex border-b pb-3 px-3 border-slate-200 items-center">
    <img
      src="/logo.png"
      class="w-8 object-contain cursor-pointer"
      @click="goHome"
    />
    <form @submit.prevent="handleSearch" class="w-full">
      <fwb-input
        v-model="keyword"
        name="keyword"
        ref="searchRef"
        class="w-full ml-1 border-0 focus:ring-violet-500 focus:ring-1"
        placeholder="아파트 · 지역명으로 검색"
      >
        <template #suffix>
          <button type="submit" class="h-full flex items-center cursor-pointer">
            <svg
              aria-hidden="true"
              class="w-5 h-5 text-violet-500 dark:text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
              />
            </svg>
          </button>
        </template>
      </fwb-input>
    </form>
  </header>
</template>

<script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { FwbInput } from 'flowbite-vue';
  import { useSearchStore } from '@/stores/search';
  import { storeToRefs } from 'pinia';

  const router = useRouter();
  const searchRef = ref(null);
  const { keyword } = storeToRefs(useSearchStore());
  const { resetKeyword, hasKeyword } = useSearchStore();

  const handleSearch = (e) => {
    if (!hasKeyword()) return;
    e.target[0].blur();
    router.push(`/search/${keyword.value}`);
  };

  const goHome = () => {
    resetKeyword();
    router.replace('/');
  };
</script>

<style lang="scss" scoped></style>
