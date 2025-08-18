<script setup lang="ts">
import { ref } from 'vue'

const loading = ref(false)

const testBackend = async () => {
  loading.value = true
  
  try {
    // TODO: implementar chamada real quando tiver axios
    await new Promise(resolve => setTimeout(resolve, 1000))
    console.log('Backend conectado!')
  } catch (error) {
    console.error('Erro na conexão:', error)
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card elevation="8" class="pa-4">
          <v-card-title class="text-center pb-4">
            <div>
              <h2 class="text-primary">RSL System</h2>
              <p class="text-subtitle-1 text-medium-emphasis">
                Sistema de Revisões Sistemáticas da Literatura
              </p>
            </div>
          </v-card-title>
          
          <v-card-text>
            <v-btn 
              color="primary" 
              block 
              size="large"
              @click="testBackend"
              :loading="loading"
            >
              Testar Conexão Backend
            </v-btn>
            
            <div class="text-center mt-4">
              <v-btn 
                to="/dashboard" 
                variant="outlined" 
                color="primary"
              >
                Ir para Dashboard (teste)
              </v-btn>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>