<script setup lang="ts">
import { useAuthStore } from '@/store/authStore'
import { BlockInfo, ResidentInfo, useLivingStore } from '@/store/livingStore'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { computed, onMounted, toRaw } from 'vue'

const props = defineProps<{
  room: BlockInfo
}>()

const living = useLivingStore()
const { contractList } = storeToRefs(living)

const profile = useProfileStore()
const { isAuthenticated, isResident, userCredentials } = storeToRefs(profile)

const hasContractThisBlock = computed(() => {
  return living.residentHasContract(userCredentials.value.id, props.room.id)
})

const showButton = computed(() => {
  if (isAuthenticated.value && isResident.value) {
    return true
  }
  return false
})

async function handleMoveIn() {
  const response = await living.createContract(props.room.id)

  const resident: ResidentInfo = toRaw(
    living.getResidentByAccountId(toRaw(userCredentials.value.id))
  )
  resident.contracts.push(response.id)

  await living.updateResident(resident)
}

async function handleMoveOut() {
  const resident = living.getResidentByAccountId(userCredentials.value.id)
  if (!resident) {
    return
  }

  const contract = contractList.value.find(
    (c) => c.blockId === props.room.id && resident.contracts.includes(c.id)
  )

  if (contract) {
    await living.removeContract(contract.id)
    await living.updateResident(resident)
  }
}
</script>

<template>
  <div class="rounded-2xl border border-gray-200 p-4 shadow-sm bg-white hover:shadow-md transition">
    <div class="flex justify-between items-center">
      <h3 class="text-lg font-semibold text-indigo-600">Блок №{{ room.roomNumber }}</h3>
      <span class="text-sm text-gray-500">Этаж {{ room.floor }}</span>
    </div>
    <div class="mt-2 text-sm text-gray-700 space-y-1">
      <p>
        Свободных мест: <span class="font-medium">{{ room.capacity }}</span>
      </p>
      <p>
        Газ:
        <span :class="room.isGasified ? 'text-green-600' : 'text-red-600'">{{
          room.isGasified ? 'Да' : 'Нет'
        }}</span>
      </p>
      <p>
        С/У раздельный:
        <span :class="room.isBathroomSeparated ? 'text-green-600' : 'text-red-600'">{{
          room.isBathroomSeparated ? 'Да' : 'Нет'
        }}</span>
      </p>
    </div>

    <button
      v-if="showButton && !hasContractThisBlock"
      @click="handleMoveIn"
      class="w-full mt-2 px-4 py-2 rounded-xl bg-blue-600 text-white hover:bg-blue-700 transition"
    >
      Подать заявку на заселение
    </button>
    <button
      v-if="hasContractThisBlock"
      @click="handleMoveOut"
      class="w-full mt-2 px-4 py-2 rounded-xl bg-stone-500 text-white hover:bg-stone-600 transition"
    >
      Отменить заявку на заселение
    </button>
  </div>
</template>
