import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', () => {
  // State
  const appName = ref('RSL System')
  const appVersion = ref('0.1.0')
  const theme = ref<'light' | 'dark'>('light')
  const isLoading = ref(false)
  const notifications = ref<
    Array<{
      id: string
      type: 'success' | 'error' | 'warning' | 'info'
      title: string
      message: string
      timestamp: Date
    }>
  >([])

  // Getters
  const isDarkTheme = computed(() => theme.value === 'dark')
  const notificationCount = computed(() => notifications.value.length)

  const recentNotifications = computed(() =>
    notifications.value
      .slice()
      .sort((a, b) => b.timestamp.getTime() - a.timestamp.getTime())
      .slice(0, 5)
  )

  // Actions
  function toggleTheme() {
    theme.value = theme.value === 'light' ? 'dark' : 'light'
    localStorage.setItem('rsl_theme', theme.value)
    console.log(`[AppStore] Theme changed to: ${theme.value}`)
  }

  function setLoading(loading: boolean) {
    isLoading.value = loading
  }

  function addNotification(
    type: 'success' | 'error' | 'warning' | 'info',
    title: string,
    message: string
  ) {
    const notification = {
      id: Date.now().toString(),
      type,
      title,
      message,
      timestamp: new Date()
    }

    notifications.value.push(notification)

    // Auto remove after 5 seconds for success/info
    if (type === 'success' || type === 'info') {
      setTimeout(() => {
        removeNotification(notification.id)
      }, 5000)
    }

    console.log(`[AppStore] Notification added: ${type} - ${title}`)
  }

  function removeNotification(id: string) {
    const index = notifications.value.findIndex((n) => n.id === id)
    if (index > -1) {
      notifications.value.splice(index, 1)
    }
  }

  function clearNotifications() {
    notifications.value = []
  }

  // Load theme from localStorage
  function loadTheme() {
    const savedTheme = localStorage.getItem('rsl_theme') as 'light' | 'dark'
    if (savedTheme) {
      theme.value = savedTheme
    }
  }

  // Initialize theme on store creation
  loadTheme()

  return {
    // State
    appName,
    appVersion,
    theme,
    isLoading,
    notifications,

    // Getters
    isDarkTheme,
    notificationCount,
    recentNotifications,

    // Actions
    toggleTheme,
    setLoading,
    addNotification,
    removeNotification,
    clearNotifications,
    loadTheme
  }
})
