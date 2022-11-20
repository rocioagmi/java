package quantik.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests sobre el tablero.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220703
 * 
 */
@DisplayName("Tests sobre Tablero (depende de implementaciones reales de Pieza, Celda, Figura y Color)")
@Tag("IntegrationTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class TableroTest {
	
	/** Tablero. */
	private Tablero tablero;
	
	/** Inicialización. */
	@BeforeEach
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void inicializar() {
		tablero = new Tablero();
	}

	/**
	 * Comprueba que el constructor del tablero crea un tablero correcto.
	 */
	@Test
	@DisplayName("Comprobar que el constructor inicializa correctamente el tablero.")
	void probarConstructor() {
		assertAll("dimensiones correctas del tablero", 
				() -> assertThat("Número de filas incorrecto.", tablero.consultarNumeroFilas(), is(4)),
				() -> assertThat("Número de columnas incorrecto.", tablero.consultarNumeroColumnas(), is(4)));
	}
	
	/**
	 * Comprueba que coordenadas correctas sí pertenecen al tablero.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @see quantik.modelo.Util#proveerCoordenadas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que las coordenadas correctas sí se reconocen que están en el tablero.")
	void probarComprobacionCoordenadasCorrectas(int fila, int columna) {
		assertThat("Las coordenadas deberían estar en el tablero.", tablero.estaEnTablero(fila, columna), is(true));
	}
	
	/**
	 * Comprueba que coordenadas incorrectas NO pertenecen al tablero.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @see quantik.modelo.Util#proveerCoordenadasIncorrectas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadasIncorrectas")
	@DisplayName("Comprobar que las coordenadas fuera del tablero no se reconocen como pertenecientes al mismo .")
	void probarComprobacionCoordenadasIncorrectas(int fila, int columna) {
		assertThat("Las coordenadas no deberían estar en el tablero.", tablero.estaEnTablero(fila, columna), is(false));
	}
	
	/**
	 * Comprueba que las celdas de un tablero recién creado están vacías.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @see quantik.modelo.Util#proveerCoordenadas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que las celdas de un tablero recién creado están vacías.")
	void probarCeldasVacias(int fila, int columna) {
		Celda celda = tablero.obtenerCelda(fila, columna);
		assertAll("",
				() -> assertThat(celda.consultarFila(), is(fila)),
				() -> assertThat(celda.consultarColumna(), is(columna)),
				() -> assertThat("", celda.estaVacia(),is(true))
				);
	}
	
	/**
	 * Comprueba que la consulta de celdas de un tablero es correcta devolviendo un clon de la celda original.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @see quantik.modelo.Util#proveerCoordenadas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que la consulta de celdas clonando su contenido es correcto.")
	void probarConsultaCeldasClonadas(int fila, int columna) {
		Celda celdaClonada = tablero.consultarCelda(fila, columna);
		assertAll("",
				() -> assertThat(celdaClonada.consultarFila(), is(fila)),
				() -> assertThat(celdaClonada.consultarColumna(), is(columna)),
				() -> assertThat("", celdaClonada.estaVacia(),is(true)),
				() -> assertNotSame(celdaClonada, tablero.obtenerCelda(fila, columna), "")
				);
	}
	
	/**
	 * Comprueba que la consulta de celdas fuera del tablero devuelve nulo.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @see quantik.modelo.Util#proveerCoordenadasIncorrectas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadasIncorrectas")
	@DisplayName("Comprobar que la consulta de celdas fuera del tablero devuelve nulo.")
	void probarConsultaIncorrecta(int fila, int columna) {
		assertAll("comprobar accesos fuera del tablero al consultar celda",
				() -> assertThat("Celda consultada fuera del tablero debería ser nula.", tablero.consultarCelda(fila, columna), is(nullValue()))
		);
	}
	
	/**
	 * Comprueba que la obtención de celdas fueran del tablero devuelve nulo.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @see quantik.modelo.Util#proveerCoordenadasIncorrectas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadasIncorrectas")
	@DisplayName("Comprobar que la obtención de celdas fuera del tablero devuelve nulo.")
	void probarObtencionCeldasIncorrectas(int fila, int columna) {
		assertAll("comprobar accesos fuera del tablero al obtener celda",
				() -> assertThat("Celda obtenida fuera del tablero debería ser nula.", tablero.obtenerCelda(fila, columna), is(nullValue()))
		);
	}
	
	/**
	 * Comprueba que la colocación de piezas en el tablero es correcta.
	 *
	 * @param fila fila 
	 * @param columna columna
	 * @param pieza pieza
	 * @see quantik.modelo.Util#proveerCoordenadasYPiezas
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadasYPiezas")
	@DisplayName("Comprobar que la colocación de piezas en celdas del tablero es correcta.")
	void probarColocarPiezas(int fila, int columna, Pieza pieza) {
		tablero.colocar(fila, columna, pieza);
		assertAll("colocación de piezas",
		 () -> assertThat("Pieza mal colocada en " + fila + " / " + columna, tablero.obtenerCelda(fila, columna).consultarPieza(), is(pieza)),
		 () -> assertThat("Pieza mal colocada en " + fila + " / " + columna, tablero.consultarCelda(fila, columna).consultarPieza(), is(pieza))
		);
	}
	
	
	/** 
	 * Comprueba que la generación de texto para un tablero vacío es correcta. 
	 */
	@DisplayName("Comprobar la generación de la cadena de texto en toString con tablero vacío")
	@Test
	void comprobarCadenaTextoConTableroVacio() {
		String cadenaEsperada = """
					0	  1	    2	  3
				0 ----- ----- ----- -----
				1 ----- ----- ----- -----
				2 ----- ----- ----- -----
				3 ----- ----- ----- -----
				""";

		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		// eliminamos espacios/tabuladores para comparar
		String salida = tablero.aTexto().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero vacío es incorecta.");
	}
	
	/** 
	 * Comprueba que la generación de la cadena de texto para un tablero con algunas jugadas es correcta. 
	 */
	@DisplayName("Comprobar que la generación de la cadena de texto en toString con tablero con algunas jugadas")
	@Test
	void comprobarCadenaTextoConTableroConAlgunasJugadas() {
		tablero.colocar(0, 0, new Pieza(Figura.CILINDRO, Color.BLANCO));
		tablero.colocar(1, 1, new Pieza(Figura.CONO, Color.NEGRO));
		tablero.colocar(2, 2, new Pieza(Figura.CUBO, Color.BLANCO));
		tablero.colocar(3, 3, new Pieza(Figura.ESFERA, Color.NEGRO));
		
		String cadenaEsperada = """
					0	  1	    2	  3
				0 -CLB- ----- ----- -----
				1 ----- -CNN- ----- -----
				2 ----- ----- -CBB- -----
				3 ----- ----- ----- -ESN-
				""";

		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		// eliminamos espacios/tabuladores para comparar
		String salida = tablero.aTexto().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero con algunas jugadas es incorecta.");
	}
	
	
	/** 
	 * Comprueba que la clonación de un tablero vacío es correcta. 
	 * */
	@DisplayName("Comprobar la correcta clonación de un tablero vacío")
	@Test
	void comprobarClonacionDeUnTableroVacio() {
		Tablero tableroClonado = tablero.clonar();
		assertAll("clonación de un tablero vacío",
				() -> assertNotSame(tableroClonado, tablero, "Ambas referencias apuntan al mismo objeto, no se ha clonado."),
				() -> assertEquals(tableroClonado, tablero, "Ambos tableros no coinciden en contenido."));
	}
	
	/** 
	 * Comprueba la clonación de un tablero con algunas jugadas es correcta. 
	 */
	@DisplayName("Comprobar la correcta clonación de un tablero con algunas jugadas")
	@Test
	void comprobarClonacionDeUnTableroConAlgunosJugadas() {
		// given tablero con cuatro piezas colocadas en la diagonal...
		tablero.colocar(0, 0, new Pieza(Figura.CILINDRO, Color.BLANCO));
		tablero.colocar(1, 1, new Pieza(Figura.CONO, Color.NEGRO));
		tablero.colocar(2, 2, new Pieza(Figura.CUBO, Color.BLANCO));
		tablero.colocar(3, 3, new Pieza(Figura.ESFERA, Color.NEGRO));
		// when clonamos
		Tablero tableroClonado = tablero.clonar();
		assertAll("clonación de un tablero con varias jugadas",
				() -> assertNotSame(tableroClonado, tablero, "Ambas referencias apuntan al mismo objeto, no se ha clonado."),
				() -> assertEquals(tableroClonado, tablero, "Ambos tableros no coinciden en contenido."));
		
	}	
	
}
