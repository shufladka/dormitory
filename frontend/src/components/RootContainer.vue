<script lang="ts" setup>
import { useAuthStore, UserCredentials } from '@/store/authStore'
import { useProfileStore } from '@/store/profileStore'
import {
  Disclosure,
  DisclosureButton,
  DisclosurePanel,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
} from '@headlessui/vue'
import { Bars3Icon, BellIcon, XMarkIcon } from '@heroicons/vue/24/outline'
import { UserCircleIcon } from '@heroicons/vue/24/solid'
import { storeToRefs } from 'pinia'
import { computed, onMounted, ref, watch, watchEffect } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const currentRoute = ref(router.currentRoute.value)

const authStore = useAuthStore()
const { accountList } = storeToRefs(authStore)

const profile = useProfileStore()
const { profileOption } = storeToRefs(profile)

interface NavigationItem {
  name: string
  link: string | null
}

function isCurrentPage(current: NavigationItem): boolean {
  if (current.link) return current.link === router.currentRoute.value.fullPath
  return false
}

const currentPage = computed(() => {
  return navigation.find((item: NavigationItem) =>
    item.link.includes(router.currentRoute.value.fullPath)
  )
})

const isProfilePage = computed(() => {
  return router.currentRoute.value.fullPath.includes('/profile')
})

const navigation: NavigationItem[] = [
  { name: 'Главная', link: '/' },
  { name: 'Общежитие', link: '/dormitory' },
]

function goProfile() {
  if (profile.isAuthenticated) router.replace('/profile')
  else router.replace('/auth/sign-in')
}

function signOut() {
  localStorage.clear()
  router.replace('/auth/sign-in')
}

const isForeignProfile = computed(() => (route.fullPath.includes('/profile/') ? true : false))

watchEffect(() => {
  if (route.fullPath.includes('/profile/') && accountList.value.length > 1) {
    const result = accountList.value.find(
      (account: UserCredentials) => account.id === Number(route.params.id)
    )
    if (!result) router.replace('/error/404')
  }
})

onMounted(async () => {
  await authStore.fetchAccountList()
})
</script>

<template>
  <Disclosure as="nav" class="bg-slate-700">
    <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
      <div class="relative flex h-16 items-center justify-between">
        <div class="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
          <div class="hidden sm:ml-6 sm:block">
            <div class="flex space-x-4">
              <a
                v-for="item in navigation"
                :key="item.name"
                @click="$router.replace(item.link)"
                :class="[
                  isCurrentPage(item)
                    ? 'bg-gray-900 text-white'
                    : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                  'rounded-md px-3 py-2 text-sm font-medium',
                ]"
                :aria-current="item.current ? 'page' : undefined"
                >{{ item.name }}</a
              >
            </div>
          </div>
        </div>
        <div
          class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
        >
          <!-- Profile dropdown -->
          <Menu as="div" class="relative ml-3">
            <div>
              <MenuButton
                class="relative flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
              >
                <span class="absolute -inset-1.5" />
                <span class="sr-only">Open user menu</span>
                <UserCircleIcon class="h-10 w-10 text-slate-100" />
              </MenuButton>
            </div>
            <transition
              enter-active-class="transition ease-out duration-100"
              enter-from-class="transform opacity-0 scale-95"
              enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75"
              leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95"
            >
              <MenuItems
                class="absolute right-0 z-10 mt-2 w-[200px] origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black/5 focus:outline-none"
              >
                <MenuItem v-slot="{ active }">
                  <a
                    @click="goProfile"
                    :class="[
                      active ? 'bg-gray-100 outline-none' : '',
                      'block px-4 py-2 text-sm text-gray-700',
                    ]"
                    >Профиль</a
                  >
                </MenuItem>
                <MenuItem v-slot="{ active }">
                  <a
                    @click="signOut"
                    :class="[
                      active ? 'bg-red-100 outline-none' : '',
                      'block px-4 py-2 font-semibold text-sm text-red-700',
                    ]"
                    >Выйти из учетной записи</a
                  >
                </MenuItem>
              </MenuItems>
            </transition>
          </Menu>
        </div>
      </div>
    </div>

    <DisclosurePanel class="sm:hidden">
      <div class="space-y-1 px-2 pb-3 pt-2">
        <DisclosureButton
          v-for="item in navigation"
          :key="item.name"
          as="a"
          :href="item.href"
          :class="[
            item.current
              ? 'bg-gray-900 text-white'
              : 'text-gray-300 hover:bg-gray-700 hover:text-white',
            'block rounded-md px-3 py-2 text-base font-medium',
          ]"
          :aria-current="item.current ? 'page' : undefined"
          >{{ item.name }}</DisclosureButton
        >
      </div>
    </DisclosurePanel>
  </Disclosure>

  <header class="bg-white shadow">
    <div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
      <h1 class="text-3xl font-bold tracking-tight text-gray-900">
        {{ currentPage ? currentPage.name : '' }}

        <!-- Свитчер -->
        <div v-if="isProfilePage && !isForeignProfile" class="mt-4 flex justify-start">
          <label for="switcher" class="relative inline-flex items-center cursor-pointer">
            <input v-model="profileOption" id="switcher" type="checkbox" class="sr-only peer" />
            <div
              class="w-[240px] h-10 bg-gray-100 peer-focus:outline-none rounded-[8px] peer-checked:bg-rose-100 transition-colors duration-300"
            ></div>
            <span
              class="absolute left-0 top-0 h-10 w-[120px] rounded-[8px] bg-white border border-gray-300 shadow-sm transform peer-checked:translate-x-full transition-transform duration-300"
            ></span>
            <div
              class="absolute w-full flex justify-between px-2.5 text-sm font-medium z-10 pointer-events-none"
            >
              <span class="text-gray-600 peer-checked:text-white transition-colors"
                >Общие сведения</span
              >
              <span class="text-gray-600 peer-checked:text-white transition-colors"
                >Личные данные</span
              >
            </div>
          </label>
        </div>
      </h1>
    </div>
  </header>
  <main>
    <div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
      <slot />
    </div>
  </main>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>