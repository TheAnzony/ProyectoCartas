# Proyecto: Juego de Cartas por Turnos — CardBattle

## Enunciado

Se desarrollará una aplicación de escritorio en Java con interfaz gráfica mediante Java Swing, conectada a una base de datos MySQL a través de la librería DBConnect. La aplicación simulará un juego de cartas por turnos entre dos jugadores.

La aplicación contará con un menú principal desde el que se accederá a las siguientes secciones: **Iniciar Partida**, **Cartas**, **Mazo** e **Historial**. La sección de Cartas mostrará el catálogo completo de cartas disponibles con sistema de filtrado. La sección de Mazo permitirá al jugador construir su mazo seleccionando cartas del catálogo. El Historial mostrará un resumen de las partidas disputadas por el jugador.

Una partida enfrenta a dos jugadores en un estadio. Cada jugador dispone de puntos de vida y mana, siendo el objetivo reducir la vida del rival a cero mediante el uso de cartas. El mana máximo disponible por turno aumenta progresivamente con el número de turno global.

Los turnos son simultáneos: ambos jugadores disponen de un tiempo límite para seleccionar sus cartas, tras lo cual todas las cartas jugadas se resuelven ordenadas por su velocidad individual. En caso de empate de velocidad entre cartas, se desempata por la media de velocidad del conjunto de cartas jugadas por cada jugador en ese turno. Como último desempate se utilizará el MMR del jugador.

El juego incorpora un sistema de elementos. Cada carta pertenece a un elemento y cada estadio tiene un elemento activo. Según la interacción entre el elemento de la carta y el elemento activo del estadio, el daño aplicado se verá multiplicado o reducido.

---

## Tablas de la Base de Datos

### Elemento
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| nombre | VARCHAR | Nombre del elemento |
| descripcion | VARCHAR | Descripción del elemento |

### Carta
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| nombre | VARCHAR | Nombre de la carta |
| descripcion | VARCHAR | Descripción de la carta |
| tipo | ENUM | ofensiva / defensiva / estado |
| coste_mana | INT | Mana necesario para jugarla |
| daño | INT | Daño que inflige |
| escudo | INT | Vida que absorbe |
| duracion | INT | Turnos que dura el efecto |
| rareza | ENUM | comun / poco_comun / raro / epico / legendario |
| velocidad | INT | Determina el orden de resolución en el turno |
| elemento_id | INT FK | Elemento al que pertenece la carta |

> Relación: **N:1** con Elemento

### Interaccion_Elemento
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| elemento_atacante_id | INT FK | Elemento de la carta usada |
| elemento_defensor_id | INT FK | Elemento activo del estadio |
| multiplicador | DECIMAL | Factor de daño aplicado (ej. 2.0 o 0.5) |

> Relación: **N:1** con Elemento (dos veces)  
> Define el modificador de daño entre pares de elementos (ej. fuego vs agua → 0.5)

### Estadio
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| nombre | VARCHAR | Nombre del estadio |
| descripcion | VARCHAR | Descripción del estadio |
| elemento_inicial_id | INT FK | Elemento con el que empieza el estadio |
| elemento_activo_id | INT FK | Elemento activo en el momento actual |

> Relación: **N:1** con Elemento (dos veces)

### Jugador
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| nombre | VARCHAR | Nombre del jugador |
| apellidos | VARCHAR | Apellidos del jugador |
| email | VARCHAR | Correo electrónico |
| apodo | VARCHAR | Nombre en juego |
| fecha_registro | DATE | Fecha de alta en el sistema |
| MMR | INT | Puntuación global (matchmaking rating) |

### Mazo
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| nombre | VARCHAR | Nombre del mazo |
| jugador_id | INT FK | Jugador propietario |

> Relación: **N:1** con Jugador

### Mazo_Carta *(tabla intermedia)*
| Campo | Tipo | Descripción |
|---|---|---|
| mazo_id | INT FK | Referencia al mazo |
| carta_id | INT FK | Referencia a la carta |

> Relación: **N:M** entre Mazo y Carta

### Partida
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| jugador1_id | INT FK | Primer jugador |
| jugador2_id | INT FK | Segundo jugador |
| mazo1_id | INT FK | Mazo del jugador 1 |
| mazo2_id | INT FK | Mazo del jugador 2 |
| estadio_id | INT FK | Estadio donde se juega |
| fecha | DATETIME | Fecha y hora de la partida |
| ganador_id | INT FK | Jugador ganador |
| num_turnos | INT | Número total de turnos jugados |

> Relación: **N:1** con Jugador (×3), Mazo (×2), Estadio

### Turno
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| partida_id | INT FK | Partida a la que pertenece |
| numero_turno | INT | Número de turno global en la partida |
| vida_j1 | INT | Vida del jugador 1 al final del turno |
| vida_j2 | INT | Vida del jugador 2 al final del turno |
| mana_disponible | INT | Mana máximo disponible ese turno |
| elemento_activo_id | INT FK | Elemento activo del estadio en ese turno |
| jugador_primero_id | INT FK | Jugador que resolvió primero |
| media_velocidad_j1 | DECIMAL | Media de velocidad de las cartas del jugador 1 |
| media_velocidad_j2 | DECIMAL | Media de velocidad de las cartas del jugador 2 |

> Relación: **N:1** con Partida, Elemento, Jugador

### TurnoCarta
| Campo | Tipo | Descripción |
|---|---|---|
| id | INT PK | Identificador único |
| turno_id | INT FK | Turno al que pertenece |
| jugador_id | INT FK | Jugador que jugó la carta |
| carta_id | INT FK | Carta jugada |
| daño_real | INT | Daño aplicado tras el modificador de elemento |
| orden_resolucion | INT | Posición en la que se resolvió la carta en el turno |

> Relación: **N:1** con Turno, Jugador, Carta

---

## Relaciones entre tablas

```
Elemento ←────────── Carta
Elemento ←────────── Interaccion_Elemento (atacante y defensor)
Elemento ←────────── Estadio (inicial y activo)
Jugador  ←────────── Mazo ←── Mazo_Carta ──→ Carta
Jugador  ←────────── Partida ──→ Mazo, Estadio
Partida  ←────────── Turno ──→ Elemento, Jugador
Turno    ←────────── TurnoCarta ──→ Jugador, Carta
```

---

## Procedimientos y Lógica Destacable

| Procedimiento | Descripción |
|---|---|
| **Calcular mana por turno** | `mana = numero_turno` (o fórmula definida). Determina el límite de cartas jugables en ese turno |
| **Resolver orden de cartas** | Ordena todas las cartas del turno por `velocidad` DESC. Desempate 1: media de velocidad del jugador. Desempate 2: Carta con mayor velocidad elegida aleatoriamente. |
| **Calcular daño real** | Consulta `Interaccion_Elemento` con el elemento de la carta y el elemento activo del estadio para aplicar el multiplicador correspondiente |
| **Actualizar MMR** | Al finalizar la partida, ajusta el MMR de ganador y perdedor según el resultado |
| **Verificar fin de partida** | Tras concluir el turno, comprueba si `vida_j1 <= 0` o `vida_j2 <= 0` para determinar si la partida ha terminado |
| **Tiempo límite de turno** | Cada jugador dispone de 1 minuto y 30 segundos para seleccionar sus cartas antes de que el turno se resuelva automáticamente |
