import axios, { type AxiosResponse, type AxiosError } from 'axios'
import type { ResponseWrapper } from '@/types/api-types'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})

api.interceptors.request.use(
  (config) => {
    console.log(`[API] ${config.method?.toUpperCase()} ${config.baseURL}${config.url}`)
    
    // TODO: JWT quando implementar auth
    const token = localStorage.getItem('rsl_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    return config
  },
  (error: AxiosError) => {
    console.error('[API] Request Error:', error)
    return Promise.reject(new Error(error.message || 'Request failed'))
  }
)

api.interceptors.response.use(
  (response: AxiosResponse<ResponseWrapper<unknown>>) => {
    console.log(`[API] ${response.status} ${response.config.url}`)
    console.log('Response data:', response.data)
    return response
  },
  (error: AxiosError<ResponseWrapper<unknown>>) => {
    const status = error.response?.status
    const message = error.response?.data?.message || error.message
    const url = error.config?.url
    
    console.error(`[API] ${status || 'NETWORK'} ERROR on ${url}:`, message)
    
    // Determinar mensagem de erro baseada no status
    const getErrorMessage = (): string => {
      switch (status) {
        case 401:
          console.warn('Unauthorized - Token inválido')
          localStorage.removeItem('rsl_token')
          // TODO: Redirecionar para login
          return 'Token inválido ou expirado'
        case 403:
          console.warn('Forbidden - Sem permissão')
          return 'Acesso negado - Sem permissão'
        case 404:
          return 'Recurso não encontrado'
        case 500:
          console.error('Erro interno do servidor')
          return 'Erro interno do servidor'
        default:
          if (!status) {
            console.error('Erro de rede - Backend offline?')
            return 'Erro de rede - Verifique se o backend está rodando'
          }
          return message || 'Erro desconhecido'
      }
    }
    
    // Criar Error customizado com informações úteis
    const customError = new Error(getErrorMessage())
    Object.assign(customError, {
      status,
      originalMessage: message,
      url,
      isNetworkError: !status
    })
    
    return Promise.reject(customError)
  }
)

export default api