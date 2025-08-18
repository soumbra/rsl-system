import api from './http-client'
import type {
  ResponseWrapper,
  HealthData,
  ApiInfoData,
  TestData,
  ApiError
} from '@/types/api-types'

export const healthService = {
  // GET /api/health
  async checkHealth(): Promise<HealthData> {
    try {
      const response = await api.get<ResponseWrapper<HealthData>>('/health')
      return response.data.data
    } catch (error) {
      const apiError = error as ApiError
      throw new Error(`Falha no health check: ${apiError.message}`)
    }
  },

  // GET /api/info
  async getApiInfo(): Promise<ApiInfoData> {
    try {
      const response = await api.get<ResponseWrapper<ApiInfoData>>('/info')
      return response.data.data
    } catch (error) {
      const apiError = error as ApiError
      throw new Error(`Falha ao obter informações da API: ${apiError.message}`)
    }
  },

  // GET /api/test
  async testBasic(): Promise<TestData> {
    try {
      const response = await api.get<ResponseWrapper<TestData>>('/test')
      return response.data.data
    } catch (error) {
      const apiError = error as ApiError
      throw new Error(`Falha no teste básico: ${apiError.message}`)
    }
  },

  // GET /api/test/cors
  async testCors(): Promise<TestData> {
    try {
      const response = await api.get<ResponseWrapper<TestData>>('/test/cors')
      return response.data.data
    } catch (error) {
      const apiError = error as ApiError
      throw new Error(`Falha no teste CORS: ${apiError.message}`)
    }
  },

  // POST /api/test
  async testPost(payload: Record<string, unknown>): Promise<TestData> {
    try {
      const response = await api.post<ResponseWrapper<TestData>>('/test', payload)
      return response.data.data
    } catch (error) {
      const apiError = error as ApiError
      throw new Error(`Falha no teste POST: ${apiError.message}`)
    }
  }
}
