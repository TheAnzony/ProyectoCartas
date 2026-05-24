package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import controller.MainController;
import dao.CartaDAO;
import modulos.Carta;
import utils.ConstruirCarta;
import utils.ImageUtils;
import utils.config;

/*
 * GridLayout(0, 3, ...) → GridLayout(0, 4, ...)
Ancho del scroll: 4×200 + 3×10 = 830px
X centrado: (1440 - 830) / 2 = 305

JPanel grid = new JPanel(new GridLayout(0, 4, 10, 10));

JScrollPane scroll = new JScrollPane(grid);
scroll.setBounds((ANCHO - 830) / 2, 180, 830, 570);
 */
/**
 * Pantalla del catálogo de cartas.
 * Muestra todas las cartas disponibles en la base de datos en una cuadrícula
 * de 5 columnas con scroll vertical. Las imágenes se precargan en background.
 */
public class CartaView extends JPanel implements config {

	MainController controller;

	/**
	 * Construye la vista del catálogo y lanza la carga de cartas en background.
	 *
	 * @param c Controlador principal de la aplicación.
	 */
	public CartaView(MainController c) {
		this.controller = c;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		

		JPanel grid = new JPanel(new GridLayout(0, 5, 10, 10));
		grid.setOpaque(false);

		JScrollPane scroll = new JScrollPane(grid);
		scroll.setBounds((ANCHO - 1050) / 2, 180, 1050, 570);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(8);
		add(scroll);

		// Botón volver
		ImageIcon iconoBack = ImageUtils.cargarImagen(ARROW_BACK, 50, 50);
		ImageIcon iconoBackOscuro = ImageUtils.oscurecerImagen(iconoBack);
		JLabel btnVolver = new JLabel(iconoBack);
		btnVolver.setBounds(15, 15, 50, 50);
		btnVolver.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		btnVolver.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnVolver.setIcon(iconoBackOscuro);
			}

			public void mouseExited(MouseEvent e) {
				btnVolver.setIcon(iconoBack);
			}

			public void mouseClicked(MouseEvent e) {
				controller.lanzarMenuPrincipal();
			}
		});
		add(btnVolver);

		// Fondo en EDT — rápido, aparece inmediato
		JLabel fondo = new JLabel(ImageUtils.cargarImagen(CARTAS_IMAGE, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		ConstruirCarta.ConstruirCartasDefault();

		new SwingWorker<List<Carta>, Void>() {
			@Override
			protected List<Carta> doInBackground() throws Exception {
				List<Carta> cartas = new CartaDAO().listar();
				// Precarga imágenes individuales en background → quedan en caché
				for (Carta carta : cartas) {
					if (carta.getImagen() != null) {
						ImageUtils.cargarImagen(CARTAS_DIR + carta.getImagen(), 200, 280);
					}
				}
				return cartas;
			}

			@Override
			protected void done() {
				try {
					List<Carta> cartas = get();

					for (Carta carta : cartas) {
						grid.add(ConstruirCarta.insertarCarta(carta));
					}
					grid.revalidate();
					grid.repaint();
					javax.swing.SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(0));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.execute();

	}

	

}
