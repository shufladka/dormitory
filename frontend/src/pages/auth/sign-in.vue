<script setup lang="ts">
import { useAuthStore } from '@/store/authStore'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { onMounted, ref } from 'vue'

const router = useRouter()

const auth = useAuthStore()
const { username, password, error } = storeToRefs(auth)

const showPassword = ref<boolean>(false)

function togglePasswordVisibility() {
  showPassword.value = !showPassword.value
}

onMounted(() => {
  username.value = ''
  password.value = ''
  error.value = false
})

function goToRegistration() {
  router.replace('/auth/registration')
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2 class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">Вход в аккаунт</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <div class="space-y-6">

        <div>
          <label for="username" class="block text-sm/6 font-medium text-gray-900">Логин</label>
          <div class="mt-2">
            <input 
              v-model="username" 
              type="text" 
              name="username" 
              id="username" 
              autocomplete="username" 
              required
              class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6" />
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm/6 font-medium text-gray-900">Пароль</label>
            <button @click="togglePasswordVisibility" type="button" class="text-sm text-indigo-600">
              {{ showPassword ? 'Скрыть' : 'Показать' }}
            </button>
          </div>
          <div class="mt-2">
            <input 
              v-model="password" 
              :type="showPassword ? 'text' : 'password'" 
              name="password" 
              id="password" 
              autocomplete="current-password" 
              required
              class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6" />
          </div>
        </div>

        <div>
          <button type="submit" @click="auth.signIn" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Войти</button>
        </div>

        <div class="mt-4 text-center">
          <button @click="goToRegistration" class="text-sm text-indigo-600 hover:text-indigo-500">
            Нет аккаунта? Зарегистрируйтесь
          </button>
        </div>
      </div>

      <div v-if="error" class="pt-4 flex place-content-center text-pink-700">Произошла ошибка</div>
    
    </div>
  </div>
</template>

<style scoped>
</style>