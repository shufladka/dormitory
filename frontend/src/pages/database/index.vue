<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import VisualizeConnectionsSection from '@/components/VisualizeConnectionsSection.vue'
import RawRequestSection from '@/components/RawRequestSection.vue'
import DatabaseSectionSwitcher from '@/components/DatabaseSectionSwitcher.vue'
import { useSqlStore } from '@/store/sqlStore'
import { storeToRefs } from 'pinia'

const sqlStore = useSqlStore()
const { switcherOption } = storeToRefs(sqlStore)
</script>

<template>
  <RootContainer>
    <!-- <VisualizeConnectionsSection /> -->
    <template #switcher>
      <DatabaseSectionSwitcher />
    </template>
    <RawRequestSection v-if="switcherOption === 0" />
    <VisualizeConnectionsSection v-if="switcherOption === 1" />
  </RootContainer>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
