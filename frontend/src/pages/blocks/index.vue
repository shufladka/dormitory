<script lang="ts" setup>
import RoomList from '@/components/RoomList.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const living = useLivingStore()
const { dormitoryList, blockList, errorDormitory } = storeToRefs(living)

onMounted(async () => {
  if (dormitoryList.value.length < 1) await living.getDormitories()
  await living.getAllBlocks()
})
</script>

<template>
  <RootContainer>
    <RoomList :rooms="blockList" />
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>