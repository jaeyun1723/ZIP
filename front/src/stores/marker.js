import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useMapStore } from './map';
import { formatAmountDot } from '@/util';
import { m2ToPyung } from '../util';
import { useRouter } from 'vue-router';

export const useMarkerStore = defineStore('marker', () => {
  const router = useRouter();
  const { getMap, setCenter } = useMapStore();
  const markers = ref([]);
  const markerElements = ref([]);

  const setMarkers = (data) => {
    markers.value = data;
    drawMarkers();
  };

  const initMarkers = () => {
    for (let i = 0; i < 300; i++) {
      const curmarker = new window.naver.maps.Marker({
        position: new window.naver.maps.LatLng(37.51347, 127.041722),
        map: getMap(),
        visible: false,
      });
      markerElements.value.push(curmarker);
    }
  };

  const drawMarkers = () => {
    markers.value.forEach((e, index) => {
      const curmarker = markerElements.value[index];
      curmarker?.setPosition(new window.naver.maps.LatLng(e.lat, e.lng));
      curmarker?.setIcon({
        content:
          '<div class="relative markerImg">' +
          '<span class="text-center text-white w-[4.5rem] opacity-90 font-medium text-xs absolute top-3 left-1/2 -translate-x-1/2 translate-y-3">' +
          `<span class="text-[0.7rem] mr-[1px]"> ${m2ToPyung(
            e?.area
          )}</span>평` +
          '</span>' +
          `<span class="text-center text-white w-[4.5rem] font-bold absolute top-5 left-1/2 -translate-x-1/2 translate-y-4">${formatAmountDot(
            e?.dealAmount
          )}</span>` +
          '</div>',
        size: new window.naver.maps.Size(70, 60),
        anchor: new window.naver.maps.Point(30, 30),
      });
      curmarker?.setVisible(true);
      window.naver.maps.Event.addListener(curmarker, 'click', () => {
        router.push(`/apt?aptCode=${e.aptCode}`);
        setCenter(new window.naver.maps.LatLng(e.lat, e.lng));
      });
    });
  };

  return { markers, setMarkers, initMarkers };
});
