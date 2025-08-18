<template>
  <v-app>
    <v-app-bar>
      <v-btn icon @click="$router.back()">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>Perfil do Usuário</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <v-container>
        <v-card>
          <v-card-title>Meu Perfil</v-card-title>
          <v-card-text>
            <p>Página de perfil em desenvolvimento...</p>

            <v-btn to="/dashboard" color="primary" class="mt-4">Voltar ao Dashboard</v-btn>
          </v-card-text>
        </v-card>
      </v-container>
    </v-main>
  </v-app>
</template>
