<template>
  <div
    id="updateModal"
    @click="showModal"
    tabindex="-1"
    class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full"
  >
    <div class="relative p-4 w-full max-w-md max-h-full">
      <div
        class="relative bg-white rounded-lg flex flex-col p-10 w-full justify-center items-center gap-4"
      >
        <form
          @submit.prevent="handleUpdate"
          class="flex flex-col w-full gap-4 justify-center items-start"
        >
          <h1
            class="flex justify-center items-center gap-3 text-3xl font-bold text-violet-700 mb-4 w-full"
          >
            <img src="/logo.png" class="w-10 object-contain" />
            정보수정
          </h1>

          <div class="w-full">
            <label
              for="nickname"
              class="block text-sm font-medium text-gray-900 mb-2 ml-1"
              >닉네임</label
            >

            <input
              type="text"
              class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
              placeholder="닉네임"
              name="nickname"
              v-model="nickname"
            />
          </div>
          <div class="w-full">
            <label
              for="nickname"
              class="block text-sm font-medium text-gray-900 mb-2 ml-1"
              >주소</label
            >
            <input
              type="text"
              class="block w-full p-2 text-sm text-gray-900 rounded-lg border-0 bg-gray-100 focus:ring-violet-500 focus:ring-1"
              placeholder="주소"
              name="address"
              v-model="address"
            />
          </div>
          <span v-show="isWarning" class="text-sm text-red-600 font-medium">{{
            warnMsg
          }}</span>
          <fwb-button
            type="submit"
            class="w-full bg-violet-600 hover:bg-violet-700 focus:!ring-0 mt-3"
            >변경하기</fwb-button
          >
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, watch } from 'vue';
  import { FwbButton } from 'flowbite-vue';
  import { useMemberStore } from '../../stores';
  import { useUpdateMember } from '@/queries/members';

  const { getMember } = useMemberStore();
  const props = defineProps(['isVisible', 'hideModal']);
  const { mutate: updateMember } = useUpdateMember(
    getMember().memberId,
    props.hideModal
  );

  const nickname = ref(getMember().nickname);
  const address = ref(getMember().address);
  const isWarning = ref(false);
  const warnMsg = ref('');

  const handleUpdate = () => {
    updateMember({
      address: address.value,
      nickname: nickname.value,
    });
  };

  const resetValues = () => {
    nickname.value = getMember().nickname;
    address.value = getMember().address;
    isWarning.value = false;
    warnMsg.value = '';
  };

  watch(
    () => props.isVisible,
    () => {
      resetValues();
    }
  );
</script>

<style scoped></style>
