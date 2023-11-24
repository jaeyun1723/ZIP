<template>
  <fwb-button
    @click="showModal"
    data-modal-target="loginModal"
    class="!absolute top-5 right-6 !font-extrabold bg-violet-600 hover:bg-violet-700 focus:!ring-0"
  >
    <span class="w-6" />
    로그인
    <span class="w-6"
  /></fwb-button>
</template>

<script setup>
  import { FwbButton } from 'flowbite-vue';
  import { useLoginModal } from '../stores';

  const { showModal } = useLoginModal();
</script>

<style scoped></style>
