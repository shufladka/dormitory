<script setup lang="ts">
import { DormitoryInfo } from '@/store/livingStore'
import DormitoryItem from './DormitoryItem.vue'

defineProps<{
  dormitories: DormitoryInfo[]
}>()

const emit = defineEmits(['go-to'])
</script>

<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
    <DormitoryItem
      v-for="dorm in dormitories"
      :key="dorm.id"
      v-bind="dorm"
      @click="emit('go-to', dorm.id)"
    />
  </div>
</template>
