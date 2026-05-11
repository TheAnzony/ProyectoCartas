package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import controller.MainController;
import dao.CartaDAO;
import modulos.Carta;
import utils.config;

/*
 * GridLayout(0, 3, ...) → GridLayout(0, 4, ...)
Ancho del scroll: 4×200 + 3×10 = 830px
X centrado: (1440 - 830) / 2 = 305

JPanel grid = new JPanel(new GridLayout(0, 4, 10, 10));

JScrollPane scroll = new JScrollPane(grid);
scroll.setBounds((ANCHO - 830) / 2, 180, 830, 570);
 */
public class CartaView extends JPanel implements config {

	MainController controller;

	public CartaView(MainController c) {
		// TODO Auto-generated constructor stub

		this.controller = c;
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		ImageIcon cartaFuego = cargarImagen(BOX_IMAGE_FUEGO, 200, 280);
		ImageIcon cartaAire = cargarImagen(BOX_IMAGE_AIRE, 200, 280);
		ImageIcon cartaTierra = cargarImagen(BOX_IMAGE_TIERRA, 200, 280);
		ImageIcon cartaAgua = cargarImagen(BOX_IMAGE_AGUA, 200, 280);
		
		ArrayList<ImageIcon> cartasElementos = new ArrayList<ImageIcon>();
		cartasElementos.add(cartaFuego);
		cartasElementos.add(cartaAgua);
		cartasElementos.add(cartaTierra);
		cartasElementos.add(cartaAire);

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
		ImageIcon iconoBack = cargarImagen(ARROW_BACK, 60, 60);
		ImageIcon iconoBackOscuro = oscurecerImagen(iconoBack, 60, 60);
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

		// Fondo en EDT — rápido, aparece inmediato
		JLabel fondo = new JLabel(cargarImagen(CARTAS_IMAGE, ANCHO, ALTO));
		fondo.setBounds(0, 0, ANCHO, ALTO);
		add(fondo);

		new SwingWorker<List<Carta>, Void>() {
			@Override
			protected List<Carta> doInBackground() throws Exception {

				return new CartaDAO().listar();
			}

			@Override
			protected void done() {
				try {
					List<Carta> cartas = get();

					for (Carta carta : cartas) {
						grid.add(insertarCarta(carta, cartasElementos));
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

	public JLabel insertarCarta(Carta c, ArrayList<ImageIcon> elementos) {

		int elemento = c.getId_elemento() - 1;
		JLabel caja = new JLabel(elementos.get(elemento));
		caja.setLayout(null);
		caja.setMaximumSize(new Dimension(200, 280));

		// Título centrado — ocupa todo el ancho y alinea el texto al centro
		JLabel titulo = new JLabel(c.getNombre(), JLabel.CENTER);
		titulo.setForeground(Color.white);
		titulo.setFont(new Font("Arial", Font.BOLD, 15));
		titulo.setBounds(0, 50, 200, 25);
		caja.add(titulo);
		
		JTextArea descripcion = new JTextArea(c.getDescripcion());
		descripcion.setLineWrap(true);
		descripcion.setWrapStyleWord(true);
		descripcion.setOpaque(false);
		descripcion.setEditable(false);
		descripcion.setForeground(Color.white);
		descripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		descripcion.setBounds(20, 120, 160, 130);
		caja.add(descripcion);
		
		String danoAux = c.getDano() + "";
		JLabel dano = new JLabel(danoAux);
		dano.setForeground(Color.white);
		dano.setFont(new Font("Arial", Font.BOLD, 14));
		dano.setBounds(23, 240, 20, 25);
		caja.add(dano);
		
		String veloAux = c.getVelocidad() + "";
		JLabel velocidad = new JLabel(veloAux);
		velocidad.setForeground(Color.white);
		velocidad.setFont(new Font("Arial", Font.BOLD, 16));
		velocidad.setBounds(200 - 31, 14, 20, 25);
		caja.add(velocidad);
		

		// Número de maná encima de la gota (esquina inferior izquierda)
		JLabel mana = new JLabel(String.valueOf(c.getCoste_mana()), JLabel.CENTER);
		mana.setForeground(Color.white);
		mana.setFont(new Font("Arial", Font.BOLD, 15));
		mana.setBounds(200 - 39, 240, 30, 25);
		caja.add(mana);

		return caja;

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

	private ImageIcon oscurecerImagen(ImageIcon icono, int ancho, int alto) {
		BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(icono.getImage(), 0, 0, null);
		g2d.setColor(new Color(0, 0, 0, 80));
		g2d.fillRect(0, 0, ancho, alto);
		g2d.dispose();
		return new ImageIcon(img);
	}

}
