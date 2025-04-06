<script lang="ts" setup>
import RoomList from '@/components/RoomList.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'

const living = useLivingStore()
const { dormitoryList, blockList, errorDormitory } = storeToRefs(living)

const route = useRoute()

onMounted(async () => {
  if (dormitoryList.value.length < 1) await living.getDormitories()
  await living.getBlocksByDormitory(Number(route.params.id))
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