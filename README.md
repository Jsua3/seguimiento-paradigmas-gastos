# Seguimiento: Paradigmas de Programación – Control de Gastos Personales

Mismo problema resuelto en **5 paradigmas** de programación con **Java**, trabajando en equipo con Git y GitHub. Actividad de seguimiento de Ingeniería de Software II, CUE.

## Problema
Sistema de control de gastos personales con 6 funcionalidades: registrar gasto, listar gastos, calcular total, mostrar el gasto mayor, filtrar por monto mínimo y buscar por concepto (subcadena).

## Equipo y paradigmas
| Integrante | Paradigma | Carpeta | Rama |
|---|---|---|---|
| Juan José Sua Gómez | Estructurado | `estructurado/` | `feature/paradigma-estructurado` |
| Marlon Sarria | Orientado a Objetos | `poo/` | `feature/paradigma-poo` |
| Steveen Cardona | Orientado a Eventos | `eventos/` | `feature/paradigma-eventos` |
| Emerson Ángel | Declarativo | `declarativo/` | `feature/paradigma-declarativo` |
| Alejandro Palacios | Funcional | `funcional/` | `feature/paradigma-funcional` |

## Estructura del repositorio
(una carpeta por paradigma en la raíz: estructurado/, poo/, eventos/, declarativo/, funcional/)

## Flujo de trabajo (Git)
- `master`: versión estable final (tag `v1.0`).
- `develop`: integración.
- `feature/paradigma-*`: una rama por paradigma, integrada a `develop` mediante Pull Request con aprobación de otro integrante.
- Commits con formato `feat:`, `fix:`, `docs:`, `chore:`.

## Cómo ejecutar cada versión
Las instrucciones de cada paradigma están en su sección, más abajo.

---



# paradigmas

**creado por:** Juan José Sua Gómez · **Rama:** `feature/paradigma-estructurado` · **Carpeta:** `estructurado/`

Versión del sistema de control de gastos personales hecha con programación estructurada: 
este programa lo hice con menú interactivo en consola. 

### Elementos del paradigma aplicados (lo saqué del documento del taller)
Elementos a aplicar: Variables globales/locales, estructuras condicionales (if / else), ciclos (while, for), 
funciones o procedimientos independientes con paso de parámetros y menú secuencial interactivo en consola.
Enfoque: Control de flujo secuencial y modular mediante funciones explícitas y estructuras de datos nativas 
(listas, arreglos o diccionarios simples). Evitar el uso de clases personalizadas o librerías de alto nivel para el procesamiento.



### Funcionalidades
1. Registrar gasto (concepto, monto y categoría).
2. Mostrar el listado de gastos.
3. Calcular el gasto total acumulado.
4. Mostrar el gasto de mayor valor.
5. Mostrar gastos mayores a un monto ingresado por el usuario.
6. Buscar gasto por concepto (búsqueda por subcadena).
### Configuración de credenciales
El programa lee las credenciales de MySQL desde variables de entorno.
En IntelliJ: clic en el ▶ junto a `main` → **Modify Run Configuration** →
**Modify options** → **Environment variables** y agregar:

MYSQL_USER=root;MYSQL_PASSWORD= contraseña

Si MySQL no tiene contraseña (Laragon/XAMPP), no es necesario configurar nada.
