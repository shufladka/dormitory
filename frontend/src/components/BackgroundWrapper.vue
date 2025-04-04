<script setup lang="ts">
import background from '@/assets/background.png';
</script>

<template>
  <div class="page-background">
    <slot />
  </div>
</template>

<style scoped>
.page-background {
  position: relative;
  min-height: 100vh;
  background-image: url('@/assets/background.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  z-index: 1;
}
</style>
