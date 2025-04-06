<script setup lang="ts">
import { BlockInfo } from '@/store/livingStore'
import RoomItem from './RoomItem.vue'

defineProps<{
  rooms: BlockInfo[]
}>()
</script>

<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
    <RoomItem v-for="room in rooms" :key="room.id" v-bind="room" />
  </div>
</template>
