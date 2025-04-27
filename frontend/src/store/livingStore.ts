import { updateAccountInfo } from "@/api/auth";
import { createContractInfo, createPaymentInfo, getBalanceById, getBalanceList, getBalancesList, getBlockDormitoryList, getBlockList, getContractList, getDormitoryList, getEmployeeList, getResidentList, removeContractInfo, updateContractInfo, updateResidentInfo } from "@/api/living";
import { defineStore } from "pinia";
import { computed, ref } from "vue";

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
    balanceId: number,
    amount: number,
    bankName: string,
    code: string,
    createdAt: string,
    updatedAt: string
}

export interface ContractInfo {
    id: number,
    blockId: number,
    status: string,
    statusId: number,
    rentPrice: string,
    createdAt: string,
    updatedAt: string
}

export interface BalanceInfo {
    id: number,
    amount: string,
    payments: PaymentInfo[],
    createdAt: string,
    updatedAt: string
}

export interface ResidentInfo {
    id: number,
    passportId: number,
    balanceId: number,
    accountId: number,
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
    const balanceList = ref<BalanceInfo[]>([])
    const residentList = ref<ResidentInfo[]>([])
    const employeeList = ref<EmployeeInfo[]>([])
    const contractList = ref<ContractInfo[]>([])

    const loading = ref<boolean>(false)
    const errorDormitoryType = ref<boolean>(false)
    const errorDormitory = ref<boolean>(false)
    const errorBlock = ref<boolean>(false)
    const errorStatus = ref<boolean>(false)
    const errorPayment = ref<boolean>(false)
    const errorBalance = ref<boolean>(false)
    const errorResident = ref<boolean>(false)
    const errorEmployee = ref<boolean>(false)
    const errorContract = ref<boolean>(false)

    function residentHasContract(accountId: number, blockId: number): boolean {
        const resident = residentList.value.find(r => r.accountId === accountId)
        if (!resident) return false
    
        return resident.contracts.some(contractId => {
            const contract = contractList.value.find(c => c.id === contractId)
            return contract?.blockId === blockId
        })
    }

    function getResidentByAccountId(accountId: number) {
        return residentList.value.find((resident: ResidentInfo) => resident.accountId === accountId)
    }

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

    async function updateResident(resident: ResidentInfo) {
        try {
            loading.value = true
            errorResident.value = false

            await updateResidentInfo(resident)
            await getResidents()
            await getContracts()
            
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

    async function getContracts() {
        try {
            loading.value = true
            errorContract.value = false

            contractList.value = await getContractList()
        } catch (e: unknown) {
            console.log(e)
            errorContract.value = true
        } finally {
            loading.value = false
        }
    }

    async function createContract(blockId: number) {
        try {
            loading.value = true
            errorContract.value = false

            const response = await createContractInfo({ blockId, statusId: 1 })
            return response
        } catch (e: unknown) {
            console.log(e)
            errorContract.value = true
            return null
        } finally {
            loading.value = false
        }
    }

    async function updateContract(contract: ContractInfo) {
        try {
            loading.value = true
            errorContract.value = false

            await updateContractInfo(contract)
            await getResidents()
            await getContracts()
        } catch (e: unknown) {
            console.log(e)
            errorContract.value = true
        } finally {
            loading.value = false
        }
    }

    async function removeContract(contractId: number) {
        try {
            loading.value = true
            errorContract.value = false

            await removeContractInfo(contractId)
        } catch (e: unknown) {
            console.log(e)
            errorContract.value = true
            return null
        } finally {
            loading.value = false
        }
    }

    async function getBalance(id: number) {
        try {
            loading.value = true
            errorBalance.value = false

            return await getBalanceById(id) 
        } catch (e: unknown) {
            console.log(e)
            errorBalance.value = true
        } finally {
            loading.value = false
        }
    }

    async function createPayment(payment: Record<string, any>) {
        try {
            loading.value = true
            errorPayment.value = false

            const response = await createPaymentInfo(payment)
            return response
        } catch (e: unknown) {
            console.log(e)
            errorPayment.value = true
            return null
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
        balanceList,
        residentList,
        employeeList,
        contractList,

        loading,

        errorDormitoryType,
        errorDormitory,
        errorBlock,
        errorStatus,
        errorPayment,
        errorDebt: errorBalance,
        errorResident,
        errorEmployee,
        errorContract,

        getResidentByAccountId,

        residentHasContract,

        getDormitories,
        getAllBlocks,
        getBlocksByDormitory,
        getResidents,
        getEmployees,
        getContracts,
        getBalance,

        createContract,
        createPayment,

        updateResident,
        updateContract,

        removeContract,
    }
})