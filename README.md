# Seguimiento: Paradigmas de Programación – Control de Gastos Personales

Mismo problema resuelto en **5 paradigmas** de programación con **Java**, trabajando en equipo con Git y GitHub. Actividad de seguimiento de Ingeniería de Software II, CUE.

## Problema
Sistema de control de gastos personales con 6 funcionalidades:
1. Registrar gasto (concepto, monto y categoría).
2. Mostrar el listado de gastos.
3. Calcular el gasto total acumulado.
4. Mostrar el gasto de mayor valor.
5. Mostrar gastos mayores a un monto ingresado.
6. Buscar gasto por concepto (búsqueda por subcadena).

## Equipo y paradigmas
| Integrante | Paradigma | Carpeta | Rama |
|---|---|---|---|
| Juan José Sua Gómez | Estructurado | `estructurado/` | `feature/paradigma-estructurado` |
| Marlon Sarria | Orientado a Objetos | `poo/` | `feature/paradigma-poo` |
| Steveen Cardona | Orientado a Eventos | `eventos/` | `feature/paradigma-eventos` |
| Emerson Ángel | Declarativo | `declarativo/` | `feature/paradigma-declarativo` |
| Alejandro Palacios | Funcional | `funcional/` | `feature/paradigma-funcional` |

## Estructura del repositorio
```
├── estructurado/   # Programación estructurada (consola)
├── poo/            # Programación orientada a objetos (consola)
├── eventos/        # Programación orientada a eventos (GUI Swing)
├── declarativo/    # Programación declarativa (SQL con MySQL y JDBC)
├── funcional/      # Programación funcional (streams y lambdas)
├── lib/            # Librerías (.jar) del paradigma declarativo
├── pom.xml
├── .gitignore
└── README.md
```

## Flujo de trabajo (Git)
- `master`: versión estable final (tag `v1.0`).
- `develop`: rama de integración.
- `feature/paradigma-*`: una rama por paradigma, integrada a `develop` mediante Pull Request con aprobación de otro integrante.
- Commits con formato `feat:`, `fix:`, `docs:`, `chore:`.

## Cómo ejecutar
Requisito: JDK 17 o superior. Los comandos se ejecutan **desde la raíz del repositorio** y dejan los archivos compilados en la carpeta `out/`, que está ignorada por Git. Las instrucciones específicas de cada versión están en su sección.

---

## Paradigma Estructurado
**Autor:** Juan José Sua Gómez · **Rama:** `feature/paradigma-estructurado` · **Carpeta:** `estructurado/`

Versión hecha con programación estructurada: control de flujo secuencial y modular con un menú interactivo en consola. No usa clases propias, solo la clase principal que exige Java.

### Elementos del paradigma aplicados
- Variables locales dentro de cada función.
- Condicionales `if / else`.
- Ciclos `while` y `for`.
- Funciones independientes con paso de parámetros: `registrarGasto`, `mostrarGastos`, `calcularTotal`, `mostrarMayorGasto`, `filtrarPorMonto` y `buscarPorConcepto`.
- Menú secuencial interactivo en consola.
- Estructura de datos nativa: `ArrayList`.

### Cómo ejecutarlo
```bash
javac -d out estructurado/ControlGastosEstructurado.java
java -cp out estructurado.ControlGastosEstructurado
```

---

## Paradigma Orientado a Objetos
**Rama:** `feature/paradigma-poo` · **Carpeta:** `poo/`

Versión con clases, objetos y encapsulamiento, con un menú de consola.

### Elementos del paradigma aplicados
- **`Gasto`**: representa un gasto con su concepto, monto y categoría.
- **`GestorGastos`**: administra la colección de objetos `Gasto` (relación de **composición**) con los métodos `agregarGasto`, `listarGastos`, `calcularTotal`, `obtenerGastoMayor`, `filtrarPorMonto` y `buscarPorConcepto`.
- **Encapsulamiento**: la lista de gastos es un atributo `private final` y `listarGastos()` devuelve una copia para proteger la colección interna.
- **`Main`**: menú de consola con las 6 opciones y salir (0).
- Los montos se manejan con `BigDecimal`.

### Cómo ejecutarlo
```bash
javac -d out poo/*.java
java -cp out poo.Main
```

---

## Paradigma Orientado a Eventos
**Rama:** `feature/paradigma-eventos` · **Carpeta:** `eventos/`

Interfaz gráfica con Java Swing cuyo flujo lo dirigen los eventos del usuario.

### Elementos del paradigma aplicados
- Ventana `ParadigmaEventos` (`JFrame`) con botones y campos de entrada.
- Cada botón tiene un `ActionListener` (expresión lambda) que llama a su método controlador:
  - **Registrar Gasto** → `registrarGasto()`
  - **Calcular Total** → `calcularTotal()`
  - **Filtrar por Monto Mínimo** → `filtrarPorMontoMinimo()`
  - **Buscar por Concepto** → `buscarPorConcepto()` (también al pulsar Enter en el campo de búsqueda)
  - **Mostrar Todos** y **Gasto Mayor**
- Los gastos se guardan en una lista en memoria, por lo que se pierden al cerrar la aplicación.

### Cómo ejecutarlo
```bash
javac -d out eventos/ParadigmaEventos.java
java -cp out com.gastos.eventos.ParadigmaEventos
```

---

## Paradigma Declarativo
**Rama:** `feature/paradigma-declarativo` · **Carpeta:** `declarativo/`

Versión que expresa *qué* datos se quieren y delega a MySQL el *cómo* procesarlos, usando JDBC.

### Elementos del paradigma aplicados
- Consultas SQL parametrizadas (`PreparedStatement`) en `ConsultasGastos`:
  - Total: `SUM(monto)`
  - Gasto mayor: subconsulta con `MAX(monto)`
  - Filtro por monto: `WHERE monto > ?`
  - Búsqueda por concepto: `LIKE '%texto%'`
- `ConexionBD` crea la base de datos `control_gastos` y la tabla `gastos` si no existen. El esquema también está en `schema.sql`.
- Dependencias en `lib/`: `mysql-connector-j-8.4.0.jar` y `protobuf-java-3.25.1.jar`.

### Configuración de credenciales
Requiere MySQL o MariaDB en ejecución. La conexión se configura con variables de entorno, sin escribir contraseñas en el código:

| Variable | Valor por defecto |
|---|---|
| `MYSQL_HOST` | `localhost` |
| `MYSQL_PORT` | `3306` |
| `MYSQL_USER` | `root` |
| `MYSQL_PASSWORD` | (vacía) |
| `MYSQL_DATABASE` | `control_gastos` |

Si MySQL no tiene contraseña (Laragon/XAMPP), no hace falta configurar nada. En IntelliJ las variables se definen en *Modify Run Configuration → Modify options → Environment variables*, por ejemplo `MYSQL_USER=root;MYSQL_PASSWORD=tu_clave`.

### Cómo ejecutarlo
En Windows:
```bash
javac -d out -cp "lib/*" declarativo/*.java
java -cp "out;lib/*" declarativo.Main
```
En Linux o macOS, cambia el `;` por `:` en el segundo comando.

---

## Paradigma Funcional
**Rama:** `feature/paradigma-funcional` · **Carpeta:** `funcional/`

Versión basada en funciones puras, inmutabilidad y funciones de orden superior.

### Elementos del paradigma aplicados
- **Inmutabilidad**: `registrarGasto` no modifica la lista recibida, construye una nueva; `listarGastos` devuelve `List.copyOf` y los filtros terminan en `toList()` (lista no modificable).
- **Funciones puras** en la clase `FuncionesGastos` (`final`, sin estado): reciben la lista por parámetro y devuelven un resultado.
- **`map`**: extrae los montos con `Gasto::monto`.
- **`reduce`**: suma los montos con `BigDecimal::add` partiendo de `BigDecimal.ZERO`, sin ciclos ni acumuladores mutables.
- **`filter`** con expresiones lambda: gastos mayores a un monto y búsqueda por concepto.
- **`Optional`**: para el gasto de mayor valor, que puede no existir si no hay gastos.

