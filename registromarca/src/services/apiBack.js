import axios from 'axios';


// Obtener la URL base
const BASE_URL = import.meta.env.VITE_API_URL_Revueta_Back;
const TIME_RESPONSE = Number(import.meta.env.VITE_TIMEOUT_PETICION) || 1000;

console.log("Mi URL de Backend es:", BASE_URL);

// Crear la instancia de Axios
const apiServer = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: TIME_RESPONSE, // Tiempo límite de espera (10 segundos)
});

/* 
==========================================================================
                 INTERCEPTORES (Opcional pero muy útil)
========================================================================== 
*/

// 1. Interceptor de Peticiones: Añade tokens JWT automáticamente si existen
apiServer.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// 2. Interceptor de Respuestas: Captura errores globales (ej: 401 Sesión expirada)
apiServer.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Ejemplo: Redirigir al login o limpiar localStorage si el token expiró
      localStorage.removeItem('token');
    }
    return Promise.reject(error);
  }
);

export default apiServer;