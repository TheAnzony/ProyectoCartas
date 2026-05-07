# Base de Datos — Juego de Cartas por Turnos (CardBattle)

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

La base de datos gestiona todos los elementos de un juego de cartas por turnos con sistema de elementos: catálogo de cartas, colecciones de jugadores, construcción de mazos, registro de partidas y sistema de turnos simultáneos.

Se divide en tres bloques funcionales:

- **Bloque de catálogo** — datos fijos que no cambian en uso: `ELEMENTO`, `INTERACCION_ELEMENTO`, `ESTADIO`, `CARTA`
- **Bloque de jugadores** — datos dinámicos generados por la aplicación: `JUGADOR`, `CARTA_JUGADOR`, `MAZO`, `MAZO_CARTA`
- **Bloque de partidas** — historial completo de juego: `PARTIDA`, `TURNO`, `TURNO_CARTA`

---

## 2. Tablas y estructura

### ELEMENTO
Tabla fija con los 4 elementos del juego. No se modifica en uso.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_elemento` | INT AI PK | Identificador |
| `nombre` | VARCHAR(20) UNIQUE | Nombre del elemento |
| `descripcion` | VARCHAR(255) | Descripción del elemento |

> Los IDs son fijos: Fuego=1, Agua=2, Tierra=3, Aire=4. La lógica Java depende de este orden.

---

### INTERACCION_ELEMENTO
Matriz 4×4 que define el multiplicador de daño entre elementos. 16 filas fijas.

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | INT AI PK | Identificador |
| `id_elem_atacante` | INT FK UNIQUE | Elemento de la carta usada |
| `id_elem_defensor` | INT FK UNIQUE | Elemento activo del estadio |
| `multiplicador` | DECIMAL(4,2) | Factor de daño aplicado |

**Multiplicadores definidos:**

| Atacante → Defensor activo | Multiplicador |
|---|---|
| Fuego → Tierra | x1.25 |
| Agua → Fuego | x1.25 |
| Tierra → Agua | x1.25 |
| Aire → Tierra | x1.25 |
| Elemento en desventaja | x0.75 |
| Mismo elemento | x1.00 |

---

### ESTADIO
Los 4 campos de batalla predefinidos.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_estadio` | INT AI PK | Identificador |
| `nombre` | VARCHAR(50) | Nombre del estadio |
| `descripcion` | VARCHAR(255) | Descripción |
| `id_elemento_inicial` | INT FK | Elemento con el que arranca el estadio |
| `id_elemento_activo` | INT FK | Elemento activo en el momento actual |

> Al iniciar cada partida Java resetea `id_elemento_activo` al valor de `id_elemento_inicial`. Durante la partida las cartas ESTADO pueden cambiar `id_elemento_activo`. El TURNO almacena un snapshot del elemento activo al cierre de cada turno.

---

### CARTA
Tabla central del catálogo. 56 cartas fijas distribuidas por elemento y tipo.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_carta` | INT AI PK | Identificador |
| `nombre` | VARCHAR(60) | Nombre de la carta |
| `descripcion` | VARCHAR(255) | Descripción breve |
| `tipo` | ENUM | `OFENSIVA` / `DEFENSIVA` / `ESTADO` |
| `id_elemento` | INT FK | Elemento al que pertenece |
| `coste_mana` | INT | Maná necesario para jugarla |
| `daño` | INT | Daño base (0 en DEFENSIVA y ESTADO) |
| `escudo` | INT | Valor de escudo (0 en OFENSIVA y ESTADO) |
| `duracion` | INT | Turnos que dura el efecto (0 si es instantáneo) |
| `velocidad` | INT | Orden de resolución en el turno (mayor = antes) |
| `rareza` | ENUM | `COMUN` / `POCO_COMUN` / `RARO` / `EPICO` / `LEGENDARIO` |

**Distribución de cartas:**

| Tipo | Fuego | Agua | Tierra | Aire | Total |
|---|---|---|---|---|---|
| OFENSIVA | 7 | 7 | 7 | 7 | 28 |
| DEFENSIVA | 5 | 5 | 5 | 5 | 20 |
| ESTADO | 2 | 2 | 2 | 2 | 8 |
| **Total** | **14** | **14** | **14** | **14** | **56** |

**Escala de velocidad por coste de maná:**

| Coste maná | Velocidad |
|---|---|
| 1 | 9 |
| 2 | 8 (Ráfaga Cortante → 10 por prioridad) |
| 3 | 7 |
| 4 | 6 |
| 5 | 4 – 5 |
| 6 | 3 |
| 7 | 1 |

> Las cartas **ESTADO** cambian el elemento activo del estadio al elemento de la propia carta. Su daño y escudo son siempre 0. La lógica de cambio se gestiona desde Java.

---

### JUGADOR
Registro de jugadores. Generado por la aplicación en uso.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_jugador` | INT AI PK | Identificador |
| `nombre` | VARCHAR(50) | Nombre |
| `apellidos` | VARCHAR(100) | Apellidos |
| `email` | VARCHAR(100) UNIQUE | Email (clave de unicidad) |
| `apodo` | VARCHAR(50) | Nombre visible en partida |
| `fecha_registro` | DATE | Fecha de alta |
| `MMR` | INT DEFAULT 1000 | Puntuación global de matchmaking |

---

### CARTA_JUGADOR
Colección personal de cada jugador.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_jugador` | INT PK FK | Jugador propietario |
| `id_carta` | INT PK FK | Carta obtenida |
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

> `ON DELETE CASCADE` sobre `id_jugador`: si se borra el jugador, sus mazos se borran automáticamente.

---

### MAZO_CARTA
Cartas que componen cada mazo. El límite de 10 cartas se valida en Java.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_mazo` | INT PK FK | Mazo |
| `id_carta` | INT PK FK | Carta incluida |

> `ON DELETE CASCADE` sobre `id_mazo`. `ON DELETE RESTRICT` sobre `id_carta`.

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
| `id_estadio` | INT FK | Estadio donde se juega |
| `fecha` | DATETIME | Fecha y hora de inicio de la partida |
| `id_ganador` | INT NULL FK | Ganador (NULL si está en curso) |
| `num_turnos` | INT DEFAULT 0 | Total de turnos jugados |

---

### TURNO
Cada turno de una partida. Los turnos son **simultáneos**: ambos jugadores eligen cartas al mismo tiempo y se resuelven ordenadas por velocidad.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_turno` | INT AI PK | Identificador |
| `id_partida` | INT FK | Partida a la que pertenece |
| `numero_turno` | INT | Número de turno dentro de la partida |
| `vida_j1` | INT | Vida del jugador 1 al finalizar el turno |
| `vida_j2` | INT | Vida del jugador 2 al finalizar el turno |
| `mana_disponible` | INT | Maná disponible ese turno (calculado por `calcular_mana`) |
| `id_elemento_activo` | INT FK | Elemento activo del estadio al cierre del turno |
| `id_jugador_primero` | INT FK | Jugador con mayor media de velocidad ese turno |
| `media_velocidad_j1` | DECIMAL(5,2) | Media de velocidad de las cartas del jugador 1 |
| `media_velocidad_j2` | DECIMAL(5,2) | Media de velocidad de las cartas del jugador 2 |

> `ON DELETE CASCADE` sobre `id_partida`.

---

### TURNO_CARTA
Cartas jugadas en cada turno, en orden de resolución.

| Campo | Tipo | Descripción |
|---|---|---|
| `id_turno_carta` | INT AI PK | Identificador |
| `id_turno` | INT FK | Turno al que pertenece |
| `id_jugador` | INT FK | Jugador que jugó la carta |
| `id_carta` | INT FK | Carta jugada |
| `daño_real` | INT | Daño final aplicado tras el multiplicador de elemento |
| `orden_resolucion` | INT | Posición de resolución en el turno (por velocidad DESC) |

> `ON DELETE CASCADE` sobre `id_turno`. `ON DELETE RESTRICT` sobre `id_carta`.

---

## 3. Relaciones y restricciones

```
ELEMENTO ──────────────────┬── INTERACCION_ELEMENTO (FK atacante, FK defensor)
                           ├── ESTADIO (FK inicial, FK activo)
                           ├── CARTA (FK id_elemento)
                           └── TURNO (FK id_elemento_activo)

JUGADOR ───────────────────┬── CARTA_JUGADOR (CASCADE)
                           ├── MAZO (CASCADE)
                           ├── PARTIDA (FK jugador1, jugador2, ganador — RESTRICT)
                           └── TURNO (FK id_jugador_primero — RESTRICT)

MAZO ──────────────────────┬── MAZO_CARTA (CASCADE)
                           └── PARTIDA (FK mazo_j1, mazo_j2 — RESTRICT)

CARTA ─────────────────────┬── CARTA_JUGADOR (RESTRICT)
                           ├── MAZO_CARTA (RESTRICT)
                           └── TURNO_CARTA (RESTRICT)

PARTIDA ───────────────────└── TURNO (CASCADE)

TURNO ─────────────────────└── TURNO_CARTA (CASCADE)
```

**Resumen de comportamientos al borrar:**

| Si se borra... | Efecto |
|---|---|
| Un `JUGADOR` | Se borran su `CARTA_JUGADOR` y sus `MAZO` (y por cascada sus `MAZO_CARTA`) |
| Un `MAZO` | Se borran sus `MAZO_CARTA` |
| Una `PARTIDA` | Se borran sus `TURNO` (y por cascada sus `TURNO_CARTA`) |
| Un `TURNO` | Se borran sus `TURNO_CARTA` |
| Una `CARTA` | **Bloqueado** si está en colección, mazo o ha sido jugada |
| Un `ELEMENTO` | **Bloqueado** siempre (tiene cartas, estadios y turnos referenciados) |

---

## 4. Funciones y procedimientos

### `calcular_velocidad_mazo(p_id_mazo INT)`
Devuelve `DECIMAL(5,2)`. Media del campo `velocidad` de las cartas del mazo. Mazos con cartas más rápidas tienen mayor valor y tienen ventaja en los desempates.

---

### `calcular_mana(p_turno_global INT)`
Devuelve `INT`. Escala progresiva de maná según el número de turno:

| Turnos | Maná disponible |
|---|---|
| 1 – 2 | 3 |
| 3 – 4 | 4 |
| 5 – 6 | 5 |
| 7 – 8 | 6 |
| 9+ | 7 (techo) |

---

### `calcular_dano_real(p_id_carta INT, p_id_elemento_activo INT)`
Devuelve `INT`. Aplica el multiplicador de `INTERACCION_ELEMENTO` al `daño` de la carta según el elemento activo del estadio en ese turno. Cartas DEFENSIVA y ESTADO devuelven 0.

---

### `crear_partida(...)`
Inserta la fila en `PARTIDA` con los dos jugadores, sus mazos y el estadio. Devuelve `p_id_partida`. Java resetea `id_elemento_activo` del estadio al `id_elemento_inicial` antes de empezar.

---

### `registrar_turno(...)`
Inserta el registro de un turno completado con vida de ambos jugadores, elemento activo, jugador primero y medias de velocidad. Calcula el maná automáticamente con `calcular_mana`. Devuelve `p_id_turno`.

---

### `registrar_resultado_partida(...)`
Cierra la partida actualizando `id_ganador` y `num_turnos`. Actualiza el MMR de ambos jugadores:
- **Ganador**: +100 MMR
- **Perdedor**: -50 MMR (mínimo 0)

---

## 5. Vistas

### `vista_ranking`
Jugadores ordenados por MMR descendente con posición numerada usando `ROW_NUMBER()`.

### `vista_catalogo_cartas`
Todas las cartas con el nombre del elemento en lugar del `id_elemento`. Incluye `velocidad`, `daño`, `escudo` y `duracion`. Ordenadas por elemento, tipo y coste de maná.

### `vista_historial_partidas`
Partidas con apodos de jugadores, nombres de mazos, estadio, número de turnos, ganador y fecha.

### `vista_coleccion_jugador`
Colección completa de cada jugador con nombre de carta, tipo, elemento, rareza, coste, velocidad y fecha de obtención.

### `vista_estadisticas_elemento`
Por cada elemento: total de cartas, desglose por tipo (ofensivas, defensivas, estado), coste medio, velocidad media, daño medio de ofensivas y escudo medio de defensivas.

---

## 6. Flujo de datos en una partida

```
1. Jugador construye su MAZO (máx. 10 cartas) → inserta en MAZO y MAZO_CARTA
        ↓
2. Se llama a crear_partida() → inserta en PARTIDA
   Java resetea ESTADIO.id_elemento_activo = ESTADIO.id_elemento_inicial
        ↓
3. Por cada turno (simultáneo):
   - Ambos jugadores seleccionan cartas (máx. 1 min 30 s)
   - Java ordena todas las cartas por velocidad DESC
   - Desempate de velocidad → mayor media_velocidad del jugador ese turno
   - Segundo desempate → mayor MMR del jugador
   - Se resuelven las cartas en orden: ofensivas (daño_real con multiplicador),
     defensivas (escudo), estado (cambia ESTADIO.id_elemento_activo)
   - Java verifica fin de partida: vida_j1 <= 0 o vida_j2 <= 0
   - Se llama a registrar_turno() → inserta en TURNO
   - Por cada carta jugada → inserta en TURNO_CARTA
        ↓
4. Al terminar → se llama a registrar_resultado_partida()
   · Actualiza PARTIDA (id_ganador, num_turnos)
   · Actualiza MMR de ganador (+100) y perdedor (-50)
```

---

## 7. Datos iniciales

El script `INSERCION.sql` inicializa únicamente los datos base del sistema:

| Tabla | Registros | Descripción |
|---|---|---|
| `ELEMENTO` | 4 | Fuego, Agua, Tierra, Aire |
| `INTERACCION_ELEMENTO` | 16 | Matriz completa 4×4 de multiplicadores |
| `ESTADIO` | 4 | Los 4 campos de batalla |
| `CARTA` | 56 | Catálogo completo (28 ofensivas, 20 defensivas, 8 estado) |
| `JUGADOR` | 2 | Jugadores de ejemplo para pruebas |

El resto de datos (colecciones, mazos, partidas, turnos) los genera la propia aplicación en uso.

---

## 8. Consejos de uso y mantenimiento

### Orden de ejecución de los scripts
Respetar siempre este orden al importar en phpMyAdmin o MySQL Workbench:

```
SCRIP_CREACION.sql  →  INSERCION.sql  →  PROCEDIMIENTOS.sql
```

Si se ejecutan en otro orden, las foreign keys lanzarán errores.

---

### No modificar los IDs de ELEMENTO
La lógica de multiplicadores usa los IDs de elemento directamente desde `INTERACCION_ELEMENTO`. Los IDs son fijos por diseño:

```
Fuego=1 · Agua=2 · Tierra=3 · Aire=4
```

---

### El límite de 10 cartas por mazo no está en SQL
Es una restricción de negocio que se valida en la capa Java antes de insertar en `MAZO_CARTA`.

---

### Borrar jugadores con partidas registradas
Un jugador que ha participado en una `PARTIDA` **no se puede borrar** por el `ON DELETE RESTRICT`. Para eliminarlo habría que borrar primero sus partidas o hacer un borrado lógico añadiendo un campo `activo BOOLEAN` a `JUGADOR`.

---

### Borrar cartas del catálogo
Las cartas jugadas en alguna partida tienen `ON DELETE RESTRICT` en `TURNO_CARTA`. No se pueden eliminar. Si se necesita retirar una carta del juego, la opción más segura es no mostrarla en la interfaz pero mantenerla en la base de datos.

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

### Uso de las vistas en la aplicación Java

```java
// Catálogo de cartas con filtros:
String sql = "SELECT * FROM vista_catalogo_cartas WHERE elemento = ?";

// Historial de partidas del jugador:
String sql = "SELECT * FROM vista_historial_partidas WHERE jugador1 = ? OR jugador2 = ?";

// Ranking global:
String sql = "SELECT * FROM vista_ranking";
```
