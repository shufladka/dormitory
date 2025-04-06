import { $fetch } from '@/plugins/fetch'

export async function getDormitoryList() {
  return $fetch('/api/v1/dormitories')
}

export async function createDormitoryInfo(payload: any) {
  return $fetch('/api/v1/blocks', {
    method: 'post',
    body: payload,
  })
}

export async function updateDormitoryInfo(payload: any) {
  return $fetch('/api/v1/dormitories', {
    method: 'put',
    body: payload,
  })
}

export async function getContractList() {
  return $fetch('/api/v1/contracts')
}

export async function createContractInfo(payload: any) {
  return $fetch('/api/v1/contracts', {
    method: 'post',
    body: payload,
  })
}

export async function updateContractInfo(payload: any) {
  return $fetch('/api/v1/contracts', {
    method: 'put',
    body: payload,
  })
}

export async function getBlockDormitoryList(id: number) {
  return $fetch(`/api/v1/blocks/dormitory/${id}`)
}

export async function getBlockList() {
  return $fetch('/api/v1/blocks')
}

export async function createBlockInfo(payload: any) {
  return $fetch('/api/v1/blocks', {
    method: 'post',
    body: payload,
  })
}

export async function updateBlockInfo(payload: any) {
  return $fetch('/api/v1/blocks', {
    method: 'put',
    body: payload,
  })
}

export async function getStatusList() {
  return $fetch('/api/v1/statuses')
}

export async function createStatusInfo(payload: any) {
  return $fetch('/api/v1/statuses', {
    method: 'post',
    body: payload,
  })
}

export async function updateStatusInfo(payload: any) {
  return $fetch('/api/v1/statuses', {
    method: 'put',
    body: payload,
  })
}

export async function getPaymentList() {
  return $fetch('/api/v1/payments')
}

export async function createPaymentInfo(payload: any) {
  return $fetch('/api/v1/payments', {
    method: 'post',
    body: payload,
  })
}

export async function updatePaymentInfo(payload: any) {
  return $fetch('/api/v1/payments', {
    method: 'put',
    body: payload,
  })
}

export async function getViolationList() {
  return $fetch('/api/v1/violations')
}

export async function createViolationInfo(payload: any) {
  return $fetch('/api/v1/violations', {
    method: 'post',
    body: payload,
  })
}

export async function updateViolationInfo(payload: any) {
  return $fetch('/api/v1/violations', {
    method: 'put',
    body: payload,
  })
}

export async function getDebtList() {
  return $fetch('/api/v1/debts')
}

export async function createDebtInfo(payload: any) {
  return $fetch('/api/v1/debts', {
    method: 'post',
    body: payload,
  })
}

export async function updateDebtInfo(payload: any) {
  return $fetch('/api/v1/debts', {
    method: 'put',
    body: payload,
  })
}

export async function getResidentList() {
  return $fetch('/api/v1/residents')
}

export async function updateResidentInfo(payload: any) {
  return $fetch('/api/v1/residents', {
    method: 'put',
    body: payload,
  })
}

export async function getEmployeeList() {
  return $fetch('/api/v1/employees')
}

export async function updateEmployeeInfo(payload: any) {
  return $fetch('/api/v1/employees', {
    method: 'put',
    body: payload,
  })
}
