package test;


import java.util.Iterator;

import dao.CartaDAO;
import dao.JugadorDAO;
import modulos.Carta;
import modulos.Jugador;

public class PruebasDAO {

	
	public static void main(String[] args) {
		
		
		
		
		JugadorDAO jd = new JugadorDAO();
		
		CartaDAO cd = new CartaDAO();
		
		
		jd.mostrarLista(jd.listar());
	
		
		
		
	}
}
