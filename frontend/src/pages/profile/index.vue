<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import PersonalInfoSection from '@/components/PersonalInfoSection.vue'
import RegistryAddressSection from '@/components/RegistryAddressSection.vue'
import CommonInfoSection from '@/components/CommonInfoSection.vue'
import ContactSection from '@/components/ContactsSection.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onBeforeMount, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import ResidentContractsStatus from '@/components/ResidentContractsStatus.vue'

const router = useRouter()

const profile = useProfileStore()
const { profileOption, passport, address, contact, roleList, isAuthenticated } =
  storeToRefs(profile)

onBeforeMount(() => {
  if (!isAuthenticated.value) router.replace('/auth/sign-in')
})

onMounted(async () => {
  profileOption.value = false
  await profile.getPassport()
  await profile.getContact()
  await profile.getAddress()
  await profile.getRoleList()
})
</script>

<template>
  <RootContainer>
    <div v-if="profileOption" class="space-y-8">
      <CommonInfoSection />

      <hr />
      <PersonalInfoSection />

      <hr />
      <RegistryAddressSection />

      <hr />
      <ContactSection />
    </div>
    <div v-else class="space-y-8">
      <ResidentContractsStatus />
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
