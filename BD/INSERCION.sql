-- ============================================================
--  JUEGO DE CARTAS COLECCIONABLE
--  Script 02 — Datos base
--  Motor: MySQL 8.x (XAMPP, puerto 3306)
--  Ejecutar DESPUÉS de SCRIP_CREACION.sql
-- ============================================================

USE juego_cartas;


-- ============================================================
--  1. ELEMENTO
--  Fuego=1, Agua=2, Tierra=3, Aire=4
-- ============================================================
INSERT INTO ELEMENTO (nombre, descripcion) VALUES
('Fuego',  'Elemento agresivo. Ventaja contra Tierra, desventaja contra Agua.'),
('Agua',   'Elemento de control. Ventaja contra Fuego, desventaja contra Tierra.'),
('Tierra', 'Elemento resistente. Ventaja contra Agua, desventaja contra Aire.'),
('Aire',   'Elemento veloz. Ventaja contra Tierra, desventaja contra Fuego.');


-- ============================================================
--  2. INTERACCION_ELEMENTO
--  Fuego=1, Agua=2, Tierra=3, Aire=4
--  Ventajas:    Fuego>Tierra, Agua>Fuego, Tierra>Agua, Aire>Tierra  → x1.25
--  Desventajas: Fuego<Agua,   Agua<Tierra, Tierra<Aire, Aire<Fuego  → x0.75
--  Neutro/mismo elemento                                             → x1.00
-- ============================================================
INSERT INTO INTERACCION_ELEMENTO (id_elem_atacante, id_elem_defensor, multiplicador) VALUES
-- Fuego atacante (1)
(1, 1, 1.00),
(1, 2, 0.75),
(1, 3, 1.25),
(1, 4, 1.00),
-- Agua atacante (2)
(2, 1, 1.25),
(2, 2, 1.00),
(2, 3, 0.75),
(2, 4, 1.00),
-- Tierra atacante (3)
(3, 1, 0.75),
(3, 2, 1.25),
(3, 3, 1.00),
(3, 4, 0.75),
-- Aire atacante (4)
(4, 1, 0.75),
(4, 2, 1.00),
(4, 3, 1.25),
(4, 4, 1.00);


-- ============================================================
--  3. ESTADIO
--  id_elemento_inicial = id_elemento_activo al crear el estadio.
--  Java resetea id_elemento_activo a id_elemento_inicial
--  al iniciar cada nueva partida.
-- ============================================================
INSERT INTO ESTADIO (nombre, descripcion, id_elemento_inicial, id_elemento_activo) VALUES
('Caldera Ígnea',    'Un volcán activo domina el campo. Las brasas flotan en el aire.',    1, 1),
('Abismo Oceánico',  'Las profundidades del mar rodean el campo de batalla.',               2, 2),
('Llanura Telúrica', 'Tierra firme y rocosa que retumba con cada golpe.',                  3, 3),
('Cima Tempestuosa', 'En lo alto de una montaña azotada por vientos huracanados.',         4, 4);


-- ============================================================
--  4. CARTA — 56 cartas en total
--  Columnas: nombre, descripcion, tipo, id_elemento,
--            coste_mana, daño, escudo, duracion, velocidad, rareza
--
--  Escala de velocidad por coste_mana:
--    coste 1 → velocidad 9  |  coste 2 → velocidad 8
--    coste 3 → velocidad 7  |  coste 4 → velocidad 6
--    coste 5 → velocidad 4-5|  coste 6 → velocidad 3
--    coste 7 → velocidad 1
--  Excepción: Ráfaga Cortante (Aire, coste 2) → velocidad 10 (prioridad)
--
--  Cartas ESTADO: daño=0, escudo=0, duracion=0.
--  Al jugarse cambian el elemento activo del estadio
--  al elemento de la propia carta (gestionado desde Java).
-- ============================================================

-- ── FUEGO — OFENSIVAS (7) ────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Lanzallamas',          'Ráfaga de fuego directo al rival.',                        'OFENSIVA', 1, 2, 18, 0, 0, 8, 'COMUN'),
('Bola de Fuego',        'Esfera ígnea que explota al impactar.',                    'OFENSIVA', 1, 3, 22, 0, 0, 7, 'COMUN'),
('Torbellino de Llamas', 'Remolino de fuego.',                                       'OFENSIVA', 1, 4, 28, 0, 0, 6, 'POCO_COMUN'),
('Dragón Joven',         'Cría de dragón hambrienta de batalla.',                    'OFENSIVA', 1, 5, 32, 0, 0, 5, 'RARO'),
('Lluvia de Meteoros',   'Rocas ardientes difíciles de bloquear.',                   'OFENSIVA', 1, 5, 30, 0, 0, 4, 'RARO'),
('Fénix Renacido',       'Ave de fuego que calcina al enemigo.',                     'OFENSIVA', 1, 6, 38, 0, 0, 3, 'EPICO'),
('Señor de las Llamas',  'Encarnación del fuego eterno.',           'OFENSIVA', 1, 7, 50, 0, 0, 1, 'LEGENDARIO');

-- ── FUEGO — DEFENSIVAS (5) ───────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Manto de Brasas',   'Escudo de ascuas que quema al atacante.',           'DEFENSIVA', 1, 2,  0, 14, 1, 8, 'COMUN'),
('Escudo Ígneo',      'Barrera de llamas que dura varios turnos.',         'DEFENSIVA', 1, 3,  0, 20, 2, 7, 'POCO_COMUN'),
('Armadura de Magma', 'Coraza ardiente que absorbe y regenera vida.',      'DEFENSIVA', 1, 5,  0, 30, 2, 5, 'RARO'),
('Coraza del Dragón', 'Escamas de dragón inmunes a reducción de escudo.',  'DEFENSIVA', 1, 5,  0, 32, 3, 4, 'RARO'),
('Égida del Fénix',   'Protección divina del ave inmortal. Se recarga.',   'DEFENSIVA', 1, 7,  0, 48, 3, 1, 'LEGENDARIO');

-- ── FUEGO — ESTADO (2) ───────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Invocar Brasas',    'Una explosión de brasas transforma el estadio en un infierno.',   'ESTADO', 1, 1, 0, 0, 0, 9, 'COMUN'),
('Ritual del Dragón', 'Un antiguo ritual invoca el espíritu del dragón sobre el campo.', 'ESTADO', 1, 2, 0, 0, 0, 8, 'POCO_COMUN');

-- ── AGUA — OFENSIVAS (7) ─────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Chorro de Agua',    'Potente chorro que debilita el escudo rival.',      'OFENSIVA', 2, 2, 18, 0, 0, 8, 'COMUN'),
('Corriente Fría',    'Corriente helada que limita el siguiente turno.',   'OFENSIVA', 2, 3, 20, 0, 0, 7, 'COMUN'),
('Ola Devastadora',   'Muralla de agua que aplasta escudos débiles.',      'OFENSIVA', 2, 4, 27, 0, 0, 6, 'POCO_COMUN'),
('Sirena Guerrera',   'Hipnotiza y hace descartar una carta al rival.',    'OFENSIVA', 2, 5, 30, 0, 0, 5, 'RARO'),
('Kraken Joven',      'Tentáculos que reducen la defensa rival 2 turnos.', 'OFENSIVA', 2, 5, 32, 0, 0, 4, 'RARO'),
('Tifón Oceánico',    'Tormenta que borra efectos de estadio del rival.',  'OFENSIVA', 2, 6, 38, 0, 0, 3, 'EPICO'),
('Leviatán Eterno',   'Bestia que causa daño extra ignorando el escudo.',  'OFENSIVA', 2, 7, 46, 0, 0, 1, 'LEGENDARIO');

-- ── AGUA — DEFENSIVAS (5) ────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Burbuja Protectora', 'Esfera de agua que cura al colocarse.',            'DEFENSIVA', 2, 2, 0, 13, 1, 8, 'COMUN'),
('Muro de Hielo',      'Pared helada que drena maná al que la rompa.',     'DEFENSIVA', 2, 3, 0, 20, 2, 7, 'POCO_COMUN'),
('Coraza Marina',      'Armadura de coral que premia aguantar el turno.',  'DEFENSIVA', 2, 5, 0, 28, 2, 5, 'RARO'),
('Prisión de Escarcha','Cristal de hielo que atrapa el siguiente ataque.', 'DEFENSIVA', 2, 5, 0, 30, 3, 4, 'RARO'),
('Égida del Leviatán', 'El primer ataque no le resta valor al escudo.',    'DEFENSIVA', 2, 7, 0, 46, 3, 1, 'LEGENDARIO');

-- ── AGUA — ESTADO (2) ────────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Bendición Marina',    'El océano responde al llamado e inunda el campo de batalla.',    'ESTADO', 2, 1, 0, 0, 0, 9, 'COMUN'),
('Ritual del Leviatán', 'Las profundidades se elevan transformando el estadio en abismo.','ESTADO', 2, 2, 0, 0, 0, 8, 'POCO_COMUN');

-- ── TIERRA — OFENSIVAS (7) ───────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Pedrada Sísmica',      'Roca que reduce el escudo rival al impactar.',    'OFENSIVA', 3, 2, 20, 0, 0, 8, 'COMUN'),
('Golpe Terrenal',       'Impacto que impide colocar escudos al rival.',    'OFENSIVA', 3, 3, 22, 0, 0, 7, 'COMUN'),
('Avalancha',            'Masa de roca que destruye escudos débiles.',      'OFENSIVA', 3, 4, 28, 0, 0, 6, 'POCO_COMUN'),
('Gólem de Granito',     'Coloso que ataca y regenera vida al jugador.',    'OFENSIVA', 3, 5, 30, 0, 0, 5, 'RARO'),
('Raíz Asesina',         'Raíces que impiden colocar defensas al rival.',   'OFENSIVA', 3, 5, 32, 0, 0, 4, 'RARO'),
('Titán de Piedra',      'Gigante que gana fuerza y cura en su estadio.',   'OFENSIVA', 3, 6, 36, 0, 0, 3, 'EPICO'),
('Dragón de la Montaña', 'Señor de las montañas. Convierte daño en vida.',  'OFENSIVA', 3, 7, 50, 0, 0, 1, 'LEGENDARIO');

-- ── TIERRA — DEFENSIVAS (5) ──────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Muro de Piedra',      'Bloque de granito que reduce el daño no absorbido.', 'DEFENSIVA', 3, 2, 0, 15, 1, 8, 'COMUN'),
('Escudo de Raíces',    'Raíces vivas que forman barrera y curan.',           'DEFENSIVA', 3, 3, 0, 21, 2, 7, 'POCO_COMUN'),
('Armadura de Granito', 'Planchas de granito que devuelven daño.',            'DEFENSIVA', 3, 5, 0, 29, 2, 5, 'RARO'),
('Losa Sísmica',        'Al romperse, causa daño directo al rival.',          'DEFENSIVA', 3, 5, 0, 32, 3, 4, 'RARO'),
('Égida de la Montaña', 'Cada ataque absorbido devuelve daño al rival.',      'DEFENSIVA', 3, 7, 0, 50, 3, 1, 'LEGENDARIO');

-- ── TIERRA — ESTADO (2) ──────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Despertar Sísmico', 'Un terremoto sacude el campo convirtiendo el suelo en roca viva.',  'ESTADO', 3, 1, 0, 0, 0, 9, 'COMUN'),
('Ritual del Gólem',  'Un gólem ancestral emerge y reclama el estadio como tierra firme.', 'ESTADO', 3, 2, 0, 0, 0, 8, 'POCO_COMUN');

-- ── AIRE — OFENSIVAS (7) ─────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Ráfaga Cortante',      'Cuchilla de viento con máxima prioridad de ataque.',      'OFENSIVA', 4, 2, 19, 0, 0, 10, 'COMUN'),
('Torbellino Menor',     'Remolino que roba carta si el rival tiene muchas.',        'OFENSIVA', 4, 3, 21, 0, 0,  7, 'COMUN'),
('Lancero del Viento',   'Lanzas de aire que penetran los escudos.',                'OFENSIVA', 4, 4, 26, 0, 0,  6, 'POCO_COMUN'),
('Tifón Menor',          'Tormenta que elimina completamente el escudo.',           'OFENSIVA', 4, 5, 31, 0, 0,  5, 'RARO'),
('Espectro del Huracán', 'Ser etéreo inmune a efectos de estado enemigos.',         'OFENSIVA', 4, 5, 34, 0, 0,  4, 'RARO'),
('Cóndor Celestial',     'Ave mítica que hace mucho daño al enemigo',               'OFENSIVA', 4, 6, 37, 0, 0,  3, 'EPICO'),
('Señor de los Vientos', 'Deidad que ataca dos veces en estadio Aire.',             'OFENSIVA', 4, 7, 48, 0, 0,  1, 'LEGENDARIO');

-- ── AIRE — DEFENSIVAS (5) ────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Manto de Viento',       'Viento que tiene probabilidad de evadir ataques.',        'DEFENSIVA', 4, 2, 0, 11, 1, 8, 'COMUN'),
('Capa de Niebla',        'Niebla que frena ataques débiles por completo.',          'DEFENSIVA', 4, 3, 0, 19, 2, 7, 'POCO_COMUN'),
('Armadura de Tempestad', 'Tormenta comprimida con alta probabilidad de evasión.',  'DEFENSIVA', 4, 5, 0, 26, 2, 5, 'RARO'),
('Escudo del Huracán',    'Al agotarse o romperse, contraataca al rival.',           'DEFENSIVA', 4, 5, 0, 30, 3, 4, 'RARO'),
('Égida del Cóndor',      'Alta evasión. El daño evadido no afecta al escudo.',     'DEFENSIVA', 4, 7, 0, 44, 3, 1, 'LEGENDARIO');

-- ── AIRE — ESTADO (2) ────────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, daño, escudo, duracion, velocidad, rareza) VALUES
('Llamada del Viento', 'Un vendaval repentino despeja el estadio llenándolo de aire puro.',  'ESTADO', 4, 1, 0, 0, 0, 9, 'COMUN'),
('Invocar Vendaval',   'Una invocación convoca una tormenta que toma el control del campo.', 'ESTADO', 4, 2, 0, 0, 0, 8, 'POCO_COMUN');


-- ============================================================
--  5. JUGADOR — 2 jugadores de ejemplo
-- ============================================================
INSERT INTO JUGADOR (nombre, apellidos, email, apodo, fecha_registro, MMR) VALUES
('Carlos', 'Ruiz Martínez',  'carlos.ruiz@email.com',  'CarlFire',   CURDATE(), 1000),
('Laura',  'García Sánchez', 'laura.garcia@email.com', 'LauraStorm', CURDATE(), 1000);
