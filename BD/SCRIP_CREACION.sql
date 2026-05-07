-- ============================================================
--  JUEGO DE CARTAS COLECCIONABLE
--  Script 01 — Creación de la base de datos y tablas
--  Motor: MySQL 8.x (XAMPP, puerto 3306)
--  Cadena JDBC: jdbc:mysql://127.0.0.1:3306/juego_cartas
--  Ejecutar ANTES de INSERCION.sql y PROCEDIMIENTOS.sql
-- ============================================================

DROP DATABASE IF EXISTS juego_cartas;
CREATE DATABASE juego_cartas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE juego_cartas;


-- ============================================================
--  1. ELEMENTO
-- ============================================================
CREATE TABLE ELEMENTO (
    id_elemento   INT          NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(20)  NOT NULL UNIQUE,
    descripcion   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_elemento)
);


-- ============================================================
--  2. INTERACCION_ELEMENTO
--  Matriz de multiplicadores de daño entre elementos.
--  El multiplicador se aplica sobre el daño de la carta
--  según el elemento activo del estadio en ese turno.
-- ============================================================
CREATE TABLE INTERACCION_ELEMENTO (
    id                INT           NOT NULL AUTO_INCREMENT,
    id_elem_atacante  INT           NOT NULL,
    id_elem_defensor  INT           NOT NULL,
    multiplicador     DECIMAL(4,2)  NOT NULL DEFAULT 1.00,
    PRIMARY KEY (id),
    UNIQUE KEY uq_interaccion (id_elem_atacante, id_elem_defensor),
    FOREIGN KEY (id_elem_atacante) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT,
    FOREIGN KEY (id_elem_defensor) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  3. ESTADIO
--  id_elemento_inicial: elemento con el que arranca el estadio.
--  id_elemento_activo:  elemento activo durante la partida,
--  puede cambiar con cartas ESTADO (Java lo actualiza).
-- ============================================================
CREATE TABLE ESTADIO (
    id_estadio           INT          NOT NULL AUTO_INCREMENT,
    nombre               VARCHAR(50)  NOT NULL,
    descripcion          VARCHAR(255) NOT NULL,
    id_elemento_inicial  INT          NOT NULL,
    id_elemento_activo   INT          NOT NULL,
    PRIMARY KEY (id_estadio),
    FOREIGN KEY (id_elemento_inicial) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT,
    FOREIGN KEY (id_elemento_activo)  REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  4. CARTA
--  tipo ESTADO: la carta solo cambia el elemento activo del
--  estadio al elemento de la propia carta (daño=0, escudo=0).
--  velocidad: determina el orden de resolución en el turno
--  (mayor velocidad se resuelve primero).
-- ============================================================
CREATE TABLE CARTA (
    id_carta     INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(60)  NOT NULL,
    descripcion  VARCHAR(255) NOT NULL,
    tipo         ENUM('OFENSIVA','DEFENSIVA','ESTADO') NOT NULL,
    id_elemento  INT          NOT NULL,
    coste_mana   INT          NOT NULL,
    daño         INT          NOT NULL DEFAULT 0,
    escudo       INT          NOT NULL DEFAULT 0,
    duracion     INT          NOT NULL DEFAULT 0,
    velocidad    INT          NOT NULL DEFAULT 5,
    rareza       ENUM('COMUN','POCO_COMUN','RARO','EPICO','LEGENDARIO') NOT NULL,
    PRIMARY KEY (id_carta),
    FOREIGN KEY (id_elemento) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  5. JUGADOR
--  apodo: nombre visible en partida.
--  MMR: puntuación global de matchmaking (base 1000).
-- ============================================================
CREATE TABLE JUGADOR (
    id_jugador     INT          NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(50)  NOT NULL,
    apellidos      VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    apodo          VARCHAR(50)  NOT NULL,
    fecha_registro DATE         NOT NULL,
    MMR            INT          NOT NULL DEFAULT 1000,
    PRIMARY KEY (id_jugador)
);


-- ============================================================
--  6. CARTA_JUGADOR
--  Colección de cartas que posee cada jugador.
-- ============================================================
CREATE TABLE CARTA_JUGADOR (
    id_jugador      INT  NOT NULL,
    id_carta        INT  NOT NULL,
    fecha_obtencion DATE NOT NULL,
    PRIMARY KEY (id_jugador, id_carta),
    FOREIGN KEY (id_jugador) REFERENCES JUGADOR(id_jugador) ON DELETE CASCADE,
    FOREIGN KEY (id_carta)   REFERENCES CARTA(id_carta)     ON DELETE RESTRICT
);


-- ============================================================
--  7. MAZO
--  Límite de 10 cartas por mazo gestionado desde Java.
-- ============================================================
CREATE TABLE MAZO (
    id_mazo    INT         NOT NULL AUTO_INCREMENT,
    id_jugador INT         NOT NULL,
    nombre     VARCHAR(60) NOT NULL,
    PRIMARY KEY (id_mazo),
    FOREIGN KEY (id_jugador) REFERENCES JUGADOR(id_jugador) ON DELETE CASCADE
);


-- ============================================================
--  8. MAZO_CARTA
-- ============================================================
CREATE TABLE MAZO_CARTA (
    id_mazo   INT NOT NULL,
    id_carta  INT NOT NULL,
    PRIMARY KEY (id_mazo, id_carta),
    FOREIGN KEY (id_mazo)  REFERENCES MAZO(id_mazo)   ON DELETE CASCADE,
    FOREIGN KEY (id_carta) REFERENCES CARTA(id_carta)  ON DELETE RESTRICT
);


-- ============================================================
--  9. PARTIDA
-- ============================================================
CREATE TABLE PARTIDA (
    id_partida  INT      NOT NULL AUTO_INCREMENT,
    id_jugador1 INT      NOT NULL,
    id_jugador2 INT      NOT NULL,
    id_mazo_j1  INT      NOT NULL,
    id_mazo_j2  INT      NOT NULL,
    id_estadio  INT      NOT NULL,
    fecha       DATETIME NOT NULL,
    id_ganador  INT               DEFAULT NULL,
    num_turnos  INT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id_partida),
    FOREIGN KEY (id_jugador1) REFERENCES JUGADOR(id_jugador)  ON DELETE RESTRICT,
    FOREIGN KEY (id_jugador2) REFERENCES JUGADOR(id_jugador)  ON DELETE RESTRICT,
    FOREIGN KEY (id_mazo_j1)  REFERENCES MAZO(id_mazo)        ON DELETE RESTRICT,
    FOREIGN KEY (id_mazo_j2)  REFERENCES MAZO(id_mazo)        ON DELETE RESTRICT,
    FOREIGN KEY (id_estadio)  REFERENCES ESTADIO(id_estadio)  ON DELETE RESTRICT,
    FOREIGN KEY (id_ganador)  REFERENCES JUGADOR(id_jugador)  ON DELETE RESTRICT
);


-- ============================================================
--  10. TURNO
--  Turnos simultáneos: ambos jugadores eligen cartas y se
--  resuelven ordenadas por velocidad individual.
--  id_jugador_primero: jugador con mayor media de velocidad
--  ese turno (desempate: MMR del jugador).
--  id_elemento_activo: elemento del estadio al cierre del turno.
-- ============================================================
CREATE TABLE TURNO (
    id_turno             INT          NOT NULL AUTO_INCREMENT,
    id_partida           INT          NOT NULL,
    numero_turno         INT          NOT NULL,
    vida_j1              INT          NOT NULL,
    vida_j2              INT          NOT NULL,
    mana_disponible      INT          NOT NULL,
    id_elemento_activo   INT          NOT NULL,
    id_jugador_primero   INT          NOT NULL,
    media_velocidad_j1   DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    media_velocidad_j2   DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id_turno),
    FOREIGN KEY (id_partida)         REFERENCES PARTIDA(id_partida)   ON DELETE CASCADE,
    FOREIGN KEY (id_elemento_activo) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT,
    FOREIGN KEY (id_jugador_primero) REFERENCES JUGADOR(id_jugador)   ON DELETE RESTRICT
);


-- ============================================================
--  11. TURNO_CARTA
--  Registro de cada carta jugada en un turno.
--  orden_resolucion: posición en la que se resolvió la carta
--  dentro del turno (ordenado por velocidad DESC).
--  daño_real: daño final aplicado tras el multiplicador
--  de interacción de elementos.
-- ============================================================
CREATE TABLE TURNO_CARTA (
    id_turno_carta   INT NOT NULL AUTO_INCREMENT,
    id_turno         INT NOT NULL,
    id_jugador       INT NOT NULL,
    id_carta         INT NOT NULL,
    daño_real        INT NOT NULL DEFAULT 0,
    orden_resolucion INT NOT NULL,
    PRIMARY KEY (id_turno_carta),
    FOREIGN KEY (id_turno)   REFERENCES TURNO(id_turno)     ON DELETE CASCADE,
    FOREIGN KEY (id_jugador) REFERENCES JUGADOR(id_jugador)  ON DELETE RESTRICT,
    FOREIGN KEY (id_carta)   REFERENCES CARTA(id_carta)      ON DELETE RESTRICT
);
