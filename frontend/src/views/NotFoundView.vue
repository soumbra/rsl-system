<!-- src/views/NotFoundView.vue -->
<template>
  <v-app>
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="6" class="text-center">
            <div>
              <h1 class="display-3 font-weight-bold text-primary mb-4">404</h1>
              <h2 class="headline mb-4">Página não encontrada</h2>
              <p class="text-subtitle-1 mb-4">A página que você está procurando não existe.</p>
              <v-btn to="/" color="primary" size="large">Voltar ao Início</v-btn>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>
