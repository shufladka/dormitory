import { createPassport, getPassportInfo, updatePassport } from '@/api/profile'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { UserCredentials } from './authStore'

export interface PassportInfo {
    id?: number
    surname: string
    name: string
    patronymic: string
    birthDate: string
    accountId?: number
    contactId?: number
    addressId?: number
}

export const useProfileStore = defineStore('useProfileStore', () => {
    const passport = ref<PassportInfo | null>(null)

    const surname = ref<string>('')
    const name = ref<string>('')
    const patronymic = ref<string>('')
    const birthDate = ref()

    const loading = ref<boolean>(false)
    const error = ref<boolean>(false)

    const router = useRouter()

    const userCredentials = ref<UserCredentials | null>(null)
    const loadUserData = () => {
        const storedUserData = localStorage.getItem('account-data')
        if (storedUserData) {
            userCredentials.value = JSON.parse(storedUserData)
        }
    }

    async function getPassport() {
        try {
            loading.value = true
            loadUserData()

            const response = await getPassportInfo(userCredentials.value.id)
            passport.value = response
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
            mapPassportFields(passport.value)
        }
    }

    async function save() {
        try {
            loading.value = true
            error.value = false

            passport.value.accountId = userCredentials.value.id
            await createPassport(passport.value)
        } catch (e: unknown) {
            console.log(passport.value)
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    async function update() {
        try {
            loading.value = true
            error.value = false

            await updatePassport(passport.value)
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    function mapPassportFields(passportData: PassportInfo) {
        surname.value = passportData ? passportData.surname : ''
        name.value = passportData ? passportData.name : ''
        patronymic.value = passportData ? passportData.patronymic : ''
        birthDate.value = passportData ? passportData.birthDate : ''
    }

    return {
        passport,
        surname,
        name,
        patronymic,
        birthDate,
        error,
        loading,
        getPassport,
        save,
        update,
    }
})
