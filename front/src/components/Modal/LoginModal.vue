<template>
  <div
    id="loginModal"
    tabindex="-1"
    class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full"
  >
    <div class="relative p-4 w-full max-w-md max-h-full">
      <div class="relative bg-white rounded-lg p-10 w-full">
        <!-- 로그인 -->
        <form
          @submit.prevent="handleLogin"
          v-if="getIsLoginModal()"
          class="flex flex-col w-full gap-4 justify-center items-center"
        >
          <h1
            class="flex justify-center items-center gap-3 text-3xl font-bold text-violet-700 mb-4 w-full"
          >
            <img src="/logo.png" class="w-10 object-contain" />
            로그인
          </h1>
          <input
            type="text"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="아이디"
            name="id"
            v-model="id"
          />
          <input
            type="password"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="비밀번호"
            name="pw"
            v-model="pw"
          />
          <fwb-button
            type="submit"
            class="w-full bg-violet-600 hover:bg-violet-700 focus:!ring-0"
            >로그인</fwb-button
          >
          <span v-show="isWarning" class="text-sm text-red-600 font-medium"
            >아이디 또는 패스워드를 확인해주세요.</span
          >
          <div class="flex items-center justify-center gap-4 mt-2 px-2 w-full">
            <span class="text-sm text-gray-600">아직 회원이 아니신가요?</span>
            <span
              class="cursor-pointer text-sm text-violet-600 font-semibold"
              @click="switchModal"
              >회원가입</span
            >
          </div>
        </form>

        <!-- 회원가입 -->
        <form
          @submit.prevent="handleSignup"
          v-if="!getIsLoginModal()"
          class="flex flex-col w-full gap-4 justify-center items-center"
        >
          <h1
            class="flex justify-center items-center gap-3 text-3xl font-bold text-violet-700 mb-4 w-full"
          >
            <img src="/logo.png" class="w-10 object-contain" />
            회원가입
          </h1>
          <input
            type="text"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="아이디"
            name="id"
            v-model="id"
          />
          <input
            type="password"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="비밀번호"
            name="pw"
            v-model="pw"
          />
          <input
            type="password"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="비밀번호 확인"
            name="pwcheck"
            v-model="pwcheck"
          />
          <input
            type="text"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="닉네임"
            name="nickname"
            v-model="nickname"
          />
          <input
            type="text"
            class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
            placeholder="주소"
            name="address"
            v-model="address"
          />
          <span v-show="isWarning" class="text-sm text-red-600 font-medium">{{
            warnMsg
          }}</span>
          <fwb-button
            type="submit"
            class="w-full bg-violet-600 hover:bg-violet-700 focus:!ring-0"
            >회원가입</fwb-button
          >
          <div class="flex items-center justify-center gap-4 mt-2 px-2 w-full">
            <span class="text-sm text-gray-600">이미 계정이 있으신가요?</span>
            <span
              class="cursor-pointer text-sm text-violet-600 font-semibold"
              @click="switchModal"
              >로그인</span
            >
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, watch } from 'vue';
  import { FwbButton } from 'flowbite-vue';
  import { useLogin, useSignup } from '@/queries/members';
  import { useLoginModal } from '@/stores';

  const props = defineProps(['isVisible', 'hideModal']);

  watch(
    () => props.isVisible,
    () => {
      resetValues();
    }
  );

  const id = ref('');
  const pw = ref('');
  const pwcheck = ref('');
  const nickname = ref('');
  const address = ref('');
  const isWarning = ref(false);
  const warnMsg = ref('');

  const { getIsLoginModal, switchTab } = useLoginModal();

  const { mutate: login } = useLogin(props.hideModal, {
    onError: () => {
      isWarning.value = true;
    },
  });
  const { mutate: signup } = useSignup(props.hideModal, {
    onError: ({ data }) => {
      isWarning.value = true;
      warnMsg.value = data.msg;
    },
  });

  const resetValues = () => {
    id.value = '';
    pw.value = '';
    pwcheck.value = '';
    nickname.value = '';
    address.value = '';
    isWarning.value = false;
  };

  const switchModal = () => {
    switchTab();
    resetValues();
  };

  const handleLogin = () => {
    login({ memberId: id.value, pw: pw.value });
  };

  const handleSignup = () => {
    if (pw.value !== pwcheck.value) {
      isWarning.value = true;
      warnMsg.value = '비밀번호가 일치하지 않습니다.';
      return;
    }
    signup({
      memberId: id.value,
      pw: pw.value,
      nickname: nickname.value,
      address: address.value,
    });
  };
</script>

<style scoped></style>
