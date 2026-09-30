# ProyectoCartas — Juego de cartas coleccionables

Aplicación de escritorio en **Java Swing** con base de datos **MySQL**, desarrollada como **Práctica Final** del primer curso del CFGS de Desarrollo de Aplicaciones Multiplataforma (DAM) en la UCAM.

El jugador crea mazos con cartas de cuatro elementos y se enfrenta en partidas por turnos simultáneos. El resultado depende de la velocidad de cada carta, las ventajas entre elementos y el estadio en el que se juega.

<!-- Añade aquí una captura del menú principal, por ejemplo:
![Menú principal](docs/menu.png)
-->

---

## Funcionalidades

- **Menú principal**: Iniciar partida, Cartas, Mazo e Historial.
- **Catálogo de cartas**: consulta de las 56 cartas con filtros.
- **Gestión de mazos**: crear, editar y eliminar mazos, con un máximo de 10 cartas por mazo.
- **Gestión de jugadores**: alta, edición y borrado. Al eliminar un jugador se borran también sus mazos y partidas.
- **Simulación de partidas**: turnos simultáneos, orden según la velocidad de cada carta, multiplicadores por elemento y cambio del elemento activo del estadio.
- **Historial de partidas** y puntuación **MMR** por jugador (base 1000).

## Mecánicas del juego

### Elementos

Hay cuatro elementos: **Fuego, Agua, Tierra y Aire**. El elemento activo del estadio modifica el daño:

| Relación | Multiplicador |
|---|---|
| Ventaja | ×1.25 |
| Neutral / mismo elemento | ×1.00 |
| Desventaja | ×0.75 |

- Fuego > Tierra · Agua > Fuego · Tierra > Agua · Aire > Tierra
- Fuego < Agua · Agua < Tierra · Tierra < Aire · Aire < Fuego

### Cartas

- **Tipos**: Ofensiva (hace daño), Defensiva (da escudo) y Estado (cambia el elemento activo del estadio).
- **Rarezas**: Común, Poco común, Raro, Épico y Legendario.
- **Atributos**: coste de maná, daño, escudo, duración y velocidad.
- **Velocidad**: la carta más rápida se resuelve primero. A más coste de maná, menos velocidad (coste 1 → velocidad 9; coste 7 → velocidad 1).

### Estadios

Caldera Ígnea (Fuego) · Abismo Oceánico (Agua) · Llanura Telúrica (Tierra) · Cima Tempestuosa (Aire)

---

## Tecnologías

- **Java 21** con **Java Swing** para la interfaz
- **JDBC** con MySQL Connector/J (sin ORM)
- **MySQL 8** (XAMPP)
- **Eclipse IDE**
- **Git / GitHub** para el control de versiones

## Base de datos

La base de datos `juego_cartas` tiene 8 tablas:

`ELEMENTO` · `INTERACCION_ELEMENTO` · `ESTADIO` · `CARTA` · `JUGADOR` · `MAZO` · `MAZO_CARTA` · `PARTIDA`

- Borrado en cascada: `JUGADOR → MAZO → MAZO_CARTA`
- Procedimiento almacenado `eliminar_jugador`: borra al jugador y sus partidas dentro de una transacción, con rollback si algo falla.
- Datos iniciales: 56 cartas, 4 elementos con sus interacciones, 4 estadios y jugadores de ejemplo.

## Estructura del repositorio

```
ProyectoCartas/
├── BD/
│   ├── SCRIP_CREACION.sql   # Creación de tablas y procedimiento
│   └── INSERCION.sql        # Datos iniciales
├── workspace/ProyectoCartas/  # Proyecto Eclipse (código fuente en src/)
├── enunciado.pdf            # Enunciado de la práctica
└── README.md
```

## Cómo ejecutarlo

### Requisitos

- JDK 21
- XAMPP (o MySQL 8) en el puerto 3306
- Eclipse IDE

### Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/TheAnzony/ProyectoCartas.git
   ```
2. Arranca MySQL desde XAMPP.
3. Ejecuta los scripts en este orden (desde phpMyAdmin o la consola de MySQL):
   1. `BD/SCRIP_CREACION.sql`
   2. `BD/INSERCION.sql`
4. En Eclipse: **File → Import → Existing Projects into Workspace** y selecciona `workspace/ProyectoCartas`.
5. Comprueba que `lib/mysql-connector-j-9.7.0.jar` esté en el Build Path.
6. Revisa los datos de conexión en la clase `DBConnect` (URL `jdbc:mysql://127.0.0.1:3306/juego_cartas`, usuario y contraseña).
7. Ejecuta la clase principal.

---

## Autores

| Nombre | GitHub |
|---|---|
| Antonio Nicolás Ruiz | [@TheAnzony](https://github.com/TheAnzony) |
| Jonathan Ortiz Belmar | [@USUARIO_COMPAÑERO](https://https://github.com/BlueJhoon95) |

Estudiantes de DAM en la UCAM.
