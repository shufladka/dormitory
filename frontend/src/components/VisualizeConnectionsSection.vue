<script lang="ts" setup>
import { useSqlStore } from '@/store/sqlStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'
import { DataSet, Network } from 'vis-network/standalone'

const sqlStore = useSqlStore()
const { tables, foreignKeys } = storeToRefs(sqlStore)

// Визуализация данных с помощью Vis.js
const drawGraph = () => {
  const nodes = new DataSet(
    tables.value.map((table, index) => ({
      id: table.table_name,
      label: table.table_name,
      shape: 'box', // Используем прямоугольники для отображения таблиц
      width: table.table_name.length * 8, // Делаем ширину в зависимости от длины названия
      height: 30,
      font: {
        size: 16,
        color: '#ffffff',
      },
      color: {
        background: '#007bff',
        border: '#ffffff',
      },
    }))
  )

  const edges = new DataSet(
    foreignKeys.value.map((fk) => ({
      from: fk.table_name,
      to: fk.referenced_table_name,
      label: `${fk.column_name} → ${fk.referenced_column_name}`,
      arrows: {
        to: {
          enabled: true,
          scaleFactor: 0.5,
        },
      },
      color: {
        color: '#999',
        highlight: '#ff0000',
        hover: '#ff0000',
      },
      font: {
        size: 12,
        color: '#000000',
        align: 'middle',
      },
    }))
  )

  const container = document.getElementById('network') as HTMLElement

  const data = {
    nodes: nodes,
    edges: edges,
  }

  const options = {
    nodes: {
      shape: 'box', // Прямоугольники для отображения таблиц
      size: 30,
      font: {
        size: 14,
        color: '#ffffff',
      },
      color: {
        background: '#007bff',
        border: '#ffffff',
      },
    },
    edges: {
      arrows: {
        to: {
          enabled: true,
          scaleFactor: 0.5,
        },
      },
      color: {
        color: '#999',
        highlight: '#ff0000',
        hover: '#ff0000',
      },
      font: {
        size: 12,
        color: '#000000',
        align: 'middle',
      },
    },
    physics: {
      enabled: true,
      barnesHut: {
        gravitationalConstant: -8000,
        centralGravity: 0.2,
        springLength: 150,
        springConstant: 0.04,
        damping: 0.09,
      },
    },
  }

  new Network(container, data, options)
}

onMounted(async () => {
  tables.value = await sqlStore.getAllTables()
  foreignKeys.value = await sqlStore.getAllForeignKeys()
  drawGraph()
})
</script>

<template>
  <div class="p-4">
    <h2 class="text-2xl font-semibold mb-4">Структура базы данных</h2>

    <div id="network" class="bg-gray-50 border rounded-md" style="height: 500px"></div>

    <div class="mt-6">
      <h3 class="text-xl font-semibold mb-2">Таблицы</h3>
      <ul class="list-disc pl-5">
        <li v-for="table in tables" :key="table.table_name" class="text-gray-700">
          {{ table.table_name }}
        </li>
      </ul>
    </div>

    <div class="mt-6">
      <h3 class="text-xl font-semibold mb-2">Связи</h3>
      <ul class="list-disc pl-5">
        <li v-for="(fk, index) in foreignKeys" :key="index" class="text-gray-700">
          {{ fk.table_name }} ({{ fk.column_name }}) → {{ fk.referenced_table_name }} ({{
            fk.referenced_column_name
          }}
          )
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
#network {
  width: 100%;
  height: 500px;
}
</style>
