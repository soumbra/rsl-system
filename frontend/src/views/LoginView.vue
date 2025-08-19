<script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { useBackendStore } from '@/stores/backendStore'
  import { useAppStore } from '@/stores/appStores'
  import { useAuthStore } from '@/stores/authStore'

  const router = useRouter()

  const backendStore = useBackendStore() // ← Conectividade backend
  const appStore = useAppStore() // ← UI global (theme, notifications)
  const authStore = useAuthStore() // ← Autenticação (futuro)

  const loading = ref(false)

  const testBackend = async () => {
    loading.value = true

    try {
      // ✅ Backend store gerencia conectividade
      const success = await backendStore.checkConnection()

      if (success) {
        // ✅ App store gerencia notificações
        appStore.addNotification(
          'success',
          'Backend Conectado!',
          `${backendStore.backendService} está funcionando perfeitamente.`
        )
      } else {
        appStore.addNotification(
          'error',
          'Erro de Conexão',
          backendStore.error || 'Não foi possível conectar ao backend.'
        )
      }
    } catch (error) {
      appStore.addNotification('error', 'Erro Inesperado', 'Ocorreu um erro ao testar a conexão.')
    } finally {
      loading.value = false
    }
  }

  const goToDashboard = () => {
    if (backendStore.isConnected) {
      router.push('/dashboard')
    }
  }

  // ✅ Futuro: função de login usando authStore
  const simulateLogin = async () => {
    try {
      appStore.setLoading(true)

      // TODO: Chamar API de login quando implementar JWT
      // const authData = await authService.login(credentials)
      // authStore.setAuth(authData)

      // Simular login por enquanto
      await new Promise((resolve) => setTimeout(resolve, 1000))
      appStore.addNotification('success', 'Login simulado!', 'Bem-vindo ao RSL System')
      router.push('/dashboard')
    } catch (error) {
      appStore.addNotification('error', 'Erro no login', 'Credenciais inválidas')
    } finally {
      appStore.setLoading(false)
    }
  }
</script>

<template>
  <v-app :theme="appStore.theme">
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="6" lg="4">
            <v-card elevation="8" class="pa-6">
              <v-card-title class="text-center pb-4">
                <div>
                  <!-- ✅ App name vem do appStore -->
                  <h2 class="text-primary">{{ appStore.appName }}</h2>
                  <p class="text-subtitle-1 text-medium-emphasis">
                    Sistema de Revisões Sistemáticas da Literatura
                  </p>
                  <div class="text-caption text-medium-emphasis">v{{ appStore.appVersion }}</div>
                </div>
              </v-card-title>

              <v-card-text>
                <v-alert
                  v-if="backendStore.isConnected"
                  type="success"
                  class="mb-4"
                  variant="tonal"
                >
                  <strong>✅ Backend Conectado!</strong>
                  <div class="text-caption mt-2">
                    <div>
                      <strong>Serviço:</strong>
                      {{ backendStore.backendService }}
                    </div>
                    <div>
                      <strong>Versão:</strong>
                      {{ backendStore.backendVersion }}
                    </div>
                    <div>
                      <strong>Status:</strong>
                      {{ backendStore.connectionStatus }}
                    </div>
                    <div>
                      <strong>Última verificação:</strong>
                      {{ backendStore.lastCheckFormatted }}
                    </div>
                  </div>
                </v-alert>

                <v-alert v-else-if="backendStore.error" type="error" class="mb-4" variant="tonal">
                  <strong>Erro de Conexão</strong>
                  <div class="text-caption mt-1">{{ backendStore.error }}</div>
                  <div class="text-caption">
                    Verifique se o backend está rodando em localhost:8080
                  </div>
                </v-alert>

                <v-alert
                  v-else-if="backendStore.connectionStatus === 'checking'"
                  type="info"
                  class="mb-4"
                  variant="tonal"
                >
                  <strong>🔄 Verificando Conexão...</strong>
                  <div class="text-caption">Testando conectividade com o backend...</div>
                </v-alert>
                <v-btn
                  color="primary"
                  block
                  size="large"
                  class="mb-3"
                  @click="testBackend"
                  :loading="loading || backendStore.isChecking"
                  :disabled="appStore.isLoading"
                >
                  <v-icon start>mdi-wifi</v-icon>
                  {{ backendStore.isConnected ? 'Testar Novamente' : 'Testar Conexão Backend' }}
                </v-btn>
                <v-btn
                  @click="goToDashboard"
                  variant="outlined"
                  color="primary"
                  block
                  size="large"
                  class="mb-3"
                  :disabled="!backendStore.isConnected || appStore.isLoading"
                >
                  <v-icon start>mdi-view-dashboard</v-icon>
                  Ir para Dashboard
                </v-btn>
                <v-btn
                  @click="simulateLogin"
                  variant="text"
                  color="secondary"
                  block
                  size="large"
                  class="mb-3"
                  :loading="appStore.isLoading"
                  :disabled="!backendStore.isConnected"
                >
                  <v-icon start>mdi-login</v-icon>
                  Login Simulado (JWT futuro)
                </v-btn>
                <v-divider class="my-4" />
                <div class="d-flex justify-space-between">
                  <v-btn @click="appStore.toggleTheme" variant="text" size="small">
                    <v-icon start>
                      {{ appStore.isDarkTheme ? 'mdi-weather-night' : 'mdi-weather-sunny' }}
                    </v-icon>
                    {{ appStore.isDarkTheme ? 'Claro' : 'Escuro' }}
                  </v-btn>
                  <v-chip v-if="appStore.notificationCount > 0" color="primary" size="small">
                    <v-icon start>mdi-bell</v-icon>
                    {{ appStore.notificationCount }} notificações
                  </v-chip>
                </div>

                <!-- Informações técnicas -->
                <v-expansion-panels v-if="backendStore.info || backendStore.health" class="mt-4">
                  <v-expansion-panel>
                    <v-expansion-panel-title>
                      <v-icon start>mdi-information-outline</v-icon>
                      Informações Técnicas
                    </v-expansion-panel-title>
                    <v-expansion-panel-text>
                      <div class="text-caption">
                        <!-- Backend Info -->
                        <div v-if="backendStore.health" class="mb-2">
                          <strong>Health Endpoint:</strong>
                          <br />
                          - Serviço: {{ backendStore.health.service }}
                          <br />
                          - Versão: {{ backendStore.health.version }}
                          <br />
                        </div>

                        <div v-if="backendStore.info" class="mb-2">
                          <strong>Info Endpoint:</strong>
                          <br />
                          - Nome: {{ backendStore.info.name }}
                          <br />
                          - Descrição: {{ backendStore.info.description }}
                          <br />
                          - Java: {{ backendStore.info.java_version }}
                          <br />
                          - Spring Boot: {{ backendStore.info.spring_boot }}
                          <br />
                        </div>
                        <div class="mb-2">
                          <strong>Frontend:</strong>
                          <br />
                          - App: {{ appStore.appName }}
                          <br />
                          - Versão: {{ appStore.appVersion }}
                          <br />
                          - Tema: {{ appStore.theme }}
                          <br />
                          - Loading: {{ appStore.isLoading ? 'Sim' : 'Não' }}
                          <br />
                        </div>
                        <div>
                          <strong>Autenticação:</strong>
                          <br />
                          - Logado: {{ authStore.isAuthenticated ? 'Sim' : 'Não' }}
                          <br />
                          - Token: {{ authStore.token ? 'Presente' : 'Ausente' }}
                          <br />
                          - Usuário: {{ authStore.user?.name || 'N/A' }}
                          <br />
                        </div>
                        <div class="mt-2">
                          <strong>Conexão:</strong>
                          <br />
                          - URL: http://localhost:8080/api
                          <br />
                          - Status: {{ backendStore.connectionStatus }}
                          <br />
                          - Última verificação: {{ backendStore.lastCheckFormatted }}
                          <br />
                        </div>
                      </div>
                    </v-expansion-panel-text>
                  </v-expansion-panel>
                </v-expansion-panels>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
    <div class="notification-container">
      <v-snackbar
        v-for="notification in appStore.recentNotifications"
        :key="notification.id"
        :model-value="true"
        :color="notification.type"
        :timeout="notification.type === 'error' ? 10000 : 4000"
        location="top right"
        class="mb-2"
      >
        <div>
          <strong>{{ notification.title }}</strong>
          <br />
          <span class="text-caption">{{ notification.message }}</span>
        </div>
        <template #actions>
          <v-btn
            icon="mdi-close"
            size="small"
            @click="appStore.removeNotification(notification.id)"
          />
        </template>
      </v-snackbar>
    </div>
  </v-app>
</template>

<style scoped>
  .notification-container {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 9999;
    pointer-events: none;
  }

  .notification-container .v-snackbar {
    pointer-events: all;
  }
</style>
