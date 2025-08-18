import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const LoginView = () => import('@/views/LoginView.vue')
const DashboardView = () => import('@/views/DashboardView.vue')
const ProfileView = () => import('@/views/ProfileView.vue')
const NotFoundView = () => import('@/views/NotFoundView.vue')

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: {
      requiresAuth: false,
      title: 'Login - RSL System'
    }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardView,
    meta: {
      requiresAuth: true,
      title: 'Dashboard - RSL System'
    }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: ProfileView,
    meta: {
      requiresAuth: true,
      title: 'Perfil - RSL System'
    }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFoundView,
    meta: {
      requiresAuth: false,
      title: '404 - Página não encontrada'
    }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// Navigation Guards (para JWT futuro)
router.beforeEach((to, from, next) => {
  // Definir título da página
  if (to.meta.title) {
    document.title = to.meta.title as string
  }

  // TODO: Implementar verificação JWT quando tiver auth
  // Temporariamente desabilitado para desenvolvimento
  /* 
  const requiresAuth = to.meta.requiresAuth
  const isAuthenticated = false

  if (requiresAuth && !isAuthenticated) {
    next('/login')
  } else if (!requiresAuth && isAuthenticated && to.path === '/login') {
    next('/dashboard')
  } else {
    next()
  }
  */

  // Permitir todas as navegações durante desenvolvimento
  next()
})

export default router
