<div align="center">

# ⚛️ Frontend - Club de Fidelización & Recompensas Multi-Marca

> **Aplicación cliente SPA (Single Page Application) desarrollada con React 19, Vite y Tailwind CSS 4 para la interacción, consulta y registro de clientes en marcas comerciales asociadas.**

[![React](https://img.shields.io/badge/React-19-blue?style=for-the-badge&logo=react&logoColor=white)](https://react.dev/)
[![Vite](https://img.shields.io/badge/Vite-6-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)
[![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-4-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![Axios](https://img.shields.io/badge/Axios-1.x-5A29E4?style=for-the-badge&logo=axios&logoColor=white)](https://axios-http.com/)
[![Lucide](https://img.shields.io/badge/Lucide_Icons-1.x-F05032?style=for-the-badge&logo=lucide&logoColor=white)](https://lucide.dev/)

---

### 🧭 Navegación entre Módulos
**[⬅️ Volver al README Principal](../README.md)** &nbsp;|&nbsp; **[☕ Ver Documentación del Backend (Spring Boot)](../Registro_Marca/README.md)**

---

</div>

## 📑 Tabla de Contenidos

- [📌 Descripción del Frontend](#-descripción-del-frontend)
- [🚀 Tecnologías Principales](#-tecnologías-principales)
- [✨ Características y Funcionalidades](#-características-y-funcionalidades)
- [📁 Estructura del Proyecto](#-estructura-del-proyecto)
- [🔌 Integración con el Backend y Servicios](#-integración-con-el-backend-y-servicios)
- [⚙️ Variables de Entorno](#️-variables-de-entorno)
- [🛠️ Instalación y Ejecución Local](#️-instalación-y-ejecución-local)
- [👥 Marcas Aliadas Oficiales](#-marcas-aliadas-oficiales)
- [👨‍💻 Autor](#-autor)

---

## 📌 Descripción del Frontend

Esta aplicación constituye la interfaz de usuario de la plataforma **Club de Fidelización & Recompensas Multi-Marca**. Permite a los clientes conocer las marcas aliadas (*American Eagle, Americanino, Chevignon, Esprit, NAF NAF, Rifle*), explorar las recompensas por compras y afiliarse al programa mediante un formulario reactivo con validaciones avanzadas en tiempo real y selectores geográficos en cascada.

La aplicación consume de manera directa y desacoplada la **API REST del Backend (Spring Boot)** ubicada en `http://localhost:8080/`.

---

## 🚀 Tecnologías Principales

| Tecnología | Versión | Propósito |
| :--- | :--- | :--- |
| **[React](https://react.dev/)** | `^19.2.8` | Biblioteca base para interfaces de usuario reactivas y modulares |
| **[Vite](https://vitejs.dev/)** | `^6.x / ^8.x` | Servidor de desarrollo con Hot Module Replacement (HMR) ultra rápido |
| **[Tailwind CSS](https://tailwindcss.com/)** | `^4.3.3` | Framework de diseño utilitario integrado vía `@tailwindcss/vite` |
| **[React Router DOM](https://reactrouter.com/)** | `^7.18.3` | Gestión de rutas, navegación y modales en ruta contextual (`backgroundLocation`) |
| **[Axios](https://axios-http.com/)** | `^1.20.0` | Cliente HTTP centralizado para peticiones a la API con interceptores |
| **[Lucide React](https://lucide.dev/)** | `^1.41.0` | Iconografía vectorizada moderna |
| **[Oxlint](https://oxc.rs/)** | `^1.79.0` | Linter de alto rendimiento para validación estática de código JSX |

---

## ✨ Características y Funcionalidades

### 1. Navegación Dinámica y Responsiva
- **TopBar Corporativa**: Notificaciones de promociones y beneficios del club.
- **Header con Mega Menús**:
  - **Beneficios**: Desglose visual de incentivos (*Puntos por compras, descuentos exclusivos, regalo de cumpleaños, experiencias VIP*).
  - **Marcas Aliadas**: Visualización en cuadrícula interactiva con los logotipos oficiales.
- **Menú Móvil Lateral (Drawer)**: Navegación táctil optimizada con diseño drawer y acordeones para pantallas pequeñas.

### 2. Modal de Registro en Ruta (`backgroundLocation`)
- Ruta flotante `/registro` sobre la pantalla actual (`/inicio`), manteniendo intacto el fondo visual mediante `location.state.backgroundLocation`.
- Bloqueo dinámico del scroll en el elemento `body` al desplegar el formulario, evitando saltos de desplazamiento.

### 3. Formulario de Registro Integral y Reactivo
- **Datos Personales**: Nombre y apellido con validación tipográfica instantánea.
- **Documento de Identidad**: Selector dinámico poblado desde la API del backend (`GET /tipo-documento`) y campo de número con validación de unicidad.
- **Fecha de Nacimiento con Validación Estricta**:
  - Máscara dinámica al escribir (`dd/mm/aaaa`).
  - Validación de coherencia de días según el mes y año (calendario gregoriano).
  - Comprobación automática de mayoría de edad (+18 años).
  - Conversión transparente a formato ISO (`yyyy-mm-dd`) al enviar a la API.
- **Credenciales y Marca Preferida**:
  - Contraseña segura y correo electrónico.
  - Selección de la marca comercial favorita desde el catálogo dinámico (`GET /marca`).
- **Ubicación Geográfica en Cascada**:
  - Selectores dependientes: **País → Departamento / Estado → Ciudad**.
  - Integración con API geográfica externa y reseteo automático en cadena ante cambios de selección.

### 4. Manejo Granular de Respuestas y Errores
- Feedback de estados de carga (`loading`) con spinners visuales.
- Mensajes de confirmación y redirección tras el registro exitoso.
- Mapeo automático de los errores del backend (`ErrorResponseDTO`) directamente sobre cada campo infractor del formulario.

---

## 📁 Estructura del Proyecto

```text
registromarca/
├── public/                     # Recursos estáticos y favicons
├── src/
│   ├── assets/                 # Imágenes, banners hero y logos de marcas aliadas
│   │   ├── fondoInicio/        # Fotografías hero para la portada
│   │   └── marcasAliadas/      # SVGs oficiales de las marcas
│   ├── components/             # Componentes modulares
│   │   ├── form/               # Elementos de formulario
│   │   │   ├── InputComponenet.jsx   # Input accesible con soporte de errores
│   │   │   └── SelectComponent.jsx   # Select estilizado con iconos dinámicos
│   │   ├── header/             # Encabezado de la página
│   │   │   ├── MobileDrawer.jsx      # Drawer lateral móvil
│   │   │   └── TopBar.jsx            # Barra superior de anuncios
│   │   ├── BeneficiosMenu.jsx  # Mega menú de recompensas
│   │   ├── LinkButton.jsx      # Enlace estilizado con apariencia de botón
│   │   └── MarcasAliadasMenu.jsx # Mega menú de marcas
│   ├── layouts/
│   │   └── LayoutHeader.jsx    # Layout con Header sticky y Outlet
│   ├── pages/
│   │   ├── HomePage.jsx        # Landing page principal
│   │   └── RegistroPage.jsx    # Modal de registro con validaciones
│   ├── routes/
│   │   └── RoutesApp.Route.jsx # Definición de rutas y soporte backgroundLocation
│   ├── services/
│   │   ├── apiBack.js          # Configuración base de Axios e interceptores
│   │   ├── marcaService.js     # Consumo de endpoints de marcas
│   │   ├── tipoDocumentoService.js # Consumo de tipos de documento
│   │   ├── ubicacionService.js # Consumo de API geográfica y ubicaciones
│   │   └── usuarioService.js   # Peticiones de creación de usuario
│   ├── App.css                 # Estilos específicos de componentes
│   ├── App.jsx                 # Componente raíz
│   ├── index.css               # Directivas utilitarias de Tailwind CSS v4
│   └── main.jsx                # Punto de anclaje de React en el DOM
├── .env                        # Variables de entorno locales
├── .oxlintrc.json              # Configuración del linter Oxlint
├── index.html                  # Plantilla HTML inicial
├── package.json                # Dependencias y scripts
└── vite.config.js              # Configuración de Vite y plugins
```

---

## 🔌 Integración con el Backend y Servicios

### 1. API Backend Propia (`apiBack.js`)
Configuración centralizada de Axios que apunta al backend en Spring Boot (`http://localhost:8080/`):
- **Interceptor de Peticiones**: Inyecta el token de autenticación (si existe) en las cabeceras.
- **Interceptor de Respuestas**: Detecta errores de validación y estados `401 Unauthorized`.

> [!NOTE]
> La base de datos del backend comenzó operando en **H2** y fue migrada exitosamente a **MySQL en Aiven Cloud** (con cifrado SSL obligatorio), asegurando alta disponibilidad y persistencia real de todos los usuarios registrados desde esta interfaz.

**Endpoints consumidos:**
* `GET /marca`: Obtiene el listado de marcas comerciales activas.
* `GET /tipo-documento`: Obtiene el catálogo de documentos válidos (CC, CE, TI, etc.).
* `POST /usuario`: Envía el payload completo para registrar al cliente y disparar el correo de bienvenida.
* `GET /ubicacion` y `POST /ubicacion`: Consulta y guardado de direcciones.

### 2. API Geográfica Externa
* Utilizada en `ubicacionService.js` (`https://countriesnow.space/api/v0.1/countries`) para la carga de países, departamentos y ciudades dinámicas.

---

## ⚙️ Variables de Entorno

Crea un archivo `.env` en la raíz de `registromarca/`:

```env
# URL base de la API Backend en Spring Boot
VITE_API_URL_Revueta_Back=http://localhost:8080/

# Tiempo de espera límite para peticiones HTTP (en milisegundos)
VITE_TIMEOUT_PETICION=10000
```

---

## 🛠️ Instalación y Ejecución Local

### Prerrequisitos
* **Node.js** (versión 18 o superior)
* **npm** (o gestor de paquetes de tu preferencia)
* El backend **Registro_Marca** corriendo en `http://localhost:8080` ([Ver instrucciones](../Registro_Marca/README.md))

### Pasos:

1. **Ingresar a la carpeta del frontend:**
   ```bash
   cd registromarca
   ```

2. **Instalar las dependencias:**
   ```bash
   npm install
   ```

3. **Ejecutar en modo desarrollo:**
   ```bash
   npm run dev
   ```
   Abre tu navegador en: 👉 **`http://localhost:5173`**

4. **Compilar para producción:**
   ```bash
   npm run build
   ```

5. **Auditar código con Oxlint:**
   ```bash
   npm run lint
   ```

---

## 👥 Marcas Aliadas Oficiales

* 🦅 **American Eagle**
* 👖 **Americanino**
* 🧥 **Chevignon**
* 👗 **Esprit**
* 🌸 **NAF NAF**
* 🎯 **Rifle**

---

## 👨‍💻 Autor

Este proyecto fue diseñado, desarrollado e implementado por:

**Juan David Zapata Barrera**  
*Desarrollador de Software*  
GitHub: [@JuanZB360](https://github.com/JuanZB360)

---

<div align="center">

Club de Fidelización © 2026 - Todos los derechos reservados.

</div>
