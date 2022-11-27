package quantik.modelo;
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
	
	private Celda[] celdas;
	private Color color;
	private Figura figura;
	private Pieza pieza;
	

	public Grupo(Celda[] celdas) {
		celdas = new Celda[1];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				
				celdas = new Celda[i];
			}
		}
	}
	
	public String aTexto() {
		
	}
	
	public Pieza clonar() {
		
	}
	
	public Color consultarColor() {
		
	}
	
	public Figura consultarFigura() {
		
	}
	
	public boolean equals(Object obj) {
		
	}
	
	public boolean estaCompletoConFigurasDiferentes() {
		
	}
	
	public boolean existeMismaPiezaDelColorContrario(Figura figura, Color color) {
		
	}
	
	public int hashCode() {
		
	}
	
	public String toString() {
		
	}
}

