import { login, registration, updateAccountInfo } from "@/api/profile";
import { defineStore } from "pinia";
import { ref } from "vue";

export interface DormitoryTypes {
    id: number,
    name: string
}

export interface DormitoryInfo {
    id: number,
    addressId: number,
    dormitoryTypeId: number,
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
    statusId: number,
    reason: string,
    bankName: string,
    code: string,
    createdAt: string,
    updatedAt: string
}

export interface DebtInfo {
    id: number,
    contractId: number,
    statusId: number,
    amount: string,
    createdAt: string,
    updatedAt: string
}

export const useLivingStore = defineStore('useLivingStore', () => {
    const dormitoryTypeList = ref<DormitoryTypes[]>([])
    const dormitoryList = ref<DormitoryInfo[]>([])
    const blockList = ref<BlockInfo[]>([])
    const statusList = ref<StatusInfo[]>([])
    const paymentList = ref<PaymentInfo[]>([])
    const violationList = ref<ViolationInfo[]>([])
    const debtList = ref<DebtInfo[]>([])

    const loading = ref<boolean>(false)
    const errorDormitoryType = ref<boolean>(false)
    const errorDormitory = ref<boolean>(false)
    const errorBlock = ref<boolean>(false)
    const errorStatus = ref<boolean>(false)
    const errorPayment = ref<boolean>(false)
    const errorViolation = ref<boolean>(false)
    const errorDebt = ref<boolean>(false)

    return {
        dormitoryTypeList,
        dormitoryList,
        blockList,
        statusList,
        paymentList,
        violationList,
        debtList,

        loading,

        errorDormitoryType,
        errorDormitory,
        errorBlock,
        errorStatus,
        errorPayment,
        errorViolation,
        errorDebt
    }
})