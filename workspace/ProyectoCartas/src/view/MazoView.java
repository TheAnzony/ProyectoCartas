package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.MainController;
import dao.CartaDAO;
import dao.MazoDAO;
import dao.Mazo_cartaDAO;
import modulos.Carta;
import modulos.Jugador;
import modulos.Mazo;
import modulos.Mazo_carta;
import utils.ConstruirCarta;
import utils.ImageUtils;
import utils.config;

/**
 * Pantalla de edición del mazo de un jugador.
 * Muestra 10 slots en la parte superior con las cartas actuales del mazo,
 * y en la parte inferior un grid con todas las cartas disponibles.
 * Hacer clic en una carta del grid la añade al primer slot libre;
 * hacer clic en un slot lleno elimina esa carta del mazo.
 * Si el jugador no tiene mazo, se crea uno vacío automáticamente.
 */
public class MazoView extends JPanel implements config {

	/** Número máximo de cartas por mazo. */
	private static final int NUM_SLOTS = 10;
	/** Ancho de cada slot de carta en la parte superior. */
	private static final int SLOT_W    = 128;
	/** Alto de cada slot de carta en la parte superior. */
	private static final int SLOT_H    = 179;
	/** Separación horizontal entre slots. */
	private static final int SLOT_GAP  = 8;
	/** Posición vertical de la fila de slots. */
	private static final int SLOT_Y    = 80;
	/** Posición vertical del grid de selección de cartas. */
	private static final int GRID_Y    = SLOT_Y + SLOT_H + 20;

	/** Ancho de las cartas en el grid de selección. */
	private static final int CARD_W = 160;
	/** Alto de las cartas en el grid de selección. */
	private static final int CARD_H = 224;

	private MainController controller;
	private Jugador jugador;
	/** Mazo activo del jugador que se está editando. */
	private Mazo mazoActual;

	/** Cartas actualmente asignadas a cada slot (null = slot vacío). */
	private final Carta[] slotCards = new Carta[NUM_SLOTS];
	/** Paneles de slot correspondientes a cada posición del mazo. */
	private final JPanel[] slots = new JPanel[NUM_SLOTS];
	/** Estado de sombreado de cada carta del grid: {@code true} = ya está en el mazo. */
	private final Map<Integer, boolean[]> shadingState = new HashMap<>();
	/** Paneles wrapper de cada carta en el grid, indexados por {@code id_carta}. */
	private final Map<Integer, JPanel> gridWrappers = new HashMap<>();

	/**
	 * Construye el editor de mazo para el jugador indicado.
	 * La carga de datos (mazo y cartas) se realiza en background.
	 *
	 * @param c Controlador principal de la aplicación.
	 * @param j Jugador cuyo mazo se va a editar.
	 */
	public MazoView(MainController c, Jugador j) {
		this.controller = c;
		this.jugador = j;

		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		// ── Botón volver ──────────────────────────────────────────────────
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

			public void mouseReleased(MouseEvent e) {
				controller.lanzarMenuJugador();
			}
		});
		add(btnVolver);

		// ── Título ────────────────────────────────────────────────────────
		JLabel titulo = new JLabel("Mazo de " + jugador.getApodo(), JLabel.CENTER);
		titulo.setBounds(0, 10, ANCHO, 45);
		titulo.setForeground(Color.WHITE);
		titulo.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 58f));
		add(titulo);

		// ── 10 slots centrados ────────────────────────────────────────────
		int totalW = NUM_SLOTS * SLOT_W + (NUM_SLOTS - 1) * SLOT_GAP;
		int startX = (ANCHO - totalW) / 2;

		for (int i = 0; i < NUM_SLOTS; i++) {
			final int idx = i;
			slots[i] = new JPanel(null);
			slots[i].setOpaque(false);
			slots[i].setBounds(startX + i * (SLOT_W + SLOT_GAP), SLOT_Y, SLOT_W, SLOT_H);
			slots[i].addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (slotCards[idx] != null)
						eliminarCartaDeSlot(idx);
				}
			});
			JPanel ph = crearPlaceholder();
			ph.setBounds(0, 0, SLOT_W, SLOT_H);
			slots[i].add(ph);
			add(slots[i]);
		}

		// ── Grid inferior (todas las cartas) ──────────────────────────────
		JPanel gridCartas = new JPanel(new GridLayout(0, 5, 10, 10));
		gridCartas.setOpaque(false);

		int gridW = 5 * CARD_W + 4 * 10;
		JScrollPane scrollCartas = new JScrollPane(gridCartas);
		scrollCartas.setBounds((ANCHO - gridW) / 2, GRID_Y, gridW, ALTO - GRID_Y - 10);
		scrollCartas.setOpaque(false);
		scrollCartas.getViewport().setOpaque(false);
		scrollCartas.setBorder(null);
		scrollCartas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollCartas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollCartas.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollCartas);

		// ── Overlay oscuro sobre el fondo ────────────────────────────────
		// CAPA QUE OSCURECE EL FONDO
		JPanel oscurece = new JPanel();
		oscurece.setBackground(new Color(0, 0, 0, 70));
		oscurece.setOpaque(true);
		oscurece.setBounds(0, 0, ANCHO, ALTO);
		add(oscurece);

		// ── Fondo — siempre el último ─────────────────────────────────────
		JLabel fondo = new JLabel(ImageUtils.cargarImagen(FONDO_DEFAULT, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		// ── Carga de datos en hilo de fondo ───────────────────────────────
		new javax.swing.SwingWorker<Void, Void>() {
			private List<Carta> cartasMazo;
			private List<Carta> todasCartas;

			@Override
			protected Void doInBackground() {
				List<Mazo> mazos = new MazoDAO().listarPorJugador(jugador.getId_jugador());
				if (mazos.isEmpty()) {
					new MazoDAO().insertar(new Mazo(jugador.getId_jugador(), "Mazo de " + jugador.getApodo()));
					mazos = new MazoDAO().listarPorJugador(jugador.getId_jugador());
				}
				mazoActual = mazos.get(0);
				cartasMazo = new CartaDAO().listarPorMazo(mazoActual.getId_mazo());
				todasCartas = new CartaDAO().listar();
				ConstruirCarta.ConstruirCartasDefault();
				return null;
			}

			@Override
			protected void done() {
				try {
					get();

					// Rellenar slots con las cartas del mazo
					Set<Integer> idsEnMazo = new HashSet<>();
					int slotIdx = 0;
					for (Carta carta : cartasMazo) {
						if (slotIdx >= NUM_SLOTS)
							break;
						idsEnMazo.add(carta.getId_carta());
						slotCards[slotIdx] = carta;
						rellenarSlot(slotIdx, carta);
						slotIdx++;
					}

					// Rellenar grid con todas las cartas
					for (Carta carta : todasCartas) {
						boolean[] shaded = { idsEnMazo.contains(carta.getId_carta()) };
						shadingState.put(carta.getId_carta(), shaded);

						JLabel cardLabel = ConstruirCarta.insertarCarta(carta, CARD_W, CARD_H);
						cardLabel.setBounds(0, 0, CARD_W, CARD_H);

						JPanel wrapper = new JPanel(null) {
							@Override
							public void paint(Graphics g) {
								super.paint(g);
								if (shaded[0]) {
									Graphics2D g2 = (Graphics2D) g.create();
									g2.setColor(new Color(0, 0, 0, 160));
									g2.fillRect(0, 0, getWidth(), getHeight());
									g2.dispose();
								}
							}
						};
						wrapper.setOpaque(false);
						wrapper.setPreferredSize(new Dimension(CARD_W, CARD_H));
						wrapper.add(cardLabel);
						wrapper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						gridWrappers.put(carta.getId_carta(), wrapper);

						MouseAdapter ml = new MouseAdapter() {
							public void mouseReleased(MouseEvent e) {
								añadirCartaAlMazo(carta);
							}
						};
						wrapper.addMouseListener(ml);
						cardLabel.addMouseListener(ml);
						for (Component child : cardLabel.getComponents()) {
							child.addMouseListener(ml);
						}

						gridCartas.add(wrapper);
					}

					gridCartas.revalidate();
					gridCartas.repaint();
					javax.swing.SwingUtilities.invokeLater(() -> scrollCartas.getVerticalScrollBar().setValue(0));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.execute();
	}

	/**
	 * Crea un panel placeholder (slot vacío) con fondo oscuro semitransparente,
	 * borde punteado blanco y un símbolo {@code +} centrado.
	 *
	 * @return Panel listo para añadir a un slot vacío.
	 */
	private JPanel crearPlaceholder() {
		JPanel ph = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(0, 0, 0, 120));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
				float[] dash = { 6f, 4f };
				g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, dash, 0));
				g2.setColor(new Color(255, 255, 255, 160));
				g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 12, 12);
				g2.setFont(new Font("Arial", Font.BOLD, 28));
				g2.setColor(new Color(255, 255, 255, 140));
				FontMetrics fm = g2.getFontMetrics();
				String plus = "+";
				g2.drawString(plus, (getWidth() - fm.stringWidth(plus)) / 2,
						(getHeight() + fm.getAscent() - fm.getDescent()) / 2);
				g2.dispose();
			}
		};
		ph.setOpaque(false);
		return ph;
	}

	/**
	 * Rellena el slot {@code idx} con el componente visual de la carta dada.
	 * Añade el listener para eliminarla al hacer clic.
	 *
	 * @param idx   Índice del slot (0–9).
	 * @param carta Carta a mostrar en el slot.
	 */
	private void rellenarSlot(int idx, Carta carta) {
		JPanel slot = slots[idx];
		slot.removeAll();
		slot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JLabel cardImg = ConstruirCarta.insertarCarta(carta, SLOT_W, SLOT_H);
		cardImg.setBounds(0, 0, SLOT_W, SLOT_H);
		cardImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		MouseAdapter ml = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				eliminarCartaDeSlot(idx);
			}
		};
		cardImg.addMouseListener(ml);
		for (Component child : cardImg.getComponents())
			child.addMouseListener(ml);

		slot.add(cardImg);
		slot.revalidate();
		slot.repaint();
	}

	/**
	 * Vacía el slot {@code idx} reemplazándolo por un placeholder.
	 *
	 * @param idx Índice del slot a vaciar (0–9).
	 */
	private void vaciarSlot(int idx) {
		JPanel slot = slots[idx];
		slot.removeAll();
		slot.setCursor(Cursor.getDefaultCursor());
		JPanel ph = crearPlaceholder();
		ph.setBounds(0, 0, SLOT_W, SLOT_H);
		slot.add(ph);
		slot.revalidate();
		slot.repaint();
	}

	/**
	 * Añade la carta seleccionada al primer slot libre del mazo y la persiste en la base de datos.
	 * Si la carta ya está en el mazo o el mazo está lleno (10 cartas), no hace nada.
	 *
	 * @param carta Carta a añadir al mazo.
	 */
	private void añadirCartaAlMazo(Carta carta) {
		boolean[] shaded = shadingState.get(carta.getId_carta());
		if (shaded != null && shaded[0])
			return;

		int idx = -1;
		for (int i = 0; i < NUM_SLOTS; i++) {
			if (slotCards[i] == null) {
				idx = i;
				break;
			}
		}
		if (idx == -1)
			return; // mazo lleno

		boolean ok = new Mazo_cartaDAO().insertar(new Mazo_carta(mazoActual.getId_mazo(), carta.getId_carta()));
		if (ok) {
			slotCards[idx] = carta;
			rellenarSlot(idx, carta);
			if (shaded != null) {
				shaded[0] = true;
				JPanel w = gridWrappers.get(carta.getId_carta());
				if (w != null)
					w.repaint();
			}
		}
	}

	/**
	 * Elimina la carta del slot {@code idx}, la borra de la base de datos y la vuelve
	 * a marcar como disponible (sin sombreado) en el grid de selección.
	 *
	 * @param idx Índice del slot cuya carta se va a eliminar (0–9).
	 */
	private void eliminarCartaDeSlot(int idx) {
		Carta carta = slotCards[idx];
		if (carta == null)
			return;

		boolean ok = new Mazo_cartaDAO().eliminar(mazoActual.getId_mazo(), carta.getId_carta());
		if (ok) {
			slotCards[idx] = null;
			vaciarSlot(idx);
			boolean[] shaded = shadingState.get(carta.getId_carta());
			if (shaded != null) {
				shaded[0] = false;
				JPanel w = gridWrappers.get(carta.getId_carta());
				if (w != null)
					w.repaint();
			}
		}
	}

}
