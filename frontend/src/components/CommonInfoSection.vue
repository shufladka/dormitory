<script lang="ts" setup>
import RootContainer from '@/components/RootContainer.vue'
import ProfileSectionWrapper from '@/components/ProfileSectionWrapper.vue'
import { RoleInfo, useProfileStore } from '@/store/profileStore'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import Checkbox from '@/components/Checkbox.vue'
import { onMounted, ref } from 'vue'
import { useAuthStore } from '@/store/authStore'

const router = useRouter()

const selectedOptions = ref<RoleInfo[]>([])

const auth = useAuthStore()

const profile = useProfileStore()
const { passport, roleList, errorRole, loading, userCredentials } = storeToRefs(profile)

async function cancelHandle() {
  await profile.getRoleList()
  selectedOptions.value = userCredentials.value.roles
}

async function roleHandle() {
  let roles = ref<number[]>([])

  roleList.value.forEach((role: RoleInfo) => {
    selectedOptions.value.forEach((selected: any) => {
      if (selected === role.name) {
        console.log(role)
        roles.value.push(role.id)
      }
    })
  })

  await auth.updateAccount(roles.value)
}

onMounted(() => {
  profile.loadUserData()
  selectedOptions.value = userCredentials.value.roles
})
</script>

<template>
  <ProfileSectionWrapper
    title="Информация о пользователе"
    @cancel="cancelHandle"
    @save="roleHandle"
    :loading="loading"
    :error="errorRole"
  >
    <div>
      <h2 class="text-base/7 font-semibold text-gray-900">Список доступных ролей</h2>
      <p class="mt-1 text-sm/6 text-gray-600">Отмеченные роли уже присвоены этому пользователю</p>

      <div class="space-y-10">
        <fieldset>
          <!-- <legend class="text-sm/6 font-semibold text-gray-900">By email</legend> -->
          <div class="mt-6 space-y-6">
            <Checkbox
              v-for="(roleItem, roleIndex) in roleList"
              :key="roleIndex"
              v-model="selectedOptions"
              :value="roleItem.name"
              :label="roleItem.name"
              :name="roleItem.name"
            />
          </div>
        </fieldset>
      </div>
    </div>
  </ProfileSectionWrapper>
</template>

<style scoped>
body,
html {
  height: 100%;
}
</style>
