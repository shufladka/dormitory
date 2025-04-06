<script setup>
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const living = useLivingStore()
const { residentList, blockList, errorDormitory } = storeToRefs(living)

onMounted(async () => {
  // if (dormitoryList.value.length < 1) await living.getDormitories()
  await living.getResidents()
})
</script>

<template>
  <RootContainer>
    <div>
      {{ residentList }}
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>