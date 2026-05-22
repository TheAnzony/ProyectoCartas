package view;

import controller.MainController;
import dao.EstadioDAO;
import dao.JugadorDAO;
import dao.PartidaDAO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import modulos.Estadio;
import modulos.Jugador;
import modulos.Partida;
import utils.ImageUtils;
import utils.config;

/**
 * Pantalla del historial de partidas.
 * Muestra todas las partidas registradas en una tabla con columnas de
 * Fecha, Jugador 1, VS, Jugador 2, Ganador, Turnos y Estadio.
 * Los datos se cargan en background desde la base de datos.
 */
public class HistorialView extends JPanel implements config {

	/** Formato de fecha para mostrar en la tabla de partidas. */
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");

	private final MainController controller;

	/**
	 * Construye la vista del historial con cabecera, lista scrollable y fondo.
	 *
	 * @param c Controlador principal de la aplicación.
	 */
	public HistorialView(MainController c) {
		this.controller = c;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		// ── Título ────────────────────────────────────────────────────────
		JLabel titulo = new JLabel("Historial de Partidas", JLabel.CENTER);
		titulo.setBounds(0, 25, ANCHO, 60);
		titulo.setForeground(new Color(255, 195, 30));
		titulo.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 48f));
		add(titulo);

		// ── Cabecera de columnas ──────────────────────────────────────────
		JPanel header = crearCabecera();
		header.setBounds((ANCHO - 1100) / 2, 100, 1100, 35);
		add(header);

		// ── Lista de partidas ─────────────────────────────────────────────
		JPanel lista = new JPanel(null);
		lista.setOpaque(false);

		JScrollPane scroll = new JScrollPane(lista);
		scroll.setBounds((ANCHO - 1100) / 2, 140, 1100, ALTO - 200);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll);

		// ── Botón volver ──────────────────────────────────────────────────
		ImageIcon iconoBack    = ImageUtils.cargarImagen(ARROW_BACK, 50, 50);
		ImageIcon iconoBackOsc = ImageUtils.oscurecerImagen(iconoBack);
		JLabel btnVolver = new JLabel(iconoBack);
		btnVolver.setBounds(15, 15, 50, 50);
		btnVolver.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		btnVolver.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)  { btnVolver.setIcon(iconoBackOsc); }
			public void mouseExited(MouseEvent e)   { btnVolver.setIcon(iconoBack); }
			public void mouseReleased(MouseEvent e) { controller.lanzarMenuPrincipal(); }
		});
		add(btnVolver);

		// ── Overlay + fondo ───────────────────────────────────────────────
		JPanel overlay = new JPanel() {
			@Override protected void paintComponent(Graphics g) {
				g.setColor(new Color(0, 0, 0, 120));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		overlay.setOpaque(false);
		overlay.setBounds(0, 0, ANCHO, ALTO);
		add(overlay);

		JLabel fondo = new JLabel(ImageUtils.cargarImagen(FONDO_DEFAULT, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		// ── Carga en background ───────────────────────────────────────────
		new SwingWorker<Void, Void>() {
			private List<Partida>       partidas;
			private Map<Integer,String> jugMap   = new HashMap<>();
			private Map<Integer,String> estadMap = new HashMap<>();

			@Override protected Void doInBackground() {
				partidas = new PartidaDAO().listarTodas();
				for (Jugador j : new JugadorDAO().listar())
					jugMap.put(j.getId_jugador(), j.getApodo());
				for (Estadio e : new EstadioDAO().listar())
					estadMap.put(e.getId_estadio(), e.getNombre());
				return null;
			}

			@Override protected void done() {
				try {
					get();
					if (partidas.isEmpty()) {
						JLabel vacio = new JLabel("No hay partidas registradas todavía.", JLabel.CENTER);
						vacio.setForeground(Color.LIGHT_GRAY);
						vacio.setFont(new Font("Arial", Font.ITALIC, 18));
						vacio.setBounds(0, 40, 1100, 30);
						lista.add(vacio);
						lista.setPreferredSize(new Dimension(1100, 100));
					} else {
						int y = 0;
						for (Partida p : partidas) {
							JPanel fila = crearFila(p, jugMap, estadMap);
							fila.setBounds(0, y, 1100, 55);
							lista.add(fila);
							y += 61;
						}
						lista.setPreferredSize(new Dimension(1100, y));
					}
					lista.revalidate();
					lista.repaint();
				} catch (Exception ex) { ex.printStackTrace(); }
			}
		}.execute();
	}

	/**
	 * Crea el panel de cabecera con las etiquetas de columna.
	 *
	 * @return Panel con las cabeceras de la tabla.
	 */
	private JPanel crearCabecera() {
		JPanel cab = new JPanel(null);
		cab.setOpaque(false);
		Font f = new Font("Arial", Font.BOLD, 13);
		Color c = new Color(200, 200, 200);
		agregarLabel(cab, "Fecha",    0,   140, f, c, JLabel.LEFT);
		agregarLabel(cab, "Jugador 1",160,  170, f, c, JLabel.LEFT);
		agregarLabel(cab, "VS",       270,   80, f, c, JLabel.CENTER);
		agregarLabel(cab, "Jugador 2",400,  180, f, c, JLabel.LEFT);
		agregarLabel(cab, "Ganador",  620,  200, f, new Color(255, 210, 30), JLabel.LEFT);
		agregarLabel(cab, "Turnos",   840,   80, f, c, JLabel.CENTER);
		agregarLabel(cab, "Estadio",  940,  160, f, c, JLabel.LEFT);
		return cab;
	}

	/**
	 * Crea el panel de una fila de partida con fondo redondeado y los datos formateados.
	 * El ganador se resalta en verde y en negrita.
	 *
	 * @param p        Partida a representar.
	 * @param jugMap   Mapa de {@code id_jugador → apodo} para resolver nombres.
	 * @param estadMap Mapa de {@code id_estadio → nombre} para resolver el estadio.
	 * @return Panel con los datos de la partida listos para añadir a la lista.
	 */
	private JPanel crearFila(Partida p, Map<Integer,String> jugMap, Map<Integer,String> estadMap) {
		JPanel fila = new JPanel(null) {
			@Override protected void paintComponent(Graphics g) {
				Graphics2D g2 = (java.awt.Graphics2D) g.create();
				g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(15, 15, 35, 200));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				g2.setColor(new Color(70, 70, 100));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
				g2.dispose();
			}
		};
		fila.setOpaque(false);
		fila.setMaximumSize(new Dimension(1100, 55));
		fila.setPreferredSize(new Dimension(1100, 55));

		Font fNormal = new Font("Arial", Font.PLAIN, 13);
		Font fBold   = new Font("Arial", Font.BOLD,  13);

		String apodo1   = jugMap.getOrDefault(p.getId_jugador1(), "Jugador " + p.getId_jugador1());
		String apodo2   = jugMap.getOrDefault(p.getId_jugador2(), "Jugador " + p.getId_jugador2());
		String ganador  = p.getId_ganador() == null ? "─" : jugMap.getOrDefault(p.getId_ganador(), "?");
		String estadio  = p.getId_estadio() == 0 ? "─" : estadMap.getOrDefault(p.getId_estadio(), "─");
		String fecha    = p.getFecha() != null ? p.getFecha().format(FMT) : "─";
		String turnos   = p.getNum_turnos() == 0 ? "─" : p.getNum_turnos() + "";
		boolean j1gano  = p.getId_ganador() != null && p.getId_ganador() == p.getId_jugador1();
		boolean j2gano  = p.getId_ganador() != null && p.getId_ganador() == p.getId_jugador2();

		agregarLabel(fila, fecha,    10,  140, fNormal, new Color(180,180,180), JLabel.LEFT);
		agregarLabel(fila, apodo1,  160,  170, j1gano ? fBold : fNormal, j1gano ? new Color(80,220,80) : Color.WHITE, JLabel.LEFT);
		agregarLabel(fila, "vs",    270,   80, fNormal, new Color(150,150,150), JLabel.CENTER);
		agregarLabel(fila, apodo2,  400,  180, j2gano ? fBold : fNormal, j2gano ? new Color(80,220,80) : Color.WHITE, JLabel.LEFT);
		agregarLabel(fila, ganador, 620,  200, fBold,   new Color(255, 210, 30), JLabel.LEFT);
		agregarLabel(fila, turnos,  840,   80, fNormal, new Color(180,180,180), JLabel.CENTER);
		agregarLabel(fila, estadio, 940,  155, fNormal, new Color(160,160,210), JLabel.LEFT);

		return fila;
	}

	/**
	 * Añade un {@link JLabel} posicionado absolutamente a un panel.
	 *
	 * @param p     Panel destino.
	 * @param texto Texto a mostrar.
	 * @param x     Posición horizontal del label dentro del panel.
	 * @param w     Ancho del label en píxeles.
	 * @param f     Fuente del texto.
	 * @param c     Color del texto.
	 * @param align Alineación horizontal ({@link JLabel#LEFT}, {@link JLabel#CENTER}, etc.).
	 */
	private void agregarLabel(JPanel p, String texto, int x, int w, Font f, Color c, int align) {
		JLabel lbl = new JLabel(texto, align);
		lbl.setBounds(x, 0, w, p.getPreferredSize() != null ? 55 : 35);
		lbl.setForeground(c);
		lbl.setFont(f);
		p.add(lbl);
	}
}
