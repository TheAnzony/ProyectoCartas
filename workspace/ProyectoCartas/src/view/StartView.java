package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import controller.MainController;
import dao.CartaDAO;
import modulos.Carta;
import utils.ConstruirCarta;
import utils.ImageUtils;
import utils.config;

/**
 * Pantalla de carga inicial de la aplicación.
 * Precarga en caché todas las imágenes de cartas (en varios tamaños) y los marcos
 * de elemento mientras muestra el porcentaje de progreso. Al terminar, indica
 * al usuario que pulse cualquier tecla para continuar al menú principal.
 */
public class StartView extends JPanel implements config {

	private MainController controller;
	/** Indica si la precarga de imágenes ha finalizado y se puede pasar al menú. */
	private boolean cargado = false;

	/**
	 * Construye la pantalla de carga, inicia la precarga de imágenes en background
	 * y configura el listener de teclado para avanzar al menú principal.
	 *
	 * @param c Controlador principal de la aplicación.
	 */
	public StartView(MainController c) {
		this.controller = c;

		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));
		ConstruirCarta.ConstruirCartasDefault();

		ImageIcon iconoOriginal = new ImageIcon(START_IMAGE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(ANCHO, ALTO, Image.SCALE_SMOOTH);

		JLabel background = new JLabel(new ImageIcon(imgEscalada));
		background.setBounds(0, 0, ANCHO, ALTO);
		background.setLayout(null);

		JLabel labelEstado = new JLabel("0%", SwingConstants.CENTER);
		labelEstado.setBounds(0, ALTO - 110, ANCHO, 60);
		labelEstado.setFont(new Font("Arial", Font.BOLD, 32));
		labelEstado.setForeground(Color.WHITE);
		background.add(labelEstado);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (cargado) {
					controller.lanzarMenuPrincipal();
				}
			}
		});

		setFocusable(true);
		requestFocusInWindow();

		add(background);

		new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() {
				
				CartaDAO dao = new CartaDAO();
				List<Carta> lista = dao.listar();
				for (int i = 0; i < lista.size(); i++) {
					String img = lista.get(i).getImagen();
					ImageUtils.cargarImagen(CARTAS_DIR + img, 200, 280);  // CartaView
					ImageUtils.cargarImagen(CARTAS_DIR + img, 160, 224);  // MazoView carrusel
					ImageUtils.cargarImagen(CARTAS_DIR + img, 128, 179);  // MazoView slots
					int porcentaje = (int) ((i + 1) * 100.0 / lista.size());
					publish(porcentaje);
				}
				// Fondos de elemento para carrusel y slots del mazo
				ImageUtils.cargarImagen(BOX_IMAGE_FUEGO,  160, 224);
				ImageUtils.cargarImagen(BOX_IMAGE_AGUA,   160, 224);
				ImageUtils.cargarImagen(BOX_IMAGE_TIERRA, 160, 224);
				ImageUtils.cargarImagen(BOX_IMAGE_AIRE,   160, 224);
				ImageUtils.cargarImagen(BOX_IMAGE_FUEGO,  128, 179);
				ImageUtils.cargarImagen(BOX_IMAGE_AGUA,   128, 179);
				ImageUtils.cargarImagen(BOX_IMAGE_TIERRA, 128, 179);
				ImageUtils.cargarImagen(BOX_IMAGE_AIRE,   128, 179);
				return null;
			}

			@Override
			protected void process(List<Integer> valores) {
				labelEstado.setText(valores.get(valores.size() - 1) + "%");
			}

			@Override
			protected void done() {
				cargado = true;
				labelEstado.setText("Pulsa cualquier tecla para continuar");
			}

		}.execute();
	}

}
