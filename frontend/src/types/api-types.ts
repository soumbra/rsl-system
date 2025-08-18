export interface ResponseWrapper<T> {
  status: string
  message?: string
  timestamp: string
  data: T
}

export interface HealthData {
  service: string
  version: string
}

// api/info retorna
export interface ApiInfoData {
  name: string
  description: string
  java_version: string
  spring_boot: string
}

///api/test/cors 
export interface TestData {
  message: string
  frontend_can_access?: boolean
  cors_status?: string
  received_data?: Record<string, unknown>
}

export interface ApiError extends Error {
  status?: number
  originalMessage?: string
  url?: string
  isNetworkError?: boolean
}

// Tipos para o store/state management futuro
export interface User {
  id: number
  name: string
  email: string
  roles: string[]
}

export interface AuthData {
  token: string
  refreshToken: string
  user: User
}