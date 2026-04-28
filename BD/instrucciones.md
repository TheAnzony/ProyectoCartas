# Base de Datos — Juego de Cartas Coleccionable

> Motor: MySQL 8.x · Charset: utf8mb4 · Puerto: 3306  
> Cadena JDBC: `jdbc:mysql://127.0.0.1:3306/juego_cartas`

---

## Índice

1. [Visión general](#1-visión-general)
2. [Tablas y estructura](#2-tablas-y-estructura)
3. [Relaciones y restricciones](#3-relaciones-y-restricciones)
4. [Funciones y procedimientos](#4-funciones-y-procedimientos)
5. [Vistas](#5-vistas)
6. [Flujo de datos en una partida](#6-flujo-de-datos-en-una-partida)
7. [Datos iniciales](#7-datos-iniciales)
8. [Consejos de uso y mantenimiento](#8-consejos-de-uso-y-mantenimiento)

---

## 1. Visión general

La base de datos está diseñada para gestionar todos los elementos de un juego de cartas coleccionable por elementos: catálogo de cartas, colecciones de jugadores, construcción de mazos, registro de partidas y sistema de turnos.

Se divide en tres bloques funcionales:

- **Bloque de catálogo** — datos fijos que no cambian en uso: `ELEMENTO`, `INTERACCION_ELEMENTO`, `EFECTO_ESTADO`, `ESTADIO`, `CARTA`
- **Bloque de jugadores** — datos dinámicos generados por la aplicación: `JUGADOR`, `CARTA_JUGADOR`, `MAZO`, `MAZO_CARTA`
- **Bloque de partidas** — historial completo de juego: `PARTIDA`, `TURNO`, `TURNO_CARTA`

---

## 2. Tablas y estructura

### ELEMENTO
Tabla fija con los 5 elementos del juego. No se modifica en uso.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_elemento` | INT AI PK | Identificador |
| `nombre` | VARCHAR(20) UNIQUE | Nombre del elemento |
| `descripcion` | VARCHAR(255) | Descripción del elemento |
| `color_hex` | CHAR(7) | Color representativo (#RRGGBB) |

> Los IDs son fijos: Fuego=1, Agua=2, Tierra=3, Aire=4, Neutral=5. La lógica Java depende de este orden.

---

### INTERACCION_ELEMENTO
Tabla cruzada 5×5 que define el multiplicador de daño entre elementos. 25 filas fijas.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_elem_atacante` | INT PK FK | Elemento que ataca |
| `id_elem_defensor` | INT PK FK | Elemento que defiende |
| `multiplicador` | DECIMAL(4,2) | Factor de daño aplicado |

**Multiplicadores definidos:**

| Atacante → Defensor | Multiplicador |
|---|---|
| Fuego → Tierra | x1.25 |
| Agua → Fuego | x1.25 |
| Tierra → Agua | x1.25 |
| Aire → Tierra | x1.25 |
| Elemento en desventaja | x0.75 |
| Mismo elemento | x1.00 |
| Cualquiera → Neutral | x1.00 |
| Neutral → cualquiera | x1.00 |

---

### EFECTO_ESTADO
Define los 14 efectos de estadio posibles: 3 por cada elemento elemental (duración 2, 3 y 4 turnos) más 2 neutrales (Silencio Elemental).

| Campo | Tipo | Descripción |
|---|---|---|
| `id_efecto` | INT AI PK | Identificador |
| `id_elemento` | INT FK | Elemento al que pertenece |
| `bonus_ataque_pct` | INT | % de bonus de ataque para el elemento dominante |
| `penalty_ataque_pct` | INT | % de penalización para el elemento en desventaja |
| `duracion_turnos` | INT | Turnos que dura el efecto |
| `descripcion` | VARCHAR(255) | Descripción del efecto |

---

### ESTADIO
Los 5 campos de batalla predefinidos. El campo `id_elemento_activo` cambia dinámicamente durante la partida.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_estadio` | INT AI PK | Identificador |
| `nombre` | VARCHAR(50) | Nombre del estadio |
| `descripcion` | VARCHAR(255) | Descripción |
| `id_elemento_activo` | INT FK | Elemento que domina actualmente |

> Las partidas siempre empiezan en **Campo Neutro** (`id_estadio = 1`). El estadio activo lo gestiona Java en tiempo de partida; la columna `id_elemento_activo` refleja el estado persistido.

---

### CARTA
Tabla central del catálogo. 66 cartas fijas distribuidas por elemento y tipo.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_carta` | INT AI PK | Identificador |
| `nombre` | VARCHAR(60) | Nombre de la carta |
| `descripcion` | VARCHAR(255) | Descripción breve |
| `tipo` | ENUM | `OFENSIVA` / `DEFENSIVA` / `ESTADO` / `NEUTRAL` |
| `id_elemento` | INT FK | Elemento al que pertenece |
| `coste_mana` | INT | Maná necesario para jugarla |
| `ataque` | INT | Daño base (0 en no-ofensivas) |
| `escudo` | INT | Valor de escudo (0 en no-defensivas) |
| `duracion` | INT | Turnos que dura el efecto (0 si es instantáneo) |
| `rareza` | ENUM | `COMUN` / `POCO_COMUN` / `RARA` / `EPICA` / `LEGENDARIA` |
| `efecto` | TEXT | Descripción completa del efecto especial |

**Distribución de cartas:**

| Tipo | Fuego | Agua | Tierra | Aire | Neutral | Total |
|---|---|---|---|---|---|---|
| OFENSIVA | 7 | 7 | 7 | 7 | 0 | 28 |
| DEFENSIVA | 5 | 5 | 5 | 5 | 0 | 20 |
| ESTADO | 3 | 3 | 3 | 3 | 0 | 12 |
| NEUTRAL | 0 | 0 | 0 | 0 | 6 | 6 |
| **Total** | **15** | **15** | **15** | **15** | **6** | **66** |

> Las cartas neutrales solo pueden tener `id_elemento = 5` y tipo `NEUTRAL`. Esta restricción se valida en Java.

---

### JUGADOR
Registro de jugadores. Generado por la aplicación en uso.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_jugador` | INT AI PK | Identificador |
| `nombre` | VARCHAR(50) | Nombre |
| `apellidos` | VARCHAR(100) | Apellidos |
| `email` | VARCHAR(100) UNIQUE | Email (clave de unicidad) |
| `fecha_registro` | DATE | Fecha de alta |
| `puntuacion_total` | INT DEFAULT 0 | Puntos acumulados (se suman 100 por victoria) |

---

### CARTA_JUGADOR
Colección personal de cada jugador. Una fila por carta obtenida.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_jugador` | INT PK FK | Jugador propietario |
| `id_carta` | INT PK FK | Carta obtenida |
| `cantidad` | INT DEFAULT 1 | Cantidad en posesión |
| `fecha_obtencion` | DATE | Cuándo la obtuvo |

> `ON DELETE CASCADE` sobre `id_jugador`: si se borra un jugador, su colección se borra automáticamente.

---

### MAZO
Mazos creados por cada jugador. Un jugador puede tener varios mazos.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_mazo` | INT AI PK | Identificador |
| `id_jugador` | INT FK | Propietario |
| `nombre` | VARCHAR(60) | Nombre del mazo |
| `fecha_creacion` | DATE | Fecha de creación |

> `ON DELETE CASCADE` sobre `id_jugador`: si se borra el jugador, sus mazos se borran automáticamente.

---

### MAZO_CARTA
Cartas que componen cada mazo. El límite de 10 cartas se valida en Java, no con constraint SQL.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_mazo` | INT PK FK | Mazo |
| `id_carta` | INT PK FK | Carta incluida |
| `cantidad` | INT DEFAULT 1 | Cantidad de esa carta en el mazo |

> `ON DELETE CASCADE` sobre `id_mazo`: si se borra el mazo, sus entradas en esta tabla desaparecen. `ON DELETE RESTRICT` sobre `id_carta`: no se puede borrar una carta que esté en un mazo.

---

### PARTIDA
Registro de cada partida jugada.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_partida` | INT AI PK | Identificador |
| `id_jugador1` | INT FK | Jugador 1 |
| `id_jugador2` | INT FK | Jugador 2 |
| `id_mazo_j1` | INT FK | Mazo usado por jugador 1 |
| `id_mazo_j2` | INT FK | Mazo usado por jugador 2 |
| `id_estadio` | INT FK DEFAULT 1 | Estadio donde se juega |
| `fecha_inicio` | DATETIME | Inicio de la partida |
| `fecha_fin` | DATETIME NULL | Fin (NULL si está en curso) |
| `id_ganador` | INT NULL FK | Ganador (NULL si está en curso) |
| `turnos_totales` | INT DEFAULT 0 | Total de turnos jugados |
| `velocidad_j1` | DECIMAL(5,2) | Velocidad calculada del mazo J1 |
| `velocidad_j2` | DECIMAL(5,2) | Velocidad calculada del mazo J2 |

> Una partida **en curso** tiene `fecha_fin = NULL` e `id_ganador = NULL`.

---

### TURNO
Cada turno de una partida con el estado de vida y maná en ese momento.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_turno` | INT AI PK | Identificador |
| `id_partida` | INT FK | Partida a la que pertenece |
| `id_jugador_activo` | INT FK | Jugador que actúa en este turno |
| `numero_turno` | INT | Número de turno dentro de la partida |
| `mana_disponible` | INT | Maná disponible al inicio del turno |
| `mana_gastado` | INT | Maná efectivamente gastado |
| `vida_j1_restante` | INT | Vida del jugador 1 al finalizar el turno |
| `vida_j2_restante` | INT | Vida del jugador 2 al finalizar el turno |

> `ON DELETE CASCADE` sobre `id_partida`: si se borra la partida, sus turnos se borran automáticamente.

---

### TURNO_CARTA
Cartas jugadas en cada turno, en orden.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_turno` | INT PK FK | Turno al que pertenece |
| `id_carta` | INT PK FK | Carta jugada |
| `orden_juego` | INT PK | Orden dentro del turno (1, 2, 3...) |
| `tipo_accion` | ENUM | `ESTADO` / `DEFENSIVA` / `OFENSIVA` / `NEUTRAL` |
| `dano_causado` | INT | Daño efectivo causado (0 si no es ofensiva) |
| `resultado` | VARCHAR(100) | Descripción del resultado de la acción |

> `ON DELETE RESTRICT` sobre `id_carta`: no se puede borrar una carta que haya participado en una partida.

---

## 3. Relaciones y restricciones

```
ELEMENTO ──────────────────┬── INTERACCION_ELEMENTO (FK atacante, FK defensor)
                           ├── EFECTO_ESTADO (FK id_elemento)
                           ├── ESTADIO (FK id_elemento_activo)
                           └── CARTA (FK id_elemento)

JUGADOR ───────────────────┬── CARTA_JUGADOR (CASCADE)
                           ├── MAZO (CASCADE)
                           └── PARTIDA (FK jugador1, jugador2, ganador — RESTRICT)

MAZO ──────────────────────┬── MAZO_CARTA (CASCADE)
                           └── PARTIDA (FK mazo_j1, mazo_j2 — RESTRICT)

CARTA ─────────────────────┬── CARTA_JUGADOR (RESTRICT)
                           ├── MAZO_CARTA (RESTRICT)
                           └── TURNO_CARTA (RESTRICT)

PARTIDA ───────────────────└── TURNO (CASCADE)

TURNO ─────────────────────└── TURNO_CARTA (CASCADE)
```

**Resumen de comportamientos al borrar:**

| Si se borra... | Efecto en cascada |
|---|---|
| Un `JUGADOR` | Se borran su `CARTA_JUGADOR` y sus `MAZO` (y por cascada sus `MAZO_CARTA`) |
| Un `MAZO` | Se borran sus `MAZO_CARTA` |
| Una `PARTIDA` | Se borran sus `TURNO` (y por cascada sus `TURNO_CARTA`) |
| Una `CARTA` | **Bloqueado** si está en colección, mazo o ha sido jugada |
| Un `ELEMENTO` | **Bloqueado** siempre (tiene cartas y estadios referenciados) |

---

## 4. Funciones y procedimientos

### `calcular_velocidad_mazo(p_id_mazo INT)`
Devuelve `DECIMAL(5,2)`. Calcula la velocidad de un mazo según la fórmula:

```
velocidad = 7 - AVG(coste_mana de las cartas del mazo)
```

Un mazo con cartas baratas tiene mayor velocidad y va primero.

---

### `calcular_mana(p_turno_global INT)`
Devuelve `INT`. Implementa la escala de progresión de maná según el turno global acumulado de ambos jugadores:

| Turnos globales | Maná disponible |
|---|---|
| 1 - 2 | 3 |
| 3 - 4 | 4 |
| 5 - 6 | 5 |
| 7 - 8 | 6 |
| 9 en adelante | 7 (techo) |

---

### `crear_partida(...)`
Procedimiento que recibe los dos jugadores y sus mazos, calcula velocidades y determina quién va primero:

1. Mayor velocidad → va primero
2. Empate → más cartas de coste 2 en el mazo
3. Empate total → aleatoriedad (`RAND()`)

Inserta la fila en `PARTIDA` y devuelve `p_id_partida` y `p_primer_jugador` como parámetros OUT.

---

### `registrar_resultado_partida(...)`
Actualiza la partida con `fecha_fin`, `id_ganador` y `turnos_totales`, y suma 100 puntos al jugador ganador en la tabla `JUGADOR`.

---

## 5. Vistas

### `vista_ranking`
Jugadores ordenados por puntuación descendente con posición numerada usando `ROW_NUMBER()`.

### `vista_catalogo_cartas`
Todas las cartas con el nombre del elemento en lugar del `id_elemento`. Ordenadas por elemento, tipo y coste de maná.

### `vista_historial_partidas`
Partidas con nombres reales de jugadores, mazos y ganador. Incluye duración en segundos calculada con `TIMESTAMPDIFF`. Las partidas en curso muestran `NULL` en ganador y duración.

### `vista_coleccion_jugador`
Colección completa de cada jugador con el nombre de la carta, tipo, elemento, rareza, coste y fecha de obtención.

---

## 6. Flujo de datos en una partida

```
1. Jugador crea su MAZO (máx. 10 cartas) → inserta en MAZO y MAZO_CARTA
        ↓
2. Se llama a crear_partida() → inserta en PARTIDA con velocidades calculadas
        ↓
3. Por cada turno → inserta en TURNO (vida, maná, jugador activo)
        ↓
4. Por cada carta jugada en el turno → inserta en TURNO_CARTA
        ↓
5. Al terminar → se llama a registrar_resultado_partida()
   · Actualiza PARTIDA (fecha_fin, id_ganador, turnos_totales)
   · Suma 100 puntos en JUGADOR
```

---

## 7. Datos iniciales

El script `02_insertar_datos.sql` inicializa únicamente los datos base del sistema:

| Tabla | Registros | Descripción |
|---|---|---|
| `ELEMENTO` | 5 | Fuego, Agua, Tierra, Aire, Neutral |
| `INTERACCION_ELEMENTO` | 25 | Matriz completa 5×5 de multiplicadores |
| `EFECTO_ESTADO` | 14 | 3 por elemento elemental + 2 neutrales |
| `ESTADIO` | 5 | Los 5 campos de batalla |
| `CARTA` | 66 | Catálogo completo de cartas |
| `JUGADOR` | 2 | Jugadores de ejemplo para pruebas |

El resto de datos (colecciones, mazos, partidas, turnos) los genera la propia aplicación en uso.

---

## 8. Consejos de uso y mantenimiento

### Orden de ejecución de los scripts
Respetar siempre este orden al importar en phpMyAdmin o MySQL Workbench:

```
01_crear_bbdd.sql  →  02_insertar_datos.sql  →  03_procedimientos.sql
```

Si se ejecutan en otro orden, las foreign keys lanzarán errores.

---

### No modificar los IDs de ELEMENTO
La lógica de multiplicadores en Java usa los IDs de elemento directamente desde `INTERACCION_ELEMENTO`. Si se alteran los IDs de la tabla `ELEMENTO`, los multiplicadores dejarán de funcionar correctamente. Los IDs son fijos por diseño:

```
Fuego=1 · Agua=2 · Tierra=3 · Aire=4 · Neutral=5
```

---

### El límite de 10 cartas por mazo no está en SQL
Es una restricción de negocio que se valida en la capa Java antes de insertar en `MAZO_CARTA`. Si se insertan datos directamente en la base de datos saltándose la aplicación, esta restricción no se aplicará automáticamente.

---

### Borrar jugadores con partidas registradas
Un jugador que ha participado en una `PARTIDA` **no se puede borrar** por el `ON DELETE RESTRICT` de las foreign keys. Para eliminarlo habría que borrar primero sus partidas (y por cascada sus turnos), o bien hacer un borrado lógico (añadir un campo `activo BOOLEAN` a la tabla `JUGADOR`).

---

### Borrar cartas del catálogo
Las cartas que han sido jugadas en alguna partida tienen `ON DELETE RESTRICT` en `TURNO_CARTA`. No se pueden eliminar del catálogo. Si se necesita retirar una carta del juego, la opción más segura es no mostrarla en la interfaz pero mantenerla en la base de datos.

---

### Resetear la base de datos completamente
Para volver al estado inicial sin perder la estructura:

```sql
USE juego_cartas;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE TURNO_CARTA;
TRUNCATE TABLE TURNO;
TRUNCATE TABLE PARTIDA;
TRUNCATE TABLE MAZO_CARTA;
TRUNCATE TABLE MAZO;
TRUNCATE TABLE CARTA_JUGADOR;
TRUNCATE TABLE JUGADOR;
SET FOREIGN_KEY_CHECKS = 1;
```

Esto limpia todos los datos de jugadores y partidas pero mantiene el catálogo de cartas, elementos y estadios intacto.

---

### Copias de seguridad
Antes de cualquier cambio estructural en producción, exportar la base de datos completa desde phpMyAdmin (`Exportar → SQL → Incluir estructura y datos`). Guardar los tres scripts SQL originales como referencia.

---

### Uso de las vistas en la aplicación Java
Las vistas están pensadas para usarse directamente desde los DAOs de consulta, evitando joins repetitivos en el código Java:

```java
// En lugar de construir el JOIN manualmente:
String sql = "SELECT * FROM vista_catalogo_cartas WHERE elemento = ?";

// En lugar de calcular la duración en Java:
String sql = "SELECT * FROM vista_historial_partidas ORDER BY fecha_inicio DESC";
```