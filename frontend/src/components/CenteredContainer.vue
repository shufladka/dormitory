<script setup lang="ts">
import BackgroundWrapper from '@/components/BackgroundWrapper.vue'
</script>

<template>
  <BackgroundWrapper>
    <div class="container">
      <slot />
    </div>
  </BackgroundWrapper>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vh;
  text-align: center;
}
</style>
