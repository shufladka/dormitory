<script lang="ts" setup>
import RoomList from '@/components/RoomList.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { computed, onMounted } from 'vue'

const living = useLivingStore()
const { residentList, dormitoryList, blockList, contractList, errorDormitory } = storeToRefs(living)

const profile = useProfileStore()
const { userCredentials } = storeToRefs(profile)

// const hasContracts = computed(() => {
//   if (userCredentials.value) return living.residentHasContracts(userCredentials.value.id)
// })

onMounted(async () => {
  if (!userCredentials.value) profile.loadUserData()
  if (contractList.value.length < 1) await living.getContracts()
  if (dormitoryList.value.length < 1) await living.getDormitories()
  if (residentList.value.length < 1) await living.getResidents()
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