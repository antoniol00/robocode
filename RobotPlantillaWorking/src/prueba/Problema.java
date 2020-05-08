package prueba;

import java.util.Random;

public class Problema {

	private int[][] mapa; // matriz que representa un mapa: posInicial,posFinal y obstáculos
	private int obstaculos; // numero de obstáculos
	private Random rand; // variable Random para generación
	private int NFILAS; // numero total de filas
	private int NCOLUMNAS; // numero total de columnas
	private Casilla posInicial; // casilla donde se guardará la posicion inicial
	private Casilla posFinal; // casilla donde se guardará la posicion final
	private long semilla; // semilla para la generación de random

	public Problema(long semilla, int NFILAS, int NCOLUMNAS, int obstaculos) {
		if (obstaculos > NFILAS * NCOLUMNAS - 2) { // comprueba que el numero de obstaculos sea correcto
			throw new RobocodeException("Not a valid number of obstacles (" + obstaculos + ") for a maximum of: "
					+ (NFILAS * NCOLUMNAS - 2));
		}
		// inicializacion de todas las variables
		posInicial = new Casilla();
		posFinal = new Casilla();
		rand = new Random(semilla);
		this.semilla = semilla;
		this.NFILAS = NFILAS;
		this.NCOLUMNAS = NCOLUMNAS;
		this.obstaculos = obstaculos;
		mapa = new int[NFILAS][NCOLUMNAS];

		// inicializar mapa con 0
		for (int x = 0; x < NFILAS; x++) {
			for (int y = 0; y < NCOLUMNAS; y++) {
				mapa[x][y] = 0;
			}
		}

		// colocar obstaculos aleatoriamente
		int cont = 0;
		while (cont < obstaculos) {
			int fil = rand.nextInt(NFILAS);
			int col = rand.nextInt(NCOLUMNAS);
			if (mapa[fil][col] == 0) {
				mapa[fil][col] = 1;
				cont++;
			}
		}

		// decidir posición de las posiciones inicial y final
		do {
			posInicial.setFila(rand.nextInt(NFILAS));
			posInicial.setColumna(rand.nextInt(NCOLUMNAS));
			posFinal.setFila(rand.nextInt(NFILAS));
			posFinal.setColumna(rand.nextInt(NCOLUMNAS));
		} while (obtenerValor(posInicial) == 1 || obtenerValor(posFinal) == 1 || posInicial.equals(posFinal));
	}

	@Override
	public String toString() {
		String str = "";
		boolean first = true;
		for (int y = NCOLUMNAS - 1; y >= 0; y--) {
			str += "\t";
			for (int x = 0; x < NFILAS; x++) {
				if (mapa[x][y] == 1) {
					str += " X ";
				} else if (posInicial.equals(new Casilla(x, y))) {
					str += " I ";
				} else if (posFinal.equals(new Casilla(x, y))) {
					str += " F ";
				} else {
					str += " - ";
				}
			}
			if (first) {
				str += "(" + NFILAS + "," + NCOLUMNAS + ")";
				first = false;
			}
			str += "\n";
		}
		str += "(0,0)\n";
		str += "\nLocalización posición inicial: " + posInicial.toString() + "\n";
		str += "Localización posición final: " + posFinal.toString() + "\n";
		return str;
	}

	/**
	 * @param c : Casilla donde se desea buscar
	 * @return Boolean con true si la casilla está dentro del mapa y no representa
	 *         una casilla obstáculo. Devuelve falso en cualquier otro caso
	 */
	public boolean posibleMov(Casilla c) {
		return c.getFila() < NFILAS && c.getColumna() < NCOLUMNAS && obtenerValor(c) == 0;
	}

	/**
	 * @param c : Casilla donde se desea buscar
	 * @return Integer que puede ser 0 (casilla libre) o 1 (casilla con obstáculo)
	 */
	public int obtenerValor(Casilla c) {
		return mapa[c.getFila()][c.getColumna()];
	}

	public int getObstaculos() {
		return obstaculos;
	}

	public Casilla getPosInicial() {
		return posInicial;
	}

	public Casilla getPosFinal() {
		return posFinal;
	}

	public long getSemilla() {
		return semilla;
	}
}
