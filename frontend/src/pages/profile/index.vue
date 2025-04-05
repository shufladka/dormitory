<script lang="ts" setup>
import { PhotoIcon, UserCircleIcon } from '@heroicons/vue/24/solid'
import { ChevronDownIcon } from '@heroicons/vue/16/solid'
import RootContainer from '@/components/RootContainer.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onBeforeMount, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = useProfileStore()
const {
  passport,
  address,
  error,
  loading,
  isAuthenticated,
} = storeToRefs(profile)

async function passportHandle() {
  if (passport.value.id) await profile.updatePassportInfo()
  else await profile.savePassportInfo()
}

async function addressHandle() {
  if (address.value.id) await profile.updateAddressInfo()
  else await profile.saveAddressInfo()
}

onBeforeMount(() => {
  if (!isAuthenticated.value) router.replace('/auth/sign-in')
})

onMounted(async () => {
  if (!passport.value.id) await profile.getPassport()
  if (!address.value.id) await profile.getAddress()
})
</script>

<template>
  <RootContainer>
    <div class="space-y-8">
      <!-- Personal Information Section -->
      <div class="pt-4">
        <h3 class="text-lg font-medium leading-6 text-gray-900">Персональные данные</h3>
      </div>

      <div class="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-8">
        <div class="sm:col-span-2">
          <label for="surname" class="block text-sm font-medium text-gray-700">Фамилия</label>
          <div class="mt-1">
            <input v-model="passport.surname" type="text" name="surname" id="surname" autocomplete="family-name" required
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="name" class="block text-sm font-medium text-gray-700">Имя</label>
          <div class="mt-1">
            <input v-model="passport.name" type="text" name="name" id="name" autocomplete="given-name" required
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="patronymic" class="block text-sm font-medium text-gray-700">Отчество</label>
          <div class="mt-1">
            <input v-model="passport.patronymic" type="text" name="patronymic" id="patronymic" autocomplete="additional-name"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="birthdate" class="block text-sm font-medium text-gray-700">Дата рождения</label>
          <div class="mt-1">
            <input v-model="passport.birthDate" type="date" name="birthdate" id="birthdate" autocomplete="bday" required
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>
      </div>

      <div class="flex justify-end gap-3">
        <div v-if="error" class="pt-4 flex place-content-end text-pink-700">Произошла ошибка</div>
        <button @click="profile.getPassport" :disabled="loading"
          class="inline-flex justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          Отменить
        </button>
        <button @click="passportHandle" :disabled="loading"
          class="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          Сохранить
        </button>
      </div>

      <!-- Registry address Section -->
      <hr />
      <div>
        <h3 class="text-lg font-medium leading-6 text-gray-900">Адрес регистрации</h3>
      </div>

      <div class="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-8">
        <div class="sm:col-span-2">
          <label for="city" class="block text-sm font-medium text-gray-700">
            Название населенного пункта
          </label>
          <div class="mt-1">
            <input v-model="address.settlement" type="text" name="city" id="city" autocomplete="address-level2"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="street-address" class="block text-sm font-medium text-gray-700">Улица</label>
          <div class="mt-1">
            <input v-model="address.street" type="text" name="street-address" id="street-address" autocomplete="street-address"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="building-number" class="block text-sm font-medium text-gray-700">Номер здания</label>
          <div class="mt-1">
            <input v-model="address.buildingNumber" type="number" name="building-number" id="building-number"
              autocomplete="address-line1"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="building-index" class="block text-sm font-medium text-gray-700">Индекс здания</label>
          <div class="mt-1">
            <input v-model="address.buildingIndex" type="text" name="building-index" id="building-index" autocomplete="off"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="flat-number" class="block text-sm font-medium text-gray-700">Номер квартиры</label>
          <div class="mt-1">
            <input v-model="address.flatNumber" type="number" name="flat-number" id="flat-number" autocomplete="address-line2"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="postal-code" class="block text-sm font-medium text-gray-700">Почтовый индекс</label>
          <div class="mt-1">
            <input v-model="address.zip" type="number" name="postal-code" id="postal-code" autocomplete="postal-code"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>
        </div>

        <div class="sm:col-span-2 pt-8 flex flex-row items-start">
          <input v-model="address.isCity" id="isCity" name="isCity" type="checkbox"
            class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500" />
          <label for="isCity" class="font-medium ml-2 mb-2 text-sm text-gray-700">
            Населенный пункт является городом
          </label>
        </div>
      </div>

      <div class="flex justify-end gap-3">
        <div v-if="error" class="pt-4 flex place-content-end text-pink-700">Произошла ошибка</div>
        <button @click="profile.getAddress" :disabled="loading"
          class="inline-flex justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          Отменить
        </button>
        <button @click="addressHandle" :disabled="loading"
          class="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          Сохранить
        </button>
      </div>

      <!-- <div class="sm:col-span-4">
            <label for="email" class="block text-sm font-medium text-gray-700">Email address</label>
            <div class="mt-1">
              <input
                id="email"
                name="email"
                type="email"
                autocomplete="email"
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
          </div> -->

      <!-- <div class="sm:col-span-3">
            <label for="country" class="block text-sm font-medium text-gray-700">Country</label>
            <div class="mt-1">
              <select
                id="country"
                name="country"
                autocomplete="country-name"
                class="block w-full rounded-md border-gray-300 bg-white py-2 px-3 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
              >
                <option>United States</option>
                <option>Canada</option>
                <option>Mexico</option>
              </select>
            </div>
          </div> -->

      <!-- <div class="sm:col-span-6">
            <label for="street-address" class="block text-sm font-medium text-gray-700"
              >Street address</label
            >
            <div class="mt-1">
              <input
                type="text"
                name="street-address"
                id="street-address"
                autocomplete="street-address"
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
          </div> -->

      <!-- <div class="sm:col-span-2">
            <label for="city" class="block text-sm font-medium text-gray-700">City</label>
            <div class="mt-1">
              <input
                type="text"
                name="city"
                id="city"
                autocomplete="address-level2"
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
          </div> -->

      <!-- <div class="sm:col-span-2">
            <label for="region" class="block text-sm font-medium text-gray-700"
              >State / Province</label
            >
            <div class="mt-1">
              <input
                type="text"
                name="region"
                id="region"
                autocomplete="address-level1"
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
          </div> -->

      <!-- <div class="sm:col-span-2">
            <label for="postal-code" class="block text-sm font-medium text-gray-700"
              >ZIP / Postal code</label
            >
            <div class="mt-1">
              <input
                type="text"
                name="postal-code"
                id="postal-code"
                autocomplete="postal-code"
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
          </div> -->

      <!-- Notifications Section -->
      <!-- <div class="pt-8">
      <div>
        <h3 class="text-lg font-medium leading-6 text-gray-900">Notifications</h3>
        <p class="mt-1 text-sm text-gray-500">We will always let you know about important changes, but you pick what else you want to hear about.</p>
      </div>

      <div class="mt-6 space-y-6">
        <fieldset>
          <legend class="text-base font-medium text-gray-900">By Email</legend>
          <div class="mt-4 space-y-4">
            <div class="flex items-start">
              <div class="flex h-5 items-center">
                <input id="comments" name="comments" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
              </div>
              <div class="ml-3 text-sm">
                <label for="comments" class="font-medium text-gray-700">Comments</label>
                <p class="text-gray-500">Get notified when someone posts a comment on a posting.</p>
              </div>
            </div>
            <div class="flex items-start">
              <div class="flex h-5 items-center">
                <input id="candidates" name="candidates" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
              </div>
              <div class="ml-3 text-sm">
                <label for="candidates" class="font-medium text-gray-700">Candidates</label>
                <p class="text-gray-500">Get notified when a candidate applies for a job.</p>
              </div>
            </div>
            <div class="flex items-start">
              <div class="flex h-5 items-center">
                <input id="offers" name="offers" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
              </div>
              <div class="ml-3 text-sm">
                <label for="offers" class="font-medium text-gray-700">Offers</label>
                <p class="text-gray-500">Get notified when a candidate accepts or rejects an offer.</p>
              </div>
            </div>
          </div>
        </fieldset>
      </div>
    </div> -->
    </div>
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
