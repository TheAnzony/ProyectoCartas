package utils;

/**
 * Interfaz de configuración global de la aplicación.
 * Define las dimensiones de la ventana y las rutas a todos los recursos gráficos
 * (fondos, botones, cartas, fuentes). Todas las vistas la implementan para
 * acceder directamente a estas constantes.
 */
public interface config {

	// ── Dimensiones de la ventana ─────────────────────────────────────────

	/** Ancho de la ventana en píxeles. */
	int ANCHO = 1440;
	/** Alto de la ventana en píxeles. */
	int ALTO = 810;

	// ── Directorios de recursos ───────────────────────────────────────────

	/** Directorio de imágenes de fondo. */
	String FONDOS_DIR  = "assets/fondos/";
	/** Directorio de imágenes de botones e iconos de interfaz. */
	String BOTONES_DIR = "assets/botones/";
	/** Directorio de imágenes de cartas y marcos de elemento. */
	String CARTAS_DIR  = "assets/cartas/";
	/** Directorio de ficheros de fuentes tipográficas. */
	String FUENTES_DIR = "assets/fuentes/";

	// ── Fuentes ───────────────────────────────────────────────────────────

	/** Fuente medieval (CloisterBlack) usada en títulos y etiquetas destacadas. */
	String FONT_MEDIEVAL = FUENTES_DIR + "CloisterBlack.ttf";

	// ── Fondos de pantalla ────────────────────────────────────────────────

	/** Fondo de la pantalla de inicio (StartView). */
	String START_IMAGE    = FONDOS_DIR + "start.png";
	/** Fondo del menú principal (MenuView). */
	String MENU_IMAGE     = FONDOS_DIR + "menu.png";
	/** Fondo de la vista del catálogo de cartas (CartaView). */
	String CARTAS_IMAGE   = FONDOS_DIR + "carta_view.png";
	/** Fondo genérico usado en las vistas sin fondo propio. */
	String FONDO_DEFAULT  = FONDOS_DIR + "fondo_default.png";

	// ── Marcos de carta por elemento ──────────────────────────────────────

	/** Marco de carta del elemento Tierra. */
	String BOX_IMAGE_TIERRA = CARTAS_DIR + "marco_carta_tierra.png";
	/** Marco de carta del elemento Fuego. */
	String BOX_IMAGE_FUEGO  = CARTAS_DIR + "marco_carta_fuego.png";
	/** Marco de carta del elemento Agua. */
	String BOX_IMAGE_AGUA   = CARTAS_DIR + "marco_carta_agua.png";
	/** Marco de carta del elemento Aire. */
	String BOX_IMAGE_AIRE   = CARTAS_DIR + "marco_carta_aire.png";

	// ── Iconos de interfaz ────────────────────────────────────────────────

	/** Imagen de caja/fondo para filas de jugadores en la lista. */
	String BOX_IMAGE  = BOTONES_DIR + "caja.png";
	/** Icono de avatar de jugador en la lista. */
	String ICONO_LIST = BOTONES_DIR + "icon_player_list.png";

	// ── Botones del menú principal ────────────────────────────────────────

	/** Botón "Jugadores" del menú principal. */
	String BTN_JUGADORES = BOTONES_DIR + "jugadores.png";
	/** Botón "Cartas" del menú principal. */
	String BTN_CARTAS    = BOTONES_DIR + "cartas.png";
	/** Botón "Salir" del menú principal. */
	String BTN_SALIR     = BOTONES_DIR + "salir.png";
	/** Botón "Historial" del menú principal. */
	String BTN_HISTORIAL = BOTONES_DIR + "historial.png";
	/** Botón "Iniciar Partida" del menú principal. */
	String BTN_PARTIDA   = BOTONES_DIR + "iniciar_partida.png";
	/** Flecha de retroceso usada en todas las vistas. */
	String ARROW_BACK    = BOTONES_DIR + "arrow_back.png";

	// ── Botones de gestión de jugadores ──────────────────────────────────

	/** Botón de imagen para añadir un jugador (PlayersView). */
	String BTN_ANADIR_JUGADOR    = BOTONES_DIR + "boton_añadir_jugador.png";
	/** Botón de imagen para eliminar un jugador (PlayersView). */
	String BTN_ELIMINAR_JUGADOR  = BOTONES_DIR + "eliminar_jugador.png";
	/** Botón de imagen para modificar un jugador (PlayersView). */
	String BTN_MODIFICAR_JUGADOR = BOTONES_DIR + "modificar_jugador.png";
}
