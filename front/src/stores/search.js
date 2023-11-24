import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useSearchStore = defineStore('search', () => {
  const keyword = ref('');

  const setKeyWord = (word) => {
    keyword.value = word;
  };

  const resetKeyword = () => {
    keyword.value = '';
  };

  const hasKeyword = () => !!keyword.value.length;

  return { keyword, setKeyWord, resetKeyword, hasKeyword };
});
