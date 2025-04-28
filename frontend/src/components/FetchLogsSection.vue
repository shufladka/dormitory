<script lang="ts" setup>
import { useSqlStore } from '@/store/sqlStore'
import { storeToRefs } from 'pinia'
import { ref, onMounted } from 'vue'

const sqlStore = useSqlStore()
const { loading } = storeToRefs(sqlStore)

const logStatus = ref<boolean>(false) // Состояние логирования
const logLimit = ref<number>(10) // Число N для отображения логов
const logsData = ref<any[]>([]) // Массив с данными логов
const errorMessage = ref<string>('') // Сообщения об ошибках
const currentLogStatus = ref<string>('') // Текущий статус general_log

// Функция для получения текущего статуса general_log
async function getCurrentLogStatus() {
  const result = await sqlStore.rawRequest('SHOW VARIABLES LIKE "general_log";')
  console.log(result)
  if (result && result.length > 0) {
    currentLogStatus.value = result[0].Value === 'ON' ? 'Разрешен' : 'Запрещен'
    logStatus.value = result[0].Value === 'ON'
  }
}

// Функция для переключения логирования
async function toggleLogStatus(enable: boolean) {
  errorMessage.value = ''
  try {
    // Асинхронно меняем статус логирования
    if (enable === true) {
      await sqlStore.setGeneralLogOn()
    } else {
      await sqlStore.setGeneralLogOff()
    }

    // Обновляем состояние UI только после успешного запроса
    logStatus.value = enable
    currentLogStatus.value = logStatus.value ? 'Разрешен' : 'Запрещен'
  } catch (err) {
    console.error(err)
    errorMessage.value = 'Ошибка при изменении статуса логирования.'
  }
}

// Функция для получения логов
async function fetchLogs() {
  if (!logLimit.value) {
    errorMessage.value = 'Введите количество логов для отображения.'
    return
  }

  try {
    const response = await sqlStore.rawRequest(
      `SELECT * FROM mysql.general_log ORDER BY event_time DESC LIMIT ${logLimit.value};`
    )
    logsData.value = response
  } catch (err) {
    console.error(err)
    errorMessage.value = 'Ошибка при получении логов.'
  }
}

// Загружаем текущий статус логирования при монтировании компонента
onMounted(async () => {
  await getCurrentLogStatus()
})
</script>

<template>
  <div class="p-6 bg-white shadow-lg rounded-lg">
    <h2 class="text-2xl font-semibold mb-4">Управление логами MySQL</h2>

    <!-- Текущий статус логирования -->
    <div class="mb-4">
      Текущий статус логирования:
      <span class="font-semibold">{{ currentLogStatus }}</span>
    </div>

    <!-- Кнопки для разрешения/запрета логирования -->
    <div class="flex space-x-4 mb-4">
      <button
        @click="toggleLogStatus(true)"
        :class="logStatus ? 'bg-green-500' : 'bg-gray-400'"
        class="text-white p-3 rounded-md hover:bg-green-600 disabled:bg-gray-400"
        :disabled="loading"
      >
        Разрешить запись логов
      </button>
      <button
        @click="toggleLogStatus(false)"
        :class="logStatus ? 'bg-gray-400' : 'bg-red-500'"
        class="text-white p-3 rounded-md hover:bg-red-600 disabled:bg-gray-400"
        :disabled="loading"
      >
        Запретить запись логов
      </button>
    </div>

    <!-- Поле ввода для количества последних логов -->
    <div class="mb-4">
      <input
        v-model="logLimit"
        type="number"
        min="1"
        class="w-full p-3 border border-gray-300 rounded-md"
        placeholder="Введите количество последних логов"
      />
    </div>

    <!-- Кнопка для вывода последних N логов -->
    <div class="flex justify-start mb-4">
      <button
        @click="fetchLogs"
        :disabled="loading"
        class="bg-blue-500 text-white p-3 rounded-md hover:bg-blue-600 disabled:bg-gray-400"
      >
        Вывести последние {{ logLimit }} логов
      </button>
    </div>

    <!-- Сообщение об ошибке -->
    <p v-if="errorMessage" class="text-red-500 text-sm">{{ errorMessage }}</p>

    <!-- Таблица с логами -->
    <div v-if="logsData.length > 0" class="overflow-x-auto">
      <table class="min-w-full border-collapse table-auto">
        <thead>
          <tr class="bg-gray-100">
            <th
              v-for="(value, key) in logsData[0]"
              :key="key"
              class="px-4 py-2 border border-gray-300"
            >
              {{ key }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in logsData" :key="index">
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

<style scoped>
</style>
