import { getAccountsList, login } from "@/api/profile";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore('useAuthStore', () => {
    const username = ref<string>('') 
    const password = ref<string>('') 

    async function signIn() {
        await login({ username: username.value, password: password.value })
    }

    return {
        username,
        password,
        signIn
    }
})