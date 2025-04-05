<script setup lang="ts">
import background from '@/assets/background.png';
</script>

<template>
  <div class="page-background">
    <slot />
  </div>
</template>

<style lang="css" scoped>
.page-background {
  position: relative;
  max-width: 100%;
  max-height: 100%;
  background-image: url('@/assets/background.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  z-index: 1;
  overflow: hidden;
}

body, html {
  max-height: 100%;
  overflow-x: hidden;
}
</style>
