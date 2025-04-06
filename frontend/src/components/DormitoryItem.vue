<script setup lang="ts">
import { DormitoryInfo } from '@/store/livingStore'

defineProps<DormitoryInfo>()
</script>

<template>
  <div class="rounded-2xl border border-gray-200 p-4 shadow-sm bg-white hover:shadow-md transition">
    <h3 class="text-lg font-semibold text-indigo-700">Общежитие №{{ id }}</h3>
    <div class="mt-2 text-sm text-gray-700 space-y-1">
      <p>
        Тип: <span class="font-medium">{{ dormitoryType }}</span>
      </p>
      <p>
        Этажей: <span class="font-medium">{{ floors }}</span>
      </p>
      <p>
        Блоков: <span class="font-medium">{{ blocks }}</span>
      </p>
      <p>
        Лифт:
        <span :class="hasLift ? 'text-green-600' : 'text-red-600'">
          {{ hasLift ? 'Есть' : 'Нет' }}
        </span>
      </p>
    </div>
  </div>
</template>
