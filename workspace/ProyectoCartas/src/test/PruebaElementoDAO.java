package test;

import java.util.List;
import dao.ElementoDAO;
import modulos.Elemento;

public class PruebaElementoDAO {

	public static void main(String[] args) {
		
		// Instanciamos el DAO que vamos a probar
		ElementoDAO dao = new ElementoDAO();
		
		System.out.println("=== INICIANDO PRUEBAS DE ELEMENTO DAO ===\n");


		System.out.println("--- PRUEBA 1: INSERTAR ---");
		// Usamos el constructor sin ID porque la base de datos lo genera (AUTO_INCREMENT)
		Elemento nuevoElemento = new Elemento(1, "Fuego", "Elemento muy caliente");
		dao.insertar(nuevoElemento);
		
		

		System.out.println("\n--- PRUEBA 2: LISTAR TODOS ---");
		List<Elemento> listaElementos = dao.listar();
		
		for (Elemento e : listaElementos) {
			System.out.println(e.toString());
		}

		
		// Para las siguientes pruebas, vamos a coger el ID del último elemento
		// que acabamos de insertar, así nos aseguramos de que no de error
		if (listaElementos.size() > 0) {
			
			// Cogemos el último elemento de la lista
			int idPrueba = listaElementos.get(listaElementos.size() - 1).getId_elemento();
			

			System.out.println("\n--- PRUEBA 3: BUSCAR POR ID (" + idPrueba + ") ---");
			Elemento elementoEncontrado = dao.buscar(idPrueba);
			
			if (elementoEncontrado != null) {
				System.out.println("Lo he encontrado: " + elementoEncontrado.toString());
			} else {
				System.out.println("No se ha encontrado.");
			}
			

		
			System.out.println("\n--- PRUEBA 4: ACTUALIZAR ---");
			if (elementoEncontrado != null) {
				// Le cambiamos algunos datos
				elementoEncontrado.setNombre("Fuego Mágico");
				elementoEncontrado.setDescripcion("Ahora quema el doble");
				
				// Llamamos al DAO para actualizar
				dao.actualizar(elementoEncontrado);
				
				// Lo volvemos a buscar para ver si de verdad cambió en la BD
				Elemento elementoActualizado = dao.buscar(idPrueba);
				System.out.println("Comprobación tras actualizar: " + elementoActualizado.toString());
			}


		
			System.out.println("\n--- PRUEBA 5: ELIMINAR ---");
			dao.eliminar(idPrueba);
			
			// Comprobamos si de verdad se ha borrado intentando buscarlo de nuevo
			Elemento elementoBorrado = dao.buscar(idPrueba);
			if (elementoBorrado == null) {
				System.out.println("Comprobación final: El elemento ya no existe en la base de datos.");
			}
			
		} else {
			System.out.println("\nNo hay elementos en la base de datos para probar Buscar, Actualizar y Eliminar.");
		}
		
		
	}
}