package quantik.util;

/**
 * 
 * @author Rocío Águeda Miguel
 * @version 1.0
 *
 */
public enum Color {
	
	BLANCO('B'),
	NEGRO('N');
	
	private char caracter;
	
	private Color(char letra) {
		this.caracter = letra;
	}
	
	public char toChar() {
		return caracter;
	}
	
	public Color obtenerContrario() {
		if (Color.BLANCO.toChar() == caracter) {
			return Color.NEGRO;
		} else {
			return Color.BLANCO;
		}
		
	}
}
