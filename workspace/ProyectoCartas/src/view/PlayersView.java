package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.MainController;
import dao.CartaDAO;
import dao.JugadorDAO;
import modulos.Carta;
import modulos.Jugador;
import utils.config;

public class PlayersView extends JPanel implements config {

	private MainController controller;

	private static final int BTN_ANCHO = 300;
	private static final int BTN_ALTO = 80;
	private static final int BTN_X = (ANCHO - BTN_ANCHO) / 2;
	private static final int BTN_GAP = 10;
	private static final int BTN_INICIO = 280; // centrado automático

	public PlayersView(MainController c) {
		this.controller = c;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		// Cargar imágenes reutilizables UNA sola vez
		ImageIcon iconoCaja   = cargarImagen(BOX_IMAGE,  300, 70);
		ImageIcon iconoAvatar = cargarImagen(ICONO_LIST,  50, 50);

		// Panel que contiene la lista de jugadores
		JPanel lista = new JPanel();
		lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
		lista.setOpaque(false);

		JScrollPane scroll = new JScrollPane(lista);
		scroll.setBounds((ANCHO - 300) / 2, 280, 600, 500);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(8);
		add(scroll);

		// Botón volver
		ImageIcon iconoBack         = cargarImagen(ARROW_BACK, 60, 60);
		ImageIcon iconoBackOscuro   = oscurecerImagen(iconoBack, 60, 60);
		JLabel btnVolver = new JLabel(iconoBack);
		btnVolver.setBounds(20, 20, 60, 60);
		btnVolver.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { btnVolver.setIcon(iconoBackOscuro); }
			public void mouseExited(MouseEvent e)  { btnVolver.setIcon(iconoBack); }
			public void mouseClicked(MouseEvent e) { controller.lanzarMenuPrincipal(); }
		});
		add(btnVolver);

		// Fondo en EDT — rápido, aparece inmediato
		JLabel fondo = new JLabel(cargarImagen(JUGADORES_IMAGE, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		// Solo la consulta BD en hilo de fondo
		new javax.swing.SwingWorker<List<Jugador>, Void>() {

			@Override
			protected List<Jugador> doInBackground() {
				return new JugadorDAO().listar();
			}

			@Override
			protected void done() {
				try {
					List<Jugador> cartas = get();

					for (Jugador j : cartas) {
						lista.add(crearFilaJugador(j, iconoCaja, iconoAvatar));
						lista.add(Box.createVerticalStrut(10));
					}

					lista.revalidate();
					lista.repaint();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.execute();

	}

	private JLabel crearFilaJugador(Jugador j, ImageIcon iconoCaja, ImageIcon iconoAvatar) {
		JLabel caja = new JLabel(iconoCaja);
		caja.setLayout(null);
		caja.setMaximumSize(new Dimension(300, 70));

		JLabel icono = new JLabel(iconoAvatar);
		icono.setBounds(10, 10, 50, 50);

		JLabel texto = new JLabel(j.getApodo());
		texto.setForeground(Color.white);
		texto.setFont(new Font("Arial", Font.BOLD, 16));
		texto.setBounds(70, 20, 400, 30);

		// Capa oscura para el hover
		JPanel capa = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        g.setColor(new Color(0, 0, 0, 50));
		        g.fillRect(0, 0, getWidth(), getHeight());
		    }
		};
		
		
		capa.setOpaque(false);  // sin esto sigue tapando todo
		capa.setBounds(0, 0, 300, 70);
		capa.setVisible(false);

		caja.add(capa); // primero → encima de todo
		caja.add(texto);
		caja.add(icono);


		caja.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				capa.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				capa.setVisible(false);
			}
		});

		return caja;
	}

	private ImageIcon oscurecerImagen(ImageIcon icono, int ancho, int alto) {
		BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(icono.getImage(), 0, 0, null);
		g2d.setColor(new Color(0, 0, 0, 80));
		g2d.fillRect(0, 0, ancho, alto);
		g2d.dispose();
		return new ImageIcon(img);
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

}
