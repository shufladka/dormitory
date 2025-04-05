<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import PersonalInfoSection from '@/components/PersonalInfoSection.vue'
import RegistryAddressSection from '@/components/RegistryAddressSection.vue'
import ContactSection from '@/components/ContactsSection.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onBeforeMount, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const { passport, address, contact, isAuthenticated } = storeToRefs(profile)

onBeforeMount(() => {
  if (!isAuthenticated.value) router.replace('/auth/sign-in')
})

onMounted(async () => {
  if (!passport.value.id) await profile.getPassport()
  if (!contact.value.id) await profile.getContact()
  if (!address.value.id) await profile.getAddress()
})
</script>

<template>
  <RootContainer>
    <div class="space-y-8">
      <PersonalInfoSection />

      <hr />
      <RegistryAddressSection />

      <hr />
      <ContactSection />
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
