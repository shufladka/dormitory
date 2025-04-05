import { login, registration } from "@/api/profile";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore('useAuthStore', () => {
    const username = ref<string>('') 
    const password = ref<string>('') 
    const error = ref<boolean>(false)

    async function signIn() {
        try {
            error.value = false

            const response = await login({ username: username.value, password: password.value })
            console.log(response)
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    async function register() {
        try {
            error.value = false

            const response = await registration({ username: username.value, password: password.value })
            console.log(response)
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