<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import ProfileSectionWrapper from '@/components/ProfileSectionWrapper.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const { passport, errorPassport, loading, isAuthenticated } = storeToRefs(profile)

async function passportHandle() {
  if (passport.value.id) await profile.updatePassportInfo()
  else await profile.savePassportInfo()
  router.push('/profile')
}
</script>

<template>
  <ProfileSectionWrapper
    title="Персональные данные"
    @cancel="profile.getPassport"
    @save="passportHandle"
    :loading="loading"
    :error="errorPassport"
  >
    <div class="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-8">
      <div class="sm:col-span-2">
        <label for="surname" class="block text-sm font-medium text-gray-700">Фамилия</label>
        <div class="mt-1">
          <input
            v-model="passport.surname"
            type="text"
            name="surname"
            id="surname"
            autocomplete="family-name"
            required
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="name" class="block text-sm font-medium text-gray-700">Имя</label>
        <div class="mt-1">
          <input
            v-model="passport.name"
            type="text"
            name="name"
            id="name"
            autocomplete="given-name"
            required
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="patronymic" class="block text-sm font-medium text-gray-700">Отчество</label>
        <div class="mt-1">
          <input
            v-model="passport.patronymic"
            type="text"
            name="patronymic"
            id="patronymic"
            autocomplete="additional-name"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="birthdate" class="block text-sm font-medium text-gray-700">Дата рождения</label>
        <div class="mt-1">
          <input
            v-model="passport.birthDate"
            type="date"
            name="birthdate"
            id="birthdate"
            autocomplete="bday"
            required
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>
    </div>
  </ProfileSectionWrapper>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
