<script setup lang="ts">
import { BlockInfo } from '@/store/livingStore'

defineProps<BlockInfo>()
</script>

<template>
  <div class="rounded-2xl border border-gray-200 p-4 shadow-sm bg-white hover:shadow-md transition">
    <div class="flex justify-between items-center">
      <h3 class="text-lg font-semibold text-indigo-600">Комната №{{ roomNumber }}</h3>
      <span class="text-sm text-gray-500">Этаж {{ floor }}</span>
    </div>
    <div class="mt-2 text-sm text-gray-700 space-y-1">
      <p>
        Коек: <span class="font-medium">{{ capacity }}</span>
      </p>
      <p>
        Газ:
        <span :class="isGasified ? 'text-green-600' : 'text-red-600'">{{
          isGasified ? 'Да' : 'Нет'
        }}</span>
      </p>
      <p>
        С/У раздельный:
        <span :class="isBathroomSeparated ? 'text-green-600' : 'text-red-600'">{{
          isBathroomSeparated ? 'Да' : 'Нет'
        }}</span>
      </p>
    </div>
  </div>
</template>
