package quantik.modelo;
import java.util.Objects;

import quantik.util.*;

/**
 * @author Rocio Agueda Miguel
 * @version 1.0
 * @see quantik.util.Color
 * @see quantik.util.Figura
 *
 */
public class Caja {
	
	/**
	 * Atributo privado pieza
	 */
	private Pieza pieza;
	
	/**
	 * Atributo privado color
	 */
	private Color color;
	
	/**
	 * Atributo privado figura
	 */
	private Figura figura;
	
	
	/**
	 * Constructor de la clase caja que carga ocho piezas nuevas 
	 * 	del color que corresponda.
	 * 
	 * @param color 	color del que queremos que sean las piezas
	 */
	public Caja(Color color) {
		
	}
	
	
	/**
	 * Metodo que devuelve un clon en profundidad de la caja actual.
	 * 
	 * @return Caja		clon de la caja actual.
	 */
	public Caja clonar() {
		return new Caja(this.color);
	}
	
	
	/**
	 * Nos permite consultar el color de las piezas de la caja.
	 * 
	 * @return color 	color de las piezas de la caja.
	 */
	public Color consultarColor() {
		return color;
	}
	
	
	/**
	 * Este metodo devuelve un array con todas los clones de piezas
	 * 	que tenemos en la caja.
	 *  
	 * @return piezas 	array con las piezas que hay en la caja.
	 */
	public Pieza[] consultarPiezasDisponibles() {
		Pieza [] piezasCaja = new Pieza [8];
		for(int i = 0; i < piezasCaja.length; i++) {
			piezasCaja[i] = this.pieza.clonar();
		}
		return piezasCaja;
	}
	
	
	/**
	 * Metodo que devuelve el numero de piezas que hay actualmente
	 * 	en la caja.
	 * 	
	 * @return contador   entero que representa el numero de piezas. 
	 */
	public int contarPiezasActuales() {
		int contador = 0;
			for(int i = 0; i < 8; i++) {
				contador++;
			}
		return contador;
	}
	
	
	/**
	 * Consulta si hay una pieza disponible con la figura pasada como argumento.
	 * @param figura  figura de la pieza que estamos buscando.
	 * @return boolean True si la pieza esta disponinle y False en caso contrario.
	 */
	public boolean estaDisponible(Figura figura) {
		
		boolean encontrado = false;
		
		for(Pieza piezas : consultarPiezasDisponibles()) {
			if(piezas.consultarFigura() == figura) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	
	public Pieza retirar(Figura figura) {
		return null;
			
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(color, figura, pieza);
	}
	
	@Override
	public String toString() {
		return "Caja [pieza =" + pieza + ", color =" + color + ", figura =" + figura + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caja other = (Caja) obj;
		return color == other.color && figura == other.figura && Objects.equals(pieza, other.pieza);
	}
}
