# 🎁 Sistema de Fidelización - Registro de Clientes & Marcas

Frontend para la plataforma de fidelización de clientes de marcas aliadas (*American Eagle*, *Americanino*, *Chevignon*, *Esprit*, *NAF NAF*, *Rifle*). Esta aplicación permite a los usuarios conocer las marcas asociadas, explorar los beneficios del club y registrarse en el sistema acumulador de puntos mediante un formulario reactivo con validaciones avanzadas e integración geográfica en cascada.

---

## 🚀 Tecnologías Principales

| Tecnología | Versión | Propósito |
| :--- | :--- | :--- |
| **[React](https://react.dev/)** | `^19.2.8` | Biblioteca base para la construcción de interfaces de usuario declarativas |
| **[Vite](https://vitejs.dev/)** | `^8.2.2` | Entorno de desarrollo rápido y empaquetador de producción |
| **[Tailwind CSS](https://tailwindcss.com/)** | `^4.3.3` | Framework de estilos utilitarios con `@tailwindcss/vite` |
| **[React Router DOM](https://reactrouter.com/)** | `^7.18.3` | Gestión de rutas, navegación y modales contextuales (`backgroundLocation`) |
| **[Axios](https://axios-http.com/)** | `^1.20.0` | Cliente HTTP para consumo de APIs con interceptores JWT |
| **[Lucide React](https://lucide.dev/)** | `^1.41.0` | Iconografía vectorizada moderna |
| **[Oxlint](https://oxc.rs/)** | `^1.79.0` | Linter de alto rendimiento para JavaScript/JSX |

---

## ✨ Características y Funcionalidades

### 1. Navegación Dinámica y Responsiva
- **TopBar Corporativa**: Mensajes promocionales e informativos sobre el club de beneficios.
- **Header con Mega Menús**:
  - **Beneficios**: Desglose interactivo de ventajas (*Puntos por compras, descuentos exclusivos, regalos de cumpleaños, experiencias VIP*).
  - **Marcas Aliadas**: Visualización con logotipos oficiales en cuadrícula interactiva.
- **Menú Móvil Lateral (Drawer)**:
  - Navegación táctil optimizada con diseño drawer y acordeones expandibles para categorías y marcas.

### 2. Modal de Registro en Ruta (`backgroundLocation`)
- Implementación de ruta flotante `/registro` sobre la ruta actual (`/inicio`), manteniendo el contexto visual mediante el estado `location.state.backgroundLocation`.
- Bloqueo dinámico del scroll en el elemento `body` al abrir el modal, con compensación automática del ancho de la barra de desplazamiento.

### 3. Formulario de Registro Integral
- **Datos Personales**: Campos para nombre y apellido con validación en tiempo real.
- **Documento de Identidad**:
  - Selector dinámico de tipos de documento obtenidos desde el backend.
  - Validación de número de identificación.
- **Fecha de Nacimiento**:
  - Máscara y formateo automático dinámico al escribir (`dd/mm/aaaa`).
  - Validación de coherencia de días según el mes y año (calendario gregoriano).
  - Comprobación estricta de mayoría de edad (mínimo 18 años requeridos).
  - Conversión automática al formato ISO (`yyyy-mm-dd`) para el consumo del backend.
- **Credenciales y Marca Favorita**:
  - Captura segura de correo electrónico y contraseña.
  - Asociación obligatoria a una de las marcas aliadas del programa.
- **Ubicación Geográfica en Cascada**:
  - Selectores dependientes: **País → Departamento / Estado → Ciudad**.
  - Conexión con servicio geográfico externo y limpieza automática en cadena ante cambios de selección previa.
  - Campo de dirección de residencia.

### 4. Manejo Robusto de Errores y Estados
- Retroalimentación de estados de carga (`loading`) con animaciones.
- Confirmación visual de éxito y redirección automática tras completar el registro.
- Captura, mapeo y renderizado granular de errores devueltos por el backend (por campo específico o alerta global).

---

## 📁 Estructura del Proyecto

```text
registromarca/
├── public/                     # Archivos estáticos y favicon
├── src/
│   ├── assets/                 # Recursos multimedia (imágenes, logos de marcas aliadas)
│   │   ├── fondoInicio/        # Fondos para la sección hero
│   │   └── marcasAliadas/      # SVGs de logotipos oficiales
│   ├── components/             # Componentes reutilizables
│   │   ├── form/               # Elementos de formulario estilizados
│   │   │   ├── InputComponenet.jsx   # Input con soporte de errores y accesibilidad
│   │   │   └── SelectComponent.jsx   # Select personalizado con icono y opciones dinámicas
│   │   ├── header/             # Subcomponentes del encabezado
│   │   │   ├── MobileDrawer.jsx      # Menú móvil lateral con acordeones
│   │   │   └── TopBar.jsx            # Barra informativa superior
│   │   ├── BeneficiosMenu.jsx  # Mega menú de recompensas y beneficios
│   │   ├── LinkButton.jsx      # Enlace estéticamente estilizado como botón
│   │   └── MarcasAliadasMenu.jsx # Mega menú con listado de marcas aliadas
│   ├── layouts/
│   │   └── LayoutHeader.jsx    # Plantilla principal con Header sticky y Outlet
│   ├── pages/
│   │   ├── HomePage.jsx        # Landing page con Hero y tarjetas de propuesta de valor
│   │   └── RegistroPage.jsx    # Modal de registro con formulario y validaciones
│   ├── routes/
│   │   └── RoutesApp.Route.jsx # Definición de rutas y soporte de modal contextual
│   ├── services/
│   │   ├── apiBack.js          # Instancia centralizada de Axios e interceptores
│   │   ├── marcaService.js     # Consumo de endpoints de marcas aliadas
│   │   ├── tipoDocumentoService.js # Consumo de catálogo de tipos de documento
│   │   ├── ubicacionService.js # Integración con API geográfica externa y endpoints de ubicación
│   │   └── usuarioService.js   # Registro, consulta y actualización de usuarios
│   ├── App.css                 # Estilos globales de la aplicación
│   ├── App.jsx                 # Componente contenedor de la aplicación
│   ├── index.css               # Importaciones base de Tailwind CSS v4
│   └── main.jsx                # Punto de entrada de React con BrowserRouter
├── .env                        # Variables de entorno locales
├── .oxlintrc.json              # Configuración de reglas para Oxlint
├── index.html                  # Plantilla HTML base del proyecto
├── package.json                # Dependencias y scripts de ejecución
└── vite.config.js              # Configuración de Vite y plugins
```

---

## 🔌 Servicios y APIs

### 1. Backend Propio (`apiBack.js`)
Configuración centralizada de Axios con URL base configurable y tiempo de respuesta límite:
- **Interceptor de Peticiones**: Inyecta de manera automática el token JWT desde `localStorage.getItem('token')` en la cabecera `Authorization: Bearer <token>`.
- **Interceptor de Respuestas**: Intercepta respuestas con estado `401 Unauthorized` para invalidar la sesión y limpiar el almacenamiento local.

**Endpoints utilizados:**
- `GET /marca`: Consulta las marcas activas vinculadas al programa.
- `GET /tipo-documento`: Consulta los tipos de documento habilitados.
- `POST /usuario`: Registra un nuevo usuario asociando su marca, tipo de documento y dirección.
- `GET /ubicacion` y `POST /ubicacion`: Consulta y guardado de direcciones.

### 2. API Geográfica Pública Externa
- **Endpoint Base**: `https://countriesnow.space/api/v0.1/countries`
- Utilizada en `ubicacionService.js` para cargar dinámicamente:
  - Lista de países (`/iso`).
  - Departamentos/estados asociados al país (`/states`).
  - Ciudades vinculadas al departamento (`/state/cities`).

---

## ⚙️ Variables de Entorno

El proyecto requiere un archivo `.env` en la raíz con las siguientes claves:

```env
# URL base de la API Backend (Spring Boot, Node.js, etc.)
VITE_API_URL_Revueta_Back=http://localhost:8080/

# Tiempo máximo de espera para peticiones HTTP (en milisegundos)
VITE_TIMEOUT_PETICION=10000
```

---

## 🛠️ Instalación y Ejecución

### Prerrequisitos
- [Node.js](https://nodejs.org/) (versión 18.x o superior)
- Gestor de paquetes `npm` (o `yarn` / `pnpm`)

### Instrucciones

1. **Instalar dependencias del proyecto:**
   ```bash
   npm install
   ```

2. **Ejecutar el servidor en entorno de desarrollo:**
   ```bash
   npm run dev
   ```
   Abre tu navegador en `http://localhost:5173` (o el puerto indicado en la terminal).

3. **Construir para producción:**
   ```bash
   npm run build
   ```
   Genera la distribución lista para producción en la carpeta `dist/`.

4. **Visualizar la versión construida (preview):**
   ```bash
   npm run preview
   ```

5. **Verificar el código con Oxlint:**
   ```bash
   npm run lint
   ```

---

## 👥 Marcas Aliadas Oficiales

- 🦅 **American Eagle**
- 👖 **Americanino**
- 🧥 **Chevignon**
- 👗 **Esprit**
- 🌸 **NAF NAF**
- 🎯 **Rifle**

---

## 📄 Licencia y Entrega

Proyecto desarrollado como parte de la solución de entrega del programa de fidelización y registro multimarca. Distribuido para fines académicos y de evaluación técnica.
