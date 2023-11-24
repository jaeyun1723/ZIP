<script setup>
  import { onMounted } from 'vue';
  import { RouterView } from 'vue-router';
  import LoginButton from './components/LoginButton.vue';
  import { globalCookiesConfig } from 'vue3-cookies';
  import { useMemberStore, useLoginModal } from '@/stores';
  import UserMenu from '@/components/UserMenu.vue';
  import { FwbSpinner } from 'flowbite-vue';
  import LoginModal from '@/components/Modal/LoginModal.vue';

  globalCookiesConfig({
    expireTimes: 3600,
  });

  const { isVisible, hideModal, initModal } = useLoginModal();
  const { getMember } = useMemberStore();

  onMounted(() => {
    initModal();
  });
</script>

<template>
  <Suspense>
    <div class="relative">
      <router-view />
      <template v-if="!getMember()?.memberId"> <login-button /></template>
      <template v-if="getMember()?.memberId">
        <div><UserMenu /></div>
      </template>
      <LoginModal :hideModal="hideModal" :isVisible="isVisible" />
    </div>
    <template #fallback> <fwb-spinner color="purple" size="12" /></template>
  </Suspense>
</template>

<style scoped></style>
