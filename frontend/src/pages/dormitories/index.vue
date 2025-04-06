<script lang="ts" setup>
import DormitoryList from '@/components/DormitoryList.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { storeToRefs } from 'pinia'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const living = useLivingStore()
const { dormitoryList, errorDormitory } = storeToRefs(living)

function goBlocks(id: number) {
  router.replace(`/blocks/${id}`)
}

onMounted(async () => {
  await living.getDormitories()
})
</script>

<template>
  <RootContainer>
    <DormitoryList :dormitories="dormitoryList" @go-to="goBlocks" />
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>