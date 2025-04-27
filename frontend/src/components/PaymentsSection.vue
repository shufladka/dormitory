<script setup lang="ts">
import { BalanceInfo, PaymentInfo, useLivingStore } from '@/store/livingStore'
import { useProfileStore } from '@/store/profileStore'
import dayjs from '@/plugins/dayjs'
import { storeToRefs } from 'pinia'
import { computed, onMounted, ref } from 'vue'

const living = useLivingStore()
const profile = useProfileStore()
const { userCredentials } = storeToRefs(profile)

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

function formatDate(date: string) {
  return dayjs(date).format('D MMMM YYYY')
}

const balance = ref<BalanceInfo>(null)

const paymentAmount = ref<number>()
const paymentBankName = ref<string>()
const paymentCode = ref<string>()

const validate = computed(
  () =>
    (paymentAmount.value ?? 0) > 0 &&
    (paymentBankName.value?.length ?? 0) > 0 &&
    (paymentCode.value?.length ?? 0) > 0
)

async function addPayment() {
  if (validate) {
    const balanceId = resident.value.balanceId

    await living.createPayment({
      balanceId,
      amount: paymentAmount.value,
      bankName: paymentBankName.value,
      code: paymentCode.value,
    })

    paymentAmount.value = null
    paymentBankName.value = null
    paymentCode.value = null

    balance.value = await living.getBalance(balanceId)
  }
}

onMounted(async () => {
  await living.getResidents()
  balance.value = await living.getBalance(resident.value.balanceId)
})
</script>

<template>
  <div class="bg-white rounded-2xl p-6 shadow border space-y-6">
    <h3 class="text-xl font-semibold text-gray-900">Оплата за проживание</h3>

    <div v-if="balance" class="space-y-2">
      <div class="text-gray-700">
        Текущий баланс:
        <span class="font-bold">{{ balance.amount }} BYN</span>
      </div>

      <div class="border-t pt-4">
        <h4 class="text-lg font-medium text-gray-800 mb-2">Добавить новый платёж</h4>

        <div class="flex flex-row gap-2">
          <input
            v-model="paymentAmount"
            type="number"
            step="0.01"
            placeholder="Сумма"
            class="w-full border rounded-lg p-2 text-sm"
          />
          <input
            v-model="paymentBankName"
            type="text"
            placeholder="Название банка"
            class="w-full border rounded-lg p-2 text-sm"
          />
          <input
            v-model="paymentCode"
            type="text"
            placeholder="Код операции"
            class="w-full border rounded-lg p-2 text-sm"
          />
          <button
            @click="addPayment"
            :disabled="!validate"
            class="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 text-white font-semibold py-2 rounded-lg"
          >
            Добавить платёж
          </button>
        </div>
      </div>

      <div class="border-t pt-4">
        <h4 class="text-lg font-medium text-gray-800 mb-2">История платежей</h4>

        <ul v-if="balance.payments.length" class="space-y-3">
          <li
            v-for="payment in balance.payments"
            :key="payment.id"
            class="gap-2 p-3 bg-gray-50 rounded-lg border"
          >
            <div class="flex justify-between text-sm text-gray-700">
              <div>
                Сумма платежа: <span class="font-semibold">{{ payment.amount }} BYN</span>
              </div>
              <div>
                Банк: <span class="font-semibold">{{ payment.bankName }}</span>
              </div>
            </div>
            <div class="text-sm text-gray-600 mt-0.5">
              Код операции:
              <span class="font-medium">{{ payment.code }}</span>
            </div>
            <div class="text-sm text-gray-600 mt-0.5">
              Дата оплаты:
              <span class="font-medium">{{ formatDate(payment.createdAt) }}</span>
            </div>
          </li>
        </ul>

        <div v-else class="text-sm text-gray-500">Платежи отсутствуют.</div>
      </div>
    </div>

    <div v-else class="text-gray-500 text-center">Загрузка данных баланса...</div>
  </div>
</template>
