<script lang="ts" setup>
import { useSqlStore } from '@/store/sqlStore'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'

const sqlStore = useSqlStore()
const { error, loading } = storeToRefs(sqlStore)

const sqlQuery = ref('')
const resultData = ref()
const errorMessage = ref('')

async function executeSqlQuery() {
  if (!sqlQuery.value.trim()) {
    errorMessage.value = 'Введите SQL-запрос.'
    return
  }

  const response = await sqlStore.rawRequest(sqlQuery.value)
  resultData.value = response
}
</script>

<template>
  <div class="p-6 bg-white shadow-lg rounded-lg">
    <h2 class="text-2xl font-semibold mb-4">Выполнение SQL-запросов</h2>

    <textarea
      v-model="sqlQuery"
      placeholder="Введите SQL-запрос"
      class="w-full p-3 border border-gray-300 rounded-md mb-4 h-32 resize-none"
    ></textarea>

    <div class="flex justify-between items-center mb-4">
      <button
        @click="executeSqlQuery"
        :disabled="loading"
        class="bg-blue-500 text-white p-3 rounded-md hover:bg-blue-600 disabled:bg-gray-400"
      >
        {{ loading ? 'Выполнение...' : 'Выполнить запрос' }}
      </button>
      <p v-if="errorMessage" class="text-red-500 text-sm">{{ errorMessage }}</p>
    </div>

    <div v-if="resultData" class="overflow-x-auto">
      <table class="min-w-full border-collapse table-auto">
        <thead>
          <tr class="bg-gray-100">
            <th
              v-for="(value, key) in resultData[0]"
              :key="key"
              class="px-4 py-2 border border-gray-300"
            >
              {{ key }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in resultData" :key="index">
            <td v-for="(value, key) in row" :key="key" class="px-4 py-2 border border-gray-300">
              {{ value !== null ? value : 'NULL' }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else-if="!loading" class="mt-4 text-center text-gray-500">
      Нет данных для отображения.
    </div>
  </div>
</template>

<style scoped></style>
