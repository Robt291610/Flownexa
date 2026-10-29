take into acconunt
src/main/resources/db/schema.sql     CREATE EXTENSION IF NOT EXISTS pgcrypto;

Auth API --- Arquitectura Hexagonal con Spring Boot
1. Objetivo
Este documento define una estructura inicial para el servicio de
autenticación (Auth API) de la plataforma RAG para empresas.

La arquitectura propuesta es Hexagonal (Ports and Adapters). Su
objetivo es mantener las reglas y los casos de uso de la aplicación
independientes de detalles externos como HTTP, PostgreSQL, Spring Data
JPA, correo electrónico y la implementación criptográfica de JWT.

La propuesta comienza con un solo proyecto Spring Boot y un solo módulo
Maven. No es necesario separar cada capa en módulos Maven desde el
inicio.

auth-api/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── empresa/
│   │   │           └── auth/
│   │   │               ├── AuthApiApplication.java
│   │   │               │
│   │   │               ├── domain/
│   │   │               │   ├── model/
│   │   │               │   │   ├── User.java
│   │   │               │   │   ├── Role.java
│   │   │               │   │   └── PasswordResetToken.java
│   │   │               │   ├── exception/
│   │   │               │   │   ├── InvalidCredentialsException.java
│   │   │               │   │   ├── EmailAlreadyExistsException.java
│   │   │               │   │   └── InvalidResetTokenException.java
│   │   │               │   └── service/
│   │   │               │
│   │   │               ├── application/
│   │   │               │   ├── port/
│   │   │               │   │   ├── in/
│   │   │               │   │   │   ├── RegisterUserUseCase.java
│   │   │               │   │   │   ├── LoginUseCase.java
│   │   │               │   │   │   └── RequestPasswordResetUseCase.java
│   │   │               │   │   └── out/
│   │   │               │   │       ├── LoadUserPort.java
│   │   │               │   │       ├── SaveUserPort.java
│   │   │               │   │       ├── PasswordHasher.java
│   │   │               │   │       ├── TokenIssuer.java
│   │   │               │   │       ├── EmailSender.java
│   │   │               │   │       ├── LoadPasswordResetTokenPort.java
│   │   │               │   │       └── SavePasswordResetTokenPort.java
│   │   │               │   └── service/
│   │   │               │       ├── RegisterUserService.java
│   │   │               │       ├── LoginService.java
│   │   │               │       └── PasswordResetService.java
│   │   │               │
│   │   │               ├── adapter/
│   │   │               │   ├── in/
│   │   │               │   │   └── web/
│   │   │               │   │       ├── AuthController.java
│   │   │               │   │       ├── dto/
│   │   │               │   │       │   ├── LoginRequest.java
│   │   │               │   │       │   ├── RegisterRequest.java
│   │   │               │   │       │   ├── RegisterResponse.java
│   │   │               │   │       │   ├── AuthResponse.java
│   │   │               │   │       │   ├── ForgotPasswordRequest.java
│   │   │               │   │       │   └── ResetPasswordRequest.java
│   │   │               │   │       ├── mapper/
│   │   │               │   │       │   └── AuthWebMapper.java
│   │   │               │   │       └── exception/
│   │   │               │   │           └── GlobalExceptionHandler.java
│   │   │               │   │
│   │   │               │   └── out/
│   │   │               │       ├── persistence/
│   │   │               │       │   ├── entity/
│   │   │               │       │   │   ├── UserEntity.java
│   │   │               │       │   │   └── PasswordResetTokenEntity.java
│   │   │               │       │   ├── SpringDataUserRepository.java
│   │   │               │       │   ├── SpringDataPasswordResetTokenRepository.java
│   │   │               │       │   ├── UserPersistenceAdapter.java
│   │   │               │       │   ├── PasswordResetTokenPersistenceAdapter.java
│   │   │               │       │   └── mapper/
│   │   │               │       │       ├── UserMapper.java
│   │   │               │       │       └── PasswordResetTokenMapper.java
│   │   │               │       ├── security/
│   │   │               │       │   ├── SpringPasswordHasher.java
│   │   │               │       │   └── JwtTokenIssuer.java
│   │   │               │       └── email/
│   │   │               │           ├── SmtpEmailSender.java
│   │   │               │           └── LoggingEmailSender.java
│   │   │               │
│   │   │               ├── scheduler/
│   │   │               │   └── PasswordResetTokenCleanupJob.java
│   │   │               │
│   │   │               └── config/
│   │   │                   ├── SecurityConfig.java
│   │   │                   ├── JwtConfig.java
│   │   │                   └── OpenApiConfig.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/
│   │           └── migration/
│   └── test/
│       └── java/
│           └── com/
│               └── empresa/
│                   └── auth/
│                       ├── application/
│                       ├── adapter/
│                       └── architecture/
└── README.md
com.empresa.auth es un nombre de ejemplo. Sustitúyelo por el paquete
real de tu proyecto. Los archivos que aparecen en el árbol son una
guía de destino: no tienes que crearlos todos desde el primer día.

3. Responsabilidad de cada parte
domain/ --- Dominio
Contiene los conceptos y reglas centrales del negocio, por ejemplo, el
usuario, los roles y las excepciones del dominio.

model/: modelos del dominio, como User y Role.
exception/: excepciones propias del dominio.
service/: reglas de dominio que no encajan naturalmente en una
sola entidad.
Mantén esta capa libre de dependencias de Spring MVC, Spring Data JPA y
detalles HTTP. Si utilizas entidades JPA separadas, el modelo de dominio
tampoco necesita anotaciones JPA.

application/ --- Casos de uso y puertos
Coordina las operaciones que ofrece el servicio.

port/in/: interfaces de los casos de uso que la aplicación permite
ejecutar.
port/out/: interfaces que describen las capacidades externas que
la aplicación necesita.
service/: implementaciones de los casos de uso.
Ejemplos: - RegisterUserUseCase: registrar un usuario. -
LoginUseCase: iniciar sesión. - LoadUserPort: buscar un usuario. -
TokenIssuer: solicitar la creación de un token. - PasswordHasher:
generar y verificar hashes de contraseñas.

Los puertos son interfaces definidas desde las necesidades de la
aplicación; los adaptadores externos las implementan.

adapter/in/web/ --- Adaptador de entrada
Expone la aplicación por HTTP mediante Spring MVC.

AuthController: recibe solicitudes REST y llama a los casos de
uso.
dto/: objetos de entrada y salida de la API.
mapper/: conversión entre DTO y los objetos que usa la aplicación,
cuando esa conversión lo justifique.
El controlador debe ser delgado: no debe contener toda la lógica de
autenticación ni acceder directamente al repositorio JPA.

adapter/out/ --- Adaptadores de salida
Implementa los puertos de salida definidos por la aplicación.

persistence/: conecta los casos de uso con PostgreSQL mediante
Spring Data JPA.
security/: implementa el hash de contraseñas y la emisión de JWT.
email/: envía correos de verificación o recuperación de
contraseña.
SpringDataUserRepository es el repositorio técnico de Spring Data.
UserPersistenceAdapter implementa los puertos que utiliza la
aplicación y traduce entre el modelo de dominio y las entidades de
persistencia.

config/ --- Configuración del framework
Contiene la configuración de Spring Security, beans y claves o
propiedades necesarias para la integración criptográfica.

No guardes claves privadas, contraseñas de bases de datos ni secretos
directamente en el repositorio. Usa variables de entorno o un gestor de
secretos apropiado.

resources/
application.yml: configuración de la aplicación y conexión a
PostgreSQL.
db/migration/: scripts de Flyway, si utilizas migraciones
versionadas.
4. Dirección de las dependencias
La regla fundamental es que las dependencias de código apuntan hacia el
interior:

HTTP / Spring MVC
       |
       v
Adaptador de entrada
       |
       v
Puerto de entrada
       |
       v
Servicio de aplicación
       |
       +------> Dominio
       |
       +------> Puertos de salida
                       ^
                       |
              Adaptadores de salida
              (JPA, JWT, correo)
El servicio de aplicación conoce las interfaces de salida, pero no
necesita conocer sus implementaciones concretas. La configuración de
Spring conecta las implementaciones con los puertos.

Importante: el diagrama representa dependencias de código y
colaboración conceptual, no necesariamente el orden exacto de todas las
llamadas en tiempo de ejecución.

5. Dependencias recomendadas
Selecciona las dependencias compatibles con la versión de Spring Boot
que elijas en Spring Initializr.

Dependencias principales
Dependencia Uso

Spring Web Endpoints REST mediante Spring MVC.

Spring Security Filtros de seguridad, autenticación
y autorización.

Spring Data JPA Persistencia mediante repositorios
y JPA.

PostgreSQL Driver Conexión JDBC a PostgreSQL.

Validation Validación de DTO mediante Jakarta
Bean Validation.

Dependencias recomendadas según la implementación
Dependencia Cuándo utilizarla

OAuth2 Resource Server Principalmente en la Business API,
para validar JWT. También puede ser
útil si el Auth API necesita
validar tokens.

Spring Security OAuth2 JOSE Soporte JOSE/JWT; confirma si ya
está disponible por las
dependencias elegidas. Puede
añadirse explícitamente para la
emisión de JWT mediante
JwtEncoder.

Flyway Migration Control de versiones del esquema de
base de datos. Añade el módulo de
Flyway compatible con tu versión de
PostgreSQL y Spring Boot.

Spring Boot Starter Mail Envío de correos para verificación
y recuperación de contraseñas.

Spring Boot Actuator Health checks y monitorización.

Spring Boot Starter Test Pruebas unitarias y de integración;
normalmente se incluye al crear el
proyecto.

Lombok es opcional. Puedes usarlo si te resulta útil, pero no es un
requisito arquitectónico.

JWT: una consideración importante
Spring Security no crea automáticamente un flujo completo de emisión de
JWT por el mero hecho de añadir Spring Security.

Si vas a emitir JWT desde tu propio Auth API, puedes usar las
abstracciones de Spring Security, como JwtEncoder, con una
configuración correcta de claves y algoritmos. También puedes elegir una
biblioteca JWT especializada. No implementes manualmente algoritmos
criptográficos ni validación de firmas.

La Business API puede usar Spring Security OAuth2 Resource Server para
validar los tokens. Configura correctamente el emisor, la expiración,
las claves de confianza y, cuando corresponda, la audiencia del token.

6. Qué implementar primero
No es necesario construir toda la estructura de una vez. Un orden
práctico sería:

Crear el proyecto y configurar PostgreSQL.
Definir el modelo de usuario y la persistencia.
Implementar el caso de uso de registro con hash de contraseñas.
Implementar el inicio de sesión usando Spring Security.
Implementar la emisión de JWT.
Configurar la Business API para validar los tokens.
Añadir autorización por roles y empresa.
Implementar recuperación de contraseña, verificación de correo y
MFA.
La seguridad de la Business API debe comprobar también que el usuario
tiene acceso a los recursos de su empresa; estar autenticado no
significa tener permiso para consultar cualquier documento.

7. Referencias oficiales
Spring Initializr
Spring Security
Spring Security --- OAuth 2.0 Resource Server
JWT
Spring Data JPA
Flyway Documentation

