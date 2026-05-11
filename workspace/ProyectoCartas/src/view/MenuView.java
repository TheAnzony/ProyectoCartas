package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



import controller.MainController;
import utils.ImageUtils;
import utils.config;

public class MenuView extends JPanel implements config {

	private MainController controller;

	private static final int BTN_ANCHO = 300;
	private static final int BTN_ALTO = 80;
	private static final int BTN_X = (ANCHO - BTN_ANCHO) / 2;
	private static final int BTN_GAP = 10;
	private static final int BTN_INICIO = 280; // centrado automático

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

	private JLabel crearBoton(String ruta, int x, int y) {
		ImageIcon iconoNormal = ImageUtils.cargarImagen(ruta, BTN_ANCHO, BTN_ALTO);
		ImageIcon iconoOscurecido = ImageUtils.oscurecerImagen(iconoNormal);

		JLabel boton = new JLabel(iconoNormal);
		boton.setBounds(x, y, BTN_ANCHO, BTN_ALTO);

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
