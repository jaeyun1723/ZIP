import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useLocationDealsStore = defineStore('locationDeals', () => {
  const locationDeals = ref([]);

  const setLocationDeals = (arr) => {
    locationDeals.value = arr;
  };

  const getLocationDeals = () => locationDeals.value;

  return { locationDeals, setLocationDeals, getLocationDeals };
});
