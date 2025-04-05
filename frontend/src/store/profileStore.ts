import { createAddress, createContact, createPassport, getAddressInfo, getContactInfo, getPassportInfo, updateAddress, updateContact, updatePassport } from '@/api/profile'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'
import { UserCredentials } from './authStore'

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

export interface ContactInfo {
    id: number,
    provider: string,
    code: string,
    phoneNumber: string,
    email: string
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

    const contact = ref<ContactInfo>({
        id: null,
        provider: null,
        code: null,
        phoneNumber: null,
        email: null
    })

    const loading = ref<boolean>(false)
    const errorPassport = ref<boolean>(false)
    const errorAddress = ref<boolean>(false)
    const errorContact = ref<boolean>(false)

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
            errorPassport.value = false

            passport.value.accountId = userCredentials.value.id
            await createPassport(passport.value)
        } catch (e: unknown) {
            console.log(e)
            errorPassport.value = true
        } finally {
            loading.value = false
        }
    }

    async function updatePassportInfo() {
        try {
            loading.value = true
            errorPassport.value = false

            await updatePassport(passport.value)
        } catch (e: unknown) {
            console.log(e)
            errorPassport.value = true
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
            errorAddress.value = false

            const response = await createAddress(address.value)
            passport.value.addressId = response.id
            await updatePassportInfo()
        } catch (e: unknown) {
            console.log(e)
            errorAddress.value = true
        } finally {
            loading.value = false
        }
    }

    async function updateAddressInfo() {
        try {
            loading.value = true
            errorAddress.value = false

            await updateAddress(address.value)
        } catch (e: unknown) {
            console.log(e)
            errorAddress.value = true
        } finally {
            loading.value = false
        }
    }

    async function getContact() {
        try {
            loading.value = true

            contact.value = await getContactInfo(passport.value.id)
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
        }
    }

    async function saveContactInfo() {
        try {
            loading.value = true
            errorContact.value = false

            const response = await createContact(contact.value)
            passport.value.contactId = response.id
            await updatePassportInfo()
        } catch (e: unknown) {
            console.log(e)
            errorContact.value = true
        } finally {
            loading.value = false
        }
    }

    async function updateContactInfo() {
        try {
            loading.value = true
            errorContact.value = false

            await updateContact(contact.value)
        } catch (e: unknown) {
            console.log(e)
            errorContact.value = true
        } finally {
            loading.value = false
        }
    }

    return {
        passport,
        address,
        contact,
        errorPassport,
        errorAddress,
        errorContact,
        loading,
        getPassport,
        getAddress,
        getContact,
        savePassportInfo,
        updatePassportInfo,
        saveAddressInfo,
        updateAddressInfo,
        saveContactInfo,
        updateContactInfo,
        isAuthenticated,
    }
})
