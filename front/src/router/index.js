import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import SidebarLayout from '@/components/SideBar/SidebarLayout.vue';
import { Main, Search, Region, Apt } from '@/components/SideBar/Content';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path: '/',
          component: SidebarLayout,
          children: [
            {
              path: '/',
              component: Main,
            },
            {
              path: '/search/:keyword',
              component: Search,
            },
            {
              path: '/region',
              component: Region,
            },
            {
              path: '/apt',
              component: Apt,
            },
          ],
        },
      ],
    },
  ],
});

export default router;
