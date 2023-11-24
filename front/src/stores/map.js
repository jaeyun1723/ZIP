import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useMapStore = defineStore('map', () => {
  const map = ref();

  const setMap = (obj) => {
    map.value = obj;
  };

  const setCenter = (pos) => {
    map.value.setCenter(pos);
  };

  const morph = (pos, zoom = 18) => {
    map.value.morph(pos, zoom);
  };

  const getMap = () => map.value;

  return { map, setMap, setCenter, morph, getMap };
});
