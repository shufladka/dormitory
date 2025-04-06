<script setup lang="ts">
import { BlockInfo, ContractInfo, ResidentInfo } from '@/store/livingStore'
import { PassportInfo } from '@/store/profileStore'
import { ref, computed, onMounted, watch } from 'vue'

const props = defineProps<{
  resident: ResidentInfo
  passports: PassportInfo[]
  blocks: BlockInfo[]
  contracts: ContractInfo[]
}>()

// ФИО жильца по passportId
const passport = computed(() => props.passports.find((p) => p.id === props.resident.passportId))

const fullName = computed(() =>
  passport.value
    ? `${passport.value.surname} ${passport.value.name} ${passport.value.patronymic}`
    : 'Неизвестно'
)

// Фильтруем контракты по resident.contracts и связываем их с блоками
const residentContracts = computed(() => {
  return props.contracts.filter((c) => props.resident.contracts.includes(c.id))
})

// Проверяем, есть ли активные контракты
const hasActiveContract = computed(() => residentContracts.value.length > 0)

// Получаем список блоков, на которые указывают контракты
const contractBlockIds = computed(() => residentContracts.value.map((c) => c.blockId))

const blocksByContracts = computed(() => {
  return props.blocks.filter((b) => contractBlockIds.value.includes(b.id))
})

const selectedBlock = ref<number | null>(null)

// Используем watch для отслеживания изменений в blocksByContracts
watch(
  blocksByContracts,
  (newBlocks) => {
    if (newBlocks.length > 0 && selectedBlock.value === null) {
      selectedBlock.value = newBlocks[0].id // Устанавливаем первый блок, если он есть
    }
  },
  { immediate: true } // Включаем immediate, чтобы сработало при инициализации
)

// Обработчик для заявки на заселение
const handleMoveIn = () => {
  if (selectedBlock.value !== null) {
    console.log('Заявка на заселение:', props.resident.id, selectedBlock.value)
    // emit('move-in', { residentId: props.resident.id, blockId: selectedBlock.value })
  }
}

// Обработчик для заявки на выселение
const handleMoveOut = () => {
  console.log('Заявка на выселение:', props.resident.id)
  // emit('move-out', props.resident.id)
}
</script>

<template>
  <div class="rounded-2xl border p-4 shadow-sm w-full bg-white">
    <h3 class="text-lg font-semibold text-indigo-600">[Номер {{ resident.id }}] {{ fullName }}</h3>
    <span class="text-sm text-gray-500">Ожидает решения о заселении</span>
    <!-- <div v-if="hasActiveContract" class="mt-4">
      <p class="text-green-700 font-medium">Есть активный контракт</p>
      <button
        @click="handleMoveOut"
        class="mt-2 px-4 py-2 rounded-xl bg-red-600 text-white hover:bg-red-700 transition"
      >
        Подать заявку на выселение
      </button>
    </div> -->

    <div class="mt-4 space-y-2">
      <label class="block text-sm font-medium text-gray-700">Выберите блок:</label>
      <select v-model="selectedBlock" class="w-full rounded-xl border px-3 py-2 text-sm">
        <option disabled value="">-- Комната --</option>
        <option v-for="block in blocksByContracts" :key="block.id" :value="block.id">
          [ID {{ block.id }}] Комната №{{ block.roomNumber }}
        </option>
      </select>
      <button
        :disabled="!selectedBlock"
        @click="handleMoveIn"
        class="w-full mt-2 px-4 py-2 rounded-xl disabled:text-zinc-700 disabled:bg-slate-200 default:hover::bg-slate-200 bg-blue-600 text-white hover:bg-blue-700 transition"
      >
        Согласовать заселение
      </button>
    </div>
  </div>
</template>
