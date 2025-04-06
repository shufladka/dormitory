import { $fetch } from '@/plugins/fetch'

export async function getPassportInfo(id: number) {
  return $fetch(`/api/v1/passports/account/${id}`)
}

export async function getPassportList() {
  return $fetch('/api/v1/passports')
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

export async function getContactInfo(id: number) {
  return $fetch(`/api/v1/contacts/passport/${id}`)
}

export async function createContact(payload: any) {
  return $fetch('/api/v1/contacts', {
    method: 'post',
    body: payload,
  })
}

export async function updateContact(payload: any) {
  return $fetch('/api/v1/contacts', {
    method: 'put',
    body: payload,
  })
}

export async function getRoles() {
  return $fetch('/api/v1/roles')
}

export async function createRole(payload: any) {
  return $fetch('/api/v1/roles', {
    method: 'post',
    body: payload,
  })
}

export async function updateRole(payload: any) {
  return $fetch('/api/v1/contacts', {
    method: 'put',
    body: payload,
  })
}