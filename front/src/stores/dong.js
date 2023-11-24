import { defineStore } from 'pinia';
import { reactive } from 'vue';

export const useDongStore = defineStore(
  'dong',
  () => {
    const dong = reactive({ sido: null, gugun: null, dong: null, code: null });

    const setDong = (region) => {
      dong.sido = region.sidoName;
      dong.gugun = region.gugunName;
      dong.dong = region.dongName;
      dong.code = region.dongCode;
    };

    const getDong = () => dong;

    return { dong, setDong, getDong };
  },
  {
    persist: true,
  }
);
