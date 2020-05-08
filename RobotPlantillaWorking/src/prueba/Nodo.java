package prueba;

public class Nodo implements Comparable<Nodo> {

	private Casilla actual; // Casilla actual del nodo
	@SuppressWarnings("unused")
	private Casilla meta; // Casilla final del problema
	private Nodo padre; // Nodo padre, usado para calcular coste y movimiento
	private int movimiento; // Movimiento que ha realizado en el problema con respecto al padre
	private int heuristico; // Valor de la función heurística
	private int coste; // Coste acumulado desde el origen del problema hasta la casilla actual

	/**
	 * Constructor de un Nodo que no es raíz
	 * 
	 * @param fila    : entero positivo con el valor de la fila actual
	 * @param columna : entero positivo con el valor de la columna actual
	 * @param padre   : Nodo padre
	 * @param p       : Problema que busca solución
	 */
	public Nodo(int fila, int columna, Nodo padre, Problema p) {
		actual = new Casilla(fila, columna);
		this.padre = padre;
		meta = new Casilla(p.getPosFinal().getFila(), p.getPosFinal().getColumna());
		calcularGiro();
		heuristico = 0;
		coste = (this.movimiento > 3 ? Solucion.COSTE_D : Solucion.COSTE_H) + padre.coste;
	}

	// Constructor de un nodo sin padre -> raiz. Su movimiento es -1
	public Nodo(int fila, int columna, Problema p) {
		meta = new Casilla(p.getPosFinal().getFila(), p.getPosFinal().getColumna());
		actual = new Casilla(fila, columna);
		movimiento = -1;
		heuristico = 0;
		this.padre = null;
		coste = 0;
	}

	/**
	 * Método que calcula el movimiento que ha realizado el robot con respecto a su
	 * padre
	 */
	private void calcularGiro() {
		if (padre.getActual().getFila() - actual.getFila() == 0) { // misma fila que el padre
			if (padre.getActual().getColumna() - actual.getColumna() > 0) {
				movimiento = 3;// abajo EN EL MAPA
			} else
				movimiento = 2; // arriba EN EL MAPA
		} else if (padre.getActual().getColumna() - actual.getColumna() == 0) { // misma columna que el padre
			if (padre.getActual().getFila() - actual.getFila() < 0) {
				movimiento = 1;// derecha EN EL MAPA
			} else
				movimiento = 0;// izquierda EN EL MAPA
		} else if (padre.getActual().getColumna() - actual.getColumna() < 0) { // columa derecha del padre
			if (padre.getActual().getFila() - actual.getFila() > 0) {
				movimiento = 4; // arribaizquierda EN EL MAPA
			} else {
				movimiento = 5; // arribaderecha EN EL MAPA
			}
		} else { // columna izquierda del padre
			if (padre.getActual().getFila() - actual.getFila() > 0) {
				movimiento = 6; // abajoizquierda EN EL MAPA
			} else {
				movimiento = 7; // abajoderecha EN EL MAPA
			}
		}
	}

	public Casilla getActual() {
		return actual;
	}

	public int getMovimiento() {
		return movimiento;
	}

	public int getHeuristico() {
		return heuristico;
	}

	public void setHeuristico(int heuristico) {
		this.heuristico = heuristico;
	}

	public Nodo getPadre() {
		return padre;
	}

	public int getCoste() {
		return coste;
	}

	@Override
	public int hashCode() {
		return actual.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean res = obj instanceof Nodo;
		Nodo c = res ? (Nodo) obj : null;
		return res && this.actual.equals(c.actual);
	}

	@Override
	public String toString() {
		return "Nodo [actual=" + actual + ", movimiento=" + movimiento + "]"; // Ejemplo : Nodo [actual=
																				// [5,6],movimiento=5];
	}

	/**
	 * Método usado para la inserción en una cola de prioridad
	 */
	@Override
	public int compareTo(Nodo o) {
		return Integer.compare(this.heuristico, o.getHeuristico());
	}
}
