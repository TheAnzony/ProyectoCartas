-- ============================================================
--  JUEGO DE CARTAS COLECCIONABLE
--  Script 03 — Funciones, procedimientos y vistas
--  Motor: MySQL 8.x (XAMPP, puerto 3306)
--  Ejecutar DESPUÉS de 01_crear_bbdd.sql y 02_insertar_datos.sql
-- ============================================================

USE juego_cartas;


-- ============================================================
--  FUNCIONES
-- ============================================================

DELIMITER $$

-- ------------------------------------------------------------
--  calcular_velocidad_mazo(id_mazo)
--  Devuelve DECIMAL(5,2)
--  Fórmula: 7 - AVG(coste_mana) de las cartas del mazo
-- ------------------------------------------------------------
CREATE FUNCTION calcular_velocidad_mazo(p_id_mazo INT)
RETURNS DECIMAL(5,2)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE v_velocidad DECIMAL(5,2);
    SELECT ROUND(7 - AVG(c.coste_mana), 2)
    INTO v_velocidad
    FROM MAZO_CARTA mc
    JOIN CARTA c ON mc.id_carta = c.id_carta
    WHERE mc.id_mazo = p_id_mazo;
    RETURN COALESCE(v_velocidad, 0.00);
END$$

-- ------------------------------------------------------------
--  calcular_mana(turno_global)
--  Devuelve INT
--  Escala: 1-2=3, 3-4=4, 5-6=5, 7-8=6, 9+ techo de 7
-- ------------------------------------------------------------
CREATE FUNCTION calcular_mana(p_turno_global INT)
RETURNS INT
DETERMINISTIC
NO SQL
BEGIN
    DECLARE v_mana INT;
    CASE
        WHEN p_turno_global <= 2 THEN SET v_mana = 3;
        WHEN p_turno_global <= 4 THEN SET v_mana = 4;
        WHEN p_turno_global <= 6 THEN SET v_mana = 5;
        WHEN p_turno_global <= 8 THEN SET v_mana = 6;
        ELSE                          SET v_mana = 7;
    END CASE;
    RETURN v_mana;
END$$


-- ============================================================
--  PROCEDIMIENTOS
-- ============================================================

-- ------------------------------------------------------------
--  crear_partida(...)
--  Inserta la partida, calcula velocidades y determina
--  quién va primero según la lógica de iniciativa:
--    1. Mayor velocidad va primero
--    2. Desempate: más cartas de coste 2
--    3. Segundo desempate: aleatoriedad
-- ------------------------------------------------------------
CREATE PROCEDURE crear_partida(
    IN  p_id_jugador1    INT,
    IN  p_id_jugador2    INT,
    IN  p_id_mazo_j1     INT,
    IN  p_id_mazo_j2     INT,
    IN  p_id_estadio     INT,
    OUT p_id_partida     INT,
    OUT p_primer_jugador INT
)
BEGIN
    DECLARE v_vel1             DECIMAL(5,2);
    DECLARE v_vel2             DECIMAL(5,2);
    DECLARE v_cartas_coste2_j1 INT;
    DECLARE v_cartas_coste2_j2 INT;

    -- Calcular velocidades de cada mazo
    SET v_vel1 = calcular_velocidad_mazo(p_id_mazo_j1);
    SET v_vel2 = calcular_velocidad_mazo(p_id_mazo_j2);

    -- Determinar quién va primero
    IF v_vel1 > v_vel2 THEN
        SET p_primer_jugador = p_id_jugador1;

    ELSEIF v_vel2 > v_vel1 THEN
        SET p_primer_jugador = p_id_jugador2;

    ELSE
        -- Desempate 1: número de cartas con coste 2
        SELECT COUNT(*) INTO v_cartas_coste2_j1
        FROM MAZO_CARTA mc
        JOIN CARTA c ON mc.id_carta = c.id_carta
        WHERE mc.id_mazo = p_id_mazo_j1 AND c.coste_mana = 2;

        SELECT COUNT(*) INTO v_cartas_coste2_j2
        FROM MAZO_CARTA mc
        JOIN CARTA c ON mc.id_carta = c.id_carta
        WHERE mc.id_mazo = p_id_mazo_j2 AND c.coste_mana = 2;

        IF v_cartas_coste2_j1 > v_cartas_coste2_j2 THEN
            SET p_primer_jugador = p_id_jugador1;
        ELSEIF v_cartas_coste2_j2 > v_cartas_coste2_j1 THEN
            SET p_primer_jugador = p_id_jugador2;
        ELSE
            -- Desempate 2: aleatoriedad
            IF RAND() >= 0.5 THEN
                SET p_primer_jugador = p_id_jugador1;
            ELSE
                SET p_primer_jugador = p_id_jugador2;
            END IF;
        END IF;
    END IF;

    -- Insertar la partida
    INSERT INTO PARTIDA (
        id_jugador1, id_jugador2,
        id_mazo_j1,  id_mazo_j2,
        id_estadio,  fecha_inicio,
        velocidad_j1, velocidad_j2
    ) VALUES (
        p_id_jugador1, p_id_jugador2,
        p_id_mazo_j1,  p_id_mazo_j2,
        p_id_estadio,  NOW(),
        v_vel1, v_vel2
    );

    SET p_id_partida = LAST_INSERT_ID();
END$$

-- ------------------------------------------------------------
--  registrar_resultado_partida(id_partida, id_ganador, turnos)
--  Actualiza fecha_fin, id_ganador, turnos_totales
--  y suma 100 puntos al ganador
-- ------------------------------------------------------------
CREATE PROCEDURE registrar_resultado_partida(
    IN p_id_partida     INT,
    IN p_id_ganador     INT,
    IN p_turnos_totales INT
)
BEGIN
    UPDATE PARTIDA
    SET id_ganador     = p_id_ganador,
        turnos_totales = p_turnos_totales,
        fecha_fin      = NOW()
    WHERE id_partida = p_id_partida;

    UPDATE JUGADOR
    SET puntuacion_total = puntuacion_total + 100
    WHERE id_jugador = p_id_ganador;
END$$

DELIMITER ;


-- ============================================================
--  VISTAS
-- ============================================================

-- ------------------------------------------------------------
--  vista_ranking
--  Jugadores ordenados por puntuación con posición numerada
-- ------------------------------------------------------------
CREATE VIEW vista_ranking AS
SELECT
    ROW_NUMBER() OVER (ORDER BY puntuacion_total DESC) AS posicion,
    CONCAT(nombre, ' ', apellidos)                      AS jugador,
    puntuacion_total,
    fecha_registro
FROM JUGADOR
ORDER BY puntuacion_total DESC;


-- ------------------------------------------------------------
--  vista_catalogo_cartas
--  Todas las cartas con nombre de elemento en lugar de id
-- ------------------------------------------------------------
CREATE VIEW vista_catalogo_cartas AS
SELECT
    c.id_carta,
    c.nombre,
    c.tipo,
    e.nombre   AS elemento,
    c.rareza,
    c.coste_mana,
    c.ataque,
    c.escudo,
    c.duracion,
    c.efecto
FROM CARTA c
JOIN ELEMENTO e ON c.id_elemento = e.id_elemento
ORDER BY e.nombre, c.tipo, c.coste_mana;


-- ------------------------------------------------------------
--  vista_historial_partidas
--  Partidas con nombres de jugadores, mazos, ganador
--  y duración en segundos
-- ------------------------------------------------------------
CREATE VIEW vista_historial_partidas AS
SELECT
    p.id_partida,
    CONCAT(j1.nombre, ' ', j1.apellidos) AS jugador1,
    CONCAT(j2.nombre, ' ', j2.apellidos) AS jugador2,
    m1.nombre                            AS mazo_j1,
    m2.nombre                            AS mazo_j2,
    e.nombre                             AS estadio,
    p.turnos_totales,
    p.velocidad_j1,
    p.velocidad_j2,
    CONCAT(jg.nombre, ' ', jg.apellidos) AS ganador,
    p.fecha_inicio,
    p.fecha_fin,
    TIMESTAMPDIFF(SECOND, p.fecha_inicio, p.fecha_fin) AS duracion_segundos
FROM PARTIDA p
JOIN JUGADOR  j1 ON p.id_jugador1 = j1.id_jugador
JOIN JUGADOR  j2 ON p.id_jugador2 = j2.id_jugador
JOIN MAZO     m1 ON p.id_mazo_j1  = m1.id_mazo
JOIN MAZO     m2 ON p.id_mazo_j2  = m2.id_mazo
JOIN ESTADIO  e  ON p.id_estadio  = e.id_estadio
LEFT JOIN JUGADOR jg ON p.id_ganador = jg.id_jugador;


-- ------------------------------------------------------------
--  vista_coleccion_jugador
--  Colección de cada jugador con stats de sus cartas
-- ------------------------------------------------------------
CREATE VIEW vista_coleccion_jugador AS
SELECT
    CONCAT(j.nombre, ' ', j.apellidos) AS jugador,
    c.nombre                            AS carta,
    c.tipo,
    e.nombre                            AS elemento,
    c.rareza,
    c.coste_mana,
    cj.fecha_obtencion
FROM CARTA_JUGADOR cj
JOIN JUGADOR  j ON cj.id_jugador = j.id_jugador
JOIN CARTA    c ON cj.id_carta   = c.id_carta
JOIN ELEMENTO e ON c.id_elemento = e.id_elemento
ORDER BY j.id_jugador, e.nombre, c.tipo;


-- ------------------------------------------------------------
--  vista_estadisticas_elemento
--  Cuenta y stats medios de cartas por elemento
-- ------------------------------------------------------------
CREATE VIEW vista_estadisticas_elemento AS
SELECT
    e.nombre                        AS elemento,
    COUNT(c.id_carta)               AS total_cartas,
    SUM(CASE WHEN c.tipo = 'OFENSIVA'  THEN 1 ELSE 0 END) AS ofensivas,
    SUM(CASE WHEN c.tipo = 'DEFENSIVA' THEN 1 ELSE 0 END) AS defensivas,
    ROUND(AVG(c.coste_mana), 2)     AS coste_medio,
    ROUND(AVG(NULLIF(c.ataque, 0)), 2) AS ataque_medio_ofensivas,
    ROUND(AVG(NULLIF(c.escudo, 0)), 2) AS escudo_medio_defensivas
FROM CARTA c
JOIN ELEMENTO e ON c.id_elemento = e.id_elemento
GROUP BY e.id_elemento, e.nombre
ORDER BY e.nombre;