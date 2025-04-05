<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import ProfileSectionWrapper from '@/components/ProfileSectionWrapper.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const { address, errorAddress, loading, isAuthenticated } = storeToRefs(profile)

async function addressHandle() {
  if (address.value.id) await profile.updateAddressInfo()
  else await profile.saveAddressInfo()
}
</script>

<template>
  <ProfileSectionWrapper
    title="Адрес регистрации"
    @cancel="profile.getAddress"
    @save="addressHandle"
    :loading="loading"
    :error="errorAddress"
  >
    <div class="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-8">
      <div class="sm:col-span-2">
        <label for="city" class="block text-sm font-medium text-gray-700">
          Название населенного пункта
        </label>
        <div class="mt-1">
          <input
            v-model="address.settlement"
            type="text"
            name="city"
            id="city"
            autocomplete="address-level2"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="street-address" class="block text-sm font-medium text-gray-700">Улица</label>
        <div class="mt-1">
          <input
            v-model="address.street"
            type="text"
            name="street-address"
            id="street-address"
            autocomplete="street-address"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="building-number" class="block text-sm font-medium text-gray-700"
          >Номер здания</label
        >
        <div class="mt-1">
          <input
            v-model="address.buildingNumber"
            type="number"
            name="building-number"
            id="building-number"
            autocomplete="address-line1"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="building-index" class="block text-sm font-medium text-gray-700"
          >Индекс здания</label
        >
        <div class="mt-1">
          <input
            v-model="address.buildingIndex"
            type="text"
            name="building-index"
            id="building-index"
            autocomplete="off"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="flat-number" class="block text-sm font-medium text-gray-700"
          >Номер квартиры</label
        >
        <div class="mt-1">
          <input
            v-model="address.flatNumber"
            type="number"
            name="flat-number"
            id="flat-number"
            autocomplete="address-line2"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2">
        <label for="postal-code" class="block text-sm font-medium text-gray-700"
          >Почтовый индекс</label
        >
        <div class="mt-1">
          <input
            v-model="address.zip"
            type="number"
            name="postal-code"
            id="postal-code"
            autocomplete="postal-code"
            class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
      </div>

      <div class="sm:col-span-2 pt-8 flex flex-row items-start">
        <input
          v-model="address.isCity"
          id="isCity"
          name="isCity"
          type="checkbox"
          class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
        />
        <label for="isCity" class="font-medium ml-2 mb-2 text-sm text-gray-700">
          Населенный пункт является городом
        </label>
      </div>
    </div>
  </ProfileSectionWrapper>
</template>

<style scoped></style>
