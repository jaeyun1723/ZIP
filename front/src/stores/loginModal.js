import { Modal } from 'flowbite';
import { defineStore } from 'pinia';
import { ref, reactive } from 'vue';

export const useLoginModal = defineStore('loginModal', () => {
  const isVisible = ref(false);
  const modal = reactive({ modal: null });
  const isLoginModal = ref(true);

  const initModal = () => {
    const targetEl = document.getElementById('loginModal');

    const options = {
      onHide: () => {
        isLoginModal.value = true;
        isVisible.value = false;
      },
      onShow: () => {
        isVisible.value = true;
      },
    };
    modal.modal = new Modal(targetEl, options);
  };

  const showModal = () => {
    modal.modal.show();
  };

  const hideModal = () => {
    modal.modal.hide();
  };

  const switchTab = () => {
    isLoginModal.value = !isLoginModal.value;
  };

  const getIsLoginModal = () => isLoginModal.value;

  return {
    isVisible,
    isLoginModal,
    initModal,
    hideModal,
    showModal,
    switchTab,
    getIsLoginModal,
  };
});
