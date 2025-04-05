<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import ProfileSectionWrapper from '@/components/ProfileSectionWrapper.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onBeforeMount, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const { contact, errorContact, loading, isAuthenticated } = storeToRefs(profile)

async function contactHandle() {
  if (contact.value.id) await profile.updateContactInfo()
  else await profile.saveContactInfo()
}

onMounted(async () => {
  if (!contact.value.id) await profile.getContact()
})
</script>

<template>
  <ProfileSectionWrapper
    title="Контактные данные"
    @cancel="contact.getContact"
    @save="contactHandle"
    :loading="loading"
    :error="errorContact"
  >
    <div class="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-8">
      <div class="sm:col-span-1">
        <label for="provider" class="block text-sm font-medium text-gray-700">Оператор связи</label>
        <div class="mt-1">
          <input
            v-model="contact.provider"
            type="text"
            name="provider"
            id="provider"
            autocomplete="off"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-1">
        <label for="code" class="block text-sm font-medium text-gray-700">Телефонный код</label>
        <div class="mt-1">
          <input
            v-model="contact.code"
            type="text"
            name="code"
            id="code"
            autocomplete="off"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="phoneNumber" class="block text-sm font-medium text-gray-700"
          >Номер телефона</label
        >
        <div class="mt-1">
          <input
            v-model="contact.phoneNumber"
            type="tel"
            name="phoneNumber"
            id="phoneNumber"
            autocomplete="tel-national"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-4">
        <label for="email" class="block text-sm font-medium text-gray-700">Почтовый адрес</label>
        <div class="mt-1">
          <input
            v-model="contact.email"
            type="email"
            name="email"
            id="email"
            autocomplete="email"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>
    </div>
  </ProfileSectionWrapper>
</template>

<style scoped></style>
