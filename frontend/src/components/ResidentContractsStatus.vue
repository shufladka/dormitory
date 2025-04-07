<script setup lang="ts">
import { useLivingStore } from '@/store/livingStore'
import { useProfileStore } from '@/store/profileStore'
import dayjs from '@/plugins/dayjs'
import { storeToRefs } from 'pinia'
import { computed, onMounted } from 'vue'

const living = useLivingStore()
const profile = useProfileStore()
const { userCredentials } = storeToRefs(profile)
const { contractList } = storeToRefs(living)

const accountId = computed(() => {
  const raw = localStorage.getItem('account-data')
  if (!raw) return null
  try {
    const parsed = JSON.parse(raw)
    return parsed.id ?? null
  } catch (e) {
    console.error('Ошибка парсинга account-data из localStorage:', e)
    return null
  }
})

const resident = computed(() => {
  if (!accountId.value) return null
  return living.getResidentByAccountId(accountId.value)
})

const residentContracts = computed(() => {
  if (!resident.value) return []
  return contractList.value.filter((c) => resident.value.contracts.includes(c.id))
})

const isSettled = computed(() => residentContracts.value.some((c) => c.status === 'Заселён'))

const isEvicted = computed(() => residentContracts.value.some((c) => c.status === 'Выселен'))

function statusColor(status: string) {
  switch (status) {
    case 'Заселён':
      return 'bg-green-100 text-green-800'
    case 'Выселен':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

function formatDate(date: string) {
  return dayjs(date).format('D MMMM YYYY')
}

onMounted(async () => {
  await living.getAllBlocks()
  await living.getResidents()
  await living.getContracts()
})
</script>

<template>
  <div class="bg-white rounded-2xl p-6 shadow border space-y-4">
    <h3 class="text-xl font-semibold text-gray-900">Договоры на проживание</h3>

    <div class="text-sm">
      <div v-if="isSettled" class="flex items-center text-green-600 gap-2">
        <span>✅</span>
        <span>Статус договора: Заселён</span>
      </div>
      <div v-else-if="isEvicted" class="flex items-center text-red-600 gap-2">
        <span>⛔</span>
        <span>Статус договора: Выселен</span>
      </div>
      <div v-else class="flex items-center text-gray-500 gap-2">
        <span>ℹ️</span>
        <span>Нет активных заявок</span>
      </div>
    </div>

    <div v-if="residentContracts.length">
      <h4 class="text-sm font-medium text-gray-700 mb-2">Ваши контракты:</h4>
      <ul class="space-y-2">
        <li
          v-for="contract in residentContracts"
          :key="contract.id"
          class="border rounded-lg p-3 bg-gray-50"
        >
          <div class="flex justify-between items-center mb-1">
            <span class="font-semibold text-gray-800"> Контракт №{{ contract.id }} </span>
            <span
              class="text-xs px-2 py-1 rounded-full font-medium"
              :class="statusColor(contract.status)"
            >
              {{ contract.status }}
            </span>
          </div>
          <div class="text-sm text-gray-600">
            Комната: {{ contract.blockId }}<br />
            Дата регистрации договора:
            <span class="font-medium">{{ formatDate(contract.createdAt) }}</span>
          </div>
        </li>
      </ul>
    </div>
    <div v-else class="text-sm text-gray-500">Контракты отсутствуют.</div>
  </div>
</template>
