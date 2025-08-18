<script setup lang="ts">
  import { ref, reactive } from 'vue'
  import { healthService } from '@/services/healthService'
  import type { HealthData, ApiInfoData, TestData, ApiError } from '@/types/api-types'

  const loading = ref(false)

  interface BackendStatus {
    isConnected: boolean
    health: HealthData | null
    info: ApiInfoData | null
    corsTest: TestData | null
    error: string | null
  }

  const backendStatus = reactive<BackendStatus>({
    isConnected: false,
    health: null,
    info: null,
    corsTest: null,
    error: null
  })

  const testBackend = async () => {
    loading.value = true
    backendStatus.error = null

    try {
      console.log('🔄 Iniciando teste de conexão com backend RSL System...')

      // 1. Teste básico de saúde
      console.log('📍 Testando /api/health...')
      const health = await healthService.checkHealth()
      backendStatus.health = health
      console.log('✅ Health OK:', health)

      // 2. Informações da API
      console.log('📍 Testando /api/info...')
      const info = await healthService.getApiInfo()
      backendStatus.info = info
      console.log('✅ Info OK:', info)

      // 3. Teste de CORS
      console.log('📍 Testando /api/test/cors...')
      const corsTest = await healthService.testCors()
      backendStatus.corsTest = corsTest
      console.log('✅ CORS OK:', corsTest)

      // 4. Teste POST
      console.log('📍 Testando POST /api/test...')
      await healthService.testPost({
        test: 'frontend-connection',
        timestamp: new Date().toISOString()
      })
      console.log('✅ POST OK')

      backendStatus.isConnected = true
      console.log('🎉 TODOS OS TESTES PASSARAM! Backend RSL System conectado.')
    } catch (error) {
      const apiError = error as ApiError
      backendStatus.isConnected = false
      backendStatus.error = apiError.message || 'Erro de conexão'
      console.error('Falha na conexão:', apiError)
    } finally {
      loading.value = false
    }
  }
</script>

<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-card elevation="8" class="pa-6">
          <v-card-title class="text-center pb-4">
            <div>
              <h2 class="text-primary">RSL System</h2>
              <p class="text-subtitle-1 text-medium-emphasis">
                Sistema de Revisões Sistemáticas da Literatura
              </p>
            </div>
          </v-card-title>

          <v-card-text>
            <!-- Status da conexão -->
            <v-alert v-if="backendStatus.isConnected" type="success" class="mb-4" variant="tonal">
              <strong>✅ Backend Conectado!</strong>
              <div class="text-caption mt-2">
                <div>
                  <strong>Serviço:</strong>
                  {{ backendStatus.health?.service }}
                </div>
                <div>
                  <strong>Versão:</strong>
                  {{ backendStatus.health?.version }}
                </div>
                <div>
                  <strong>Nome:</strong>
                  {{ backendStatus.info?.name }}
                </div>
                <div>
                  <strong>Java:</strong>
                  {{ backendStatus.info?.java_version }}
                </div>
                <div>
                  <strong>Spring:</strong>
                  {{ backendStatus.info?.spring_boot }}
                </div>
                <div>
                  <strong>CORS:</strong>
                  {{ backendStatus.corsTest?.frontend_can_access ? 'OK' : 'ERRO' }}
                </div>
              </div>
            </v-alert>

            <v-alert v-else-if="backendStatus.error" type="error" class="mb-4" variant="tonal">
              <strong>Erro de Conexão</strong>
              <div class="text-caption mt-1">{{ backendStatus.error }}</div>
              <div class="text-caption">Verifique se o backend está rodando em localhost:8080</div>
            </v-alert>

            <v-btn
              color="primary"
              block
              size="large"
              class="mb-3"
              @click="testBackend"
              :loading="loading"
            >
              <v-icon start>mdi-wifi</v-icon>
              {{ backendStatus.isConnected ? 'Testar Novamente' : 'Testar Conexão Backend' }}
            </v-btn>

            <v-btn
              to="/dashboard"
              variant="outlined"
              color="primary"
              block
              size="large"
              :disabled="!backendStatus.isConnected"
            >
              <v-icon start>mdi-view-dashboard</v-icon>
              Ir para Dashboard
            </v-btn>

            <v-expansion-panels v-if="backendStatus.info" class="mt-4">
              <v-expansion-panel>
                <v-expansion-panel-title>
                  <v-icon start>mdi-information-outline</v-icon>
                  Informações do Backend
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <div class="text-caption">
                    <strong>Health Service:</strong>
                    {{ backendStatus.health?.service }}
                    <br />
                    <strong>Health Version:</strong>
                    {{ backendStatus.health?.version }}
                    <br />
                    <strong>Info Name:</strong>
                    {{ backendStatus.info.name }}
                    <br />
                    <strong>Descrição:</strong>
                    {{ backendStatus.info.description }}
                    <br />
                    <strong>Java:</strong>
                    {{ backendStatus.info.java_version }}
                    <br />
                    <strong>Spring Boot:</strong>
                    {{ backendStatus.info.spring_boot }}
                    <br />
                    <strong>URL:</strong>
                    http://localhost:8080/api
                    <br />
                    <strong>CORS Test:</strong>
                    {{ backendStatus.corsTest?.message }}
                    <br />
                    <strong>Frontend Access:</strong>
                    {{ backendStatus.corsTest?.frontend_can_access ? 'Sim' : 'Não' }}
                  </div>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
