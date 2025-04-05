import { createPassport, getPassportInfo } from '@/api/profile'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

export interface PassportInfo {
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

    const error = ref<boolean>(false)

    const router = useRouter()

    async function getPassport() {
        try {
            error.value = false

            const response = await getPassportInfo(10)
            passport.value = response

            mapPassportFields(passport.value)
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    async function savePassport() {
        try {
            error.value = false

            const response = await createPassport({
                surname: surname.value,
                name: name.value,
                patronymic: patronymic.value,
                birthDate: birthDate.value,
            })
            // router.replace('/')
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    async function updatePassport() {
        try {
            error.value = false

            // const response = await registration({ username: username.value, password: password.value })
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        }
    }

    function mapPassportFields(passportData: PassportInfo) {
        surname.value = passportData.surname || ''
        name.value = passportData.name || ''
        patronymic.value = passportData.patronymic || ''
        birthDate.value = passportData.birthDate || ''
    }

    return {
        passport,
        surname,
        name,
        patronymic,
        birthDate,
        error,
        getPassport,
    }
})
