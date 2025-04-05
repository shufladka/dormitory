import { $fetch } from '@/plugins/fetch'

export async function login(payload: any) {
    return $fetch('/api/v1/accounts/sign-in', {
      method: 'post',
      body: payload,
    })
  }

export async function getAccountsList() {
  return $fetch('/api/v1/accounts')
}