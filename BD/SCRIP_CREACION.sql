-- Active: 1777310493974@@127.0.0.1@3306@mysql
-- ================================================
-- SCRIPT DE CREACIÓN - Juego de Cartas Elemental
-- ================================================

CREATE DATABASE IF NOT EXISTS ProyectoCartas;
USE ProyectoCartas;

-- ================================================
-- TABLA: ELEMENTO
-- ================================================
CREATE TABLE ELEMENTO (
    id_elemento INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(50)  NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    color_hex   VARCHAR(7)   NOT NULL
);

-- ================================================
-- TABLA: CARTA
-- ================================================
CREATE TABLE CARTA (
    id_carta    INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    tipo_carta  ENUM('OFENSIVA', 'DEFENSIVA', 'ESTADO') NOT NULL,
    id_elemento INT          NOT NULL,
    coste_mana  INT          NOT NULL,
    puntos_vida INT          NOT NULL DEFAULT 0,
    ataque      INT          NOT NULL DEFAULT 0,
    defensa     INT          NOT NULL DEFAULT 0,
    rareza      ENUM('COMUN', 'POCO_COMUN', 'RARA', 'EPICA', 'LEGENDARIA') NOT NULL,
    CONSTRAINT fk_carta_elemento FOREIGN KEY (id_elemento)
        REFERENCES ELEMENTO(id_elemento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: EFECTO_ESTADO
-- ================================================
CREATE TABLE EFECTO_ESTADO (
    id_efecto       INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    descripcion     VARCHAR(255) NOT NULL,
    id_elemento     INT          NOT NULL,
    duracion_turnos INT          NOT NULL,
    mod_ataque      DECIMAL(4,2) NOT NULL DEFAULT 1.00,
    mod_defensa     DECIMAL(4,2) NOT NULL DEFAULT 1.00,
    CONSTRAINT fk_efecto_elemento FOREIGN KEY (id_elemento)
        REFERENCES ELEMENTO(id_elemento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: ESTADIO
-- ================================================
CREATE TABLE ESTADIO (
    id_estadio         INT AUTO_INCREMENT PRIMARY KEY,
    nombre             VARCHAR(100) NOT NULL,
    descripcion        VARCHAR(255) NOT NULL,
    id_elemento_activo INT          NOT NULL,
    CONSTRAINT fk_estadio_elemento FOREIGN KEY (id_elemento_activo)
        REFERENCES ELEMENTO(id_elemento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: INTERACCION_ELEMENTO
-- ================================================
CREATE TABLE INTERACCION_ELEMENTO (
    id_elem_atacante INT          NOT NULL,
    id_elem_defensor INT          NOT NULL,
    multiplicador    DECIMAL(4,2) NOT NULL,
    descripcion      VARCHAR(255),
    PRIMARY KEY (id_elem_atacante, id_elem_defensor),
    CONSTRAINT fk_interaccion_atacante FOREIGN KEY (id_elem_atacante)
        REFERENCES ELEMENTO(id_elemento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_interaccion_defensor FOREIGN KEY (id_elem_defensor)
        REFERENCES ELEMENTO(id_elemento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: JUGADOR
-- ================================================
CREATE TABLE JUGADOR (
    id_jugador       INT AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    apellidos        VARCHAR(100) NOT NULL,
    email            VARCHAR(150) NOT NULL UNIQUE,
    fecha_registro   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    puntuacion_total INT          NOT NULL DEFAULT 0
);

-- ================================================
-- TABLA: CARTA_JUGADOR
-- ================================================
CREATE TABLE CARTA_JUGADOR (
    id_jugador      INT      NOT NULL,
    id_carta        INT      NOT NULL,
    fecha_obtencion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cantidad        INT      NOT NULL DEFAULT 1,
    PRIMARY KEY (id_jugador, id_carta),
    CONSTRAINT fk_cj_jugador FOREIGN KEY (id_jugador)
        REFERENCES JUGADOR(id_jugador)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_cj_carta FOREIGN KEY (id_carta)
        REFERENCES CARTA(id_carta)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: MAZO
-- ================================================
CREATE TABLE MAZO (
    id_mazo        INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador     INT          NOT NULL,
    nombre         VARCHAR(100) NOT NULL,
    fecha_creacion DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mazo_jugador FOREIGN KEY (id_jugador)
        REFERENCES JUGADOR(id_jugador)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: MAZO_CARTA
-- ================================================
CREATE TABLE MAZO_CARTA (
    id_mazo  INT NOT NULL,
    id_carta INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id_mazo, id_carta),
    CONSTRAINT fk_mc_mazo FOREIGN KEY (id_mazo)
        REFERENCES MAZO(id_mazo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_mc_carta FOREIGN KEY (id_carta)
        REFERENCES CARTA(id_carta)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: PARTIDA
-- ================================================
CREATE TABLE PARTIDA (
    id_partida     INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador1    INT      NOT NULL,
    id_jugador2    INT      NOT NULL,
    id_mazo_j1     INT      NOT NULL,
    id_mazo_j2     INT      NOT NULL,
    id_estadio     INT      NOT NULL,
    fecha_inicio   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_fin      DATETIME,
    id_ganador     INT,
    turnos_totales INT      NOT NULL DEFAULT 0,
    CONSTRAINT fk_partida_j1 FOREIGN KEY (id_jugador1)
        REFERENCES JUGADOR(id_jugador)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_partida_j2 FOREIGN KEY (id_jugador2)
        REFERENCES JUGADOR(id_jugador)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_partida_mazo_j1 FOREIGN KEY (id_mazo_j1)
        REFERENCES MAZO(id_mazo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_partida_mazo_j2 FOREIGN KEY (id_mazo_j2)
        REFERENCES MAZO(id_mazo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_partida_estadio FOREIGN KEY (id_estadio)
        REFERENCES ESTADIO(id_estadio)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_partida_ganador FOREIGN KEY (id_ganador)
        REFERENCES JUGADOR(id_jugador)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: TURNO
-- ================================================
CREATE TABLE TURNO (
    id_turno          INT AUTO_INCREMENT PRIMARY KEY,
    id_partida        INT NOT NULL,
    numero_turno      INT NOT NULL,
    id_jugador_activo INT NOT NULL,
    mana_disponible   INT NOT NULL,
    mana_gastado      INT NOT NULL DEFAULT 0,
    vida_j1_restante  INT NOT NULL,
    vida_j2_restante  INT NOT NULL,
    CONSTRAINT fk_turno_partida FOREIGN KEY (id_partida)
        REFERENCES PARTIDA(id_partida)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_turno_jugador FOREIGN KEY (id_jugador_activo)
        REFERENCES JUGADOR(id_jugador)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ================================================
-- TABLA: TURNO_CARTA
-- ================================================
CREATE TABLE TURNO_CARTA (
    id_turno     INT          NOT NULL,
    id_carta     INT          NOT NULL,
    orden_juego  INT          NOT NULL,
    tipo_accion  ENUM('ESTADO', 'DEFENSIVA', 'OFENSIVA') NOT NULL,
    dano_causado INT          NOT NULL DEFAULT 0,
    resultado    VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_turno, id_carta, orden_juego),
    CONSTRAINT fk_tc_turno FOREIGN KEY (id_turno)
        REFERENCES TURNO(id_turno)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_tc_carta FOREIGN KEY (id_carta)
        REFERENCES CARTA(id_carta)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);