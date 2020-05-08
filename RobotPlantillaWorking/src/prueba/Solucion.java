package prueba;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class Solucion {
	private Problema pr; // Problema del que se desea encontrar solucion
	private List<Nodo> sol; // Lista donde se almacenan los Nodos solución en orden de ejecución
	private Nodo meta; // Nodo que almacena información de la meta
	private List<Nodo> cerrados; // Lista de nodos ya cerrados
	private Queue<Nodo> abiertos; // Cola de nodos abiertos
	private int nodos_expandidos; // total de nodos que hemos necesitado expandir
	private int nodos_abiertos; // total de nodos que hemos dejado abiertos
	public static final int COSTE_H = 100; // Variable fija que marca el valor de un movimiento horizontal
	public static final int COSTE_D = 142; // Variable fija que marca el valor de un movimiento diagonal

	/**
	 * Constructor de la Solución
	 * 
	 * @param pr     : Problema del que se desea encontrar solución
	 * @param opcion : método que se debe usar : 1->AMPLITUD, 2->VORAZ, 3->A*
	 */
	public Solucion(Problema pr, int opcion) {
		// inicialización de las variables
		this.pr = pr;
		this.nodos_abiertos = 0;
		this.nodos_expandidos = 0;
		meta = new Nodo(pr.getPosFinal().getFila(), pr.getPosFinal().getColumna(), pr);

		if (opcion < 1 || opcion > 3) {
			throw new RobocodeException("valor opcion problema no válido");
		}
		switch (opcion) {
		case 1:
			sol = this.getSolucion(1);
			break;
		case 2:
			sol = this.getSolucion(2); // f = h; Voraz
			break;
		case 3:
			sol = this.getSolucion(3); // f = h+g; A*
			break;
		}
		if (this.getAbiertos() != null)
			this.nodos_abiertos = this.getAbiertos().size();
	}

	/**
	 * Método principal para calcular la solución
	 * 
	 * @param i : identifica el método que debemos utilizar
	 * @return List<Nodo> con la solución por pasos del problema
	 */
	public List<Nodo> getSolucion(int i) {

		PriorityQueue<Nodo> abiertos = new PriorityQueue<Nodo>(new TreeSet<Nodo>());
		abiertos.add(new Nodo(pr.getPosInicial().getFila(), pr.getPosInicial().getColumna(), pr)); // nodo inicial
		List<Nodo> cerrados = new LinkedList<>();

		while (!abiertos.isEmpty()) { // MIENTRAS QUE HAYA NODOS ABIERTOS...
			Nodo actual = abiertos.poll(); // SACAMOS EL PRIMERO DE LA COLA
			this.nodos_expandidos++;
			if (actual.equals(meta)) { // SI ES LA META...
				this.cerrados = cerrados;
				this.abiertos = abiertos;
				return muestraCamino(actual); // DEVOLVEMOS CAMINO SOLUCION
			} else { // SI NO ES LA META...
				cerrados.add(actual); // AÑADIMOS EL NODO A CERRADOS
				Queue<Nodo> succesors = getSucesores(actual, pr, i); // CALCULAMOS SUCESORES
				for (Nodo n : succesors) {
					// AÑADIMOS DE ENTRE LOS SUCESORES, AQUELLOS QUE NO HAN SIDO CALCULADOS
					// (ABIERTOS O CERRADOS)
					if (!abiertos.contains(n) && !cerrados.contains(n)) {
						abiertos.add(n);
					}
					if(abiertos.size() > this.nodos_abiertos) {
						nodos_abiertos = abiertos.size();
					}
				}
			}
		}
		return null; // SI NO HA ENCONTRADO SOLUCIÓN, DEVUELVE NULL
	}

	/**
	 * Método para calcular posibles sucesores de un nodo
	 * 
	 * @param node : Nodo del cual se quiere obtener sucesores
	 * @param p    : Problema para comprobar matriz de obstáculos
	 * @param i    : opcion de solucion del problema
	 * @return
	 */
	private Queue<Nodo> getSucesores(Nodo node, Problema p, int i) {
		// inicializamos variable resultado y casilla actual
		Queue<Nodo> res = new LinkedList<>();
		Casilla actual = node.getActual();

		/**
		 * Para cada nodo posible (ARRIBA, ABAJO, DERECHA, IZQUIERDA, ARRIBADERECHA, ARRIBAIZQUIERDA, ABAJODERECHA, ABAJOIZQUIERDA)...
		 *  1. Verifica que que el movimiento sea a una casilla posible
		 * 		2.1. Si estamos en Amplitud, añadimos el nodo sin heurística
		 * 		2.2. Si estamos en Voraz, añadimos el nodo con heuristico voraz 
		 * 		2.3. Si estamos en A*, añadimos el nodo con heuristico A*
		 */

		// arriba
		if (actual.getFila() - 1 >= 0 && actual.getColumna() >= 0
				&& p.posibleMov(new Casilla(actual.getFila() - 1, actual.getColumna()))) {
			if (i == 1)
				res.add(new Nodo(actual.getFila() - 1, actual.getColumna(), node, pr));
			else {
				Nodo arriba = new Nodo(actual.getFila() - 1, actual.getColumna(), node, pr);
				if (i == 2)
					arriba.setHeuristico(heuristicoVoraz(meta.getActual(), arriba.getActual()));
				else
					arriba.setHeuristico(heuristicoA(meta.getActual(), arriba.getActual(), node));
				res.add(arriba);
			}

		}
		// abajo
		if (actual.getFila() + 1 >= 0 && actual.getColumna() >= 0
				&& p.posibleMov(new Casilla(actual.getFila() + 1, actual.getColumna()))) {
			if (i == 1)
				res.add(new Nodo(actual.getFila() + 1, actual.getColumna(), node, pr));
			else {
				Nodo abajo = new Nodo(actual.getFila() + 1, actual.getColumna(), node, pr);
				if (i == 2)
					abajo.setHeuristico(heuristicoVoraz(meta.getActual(), abajo.getActual()));
				else
					abajo.setHeuristico(heuristicoA(meta.getActual(), abajo.getActual(), node));
				res.add(abajo);
			}
		}
		// derecha
		if (actual.getFila() >= 0 && actual.getColumna() + 1 >= 0
				&& p.posibleMov(new Casilla(actual.getFila(), actual.getColumna() + 1))) {
			if (i == 1)
				res.add(new Nodo(actual.getFila(), actual.getColumna() + 1, node, pr));
			else {
				Nodo derecha = new Nodo(actual.getFila(), actual.getColumna() + 1, node, pr);
				if (i == 2)
					derecha.setHeuristico(heuristicoVoraz(meta.getActual(), derecha.getActual()));
				else
					derecha.setHeuristico(heuristicoA(meta.getActual(), derecha.getActual(), node));
				res.add(derecha);
			}
		}
		// izquierda
		if (actual.getFila() >= 0 && actual.getColumna() - 1 >= 0
				&& p.posibleMov(new Casilla(actual.getFila(), actual.getColumna() - 1))) {
			if (i == 1)
				res.add(new Nodo(actual.getFila(), actual.getColumna() - 1, node, pr));
			else {
				Nodo izquierda = new Nodo(actual.getFila(), actual.getColumna() - 1, node, pr);
				if (i == 2)
					izquierda.setHeuristico(heuristicoVoraz(meta.getActual(), izquierda.getActual()));
				else
					izquierda.setHeuristico(heuristicoA(meta.getActual(), izquierda.getActual(), node));
				res.add(izquierda);
			}
		}
		// arribaderecha
		if (actual.getFila() + 1 >= 0 && actual.getColumna() + 1 >= 0
				&& p.posibleMov(new Casilla(actual.getFila() + 1, actual.getColumna() + 1))
				&& p.obtenerValor(new Casilla(actual.getFila() + 1, actual.getColumna())) == 0
				&& p.obtenerValor(new Casilla(actual.getFila(), actual.getColumna() + 1)) == 0) {
			if (i == 1)
				res.add(new Nodo(actual.getFila() + 1, actual.getColumna() + 1, node, pr));
			else {
				Nodo arribaderecha = new Nodo(actual.getFila() + 1, actual.getColumna() + 1, node, pr);
				if (i == 2)
					arribaderecha.setHeuristico(heuristicoVoraz(meta.getActual(), arribaderecha.getActual()));
				else
					arribaderecha.setHeuristico(heuristicoA(meta.getActual(), arribaderecha.getActual(), node));
				res.add(arribaderecha);
			}
		}
		// abajoderecha
		if (actual.getFila() - 1 >= 0 && actual.getColumna() + 1 >= 0
				&& p.posibleMov(new Casilla(actual.getFila() - 1, actual.getColumna() + 1))
				&& p.obtenerValor(new Casilla(actual.getFila() - 1, actual.getColumna())) == 0
				&& p.obtenerValor(new Casilla(actual.getFila(), actual.getColumna() + 1)) == 0) {
			if (i == 1)
				res.add(new Nodo(actual.getFila() - 1, actual.getColumna() + 1, node, pr));
			else {
				Nodo abajoderecha = new Nodo(actual.getFila() - 1, actual.getColumna() + 1, node, pr);
				if (i == 2)
					abajoderecha.setHeuristico(heuristicoVoraz(meta.getActual(), abajoderecha.getActual()));
				else
					abajoderecha.setHeuristico(heuristicoA(meta.getActual(), abajoderecha.getActual(), node));
				res.add(abajoderecha);
			}
		}
		// abajoizquierda
		if (actual.getFila() - 1 >= 0 && actual.getColumna() - 1 >= 0
				&& p.posibleMov(new Casilla(actual.getFila() - 1, actual.getColumna() - 1))
				&& p.obtenerValor(new Casilla(actual.getFila() - 1, actual.getColumna())) == 0
				&& p.obtenerValor(new Casilla(actual.getFila(), actual.getColumna() - 1)) == 0) {
			if (i == 1)
				res.add(new Nodo(actual.getFila() - 1, actual.getColumna() - 1, node, pr));
			else {
				Nodo abajoizquierda = new Nodo(actual.getFila() - 1, actual.getColumna() - 1, node, pr);
				if (i == 2)
					abajoizquierda.setHeuristico(heuristicoVoraz(meta.getActual(), abajoizquierda.getActual()));
				else
					abajoizquierda.setHeuristico(heuristicoA(meta.getActual(), abajoizquierda.getActual(), node));
				res.add(abajoizquierda);
			}
		}
		// arribaizquierda
		if (actual.getFila() + 1 >= 0 && actual.getColumna() - 1 >= 0
				&& p.posibleMov(new Casilla(actual.getFila() + 1, actual.getColumna() - 1))
				&& p.obtenerValor(new Casilla(actual.getFila() + 1, actual.getColumna())) == 0
				&& p.obtenerValor(new Casilla(actual.getFila(), actual.getColumna() - 1)) == 0) {
			if (i == 1)
				res.add(new Nodo(actual.getFila() + 1, actual.getColumna() - 1, node, pr));
			else {
				Nodo arribaizquierda = new Nodo(actual.getFila() + 1, actual.getColumna() - 1, node, pr);
				if (i == 2)
					arribaizquierda.setHeuristico(heuristicoVoraz(meta.getActual(), arribaizquierda.getActual()));
				else
					arribaizquierda.setHeuristico(heuristicoA(meta.getActual(), arribaizquierda.getActual(), node));
				res.add(arribaizquierda);
			}
		}
		return res;
	}

	/**
	 * @param meta:   Casilla meta
	 * @param actual: Casilla actual
	 * @return valor del heurístico Voraz, esto es h = distancia octil
	 */
	private int heuristicoVoraz(Casilla meta, Casilla actual) {
		int af = Math.abs(meta.getFila() - actual.getFila());
		int ac = Math.abs(meta.getColumna() - actual.getColumna());
		return COSTE_D * Math.min(ac, af) + COSTE_H * (Math.max(ac, af) - Math.min(ac, af));
	}

	/**
	 * @param meta:   Casilla meta
	 * @param actual: Casilla actual
	 * @return valor del heurístico A*, esto es h = distancia octil + coste
	 *         acumulado
	 */
	private int heuristicoA(Casilla meta, Casilla actual, Nodo n) {
		return heuristicoVoraz(meta, actual) + n.getCoste();
	}

	// muestra el camino solución
	private List<Nodo> muestraCamino(Nodo meta) {
		LinkedList<Nodo> path = new LinkedList<>();
		while (meta.getPadre() != null) {
			path.addFirst(meta); // RECORREMOS DESDE META HASTA INICIO (PADRE == NULL)
			meta = meta.getPadre();
		}
		return path;
	}

	public List<Nodo> getSol() {
		return sol;
	}

	public List<Nodo> getCerrados() {
		return cerrados;
	}

	public Queue<Nodo> getAbiertos() {
		return abiertos;
	}

	public int getNodos_expandidos() {
		return nodos_expandidos;
	}

	public int getNodos_abiertos() {
		return nodos_abiertos;
	}

	//Devuelve el coste total, esto es, el coste acumulado del ultimo nodo de la solucion
	public int getCoste_total() {
		return this.getSol().get(this.getSol().size() - 1).getCoste();
	}

}
