# Proyecto `mod05proyMvcSpringBoot` — Explicación Arquitectónica

## 1. ¿Qué es este proyecto?

Es una **aplicación web Spring Boot 4+ (Java 21)** que implementa un **sistema simple de gestión de artículos y ventas**, con:

- Una **interfaz web (Thymeleaf)** para consultar e insertar artículos
- Una **API REST** para los mismos datos (JSON)
- **Seguridad con Spring Security**: autenticación por formulario, HTTP Basic y tokens JWT
- **Dos bases de datos MySQL**: una para datos de negocio y otra para usuarios/roles
- **Dos capas de persistencia**: una con `JdbcTemplate` (principal) y otra con JDBC puro (legacy)

---

## 2. Estructura de Paquetes

```
mx.com.qtx.mod05proyMvcSpringBoot/
├── Mod05proyMvcSpringBootApplication.java   ← Clase principal
│
├── objetosNegocio/        ← Modelos de dominio (POJOs)
│   ├── Articulo.java       ← Artículo con validaciones Jakarta Validation
│   ├── Categoria.java      ← Categoría del artículo
│   ├── Persona.java        ← Persona (cliente/vendedor)
│   ├── Venta.java
│   ├── DetalleVenta.java
│   └── validacion/        ← Interfaces para grupos de validación
│
├── core/                   ← Contrato de negocio (interfaz)
│   └── IGestorVentas.java  ← Define qué operaciones de negocio existen
│
├── servicios/              ← Implementación de la lógica de negocio
│   ├── GestorVentas.java   ← @Service que implementa IGestorVentas
│   ├── IGestorDatosSpring.java ← Interfaz de acceso a datos con DTOs
│   ├── dtos/               ← DTOs (ArticuloDTO, CategoriaDTO, VentaDTO, etc.)
│   └── err/                ← Excepciones de negocio
│
├── persistencia/           ← Implementaciones de acceso a datos
│   ├── GestorBD_MySQL.java         ← JDBC puro (legacy, implementa IGestorBD)
│   └── jdbctemplate/
│       └── GestorDatosJdbcTemplate.java ← Implementación principal con JdbcTemplate
│
├── web/                    ← CONTROLADORES (MVC y REST)
│   ├── VentasController.java      ← @Controller (Thymeleaf)
│   └── ApiVentasController.java   ← @RestController (JSON)
│
├── seguridad/              ← Configuración de seguridad
│   ├── ConfiguracionSeguridad.java ← @Configuration con SecurityFilterChains
│   ├── UtilPasswords.java
│   ├── UtilWebSecurity.java
│   ├── jwt/                ← Lógica de JWT
│   ├── core/               ← Servicios de seguridad
│   ├── servicios/          ← UserDetailsService, etc.
│   └── web/                ← Filtros y controladores de seguridad
│       ├── FiltroTokensJwt_SS.java       ← Filtro JWT
│       ├── SeguridadCtlr.java            ← Login/logout
│       └── ManejadorRechazoAutorizacionApi.java ← Manejador de errores 403
│
├── audit/                  ← Logging de operaciones
├── util/                   ← Utilidades y validaciones personalizadas
└── probadores/             ← Clases de prueba
```

---

## 3. Arquitectura por Capas (Flujo de Datos)

```
CLIENTE (Navegador / Postman)
       ↓
┌─────────────────────────────────┐
│   CONTROLADORES (web/)           │
│   VentasController (@Controller)  │→ Thymeleaf templates
│   ApiVentasController (@RestCtrl) │→ JSON
└─────────────────────────────────┘
       ↓
┌─────────────────────────────────┐
│   SERVICIOS (servicios/)         │
│   GestorVentas (@Service)         │
│   Implementa: IGestorVentas       │
│   Traduce: DTOs ↔ ObjetosNegocio │
└─────────────────────────────────┘
       ↓
┌─────────────────────────────────┐
│   PERSISTENCIA (persistencia/)    │
│   GestorDatosJdbcTemplate         │← Principal (Spring JdbcTemplate)
│   GestorBD_MySQL                  │← Legacy (JDBC puro)
│   Implementan: IGestorDatosSpring │
└─────────────────────────────────┘
       ↓
┌─────────────────────────────────┐
│   BASE DE DATOS MySQL             │
│   - ejemplosjdbc (negocio)        │
│   - bdUsuarios (seguridad)        │
└─────────────────────────────────┘
```

### Flujo típico de una petición web

1. El navegador hace `GET /consultarArticulo`
2. Spring Security verifica si el usuario está autenticado (formulario login)
3. `VentasController.irAconsultaArticulo()` retorna la vista Thymeleaf
4. El usuario escribe una clave y hace POST
5. `VentasController.buscarArticulo(cveArticulo, model)`
6. Llama a `gestorVtas.recuperarArticuloXID()` (en GestorVentas)
7. GestorVentas llama a `gestorDatos.leerArticuloXID()` (GestorDatosJdbcTemplate)
8. Se ejecuta la consulta SQL y se devuelve un `ArticuloDTO`
9. GestorVentas convierte el DTO a `Articulo` (objeto de negocio) y lo devuelve
10. El controlador pone el resultado en el `Model` y retorna la vista

### Flujo equivalente para API REST

- El cliente (Postman/app) hace `GET /api/articulos/LAP-001`
- Spring Security valida el token JWT (en el header `Authorization: Bearer ...`)
- `ApiVentasController.getArticuloXcve()` procesa y devuelve JSON

---

## 4. Configuración de Seguridad (Clase Clave)

**`ConfiguracionSeguridad.java`** define **dos cadenas de filtros (`SecurityFilterChain`)**:

| Orden | Ruta | Autenticación | CSRF | Sesiones |
|-------|------|---------------|------|----------|
| 1 | `/api/**` | JWT + HTTP Basic | Desactivado | STATELESS |
| 2 | `/**` (lo demás) | Form Login con página personalizada | Activado | Por defecto |

### Roles definidos (3 usuarios en BD `bdUsuarios`)

| Usuario | Password (hash bcrypt) | Roles |
|---------|----------------------|-------|
| alex | ... | admin, vtas |
| david | ... | vtas, compras |
| tavo | ... | compras, cte |

### Reglas de autorización

- `/consultarArticulo`, `/buscarArticulos` → requiere rol `vtas`
- `/insertarArticulo`, `/procesarInsercionArticulo` → requiere **admin EN horario laboral (9-18) Y desde IP interna (192.168.x.x)**
- `/api/**` → requiere rol `cte`
- `/login`, `/logout` → público

Hay un **autorizador compuesto** (`autorizadorAdminHorarioLaboralIpInterna`) que combina tres verificaciones: rol admin + horario laboral + IP interna.

---

## 5. Dos DataSources (Dos Bases de Datos)

En `application.properties` se configuran dos conexiones MySQL:

```
spring.datasource.negocio.*    → BD: ejemplosjdbc (datos de negocio)
spring.datasource.security.*   → BD: bdUsuarios (usuarios y roles)
```

La clase principal `Mod05proyMvcSpringBootApplication` crea el **DataSource de negocio** como `@Primary`, y `ConfiguracionSeguridad` crea el **DataSource de seguridad**.

---

## 6. Puntos Clave para Modificar el Proyecto

### ✚ Agregar una nueva entidad (ej. `Proveedor`)

1. **`objetosNegocio/Proveedor.java`** — POJO con validaciones Jakarta
2. **`servicios/dtos/ProveedorDTO.java`** — DTO para la capa de persistencia
3. **`servicios/IGestorDatosSpring.java`** — Agregar métodos CRUD a la interfaz
4. **`persistencia/jdbctemplate/GestorDatosJdbcTemplate.java`** — Implementar los métodos con `JdbcTemplate`
5. **`core/IGestorVentas.java`** — Agregar métodos de negocio
6. **`servicios/GestorVentas.java`** — Implementar la lógica de negocio (conversión DTO ↔ ObjetoNegocio)
7. **`web/VentasController.java`** o **`web/ApiVentasController.java`** — Endpoints web/REST
8. **Template Thymeleaf** (en `src/main/resources/templates/`) — Si es necesario para la UI

### ✚ Cambiar el proveedor de persistencia (ej. a JPA/Hibernate)

- Reemplazar o modificar `GestorDatosJdbcTemplate` para que use `EntityManager`/JpaRepository
- La interfaz `IGestorDatosSpring` **no cambia**, solo su implementación
- Los servicios (`GestorVentas`) y controladores **no se tocan**

### ✚ Agregar un nuevo endpoint REST

- En `ApiVentasController.java` agregar método con `@GetMapping`, `@PostMapping`, etc.
- En `ConfiguracionSeguridad` ajustar `requestMatchers("/api/**")` si hay reglas especiales

### ✚ Agregar un nuevo rol de seguridad

1. En `ConfiguracionSeguridad.java`, agregar el rol en `.hasRole("nuevoRol")`
2. Crear el usuario en BD con ese rol (o en el `UserDetailsManager`)

### ✚ Cambiar la vista de login

Los templates están en `src/main/resources/templates/seguridad/`:
- `login.html`
- `logout.html`

---

## 7. Resumen Visual de Dependencias entre Capas

```
Controller (@Controller / @RestController)
    │  depends on (tiene un campo)
    ▼
IGestorVentas (interfaz en core/)
    ▲  implementa
    │
GestorVentas (@Service en servicios/)
    │  depends on
    ▼
IGestorDatosSpring (interfaz en servicios/)
    ▲  implementan
    │
    ├── GestorDatosJdbcTemplate (principal)
    └── GestorBD_MySQL (legacy, implementa IGestorBD, no IGestorDatosSpring)
```

**Regla de oro:** Las dependencias apuntan hacia interfaces/abstracciones. Los controladores conocen `IGestorVentas`. Los servicios conocen `IGestorDatosSpring`. Así cambiar la base de datos o la lógica de negocio no afecta a las capas superiores.

---

## 8. Cómo Ejecutar el Proyecto

Necesitas **MySQL corriendo** con las bases de datos `ejemplosjdbc` y `bdUsuarios` (se crean automáticamente con `createDatabaseIfNotExist=true`). Luego:

```bash
./mvnw spring-boot:run
```

Usuarios de prueba: `alex` / `david` / `tavo` (contraseñas en `ConfiguracionSeguridad.java` con hash bcrypt).