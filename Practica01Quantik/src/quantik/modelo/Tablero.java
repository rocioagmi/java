package quantik.modelo;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Rocio Agueda Miguel
 * @version 1.0
 * @see quantik.modelo.pieza
 * @see quantik.modelo.celda
 * 
 *
 */
public class Tablero {

	/**
	 * Array privado de 2 dimensiones.
	 */
	private Celda[][] matriz ;
	
	
	/**
	 * Atributo privado de tipo entero fila
	 */
	private int fila;
	
	
	/**
	 * Atributo privado de tipo entero columna
	 */
	private int columna;
	
	
	/**
	 * Atributo privado pieza 
	 */
	private Pieza pieza;
	
	
	/**
	 * Constructor de la clase, crea un array de dos dimensiones que contiene las coordenadas de todas las celdas.
	 * 
	 */
	public Tablero() {
		for (int i = 0; i < fila; i++) {
			for (int j = 0; j < columna; j++) {
				matriz[i][j] = new Celda(i, j);
			}
		}
	}
	
	
	/**
	 * Metodo que devuelve el estado del tablero actual.
	 * 
	 * @return String s		estado actual del tablero.
	 */
	public String aTexto() { 
		String s = "";
		for (int i = 0; i < consultarNumeroFilas(); i++) {
			for (int j = 0; j < consultarNumeroColumnas(); j++) {
				if(matriz[i][j].estaVacia()) {
					s = s + "-";
				} else {
					s = s + matriz[i][j].consultarPieza().aTexto();
				}
				s = s + "\t";
			}
			s = s + "\n";
		}
		return s;
	}
	
	
	/**
	 * Metodo que devuelve un clon en profundidad del método actual.
	 * 
	 * @return tablero		clon del tablero actual.
	 */
	public Tablero clonar() {
		Tablero tablero = new Tablero();
		for (int i = 0; i < fila; i++) {
			for (int j = 0; j < columna; j++) {
				tablero.matriz[i][j] = this.matriz[i][j].clonar() ;
			}
		}
		return tablero;
		
	}
	
	
	/**
	 * Metodo que coloca en la posicion indicada la pieza pasada como argumento.
	 * 
	 * @param fila	entero que indica la fila a la que pertenece la celda donde vamos a colocar la pieza.
	 * @param columna	entero que indica la columna a la que pertenece la celda donde vamos a colocar la pieza.
	 * @param pieza		pieza que vamos a colocar en la celda especificada.
	 */
	public void colocar(int fila, int columna, Pieza pieza) {
		if(estaEnTablero(fila, columna)) {
			if(matriz[fila][columna].consultarPieza() == null) {
				matriz[fila][columna].colocar(pieza);
			}
		}
	}
	
	
	/**
	 * Metodo que devuelve un clon en profundidad de la celda con las coordenadas indicadas.
	 * 
	 * @param fila
	 * @param columna
	 * @return
	 */
	public Celda consultarCelda(int fila, int columna) {
		if(estaEnTablero(fila, columna)) {
			return matriz[fila][columna].clonar();
		}
		return null;
	}
	
	
	public int consultarNumeroColumnas() {
		return matriz[0].length;
	}
	
	
	public int consultarNumeroFilas() {
		return matriz.length;
	}
	
	
	public boolean estaEnTablero(int fila, int columna) {
		if (fila > 0 && columna > 0 && fila < consultarNumeroFilas() && columna < consultarNumeroColumnas()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		return columna == other.columna && fila == other.fila && Arrays.deepEquals(matriz, other.matriz)
				&& Objects.equals(pieza, other.pieza);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(matriz);
		result = prime * result + Objects.hash(columna, fila, pieza);
		return result;
	}


	@Override
	public String toString() {
		return "Tablero [matriz=" + Arrays.toString(matriz) + ", fila=" + fila + ", columna=" + columna + ", pieza="
				+ pieza + "]";
	}
	
	 
}
