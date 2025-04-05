<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import { useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { onBeforeMount, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

defineProps({
  title: String,
  loading: Boolean,
  error: Boolean,
})

const emit = defineEmits(['cancel', 'save'])

function cancel() {
  emit('cancel')
}

function save() {
  emit('save')
}
</script>

<template>
  <div class="pt-4">
    <h3 class="text-lg font-medium leading-6 text-gray-900">{{ title }}</h3>
  </div>

  <slot />

  <div class="flex justify-end gap-3">
    <div v-if="error" class="pt-4 flex place-content-end text-pink-700">Произошла ошибка</div>
    <button
      @click="cancel"
      :disabled="loading"
      class="inline-flex justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
    >
      Отменить
    </button>
    <button
      @click="save"
      :disabled="loading"
      class="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
    >
      Сохранить
    </button>
  </div>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
