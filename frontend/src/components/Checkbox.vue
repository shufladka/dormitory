<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  modelValue: any[]
  value: string
  label: string
  description?: string
  name: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: any[]): void
}>()

function toggle() {
  const newValue = [...props.modelValue]
  const index = newValue.indexOf(props.value)
  if (index === -1) {
    newValue.push(props.value)
  } else {
    newValue.splice(index, 1)
  }
  emit('update:modelValue', newValue)
}

const isChecked = computed(() => props.modelValue.includes(props.value))
</script>

<template>
  <div class="flex gap-3">
    <div class="flex h-6 shrink-0 items-center">
      <div class="group grid size-4 grid-cols-1">
        <input
          :id="value"
          :name="name"
          type="checkbox"
          :checked="isChecked"
          @change="toggle"
          class="col-start-1 row-start-1 appearance-none rounded border border-gray-300 bg-white checked:border-indigo-600 checked:bg-indigo-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        />
        <svg
          class="pointer-events-none col-start-1 row-start-1 size-3.5 self-center justify-self-center stroke-white"
          viewBox="0 0 14 14"
          fill="none"
        >
          <path
            class="opacity-0 group-has-[:checked]:opacity-100"
            d="M3 8L6 11L11 3.5"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </div>
    </div>
    <div class="text-sm/6">
      <label :for="value" class="font-medium text-gray-900">{{ label }}</label>
      <p v-if="description" class="text-gray-500">{{ description }}</p>
    </div>
  </div>
</template>
