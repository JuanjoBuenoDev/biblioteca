# 📚 Gestión Biblioteca — API REST

API REST completa para la gestión de una biblioteca, desarrollada con **Java 21** y **Spring Boot 3.5**.
Incluye autenticación y autorización mediante **JWT**, persistencia con **JPA/Hibernate** y arquitectura en capas.

---

## 🛠️ Tecnologías

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.5.0 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | H2 (desarrollo) / MySQL (producción) |
| Seguridad | Spring Security + JWT (jjwt 0.12.6) |
| Validación | Jakarta Bean Validation |
| Utilidades | Lombok |
| Build | Maven |

---

## 🏗️ Arquitectura

```
com.biblioteca
├── config/         # Configuración de seguridad y JWT
├── security/       # Filtro JWT, JwtUtil, UserDetailsService
├── entity/         # Entidades JPA (Autor, Libro, Usuario, Prestamo)
├── repository/     # Interfaces JpaRepository
├── service/        # Lógica de negocio
├── controller/     # Endpoints REST
└── dto/            # Objetos de transferencia de datos
```

---

## 📋 Requisitos previos

- Java 21+
- Maven 3.9+
- (Opcional) MySQL 8+ para producción

---

## 🚀 Arrancar el proyecto

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/gestion-biblioteca.git
cd gestion-biblioteca

# Arrancar con H2 en memoria (desarrollo)
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

### Consola H2

Con la aplicación arrancada, accede a la consola de base de datos en:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:biblioteca
Usuario:  sa
Password: (vacío)
```

---

## ⚙️ Configuración

El fichero `src/main/resources/application.properties` contiene la configuración por defecto para desarrollo con H2:

```properties
spring.datasource.url=jdbc:h2:mem:biblioteca
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
jwt.secret=MiClaveSecretaSuperSegura1234567890AbCd!!
jwt.expiration=86400000
```

### Cambiar a MySQL (producción)

Comenta el bloque H2 y descomenta/añade:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

---

## 🔐 Autenticación

La API usa **JWT Bearer Token**. El flujo es:

1. Regístrate o inicia sesión para obtener el token.
2. Incluye el token en todas las peticiones protegidas.

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Endpoints públicos (sin token)

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/auth/register` | Registrar nuevo usuario |
| `POST` | `/api/auth/login` | Iniciar sesión y obtener JWT |

### Ejemplo de login

```json
POST /api/auth/login
{
  "username": "admin",
  "password": "Admin1234!"
}
```

Respuesta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "username": "admin",
  "rol": "ADMIN"
}
```

---

## 📡 Endpoints

### Autores `/api/autores`

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `GET` | `/` | USER, ADMIN | Listar todos los autores |
| `GET` | `/{id}` | USER, ADMIN | Buscar autor por ID |
| `GET` | `/nacionalidad/{n}` | USER, ADMIN | Filtrar por nacionalidad |
| `POST` | `/` | ADMIN | Crear autor |
| `PUT` | `/{id}` | ADMIN | Actualizar autor |
| `DELETE` | `/{id}` | ADMIN | Eliminar autor |

### Libros `/api/libros`

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `GET` | `/` | USER, ADMIN | Listar todos los libros |
| `GET` | `/{id}` | USER, ADMIN | Buscar libro por ID |
| `GET` | `/isbn/{isbn}` | USER, ADMIN | Buscar por ISBN |
| `GET` | `/buscar?titulo=X` | USER, ADMIN | Búsqueda parcial por título |
| `GET` | `/disponibles` | USER, ADMIN | Libros con stock > 0 |
| `POST` | `/` | ADMIN | Crear libro |
| `PUT` | `/{id}` | ADMIN | Actualizar libro |
| `DELETE` | `/{id}` | ADMIN | Eliminar libro |

### Préstamos `/api/prestamos`

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `POST` | `/` | USER, ADMIN | Crear préstamo |
| `PUT` | `/{id}/devolver` | USER, ADMIN | Registrar devolución |
| `GET` | `/usuario/{id}` | USER, ADMIN | Préstamos de un usuario |
| `GET` | `/activos` | ADMIN | Todos los préstamos activos |
| `GET` | `/vencidos` | ADMIN | Préstamos vencidos |

### Usuarios `/api/usuarios`

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `GET` | `/` | ADMIN | Listar usuarios |
| `GET` | `/{id}` | ADMIN | Buscar usuario |
| `PUT` | `/{id}/password` | ADMIN, propio USER | Cambiar contraseña |
| `PUT` | `/{id}/desactivar` | ADMIN | Desactivar cuenta |

---

## 🗄️ Modelo de datos

```
autores          libros              usuarios         prestamos
────────         ──────────          ────────         ──────────
id (PK)          id (PK)             id (PK)          id (PK)
nombre           isbn (UNIQUE)       username         fecha_inicio
apellidos        titulo              password         fecha_fin_prevista
nacionalidad     genero              email            fecha_devolucion
fecha_nac        anio_publicacion    rol              estado
                 stock               activo           usuario_id (FK)
                 autor_id (FK)                        libro_id (FK)
```

---

## 👤 Usuarios de prueba

Al arrancar, el `DataLoader` inserta automáticamente:

| Username | Password | Rol |
|---|---|---|
| `admin` | `Admin1234!` | ADMIN |
| `usuario1` | `User1234!` | USER |
| `usuario2` | `User1234!` | USER |

---

## 📁 Estructura del proyecto

```
src/
└── main/
    ├── java/com/biblioteca/
    │   ├── GestionBibliotecaApplication.java
    │   ├── config/
    │   │   ├── SecurityConfig.java
    │   │   └── JwtConfig.java
    │   ├── security/
    │   │   ├── JwtUtil.java
    │   │   ├── JwtAuthenticationFilter.java
    │   │   └── UserDetailsServiceImpl.java
    │   ├── entity/
    │   │   ├── Autor.java
    │   │   ├── Libro.java
    │   │   ├── Usuario.java
    │   │   └── Prestamo.java
    │   ├── repository/
    │   │   ├── AutorRepository.java
    │   │   ├── LibroRepository.java
    │   │   ├── UsuarioRepository.java
    │   │   └── PrestamoRepository.java
    │   ├── service/
    │   │   ├── AutorService.java
    │   │   ├── LibroService.java
    │   │   ├── UsuarioService.java
    │   │   └── PrestamoService.java
    │   ├── controller/
    │   │   ├── AuthController.java
    │   │   ├── AutorController.java
    │   │   ├── LibroController.java
    │   │   ├── UsuarioController.java
    │   │   └── PrestamoController.java
    │   └── dto/
    │       ├── LoginRequest.java
    │       ├── LoginResponse.java
    │       ├── LibroDTO.java
    │       └── PrestamoDTO.java
    └── resources/
        └── application.properties
```

---

## 📄 Licencia

Juanjo Bueno
Proyecto Backend.
