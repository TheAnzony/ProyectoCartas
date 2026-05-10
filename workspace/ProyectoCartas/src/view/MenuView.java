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
import utils.config;

public class MenuView extends JPanel implements config {

	private MainController controller;

	private JLabel btnIniciarPartida;
	private JLabel btnJugadores;
	private JLabel btnCartas;
	private JLabel btnHistorial;
	private JLabel btnSalir;

	private static final int BTN_ANCHO = 300;
	private static final int BTN_ALTO = 80;
	private static final int BTN_X = (ANCHO - BTN_ANCHO) / 2;
	private static final int BTN_GAP = 10;
	private static final int BTN_INICIO = 280; // centrado automático

	public MenuView(MainController c) {
		this.controller = c;

		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		btnIniciarPartida = crearBoton(BTN_PARTIDA, BTN_X, BTN_INICIO);
		btnIniciarPartida.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.lanzarPartida();
			}
		});

		btnJugadores = crearBoton(BTN_JUGADORES, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP));
		btnJugadores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.lanzarMenuJugador();
			}
		});

		btnCartas = crearBoton(BTN_CARTAS, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP) * 2);
		btnHistorial = crearBoton(BTN_HISTORIAL, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP) * 3);

		btnSalir = crearBoton(BTN_SALIR, BTN_X, BTN_INICIO + (BTN_ALTO + BTN_GAP) * 4);
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

		JLabel fondo = new JLabel(cargarImagen(MENU_IMAGE, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);
	}

	private JLabel crearBoton(String ruta, int x, int y) {
		ImageIcon iconoNormal = cargarImagen(ruta, BTN_ANCHO, BTN_ALTO);
		ImageIcon iconoOscurecido = oscurecerImagen(iconoNormal);

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

	private ImageIcon cargarImagen(String ruta, int ancho, int alto) {
		try {
			BufferedImage original = ImageIO.read(new File(ruta));
			BufferedImage escalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = escalada.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(original, 0, 0, ancho, alto, null);
			g2d.dispose();
			return new ImageIcon(escalada);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ImageIcon oscurecerImagen(ImageIcon icono) {
		BufferedImage original = new BufferedImage(BTN_ANCHO, BTN_ALTO, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = original.createGraphics();
		g2d.drawImage(icono.getImage(), 0, 0, null);
		g2d.setColor(new Color(0, 0, 0, 50)); // 80 = nivel de oscurecimiento (0-255)
		g2d.fillRect(0, 0, BTN_ANCHO, BTN_ALTO);
		g2d.dispose();
		return new ImageIcon(original);
	}

	public JLabel getBtnIniciarPartida() {
		return btnIniciarPartida;
	}

	public JLabel getBtnJugadores() {
		return btnJugadores;
	}

	public JLabel getBtnCartas() {
		return btnCartas;
	}

	public JLabel getBtnHistorial() {
		return btnHistorial;
	}

	public JLabel getBtnSalir() {
		return btnSalir;
	}

}
