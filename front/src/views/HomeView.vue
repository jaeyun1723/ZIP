<script setup>
  import { onBeforeMount } from 'vue';
  import { NaverMap } from 'vue3-naver-maps';
  import { useMapStore, useMarkerStore, useLocationDealsStore } from '@/stores';
  import { useSearchLocation } from '@/queries/map';

  const mapOptions = {
    latitude: 37.51347, // 지도 중앙 위도
    longitude: 127.041722, // 지도 중앙 경도
    zoom: 17,
    minZoom: 16,
    tileTransition: true,
  };

  const { setMap } = useMapStore();
  const { setMarkers, initMarkers } = useMarkerStore();
  const { setLocationDeals } = useLocationDealsStore();

  const { mutate: getLocations } = useSearchLocation({
    onSuccess: ({ data }) => {
      setMarkers(data.object);
      setLocationDeals(data.object);
    },
  });

  onBeforeMount(() => {
    navigator.geolocation.getCurrentPosition((pos) => {
      if (pos.coords.latitude) mapOptions.latitude = pos.coords.latitude;
      if (pos.coords.longitude) mapOptions.longitude = pos.coords.longitude;
    });
  });

  const initLayers = [
    'BACKGROUND',
    'BACKGROUND_DETAIL',
    'POI_KOREAN',
    'TRANSIT',
    'ENGLISH',
  ];

  const onLoadMap = (mapObject) => {
    setMap(mapObject);
    initMarkers();

    const minPoint = mapObject.bounds.getMin();
    const maxPoint = mapObject.bounds.getMax();
    getLocations({
      start: {
        lat: minPoint.y,
        lng: minPoint.x,
      },
      end: {
        lat: maxPoint.y,
        lng: maxPoint.x,
      },
    });
  };

  function debounce(func, timeout = 300) {
    let timer;
    return (...args) => {
      clearTimeout(timer);
      timer = setTimeout(() => {
        func.apply(this, args);
      }, timeout);
    };
  }

  const boundsChanged = debounce((mapObject) => {
    const minPoint = mapObject.getMin();
    const maxPoint = mapObject.getMax();
    getLocations({
      start: {
        lat: minPoint.y,
        lng: minPoint.x,
      },
      end: {
        lat: maxPoint.y,
        lng: maxPoint.x,
      },
    });
  });
</script>

<template>
  <router-view></router-view>
  <naver-map
    style="
      width: 100%;
      height: 100vh;
      position: absolute;
      top: 0%;
      left: 0%;
      z-index: -1;
    "
    :mapOptions="mapOptions"
    :initLayers="initLayers"
    @onLoad="onLoadMap"
    @bounds_changed="boundsChanged"
  >
    <!-- <naver-marker
      v-for="marker in markers"
      :key="marker.no"
      :latitude="Number(marker.lat)"
      :longitude="Number(marker.lng)"
      >
      <Marker :area="marker?.area" :price="marker?.dealAmount.split(',')[0]" />
    </naver-marker> -->
  </naver-map>
</template>
