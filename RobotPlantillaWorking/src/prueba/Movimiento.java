package prueba;

public class Movimiento {
	private enum Move {
		IZQUIERDA, ARRIBA, ABAJO, DERECHA, ARRIBADERECHA, ARRIBAIZQUIERDA, ABAJODERECHA, ABAJOIZQUIERDA
	}

	private Move selected;

	/**
	 * Asigna a la variable de tipo Move el movimiento que debe realizar el robot
	 * @param i = entero positivo menor de 7 que identifica un movimimiento
	 */
	public Movimiento(int i) {
		if (i < 0 || i > 7)
			throw new RobocodeException("movimiento no permitido");
		if (i == 0) {
			selected = Move.IZQUIERDA;
		} else if (i == 1) {
			selected = Move.DERECHA;
		} else if (i == 2) {
			selected = Move.ARRIBA;
		} else if (i == 3) {
			selected = Move.ABAJO;
		} else if (i == 4) {
			selected = Move.ARRIBAIZQUIERDA;
		} else if (i == 5) {
			selected = Move.ARRIBADERECHA;
		} else if (i == 6) {
			selected = Move.ABAJOIZQUIERDA;
		} else if (i == 7) {
			selected = Move.ABAJODERECHA;
		}
	}

	public Move getSelected() {
		return selected;
	}
}
