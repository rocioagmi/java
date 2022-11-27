package quantik.modelo;
import java.util.Arrays;
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
	 * Atributo privado color
	 */
	private Color color;
	
	
	/**
	 * Conjnunto de piezas que tenemos en la caja
	 */
	Pieza [] piezasCaja = new Pieza [8];
	
	
	/**
	 * Contador de las piezas de la caja
	 */
	private int contador;
	
	
	/**
	 * Constructor de la clase caja que carga ocho piezas nuevas 
	 * 	del color que corresponda.
	 * 
	 * @param color 	color del que queremos que sean las piezas
	 */
	public Caja(Color color) {
		this.color = color;
		this.piezasCaja[0] = new Pieza(Figura.CILINDRO, color);
		this.piezasCaja[1] = new Pieza(Figura.CILINDRO, color);
		this.piezasCaja[2] = new Pieza(Figura.CONO, color);
		this.piezasCaja[3] = new Pieza(Figura.CONO, color);
		this.piezasCaja[4] = new Pieza(Figura.CUBO, color);
		this.piezasCaja[5] = new Pieza(Figura.CUBO, color);
		this.piezasCaja[6] = new Pieza(Figura.ESFERA, color);
		this.piezasCaja[7] = new Pieza(Figura.ESFERA, color);
		contador = 8;
		
	}
	
	
	/**
	 * Metodo que devuelve un clon en profundidad de la caja actual.
	 * 
	 * @return Caja		clon de la caja actual.
	 */
	public Caja clonar() {
		Caja cajaClon = new Caja(this.color);
		//cajaClon.piezasCaja = this.piezasCaja.clone();
		for(int i = 0; i < this.piezasCaja.length; i++) {
			cajaClon.piezasCaja[i].consultarFigura();
		}
		return cajaClon;
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
		return this.clonar().piezasCaja;
	}
	
	
	/**
	 * Metodo que devuelve el numero de piezas que hay actualmente
	 * 	en la caja.
	 * 	
	 * @return contador   entero que representa el numero de piezas. 
	 */
	public int contarPiezasActuales() {
		return contador; 
	}
	
	
	/**
	 * Consulta si hay una pieza disponible con la figura pasada como argumento.
	 * @param figura  figura de la pieza que estamos buscando.
	 * @return boolean True si la pieza esta disponinle y False en caso contrario.
	 */
	public boolean estaDisponible(Figura figura) {
		
		boolean encontrado = false;
		
		for(Pieza pieza : this.piezasCaja) {
			if(pieza != null && pieza.consultarFigura().aTexto() == figura.aTexto()) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	
	public Pieza retirar(Figura figura) {
		 
		Pieza pieza = null;
		for(int i = 0; i < this.piezasCaja.length; i++) {
			if(this.piezasCaja[i] != null && this.piezasCaja[i].consultarFigura().aTexto() == figura.aTexto()) {
				pieza = this.piezasCaja[i].clonar();
				this.piezasCaja[i] = null;
				contador -= 1; 
			}	
		}
		return pieza;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(piezasCaja);
		result = prime * result + Objects.hash(color, contador);
		return result;
	}
	
	
	@Override
	public String toString() {
		return "Caja [color=" + color + ", piezasCaja=" + Arrays.toString(piezasCaja) + ", contador=" + contador + "]";
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
		return color == other.color && contador == other.contador && Arrays.equals(piezasCaja, other.piezasCaja);
	}
}
