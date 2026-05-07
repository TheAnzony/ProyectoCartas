-- ============================================================
--  JUEGO DE CARTAS COLECCIONABLE
--  Script 03 — Funciones, procedimientos y vistas
--  Motor: MySQL 8.x (XAMPP, puerto 3306)
--  Ejecutar DESPUÉS de SCRIP_CREACION.sql e INSERCION.sql
-- ============================================================

USE juego_cartas;


-- ============================================================
--  FUNCIONES
-- ============================================================

DELIMITER $$

-- ------------------------------------------------------------
--  calcular_velocidad_mazo(id_mazo)
--  Devuelve DECIMAL(5,2)
--  Media del campo velocidad de las cartas del mazo.
-- ------------------------------------------------------------
CREATE FUNCTION calcular_velocidad_mazo(p_id_mazo INT)
RETURNS DECIMAL(5,2)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE v_velocidad DECIMAL(5,2);
    SELECT ROUND(AVG(c.velocidad), 2)
    INTO v_velocidad
    FROM MAZO_CARTA mc
    JOIN CARTA c ON mc.id_carta = c.id_carta
    WHERE mc.id_mazo = p_id_mazo;
    RETURN COALESCE(v_velocidad, 0.00);
END$$


-- ------------------------------------------------------------
--  calcular_mana(turno_global)
--  Devuelve INT
--  Escala progresiva de maná por tramos de turno.
--    Turnos 1-2  → 3 maná
--    Turnos 3-4  → 4 maná
--    Turnos 5-6  → 5 maná
--    Turnos 7-8  → 6 maná
--    Turno 9+    → 7 maná
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


-- ------------------------------------------------------------
--  calcular_dano_real(id_carta, id_elemento_activo)
--  Devuelve INT
--  Aplica el multiplicador de INTERACCION_ELEMENTO al daño
--  de la carta según el elemento activo del estadio ese turno.
--  Las cartas DEFENSIVAS y ESTADO devuelven 0.
-- ------------------------------------------------------------
CREATE FUNCTION calcular_dano_real(p_id_carta INT, p_id_elemento_activo INT)
RETURNS INT
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE v_dano             INT;
    DECLARE v_id_elemento      INT;
    DECLARE v_multiplicador    DECIMAL(4,2);

    SELECT daño, id_elemento
    INTO v_dano, v_id_elemento
    FROM CARTA WHERE id_carta = p_id_carta;

    IF v_dano = 0 THEN
        RETURN 0;
    END IF;

    SELECT multiplicador
    INTO v_multiplicador
    FROM INTERACCION_ELEMENTO
    WHERE id_elem_atacante = v_id_elemento
      AND id_elem_defensor = p_id_elemento_activo;

    RETURN ROUND(v_dano * COALESCE(v_multiplicador, 1.00));
END$$


-- ============================================================
--  PROCEDIMIENTOS
-- ============================================================

-- ------------------------------------------------------------
--  crear_partida(...)
--  Inserta la partida. Java resetea id_elemento_activo del
--  estadio al id_elemento_inicial antes de empezar.
-- ------------------------------------------------------------
CREATE PROCEDURE crear_partida(
    IN  p_id_jugador1 INT,
    IN  p_id_jugador2 INT,
    IN  p_id_mazo_j1  INT,
    IN  p_id_mazo_j2  INT,
    IN  p_id_estadio  INT,
    OUT p_id_partida  INT
)
BEGIN
    INSERT INTO PARTIDA (
        id_jugador1, id_jugador2,
        id_mazo_j1,  id_mazo_j2,
        id_estadio,  fecha
    ) VALUES (
        p_id_jugador1, p_id_jugador2,
        p_id_mazo_j1,  p_id_mazo_j2,
        p_id_estadio,  NOW()
    );

    SET p_id_partida = LAST_INSERT_ID();
END$$


-- ------------------------------------------------------------
--  registrar_turno(...)
--  Inserta el registro de un turno completado.
-- ------------------------------------------------------------
CREATE PROCEDURE registrar_turno(
    IN  p_id_partida         INT,
    IN  p_numero_turno       INT,
    IN  p_vida_j1            INT,
    IN  p_vida_j2            INT,
    IN  p_id_elemento_activo INT,
    IN  p_id_jugador_primero INT,
    IN  p_media_vel_j1       DECIMAL(5,2),
    IN  p_media_vel_j2       DECIMAL(5,2),
    OUT p_id_turno           INT
)
BEGIN
    INSERT INTO TURNO (
        id_partida, numero_turno, mana_disponible,
        vida_j1, vida_j2,
        id_elemento_activo, id_jugador_primero,
        media_velocidad_j1, media_velocidad_j2
    ) VALUES (
        p_id_partida, p_numero_turno, calcular_mana(p_numero_turno),
        p_vida_j1, p_vida_j2,
        p_id_elemento_activo, p_id_jugador_primero,
        p_media_vel_j1, p_media_vel_j2
    );

    SET p_id_turno = LAST_INSERT_ID();
END$$


-- ------------------------------------------------------------
--  registrar_resultado_partida(id_partida, id_ganador, turnos)
--  Cierra la partida y actualiza el MMR de ambos jugadores.
--  Ganador: +100 MMR. Perdedor: -50 MMR (mínimo 0).
-- ------------------------------------------------------------
CREATE PROCEDURE registrar_resultado_partida(
    IN p_id_partida INT,
    IN p_id_ganador INT,
    IN p_num_turnos INT
)
BEGIN
    DECLARE v_id_perdedor INT;

    SELECT IF(id_jugador1 = p_id_ganador, id_jugador2, id_jugador1)
    INTO v_id_perdedor
    FROM PARTIDA WHERE id_partida = p_id_partida;

    UPDATE PARTIDA
    SET id_ganador = p_id_ganador,
        num_turnos = p_num_turnos
    WHERE id_partida = p_id_partida;

    UPDATE JUGADOR
    SET MMR = MMR + 100
    WHERE id_jugador = p_id_ganador;

    UPDATE JUGADOR
    SET MMR = GREATEST(0, MMR - 50)
    WHERE id_jugador = v_id_perdedor;
END$$

DELIMITER ;


-- ============================================================
--  VISTAS
-- ============================================================

-- ------------------------------------------------------------
--  vista_ranking
-- ------------------------------------------------------------
CREATE VIEW vista_ranking AS
SELECT
    ROW_NUMBER() OVER (ORDER BY MMR DESC) AS posicion,
    apodo,
    CONCAT(nombre, ' ', apellidos)        AS nombre_completo,
    MMR,
    fecha_registro
FROM JUGADOR
ORDER BY MMR DESC;


-- ------------------------------------------------------------
--  vista_catalogo_cartas
-- ------------------------------------------------------------
CREATE VIEW vista_catalogo_cartas AS
SELECT
    c.id_carta,
    c.nombre,
    c.tipo,
    e.nombre     AS elemento,
    c.rareza,
    c.coste_mana,
    c.velocidad,
    c.daño,
    c.escudo,
    c.duracion
FROM CARTA c
JOIN ELEMENTO e ON c.id_elemento = e.id_elemento
ORDER BY e.nombre, c.tipo, c.coste_mana;


-- ------------------------------------------------------------
--  vista_historial_partidas
-- ------------------------------------------------------------
CREATE VIEW vista_historial_partidas AS
SELECT
    p.id_partida,
    j1.apodo          AS jugador1,
    j2.apodo          AS jugador2,
    m1.nombre         AS mazo_j1,
    m2.nombre         AS mazo_j2,
    e.nombre          AS estadio,
    p.num_turnos,
    jg.apodo          AS ganador,
    p.fecha
FROM PARTIDA p
JOIN JUGADOR  j1 ON p.id_jugador1 = j1.id_jugador
JOIN JUGADOR  j2 ON p.id_jugador2 = j2.id_jugador
JOIN MAZO     m1 ON p.id_mazo_j1  = m1.id_mazo
JOIN MAZO     m2 ON p.id_mazo_j2  = m2.id_mazo
JOIN ESTADIO  e  ON p.id_estadio  = e.id_estadio
LEFT JOIN JUGADOR jg ON p.id_ganador = jg.id_jugador;


-- ------------------------------------------------------------
--  vista_coleccion_jugador
-- ------------------------------------------------------------
CREATE VIEW vista_coleccion_jugador AS
SELECT
    j.apodo          AS jugador,
    c.nombre         AS carta,
    c.tipo,
    e.nombre         AS elemento,
    c.rareza,
    c.coste_mana,
    c.velocidad,
    cj.fecha_obtencion
FROM CARTA_JUGADOR cj
JOIN JUGADOR  j ON cj.id_jugador = j.id_jugador
JOIN CARTA    c ON cj.id_carta   = c.id_carta
JOIN ELEMENTO e ON c.id_elemento = e.id_elemento
ORDER BY j.id_jugador, e.nombre, c.tipo;


-- ------------------------------------------------------------
--  vista_estadisticas_elemento
-- ------------------------------------------------------------
CREATE VIEW vista_estadisticas_elemento AS
SELECT
    e.nombre                                               AS elemento,
    COUNT(c.id_carta)                                      AS total_cartas,
    SUM(CASE WHEN c.tipo = 'OFENSIVA'  THEN 1 ELSE 0 END) AS ofensivas,
    SUM(CASE WHEN c.tipo = 'DEFENSIVA' THEN 1 ELSE 0 END) AS defensivas,
    SUM(CASE WHEN c.tipo = 'ESTADO'    THEN 1 ELSE 0 END) AS estado,
    ROUND(AVG(c.coste_mana), 2)                            AS coste_medio,
    ROUND(AVG(c.velocidad), 2)                             AS velocidad_media,
    ROUND(AVG(NULLIF(c.daño,   0)), 2)                     AS daño_medio_ofensivas,
    ROUND(AVG(NULLIF(c.escudo, 0)), 2)                     AS escudo_medio_defensivas
FROM CARTA c
JOIN ELEMENTO e ON c.id_elemento = e.id_elemento
GROUP BY e.id_elemento, e.nombre
ORDER BY e.nombre;
