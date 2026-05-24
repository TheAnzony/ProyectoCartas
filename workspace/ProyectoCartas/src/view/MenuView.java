package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



import controller.MainController;
import utils.ImageUtils;
import utils.config;

/**
 * Pantalla del menú principal del juego.
 * Muestra cinco botones de imagen (Iniciar Partida, Jugadores, Cartas, Historial y Salir)
 * centrados verticalmente sobre el fondo del menú.
 */
public class MenuView extends JPanel implements config {

	private MainController controller;

	/** Ancho de cada botón del menú en píxeles. */
	private static final int BTN_ANCHO = 300;
	/** Alto de cada botón del menú en píxeles. */
	private static final int BTN_ALTO  = 80;
	/** Coordenada X de todos los botones (centrados horizontalmente). */
	private static final int BTN_X     = (ANCHO - BTN_ANCHO) / 2;
	/** Separación vertical entre botones en píxeles. */
	private static final int BTN_GAP   = 10;
	/** Coordenada Y del primer botón. */
	private static final int BTN_INICIO = 280;

	/**
	 * Construye el menú principal con todos sus botones y el fondo.
	 *
	 * @param c Controlador principal de la aplicación.
	 */
	public MenuView(MainController c) {
		this.controller = c;

		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		JLabel btnIniciarPartida = crearBoton(BTN_PARTIDA, BTN_X, BTN_INICIO);
		btnIniciarPartida.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.lanzarPartida();
			}
		});

		JLabel btnJugadores = crearBoton(BTN_JUGADORES, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP));
		btnJugadores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.lanzarMenuJugador();
			}
		});

		JLabel btnCartas = crearBoton(BTN_CARTAS, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP) * 2);
		btnCartas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.lanzarMenuCartas();
				;
			}
		});

		JLabel btnHistorial = crearBoton(BTN_HISTORIAL, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP) * 3);
		btnHistorial.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.lanzarHistorial();
			}
		});

		JLabel btnSalir = crearBoton(BTN_SALIR, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP) * 4);
		btnSalir.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				System.exit(0);

			}
		});

		add(btnIniciarPartida);
		add(btnJugadores);
		add(btnCartas);
		add(btnHistorial);
		add(btnSalir);

		// CAPA QUE OSCURECE EL FONDO
		JPanel oscurece = new JPanel();
		oscurece.setBackground(new Color(0, 0, 0, 50));
		oscurece.setOpaque(true);
		oscurece.setBounds(0, 0, ANCHO, ALTO);
		add(oscurece);

		JLabel fondo = new JLabel(ImageUtils.cargarImagen(MENU_IMAGE, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);
	}

	/**
	 * Crea un botón de imagen con efecto de oscurecimiento al pasar el ratón.
	 *
	 * @param ruta Ruta al fichero de imagen del botón.
	 * @param x    Posición horizontal del botón.
	 * @param y    Posición vertical del botón.
	 * @return {@link JLabel} configurado como botón interactivo.
	 */
	private JLabel crearBoton(String ruta, int x, int y) {
		ImageIcon iconoNormal = ImageUtils.cargarImagen(ruta, BTN_ANCHO, BTN_ALTO);
		ImageIcon iconoOscurecido = ImageUtils.oscurecerImagen(iconoNormal);

		JLabel boton = new JLabel(iconoNormal);
		boton.setBounds(x, y, BTN_ANCHO, BTN_ALTO);
		boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

		boton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				boton.setIcon(iconoOscurecido);
			}

			public void mouseExited(MouseEvent e) {
				boton.setIcon(iconoNormal);
			}

		});

		return boton;
	}

	

}
