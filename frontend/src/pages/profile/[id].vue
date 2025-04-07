<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import PersonalInfoSection from '@/components/PersonalInfoSection.vue'
import RegistryAddressSection from '@/components/RegistryAddressSection.vue'
import CommonInfoSection from '@/components/CommonInfoSection.vue'
import ContactSection from '@/components/ContactsSection.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { computed, onMounted, watchEffect } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const { profileOption, isEmployee, passport, address, contact, roleList, isAuthenticated } =
  storeToRefs(profile)

const accountId = computed(() => Number(router.currentRoute.value.params.id))

watchEffect(async () => {
  // Если credentials отсутствуют — редиректим на страницу входа
  if (!isEmployee.value) {
    router.replace('/error/403')
    return
  }
})

onMounted(async () => {
  profileOption.value = true
  if (!passport.value.id) await profile.getPassport(accountId.value)
  await profile.getContact()
  await profile.getAddress()
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
      {{ dfg }}
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
