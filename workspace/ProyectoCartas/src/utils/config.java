package utils;

public interface config {
	
	int ANCHO = 1440;
	int ALTO = 810;
	
	String FONDOS_DIR = "assets/fondos/";
	String ELEMENTOS_DIR = "assets/elementos/";
	String BOTONES_DIR = "assets/botones/";
	String CARTAS_DIR = "assets/cartas/";
	
	String START_IMAGE = FONDOS_DIR+"start.png";
	String MENU_IMAGE = FONDOS_DIR+"menu.png";
	String JUGADORES_IMAGE = FONDOS_DIR+"jugadores_menu.png";
	String CARTAS_IMAGE = FONDOS_DIR+"carta_view.png";
	
	String BOX_IMAGE_TIERRA = CARTAS_DIR+"marco_carta_tierra.png";
	String BOX_IMAGE_FUEGO = CARTAS_DIR+"marco_carta_fuego.png";
	String BOX_IMAGE_AGUA = CARTAS_DIR+"marco_carta_agua.png";
	String BOX_IMAGE_AIRE = CARTAS_DIR+"marco_carta_aire.png";
	
	String BOX_IMAGE = BOTONES_DIR+"caja.png";
	String ICONO_LIST = BOTONES_DIR+"icon_player_list.png";
	
	String BTN_JUGADORES = BOTONES_DIR+"jugadores.png";
	String BTN_CARTAS = BOTONES_DIR+"cartas.png";
	String BTN_SALIR = BOTONES_DIR+"salir.png";
	String BTN_HISTORIAL = BOTONES_DIR+"historial.png";
	String BTN_PARTIDA = BOTONES_DIR+"iniciar_partida.png";
	String ARROW_BACK  = BOTONES_DIR+"arrow_back.png";
	

}
