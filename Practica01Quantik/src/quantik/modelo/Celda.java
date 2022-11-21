package quantik.modelo;

import java.util.Objects;

/**
 * 
 * @author Rocio Agueda Miguel
 * @version 1.0
 * @see quiantik.modelo.Pieza
 *
 */
public class Celda {
	
	/**
	 * Atributo privado fila
	 */
	private int fila;
	
	/**
	 * Atributo privado columna
	 */
	private int columna;
	
	/**
	 * Atributo privado pieza
	 */
	private Pieza pieza;
	
	/**
	 * Constructor de la clase que inicializa la celda vacía, 
	 * 	solo tendra las coordenadas de su posicion fija en el tablero.
	 *
	 * @param fila numero de la fila en la que se situa la celda
	 * @param columna numero de la columna en la que se situa la celda
	 */
	public Celda(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.pieza = null;
	}
	
	
	/**
	 * Metodo que devuelve un clon en profundidad de la celda actual.
	 * 
	 * @return celda clon de la celda actual.
	 */
	public Celda clonar() {
		Celda celda = new Celda(this.fila, this.columna);
		celda.colocar(pieza.clonar());
		return celda;
	}
	
	/**
	 * Procedimiento que establece una pieza pasada como argumento en
	 * 	la celda actual.
	 * 
	 * @param pieza pieza que vamos a colocar en la celda.
	 */
	public void colocar(Pieza pieza){
		this.pieza = pieza;
	}
	
	
	public int consultarColumna() {
		return columna;
	}
	
	
	public int consultarFila() {
		return fila;
	}
	
	
	public Pieza consultarPieza() {
		return pieza;
	}
	
	
	/**
	 * Metodo que comprueba si hay una pieza o no colocada en la celda actual.
	 * 
	 * @return boolean True si la celda esta vacia y False en caso contrario.
	 */
	public boolean estaVacia() {
		return (pieza == null);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return columna == other.columna && fila == other.fila && Objects.equals(pieza, other.pieza);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(columna, fila, pieza);
	}
	
	@Override
	public String toString() {
		return "Celda [fila =" + fila + ", columna =" + columna + ", pieza =" + pieza + "]";
	}

}
