import { $fetch } from '@/plugins/fetch'

export async function postRawRequest(payload: string) {
    return $fetch('/api/v1/sql/execute', {
      method: 'post',
      body: payload,
    })
}
  
export async function getTables() {
  return $fetch('/api/v1/sql/tables')
}
  
export async function getForeignKeys() {
  return $fetch('/api/v1/sql/foreign-keys')
}

export async function postLogOn() {
  return $fetch('/api/v1/sql/log/on', {
    method: 'post'
  })
}

export async function postLogOff() {
  return $fetch('/api/v1/sql/log/off', {
    method: 'post'
  })
}
  