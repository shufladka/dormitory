<script setup>
import ResidentCard from '@/components/ResidentCard.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const living = useLivingStore()
const { residentList, blockList, errorDormitory } = storeToRefs(living)

onMounted(async () => {
  // if (dormitoryList.value.length < 1) await living.getDormitories()
  await living.getAllBlocks()
  await living.getResidents()
  await living.getContracts()
})
</script>

<template>
  <RootContainer>
    <div>
      {{ residentList }}
      {{ blockList }}
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <ResidentCard
        v-for="resident in residentList"
        :key="resident.id"
        :resident="resident"
        :blocks="blockList"
        @move-in="onMoveIn"
        @move-out="onMoveOut"
      />
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>