<script setup>
  import SearchBar from './SearchBar.vue';
  import Footer from './Footer.vue';
</script>

<template>
  <div
    class="absolute w-[23rem] h-[94vh] top-8 left-4 pt-3 bg-white flex flex-col justify-betwee"
  >
    <SearchBar />
    <main class="w-full grow overflow-y-auto p-0">
      <router-view :key="$route.fullPath"></router-view>
    </main>
    <Footer />
  </div>
</template>

<style scoped></style>
