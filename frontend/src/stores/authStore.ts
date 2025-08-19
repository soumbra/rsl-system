import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type { User, AuthData } from '@/types/api-types'

export const useAuthStore = defineStore('auth', () => {
  // State
  const user = ref<User | null>(null)
  const token = ref<string | null>(localStorage.getItem('rsl_token'))
  const refreshToken = ref<string | null>(localStorage.getItem('rsl_refresh_token'))
  const isLoading = ref(false)

  // Getters
  const isAuthenticated = computed(() => !!token.value && !!user.value)
  const userRole = computed(() => user.value?.roles?.[0] || null)
  const userName = computed(() => user.value?.name || 'Usuário')
  const userEmail = computed(() => user.value?.email || '')

  // Actions
  function setAuth(authData: AuthData) {
    user.value = authData.user
    token.value = authData.token
    refreshToken.value = authData.refreshToken

    // Persist in localStorage
    localStorage.setItem('rsl_token', authData.token)
    localStorage.setItem('rsl_refresh_token', authData.refreshToken)
    localStorage.setItem('rsl_user', JSON.stringify(authData.user))

    console.log('[AuthStore] User authenticated:', authData.user.name)
  }

  function logout() {
    console.log('Logging out user:', user.value?.name)

    // Clear state
    user.value = null
    token.value = null
    refreshToken.value = null

    // Clear localStorage
    localStorage.removeItem('rsl_token')
    localStorage.removeItem('rsl_refresh_token')
    localStorage.removeItem('rsl_user')
  }

  function loadFromStorage() {
    const storedToken = localStorage.getItem('rsl_token')
    const storedUser = localStorage.getItem('rsl_user')

    if (storedToken && storedUser) {
      try {
        token.value = storedToken
        user.value = JSON.parse(storedUser)
        refreshToken.value = localStorage.getItem('rsl_refresh_token')
        console.log('[AuthStore] Loaded from storage:', user.value?.name)
      } catch (error) {
        console.error('[AuthStore] Error loading from storage:', error)
        logout() // Clear corrupted data
      }
    }
  }

  // Initialize from localStorage on store creation
  loadFromStorage()

  return {
    // State
    user,
    token,
    refreshToken,
    isLoading,

    // Getters
    isAuthenticated,
    userRole,
    userName,
    userEmail,

    // Actions
    setAuth,
    logout,
    loadFromStorage
  }
})
