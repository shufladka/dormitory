<script setup>
import { onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'

onBeforeMount(() => {
  const router = useRouter()
  router.replace('/dormitories')
})
</script>

<template>
  <RootContainer />
</template>