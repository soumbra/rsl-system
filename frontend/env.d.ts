/// <reference types="vite/client" />

// Declaração de módulo para arquivos .vue
declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<Record<string, unknown>, Record<string, unknown>, unknown>
  export default component
}
