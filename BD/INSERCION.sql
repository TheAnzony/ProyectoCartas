-- ============================================================
--  JUEGO DE CARTAS COLECCIONABLE
--  Script 02 — Datos base (inicialización por defecto)
--  Motor: MySQL 8.x (XAMPP, puerto 3306)
--  Ejecutar DESPUÉS de 01_crear_bbdd.sql
-- ============================================================

USE juego_cartas;


-- ============================================================
--  1. ELEMENTO
-- ============================================================
INSERT INTO ELEMENTO (nombre, descripcion, color_hex) VALUES
('Fuego',   'Elemento agresivo. Ventaja contra Tierra, desventaja contra Agua.', '#FF4500'),
('Agua',    'Elemento de control. Ventaja contra Fuego, desventaja contra Tierra.', '#1E90FF'),
('Tierra',  'Elemento resistente. Ventaja contra Agua, desventaja contra Aire.', '#228B22'),
('Aire',    'Elemento veloz. Ventaja contra Tierra, desventaja contra Fuego.', '#9370DB'),
('Neutral', 'Sin ventaja ni desventaja elemental. Multiplicador siempre x1.00.', '#808080');


-- ============================================================
--  2. INTERACCION_ELEMENTO
--  Fuego=1, Agua=2, Tierra=3, Aire=4, Neutral=5
-- ============================================================
INSERT INTO INTERACCION_ELEMENTO VALUES
(1,1, 1.00),(1,2, 0.75),(1,3, 1.25),(1,4, 1.00),(1,5, 1.00),
(2,1, 1.25),(2,2, 1.00),(2,3, 0.75),(2,4, 1.00),(2,5, 1.00),
(3,1, 0.75),(3,2, 1.25),(3,3, 1.00),(3,4, 0.75),(3,5, 1.00),
(4,1, 1.00),(4,2, 1.00),(4,3, 1.25),(4,4, 1.00),(4,5, 1.00),
(5,1, 1.00),(5,2, 1.00),(5,3, 1.00),(5,4, 1.00),(5,5, 1.00);


-- ============================================================
--  3. EFECTO_ESTADO
--  14 efectos: 3 por elemento elemental + 2 neutrales
-- ============================================================
INSERT INTO EFECTO_ESTADO (id_elemento, bonus_ataque_pct, penalty_ataque_pct, duracion_turnos, descripcion) VALUES
(1, 20, 10, 2, 'Estadio Fuego estándar: +20% ataque Fuego, -10% ataque Agua durante 2 turnos'),
(1, 20, 10, 3, 'Estadio Fuego avanzado: +20% ataque Fuego, -10% ataque Agua durante 3 turnos'),
(1, 30, 15, 4, 'Estadio Fuego legendario: +30% ataque Fuego, -15% ataque Agua durante 4 turnos'),
(2, 20, 10, 2, 'Estadio Agua estándar: +20% ataque Agua, -10% ataque Fuego durante 2 turnos'),
(2, 20, 10, 3, 'Estadio Agua avanzado: +20% ataque Agua, -10% ataque Fuego durante 3 turnos'),
(2, 30, 15, 4, 'Estadio Agua legendario: +30% ataque Agua, -15% ataque Fuego durante 4 turnos'),
(3, 20, 10, 2, 'Estadio Tierra estándar: +20% ataque Tierra, -10% ataque Aire durante 2 turnos'),
(3, 20, 10, 3, 'Estadio Tierra avanzado: +20% ataque Tierra, -10% ataque Aire durante 3 turnos'),
(3, 30, 15, 4, 'Estadio Tierra legendario: +30% ataque Tierra, -15% ataque Aire durante 4 turnos'),
(4, 20, 10, 2, 'Estadio Aire estándar: +20% ataque Aire, -10% ataque Tierra durante 2 turnos'),
(4, 20, 10, 3, 'Estadio Aire avanzado: +20% ataque Aire, -10% ataque Tierra durante 3 turnos'),
(4, 30, 15, 4, 'Estadio Aire legendario: +30% ataque Aire, -15% ataque Tierra durante 4 turnos'),
(5,  0,  0, 2, 'Silencio Elemental: ningún elemento recibe bonificaciones durante 2 turnos'),
(5,  0,  0, 3, 'Gran Silencio: ningún elemento recibe bonificaciones durante 3 turnos');


-- ============================================================
--  4. ESTADIO
--  Las partidas siempre empiezan en Campo Neutro (id=1)
-- ============================================================
INSERT INTO ESTADIO (nombre, descripcion, id_elemento_activo) VALUES
('Campo Neutro',     'Sin elemento dominante. Ninguna carta recibe bonificaciones.', 5),
('Caldera Ígnea',    'El fuego arde en el campo. Las cartas de Fuego dominan.',      1),
('Abismo Oceánico',  'El agua lo inunda todo. Las cartas de Agua dominan.',          2),
('Llanura Telúrica', 'La tierra vibra. Las cartas de Tierra dominan.',               3),
('Cima Tempestuosa', 'El viento arrasa. Las cartas de Aire dominan.',                4);


-- ============================================================
--  5. CARTA — 66 cartas en total
--  Distribución: 28 OFENSIVA, 20 DEFENSIVA, 12 ESTADO, 6 NEUTRAL
-- ============================================================

-- ── FUEGO — OFENSIVAS (7) ────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Lanzallamas',          'Ráfaga de fuego directo al rival.',                        'OFENSIVA', 1, 2, 18, 0, 0, 'COMUN',      'Causa 18 de daño directo al jugador rival.'),
('Bola de Fuego',        'Esfera ígnea que explota al impactar.',                    'OFENSIVA', 1, 3, 22, 0, 0, 'COMUN',      'Causa 22 de daño. En estadio Fuego, +5 de daño adicional.'),
('Torbellino de Llamas', 'Remolino de fuego que ignora parte del escudo.',           'OFENSIVA', 1, 4, 28, 0, 0, 'POCO_COMUN', 'Causa 28 de daño. Ignora 8 puntos del escudo defensivo rival.'),
('Dragón Joven',         'Cría de dragón hambrienta de batalla.',                    'OFENSIVA', 1, 5, 32, 0, 0, 'RARA',       'Causa 32 de daño. Si la vida rival queda por debajo de 40, robas 1 carta adicional.'),
('Lluvia de Meteoros',   'Rocas ardientes que no pueden bloquearse fácilmente.',     'OFENSIVA', 1, 5, 30, 0, 0, 'RARA',       'Causa 30 de daño. No puede ser bloqueado por escudos de valor igual o inferior a 12.'),
('Fénix Renacido',       'Ave de fuego que potencia el siguiente ataque.',            'OFENSIVA', 1, 6, 38, 0, 0, 'EPICA',      'Causa 38 de daño. La siguiente carta ofensiva de Fuego jugada este turno cuesta 2 maná menos.'),
('Señor de las Llamas',  'Encarnación del fuego eterno. Aplica quemadura.',           'OFENSIVA', 1, 7, 50, 0, 0, 'LEGENDARIA', 'Causa 50 de daño. Aplica quemadura: el rival pierde 6 de vida al inicio de sus próximos 2 turnos.');

-- ── FUEGO — DEFENSIVAS (5) ───────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Manto de Brasas',   'Escudo de ascuas que quema al atacante.',           'DEFENSIVA', 1, 2, 0, 14, 1, 'COMUN',      'Otorga 14 de escudo (1 turno). Si absorbe daño, el rival recibe 3 de quemadura.'),
('Escudo Ígneo',      'Barrera de llamas que dura varios turnos.',         'DEFENSIVA', 1, 3, 0, 20, 2, 'POCO_COMUN', 'Otorga 20 de escudo (2 turnos). En estadio Fuego, el escudo sube a 26.'),
('Armadura de Magma', 'Coraza ardiente que absorbe y regenera vida.',      'DEFENSIVA', 1, 5, 0, 30, 2, 'RARA',       'Otorga 30 de escudo (2 turnos). Recuperas 6 puntos de vida al colocarlo.'),
('Coraza del Dragón', 'Escamas de dragón inmunes a reducción de escudo.',  'DEFENSIVA', 1, 5, 0, 32, 3, 'RARA',       'Otorga 32 de escudo (3 turnos). Inmune a efectos que reduzcan el valor del escudo.'),
('Égida del Fénix',   'Protección divina del ave inmortal. Se recarga.',   'DEFENSIVA', 1, 7, 0, 48, 3, 'LEGENDARIA', 'Otorga 48 de escudo (3 turnos). Si tu vida cae por debajo de 20, el escudo se recarga una vez a la mitad de su valor.');

-- ── FUEGO — ESTADO (3) ───────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Hoguera del Alba',   'El fuego del amanecer domina el estadio.',            'ESTADO', 1, 3, 0, 0, 2, 'POCO_COMUN', 'Activa estadio Fuego durante 2 turnos. Cartas de Fuego +20% ataque. Cartas de Agua -10% ataque.'),
('Corazón del Volcán', 'El estadio arde. El rival no puede cambiarlo.',       'ESTADO', 1, 5, 0, 0, 3, 'RARA',       'Activa estadio Fuego durante 3 turnos. Cartas de Fuego +20% ataque. El rival no puede cambiar el estadio este turno.'),
('Apocalipsis Ígneo',  'El fuego lo consume todo. Destruye escudos rivales.', 'ESTADO', 1, 7, 0, 0, 4, 'LEGENDARIA', 'Activa estadio Fuego durante 4 turnos. Cartas de Fuego +30% ataque. Al activarse, destruye todos los escudos del rival.');

-- ── AGUA — OFENSIVAS (7) ─────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Chorro de Agua',    'Potente chorro que debilita el escudo rival.',      'OFENSIVA', 2, 2, 18, 0, 0, 'COMUN',      'Causa 18 de daño. Reduce en 4 el escudo activo del rival.'),
('Corriente Fría',    'Corriente helada que bloquea el siguiente turno.',  'OFENSIVA', 2, 3, 20, 0, 0, 'COMUN',      'Causa 20 de daño. El rival no puede jugar cartas ofensivas en su próximo turno.'),
('Ola Devastadora',   'Muralla de agua que aplasta escudos débiles.',      'OFENSIVA', 2, 4, 27, 0, 0, 'POCO_COMUN', 'Causa 27 de daño. Destruye el escudo rival si su valor es igual o inferior a 12.'),
('Sirena Guerrera',   'Hipnotiza y hace descartar una carta al rival.',    'OFENSIVA', 2, 5, 30, 0, 0, 'RARA',       'Causa 30 de daño. El rival descarta 1 carta aleatoria de su mano.'),
('Kraken Joven',      'Tentáculos que reducen la defensa rival 2 turnos.', 'OFENSIVA', 2, 5, 32, 0, 0, 'RARA',       'Causa 32 de daño. Reduce en 10 el escudo defensivo rival durante 2 turnos.'),
('Tifón Oceánico',    'Tormenta que borra efectos de estadio del rival.',  'OFENSIVA', 2, 6, 38, 0, 0, 'EPICA',      'Causa 38 de daño. Elimina todos los efectos de estadio positivos activos del rival.'),
('Leviatán Eterno',   'Bestia que causa daño extra ignorando el escudo.',  'OFENSIVA', 2, 7, 46, 0, 0, 'LEGENDARIA', 'Causa 46 de daño. Además causa 8 de daño directo que ignora completamente el escudo rival.');

-- ── AGUA — DEFENSIVAS (5) ────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Burbuja Protectora', 'Esfera de agua que cura al colocarse.',            'DEFENSIVA', 2, 2, 0, 13, 1, 'COMUN',      'Otorga 13 de escudo (1 turno). Recuperas 4 puntos de vida al colocarlo.'),
('Muro de Hielo',      'Pared helada que drena maná al que la rompa.',     'DEFENSIVA', 2, 3, 0, 20, 2, 'POCO_COMUN', 'Otorga 20 de escudo (2 turnos). El rival que lo rompa pierde 1 de maná en su próximo turno.'),
('Coraza Marina',      'Armadura de coral que premia aguantar el turno.',  'DEFENSIVA', 2, 5, 0, 28, 2, 'RARA',       'Otorga 28 de escudo (2 turnos). Si aguanta un turno completo sin romperse, ganas 1 maná extra.'),
('Prisión de Escarcha','Cristal de hielo que atrapa el siguiente ataque.', 'DEFENSIVA', 2, 5, 0, 30, 3, 'RARA',       'Otorga 30 de escudo (3 turnos). Si el rival ataca y no rompe el escudo, no puede volver a atacar ese turno.'),
('Égida del Leviatán', 'El primer ataque no le resta valor al escudo.',    'DEFENSIVA', 2, 7, 0, 46, 3, 'LEGENDARIA', 'Otorga 46 de escudo (3 turnos). El primer ataque recibido es absorbido completamente sin restar valor al escudo.');

-- ── AGUA — ESTADO (3) ────────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Lluvia Torrencial',  'Tormenta que activa el estadio Agua y cura.',     'ESTADO', 2, 3, 0, 0, 2, 'POCO_COMUN', 'Activa estadio Agua durante 2 turnos. Cartas de Agua +20% ataque. Recuperas 4 puntos de vida al activarlo.'),
('Inundación',         'El agua domina. El rival pierde 1 maná.',         'ESTADO', 2, 5, 0, 0, 3, 'RARA',       'Activa estadio Agua durante 3 turnos. Cartas de Agua +20% ataque. El rival pierde 1 maná en su próximo turno.'),
('Diluvio Primordial', 'Cancela el estadio rival y causa daño directo.',  'ESTADO', 2, 7, 0, 0, 4, 'LEGENDARIA', 'Activa estadio Agua durante 4 turnos. Cartas de Agua +30% ataque. Cancela el estadio rival activo y le causa 5 de daño.');

-- ── TIERRA — OFENSIVAS (7) ───────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Pedrada Sísmica',      'Roca que reduce el escudo rival al impactar.',    'OFENSIVA', 3, 2, 20, 0, 0, 'COMUN',      'Causa 20 de daño. Si el rival tiene escudo activo, lo reduce en 5 adicionales.'),
('Golpe Terrenal',       'Impacto que impide colocar escudos al rival.',    'OFENSIVA', 3, 3, 22, 0, 0, 'COMUN',      'Causa 22 de daño. En estadio Tierra, el rival no puede colocar escudos el siguiente turno.'),
('Avalancha',            'Masa de roca que destruye escudos débiles.',      'OFENSIVA', 3, 4, 28, 0, 0, 'POCO_COMUN', 'Causa 28 de daño. Ignora por completo el escudo rival si su valor es igual o inferior a 15.'),
('Gólem de Granito',     'Coloso que ataca y regenera vida al jugador.',    'OFENSIVA', 3, 5, 30, 0, 0, 'RARA',       'Causa 30 de daño. Recuperas 8 puntos de vida al jugar esta carta.'),
('Raíz Asesina',         'Raíces que impiden colocar defensas al rival.',   'OFENSIVA', 3, 5, 32, 0, 0, 'RARA',       'Causa 32 de daño. El rival no puede colocar cartas defensivas en su próximo turno.'),
('Titán de Piedra',      'Gigante que gana fuerza y cura en su estadio.',   'OFENSIVA', 3, 6, 36, 0, 0, 'EPICA',      'Causa 36 de daño. En estadio Tierra, +8 de daño adicional y recuperas 6 de vida.'),
('Dragón de la Montaña', 'Señor de las montañas. Convierte daño en vida.',  'OFENSIVA', 3, 7, 50, 0, 0, 'LEGENDARIA', 'Causa 50 de daño. El 25% del daño causado se convierte en vida recuperada para ti.');

-- ── TIERRA — DEFENSIVAS (5) ──────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Muro de Piedra',      'Bloque de granito que reduce el daño no absorbido.', 'DEFENSIVA', 3, 2, 0, 15, 1, 'COMUN',      'Otorga 15 de escudo (1 turno). El daño no absorbido se reduce un 10% adicional.'),
('Escudo de Raíces',    'Raíces vivas que forman barrera y curan.',           'DEFENSIVA', 3, 3, 0, 21, 2, 'POCO_COMUN', 'Otorga 21 de escudo (2 turnos). Recuperas 5 puntos de vida al colocarlo.'),
('Armadura de Granito', 'Planchas de granito que devuelven daño.',            'DEFENSIVA', 3, 5, 0, 29, 2, 'RARA',       'Otorga 29 de escudo (2 turnos). Devuelve el 20% del daño absorbido al atacante.'),
('Losa Sísmica',        'Al romperse, causa daño directo al rival.',          'DEFENSIVA', 3, 5, 0, 32, 3, 'RARA',       'Otorga 32 de escudo (3 turnos). Al romperse, causa 6 de daño directo al rival.'),
('Égida de la Montaña', 'Cada ataque absorbido devuelve 5 al rival.',         'DEFENSIVA', 3, 7, 0, 50, 3, 'LEGENDARIA', 'Otorga 50 de escudo (3 turnos). Cada ataque absorbido devuelve 5 de daño directo al rival.');

-- ── TIERRA — ESTADO (3) ──────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Raíces Ancestrales', 'Las raíces más antiguas dominan y curan.',          'ESTADO', 3, 3, 0, 0, 2, 'POCO_COMUN', 'Activa estadio Tierra durante 2 turnos. Cartas de Tierra +20% ataque. Recuperas 5 puntos de vida al activarlo.'),
('Dominio Telúrico',   'La tierra domina el campo y regenera cada turno.',  'ESTADO', 3, 5, 0, 0, 3, 'RARA',       'Activa estadio Tierra durante 3 turnos. Cartas de Tierra +20% ataque. Cada turno que persiste recuperas 4 de vida.'),
('Era de Piedra',      'Reduce los escudos del rival a la mitad.',          'ESTADO', 3, 7, 0, 0, 4, 'LEGENDARIA', 'Activa estadio Tierra durante 4 turnos. Cartas de Tierra +30% ataque. Los escudos del rival quedan reducidos a la mitad al activarse.');

-- ── AIRE — OFENSIVAS (7) ─────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Ráfaga Cortante',      'Cuchilla de viento con prioridad de ataque.',       'OFENSIVA', 4, 2, 19, 0, 0, 'COMUN',      'Causa 19 de daño. Tiene prioridad: se resuelve antes que cualquier otra ofensiva del turno.'),
('Torbellino Menor',     'Remolino que roba carta si el rival tiene muchas.', 'OFENSIVA', 4, 3, 21, 0, 0, 'COMUN',      'Causa 21 de daño. Si el rival tiene más de 3 cartas en mano, descarta 1 aleatoriamente.'),
('Lancero del Viento',   'Lanzas de aire que penetran los escudos.',          'OFENSIVA', 4, 4, 26, 0, 0, 'POCO_COMUN', 'Causa 26 de daño. Penetra escudos: aplica el 60% del daño aunque haya escudo activo.'),
('Tifón Menor',          'Tormenta que elimina completamente el escudo.',     'OFENSIVA', 4, 5, 31, 0, 0, 'RARA',       'Causa 31 de daño. Elimina el escudo defensivo activo del rival devolviéndolo a 0.'),
('Espectro del Huracán', 'Ser etéreo inmune a efectos de estado enemigos.',   'OFENSIVA', 4, 5, 34, 0, 0, 'RARA',       'Causa 34 de daño. Los efectos de estado enemigos no afectan a esta carta el turno en que se juega.'),
('Cóndor Celestial',     'Ave mítica que hace descartar 2 cartas al rival.',  'OFENSIVA', 4, 6, 37, 0, 0, 'EPICA',      'Causa 37 de daño. El rival descarta 2 cartas aleatorias de su mano.'),
('Señor de los Vientos', 'Deidad que ataca dos veces en estadio Aire.',       'OFENSIVA', 4, 7, 48, 0, 0, 'LEGENDARIA', 'Causa 48 de daño. En estadio Aire, repite el ataque una segunda vez por 22 de daño adicional.');

-- ── AIRE — DEFENSIVAS (5) ────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Manto de Viento',       'Viento que tiene probabilidad de evadir ataques.',      'DEFENSIVA', 4, 2, 0, 11, 1, 'COMUN',      'Otorga 11 de escudo (1 turno). 20% de probabilidad de evadir completamente el ataque.'),
('Capa de Niebla',        'Niebla que frena ataques débiles por completo.',        'DEFENSIVA', 4, 3, 0, 19, 2, 'POCO_COMUN', 'Otorga 19 de escudo (2 turnos). Ataques con valor igual o inferior a 18 no penetran este escudo.'),
('Armadura de Tempestad', 'Tormenta comprimida con alta probabilidad de evasión.', 'DEFENSIVA', 4, 5, 0, 26, 2, 'RARA',       'Otorga 26 de escudo (2 turnos). 30% de probabilidad de evadir completamente cualquier ataque.'),
('Escudo del Huracán',    'Al agotarse o romperse, contraataca al rival.',         'DEFENSIVA', 4, 5, 0, 30, 3, 'RARA',       'Otorga 30 de escudo (3 turnos). Al agotarse o romperse, causa 8 de daño directo al rival.'),
('Égida del Cóndor',      'Alta evasión. El daño evadido no afecta al escudo.',   'DEFENSIVA', 4, 7, 0, 44, 3, 'LEGENDARIA', 'Otorga 44 de escudo (3 turnos). 40% de evasión. El daño evadido no se aplica al escudo ni al jugador.');

-- ── AIRE — ESTADO (3) ────────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Viento del Norte',   'Viento gélido que domina el estadio y roba carta.',  'ESTADO', 4, 3, 0, 0, 2, 'POCO_COMUN', 'Activa estadio Aire durante 2 turnos. Cartas de Aire +20% ataque. Robas 1 carta adicional al activarlo.'),
('Dominio del Viento', 'El aire reina y anula las prioridades rivales.',     'ESTADO', 4, 5, 0, 0, 3, 'RARA',       'Activa estadio Aire durante 3 turnos. Cartas de Aire +20% ataque. Las cartas con prioridad del rival pierden esa prioridad.'),
('Tormenta Eterna',    'Devuelve las cartas de estado del rival a su mano.', 'ESTADO', 4, 7, 0, 0, 4, 'LEGENDARIA', 'Activa estadio Aire durante 4 turnos. Cartas de Aire +30% ataque. Al activarse, devuelve al rival sus cartas de estado activas a la mano.');

-- ── NEUTRAL (6) ──────────────────────────────────────────────
INSERT INTO CARTA (nombre, descripcion, tipo, id_elemento, coste_mana, ataque, escudo, duracion, rareza, efecto) VALUES
('Poción de Combate',  'Elixir que restaura vida en plena batalla.',         'NEUTRAL', 5, 2, 0,  0, 0, 'COMUN',      'Recuperas 15 puntos de vida inmediatamente.'),
('Escudo Neutro',      'Barrera sin elemento, inmune a efectos de estadio.', 'NEUTRAL', 5, 2, 0, 14, 1, 'COMUN',      'Otorga 14 de escudo (1 turno). No se ve afectado por ningún efecto de estadio.'),
('Catalizador',        'Potencia un 20% la siguiente carta jugada.',         'NEUTRAL', 5, 3, 0,  0, 0, 'POCO_COMUN', 'La siguiente carta jugada este turno hace +20% de daño, escudo o curación.'),
('Silencio Elemental', 'Anula el estadio activo durante 2 turnos.',          'NEUTRAL', 5, 4, 0,  0, 2, 'RARA',       'Cancela el estadio elemental activo. Durante 2 turnos ninguna carta recibe bonificaciones ni penalizaciones elementales.'),
('Gran Curación',      'Restauración masiva. Bonus si la vida es crítica.',  'NEUTRAL', 5, 5, 0,  0, 0, 'RARA',       'Recuperas 28 puntos de vida. Si tienes menos de 30 de vida, recuperas 35 en su lugar.'),
('Reseteo Total',      'Borra todos los efectos activos del campo.',         'NEUTRAL', 5, 6, 0,  0, 0, 'EPICA',      'Anula todos los efectos activos de ambos jugadores: escudos, estados, bonificadores y penalizaciones. El estadio vuelve a Neutral. Ambos jugadores roban 1 carta.');


-- ============================================================
--  6. JUGADOR — 2 jugadores de ejemplo
--  El resto de jugadores, colecciones, mazos y partidas
--  los generará la propia aplicación en uso.
-- ============================================================
INSERT INTO JUGADOR (nombre, apellidos, email, fecha_registro, puntuacion_total) VALUES
('Carlos', 'Ruiz Martínez',  'carlos.ruiz@email.com',  CURDATE(), 0),
('Laura',  'García Sánchez', 'laura.garcia@email.com', CURDATE(), 0);