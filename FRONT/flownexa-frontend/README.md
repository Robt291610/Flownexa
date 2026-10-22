# FlowNexa Frontend — Estructura del Proyecto

## 1. Descripción general

El frontend de FlowNexa se desarrolla utilizando React y TypeScript con Vite. La arquitectura se organiza mediante componentes reutilizables, módulos funcionales y separación de responsabilidades.

El objetivo es mantener un código escalable, mantenible y preparado para integrarse con el backend Java + Spring Boot mediante una API REST.

## 2. Estructura de carpetas

```text
src/
├── app/
├── assets/
├── components/
│   ├── ui/
│   └── common/
├── layouts/
├── features/
│   ├── auth/
│   ├── companies/
│   ├── memberships/
│   ├── users/
│   ├── departments/
│   ├── roles/
│   ├── workflows/
│   ├── requests/
│   ├── approvals/
│   ├── notifications/
│   ├── audit/
│   └── reports/
├── services/
│   ├── api/
│   └── storage/
├── hooks/
├── types/
├── utils/
├── styles/
└── main.tsx
```

## 3. Responsabilidad de cada directorio

| Directorio | Responsabilidad |
|---|---|
| `app/` | Configuración principal, rutas y proveedores globales. |
| `assets/` | Imágenes, iconos y fuentes importadas desde el código. |
| `components/ui/` | Componentes visuales genéricos y reutilizables. |
| `components/common/` | Componentes compartidos para construir páginas. |
| `layouts/` | Estructuras visuales comunes, como autenticación y dashboard. |
| `features/` | Funcionalidades del negocio, organizadas por módulos. |
| `services/api/` | Cliente HTTP y manejo común de peticiones y errores. |
| `services/storage/` | Abstracciones de almacenamiento del lado del cliente cuando sean necesarias. |
| `hooks/` | Hooks compartidos de React. |
| `types/` | Tipos TypeScript utilizados en varios módulos. |
| `utils/` | Funciones auxiliares independientes de React. |
| `styles/` | Estilos globales, variables CSS y normalización. |

## 4. Estructura interna de los módulos

Cada módulo de `features/` puede organizarse de la siguiente manera:

```text
features/requests/
├── pages/
├── components/
├── services/
├── hooks/
├── types/
└── styles/
```

- **pages/**: páginas completas asociadas a rutas.
- **components/**: componentes específicos del módulo.
- **services/**: funciones para consumir los endpoints correspondientes.
- **hooks/**: lógica reutilizable de React dentro del módulo.
- **types/**: interfaces y tipos TypeScript específicos.
- **styles/**: estilos particulares del módulo.

No todos los módulos necesitan todas estas carpetas; se crearán según las necesidades reales.

## 5. Convenciones de archivos

- Componentes React: `PascalCase.tsx`, por ejemplo `RequestTable.tsx`.
- Hooks: `camelCase` con prefijo `use`, por ejemplo `useRequests.ts`.
- Servicios y utilidades: `kebab-case.ts`, por ejemplo `requests-service.ts`.
- Estilos: `kebab-case.css`, por ejemplo `request-table.css`.
- Tipos: nombres descriptivos, por ejemplo `requests.types.ts`.
- Pruebas: nombre del archivo seguido de `.test.ts` o `.test.tsx`.

## 6. Principios de organización

1. Separar la presentación, la lógica de la aplicación y la comunicación con el backend.
2. Reutilizar componentes cuando tengan una responsabilidad clara y puedan utilizarse en diferentes contextos.
3. Mantener las funcionalidades del negocio aisladas dentro de sus módulos.
4. Evitar que los componentes visuales contengan directamente toda la lógica de acceso a la API.
5. Centralizar los estilos globales y utilizar estilos específicos cuando corresponda.
6. Mantener los tipos TypeScript cerca de la funcionalidad que los utiliza, salvo que sean compartidos.
7. No duplicar validaciones críticas de seguridad en el frontend como sustituto de las validaciones del backend.
8. Evitar introducir carpetas o abstracciones que todavía no sean necesarias.

## 7. Integración con el backend

El frontend se comunicará con el backend Java + Spring Boot mediante una API REST.

La configuración de las peticiones HTTP se centralizará en `services/api/`, mientras que cada módulo definirá sus propios servicios para consultar o modificar los datos correspondientes.

La autorización, la validación de permisos y el aislamiento entre empresas deberán verificarse en el backend.

## 8. Evolución de la arquitectura

La estructura se ampliará progresivamente conforme se implementen las funcionalidades del sistema, comenzando por autenticación y configuración de empresas, y continuando con workflows, solicitudes, aprobaciones, auditoría y reportes.

La prioridad será mantener una arquitectura clara sin introducir complejidad innecesaria antes de que el proyecto la requiera.