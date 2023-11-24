<template>
  <nav
    class="z-10 h-12 w-full sticky top-0 text-lg font-medium flex justify-between items-center text-gray-900 p-3 bg-slate-50 border-b border-slate-200"
  >
    <ArrowLeftIcon @click="goBack" class="w-6 text-gray-700 cursor-pointer" />
    <span>
      <slot></slot>
    </span>
    <XMarkIcon @click="goHome" class="w-6 text-gray-700 cursor-pointer" />
  </nav>
</template>

<script setup>
  import { ArrowLeftIcon, XMarkIcon } from '@heroicons/vue/24/solid';
  import { useRouter } from 'vue-router';
  import { useSearchStore } from '@/stores/search';

  defineProps(['goBack']);

  const router = useRouter();
  const { resetKeyword } = useSearchStore();

  const goHome = () => {
    resetKeyword();
    router.push('/');
  };
</script>

<style scoped></style>
