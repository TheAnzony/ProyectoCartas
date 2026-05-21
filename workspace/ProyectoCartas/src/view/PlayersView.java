package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.MainController;
import dao.JugadorDAO;
import modulos.Jugador;
import utils.ImageUtils;
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
		ImageIcon iconoCaja = ImageUtils.cargarImagen(BOX_IMAGE, 300, 70);
		ImageIcon iconoAvatar = ImageUtils.cargarImagen(ICONO_LIST, 50, 50);

		// Panel que contiene la lista de jugadores
		JPanel lista = new JPanel();
		lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
		lista.setOpaque(false);

		JScrollPane scroll = new JScrollPane(lista);
		scroll.setBounds((ANCHO - 600) / 2, 280, 300, 500);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(8);
		add(scroll);

		// Botón volver
		ImageIcon iconoBack = ImageUtils.cargarImagen(ARROW_BACK, 60, 60);
		ImageIcon iconoBackOscuro = ImageUtils.oscurecerImagen(iconoBack);
		JLabel btnVolver = new JLabel(iconoBack);
		btnVolver.setBounds(20, 20, 60, 60);
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
		
		// 1. AÑADIMOS TODOS LOS BOTONES DE ACCIÓN PRINCIPALES
		add(botonAnadirJugador(iconoBackOscuro, ANCHO - 600, (ALTO / 2) - 60, 150, 60));
		add(botonEliminarJugador(iconoBackOscuro, ANCHO - 600, (ALTO / 2) + 20, 150, 60));
		
		// AQUÍ ESTÁ LA SOLUCIÓN: Añadimos el botón de actualizar debajo del de eliminar (+80px en Y)
		add(botonActualizarJugador(iconoBackOscuro, ANCHO - 600, (ALTO / 2) + 100, 150, 60));

		// 2. EL FONDO SE AÑADE SIEMPRE AL FINAL
		// En Absolute Layout (null), lo último que agregas se dibuja al fondo (capa inferior)
		JLabel fondo = new JLabel(ImageUtils.cargarImagen(JUGADORES_IMAGE, ANCHO, ALTO));
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

		caja.add(texto);
		caja.add(icono);

		return caja;
	}

	private JButton botonEliminarJugador(ImageIcon imagen, int x, int y, int ancho, int alto) {

		JButton btnAnadir = new JButton("Eliminar Jugador");
		btnAnadir.setBounds(x, y, ancho, alto);
		btnAnadir.setForeground(Color.white);
		btnAnadir.setBackground(new Color(60, 120, 60));
		btnAnadir.setFocusPainted(false); 
		btnAnadir.setBorderPainted(false); 
		btnAnadir.addActionListener(e -> {

			JTextField apodo = new JTextField();

			JPanel form = new JPanel(new GridLayout(4, 2, 5, 10));
			form.add(new JLabel("Apodo:"));
			form.add(apodo);

			int result = JOptionPane.showConfirmDialog(null, form, "Eliminar Jugador", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				JugadorDAO dao = new JugadorDAO();

				Jugador jdelete = dao.buscarApodo(apodo.getText());
				if (jdelete == null) {
					JOptionPane.showMessageDialog(null, "No se ha encontrado al jugador", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					dao.eliminar(jdelete.getId_jugador());
					JOptionPane.showMessageDialog(null, "Jugador eliminado correctamente");
					controller.lanzarMenuJugador(); 
				}

			}
		});
		return btnAnadir;
	}

	private JButton botonAnadirJugador(ImageIcon imagen, int x, int y, int ancho, int alto) {

		JButton btnAnadir = new JButton("Añadir Jugador");
		btnAnadir.setBounds(x, y, ancho, alto);
		btnAnadir.setForeground(Color.white);
		btnAnadir.setBackground(new Color(60, 120, 60));
		btnAnadir.setFocusPainted(false); 
		btnAnadir.setBorderPainted(false); 
		btnAnadir.addActionListener(e -> {

			JTextField nombre = new JTextField();
			JTextField apellidos = new JTextField();
			JTextField email = new JTextField();
			JTextField apodo = new JTextField();

			JPanel form = new JPanel(new GridLayout(4, 2, 5, 10));
			form.add(new JLabel("Nombre:"));
			form.add(nombre);
			form.add(new JLabel("Apellidos:"));
			form.add(apellidos);
			form.add(new JLabel("Email:"));
			form.add(email);
			form.add(new JLabel("Apodo:"));
			form.add(apodo);

			int result = JOptionPane.showConfirmDialog(null, form, "Añadir jugador", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				Jugador j = new Jugador(nombre.getText(), apellidos.getText(), email.getText(), apodo.getText());
				boolean ok = new JugadorDAO().insertar(j);
				if (ok) {
					JOptionPane.showMessageDialog(null, "Jugador añadido correctamente");
					controller.lanzarMenuJugador(); 
				} else {
					JOptionPane.showMessageDialog(null, "Error al añadir el jugador", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return btnAnadir;
	}
	
	private JButton botonActualizarJugador(ImageIcon imagen, int x, int y, int ancho, int alto) {

		JButton btnActualizar = new JButton("Actualizar Jugador");
		btnActualizar.setBounds(x, y, ancho, alto);
		btnActualizar.setForeground(Color.white);
		btnActualizar.setBackground(new Color(60, 120, 60)); 
		btnActualizar.setFocusPainted(false); 
		btnActualizar.setBorderPainted(false); 
		btnActualizar.addActionListener(e -> {

			JTextField apodo = new JTextField();
			JTextField nombre = new JTextField();
			JTextField apellidos = new JTextField();
			JTextField email = new JTextField();

			JPanel form = new JPanel(new GridLayout(4, 2, 5, 10));
			form.add(new JLabel("Apodo (Jugador a buscar):"));
			form.add(apodo);
			form.add(new JLabel("Nuevo Nombre:"));
			form.add(nombre);
			form.add(new JLabel("Nuevos Apellidos:"));
			form.add(apellidos);
			form.add(new JLabel("Nuevo Email:"));
			form.add(email);

			int result = JOptionPane.showConfirmDialog(null, form, "Actualizar jugador", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				Jugador j = new Jugador(nombre.getText(), apellidos.getText(), email.getText(), apodo.getText());
				
				boolean ok = new JugadorDAO().actualizar(j); 
				
				if (ok) {
					JOptionPane.showMessageDialog(null, "Jugador actualizado correctamente");
					controller.lanzarMenuJugador(); 
				} else {
					JOptionPane.showMessageDialog(null, "Error al actualizar el jugador. Verifica que el apodo exista.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return btnActualizar;
	}
}
