<script lang="ts" setup>
import { PhotoIcon, UserCircleIcon } from '@heroicons/vue/24/solid'
import { ChevronDownIcon } from '@heroicons/vue/16/solid'
import RootContainer from '@/components/RootContainer.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onMounted, watch } from 'vue'

const profile = useProfileStore()
const { passport, surname, name, patronymic, birthDate, error } = storeToRefs(profile)

onMounted(async () => {
  if (!passport.value)
    await profile.getPassport()
})

watch([surname, name, patronymic, birthDate], () => {
  if (passport.value) {
    passport.value.surname = surname.value
    passport.value.name = name.value
    passport.value.patronymic = patronymic.value
    passport.value.birthDate = birthDate.value
  }
})
</script>

<template>
  <RootContainer>
    <div class="space-y-12">
      <!-- <div class="border-b border-gray-900/10 pb-12">
          <h2 class="text-base font-semibold leading-7 text-gray-900">Profile</h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">This information will be displayed publicly so be careful what you share.</p>
  
          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div class="sm:col-span-4">
              <label for="username" class="block text-sm font-medium leading-6 text-gray-900">Username</label>
              <div class="mt-2">
                <div class="flex items-center rounded-md bg-white pl-3 ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-indigo-600">
                  <div class="shrink-0 select-none text-sm text-gray-500">workcation.com/</div>
                  <input type="text" name="username" id="username" class="block w-full flex-1 border-0 bg-transparent py-1.5 pl-1 pr-3 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm" placeholder="janesmith" />
                </div>
              </div>
            </div>
  
            <div class="col-span-full">
              <label for="about" class="block text-sm font-medium leading-6 text-gray-900">About</label>
              <div class="mt-2">
                <textarea name="about" id="about" rows="3" class="block w-full rounded-md border-0 bg-white px-3 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-indigo-600 sm:text-sm"></textarea>
              </div>
              <p class="mt-3 text-sm leading-6 text-gray-600">Write a few sentences about yourself.</p>
            </div>
  
            <div class="col-span-full">
              <label for="photo" class="block text-sm font-medium leading-6 text-gray-900">Photo</label>
              <div class="mt-2 flex items-center gap-x-3">
                <UserCircleIcon class="h-12 w-12 text-gray-300" aria-hidden="true" />
                <button type="button" class="rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">Change</button>
              </div>
            </div>
  
            <div class="col-span-full">
              <label for="cover-photo" class="block text-sm font-medium leading-6 text-gray-900">Cover photo</label>
              <div class="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                <div class="text-center">
                  <PhotoIcon class="mx-auto h-12 w-12 text-gray-300" aria-hidden="true" />
                  <div class="mt-4 flex text-sm leading-6 text-gray-600">
                    <label for="file-upload" class="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 hover:text-indigo-500">
                      <span>Upload a file</span>
                      <input id="file-upload" name="file-upload" type="file" class="sr-only" />
                    </label>
                    <p class="pl-1">or drag and drop</p>
                  </div>
                  <p class="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to 10MB</p>
                </div>
              </div>
            </div>
          </div>
        </div> -->

      <!-- Personal Information Section -->
      <div>
        <div>
          <h3 class="text-lg font-medium leading-6 text-gray-900">Персональные данные</h3>
          <p class="mt-1 text-sm text-gray-500">
            Используйте постоянный адрес, по которому вы сможете получать почту.
          </p>
        </div>

        <div class="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-6">
          <div class="sm:col-span-2">
            <label for="surname" class="block text-sm font-medium text-gray-700">Фамилия</label>
            <div class="mt-1">
              <input
                v-model="surname"
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
                v-model="name"
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
                v-model="patronymic"
                type="text"
                name="patronymic"
                id="patronymic"
                autocomplete="additional-name"
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
          </div>

          <div class="sm:col-span-2">
            <label for="birthdate" class="block text-sm font-medium text-gray-700"
              >Дата рождения</label
            >
            <div class="mt-1">
              <input
                v-model="birthDate"
                type="date"
                name="birthdate"
                id="birthdate"
                autocomplete="bday"
                required
                class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
              />
            </div>
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
        </div>
      </div>

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

      <div class="pt-5">
        <div class="flex justify-end gap-3">
          <button
            type="button"
            class="inline-flex justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
          >
            Отменить
          </button>
          <button
            type="submit"
            class="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
          >
            Сохранить
          </button>
        </div>
      </div>
    </div>
  </RootContainer>
</template>

<style scoped>
  body,
  html {
    height: 100%;
  }
</style>
