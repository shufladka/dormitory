<script setup>
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const living = useLivingStore()
const { employeeList } = storeToRefs(living)

onMounted(async () => {
  // if (dormitoryList.value.length < 1) await living.getDormitories()
  await living.getEmployees()
})
</script>

<template>
  <RootContainer>
    <div>
      {{ employeeList }}
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>