-- ============================================================
--  PruebaTurnoCartaDAO.sql
--  Datos necesarios para probar Turno_cartaDAO
--  Ejecutar DESPUÉS de PruebaTurnoDAO.sql
--  IDs de carta usados (AUTO_INCREMENT del INSERCION.sql):
--    1  = Lanzallamas        (OFENSIVA,  Fuego)
--    2  = Bola de Fuego      (OFENSIVA,  Fuego)
--    8  = Manto de Brasas    (DEFENSIVA, Fuego)
--    13 = Chorro de Agua     (OFENSIVA,  Agua)
--    20 = Burbuja Protectora (DEFENSIVA, Agua)
-- ============================================================

USE juego_cartas;

-- ── TURNO 1 — Carlos juega: defensiva + ofensiva ─────────────
INSERT INTO TURNO_CARTA (id_turno, id_carta, orden_juego, tipo_accion, dano_causado, resultado) VALUES
(1, 8,  1, 'DEFENSIVA', 0,  'Escudo de 14 colocado'),
(1, 1,  2, 'OFENSIVA',  18, 'Causa 18 de daño a Laura');

-- ── TURNO 2 — Laura juega: defensiva + ofensiva ──────────────
INSERT INTO TURNO_CARTA (id_turno, id_carta, orden_juego, tipo_accion, dano_causado, resultado) VALUES
(2, 20, 1, 'DEFENSIVA', 0,  'Escudo de 13 colocado'),
(2, 13, 2, 'OFENSIVA',  18, 'Causa 18 de daño a Carlos, escudo absorbe');

-- ── TURNO 3 — Carlos juega dos ofensivas ─────────────────────
INSERT INTO TURNO_CARTA (id_turno, id_carta, orden_juego, tipo_accion, dano_causado, resultado) VALUES
(3, 1,  1, 'OFENSIVA',  18, 'Causa 18 de daño a Laura'),
(3, 2,  2, 'OFENSIVA',  22, 'Causa 22 de daño a Laura');
