import { 
    createAddress,
    createContact,
    createPassport,
    createRole,
    getAddressInfo,
    getContactInfo,
    getPassportInfo,
    getRoles,
    updateAddress,
    updateContact,
    updatePassport,
    updateRole
} from '@/api/profile'
import { defineStore } from 'pinia'
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

export interface RoleInfo {
    id: number,
    name: string
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

    const roleList = ref<RoleInfo[]>([])

    const loading = ref<boolean>(false)
    const errorPassport = ref<boolean>(false)
    const errorAddress = ref<boolean>(false)
    const errorContact = ref<boolean>(false)
    const errorRole = ref<boolean>(false)

    const profileOption = ref<boolean>(false)

    const userCredentials = ref<UserCredentials>()
    const loadUserData = () => {
        const storedUserData = localStorage.getItem('account-data')
        if (storedUserData) {
            userCredentials.value = JSON.parse(storedUserData)
        }
    }

    function updateUserData() {
        if (userCredentials.value) {
            localStorage.setItem('account-data', JSON.stringify(userCredentials.value))
        }
    }

    const isAuthenticated = computed(() => {
        if (localStorage.getItem('account-data'))
            return true
        return false
    })

    const isResident = computed(() => {
        const credentials = JSON.parse(localStorage.getItem('account-data'))
        const result = ref<boolean>(false)
        credentials.roles.map((item) => {
            if (item === "Проживающий") {
                result.value = true
            }
        })
        return result.value
    })

    async function getPassport(passportId?: number) {
        try {
            loading.value = true

            loadUserData()

            let id = passportId || userCredentials.value.id

            passport.value = await getPassportInfo(id)
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

    async function getRoleList() {
        try {
            loading.value = true

            roleList.value = await getRoles()
        } catch (e: unknown) {
            console.log(e)
        } finally {
            loading.value = false
        }
    }

    async function saveRoleInfo(role: RoleInfo) {
        try {
            loading.value = true
            errorRole.value = false

            await createRole(role)
            await getRoleList()
        } catch (e: unknown) {
            console.log(e)
            errorRole.value = true
        } finally {
            loading.value = false
        }
    }

    async function updateRoleInfo(role: RoleInfo) {
        try {
            loading.value = true
            errorRole.value = false

            await updateRole(role)
            await getRoleList()
        } catch (e: unknown) {
            console.log(e)
            errorRole.value = true
        } finally {
            loading.value = false
        }
    }

    return {
        userCredentials,
        isResident,
        profileOption,
        passport,
        address,
        contact,
        roleList,
        errorPassport,
        errorAddress,
        errorContact,
        errorRole,
        loading,
        getPassport,
        getAddress,
        getContact,
        getRoleList,
        savePassportInfo,
        updatePassportInfo,
        saveAddressInfo,
        updateAddressInfo,
        saveContactInfo,
        updateContactInfo,
        saveRoleInfo,
        updateRoleInfo,
        isAuthenticated,
        loadUserData,
        updateUserData,
    }
})
