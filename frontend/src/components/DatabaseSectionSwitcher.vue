<script lang="ts" setup>
import { useSqlStore } from '@/store/sqlStore'
import { storeToRefs } from 'pinia'

const sqlStore = useSqlStore()
const { switcherOption } = storeToRefs(sqlStore)

const switchLabels = ['SQL-запросы', 'Визуализация', 'Статистика', 'Настройки']
</script>

<template>
  <div class="mt-4 flex justify-start">
    <div class="relative w-[440px] h-10 bg-gray-100 rounded-[8px] overflow-hidden">
      <div
        class="absolute top-0 h-10 w-[110px] bg-white border border-gray-300 shadow-sm rounded-[8px] transition-transform duration-300"
        :style="{ transform: `translateX(${switcherOption * 110}px)` }"
      ></div>

      <!-- Опции -->
      <div class="relative z-10 flex w-full h-full">
        <button
          v-for="(label, index) in switchLabels"
          :key="index"
          @click="switcherOption = index"
          class="flex-1 text-sm font-medium text-gray-600 transition-colors duration-300"
          :class="{ 'text-black': switcherOption === index }"
        >
          {{ label }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
button {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
