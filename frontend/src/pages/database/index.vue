<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
// import VisualizeConnectionsSection from '@/components/VisualizeConnectionsSection.vue'
import RawRequestSection from '@/components/RawRequestSection.vue'
</script>

<template>
  <RootContainer>
    <!-- <VisualizeConnectionsSection /> -->
    <RawRequestSection />
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
