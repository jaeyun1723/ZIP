<template>
  <nav class="!absolute top-5 right-6 flex gap-2">
    <fwb-dropdown text="Bottom" class="relative">
      <template #trigger>
        <fwb-button
          color="light"
          class="!font-extrabold focus:!ring-0 !p-[7px]"
        >
          <svg
            class="w-5 h-5 text-violet-600"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 14 18"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="1.5"
              d="M7 8a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm-2 3h4a4 4 0 0 1 4 4v2H1v-2a4 4 0 0 1 4-4Z"
            />
          </svg>
        </fwb-button>
      </template>
      <fwb-list-group class="absolute -top-1 -right-9 w-[6rem]">
        <fwb-list-group-item
          hover
          data-modal-target="updateModal"
          @click="showModal"
        >
          정보수정
        </fwb-list-group-item>
        <fwb-list-group-item hover @click="logout">
          로그아웃
        </fwb-list-group-item>
      </fwb-list-group>
    </fwb-dropdown>
  </nav>
  <update-modal :hideModal="hideModal" :isVisible="isVisible" />
</template>

<script setup>
  import { Modal } from 'flowbite';
  import { ref, reactive, onMounted } from 'vue';
  import {
    FwbButton,
    FwbDropdown,
    FwbListGroup,
    FwbListGroupItem,
  } from 'flowbite-vue';
  import { useLogout } from '@/queries/members';
  import UpdateModal from './Modal/UpdateModal.vue';

  const isVisible = ref(false);
  const modal = reactive({ modal: null });

  onMounted(() => {
    const targetEl = document.getElementById('updateModal');

    const options = {
      onHide: () => {
        isVisible.value = false;
      },
      onShow: () => {
        isVisible.value = true;
      },
    };
    modal.modal = new Modal(targetEl, options);
  });

  const { mutate: logout } = useLogout();

  const showModal = () => {
    console.log('asdf');
    modal.modal.show();
  };

  const hideModal = () => {
    modal.modal.hide();
  };
</script>

<style scoped></style>
