package prueba;

public class Casilla {

	private int fila;
	private int columna;

	/**
	 * Crea una casilla vacía
	 */
	public Casilla() {
		fila = 0;
		columna = 0;
	}

	/**
	 * Crea una casilla dados fila y columna
	 * 
	 * @param f = entero positivo con la posición de la fila
	 * @param c = entero positivo con la posición de la columna
	 */
	public Casilla(int f, int c) {
		if (f < 0 || c < 0) {
			throw new RobocodeException("No se puede crear una CASILLA con coordenadas negativas");
		}
		fila = f;
		columna = c;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	@Override
	public String toString() {
		return "[" + this.getFila() + "," + this.getColumna() + "]"; // Ejemplo : [4,5]
	}

	@Override
	public boolean equals(Object obj) {
		boolean res = obj instanceof Casilla;
		Casilla c = res ? (Casilla) obj : null;
		return res && this.getColumna() == c.getColumna() && this.getFila() == c.getFila();
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(this.getFila()) + Integer.hashCode(this.getColumna());
	}
}
