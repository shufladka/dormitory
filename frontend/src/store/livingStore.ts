import { getBlockDormitoryList, getBlockList, getDormitoryList, getEmployeeList, getResidentList } from "@/api/living";
import { defineStore } from "pinia";
import { ref } from "vue";

export interface DormitoryTypes {
    id: number,
    name: string
}

export interface DormitoryInfo {
    id: number,
    addressId: number,
    dormitoryType: string,
    floors: number,
    blocks: number,
    hasLift: boolean
}

export interface BlockInfo {
    id: number,
    dormitoryId: number,
    roomNumber: number,
    isGasified: boolean,
    isBathroomSeparated: boolean,
    capacity: number,
    floor: number
}

export interface StatusInfo {
    id: number,
    name: string
}

export interface PaymentInfo {
    id: number,
    contractId: number,
    amount: string,
    bankName: string,
    code: string,
    createdAt: string,
    updatedAt: string
}

export interface ViolationInfo {
    id: number,
    residentId: number,
    status: string,
    reason: string,
    bankName: string,
    code: string,
    createdAt: string,
    updatedAt: string
}

export interface DebtInfo {
    id: number,
    contractId: number,
    status: string,
    amount: string,
    createdAt: string,
    updatedAt: string
}

export interface ResidentInfo {
    id: number,
    passportId: number,
    contracts: number[]
}

export interface EmployeeInfo {
    id: number,
    position: string
}

export const useLivingStore = defineStore('useLivingStore', () => {
    const dormitoryTypeList = ref<DormitoryTypes[]>([])
    const dormitoryList = ref<DormitoryInfo[]>([])
    const blockList = ref<BlockInfo[]>([])
    const statusList = ref<StatusInfo[]>([])
    const paymentList = ref<PaymentInfo[]>([])
    const violationList = ref<ViolationInfo[]>([])
    const debtList = ref<DebtInfo[]>([])
    const residentList = ref<ResidentInfo[]>([])
    const employeeList = ref<EmployeeInfo[]>([])

    const loading = ref<boolean>(false)
    const errorDormitoryType = ref<boolean>(false)
    const errorDormitory = ref<boolean>(false)
    const errorBlock = ref<boolean>(false)
    const errorStatus = ref<boolean>(false)
    const errorPayment = ref<boolean>(false)
    const errorViolation = ref<boolean>(false)
    const errorDebt = ref<boolean>(false)
    const errorResident = ref<boolean>(false)
    const errorEmployee = ref<boolean>(false)

    async function getDormitories() {
        try {
            loading.value = true
            errorDormitory.value = false

            dormitoryList.value = await getDormitoryList()
        } catch (e: unknown) {
            console.log(e)
            errorDormitory.value = true
        } finally {
            loading.value = false
        }
    }

    async function getAllBlocks() {
        try {
            loading.value = true
            errorBlock.value = false

            blockList.value = await getBlockList()
        } catch (e: unknown) {
            console.log(e)
            errorBlock.value = true
        } finally {
            loading.value = false
        }
    }

    async function getBlocksByDormitory(dormitoryId: number) {
        try {
            loading.value = true
            errorBlock.value = false

            blockList.value = await getBlockDormitoryList(dormitoryId)
        } catch (e: unknown) {
            console.log(e)
            errorBlock.value = true
        } finally {
            loading.value = false
        }
    }

    async function getResidents() {
        try {
            loading.value = true
            errorResident.value = false

            residentList.value = await getResidentList()
        } catch (e: unknown) {
            console.log(e)
            errorResident.value = true
        } finally {
            loading.value = false
        }
    }

    async function getEmployees() {
        try {
            loading.value = true
            errorEmployee.value = false

            employeeList.value = await getEmployeeList()
        } catch (e: unknown) {
            console.log(e)
            errorEmployee.value = true
        } finally {
            loading.value = false
        }
    }

    return {
        dormitoryTypeList,
        dormitoryList,
        blockList,
        statusList,
        paymentList,
        violationList,
        debtList,
        residentList,
        employeeList,

        loading,

        errorDormitoryType,
        errorDormitory,
        errorBlock,
        errorStatus,
        errorPayment,
        errorViolation,
        errorDebt,
        errorResident,
        errorEmployee,

        getDormitories,
        getAllBlocks,
        getBlocksByDormitory,
        getResidents,
        getEmployees,
    }
})