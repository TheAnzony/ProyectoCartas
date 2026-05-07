# Opciones de Implementación Multijugador — CardBattle

---

## Opción 1 — Local (mismo PC)

### Cómo funciona
Ambos jugadores usan el mismo PC y la misma ventana. Como los turnos son simultáneos
necesitas resolver el problema de que un jugador no vea las cartas del otro al elegir.

### Formas de implementarlo

**A) Pantallas alternas** *(más simple)*
- Jugador 1 elige sus cartas → pulsa "Listo" → la pantalla se oculta/bloquea
- Jugador 2 elige sus cartas → pulsa "Listo"
- Se resuelven las cartas

**B) Pantalla dividida** *(más visual)*
- La ventana se divide en dos mitades, cada jugador ve solo su zona
- No se ve lo que elige el rival hasta la resolución

### Comunicación con la BD
- Una sola conexión JDBC desde la aplicación
- Al final del turno se guarda todo en BD como está definido
- Sin complejidad adicional

### Dificultad: Baja

---

## Opción 2 — Red local (2 PCs)

### Cómo funciona
Cada jugador usa su propio PC. Los dos programas se comunican entre sí por sockets
para sincronizar las selecciones de cartas de cada turno.

### Arquitectura
```
PC Jugador 1 (Servidor)          PC Jugador 2 (Cliente)
┌─────────────────────┐          ┌─────────────────────┐
│  Java Swing App     │◄────────►│  Java Swing App     │
│  ServerSocket       │  Sockets │  Socket             │
│  JDBC → MySQL       │          │  JDBC → MySQL       │
└─────────────────────┘          └─────────────────────┘
         │                                │
         └────────────────────────────────┘
                    MySQL (en PC1)
```

### Cómo sería la comunicación
1. PC1 arranca como servidor (`ServerSocket` esperando conexión)
2. PC2 se conecta introduciendo la IP de PC1
3. Cada jugador elige sus cartas en su pantalla
4. Al pulsar "Listo", cada PC envía su selección al otro por socket
5. Cuando ambos han enviado, se resuelve el turno en los dos PCs a la vez
6. Solo el servidor (PC1) escribe en la BD

### Comunicación con la BD
- MySQL instalado en PC1
- Ambos se conectan vía JDBC usando la IP local de PC1
- Solo el servidor hace escrituras en BD para evitar conflictos
- El cliente solo lee (catálogo de cartas, historial, etc.)

### Problemas a resolver
- Sincronizar el temporizador de 1:30 entre los dos PCs
- Qué pasa si un PC se desconecta a mitad de turno
- Definir el protocolo de mensajes entre los dos PCs

### Dificultad: Alta

---

## Opción 3 — 3 PCs (cliente-servidor de 3 capas)

### Cómo funciona
PC1 y PC2 son los jugadores. PC3 es el servidor de juego y aloja la base de datos.
Es la arquitectura correcta para un juego multijugador real.

### Arquitectura
```
PC1 (Jugador 1)              PC2 (Jugador 2)
┌─────────────┐              ┌─────────────┐
│ Java Swing  │              │ Java Swing  │
│ Solo vista  │              │ Solo vista  │
│ + Socket    │              │ + Socket    │
└──────┬──────┘              └──────┬──────┘
       │                           │
       └──────────┬────────────────┘
                  │ Sockets TCP
         ┌────────▼────────┐
         │ PC3 — Servidor  │
         │ Lógica partida  │
         │ Java (sin UI)   │
         │ JDBC → MySQL    │
         └────────┬────────┘
                  │
         ┌────────▼────────┐
         │ MySQL           │
         │ juego_cartas    │
         └─────────────────┘
```

### Cómo funcionaría

**PC3 hace todo el trabajo:**
- Recibe las cartas elegidas de cada jugador
- Ejecuta toda la lógica del turno (velocidades, daños, elementos)
- Escribe en la BD (TURNO, TURNO_CARTA, etc.)
- Envía el resultado del turno a ambos clientes para que lo muestren

**PC1 y PC2 solo hacen:**
- Mostrar la interfaz al jugador
- Enviar al servidor las cartas seleccionadas
- Recibir el resultado del turno y mostrarlo

### Comunicación con la BD
- MySQL solo tiene una conexión desde PC3 — sin conflictos
- Los clientes no tocan la BD directamente
- PC3 gestiona todas las lecturas y escrituras

### Ventajas
- La BD tiene una única conexión controlada
- Los clientes son ligeros
- Si un cliente se cae el servidor puede pausar la partida
- Es la arquitectura más escalable y correcta

### Problemas a resolver
- Servidor en PC3 con dos conexiones simultáneas usando hilos (`Thread`)
- Protocolo de mensajes definido entre cliente y servidor
- Sincronización del temporizador desde el servidor

### Dificultad: Muy alta

---

## Comparativa

| | Local | Red 2 PCs | Red 3 PCs |
|---|---|---|---|
| Dificultad | Baja | Alta | Muy alta |
| Tiempo estimado | 1-2 semanas | 3-5 semanas extra | 5+ semanas extra |
| Tecnología nueva | Ninguna | Sockets, Threads | Sockets, Threads, arquitectura cliente-servidor |
| Conexiones BD | 1 | 2 (riesgo conflictos) | 1 (solo PC3) |
| Riesgo | Bajo | Alto | Muy alto |
| Nota práctica | Cumple todo | Extra significativo | Extra muy significativo |

---

## Recomendación

Para la práctica implementar la **Opción 1 (local)**. Cumple todos los requisitos
del enunciado y tiene toda la lógica de partida ya definida.

Si se quisiera escalar a red, el orden lógico sería:
1. Terminar la versión local funcionando
2. Extraer la lógica de partida a una clase independiente sin UI
3. Convertir esa clase en el servidor de PC3
4. Hacer que PC1 y PC2 sean clientes que se conectan a él

Así se reutiliza todo el código ya implementado.
