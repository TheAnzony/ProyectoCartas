package view;

import controller.MainController;
import dao.CartaDAO;
import dao.EstadioDAO;
import dao.JugadorDAO;
import dao.MazoDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import modulos.Estadio;
import modulos.Jugador;
import modulos.Mazo;
import utils.ImageUtils;
import utils.config;

/**
 * Pantalla de configuración previa a la batalla.
 * Permite seleccionar dos jugadores diferentes y un estadio mediante combos.
 * Antes de iniciar la partida verifica que ambos jugadores tengan un mazo
 * con al menos 10 cartas; si no, muestra un error informativo.
 */
public class SeleccionJugadoresView extends JPanel implements config {

	private final MainController      controller;
	/** Combo para seleccionar al jugador 1. */
	private final JComboBox<Jugador>  comboJ1      = new JComboBox<>();
	/** Combo para seleccionar al jugador 2. */
	private final JComboBox<Jugador>  comboJ2      = new JComboBox<>();
	/** Combo para seleccionar el estadio de la partida. */
	private final JComboBox<Estadio>  comboEstadio = new JComboBox<>();
	/** Botón que lanza la validación y, si todo es correcto, arranca la batalla. */
	private final JButton             btnIniciar;

	/**
	 * Construye la vista de selección de jugadores con todos sus controles y el fondo.
	 *
	 * @param c Controlador principal de la aplicación.
	 */
	public SeleccionJugadoresView(MainController c) {
		this.controller = c;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		// ── Título ────────────────────────────────────────────────────────
		JLabel titulo = new JLabel("Iniciar Partida", JLabel.CENTER);
		titulo.setBounds(0, 60, ANCHO, 70);
		titulo.setForeground(new Color(255, 195, 30));
		titulo.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 58f));
		add(titulo);

		// ── J1 (izquierda) ────────────────────────────────────────────────
		JLabel lJ1 = new JLabel("Jugador 1", JLabel.CENTER);
		lJ1.setBounds(100, 200, 400, 36);
		lJ1.setForeground(new Color(80, 220, 80));
		lJ1.setFont(new Font("Arial", Font.BOLD, 24));
		add(lJ1);

		comboJ1.setBounds(150, 248, 300, 40);
		comboJ1.setFont(new Font("Arial", Font.BOLD, 15));
		add(comboJ1);

		// ── VS (centro) ───────────────────────────────────────────────────
		JLabel vs = new JLabel("VS", JLabel.CENTER);
		vs.setBounds(0, 238, ANCHO, 60);
		vs.setForeground(Color.WHITE);
		vs.setFont(new Font("Arial", Font.BOLD, 52));
		add(vs);

		// ── J2 (derecha) ──────────────────────────────────────────────────
		JLabel lJ2 = new JLabel("Jugador 2", JLabel.CENTER);
		lJ2.setBounds(ANCHO - 500, 200, 400, 36);
		lJ2.setForeground(new Color(220, 80, 80));
		lJ2.setFont(new Font("Arial", Font.BOLD, 24));
		add(lJ2);

		comboJ2.setBounds(ANCHO - 450, 248, 300, 40);
		comboJ2.setFont(new Font("Arial", Font.BOLD, 15));
		add(comboJ2);

		// ── Estadio (centro inferior) ─────────────────────────────────────
		JLabel lEstadio = new JLabel("Estadio", JLabel.CENTER);
		lEstadio.setBounds(0, 340, ANCHO, 30);
		lEstadio.setForeground(new Color(180, 180, 255));
		lEstadio.setFont(new Font("Arial", Font.BOLD, 18));
		add(lEstadio);

		comboEstadio.setBounds((ANCHO - 300) / 2, 380, 300, 40);
		comboEstadio.setFont(new Font("Arial", Font.BOLD, 15));
		// Renderer para mostrar solo el nombre del estadio
		comboEstadio.setRenderer(new DefaultListCellRenderer() {
			@Override public Component getListCellRendererComponent(
					JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Estadio) setText(((Estadio) value).getNombre());
				return this;
			}
		});
		add(comboEstadio);

		// ── Botón Iniciar ─────────────────────────────────────────────────
		btnIniciar = new JButton("INICIAR PARTIDA");
		btnIniciar.setBounds((ANCHO - 300) / 2, 455, 300, 55);
		btnIniciar.setBackground(new Color(40, 100, 200));
		btnIniciar.setForeground(Color.WHITE);
		btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
		btnIniciar.setFocusPainted(false);
		btnIniciar.setBorderPainted(false);
		btnIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIniciar.addActionListener(e -> iniciar());
		add(btnIniciar);

		// ── Botón volver ──────────────────────────────────────────────────
		ImageIcon iconoBack    = ImageUtils.cargarImagen(ARROW_BACK, 50, 50);
		ImageIcon iconoBackOsc = ImageUtils.oscurecerImagen(iconoBack);
		JLabel btnVolver = new JLabel(iconoBack);
		btnVolver.setBounds(15, 15, 50, 50);
		btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolver.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)  { btnVolver.setIcon(iconoBackOsc); }
			public void mouseExited(MouseEvent e)   { btnVolver.setIcon(iconoBack); }
			public void mouseReleased(MouseEvent e) { controller.lanzarMenuPrincipal(); }
		});
		add(btnVolver);

		// ── Overlay + Fondo ───────────────────────────────────────────────
		JPanel overlay = new JPanel() {
			@Override protected void paintComponent(Graphics g) {
				g.setColor(new Color(0, 0, 0, 110));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		overlay.setOpaque(false);
		overlay.setBounds(0, 0, ANCHO, ALTO);
		add(overlay);

		JLabel fondo = new JLabel(ImageUtils.cargarImagen(FONDO_DEFAULT, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		// ── Carga datos en background ─────────────────────────────────────
		new SwingWorker<Void, Void>() {
			private List<Jugador>  jugadores;
			private List<Estadio>  estadios;

			@Override protected Void doInBackground() {
				jugadores = new JugadorDAO().listar();
				estadios  = new EstadioDAO().listar();
				return null;
			}
			@Override protected void done() {
				try {
					get();
					for (Jugador j : jugadores) {
						comboJ1.addItem(j);
						comboJ2.addItem(j);
					}
					if (comboJ2.getItemCount() > 1) comboJ2.setSelectedIndex(1);
					for (Estadio e : estadios) comboEstadio.addItem(e);
				} catch (Exception ex) { ex.printStackTrace(); }
			}
		}.execute();
	}

	/**
	 * Valida la selección actual y lanza la batalla si ambos mazos tienen 10 cartas.
	 * La verificación de mazos se realiza en background para no bloquear la interfaz.
	 */
	private void iniciar() {
		Jugador j1 = (Jugador) comboJ1.getSelectedItem();
		Jugador j2 = (Jugador) comboJ2.getSelectedItem();
		Estadio estadio = (Estadio) comboEstadio.getSelectedItem();

		if (j1 == null || j2 == null || estadio == null) return;

		if (j1.getId_jugador() == j2.getId_jugador()) {
			JOptionPane.showMessageDialog(this,
				"Selecciona dos jugadores diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		btnIniciar.setEnabled(false);
		btnIniciar.setText("Verificando mazos...");

		new SwingWorker<String, Void>() {
			@Override protected String doInBackground() {
				MazoDAO  mazoDAO  = new MazoDAO();
				CartaDAO cartaDAO = new CartaDAO();

				List<Mazo> mazos1 = mazoDAO.listarPorJugador(j1.getId_jugador());
				if (mazos1.isEmpty() || cartaDAO.listarPorMazo(mazos1.get(0).getId_mazo()).size() < 10)
					return j1.getApodo() + " no tiene el mazo completo (necesita 10 cartas).";

				List<Mazo> mazos2 = mazoDAO.listarPorJugador(j2.getId_jugador());
				if (mazos2.isEmpty() || cartaDAO.listarPorMazo(mazos2.get(0).getId_mazo()).size() < 10)
					return j2.getApodo() + " no tiene el mazo completo (necesita 10 cartas).";

				return null; // todo OK
			}
			@Override protected void done() {
				try {
					String error = get();
					if (error != null) {
						JOptionPane.showMessageDialog(SeleccionJugadoresView.this,
							error, "Mazo incompleto", JOptionPane.ERROR_MESSAGE);
						btnIniciar.setEnabled(true);
						btnIniciar.setText("INICIAR PARTIDA");
					} else {
						controller.lanzarBatalla(j1, j2, estadio);
					}
				} catch (Exception ex) { ex.printStackTrace(); }
			}
		}.execute();
	}
}
