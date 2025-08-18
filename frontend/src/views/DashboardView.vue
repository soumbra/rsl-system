<script setup lang="ts">
  import { ref } from 'vue'

  const drawer = ref(false)
</script>

<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" temporary>
      <v-list>
        <v-list-item to="/dashboard" title="Dashboard" prepend-icon="mdi-view-dashboard" />
        <v-list-item to="/reviews" title="Revisões" prepend-icon="mdi-book-open-variant" />
        <v-list-item to="/profile" title="Perfil" prepend-icon="mdi-account" />
      </v-list>
    </v-navigation-drawer>

    <v-app-bar>
      <v-app-bar-nav-icon @click="drawer = !drawer" />
      <v-toolbar-title>RSL System - Dashboard</v-toolbar-title>
      <v-spacer />
      <v-btn to="/login" variant="outlined" color="error">Sair</v-btn>
    </v-app-bar>

    <v-main>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-card>
              <v-card-title>
                <h2>Bem-vindo ao RSL System!</h2>
              </v-card-title>
              <v-card-text>
                <p>Sistema de Revisões Sistemáticas da Literatura</p>

                <v-row class="mt-4">
                  <v-col cols="12" md="4">
                    <v-card color="primary" variant="tonal">
                      <v-card-title>Revisões</v-card-title>
                      <v-card-text>
                        <div class="text-h4">0</div>
                        <p>Revisões ativas</p>
                      </v-card-text>
                    </v-card>
                  </v-col>

                  <v-col cols="12" md="4">
                    <v-card color="success" variant="tonal">
                      <v-card-title>Artigos</v-card-title>
                      <v-card-text>
                        <div class="text-h4">0</div>
                        <p>Artigos analisados</p>
                      </v-card-text>
                    </v-card>
                  </v-col>

                  <v-col cols="12" md="4">
                    <v-card color="warning" variant="tonal">
                      <v-card-title>Pendências</v-card-title>
                      <v-card-text>
                        <div class="text-h4">0</div>
                        <p>Análises pendentes</p>
                      </v-card-text>
                    </v-card>
                  </v-col>
                </v-row>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>
