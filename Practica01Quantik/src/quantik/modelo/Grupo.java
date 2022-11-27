package quantik.modelo;
import java.util.Arrays;
import java.util.Objects;

import quantik.util.*;

/**  
 * 
 * @author Rocio Agueda Miguel
 * @see quantik.util.Color
 * @see quantik.util.Figura
 * @see quantik.modelo.Celda
 * @see quantik.modelo.Pieza
 *
 */
public class Grupo {
	
	/**
	 * Referencias a las cuatro celdas del tablero que conforman el grupo.
	 */
	private Celda [] celdas = new Celda [3];
	
	
	/**
	 * Atributo privado celda.
	 */
	private Celda celda;
	
	
	/**
	 * Constructor de la clase que se inicializa con las referencias
	 * 	a las cuatro celdas del tablero que conforman el grupo.
	 * 
	 * @param celdas	array con las referencias a las celdas del grupo.
	 */
	public Grupo(Celda[] celdas) {
		for(int i = 0; i < this.celdas.length; i++) {
			this.celdas[i] = celda.clonar();
		}
	}
	
	
	/**
	 * Metodo que devuelve un clon en profundidad del grupo actual.
	 * 
	 * @return grupo	clon de Grupo.
	 */
	public Grupo clonar() {
		Grupo grupo = new Grupo(this.celdas);
		grupo.celdas = celdas.clone();
		return grupo;
	}
	
	
	/**
	 * Devuelve el numero de celdas que componen actualmente el grupo.
	 * 
	 * @return int	numero de celdas que forman el grupo.
	 */
	public int consultarNumeroCeldas() {
		return this.celdas.length;
	}
	
	
	/**
	 * Devuelve el numero de piezas que se encuentran actualmente en las celdas
	 *  del grupo.
	 *  
	 * @return int	numero de piezas que estan en las celdas del grupo.
	 */
	public int consultarNumeroPiezas() {
		int contador = 0;
		for(Celda celda : this.celdas) {
			if(celda.estaVacia() != true) {
				contador ++;
			}
		}
		return contador;
	}
	
	
	/**
	 * Metodo que comprueba si existe una celda en el grupo igual a la celda
	 *  pasada como argumento.
	 *  
	 * @param celdaABuscar celda que queremos saber si esta en el grupo.
	 * @return boolean	True si la celda esta en el grupo y False en caso contrario.
	 */
	public boolean contieneCelda(Celda celdaABuscar) {
		boolean contiene = false;
		for(int i = 0; i < this.celdas.length; i++) {
			if(this.celdas[i] == celdaABuscar ) {
				contiene = true;
			}
		}
		return contiene;
	}
	
	
	/**
	 * Comprueba si hay cuatro piezas con cuatro figuras diferentes, 
	 *  sin tener en cuenta el color de estas.
	 * 
	 * @return boolean  True si hay cuatro piezas con figuras distintas en el grupo
	 * 						y False en caso contrario.
	 */
	public boolean estaCompletoConFigurasDiferentes() {
		boolean sonDistintos = false;
		for(int i = 0; i < this.celdas.length; i++) {
			if(celdas[i].consultarPieza() != null && celdas[i].consultarPieza().consultarFigura().aTexto() != celdas[i + 1].consultarPieza().consultarFigura().aTexto() ) {
				sonDistintos = true;
			}
		} return sonDistintos;
	}
	
	
	/**
	 * Comprueba si en las celdas del grupo hay alguna pieza que tiene la misma figura
	 *  y es de color contrario al pasado como argumento.
	 * 
	 * @param figura	figura de la pieza que estamos buscando.
	 * @param color 	color contrario al de la figura que estoy buscando.
	 * @return boolean 	True si encuentra una pieza con la misma figura y color contrario 
	 * 						al pasado y False en caso contrario.
	 */
	public boolean existeMismaPiezaDelColorContrario(Figura figura, Color color) {
		boolean existe = false;
		for(int i = 0; i < this.celdas.length; i++) {
			if(celdas[i].consultarPieza() != null && celdas[i].consultarPieza().consultarFigura().aTexto() == figura.aTexto()){
				if(celdas[i].consultarPieza().consultarColor().toChar() == color.obtenerContrario().toChar()){
					existe = true;
				}
			}
		}return existe;	
	} 
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return Objects.equals(celda, other.celda) && Arrays.equals(celdas, other.celdas);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(celdas);
		result = prime * result + Objects.hash(celda);
		return result;
	}
	
	@Override
	public String toString() {
		return "Grupo [celdas=" + Arrays.toString(celdas) + ", celda=" + celda + "]";
	}
}

