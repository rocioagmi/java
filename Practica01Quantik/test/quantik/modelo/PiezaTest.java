package quantik.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests sobre la pieza. 
 * 
 * Depende en compilación de Figura y Color (tipos enumerados).
 * 
 * El uso de la biblioteca mockito-inline-4.6.1.jar permitiría "mockear"
 * tipos enumerados pero dados los problemas que genera con OpenJDK 17 en 
 * GNU/Linux se trabaja directamente con el tipo enumerado. 
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220703
 * 
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests sobre Pieza (depende de Figura y Color).")
@Tag("IntegrationTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class PiezaTest {
	
	/**
	 * Comprueba que el constructor inicializa correctamente las piezas.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@DisplayName("Comprobar que el constructor inicializa correctamente.")
	@ParameterizedTest
	@MethodSource("proveerFiguraYColor")
	void constructorBasico(Figura figura, Color color) {
		// preparar
		Pieza pieza = new Pieza(figura, color);
		// comprobar
		assertAll("comprobando que el constructor inicializa correctamente la figura y el color",
				() -> assertThat("Figura mal inicializada.", pieza.consultarFigura(), is(figura)),
				() -> assertThat("Color mal inicializado", pieza.consultarColor(), is(color)));
	}

	/**
	 * Comprueba que el texto generado para cada pieza es correcto.
	 *  
	 * @param figura figura
	 * @param color color
	 * @param texto texto completo para la pieza
	 */
	@DisplayName("Comprobar que el texto generado para cada pieza es correcto.")
	@ParameterizedTest
	@MethodSource("proveerTodoTipoDePiezaConTexto")
	void comprobarTextos(Figura figura, Color color, String texto) {
		// given
		Pieza pieza = new Pieza(figura, color);
		// then
		assertThat("Texto incorrecto generado para la pieza con figura " + figura
				+ " de color " + color, pieza.aTexto(), is(texto));
	}
	
	/**
	 * Comprueba que la clonación de una pieza es correcta.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@DisplayName("Comprobar que el clon generado de una pieza es correcto.")
	@ParameterizedTest
	@MethodSource("proveerFiguraYColor")
	void comprobarClonacion(Figura figura, Color color) {
		// given
		Pieza pieza = new Pieza(figura, color);
		// when
		Pieza clon = pieza.clonar();
		// then
		assertAll("comprobando estado del clon",
				() -> assertNotSame(pieza, clon, "No deberían ser iguales las referencias."),
				() -> assertThat("El contenido del clon y la pieza original debería ser igual.", clon, is(pieza))
			);		
	}
	
	// Métodos de utilidad para los tests...

	/**
	 * Provee de piezas con todas las combinaciones de figuras, colores y sus textos asociados.
	 * 
	 * @return todas las combinaciones de figuras con su color y texto completo, texto de figura y carácter de color
	 */
	private static Stream<Arguments> proveerTodoTipoDePiezaConTexto() {
		return Stream.of(arguments(Figura.CILINDRO, Color.BLANCO, "CLB"),
				arguments(Figura.CILINDRO, Color.NEGRO, "CLN"),
				arguments(Figura.CONO, Color.BLANCO, "CNB"),
				arguments(Figura.CONO, Color.NEGRO, "CNN"),
				arguments(Figura.CUBO, Color.BLANCO, "CBB"),
				arguments(Figura.CUBO, Color.NEGRO, "CBN"),
				arguments(Figura.ESFERA, Color.BLANCO, "ESB"),
				arguments(Figura.ESFERA, Color.NEGRO, "ESN"));
	}
	
	/**
	 * Provee de todas las combinaciones de figuras y colores.
	 * 
	 * @return todas las combinaciones de figura y color
	 */
	private static Stream<Arguments> proveerFiguraYColor() {
		return Stream.of(arguments(Figura.CILINDRO, Color.BLANCO),
				arguments(Figura.CILINDRO, Color.NEGRO),
				arguments(Figura.CONO, Color.BLANCO),
				arguments(Figura.CONO, Color.NEGRO),
				arguments(Figura.CUBO, Color.BLANCO),
				arguments(Figura.CUBO, Color.NEGRO),
				arguments(Figura.ESFERA, Color.BLANCO),
				arguments(Figura.ESFERA, Color.NEGRO));
	}

}
