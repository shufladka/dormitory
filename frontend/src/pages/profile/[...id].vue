<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import PersonalInfoSection from '@/components/PersonalInfoSection.vue'
import RegistryAddressSection from '@/components/RegistryAddressSection.vue'
import CommonInfoSection from '@/components/CommonInfoSection.vue'
import ContactSection from '@/components/ContactsSection.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { computed, onBeforeMount, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const { profileOption, passport, address, contact, roleList, isAuthenticated } =
  storeToRefs(profile)

onBeforeMount(() => {
  if (!isAuthenticated.value) router.replace('/auth/sign-in')
})

const accountId = computed(() => Number(router.currentRoute.value.params.id))

onMounted(async () => {
  profileOption.value = true
  if (!passport.value.id) await profile.getPassport(accountId.value)
  if (!contact.value.id) await profile.getContact()
  if (!address.value.id) await profile.getAddress()
  if (roleList.value.length < 1) await profile.getRoleList()
})
</script>

<template>
  <RootContainer>
    <div v-if="profileOption" class="space-y-8">
      <PersonalInfoSection />

      <hr />
      <RegistryAddressSection />

      <hr />
      <ContactSection />
    </div>
    <div v-else class="space-y-8">
      <CommonInfoSection />
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
