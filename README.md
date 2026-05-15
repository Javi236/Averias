# 🏢 CityServices - Gestión de Averías Urbana⚠️

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/android%20studio-%233DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-%234285F4.svg?style=for-the-badge&logo=jetpack-compose&logoColor=white)
![Room](https://img.shields.io/badge/Room%20DB-SQLite-orange?style=for-the-badge&logo=sqlite)
![Hilt](https://img.shields.io/badge/Dagger--Hilt-Injection-blue?style=for-the-badge&logo=google-cloud)

**CityServices** es una solución integral para la gestión de incidencias y averías urbanas. Esta aplicación demuestra una implementación avanzada de la arquitectura de componentes de Android, utilizando bases de datos relacionales, inyección de dependencias y flujos reactivos.

---

## 🚀 Características Principales

### 🏢 Gestión de Ciudades (CRUD)
- Listado detallado de ciudades con **Código Postal** y **Población**.
- Sistema de alta y edición de ciudades.
- **Borrado Seguro**: Implementación de `AlertDialog` preventivo para advertir sobre el borrado en cascada.

### ⚠️ Gestión de Averías (Breakdown)
- **Relación 1:N**: Cada avería está vinculada a una ciudad real mediante una clave foránea (FK).
- **Selector Inteligente**: Uso de un selector (Dropdown) para asignar averías a ciudades existentes.
- **Validación Robusta**: Comprobación de campos vacíos, longitud mínima de código (>3) y unicidad de ID en base de datos.
- **Borrado en Cascada**: Al eliminar una ciudad, el sistema limpia automáticamente todas las averías asociadas.

### 🔔 UX y Feedback
- **Notificaciones Push**: Aviso inmediato al insertar una nueva avería (auto-cancelable).
- **Estados de UI**: Pantallas específicas de *Loading*, *NoData* y errores de validación mediante diálogos Material3.

---

## 🛠️ Stack Tecnológico

| Componente | Tecnología |
| :--- | :--- |
| **Interfaz de Usuario** | Jetpack Compose (Material 3) |
| **Persistencia** | Room Database (Relational & Cascade) |
| **Arquitectura** | MVVM (Model-View-ViewModel) + Repository Pattern |
| **DI** | Dagger-Hilt |
| **Asincronía** | Coroutines & StateFlow |
| **Navegación** | Jetpack Navigation (con paso de argumentos) |
| **Multimedia** | Lottie Animations |

---

## 📂 Estructura del Proyecto
