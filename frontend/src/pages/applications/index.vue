<script setup>
import ApplicationCard from '@/components/ApplicationCard.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const living = useLivingStore()
const { residentList, blockList, contractList } = storeToRefs(living)

const profile = useProfileStore()
const { passportList, userCredentials } = storeToRefs(profile)

onMounted(async () => {
  if (!userCredentials.value) profile.loadUserData()
  if (passportList.value.length < 1) await profile.getPassports()
  if (contractList.value.length < 1) await living.getContracts()
  if (residentList.value.length < 1) await living.getResidents()
  await living.getAllBlocks()
})
</script>

<template>
  <RootContainer>
    <div></div>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <ApplicationCard
        v-for="resident in residentList"
        :key="resident.id"
        :resident="resident"
        :blocks="blockList"
        :passports="passportList"
        :contracts="contractList"
        @move-in="living.updateContract"
        @move-out="living.updateContract"
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