package quantik.util;

/**
 * 
 * @author Rocío Águeda Miguel
 * @version 1.0
 *
 */
public enum Figura {
	
	CILINDRO("CL"),
	CONO("CN"),
	CUBO("CB"),
	ESFERA("ES");
	
	private String texto;
	
	private Figura(String texto) {
		this.texto = texto;
	}
	
	public String aTexto() {
		return texto;
	}
}
