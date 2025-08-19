import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { healthService } from '@/services/healthService'
import type { HealthData, ApiInfoData } from '@/types/api-types'

export const useBackendStore = defineStore('backend', () => {
  // State
  const isConnected = ref(false)
  const health = ref<HealthData | null>(null)
  const info = ref<ApiInfoData | null>(null)
  const lastCheck = ref<Date | null>(null)
  const error = ref<string | null>(null)
  const isChecking = ref(false)

  // Getters
  const connectionStatus = computed(() => {
    if (isChecking.value) return 'checking'
    if (isConnected.value) return 'connected'
    if (error.value) return 'error'
    return 'unknown'
  })

  const backendVersion = computed(() => health.value?.version || 'Unknown')
  const backendService = computed(
    () => health.value?.service || info.value?.name || 'RSL System API'
  )
  const lastCheckFormatted = computed(() => {
    if (!lastCheck.value) return 'Nunca'
    return lastCheck.value.toLocaleTimeString('pt-BR')
  })

  // Actions
  async function checkConnection(): Promise<boolean> {
    if (isChecking.value) return isConnected.value

    isChecking.value = true
    error.value = null

    try {
      console.log('🔄 [BackendStore] Checking backend connection...')

      // Test health endpoint
      const healthData = await healthService.checkHealth()
      health.value = healthData

      // Test info endpoint
      const infoData = await healthService.getApiInfo()
      info.value = infoData

      // Test CORS
      await healthService.testCors()

      isConnected.value = true
      lastCheck.value = new Date()

      console.log('✅ [BackendStore] Backend connected successfully')
      return true
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : 'Erro de conexão'
      error.value = errorMsg
      isConnected.value = false
      lastCheck.value = new Date()

      console.error('[BackendStore] Backend connection failed:', errorMsg)
      return false
    } finally {
      isChecking.value = false
    }
  }

  function reset() {
    isConnected.value = false
    health.value = null
    info.value = null
    error.value = null
    lastCheck.value = null
  }

  return {
    // State
    isConnected,
    health,
    info,
    lastCheck,
    error,
    isChecking,

    // Getters
    connectionStatus,
    backendVersion,
    backendService,
    lastCheckFormatted,

    // Actions
    checkConnection,
    reset
  }
})
