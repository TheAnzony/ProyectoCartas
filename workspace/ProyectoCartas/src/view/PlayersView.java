package view;

import controller.MainController;
import dao.JugadorDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import modulos.Jugador;
import utils.ImageUtils;
import utils.config;

/**
 * Pantalla de gestión de jugadores.
 * Muestra la lista de jugadores en una cuadrícula de 2 columnas con scroll,
 * y tres botones de imagen en la parte inferior para añadir, eliminar y modificar jugadores.
 * Al hacer clic en un jugador se navega a su editor de mazo ({@link MazoView}).
 */
public class PlayersView extends JPanel implements config {

	private final MainController controller;

	private static final int CARD_W     = 200;
	private static final int CARD_H     = 75;
	private static final int CARD_GAP_H = 20;
	private static final int CARD_GAP_V = 10;
	private static final int GRID_COLS  = 2;
	private static final int GRID_W     = GRID_COLS * CARD_W + (GRID_COLS - 1) * CARD_GAP_H;
	private static final int GRID_X     = (ANCHO - GRID_W) / 2;
	private static final int GRID_Y     = 165;
	private static final int BTN_W      = 300;
	private static final int BTN_H_IMG  = 165;
	private static final int BTN_GAP    = 20;
	private static final int BTN_Y      = ALTO - BTN_H_IMG - 55;
	private static final int GRID_H     = BTN_Y - GRID_Y - 15;

	/**
	 * Construye la vista de jugadores con lista, botones de acción y fondo.
	 * La carga de jugadores desde la base de datos se realiza en background.
	 *
	 * @param c Controlador principal de la aplicación.
	 */
	public PlayersView(MainController c) {
		this.controller = c;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		ImageIcon iconoCaja   = ImageUtils.cargarImagen(BOX_IMAGE, CARD_W, CARD_H - 5);
		ImageIcon iconoAvatar = ImageUtils.cargarImagen(ICONO_LIST, 45, 45);

		// ── Lista de jugadores (grid 2 columnas, centrado) ────────────────────
		JPanel lista = new JPanel(null);
		lista.setOpaque(false);

		JScrollPane scroll = new JScrollPane(lista);
		scroll.setBounds(GRID_X, GRID_Y, GRID_W, GRID_H);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(8);
		add(scroll);

		// ── Título ────────────────────────────────────────────────────────────
		JLabel titulo = new JLabel("Jugadores", JLabel.CENTER);
		titulo.setBounds(0, GRID_Y - 130, ANCHO, 75);
		titulo.setForeground(new Color(255, 195, 30));
		titulo.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 82f));
		add(titulo);

		// ── Botones de acción (imagen, fila horizontal, parte inferior) ───────
		int totalBtnsW = 3 * BTN_W + 2 * BTN_GAP;
		int btnStartX  = (ANCHO - totalBtnsW) / 2;

		add(crearBotonImagen(BTN_ANADIR_JUGADOR,    btnStartX,                        BTN_Y, this::accionAnadir));
		add(crearBotonImagen(BTN_ELIMINAR_JUGADOR,  btnStartX + BTN_W + BTN_GAP,      BTN_Y, this::accionEliminar));
		add(crearBotonImagen(BTN_MODIFICAR_JUGADOR, btnStartX + 2 * (BTN_W + BTN_GAP), BTN_Y, this::accionActualizar));

		// ── Botón volver ──────────────────────────────────────────────────────
		ImageIcon iconoBack = ImageUtils.cargarImagen(ARROW_BACK, 50, 50);
		ImageIcon iconoBackOsc = ImageUtils.oscurecerImagen(iconoBack);
		JLabel btnVolver = new JLabel(iconoBack);
		btnVolver.setBounds(15, 15, 50, 50);
		btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { btnVolver.setIcon(iconoBackOsc); }
			@Override public void mouseExited(MouseEvent e)  { btnVolver.setIcon(iconoBack); }
			@Override public void mouseClicked(MouseEvent e) { controller.lanzarMenuPrincipal(); }
		});
		add(btnVolver);


		// CAPA QUE OSCURECE EL FONDO
		JPanel oscurece = new JPanel();
		oscurece.setBackground(new Color(0, 0, 0, 90));
		oscurece.setOpaque(true);
		oscurece.setBounds(0, 0, ANCHO, ALTO);
		add(oscurece);

		// ── Fondo ─────────────────────────────────────────────────────────────
		JLabel fondo = new JLabel(ImageUtils.cargarImagen(FONDO_DEFAULT, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		// ── Carga jugadores en background ─────────────────────────────────────
		new SwingWorker<List<Jugador>, Void>() {
			@Override protected List<Jugador> doInBackground() {
				return new JugadorDAO().listar();
			}
			@Override protected void done() {
				try {
					List<Jugador> jugadores = get();
					for (int i = 0; i < jugadores.size(); i++) {
						int col = i % GRID_COLS;
						int row = i / GRID_COLS;
						JLabel fila = crearFilaJugador(jugadores.get(i), iconoCaja, iconoAvatar);
						fila.setBounds(col * (CARD_W + CARD_GAP_H), row * (CARD_H + CARD_GAP_V), CARD_W, CARD_H);
						lista.add(fila);
					}
					int rows = (jugadores.size() + GRID_COLS - 1) / GRID_COLS;
					lista.setPreferredSize(new Dimension(GRID_W, rows * (CARD_H + CARD_GAP_V)));
					lista.revalidate();
					lista.repaint();
				} catch (Exception ex) { ex.printStackTrace(); }
			}
		}.execute();
	}

	/**
	 * Crea un botón de imagen con efecto de oscurecimiento al pasar el ratón.
	 *
	 * @param ruta   Ruta al fichero de imagen del botón.
	 * @param x      Posición horizontal.
	 * @param y      Posición vertical.
	 * @param accion Acción a ejecutar al hacer clic.
	 * @return {@link JLabel} configurado como botón interactivo.
	 */
	private JLabel crearBotonImagen(String ruta, int x, int y, Runnable accion) {
		ImageIcon icono    = ImageUtils.cargarImagen(ruta, BTN_W, BTN_H_IMG);
		ImageIcon iconoOsc = ImageUtils.oscurecerImagen(icono);
		JLabel btn = new JLabel(icono);
		btn.setBounds(x, y, BTN_W, BTN_H_IMG);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { btn.setIcon(iconoOsc); }
			@Override public void mouseExited(MouseEvent e)  { btn.setIcon(icono); }
			@Override public void mouseClicked(MouseEvent e) { accion.run(); }
		});
		return btn;
	}

	/**
	 * Crea el componente visual de una fila de jugador con su avatar y apodo.
	 * Al hacer clic se navega al editor de mazo del jugador.
	 *
	 * @param j          Jugador a representar.
	 * @param iconoCaja  Imagen de fondo de la fila.
	 * @param iconoAvatar Imagen del icono de avatar.
	 * @return {@link JLabel} configurado como fila interactiva.
	 */
	private JLabel crearFilaJugador(Jugador j, ImageIcon iconoCaja, ImageIcon iconoAvatar) {
		ImageIcon iconoCajaOsc = ImageUtils.oscurecerImagen(iconoCaja);

		JLabel caja = new JLabel(iconoCaja);
		caja.setLayout(null);
		caja.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JLabel icono = new JLabel(iconoAvatar);
		icono.setBounds(8, 12, 45, 45);

		JLabel texto = new JLabel(j.getApodo());
		texto.setForeground(Color.WHITE);
		texto.setFont(ImageUtils.cargarFuente(FONT_MEDIEVAL, 19f));
		texto.setBounds(60, 20, CARD_W - 70, 30);

		caja.add(texto);
		caja.add(icono);
		caja.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e)  { caja.setIcon(iconoCajaOsc); }
			@Override public void mouseExited(MouseEvent e)   { caja.setIcon(iconoCaja); }
			@Override public void mouseReleased(MouseEvent e) { controller.lanzarMenuMazo(j); }
		});
		return caja;
	}

	/**
	 * Muestra un formulario para introducir los datos de un nuevo jugador y lo inserta en la base de datos.
	 * Recarga la vista si la inserción es exitosa.
	 */
	private void accionAnadir() {
		JTextField nombre    = new JTextField();
		JTextField apellidos = new JTextField();
		JTextField email     = new JTextField();
		JTextField apodo     = new JTextField();

		JPanel form = new JPanel(new GridLayout(4, 2, 5, 10));
		form.add(new JLabel("Nombre:"));    form.add(nombre);
		form.add(new JLabel("Apellidos:")); form.add(apellidos);
		form.add(new JLabel("Email:"));     form.add(email);
		form.add(new JLabel("Apodo:"));     form.add(apodo);

		int r = JOptionPane.showConfirmDialog(this, form, "Añadir jugador", JOptionPane.OK_CANCEL_OPTION);
		if (r == JOptionPane.OK_OPTION) {
			boolean ok = new JugadorDAO().insertar(
				new Jugador(nombre.getText(), apellidos.getText(), email.getText(), apodo.getText()));
			if (ok) {
				JOptionPane.showMessageDialog(this, "✅ Jugador añadido correctamente");
				controller.lanzarMenuJugador();
			} else {
				JOptionPane.showMessageDialog(this, "Error al añadir el jugador", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Solicita un apodo, busca al jugador y lo elimina junto con todas sus partidas y mazos.
	 * Recarga la vista si la eliminación es exitosa.
	 */
	private void accionEliminar() {
		JTextField apodo = new JTextField();
		JPanel form = new JPanel(new GridLayout(1, 2, 5, 10));
		form.add(new JLabel("Apodo:")); form.add(apodo);

		int r = JOptionPane.showConfirmDialog(this, form, "Eliminar Jugador", JOptionPane.OK_CANCEL_OPTION);
		if (r == JOptionPane.OK_OPTION) {
			JugadorDAO dao = new JugadorDAO();
			Jugador jd = dao.buscarApodo(apodo.getText());
			if (jd == null) {
				JOptionPane.showMessageDialog(this, "No se ha encontrado al jugador", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				dao.eliminar(jd.getId_jugador());
				JOptionPane.showMessageDialog(this, "🗑️ Jugador eliminado correctamente");
				controller.lanzarMenuJugador();
			}
		}
	}

	/**
	 * Muestra un formulario para modificar los datos de un jugador buscado por apodo.
	 * Los campos dejados vacíos no se modifican. Recarga la vista si la actualización es exitosa.
	 */
	private void accionActualizar() {
		JTextField apodoBuscar = new JTextField();
		JTextField nuevoNombre    = new JTextField();
		JTextField nuevosApellidos = new JTextField();
		JTextField nuevoEmail     = new JTextField();
		JTextField nuevoApodo     = new JTextField();

		JPanel form = new JPanel(new GridLayout(5, 2, 5, 10));
		form.add(new JLabel("Apodo (jugador a buscar):")); form.add(apodoBuscar);
		form.add(new JLabel("Nuevo Nombre (vacío = no cambiar):")); form.add(nuevoNombre);
		form.add(new JLabel("Nuevos Apellidos (vacío = no cambiar):")); form.add(nuevosApellidos);
		form.add(new JLabel("Nuevo Email (vacío = no cambiar):")); form.add(nuevoEmail);
		form.add(new JLabel("Nuevo Apodo (vacío = no cambiar):")); form.add(nuevoApodo);

		int r = JOptionPane.showConfirmDialog(this, form, "Actualizar jugador", JOptionPane.OK_CANCEL_OPTION);
		if (r != JOptionPane.OK_OPTION) return;

		JugadorDAO dao = new JugadorDAO();
		Jugador j = dao.buscarApodo(apodoBuscar.getText().trim());
		if (j == null) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado al jugador con ese apodo.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!nuevoNombre.getText().trim().isEmpty())     j.setNombre(nuevoNombre.getText().trim());
		if (!nuevosApellidos.getText().trim().isEmpty()) j.setApellidos(nuevosApellidos.getText().trim());
		if (!nuevoEmail.getText().trim().isEmpty())      j.setEmail(nuevoEmail.getText().trim());
		if (!nuevoApodo.getText().trim().isEmpty())      j.setApodo(nuevoApodo.getText().trim());

		if (dao.actualizar(j)) {
			JOptionPane.showMessageDialog(this, "✏️ Jugador actualizado correctamente");
			controller.lanzarMenuJugador();
		} else {
			JOptionPane.showMessageDialog(this, "Error al actualizar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
