package test;


import dao.JugadorDAO;
import modulos.Jugador;

public class PruebasDAO {

	
	public static void main(String[] args) {
		
		
		
		
		JugadorDAO jd = new JugadorDAO();
		
		// INSERTAR JUGADOR NUEVO (NO PUEDE TENER MISMO EMAIL)
		// jd.insertar(j);
		
		// BUSCAR JUGADOR POR ID
		
		/*Jugador jbuscado = jd.buscar(1);
		System.out.println(jbuscado);*/
		
		// MOSTRAR TODOS LOS JUGADORES DE LA BASE DE DATOS
		
		/*for (Jugador jl : jd.listar()) {
			System.out.println(jl);
		}*/
		
		// ACTUALIZAR JUGADOR
		
		// Busco el jugador teoricamente antonio en esta prueba
		
		/*Jugador buscado = jd.buscar(3);
		
		System.out.println(buscado);*/
		
		// Le cambio los valores
		/*buscado.setNombre("Paco");
		buscado.setPuntuacion_total(4);
		buscado.setEmail("paco@email.com");*/
		
		// Actualizo los datos, Ahora ya no es antonio es paco
		/*jd.actualizar(buscado);
		
		buscado = jd.buscar(3);*/
		
		// Vuelvo a mostrar el mismo id_jugador deberia llamarse paco
		//System.out.println(buscado);
		
		jd.eliminar(3);
		
		// MOSTRAR TODOS LOS JUGADORES DE LA BASE DE DATOS
		
		System.out.println();
		System.out.println();
		for (Jugador jl : jd.listar()) {
			System.out.println(jl);
		}
		
		
		
	}
}
