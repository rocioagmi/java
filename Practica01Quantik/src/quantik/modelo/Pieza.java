package quantik.modelo;
import java.util.Objects;

import quantik.util.*;

/** 
 * 
 * @author Rocio Agueda Miguel
 * @version 1.0
 * @see quantik.util.Color
 * @see quantik.util.Figura
 *
 */
public class Pieza {
	
	/**
	 * Atributo privado color.
	 */
	private Color color;
	
	/**
	 * Atributo privado figura.
	 */
	private Figura figura;
	
	/**
	 * Constructor de la clase
	 * Inicializa la figura y el color de la pieza.
	 * 
	 * @param figura forma de la pieza actual.
	 * @param color  negro o blanco de la pieza actual.
	 */
	public Pieza(Figura figura, Color color) {
		this.figura = figura;
		this.color = color;
	}
	
	/**
	 * Método que devuelve la concatenacion de los textos asociados a la figura y color de la pieza.
	 * 
	 * @return String Formado por 3 letras, las dos primeras indican la sigura y la ultima el color.
	 */
	public String aTexto() {
		return String.valueOf(consultarFigura().aTexto()) + String.valueOf(consultarColor().toChar());
	}
	
	public Pieza clonar() {
		return new Pieza(figura, color);
	}
	
	public Color consultarColor() {
		return color;
	}
	
	public Figura consultarFigura() {
		return figura;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(color, figura);
	}
	
	@Override
	public String toString() {
		return "Pieza [color=" + color + ", figura=" + figura + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		return color == other.color && figura == other.figura;
	}
}
