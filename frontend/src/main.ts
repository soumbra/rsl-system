import './assets/base.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi'

import App from './App.vue'
import router from './router'
import { rslTheme, rslDisplay, rslDefaults } from './config/theme'

const vuetify = createVuetify({
  components,
  directives,

  theme: {
    defaultTheme: 'light',
    themes: {
      light: rslTheme.light,
      dark: rslTheme.dark
    },
    variations: {
      colors: ['primary', 'secondary', 'rsl-primary', 'rsl-secondary'],
      lighten: 2,
      darken: 2
    }
  },

  defaults: rslDefaults,

  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: { mdi }
  },

  display: rslDisplay
})

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(vuetify)

app.mount('#app')
