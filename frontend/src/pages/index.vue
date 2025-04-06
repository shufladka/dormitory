<script setup>
import { onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'

onBeforeMount(() => {
  const router = useRouter()
  router.replace('/profile')
})
</script>

<template>
  <RootContainer />
</template>