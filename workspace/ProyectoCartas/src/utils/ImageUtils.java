package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils implements config {

	private static final Map<String, ImageIcon> cache = new HashMap<>();

	public static ImageIcon cargarImagen(String ruta, int ancho, int alto) {
		String key = ruta + "_" + ancho + "x" + alto;

		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		try {
			File f = new File(ruta);
			if (!f.exists()) {
				System.out.println("Imagen no existe " + ruta);
				return null;
			}
			BufferedImage original = ImageIO.read(f);
			BufferedImage escalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = escalada.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(original, 0, 0, ancho, alto, null);
			g2d.dispose();
			ImageIcon icono = new ImageIcon(escalada);
			cache.put(key, icono);
			return icono;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ImageIcon oscurecerImagen(ImageIcon icono) {
		BufferedImage original = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = original.createGraphics();
		g2d.drawImage(icono.getImage(), 0, 0, null);
		g2d.setColor(new Color(0, 0, 0, 50));
		g2d.fillRect(0, 0, icono.getIconWidth(), icono.getIconHeight());
		g2d.dispose();
		return new ImageIcon(original);
	}

}
