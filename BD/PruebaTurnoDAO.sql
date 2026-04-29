-- ============================================================
--  PruebaTurnoDAO.sql
--  Datos necesarios para probar TurnoDAO
--  Ejecutar DESPUÉS de SCRIP_CREACION.sql e INSERCION.sql
-- ============================================================

USE juego_cartas;

-- ── MAZOS ────────────────────────────────────────────────────
INSERT INTO MAZO (id_jugador, nombre, fecha_creacion) VALUES
(1, 'Mazo Fuego Carlos',  CURDATE()),
(1, 'Mazo Tierra Carlos', CURDATE()),
(2, 'Mazo Agua Laura',    CURDATE()),
(2, 'Mazo Aire Laura',    CURDATE());
-- Carlos → id_mazo 1 y 2
-- Laura  → id_mazo 3 y 4

-- ── PARTIDAS ─────────────────────────────────────────────────
INSERT INTO PARTIDA (id_jugador1, id_jugador2, id_mazo_j1, id_mazo_j2, id_estadio, fecha_inicio, fecha_fin, id_ganador, turnos_totales, velocidad_j1, velocidad_j2) VALUES
(1, 2, 1, 3, 1, '2026-04-20 10:00:00', '2026-04-20 10:07:30', 1, 12, 4.50, 3.80),
(1, 2, 2, 4, 3, '2026-04-21 16:00:00', '2026-04-21 16:09:00', 2, 15, 3.20, 4.10),
(2, 1, 4, 2, 2, '2026-04-22 18:00:00', '2026-04-22 18:05:45', 2, 10, 4.80, 3.50),
(1, 2, 1, 4, 4, '2026-04-29 11:00:00', NULL,                  NULL, 0,  4.50, 4.80);
-- Partidas 1, 2, 3 terminadas. Partida 4 en curso.

-- ── TURNOS ───────────────────────────────────────────────────
-- Turnos de la partida 1 (Carlos gana)
INSERT INTO TURNO (id_partida, id_jugador_activo, numero_turno, mana_disponible, mana_gastado, vida_j1_restante, vida_j2_restante) VALUES
(1, 1, 1,  3, 3,  100, 80),
(1, 2, 2,  3, 2,  100, 80),
(1, 1, 3,  4, 4,  100, 54),
(1, 2, 4,  4, 3,  85,  54),
(1, 1, 5,  5, 5,  85,  26),
(1, 2, 6,  5, 4,  70,  26),
(1, 1, 7,  6, 6,  70,  0);

-- Turnos de la partida 2 (Laura gana)
INSERT INTO TURNO (id_partida, id_jugador_activo, numero_turno, mana_disponible, mana_gastado, vida_j1_restante, vida_j2_restante) VALUES
(2, 2, 1,  3, 3,  100, 100),
(2, 1, 2,  3, 3,  78,  100),
(2, 2, 3,  4, 4,  78,  100),
(2, 1, 4,  4, 2,  45,  100),
(2, 2, 5,  5, 5,  45,  100),
(2, 1, 6,  5, 3,  20,  100),
(2, 2, 7,  6, 6,  0,   100);
