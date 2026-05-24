package view;

import controller.MainController;
import dao.CartaDAO;
import dao.ElementoDAO;
import dao.Interaccion_elementoDAO;
import dao.MazoDAO;
import dao.PartidaDAO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import modulos.Carta;
import modulos.Elemento;
import modulos.Estadio;
import modulos.Interaccion_elemento;
import modulos.Jugador;
import modulos.Mazo;
import modulos.Partida;
import utils.ConstruirCarta;
import utils.ImageUtils;
import utils.config;

public class PartidaView extends JPanel implements config {

	private static final int VIDA_INICIAL = 100;
	private static final int CARD_W       = 160;
	private static final int CARD_H       = 224;
	private static final int CARD_GAP     = 8;
	private static final int DECK_Y       = 300;
	private static final int BAR_W        = 420;
	private static final int BAR_H        = 18;

	// ── Estado de juego ───────────────────────────────────────────────────
	private int vidaJ1   = VIDA_INICIAL, vidaJ2   = VIDA_INICIAL;
	private int escudoJ1 = 0,            escudoJ2 = 0;
	private final List<int[]> escudosActivos = new ArrayList<>();
	private int turnoActual = 1;
	private int fase = 0; // 0 = J1 elige, 1 = J2 elige

	private final List<Carta> seleccionJ1 = new ArrayList<>();
	private final List<Carta> seleccionJ2 = new ArrayList<>();
	private List<Carta> mazoJ1 = new ArrayList<>();
	private List<Carta> mazoJ2 = new ArrayList<>();

	private final Map<Integer, JPanel> mazoWrappers = new HashMap<>();

	// ── Interacciones de elementos ────────────────────────────────────────
	private final Map<String, Double> interacciones  = new HashMap<>();
	private final Map<Integer, String> nombreElemento = new HashMap<>();
	private int elementoActivo = 0;

	// ── Refs UI ───────────────────────────────────────────────────────────
	private JPanel  barraJ1, barraJ2;
	private JLabel  lblVidaJ1, lblVidaJ2, lblEscudoJ1, lblEscudoJ2;
	private JLabel  lblTurno, lblFase, lblMana, lblEstadio;
	private JPanel  panelChips, panelMazo;

	private final Jugador  j1, j2;
	private final Estadio  estadio;
	private final MainController controller;

	public PartidaView(MainController c, Jugador j1, Jugador j2, Estadio estadio) {
		this.controller = c;
		this.j1      = j1;
		this.j2      = j2;
		this.estadio = estadio;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		buildUI();

		// ── Carga de mazos en background ──────────────────────────────────
		new SwingWorker<Void, Void>() {
			private List<Carta> c1, c2;
			private int m1id = -1, m2id = -1;

			@Override protected Void doInBackground() {
				MazoDAO  mazoDAO  = new MazoDAO();
				CartaDAO cartaDAO = new CartaDAO();
				List<Mazo> mazos1 = mazoDAO.listarPorJugador(j1.getId_jugador());
				List<Mazo> mazos2 = mazoDAO.listarPorJugador(j2.getId_jugador());
				if (!mazos1.isEmpty()) { m1id = mazos1.get(0).getId_mazo(); c1 = cartaDAO.listarPorMazo(m1id); }
				else c1 = new ArrayList<>();
				if (!mazos2.isEmpty()) { m2id = mazos2.get(0).getId_mazo(); c2 = cartaDAO.listarPorMazo(m2id); }
				else c2 = new ArrayList<>();
				for (Interaccion_elemento ie : new Interaccion_elementoDAO().listar())
					interacciones.put(ie.getId_elem_atacante() + "_" + ie.getId_elem_defensor(), ie.getMultiplicador());
				for (Elemento el : new ElementoDAO().listar())
					nombreElemento.put(el.getId_elemento(), el.getNombre());
				ConstruirCarta.ConstruirCartasDefault();
				return null;
			}
			@Override protected void done() {
				try {
					get();
					mazoJ1  = c1;  mazoJ2  = c2;
					elementoActivo = estadio.getId_elemento_activo();
					String nomElem = nombreElemento.getOrDefault(elementoActivo, "");
					if (!nomElem.isEmpty())
						lblEstadio.setText("" + estadio.getNombre() + " [" + nomElem + "]");
					actualizarMazo();
				} catch (Exception ex) { ex.printStackTrace(); }
			}
		}.execute();
	}

	// ═══════════════════════════════════════════════════════════════════════
	//  CONSTRUCCIÓN DE LA UI
	// ═══════════════════════════════════════════════════════════════════════

	private void buildUI() {
		// ── Cabecera J1 (izquierda) ───────────────────────────────────────
		JLabel nombreJ1 = new JLabel(j1.getApodo());
		nombreJ1.setBounds(20, 8, 380, 32);
		nombreJ1.setForeground(new Color(80, 220, 80));
		nombreJ1.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 26f));
		add(nombreJ1);

		barraJ1 = crearBarra(true);
		barraJ1.setBounds(20, 44, BAR_W, BAR_H);
		add(barraJ1);

		lblVidaJ1 = new JLabel("HP " + vidaJ1 + "/" + VIDA_INICIAL);
		lblVidaJ1.setBounds(20, 65, 180, 18);
		lblVidaJ1.setForeground(Color.WHITE);
		lblVidaJ1.setFont(new Font("Arial", Font.BOLD, 13));
		add(lblVidaJ1);

		lblEscudoJ1 = new JLabel("Esc 0");
		lblEscudoJ1.setBounds(210, 65, 120, 18);
		lblEscudoJ1.setForeground(new Color(140, 190, 255));
		lblEscudoJ1.setFont(new Font("Arial", Font.BOLD, 13));
		add(lblEscudoJ1);

		// ── Cabecera J2 (derecha) ─────────────────────────────────────────
		JLabel nombreJ2 = new JLabel(j2.getApodo(), JLabel.RIGHT);
		nombreJ2.setBounds(ANCHO - 400, 8, 380, 32);
		nombreJ2.setForeground(new Color(220, 80, 80));
		nombreJ2.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 26f));
		add(nombreJ2);

		barraJ2 = crearBarra(false);
		barraJ2.setBounds(ANCHO - BAR_W - 20, 44, BAR_W, BAR_H);
		add(barraJ2);

		lblVidaJ2 = new JLabel("HP " + vidaJ2 + "/" + VIDA_INICIAL, JLabel.RIGHT);
		lblVidaJ2.setBounds(ANCHO - 200, 65, 180, 18);
		lblVidaJ2.setForeground(Color.WHITE);
		lblVidaJ2.setFont(new Font("Arial", Font.BOLD, 13));
		add(lblVidaJ2);

		lblEscudoJ2 = new JLabel("Esc 0", JLabel.RIGHT);
		lblEscudoJ2.setBounds(ANCHO - 330, 65, 120, 18);
		lblEscudoJ2.setForeground(new Color(140, 190, 255));
		lblEscudoJ2.setFont(new Font("Arial", Font.BOLD, 13));
		add(lblEscudoJ2);

		// ── Turno, fase y estadio (centro) ────────────────────────────────
		lblTurno = new JLabel("TURNO 1", JLabel.CENTER);
		lblTurno.setBounds(460, 5, 520, 38);
		lblTurno.setForeground(new Color(255, 210, 30));
		lblTurno.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 32f));
		add(lblTurno);

		lblFase = new JLabel("Elige tus cartas: " + j1.getApodo(), JLabel.CENTER);
		lblFase.setBounds(460, 44, 520, 22);
		lblFase.setForeground(new Color(200, 200, 200));
		lblFase.setFont(new Font("Arial", Font.BOLD, 13));
		add(lblFase);

		lblEstadio = new JLabel("" + estadio.getNombre(), JLabel.CENTER);
		lblEstadio.setBounds(460, 68, 520, 18);
		lblEstadio.setForeground(new Color(160, 160, 220));
		lblEstadio.setFont(new Font("Arial", Font.PLAIN, 12));
		add(lblEstadio);

		// ── Línea separadora ──────────────────────────────────────────────
		JPanel sep = new JPanel();
		sep.setBackground(new Color(80, 80, 80));
		sep.setBounds(0, 95, ANCHO, 2);
		add(sep);

		// ── Mana ──────────────────────────────────────────────────────────
		lblMana = new JLabel("Mana disponible: 3   |   Mana gastado: 0", JLabel.CENTER);
		lblMana.setBounds(0, 103, ANCHO, 28);
		lblMana.setForeground(new Color(160, 160, 255));
		lblMana.setFont(new Font("Arial", Font.BOLD, 15));
		add(lblMana);

		// ── Cartas seleccionadas ───────────────────────────────────────────
		JLabel lblSel = new JLabel("Cartas seleccionadas este turno:", JLabel.CENTER);
		lblSel.setBounds(0, 136, ANCHO, 22);
		lblSel.setForeground(new Color(180, 180, 180));
		lblSel.setFont(new Font("Arial", Font.PLAIN, 12));
		add(lblSel);

		panelChips = new JPanel(null);
		panelChips.setOpaque(false);
		panelChips.setBounds(0, 160, ANCHO, 80);
		add(panelChips);

		// ── Divisor mazo ──────────────────────────────────────────────────
		JLabel lblDivider = new JLabel("── Tu Mazo ──", JLabel.CENTER);
		lblDivider.setBounds(0, 270, ANCHO, 28);
		lblDivider.setForeground(new Color(190, 190, 190));
		lblDivider.setFont(new Font("Arial", Font.BOLD, 13));
		add(lblDivider);

		// ── Botón confirmar + terminar (esquina inferior derecha) ────────────
		// Añadidos antes de panelMazo para que queden delante en z-order
		JButton btnConfirmar = new JButton("CONFIRMAR TURNO");
		btnConfirmar.setBounds(ANCHO - 260, ALTO - 200, 195, 45);
		btnConfirmar.setBackground(new Color(35, 110, 35));
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.setBorderPainted(false);
		btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfirmar.addActionListener(e -> confirmarFase());
		add(btnConfirmar);

		JButton btnTerminar = new JButton("Terminar partida");
		btnTerminar.setBounds(ANCHO - 260, ALTO - 145, 195, 45);
		btnTerminar.setBackground(new Color(140, 30, 30));
		btnTerminar.setForeground(Color.WHITE);
		btnTerminar.setFont(new Font("Arial", Font.BOLD, 14));
		btnTerminar.setFocusPainted(false);
		btnTerminar.setBorderPainted(false);
		btnTerminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTerminar.addActionListener(e -> {
			int op = JOptionPane.showConfirmDialog(PartidaView.this,
				"⚠️ La partida no ha acabado.\nSe perderá todo el progreso.\n¿Deseas continuar?",
				"Abandonar partida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (op == JOptionPane.YES_OPTION) controller.lanzarMenuPrincipal();
		});
		add(btnTerminar);

		// ── Panel de cartas del mazo (2 filas × 5) ────────────────────────
		panelMazo = new JPanel(null);
		panelMazo.setOpaque(false);
		panelMazo.setBounds(0, DECK_Y, ANCHO, 2 * CARD_H + CARD_GAP);
		add(panelMazo);

		// ── Overlay + fondo ───────────────────────────────────────────────
		JPanel overlay = new JPanel() {
			@Override protected void paintComponent(Graphics g) {
				g.setColor(new Color(0, 0, 0, 130));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		overlay.setOpaque(false);
		overlay.setBounds(0, 0, ANCHO, ALTO);
		add(overlay);

		JLabel fondo = new JLabel(ImageUtils.cargarImagen(FONDO_DEFAULT, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);
	}

	private JPanel crearBarra(boolean esJ1) {
		return new JPanel() {
			@Override protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int vida = esJ1 ? vidaJ1 : vidaJ2;
				g2.setColor(new Color(40, 40, 40, 200));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
				float pct = Math.max(0f, (float) vida / VIDA_INICIAL);
				int fw = (int)(getWidth() * pct);
				if (fw > 0) {
					Color c = pct > 0.5f ? new Color(50, 200, 50)
					        : pct > 0.25f ? new Color(220, 180, 0)
					        : new Color(200, 50, 50);
					g2.setColor(c);
					g2.fillRoundRect(0, 0, fw, getHeight(), 6, 6);
				}
				g2.dispose();
			}
		};
	}

	// ═══════════════════════════════════════════════════════════════════════
	//  MAZO
	// ═══════════════════════════════════════════════════════════════════════

	private void actualizarMazo() {
		panelMazo.removeAll();
		mazoWrappers.clear();

		List<Carta> mazoActual = (fase == 0) ? mazoJ1 : mazoJ2;
		List<Carta> selActual  = (fase == 0) ? seleccionJ1 : seleccionJ2;

		int perRow  = 5;
		int totalW  = perRow * CARD_W + (perRow - 1) * CARD_GAP;
		int startX  = (ANCHO - totalW) / 2;

		for (int i = 0; i < mazoActual.size(); i++) {
			final Carta    carta = mazoActual.get(i);
			final boolean[] sel  = { selActual.contains(carta) };

			JLabel cardLabel = ConstruirCarta.insertarCarta(carta, CARD_W, CARD_H);
			cardLabel.setBounds(0, 0, CARD_W, CARD_H);

			JPanel wrapper = new JPanel(null) {
				@Override public void paint(Graphics g) {
					super.paint(g);
					if (sel[0]) {
						Graphics2D g2 = (Graphics2D) g.create();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setColor(new Color(255, 210, 30, 70));
						g2.fillRect(0, 0, getWidth(), getHeight());
						g2.setColor(new Color(255, 210, 30));
						g2.setStroke(new BasicStroke(3f));
						g2.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
						g2.dispose();
					}
				}
			};
			wrapper.setOpaque(false);
			int col = i % perRow;
			int row = i / perRow;
			wrapper.setBounds(startX + col * (CARD_W + CARD_GAP), row * (CARD_H + CARD_GAP), CARD_W, CARD_H);
			wrapper.add(cardLabel);
			wrapper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			mazoWrappers.put(carta.getId_carta(), wrapper);

			MouseAdapter ml = new MouseAdapter() {
				public void mouseReleased(MouseEvent e) { toggleCarta(carta, sel, wrapper); }
			};
			wrapper.addMouseListener(ml);
			cardLabel.addMouseListener(ml);
			for (Component child : cardLabel.getComponents()) child.addMouseListener(ml);

			panelMazo.add(wrapper);
		}
		panelMazo.revalidate();
		panelMazo.repaint();
	}

	private void toggleCarta(Carta carta, boolean[] sel, JPanel wrapper) {
		List<Carta> selActual  = (fase == 0) ? seleccionJ1 : seleccionJ2;
		int manaDisponible     = turnoActual + 2;
		int manaGastado        = selActual.stream().mapToInt(Carta::getCoste_mana).sum();

		if (!sel[0]) {
			if (manaGastado + carta.getCoste_mana() > manaDisponible) return;
			selActual.add(carta);
			sel[0] = true;
		} else {
			selActual.remove(carta);
			sel[0] = false;
		}
		wrapper.repaint();
		actualizarChips();
		actualizarManaLabel();
	}

	// ═══════════════════════════════════════════════════════════════════════
	//  LABELS
	// ═══════════════════════════════════════════════════════════════════════

	private void actualizarChips() {
		panelChips.removeAll();
		List<Carta> selActual = (fase == 0) ? seleccionJ1 : seleccionJ2;

		int chipW = 160, chipH = 60, gap = 8;
		int totalW = selActual.size() * (chipW + gap) - (selActual.isEmpty() ? 0 : gap);
		int startX = Math.max(0, (ANCHO - totalW) / 2);

		for (int i = 0; i < selActual.size(); i++) {
			Carta carta = selActual.get(i);
			JPanel chip = new JPanel(null) {
				@Override protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(new Color(20, 20, 50, 210));
					g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
					g2.setColor(new Color(255, 210, 30));
					g2.setStroke(new BasicStroke(1.5f));
					g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
					g2.dispose();
				}
			};
			chip.setOpaque(false);
			chip.setBounds(startX + i * (chipW + gap), 5, chipW, chipH);

			JLabel nombre = new JLabel(carta.getNombre(), JLabel.CENTER);
			nombre.setBounds(4, 4, chipW - 8, 22);
			nombre.setForeground(Color.WHITE);
			nombre.setFont(new Font("Arial", Font.BOLD, 11));
			chip.add(nombre);

			String tipo = carta.getTipo();
			Color colorTipo = "OFENSIVA".equals(tipo) ? new Color(255, 100, 100)
			                : "DEFENSIVA".equals(tipo) ? new Color(100, 180, 255)
			                : new Color(200, 200, 100);
			JLabel info = new JLabel(tipo + " | " + carta.getCoste_mana() + " mana | vel " + carta.getVelocidad(), JLabel.CENTER);
			info.setBounds(4, 28, chipW - 8, 16);
			info.setForeground(colorTipo);
			info.setFont(new Font("Arial", Font.PLAIN, 10));
			chip.add(info);

			panelChips.add(chip);
		}
		panelChips.revalidate();
		panelChips.repaint();
	}

	private void actualizarManaLabel() {
		List<Carta> selActual = (fase == 0) ? seleccionJ1 : seleccionJ2;
		int manaDisponible    = turnoActual + 2;
		int gastado           = selActual.stream().mapToInt(Carta::getCoste_mana).sum();
		lblMana.setText("Mana disponible: " + manaDisponible + "   |   Mana gastado: " + gastado);
	}

	private void actualizarHP() {
		escudoJ1 = getEscudoActivo(1);
		escudoJ2 = getEscudoActivo(2);
		barraJ1.repaint();
		barraJ2.repaint();
		lblVidaJ1.setText("HP " + vidaJ1 + "/" + VIDA_INICIAL);
		lblVidaJ2.setText("HP " + vidaJ2 + "/" + VIDA_INICIAL);
		lblEscudoJ1.setText("Esc " + escudoJ1);
		lblEscudoJ2.setText("Esc " + escudoJ2);
	}

	// ═══════════════════════════════════════════════════════════════════════
	//  FLUJO DE TURNO
	// ═══════════════════════════════════════════════════════════════════════

	private void confirmarFase() {
		if (fase == 0) {
			fase = 1;
			lblFase.setText("Elige tus cartas: " + j2.getApodo());
			panelChips.removeAll();
			panelChips.repaint();
			actualizarManaLabel();
			actualizarMazo();
		} else {
			resolverTurno();
		}
	}

	private int getEscudoActivo(int jugador) {
		return escudosActivos.stream()
			.filter(e -> e[0] == jugador && turnoActual >= e[2] && turnoActual < e[2] + e[3])
			.mapToInt(e -> e[1])
			.sum();
	}

	/** Aplica daño depletando escudos activos en orden de inserción. Devuelve cantidad bloqueada. */
	private int aplicarDanoConEscudo(int dano, int objetivo) {
		int restante = dano;
		for (int[] e : escudosActivos) {
			if (e[0] == objetivo && turnoActual >= e[2] && turnoActual < e[2] + e[3] && restante > 0) {
				int absorbido = Math.min(restante, e[1]);
				e[1] -= absorbido;
				restante -= absorbido;
			}
		}
		if (objetivo == 1) vidaJ1 = Math.max(0, vidaJ1 - restante);
		else               vidaJ2 = Math.max(0, vidaJ2 - restante);
		return dano - restante;
	}

	private void resolverTurno() {
		escudoJ1 = getEscudoActivo(1);
		escudoJ2 = getEscudoActivo(2);

		List<Object[]> jugadas = new ArrayList<>();
		for (Carta c : seleccionJ1) jugadas.add(new Object[]{ c, 1 });
		for (Carta c : seleccionJ2) jugadas.add(new Object[]{ c, 2 });
		jugadas.sort(Comparator.comparingInt((Object[] o) -> ((Carta) o[0]).getVelocidad()).reversed());

		StringBuilder log = new StringBuilder();
		log.append("══════ TURNO ").append(turnoActual).append(" — Resolución ══════\n\n");

		for (Object[] jugada : jugadas) {
			Carta  c     = (Carta) jugada[0];
			int    dueno = (Integer) jugada[1];
			String quien = (dueno == 1) ? j1.getApodo() : j2.getApodo();

			if ("OFENSIVA".equals(c.getTipo())) {
				int danoBase  = c.getDano();
				double mult   = interacciones.getOrDefault(c.getId_elemento() + "_" + elementoActivo, 1.0);
				int dano      = (int) Math.round(danoBase * mult);
				int objetivo  = (dueno == 1) ? 2 : 1;
				int bloqueado = aplicarDanoConEscudo(dano, objetivo);
				int real      = dano - bloqueado;
				String victima = (objetivo == 1) ? "J1" : "J2";
				log.append("[ATQ] ").append(quien).append(" → ").append(c.getNombre())
				   .append(" [vel ").append(c.getVelocidad()).append("]: ").append(danoBase).append(" dmg");
				if (mult != 1.0) log.append(" x").append(mult)
				                    .append(" (elem) = ").append(dano).append(" dmg");
				if (bloqueado > 0) log.append(" (").append(bloqueado).append(" bloq.)");
				log.append(" → ").append(victima).append(" -").append(real).append(" vida\n");

			} else if ("DEFENSIVA".equals(c.getTipo())) {
				int escudoBase = c.getEscudo();
				double mult    = interacciones.getOrDefault(c.getId_elemento() + "_" + elementoActivo, 1.0);
				int escudo     = (int) Math.round(escudoBase * mult);
				escudosActivos.add(new int[]{ dueno, escudo, turnoActual, c.getDuracion() });
				if (dueno == 1) escudoJ1 += escudo;
				else            escudoJ2 += escudo;
				log.append("[ESC] ").append(quien).append(" → ").append(c.getNombre())
				   .append(" [vel ").append(c.getVelocidad()).append("]: +").append(escudoBase);
				if (mult != 1.0) log.append(" x").append(mult).append(" (elem) = +").append(escudo);
				log.append(" escudo (").append(c.getDuracion()).append(" turnos)\n");

			} else { // ESTADO: cambia el elemento activo del campo
				elementoActivo = c.getId_elemento();
				String nomElem = nombreElemento.getOrDefault(elementoActivo, "Elem " + elementoActivo);
				lblEstadio.setText("" + estadio.getNombre() + " [" + nomElem + "]");
				log.append("[EST] ").append(quien).append(" → ").append(c.getNombre())
				   .append(" [vel ").append(c.getVelocidad()).append("]: campo cambia a ").append(nomElem).append("\n");
			}
		}

		if (jugadas.isEmpty()) log.append("(Ningún jugador jugó cartas este turno)\n");

		int escJ1 = getEscudoActivo(1);
		int escJ2 = getEscudoActivo(2);
		log.append("\n─────────────────────────────────\n");
		log.append(j1.getApodo()).append(": HP ").append(vidaJ1).append("/").append(VIDA_INICIAL);
		if (escJ1 > 0) log.append("  Esc ").append(escJ1);
		log.append("     ").append(j2.getApodo()).append(": HP ").append(vidaJ2).append("/").append(VIDA_INICIAL);
		if (escJ2 > 0) log.append("  Esc ").append(escJ2);

		actualizarHP();

		JOptionPane.showMessageDialog(this, log.toString(),
			"Resolución — Turno " + turnoActual, JOptionPane.PLAIN_MESSAGE);

		if (vidaJ1 <= 0 || vidaJ2 <= 0) {
			Jugador ganador = (vidaJ1 <= 0 && vidaJ2 <= 0) ? j1
			                : (vidaJ2 <= 0) ? j1 : j2;
			guardarPartida(ganador);
			finPartida(ganador);
			return;
		}

		turnoActual++;
		seleccionJ1.clear();
		seleccionJ2.clear();
		fase = 0;
		lblTurno.setText("TURNO " + turnoActual);
		lblFase.setText("Elige tus cartas: " + j1.getApodo());
		panelChips.removeAll();
		panelChips.repaint();
		actualizarHP();
		actualizarManaLabel();
		actualizarMazo();
	}

	private void guardarPartida(Jugador ganador) {
		final int turnosJugados = turnoActual;
		new SwingWorker<Void, Void>() {
			@Override protected Void doInBackground() {
				Partida p = new Partida(
					j1.getId_jugador(), j2.getId_jugador(),
					estadio.getId_estadio()
				);
				p.setId_ganador(ganador.getId_jugador());
				p.setNum_turnos(turnosJugados);
				new PartidaDAO().insertar(p);
				return null;
			}
		}.execute();
	}

	private void finPartida(Jugador ganador) {
		int op = JOptionPane.showConfirmDialog(this,
			"🏆 ¡" + ganador.getApodo() + " ha ganado la partida!\n\n¿Volver al menú principal?",
			"Fin de partida", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (op == JOptionPane.YES_OPTION) controller.lanzarMenuPrincipal();
	}
}
