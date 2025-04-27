<script setup>
import { onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import RootContainer from '@/components/RootContainer.vue'

onBeforeMount(() => {
  const router = useRouter()
  router.replace('/dormitories')
})
</script>

<template>
  <RootContainer />
</template>