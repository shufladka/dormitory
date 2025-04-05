import { createAddress, createPassport, getAddressInfo, getPassportInfo, updateAddress, updatePassport } from '@/api/profile'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'
import { UserCredentials } from './authStore'

export interface AddressInfo {
    id: number
    isCity: boolean
    settlement: string,
    street: string,
    buildingNumber: number,
    buildingIndex: string,
    flatNumber: number,
    zip: string
}

export interface PassportInfo {
    id: number
    surname: string
    name: string
    patronymic: string
    birthDate: string
    accountId: number
    contactId: number
    addressId: number
}

export const useProfileStore = defineStore('useProfileStore', () => {
    const passport = ref<PassportInfo>({
        id: null,
        surname: null,
        name: null,
        patronymic: null,
        birthDate: null,
        accountId: null,
        contactId: null,
        addressId: null
    })

    const address = ref<AddressInfo>({
        id: null,
        isCity: null,
        settlement: null,
        street: null,
        buildingNumber: null,
        buildingIndex: null,
        flatNumber: null,
        zip: null
    })

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

    const isAuthenticated = computed(() => {
        if (localStorage.getItem('account-data'))
            return true
        return false
    })

    async function getPassport() {
        try {
            loading.value = true
            loadUserData()

            passport.value = await getPassportInfo(userCredentials.value.id)
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
        }
    }

    async function savePassportInfo() {
        try {
            loading.value = true
            error.value = false

            passport.value.accountId = userCredentials.value.id
            await createPassport(passport.value)
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    async function updatePassportInfo() {
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

    async function getAddress() {
        try {
            loading.value = true

            address.value = await getAddressInfo(passport.value.id)
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
        }
    }

    async function saveAddressInfo() {
        try {
            loading.value = true
            error.value = false

            const response = await createAddress(address.value)
            passport.value.addressId = response.id
            await updatePassportInfo()
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    async function updateAddressInfo() {
        try {
            loading.value = true
            error.value = false

            await updateAddress(address.value)
        } catch (e: unknown) {
            console.log(e)
            error.value = true
        } finally {
            loading.value = false
        }
    }

    return {
        passport,
        address,
        error,
        loading,
        getPassport,
        getAddress,
        savePassportInfo,
        updatePassportInfo,
        saveAddressInfo,
        updateAddressInfo,
        isAuthenticated,
    }
})
