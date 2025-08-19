// Paleta de cores RSL System
export const rslTheme = {
  light: {
    colors: {
      // Cores primárias do RSL System
      primary: '#1976D2', // Azul acadêmico
      secondary: '#424242', // Cinza neutro
      accent: '#82B1FF', // Azul claro
      error: '#FF5252', // Vermelho erro
      info: '#2196F3', // Azul informativo
      success: '#4CAF50', // Verde sucesso
      warning: '#FFC107', // Amarelo atenção

      // Cores de fundo
      background: '#FAFAFA', // Fundo claro
      surface: '#FFFFFF', // Superfície cards

      // Cores específicas RSL
      'rsl-primary': '#0D47A1', // Azul escuro acadêmico
      'rsl-secondary': '#1565C0', // Azul médio
      'rsl-accent': '#64B5F6', // Azul claro highlight

      // Estados de revisão
      'review-pending': '#FF9800', // Laranja - pendente
      'review-approved': '#4CAF50', // Verde - aprovado
      'review-rejected': '#F44336', // Vermelho - rejeitado
      'review-conflict': '#9C27B0' // Roxo - conflito
    }
  },
  dark: {
    colors: {
      primary: '#2196F3',
      secondary: '#616161',
      accent: '#82B1FF',
      error: '#FF5252',
      info: '#2196F3',
      success: '#4CAF50',
      warning: '#FFC107',

      background: '#121212',
      surface: '#1E1E1E',

      'rsl-primary': '#1976D2',
      'rsl-secondary': '#42A5F5',
      'rsl-accent': '#90CAF9',

      'review-pending': '#FFB74D',
      'review-approved': '#66BB6A',
      'review-rejected': '#EF5350',
      'review-conflict': '#BA68C8'
    }
  }
}

// Tipografia customizada
export const rslTypography = {
  fontFamily: '"Inter", "Roboto", "Helvetica Neue", Arial, sans-serif'
}

// Breakpoints responsivos
export const rslDisplay = {
  thresholds: {
    xs: 0,
    sm: 600,
    md: 960,
    lg: 1280,
    xl: 1920
  }
}

// Defaults para componentes
export const rslDefaults = {
  VCard: {
    elevation: 2,
    rounded: 'lg'
  },
  VBtn: {
    rounded: 'lg',
    style: 'text-transform: none; font-weight: 500;'
  },
  VTextField: {
    variant: 'outlined' as const,
    density: 'comfortable' as const
  },
  VAlert: {
    rounded: 'lg',
    variant: 'tonal' as const
  },
  VChip: {
    rounded: 'lg'
  }
}
