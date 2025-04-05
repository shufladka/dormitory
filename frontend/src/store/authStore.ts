import { login, registration } from "@/api/profile";
import { defineStore } from "pinia";
import { useRouter } from 'vue-router'
import { ref } from "vue";

export interface UserCredentials {
    id: number,
    username: string,
    password: string,
    roleIds: string[]
}

export const useAuthStore = defineStore('useAuthStore', () => {
    const credentials = ref<UserCredentials>({
        id: null,
        username: null,
        password: null,
        roleIds: null
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

    return {
        credentials,
        error,
        signIn,
        register,
    }
})