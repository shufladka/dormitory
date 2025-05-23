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

const emit = defineEmits(['move-in', 'move-out'])

// ФИО жильца по passportId
const passport = computed(() => props.passports.find((p) => p.id === props.resident.passportId))

const description = computed(() => {
  const contract = props.contracts.find((c) => props.resident.contracts.includes(c.id))
  return hasActiveContract.value
    ? `Является жильцом общежития. Контракт №${contract.id}`
    : 'Ожидает решения о заселении'
})

const fullName = computed(() =>
  passport.value
    ? `${passport.value.surname} ${passport.value.name} ${passport.value.patronymic}`
    : 'Неизвестно'
)

// Фильтруем контракты по resident.contracts и связываем их с блоками
const residentContracts = computed(() => {
  return props.contracts.filter((c) => props.resident.contracts.includes(c.id))
})

const hasActiveContract = computed(() => {
  // Проверяем, есть ли контракт с нужным статусом "Заселение"
  return residentContracts.value.some((contract) => contract.status === 'Заселение')
})

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
  if (selectedBlock.value === null) {
    console.warn('Блок не выбран')
    return
  }

  const contract = props.contracts.find(
    (c) => props.resident.contracts.includes(c.id) && c.blockId === selectedBlock.value
  )

  if (contract) {
    contract.statusId = 7
    console.log('Заявка на заселение:', contract)
    emit('move-out', contract)
  } else {
    console.warn('Контракт не найден для заселения')
  }
}

// Обработчик для заявки на выселение
const handleMoveOut = () => {
  if (selectedBlock.value === null) {
    console.warn('Блок не выбран')
    return
  }

  const contract = props.contracts.find(
    (c) => props.resident.contracts.includes(c.id) && c.blockId === selectedBlock.value
  )

  if (contract) {
    contract.statusId = 8
    console.log('Заявка на выселение:', contract)
    emit('move-out', contract)
  } else {
    console.warn('Контракт не найден для выселения')
  }
}
</script>

<template>
  <div
    class="rounded-2xl border p-4 w-full shadow-sm bg-white hover:shadow-md transition flex flex-col basis-full justify-between"
  >
    <div>
      <h3 class="text-lg font-semibold text-indigo-600">
        [Жилец №{{ resident.id }}] {{ fullName }}
      </h3>
      <span class="text-sm text-gray-500">{{ description }}</span>
    </div>

    <div>
      <div v-if="hasActiveContract" class="mt-4">
        <p class="text-green-700 font-medium">Есть активный контракт</p>
        <button
          @click="handleMoveOut"
          class="w-full mt-2 px-4 py-2 rounded-xl bg-red-600 text-white hover:bg-red-700 transition"
        >
          Оформить выселение
        </button>
      </div>

      <div v-else class="mt-4">
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
  </div>
</template>
