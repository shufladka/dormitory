<script setup lang="ts">
import { useRouter } from 'vue-router'
import BackgroundWrapper from '@/components/BackgroundWrapper.vue'

const router = useRouter()

function goHome() {
  router.replace('/')
}
</script>


<template>
  <main class="grid min-h-full place-items-center bg-white px-6 py-24 sm:py-32 lg:px-8">
    <div class="text-center">
      <p class="text-xl font-semibold text-indigo-600">404</p>
      <span class="mt-4 font-semibold text-5xl tracking-tight text-gray-900">
        Страница не найдена
      </span>

      <div class="mt-10">
        <button
          @click="goHome"
          class="inline-block rounded-md bg-stone-400 px-4 py-2 text-sm font-semibold text-white shadow-sm hover:bg-stone-500 transition-colors"
        >
          Вернуться на главную
        </button>
      </div>
    </div>
  </main>
</template>
