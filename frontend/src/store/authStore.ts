import { login, registration } from "@/api/profile";
import { defineStore } from "pinia";
import { useRouter } from 'vue-router'
import { ref } from "vue";

export const useAuthStore = defineStore('useAuthStore', () => {
    const username = ref<string>('') 
    const password = ref<string>('') 
    const error = ref<boolean>(false)

    const router = useRouter()

    async function signIn() {
        try {
            error.value = false

            await login({ username: username.value, password: password.value })
            router.replace('/')
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    async function register() {
        try {
            error.value = false
            await registration({ username: username.value, password: password.value })
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    return {
        username,
        password,
        error,
        signIn,
        register,
    }
})