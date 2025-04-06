import { login, registration, updateAccountInfo } from "@/api/profile";
import { defineStore } from "pinia";
import { useRouter } from 'vue-router'
import { ref } from "vue";
import { RoleInfo } from "./profileStore";

export interface UserCredentials {
    id: number,
    username: string,
    password: string,
    roles: RoleInfo[]
}

export const useAuthStore = defineStore('useAuthStore', () => {
    const credentials = ref<UserCredentials>({
        id: null,
        username: null,
        password: null,
        roles: null
    })
    
    const error = ref<boolean>(false)

    const router = useRouter()

    async function signIn() {
        try {
            error.value = false

            const response = await login({ username: credentials.value.username, password: credentials.value.password })
            localStorage.setItem('account-data', JSON.stringify(response))

            router.replace('/')
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    async function register() {
        try {
            error.value = false
            await registration({ username: credentials.value.username, password: credentials.value.password })
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    async function updateAccount(roleIds?: number[]) {
        try {
            error.value = false

            credentials.value = JSON.parse(localStorage.getItem('account-data'))

            const response = await updateAccountInfo({
                id: credentials.value.id,
                username: credentials.value.username,
                password: credentials.value.password,
                roleIds
            })

            localStorage.setItem('account-data', JSON.stringify(response))
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    return {
        credentials,
        error,
        signIn,
        register,
        updateAccount,
    }
})