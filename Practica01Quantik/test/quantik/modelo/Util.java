package quantik.modelo;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Clase de utilidad para la generación de datos para los tests parametrizados.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220703
 * 
 */
public class Util {

	/** Constructor. */
	private Util() {
		// hide implicit public constructor, only can be used with static class methods
	}

	/**
	 * Genera coordenadas correcta del tablero.
	 * 
	 * @return fila y columna correctas en el tablero
	 */
	static Stream<Arguments> proveerCoordenadas() {
		return Stream.of(Arguments.of(0, 0), Arguments.of(0, 1), Arguments.of(0, 2), Arguments.of(0, 3),
				Arguments.of(1, 0), Arguments.of(1, 1), Arguments.of(1, 2), Arguments.of(1, 3), Arguments.of(2, 0),
				Arguments.of(2, 1), Arguments.of(2, 2), Arguments.of(2, 3), Arguments.of(3, 0), Arguments.of(3, 1),
				Arguments.of(3, 2), Arguments.of(3, 3));
	}

	/**
	 * Genera coordenadas incorrectas del tablero.
	 * 
	 * @return fila y columna incorrectas en el tablero
	 */
	static Stream<Arguments> proveerCoordenadasIncorrectas() {
		return Stream.of(Arguments.of(0, -1), Arguments.of(-1, 1), Arguments.of(-1, -1), Arguments.of(4, 4),
				Arguments.of(4, 0), Arguments.of(-1, -3), Arguments.of(-1, 2), Arguments.of(1, -3), Arguments.of(-2, 0),
				Arguments.of(2, -1), Arguments.of(4, 2), Arguments.of(2, 4), Arguments.of(-1, 3), Arguments.of(3, -1),
				Arguments.of(3, 4), Arguments.of(4, 3), Arguments.of(5, 5));
	}

	/**
	 * Genera las coordenadas de una celda y las coordenadas de las celdas en su grupo horizontal.
	 * 
	 * @return coordenadas de la celda y de celdas en el grupo horizontal
	 */
	static Stream<Arguments> proveerCoordenadasyGruposHorizontales() {
		int[][] grupoHorizontal1 = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } };
		int[][] grupoHorizontal2 = { { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 } };
		int[][] grupoHorizontal3 = { { 2, 0 }, { 2, 1 }, { 2, 2 }, { 2, 3 } };
		int[][] grupoHorizontal4 = { { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 } };

		return Stream.of(Arguments.of(0, 0, grupoHorizontal1), Arguments.of(0, 1, grupoHorizontal1),
				Arguments.of(0, 2, grupoHorizontal1), Arguments.of(0, 3, grupoHorizontal1),
				Arguments.of(1, 0, grupoHorizontal2), Arguments.of(1, 1, grupoHorizontal2),
				Arguments.of(1, 2, grupoHorizontal2), Arguments.of(1, 3, grupoHorizontal2),
				Arguments.of(2, 0, grupoHorizontal3), Arguments.of(2, 1, grupoHorizontal3),
				Arguments.of(2, 2, grupoHorizontal3), Arguments.of(2, 3, grupoHorizontal3),
				Arguments.of(3, 0, grupoHorizontal4), Arguments.of(3, 1, grupoHorizontal4),
				Arguments.of(3, 2, grupoHorizontal4), Arguments.of(3, 3, grupoHorizontal4));
	}

	/**
	 * Genera las coordenadas de una celda y las coordenadas de las celdas en su grupo vertical.
	 * 
	 * @return coordenadas de la celda y de celdas en el grupo vertical
	 */
	static Stream<Arguments> proveerCoordenadasyGruposVerticales() {
		int[][] grupoVertical1 = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } };
		int[][] grupoVertical2 = { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 } };
		int[][] grupoVertical3 = { { 0, 2 }, { 1, 2 }, { 2, 2 }, { 3, 2 } };
		int[][] grupoVertical4 = { { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 3 } };

		return Stream.of(Arguments.of(0, 0, grupoVertical1), Arguments.of(1, 0, grupoVertical1),
				Arguments.of(2, 0, grupoVertical1), Arguments.of(3, 0, grupoVertical1),
				Arguments.of(0, 1, grupoVertical2), Arguments.of(1, 1, grupoVertical2),
				Arguments.of(2, 1, grupoVertical2), Arguments.of(3, 1, grupoVertical2),
				Arguments.of(0, 2, grupoVertical3), Arguments.of(1, 2, grupoVertical3),
				Arguments.of(2, 2, grupoVertical3), Arguments.of(3, 2, grupoVertical3),
				Arguments.of(0, 3, grupoVertical4), Arguments.of(1, 3, grupoVertical4),
				Arguments.of(2, 3, grupoVertical4), Arguments.of(3, 3, grupoVertical4));
	}

	/**
	 * Genera las coordenadas de una celda y las coordenadas de las celdas en su grupo cuadrado.
	 * 
	 * @return coordenadas de la celda y de celdas en el grupo cuadrado
	 */
	static Stream<Arguments> proveerCoordenadasyGruposCuadrados() {
		int[][] grupoCuadrado1 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		int[][] grupoCuadrado2 = { { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 } };
		int[][] grupoCuadrado3 = { { 2, 0 }, { 2, 1 }, { 3, 0 }, { 3, 1 } };
		int[][] grupoCuadrado4 = { { 2, 2 }, { 2, 3 }, { 3, 2 }, { 3, 3 } };

		return Stream.of(Arguments.of(0, 0, grupoCuadrado1), Arguments.of(0, 1, grupoCuadrado1),
				Arguments.of(1, 0, grupoCuadrado1), Arguments.of(1, 1, grupoCuadrado1),

				Arguments.of(0, 2, grupoCuadrado2), Arguments.of(0, 3, grupoCuadrado2),
				Arguments.of(1, 2, grupoCuadrado2), Arguments.of(1, 3, grupoCuadrado2),

				Arguments.of(2, 0, grupoCuadrado3), Arguments.of(2, 1, grupoCuadrado3),
				Arguments.of(3, 0, grupoCuadrado3), Arguments.of(3, 1, grupoCuadrado3),

				Arguments.of(2, 2, grupoCuadrado4), Arguments.of(2, 3, grupoCuadrado4),
				Arguments.of(3, 2, grupoCuadrado4), Arguments.of(3, 3, grupoCuadrado4));
	}

	/**
	 * Genera coordenadas y piezas para rellenar un tablero.
	 * 
	 * @return coordenadas y piezas para rellenar
	 */
	static Stream<Arguments> proveerCoordenadasYPiezas() {
		return Stream.of(Arguments.of(0, 0, new Pieza(Figura.CILINDRO, Color.BLANCO)),
				Arguments.of(0, 1, new Pieza(Figura.CILINDRO, Color.NEGRO)),
				Arguments.of(0, 2, new Pieza(Figura.CONO, Color.BLANCO)),
				Arguments.of(0, 3, new Pieza(Figura.CONO, Color.NEGRO)),
				Arguments.of(1, 0, new Pieza(Figura.CUBO, Color.BLANCO)),
				Arguments.of(1, 1, new Pieza(Figura.CUBO, Color.NEGRO)),
				Arguments.of(1, 2, new Pieza(Figura.ESFERA, Color.BLANCO)),
				Arguments.of(1, 3, new Pieza(Figura.ESFERA, Color.NEGRO)),
				Arguments.of(2, 0, new Pieza(Figura.CILINDRO, Color.BLANCO)),
				Arguments.of(2, 1, new Pieza(Figura.CILINDRO, Color.NEGRO)),
				Arguments.of(2, 2, new Pieza(Figura.CONO, Color.BLANCO)),
				Arguments.of(2, 3, new Pieza(Figura.CONO, Color.NEGRO)),
				Arguments.of(3, 0, new Pieza(Figura.CUBO, Color.BLANCO)),
				Arguments.of(3, 1, new Pieza(Figura.CUBO, Color.NEGRO)),
				Arguments.of(3, 2, new Pieza(Figura.ESFERA, Color.BLANCO)),
				Arguments.of(3, 3, new Pieza(Figura.ESFERA, Color.NEGRO)));
	}
	
	/**
	 * Genera coordenadas y figuras para colocar.
	 * 
	 * @return coordenadas y figuras
	 */
	static Stream<Arguments> proveerCoordenadasYFiguras() {
		return Stream.of(Arguments.of(0, 0, Figura.CILINDRO),
				Arguments.of(0, 1, Figura.CILINDRO),
				Arguments.of(0, 2, Figura.CONO),
				Arguments.of(0, 3, Figura.CONO),
				Arguments.of(1, 0, Figura.CUBO),
				Arguments.of(1, 1, Figura.CUBO),
				Arguments.of(1, 2, Figura.ESFERA),
				Arguments.of(1, 3, Figura.ESFERA),
				Arguments.of(2, 0, Figura.CILINDRO),
				Arguments.of(2, 1, Figura.CILINDRO),
				Arguments.of(2, 2, Figura.CONO),
				Arguments.of(2, 3, Figura.CONO),
				Arguments.of(3, 0, Figura.CUBO),
				Arguments.of(3, 1, Figura.CUBO),
				Arguments.of(3, 2, Figura.ESFERA),
				Arguments.of(3, 3, Figura.ESFERA));
	}


	/**
	 * Provee de todas las combinaciones de figuras y colores.
	 * 
	 * @return todas las combinaciones de figura y color
	 */
	static Stream<Arguments> proveerFiguraYColor() {
		return Stream.of(arguments(Figura.CILINDRO, Color.BLANCO), arguments(Figura.CILINDRO, Color.NEGRO),
				arguments(Figura.CONO, Color.BLANCO), arguments(Figura.CONO, Color.NEGRO),
				arguments(Figura.CUBO, Color.BLANCO), arguments(Figura.CUBO, Color.NEGRO),
				arguments(Figura.ESFERA, Color.BLANCO), arguments(Figura.ESFERA, Color.NEGRO));
	}


	/**
	 * Provee de las coordenadas de las celdas en grupos cuadrados.
	 * 
	 * @return coordenadas de las celdas en grupos cuadrados
	 */
	static Stream<Arguments> proveerGruposCuadrados() {
		int[][] grupoCuadrado1 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		int[][] grupoCuadrado2 = { { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 } };
		int[][] grupoCuadrado3 = { { 2, 0 }, { 2, 1 }, { 3, 0 }, { 3, 1 } };
		int[][] grupoCuadrado4 = { { 2, 2 }, { 2, 3 }, { 3, 2 }, { 3, 3 } };

		return Stream.of(
				Arguments.of((Object) grupoCuadrado1), 
				Arguments.of((Object) grupoCuadrado2), 
				Arguments.of((Object) grupoCuadrado3),
				Arguments.of((Object) grupoCuadrado4));

	}

	/**
	 * Provee de las coordenadas de las celdas en grupos verticales.
	 * 
	 * @return coordenadas de las celdas en grupos verticales
	 */
	static Stream<Arguments> proveerGruposVerticales() { 
		int[][] grupoVertical1 = { { 0, 0 }, {1, 0}, {2, 0}, {3, 0} };		
		int[][] grupoVertical2 = { { 0, 1 }, {1, 1}, {2, 1}, {3, 1} };
		int[][] grupoVertical3 = { { 0, 2 }, {1, 2}, {2, 2}, {3, 2} };
		int[][] grupoVertical4 = { { 0, 3 }, {1, 3}, {2, 3}, {3, 3} };
	
		  return Stream.of(
			      Arguments.of((Object) grupoVertical1),	      
			      Arguments.of((Object) grupoVertical2),	    
			      Arguments.of((Object) grupoVertical3),	      
			      Arguments.of((Object)grupoVertical4)
			    );
	}
	
	/**
	 * Provee de las coordenadas de las celdas en grupos horizontales.
	 * 
	 * @return coordenadas de las celdas en grupos horizontales
	 */
	static Stream<Arguments> proveerGruposHorizontales() {
		int[][] grupoHorizontal1 = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } };
		int[][] grupoHorizontal2 = { { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 } };
		int[][] grupoHorizontal3 = { { 2, 0 }, { 2, 1 }, { 2, 2 }, { 2, 3 } };
		int[][] grupoHorizontal4 = { { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 } };
		
		  return Stream.of(
			      Arguments.of((Object) grupoHorizontal1),	      
			      Arguments.of((Object) grupoHorizontal2),	    
			      Arguments.of((Object) grupoHorizontal3),	      
			      Arguments.of((Object) grupoHorizontal4)
			    );
	}
}
