<script setup lang="ts">
import { BlockInfo, ContractInfo } from '@/store/livingStore'
import { ref, computed } from 'vue'

const props = defineProps<{
  resident: {
    id: number
    passportId: number
    accountId: number | null
    contracts: ContractInfo[]
  }
  blocks: BlockInfo[]
}>()

const emit = defineEmits<{
  (e: 'move-in', payload: { residentId: number; blockId: number }): void
  (e: 'move-out', residentId: number): void
}>()

const selectedBlock = ref()

const hasActiveContract = computed(() => props.resident.contracts.length > 0)

const handleMoveIn = () => {
  if (selectedBlock.value !== null) {
    emit('move-in', { residentId: props.resident.id, blockId: selectedBlock.value })
  }
}

const handleMoveOut = () => {
  emit('move-out', props.resident.id)
}
</script>

<template>
  <div class="rounded-2xl border p-4 shadow-sm w-full bg-white">
    <h3 class="text-lg font-semibold">Жилец #{{ resident.id }}</h3>
    <p class="text-sm text-gray-600">Паспорт ID: {{ resident.passportId }}</p>

    <div v-if="hasActiveContract" class="mt-4">
      <p class="text-green-700 font-medium">Есть активный контракт</p>
      <button
        @click="handleMoveOut"
        class="mt-2 px-4 py-2 rounded-xl bg-red-600 text-white hover:bg-red-700 transition"
      >
        Подать заявку на выселение
      </button>
    </div>

    <div v-else class="mt-4 space-y-2">
      <label class="block text-sm font-medium text-gray-700">Выберите блок:</label>
      <select v-model="selectedBlock" class="w-full rounded-xl border px-3 py-2 text-sm">
        <option v-for="block in blocks" :key="block.id" :value="block.id">
          Комната №{{ block.roomNumber }}
        </option>
      </select>
      <button
        @click="handleMoveIn"
        class="w-full mt-2 px-4 py-2 rounded-xl bg-blue-600 text-white hover:bg-blue-700 transition"
      >
        Подать заявку на заселение
      </button>
    </div>
  </div>
</template>
