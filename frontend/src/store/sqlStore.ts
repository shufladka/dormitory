
import { getForeignKeys, getTables, postRawRequest } from "@/api/sql";
import { defineStore } from "pinia";
import { ref } from "vue";

export interface TableInfo {
    table_name: string
}

export interface ForeignKeysInfo {
    table_name: string
    column_name: string
    referenced_table_name: string
    referenced_column_name: string
}

export const useSqlStore = defineStore('useSqlStore', () => {
    const error = ref<boolean>(false)
    const loading = ref<boolean>(false)

    const tables = ref<TableInfo[]>([])
    const foreignKeys = ref<ForeignKeysInfo[]>([])

    async function rawRequest(payload: string) {
        try {
            error.value = false
            loading.value = true

            return postRawRequest(payload)
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    async function getAllTables() {
        try {
            error.value = false
            loading.value = true

            return getTables()
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    async function getAllForeignKeys() {
        try {
            error.value = false
            loading.value = true

            return getForeignKeys()
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    return {
        error,
        loading,

        tables,
        foreignKeys,

        rawRequest,
        getAllTables,
        getAllForeignKeys,
    }
})