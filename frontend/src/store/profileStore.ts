import { createAddress, createPassport, getAddressInfo, getPassportInfo, updateAddress, updatePassport } from '@/api/profile'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'
import { UserCredentials } from './authStore'

export interface AddressInfo {
    id?: number
    isCity: boolean
    settlement: string,
    street: string,
    buildingNumber: number,
    buildingIndex?: string,
    flatNumber?: number,
    zip: string
}

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
    const passport = ref<PassportInfo>()
    const address = ref<AddressInfo | null>(null)

    const surname = ref<string>('')
    const name = ref<string>('')
    const patronymic = ref<string>('')
    const birthDate = ref('')

    const isCity = ref<boolean>(false)
    const settlement = ref<string>('')
    const street = ref<string>('')
    const buildingNumber = ref<number>(0)
    const buildingIndex = ref<string | null>(null)
    const flatNumber = ref<number | null>(null)
    const zip = ref<string>('')

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

            const response = await getPassportInfo(userCredentials.value.id)
            passport.value = response
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
            mapPassportFields(passport.value)
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
            loadUserData()

            const response = await getAddressInfo(passport.value.id)
            address.value = response
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
            mapAddressFields(address.value)
        }
    }

    async function saveAddressInfo() {
        try {
            loading.value = true
            error.value = false

            await createAddress(address.value)
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

    function mapPassportFields(passportData: PassportInfo) {
        surname.value = passportData ? passportData.surname : ''
        name.value = passportData ? passportData.name : ''
        patronymic.value = passportData ? passportData.patronymic : ''
        birthDate.value = passportData ? passportData.birthDate : ''
    }

    function mapAddressFields(addressData: AddressInfo) {
        if (addressData) {
            isCity.value = addressData.isCity
            settlement.value = addressData.settlement
            street.value = addressData.street
            buildingNumber.value = addressData.buildingNumber
            buildingIndex.value = addressData.buildingIndex
            flatNumber.value = addressData.flatNumber
            zip.value = addressData.zip
        }
    }

    return {
        passport,
        address,
        surname,
        name,
        patronymic,
        birthDate,
        isCity,
        settlement,
        street,
        buildingNumber,
        buildingIndex,
        flatNumber,
        zip,
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
