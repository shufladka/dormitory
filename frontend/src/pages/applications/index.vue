<script setup>
import ApplicationCard from '@/components/ApplicationCard.vue'
import RootContainer from '@/components/RootContainer.vue'
import { useLivingStore } from '@/store/livingStore'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const living = useLivingStore()
const { residentList, blockList, contractList, errorContract } = storeToRefs(living)

const profile = useProfileStore()
const { passportList, userCredentials, isAuthenticated, isEmployee } = storeToRefs(profile)

onMounted(async () => {
  profile.loadUserData()
  if (isEmployee.value) {
    await profile.getPassports()
    await living.getContracts()
    await living.getResidents()
    await living.getAllBlocks()
  }
})
</script>

<template>
  <RootContainer>
    <div v-if="isEmployee">
      <span v-if="errorContract" class="mb-2 text-red-500 text-semibold flex justify-start"
        >Возможно, комнате больше нет мест</span
      >
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
    </div>
    <span v-else class="text-lg font-bold text-zinc-800"
      >Доступ запрещен: Вы не являетесь работником</span
    >
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>