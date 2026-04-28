-- ============================================================
--  JUEGO DE CARTAS COLECCIONABLE
--  Script 01 — Creación de la base de datos y tablas
--  Motor: MySQL 8.x (XAMPP, puerto 3306)
--  Cadena JDBC: jdbc:mysql://127.0.0.1:3306/juego_cartas
--  Ejecutar ANTES de 02_insertar_datos.sql y 03_procedimientos.sql
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
    color_hex     CHAR(7)      NOT NULL,
    PRIMARY KEY (id_elemento)
);


-- ============================================================
--  2. INTERACCION_ELEMENTO
-- ============================================================
CREATE TABLE INTERACCION_ELEMENTO (
    id_elem_atacante  INT           NOT NULL,
    id_elem_defensor  INT           NOT NULL,
    multiplicador     DECIMAL(4,2)  NOT NULL DEFAULT 1.00,
    PRIMARY KEY (id_elem_atacante, id_elem_defensor),
    FOREIGN KEY (id_elem_atacante) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT,
    FOREIGN KEY (id_elem_defensor) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  3. EFECTO_ESTADO
-- ============================================================
CREATE TABLE EFECTO_ESTADO (
    id_efecto           INT          NOT NULL AUTO_INCREMENT,
    id_elemento         INT          NOT NULL,
    bonus_ataque_pct    INT          NOT NULL DEFAULT 0,
    penalty_ataque_pct  INT          NOT NULL DEFAULT 0,
    duracion_turnos     INT          NOT NULL DEFAULT 1,
    descripcion         VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_efecto),
    FOREIGN KEY (id_elemento) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  4. ESTADIO
-- ============================================================
CREATE TABLE ESTADIO (
    id_estadio          INT          NOT NULL AUTO_INCREMENT,
    nombre              VARCHAR(50)  NOT NULL,
    descripcion         VARCHAR(255) NOT NULL,
    id_elemento_activo  INT          NOT NULL DEFAULT 5,
    PRIMARY KEY (id_estadio),
    FOREIGN KEY (id_elemento_activo) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  5. CARTA
-- ============================================================
CREATE TABLE CARTA (
    id_carta     INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(60)  NOT NULL,
    descripcion  VARCHAR(255) NOT NULL,
    tipo         ENUM('OFENSIVA','DEFENSIVA','ESTADO','NEUTRAL') NOT NULL,
    id_elemento  INT          NOT NULL,
    coste_mana   INT          NOT NULL,
    ataque       INT          NOT NULL DEFAULT 0,
    escudo       INT          NOT NULL DEFAULT 0,
    duracion     INT          NOT NULL DEFAULT 0,
    rareza       ENUM('COMUN','POCO_COMUN','RARA','EPICA','LEGENDARIA') NOT NULL,
    efecto       TEXT         NOT NULL,
    PRIMARY KEY (id_carta),
    FOREIGN KEY (id_elemento) REFERENCES ELEMENTO(id_elemento) ON DELETE RESTRICT
);


-- ============================================================
--  6. JUGADOR
-- ============================================================
CREATE TABLE JUGADOR (
    id_jugador       INT          NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(50)  NOT NULL,
    apellidos        VARCHAR(100) NOT NULL,
    email            VARCHAR(100) NOT NULL UNIQUE,
    fecha_registro   DATE         NOT NULL,
    puntuacion_total INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id_jugador)
);


-- ============================================================
--  7. CARTA_JUGADOR
-- ============================================================
CREATE TABLE CARTA_JUGADOR (
    id_jugador      INT  NOT NULL,
    id_carta        INT  NOT NULL,
    cantidad        INT  NOT NULL DEFAULT 1,
    fecha_obtencion DATE NOT NULL,
    PRIMARY KEY (id_jugador, id_carta),
    FOREIGN KEY (id_jugador) REFERENCES JUGADOR(id_jugador) ON DELETE CASCADE,
    FOREIGN KEY (id_carta)   REFERENCES CARTA(id_carta)     ON DELETE RESTRICT
);


-- ============================================================
--  8. MAZO
-- ============================================================
CREATE TABLE MAZO (
    id_mazo        INT         NOT NULL AUTO_INCREMENT,
    id_jugador     INT         NOT NULL,
    nombre         VARCHAR(60) NOT NULL,
    fecha_creacion DATE        NOT NULL,
    PRIMARY KEY (id_mazo),
    FOREIGN KEY (id_jugador) REFERENCES JUGADOR(id_jugador) ON DELETE CASCADE
);


-- ============================================================
--  9. MAZO_CARTA
--  Límite de 10 cartas por mazo gestionado desde Java
-- ============================================================
CREATE TABLE MAZO_CARTA (
    id_mazo   INT NOT NULL,
    id_carta  INT NOT NULL,
    cantidad  INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id_mazo, id_carta),
    FOREIGN KEY (id_mazo)  REFERENCES MAZO(id_mazo)  ON DELETE CASCADE,
    FOREIGN KEY (id_carta) REFERENCES CARTA(id_carta) ON DELETE RESTRICT
);


-- ============================================================
--  10. PARTIDA
-- ============================================================
CREATE TABLE PARTIDA (
    id_partida     INT          NOT NULL AUTO_INCREMENT,
    id_jugador1    INT          NOT NULL,
    id_jugador2    INT          NOT NULL,
    id_mazo_j1     INT          NOT NULL,
    id_mazo_j2     INT          NOT NULL,
    id_estadio     INT          NOT NULL DEFAULT 1,
    fecha_inicio   DATETIME     NOT NULL,
    fecha_fin      DATETIME              DEFAULT NULL,
    id_ganador     INT                   DEFAULT NULL,
    turnos_totales INT          NOT NULL DEFAULT 0,
    velocidad_j1   DECIMAL(5,2)          DEFAULT NULL,
    velocidad_j2   DECIMAL(5,2)          DEFAULT NULL,
    PRIMARY KEY (id_partida),
    FOREIGN KEY (id_jugador1) REFERENCES JUGADOR(id_jugador) ON DELETE RESTRICT,
    FOREIGN KEY (id_jugador2) REFERENCES JUGADOR(id_jugador) ON DELETE RESTRICT,
    FOREIGN KEY (id_mazo_j1)  REFERENCES MAZO(id_mazo)       ON DELETE RESTRICT,
    FOREIGN KEY (id_mazo_j2)  REFERENCES MAZO(id_mazo)       ON DELETE RESTRICT,
    FOREIGN KEY (id_estadio)  REFERENCES ESTADIO(id_estadio) ON DELETE RESTRICT,
    FOREIGN KEY (id_ganador)  REFERENCES JUGADOR(id_jugador) ON DELETE RESTRICT
);


-- ============================================================
--  11. TURNO
-- ============================================================
CREATE TABLE TURNO (
    id_turno          INT NOT NULL AUTO_INCREMENT,
    id_partida        INT NOT NULL,
    id_jugador_activo INT NOT NULL,
    numero_turno      INT NOT NULL,
    mana_disponible   INT NOT NULL,
    mana_gastado      INT NOT NULL DEFAULT 0,
    vida_j1_restante  INT NOT NULL,
    vida_j2_restante  INT NOT NULL,
    PRIMARY KEY (id_turno),
    FOREIGN KEY (id_partida)        REFERENCES PARTIDA(id_partida) ON DELETE CASCADE,
    FOREIGN KEY (id_jugador_activo) REFERENCES JUGADOR(id_jugador) ON DELETE RESTRICT
);


-- ============================================================
--  12. TURNO_CARTA
-- ============================================================
CREATE TABLE TURNO_CARTA (
    id_turno     INT          NOT NULL,
    id_carta     INT          NOT NULL,
    orden_juego  INT          NOT NULL,
    tipo_accion  ENUM('ESTADO','DEFENSIVA','OFENSIVA','NEUTRAL') NOT NULL,
    dano_causado INT          NOT NULL DEFAULT 0,
    resultado    VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_turno, id_carta, orden_juego),
    FOREIGN KEY (id_turno) REFERENCES TURNO(id_turno)  ON DELETE CASCADE,
    FOREIGN KEY (id_carta) REFERENCES CARTA(id_carta)  ON DELETE RESTRICT
);