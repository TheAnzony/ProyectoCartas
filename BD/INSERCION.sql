-- ================================================
-- SCRIPT DE INSERCIÓN - Juego de Cartas Elemental
-- ================================================

USE proyectocartas;

-- ================================================
-- ELEMENTOS
-- ================================================
INSERT INTO ELEMENTO (nombre, descripcion, color_hex) VALUES
('Fuego', 'Elemento de ataque puro, cartas agresivas y de alto daño', '#FF4500'),
('Agua', 'Elemento de control y defensa, cartas que debilitan al rival', '#1E90FF'),
('Tierra', 'Elemento de resistencia, cartas con alta defensa y vida', '#8B4513'),
('Aire', 'Elemento de velocidad, cartas baratas y efectos de estado rápidos', '#87CEEB');

-- ================================================
-- INTERACCIONES ENTRE ELEMENTOS
-- ================================================
-- Fuego vs todos
INSERT INTO INTERACCION_ELEMENTO (id_elem_atacante, id_elem_defensor, multiplicador, descripcion) VALUES
(1, 1, 1.00, 'Fuego contra Fuego, daño normal'),
(1, 2, 0.75, 'Fuego contra Agua, penalización'),
(1, 3, 1.50, 'Fuego contra Tierra, ventaja'),
(1, 4, 1.25, 'Fuego contra Aire, ligera ventaja');

-- Agua vs todos
INSERT INTO INTERACCION_ELEMENTO (id_elem_atacante, id_elem_defensor, multiplicador, descripcion) VALUES
(2, 1, 1.50, 'Agua contra Fuego, ventaja'),
(2, 2, 1.00, 'Agua contra Agua, daño normal'),
(2, 3, 0.75, 'Agua contra Tierra, penalización'),
(2, 4, 1.25, 'Agua contra Aire, ligera ventaja');

-- Tierra vs todos
INSERT INTO INTERACCION_ELEMENTO (id_elem_atacante, id_elem_defensor, multiplicador, descripcion) VALUES
(3, 1, 0.75, 'Tierra contra Fuego, penalización'),
(3, 2, 1.50, 'Tierra contra Agua, ventaja'),
(3, 3, 1.00, 'Tierra contra Tierra, daño normal'),
(3, 4, 1.25, 'Tierra contra Aire, ligera ventaja');

-- Aire vs todos
INSERT INTO INTERACCION_ELEMENTO (id_elem_atacante, id_elem_defensor, multiplicador, descripcion) VALUES
(4, 1, 0.75, 'Aire contra Fuego, penalización'),
(4, 2, 0.75, 'Aire contra Agua, penalización'),
(4, 3, 1.50, 'Aire contra Tierra, ventaja'),
(4, 4, 1.00, 'Aire contra Aire, daño normal');

-- ================================================
-- EFECTOS DE ESTADO
-- ================================================
INSERT INTO EFECTO_ESTADO (nombre, descripcion, id_elemento, duracion_turnos, mod_ataque, mod_defensa) VALUES
('Tormenta de Fuego', 'El estadio en llamas potencia el ataque de Fuego y debilita Agua', 1, 3, 1.30, 0.80),
('Marea Alta', 'El estadio inundado potencia el ataque de Agua y debilita Fuego', 2, 3, 1.30, 0.80),
('Terremoto', 'El estadio agrietado potencia la defensa de Tierra y debilita Aire', 3, 2, 1.10, 1.40),
('Torbellino', 'El estadio ventoso potencia la velocidad de Aire y debilita Tierra', 4, 2, 1.25, 0.85);

-- ================================================
-- ESTADIOS
-- ================================================
INSERT INTO ESTADIO (nombre, descripcion, id_elemento_activo) VALUES
('Volcan Ardiente', 'Un campo de batalla rodeado de lava, favorece las cartas de Fuego', 1),
('Abismo Oceánico', 'Un campo de batalla bajo el mar, favorece las cartas de Agua', 2),
('Caverna de Cristal', 'Un campo de batalla en las profundidades, favorece las cartas de Tierra', 3),
('Cima de las Nubes', 'Un campo de batalla en las alturas, favorece las cartas de Aire', 4);

-- ================================================
-- CARTAS DE FUEGO
-- ================================================
INSERT INTO CARTA (nombre, descripcion, tipo_carta, id_elemento, coste_mana, puntos_vida, ataque, defensa, rareza) VALUES
('Bola de Fuego', 'Lanza una bola de fuego al rival causando daño directo', 'OFENSIVA', 1, 3, 0, 30, 0, 'COMUN'),
('Llamarada', 'Una ráfaga de fuego que quema al rival durante varios turnos', 'OFENSIVA', 1, 5, 0, 50, 0, 'POCO_COMUN'),
('Dragón de Magma', 'Invoca un dragón de lava que arrasa con las defensas rivales', 'OFENSIVA', 1, 7, 0, 80, 0, 'EPICA'),
('Escudo de Brasas', 'Crea una barrera de fuego que absorbe el daño entrante', 'DEFENSIVA', 1, 3, 0, 0, 25, 'COMUN'),
('Armadura Ígnea', 'Una armadura forjada en volcán que otorga alta resistencia al fuego', 'DEFENSIVA', 1, 5, 0, 0, 45, 'POCO_COMUN'),
('Fénix Guardián', 'Invoca un fénix que protege al jugador y renace al ser destruido', 'DEFENSIVA', 1, 7, 0, 0, 70, 'LEGENDARIA'),
('Dominio del Fuego', 'Cambia el estadio a Fuego durante 3 turnos', 'ESTADO', 1, 2, 0, 0, 0, 'COMUN'),
('Erupción Volcánica', 'Cambia el estadio a Fuego potenciado durante 4 turnos', 'ESTADO', 1, 4, 0, 0, 0, 'RARA');

-- ================================================
-- CARTAS DE AGUA
-- ================================================
INSERT INTO CARTA (nombre, descripcion, tipo_carta, id_elemento, coste_mana, puntos_vida, ataque, defensa, rareza) VALUES
('Oleada', 'Una ola de agua que golpea al rival con fuerza moderada', 'OFENSIVA', 2, 3, 0, 28, 0, 'COMUN'),
('Tifón', 'Una tormenta marina que golpea varias veces al rival', 'OFENSIVA', 2, 5, 0, 48, 0, 'POCO_COMUN'),
('Kraken Ancestral', 'Invoca al kraken que destruye cartas defensivas del rival', 'OFENSIVA', 2, 7, 0, 75, 0, 'EPICA'),
('Barrera de Hielo', 'Congela el daño entrante con una barrera de hielo', 'DEFENSIVA', 2, 3, 0, 0, 28, 'COMUN'),
('Escudo de Coral', 'Un escudo de coral que absorbe el daño y devuelve parte al rival', 'DEFENSIVA', 2, 5, 0, 0, 48, 'POCO_COMUN'),
('Muralla del Abismo', 'Una pared de agua abismal casi impenetrable', 'DEFENSIVA', 2, 7, 0, 0, 72, 'LEGENDARIA'),
('Marea Elemental', 'Cambia el estadio a Agua durante 3 turnos', 'ESTADO', 2, 2, 0, 0, 0, 'COMUN'),
('Tsunami', 'Cambia el estadio a Agua y reduce el ataque de todas las cartas de Fuego', 'ESTADO', 2, 4, 0, 0, 0, 'RARA');

-- ================================================
-- CARTAS DE TIERRA
-- ================================================
INSERT INTO CARTA (nombre, descripcion, tipo_carta, id_elemento, coste_mana, puntos_vida, ataque, defensa, rareza) VALUES
('Roca Arrojadiza', 'Lanza una roca al rival causando daño sólido', 'OFENSIVA', 3, 3, 0, 25, 0, 'COMUN'),
('Avalancha', 'Desata una avalancha de rocas aplastando al rival', 'OFENSIVA', 3, 5, 0, 45, 0, 'POCO_COMUN'),
('Coloso de Piedra', 'Invoca un gigante de piedra de ataque devastador', 'OFENSIVA', 3, 7, 0, 70, 0, 'EPICA'),
('Muro de Tierra', 'Levanta un muro de tierra que bloquea el daño', 'DEFENSIVA', 3, 2, 0, 0, 30, 'COMUN'),
('Fortaleza de Roca', 'Una fortaleza de roca maciza con alta resistencia', 'DEFENSIVA', 3, 4, 0, 0, 55, 'POCO_COMUN'),
('Titán de Granito', 'Un titán de granito que absorbe casi cualquier ataque', 'DEFENSIVA', 3, 7, 0, 0, 80, 'LEGENDARIA'),
('Temblor Elemental', 'Cambia el estadio a Tierra durante 3 turnos', 'ESTADO', 3, 2, 0, 0, 0, 'COMUN'),
('Terremoto Mayor', 'Cambia el estadio a Tierra y debilita las cartas de Aire rivales', 'ESTADO', 3, 4, 0, 0, 0, 'RARA');

-- ================================================
-- CARTAS DE AIRE
-- ================================================
INSERT INTO CARTA (nombre, descripcion, tipo_carta, id_elemento, coste_mana, puntos_vida, ataque, defensa, rareza) VALUES
('Ráfaga de Viento', 'Un golpe de viento rápido y certero', 'OFENSIVA', 4, 2, 0, 22, 0, 'COMUN'),
('Tornado', 'Un tornado que arrastra y daña al rival repetidamente', 'OFENSIVA', 4, 5, 0, 44, 0, 'POCO_COMUN'),
('Fénix del Trueno', 'Una criatura de tormenta que ataca con rayos imparables', 'OFENSIVA', 4, 7, 0, 68, 0, 'EPICA'),
('Escudo de Viento', 'Una corriente de aire que desvía los ataques entrantes', 'DEFENSIVA', 4, 2, 0, 0, 22, 'COMUN'),
('Barrera Ciclónica', 'Un ciclón defensivo que rodea al jugador protegiéndole', 'DEFENSIVA', 4, 4, 0, 0, 42, 'POCO_COMUN'),
('Manto de Huracán', 'Una barrera de viento huracanado casi imposible de penetrar', 'DEFENSIVA', 4, 6, 0, 0, 65, 'LEGENDARIA'),
('Brisa Elemental', 'Cambia el estadio a Aire durante 3 turnos', 'ESTADO', 4, 2, 0, 0, 0, 'COMUN'),
('Tormenta Eléctrica', 'Cambia el estadio a Aire y paraliza una carta defensiva rival', 'ESTADO', 4, 4, 0, 0, 0, 'RARA');

-- ================================================
-- JUGADORES
-- ================================================
INSERT INTO JUGADOR (nombre, apellidos, email, fecha_registro, puntuacion_total) VALUES
('Carlos', 'García López', 'carlos.garcia@email.com', '2024-01-15 10:00:00', 1500),
('Laura', 'Martínez Ruiz', 'laura.martinez@email.com', '2024-01-20 11:30:00', 2200),
('Alejandro','Sánchez Torres', 'alex.sanchez@email.com', '2024-02-05 09:15:00', 800),
('María', 'Fernández Gil', 'maria.fernandez@email.com', '2024-02-10 16:45:00', 3100),
('Pablo', 'Jiménez Moreno', 'pablo.jimenez@email.com', '2024-03-01 12:00:00', 450),
('Sofía', 'Romero Castro', 'sofia.romero@email.com', '2024-03-15 18:20:00', 1750);

-- ================================================
-- COLECCIONES DE CARTAS POR JUGADOR
-- ================================================
INSERT INTO CARTA_JUGADOR (id_jugador, id_carta, cantidad) VALUES
(1, 1, 3), (1, 2, 2), (1, 3, 1),
(1, 4, 3), (1, 5, 2), (1, 6, 1),
(1, 7, 3), (1, 8, 1), (1, 9, 2),
(1, 17, 2), (1, 25, 2),
(2, 9, 3), (2, 10, 2), (2, 11, 1),
(2, 12, 3), (2, 13, 2), (2, 14, 1),
(2, 15, 3), (2, 16, 1), (2, 1, 2),
(2, 25, 2), (2, 29, 2),
(3, 17, 3), (3, 18, 2), (3, 19, 1),
(3, 20, 3), (3, 21, 2), (3, 22, 1),
(3, 23, 3), (3, 24, 1), (3, 9, 2),
(3, 1, 2), (3, 25, 2),
(4, 25, 3), (4, 26, 2), (4, 27, 1),
(4, 28, 3), (4, 29, 2), (4, 30, 1),
(4, 31, 3), (4, 32, 1), (4, 1, 2),
(4, 9, 2), (4, 17, 2),
(5, 1, 2), (5, 4, 2), (5, 7, 2),
(5, 9, 2), (5, 12, 2), (5, 15, 2),
(5, 17, 2), (5, 20, 2), (5, 23, 2),
(5, 25, 2), (5, 28, 2), (5, 31, 2),
(6, 2, 2), (6, 5, 2), (6, 8, 1),
(6, 10, 2), (6, 13, 2), (6, 16, 1),
(6, 18, 2), (6, 21, 2), (6, 24, 1),
(6, 26, 2), (6, 29, 2), (6, 32, 1);

-- ================================================
-- MAZOS
-- ================================================
INSERT INTO MAZO (id_jugador, nombre, fecha_creacion) VALUES
(1, 'Furia Ígnea', '2024-03-01 10:00:00'),
(1, 'Defensa Volcánica', '2024-03-10 11:00:00'),
(2, 'Marea Imparable', '2024-03-05 09:00:00'),
(2, 'Control Abismal', '2024-03-12 14:00:00'),
(3, 'Muro Eterno', '2024-03-08 16:00:00'),
(4, 'Tormenta Veloz', '2024-03-20 10:30:00'),
(5, 'Caos Elemental', '2024-04-01 12:00:00'),
(6, 'Equilibrio', '2024-04-05 15:00:00');

-- ================================================
-- CARTAS EN LOS MAZOS
-- ================================================
INSERT INTO MAZO_CARTA (id_mazo, id_carta, cantidad) VALUES
(1, 1, 3), (1, 2, 2), (1, 3, 1), (1, 4, 2), (1, 5, 1), (1, 7, 3), (1, 8, 1), (1, 9, 2), (1, 17, 2), (1, 25, 2), (1, 26, 1),
(2, 4, 3), (2, 5, 2), (2, 6, 1), (2, 1, 2), (2, 7, 3), (2, 8, 1), (2, 20, 2), (2, 21, 1), (2, 28, 2), (2, 29, 2), (2, 23, 1),
(3, 9, 3), (3, 10, 2), (3, 11, 1), (3, 12, 2), (3, 15, 3), (3, 16, 1), (3, 1, 2), (3, 25, 2), (3, 29, 2), (3, 13, 1), (3, 14, 1),
(4, 12, 3), (4, 13, 2), (4, 14, 1), (4, 9, 2), (4, 15, 3), (4, 16, 1), (4, 10, 2), (4, 11, 1), (4, 23, 2), (4, 20, 2), (4, 17, 1),
(5, 20, 3), (5, 21, 2), (5, 22, 1), (5, 17, 3), (5, 23, 3), (5, 24, 1), (5, 18, 2), (5, 19, 1), (5, 9, 2), (5, 12, 1), (5, 1, 1),
(6, 25, 3), (6, 26, 2), (6, 27, 1), (6, 31, 3), (6, 32, 1), (6, 28, 2), (6, 29, 2), (6, 30, 1), (6, 1, 2), (6, 9, 2), (6, 17, 1),
(7, 1, 2), (7, 4, 2), (7, 7, 1), (7, 9, 2), (7, 12, 2), (7, 15, 1), (7, 17, 2), (7, 20, 2), (7, 23, 1), (7, 25, 2), (7, 28, 2), (7, 31, 1),
(8, 2, 2), (8, 5, 1), (8, 8, 1), (8, 10, 2), (8, 13, 1), (8, 16, 1), (8, 18, 2), (8, 21, 1), (8, 24, 1), (8, 26, 2), (8, 29, 1), (8, 32, 1), (8, 7, 1), (8, 15, 1), (8, 23, 1), (8, 31, 1);

-- ================================================
-- PARTIDAS
-- ================================================
INSERT INTO PARTIDA (id_jugador1, id_jugador2, id_mazo_j1, id_mazo_j2, id_estadio, fecha_inicio, fecha_fin, id_ganador, turnos_totales) VALUES
(1, 2, 1, 3, 1, '2024-04-01 10:00:00', '2024-04-01 10:45:00', 1, 18),
(2, 3, 3, 5, 2, '2024-04-02 16:00:00', '2024-04-02 16:50:00', 2, 22),
(4, 1, 6, 2, 4, '2024-04-03 11:00:00', '2024-04-03 11:40:00', 4, 16),
(3, 4, 5, 6, 3, '2024-04-05 18:00:00', '2024-04-05 18:55:00', 4, 24),
(5, 6, 7, 8, 1, '2024-04-06 10:00:00', '2024-04-06 10:35:00', 6, 14),
(1, 4, 1, 6, 2, '2024-04-08 15:00:00', '2024-04-08 15:50:00', 4, 20);

-- ================================================
-- TURNOS 
-- ================================================
INSERT INTO TURNO (id_partida, numero_turno, id_jugador_activo, mana_disponible, mana_gastado, vida_j1_restante, vida_j2_restante) VALUES
(1, 1, 1, 3, 3, 30, 30),
(1, 2, 2, 3, 2, 30, 28),
(1, 3, 1, 3, 3, 30, 20),
(1, 4, 2, 4, 4, 24, 20),
(1, 5, 1, 4, 4, 24, 14),
(1, 6, 2, 4, 3, 18, 14),
(1, 7, 1, 5, 5, 18, 6),
(1, 8, 2, 5, 5, 12, 6),
(1, 9, 1, 5, 3, 12, 0);

-- ================================================
-- TURNO_CARTA
-- ================================================
INSERT INTO TURNO_CARTA (id_turno, id_carta, orden_juego, tipo_accion, dano_causado, resultado) VALUES
(1, 7, 1, 'ESTADO', 0, 'ACTIVADO - Estadio cambiado a Fuego'),
(1, 1, 2, 'OFENSIVA', 30, 'IMPACTO'),
(2, 12, 1, 'DEFENSIVA', 0, 'COLOCADA'),
(2, 9, 2, 'OFENSIVA', 28, 'IMPACTO - Reducido por defensa a 6'),
(3, 2, 1, 'OFENSIVA', 50, 'IMPACTO - Bonus Fuego x1.3 = 65'),
(3, 1, 2, 'OFENSIVA', 30, 'IMPACTO - Bonus Fuego x1.3 = 39'),
(4, 10, 1, 'OFENSIVA', 48, 'IMPACTO - Penalizado en estadio Fuego'),
(4, 9, 2, 'OFENSIVA', 28, 'IMPACTO - Penalizado en estadio Fuego'),
(5, 3, 1, 'OFENSIVA', 80, 'IMPACTO - Bonus Fuego x1.3 = 104'),
(6, 13, 1, 'DEFENSIVA', 0, 'COLOCADA'),
(6, 9, 2, 'OFENSIVA', 28, 'IMPACTO'),
(7, 2, 1, 'OFENSIVA', 50, 'IMPACTO'),
(7, 1, 2, 'OFENSIVA', 30, 'IMPACTO'),
(8, 11, 1, 'OFENSIVA', 75, 'IMPACTO'),
(8, 10, 2, 'OFENSIVA', 48, 'IMPACTO'),
(9, 1, 1, 'OFENSIVA', 30, 'IMPACTO - Victoria de Carlos');