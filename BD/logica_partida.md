# Lógica de Partida — CardBattle

---

## 1. Inicialización de la partida

1. Se seleccionan los dos jugadores y sus mazos respectivos
2. Se selecciona el estadio
3. Se llama al procedimiento `crear_partida()` → devuelve `id_partida`
4. Se resetea `ESTADIO.id_elemento_activo = ESTADIO.id_elemento_inicial`
5. Se inicializa el estado en Java:

```
vida_j1 = 100
vida_j2 = 100
escudo_j1 = 0
escudo_j2 = 0
duracion_escudo_j1 = 0
duracion_escudo_j2 = 0
numero_turno = 1
```

---

## 2. Bucle de turno

Se repite hasta que `vida_j1 <= 0` o `vida_j2 <= 0`.

### 2.1 Inicio del turno
- Calcular `mana_disponible = calcular_mana(numero_turno)`
- Reducir en 1 la duración de los escudos activos
- Si `duracion_escudo <= 0` → el escudo se elimina (`escudo = 0`)

### 2.2 Selección de cartas
- Cada jugador elige qué cartas jugar de su mazo
- Restricción: suma de `coste_mana` de las cartas elegidas ≤ `mana_disponible`
- Tiempo límite: **1 minuto y 30 segundos**
- Si se acaba el tiempo, se juega con las cartas seleccionadas hasta ese momento

### 2.3 Preparación para la resolución
1. Juntar todas las cartas de ambos jugadores en una sola lista
2. Calcular `media_velocidad_j1` y `media_velocidad_j2` (media del campo `velocidad` de sus cartas jugadas ese turno)
3. Determinar `jugador_primero`:
   - Mayor `media_velocidad` → va primero
   - Si empatan → el de mayor `MMR`
4. Ordenar la lista completa por `velocidad DESC`
   - En empate de velocidad entre dos cartas → va primero la del `jugador_primero`
5. Asignar `orden_resolucion` a cada carta (1, 2, 3...)

### 2.4 Resolución de cartas (en orden)

Para cada carta de la lista ordenada:

#### Carta OFENSIVA
- Obtener multiplicador de `INTERACCION_ELEMENTO` cruzando `id_elemento` de la carta con `id_elemento_activo` del estadio
- Calcular `dano_real = ROUND(carta.daño * multiplicador)`
- Aplicar daño al rival:
  - **Con escudo activo:**
    - `escudo_rival -= dano_real`
    - Si `escudo_rival < 0` → el exceso va a la vida: `vida_rival += escudo_rival` y `escudo_rival = 0`
  - **Sin escudo:**
    - `vida_rival -= dano_real`
- Comprobar fin de partida: si `vida_rival <= 0` → **hay ganador, salir del bucle**

#### Carta DEFENSIVA
- `escudo_jugador = carta.escudo`
- `duracion_escudo_jugador = carta.duracion`

#### Carta ESTADO
- `UPDATE ESTADIO SET id_elemento_activo = carta.id_elemento WHERE id_estadio = ?`
- Actualizar variable local en Java: `id_elemento_activo = carta.id_elemento`

### 2.5 Fin del turno — guardar en BD

1. Llamar a `registrar_turno()` con:
   - `id_partida`
   - `numero_turno`
   - `vida_j1`, `vida_j2`
   - `id_elemento_activo` actual
   - `id_jugador_primero`
   - `media_velocidad_j1`, `media_velocidad_j2`
   - Devuelve `id_turno`

2. Por cada carta jugada ese turno → insertar en `TURNO_CARTA`:
   - `id_turno`, `id_jugador`, `id_carta`, `dano_real`, `orden_resolucion`

3. `numero_turno += 1`

4. Volver al paso **2.1**

---

## 3. Fin de la partida

1. Determinar `id_ganador` (el jugador cuyo rival llegó a 0 de vida)
2. Llamar a `registrar_resultado_partida(id_partida, id_ganador, numero_turno - 1)`
   - Actualiza `PARTIDA` con ganador y número de turnos
   - Ganador `+100 MMR` / Perdedor `-50 MMR`
3. Mostrar pantalla de resultado con ganador, turnos jugados y vidas finales

---

## Estado que Java mantiene en memoria durante la partida

| Variable | Descripción |
|---|---|
| `id_partida` | ID de la partida en curso |
| `numero_turno` | Turno actual |
| `vida_j1`, `vida_j2` | Vida actual de cada jugador |
| `escudo_j1`, `escudo_j2` | Escudo activo de cada jugador |
| `duracion_escudo_j1`, `duracion_escudo_j2` | Turnos restantes del escudo |
| `id_elemento_activo` | Elemento activo del estadio (sincronizado con BD) |
| `mana_disponible` | Maná del turno actual (calculado con `calcular_mana`) |

> Todo lo demás (historial de cartas, turnos, daños) va a la BD al cerrar cada turno.
