# TODO — Requisitos Práctica Final

## Base de Datos

- [x] Estructura relacional con tablas, PKs, FKs y restricciones
- [x] Complejidad suficiente (11 tablas con relaciones)
- [x] Coherencia con la lógica del juego
- [x] Datos ficticios insertados (56 cartas, 4 elementos, 4 estadios, 2 jugadores)
- [x] Procedimientos almacenados (`crear_partida`, `registrar_turno`, `registrar_resultado_partida`)
- [x] Funciones (`calcular_mana`, `calcular_dano_real`, `calcular_velocidad_mazo`)
- [x] JOINs y consultas multitabla (todas las vistas)
- [x] Funciones agregadas AVG, COUNT, SUM (`vista_estadisticas_elemento`)
- [x] Borrado en cadena CASCADE (PARTIDA→TURNO→TURNO_CARTA, JUGADOR→MAZO→MAZO_CARTA)
- [ ] Añadir subconsulta explícita (`WHERE ... IN (SELECT ...)` o similar) en una vista o consulta

---

## Requisitos Funcionales de la Aplicación

- [x] Menú principal con secciones (Iniciar Partida, Cartas, Mazo, Historial)
- [x] Sección Cartas — catálogo con filtros
- [x] Sección Mazo — crear mazo y añadir cartas
- [x] Sección Historial — resumen de partidas del jugador
- [x] Simulación de partida (turnos simultáneos, velocidad, elementos, MMR)
- [ ] Gestión de jugadores — alta de jugador nuevo (inserción)
- [ ] Gestión de jugadores — editar datos del jugador (actualización)
- [ ] Gestión de jugadores — eliminar jugador (eliminación con CASCADE)
- [ ] Eliminar mazo
- [ ] Eliminar carta de un mazo

---

## Tecnologías

- [x] Java
- [x] Java Swing
- [x] Conexión JDBC (DBConnect)
- [x] MySQL
- [x] Sin ORM (sin Hibernate)

---

## Entrega

- [x] Scripts SQL (SCRIP_CREACION.sql, INSERCION.sql, PROCEDIMIENTOS.sql)
- [ ] Código fuente Java exportado (exportar proyecto)
- [ ] Ejecutable `.exe`
- [ ] Modelo E/R (diagrama entidad-relación de la BD)
- [ ] Manual de usuario (con capturas de cada pantalla)
- [ ] JavaDoc (comentarios en el código Java)
