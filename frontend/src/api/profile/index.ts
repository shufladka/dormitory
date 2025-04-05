import { $fetch } from '@/plugins/fetch'

export async function login(payload: any) {
    return $fetch('/api/v1/accounts/sign-in', {
      method: 'post',
      body: payload,
    })
}

export async function registration(payload: any) {
    return $fetch('/api/v1/accounts', {
      method: 'post',
      body: payload,
    })
}

export async function getPassportInfo(id: number) {
  return $fetch(`/api/v1/passports/account/${id}`)
}

export async function createPassport(payload: any) {
  return $fetch('/api/v1/passports', {
    method: 'post',
    body: payload,
  })
}

export async function updatePassport(payload: any) {
  return $fetch('/api/v1/passports', {
    method: 'put',
    body: payload,
  })
}

export async function getAddressInfo(id: number) {
  return $fetch(`/api/v1/addresses/passport/${id}`)
}

export async function createAddress(payload: any) {
  return $fetch('/api/v1/addresses', {
    method: 'post',
    body: payload,
  })
}

export async function updateAddress(payload: any) {
  return $fetch('/api/v1/addresses', {
    method: 'put',
    body: payload,
  })
}

export async function getAccountsList() {
  return $fetch('/api/v1/accounts')
}