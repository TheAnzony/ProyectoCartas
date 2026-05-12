package controller;

import java.util.List;

import dao.CartaDAO;
import modulos.Carta;
import utils.ImageUtils;
import utils.config;

public class Main implements config {

	public static void main(String[] args) {

		CartaDAO c = new CartaDAO();

		List<Carta> lista = c.listar();

		for (Carta carta : lista) {
			ImageUtils.cargarImagen(CARTAS_DIR + carta.getImagen(), 200, 280);
		}

		new MainController();
	}

}
