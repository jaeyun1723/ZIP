import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { createNaverMap } from 'vue3-naver-maps';
import { VueQueryPlugin } from 'vue-query';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import App from './App.vue';
import router from './router';
import './index.css';

const app = createApp(App);

app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);

const vueQueryPluginOptions = {
  queryClientConfig: {
    defaultOptions: {
      queries: {
        retry: 0,
        staleTime: 10 * 1000,
        refetchOnWindowFocus: false,
        refetchOnReconnect: false,
      },
      mutations: {
        retry: 0,
      },
    },
  },
};

app.use(VueQueryPlugin, vueQueryPluginOptions);

app
  .use(createNaverMap, {
    clientId: import.meta.env.VITE_NAVER_CLIENT_ID, // Required
    category: 'ncp', // Optional
    subModules: ['geocoder'], // Optional, "panorama" | "geocoder" | "drawing" | "visualization"
  })
  .mount('#app');
