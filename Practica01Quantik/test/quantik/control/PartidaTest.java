package quantik.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import quantik.modelo.Caja;
import quantik.modelo.Pieza;
import quantik.modelo.Tablero;
import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests unitarios sobre la partida. 
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.1 20221102
 */
@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@DisplayName("Tests sobre Partida (test unitarios simulando con Mockito el resto de clases salvo Tablero).")
@Timeout(value = 3, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class PartidaTest {

	/** Tablero. */
	@Spy Tablero tablero; // warning, NOT a mock 
	
	/** Caja de piezas blancas. */
	@Mock Caja cajaBlancas;
	
	/** Caja de piezas negras. */
	@Mock Caja cajaNegras;
	
	/** Pieza blanca. */
	@Mock Pieza piezaBlanca;
	
	/** Partida. */
	Partida partida;	

	/**
	 * Inicialización.
	 * 
	 * Dado que hay un bug en Mockito al inyectar dos argumentos del mismo tipo 
	 * (realmente instancia dos veces el mismo Mock) se opta por definir un setup
	 * con la instanciacion manual, en lugar de automática.
	 * 
	 * Adicionalmente se utiliza un Mockito.spy en lugar de la anotación @Spy 
	 * sobre el atributo partida.
	 * 
	 * @since 1.1 20221102
	 */
	@BeforeEach // Injecting manually...
	void inyectar() {
		// Instanciamos el objeto inyectando los Mocks adecuados en cada posición...
		Partida partidaSpy = new Partida(tablero, cajaBlancas, cajaNegras); 
		partida = Mockito.spy(partidaSpy); // enchufamos el "espía"
	}
	
	/** Comprueba que el turno inicial es correcto. */
	@Test
	@DisplayName("Comprobar que el turno inicial es correcto.")
	void probarTurnoEnConstructor() {
		assertThat("En una partida siempre debe iniciar turno el color BLANCO.", partida.consultarTurno(), is(Color.BLANCO));
	}
	
	/** Comprueba que se inicializa realmente con el tablero pasado en el constructor. */
	@Test
	@DisplayName("Comprobar que se inicializa con el tablero pasado en el constructor.")
	void probarTableroEnConstructor() {
		when(tablero.clonar()).thenReturn(tablero); // mockeamos devolviendo el propio tablero sin clonar en la consulta...
		assertThat("En una partida el tablero utilizado debe ser el pasado en el constructor (aunque sea un clon...).", partida.consultarTablero(), is(tablero));
	}
	
	/** 
	 * Comprueba que el número de jugadas inicial es cero.
	 * 
	 * @since 1.1 20221102
	 */
	@Test
	@DisplayName("Comprobar que el número de jugadas inicialmente es cero.")
	void probarNumeroJugadaAlIniciar() {
		// relajamos con lenient el uso de ambos métodos para dar distintas opciones de implementación
		lenient().when(cajaBlancas.contarPiezasActuales()).thenReturn(8); 
		lenient().when(cajaNegras.contarPiezasActuales()).thenReturn(8); 
		assertThat("Una partida iniciada debería tener cero jugadas.", partida.consultarNumeroJugada(), is(0));
	}
	
	/** 
	 * Comprueba que la consulta de la legalidad de movimientos no utiliza métodos que modifiquen el estado de la partida. 
	 * 
	 * @param fila fila
	 * @param columna columna
	 * @param figura figura 
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadasYFiguras")
	@Tag("WhiteBox")
	@DisplayName("Comprobar que al consultar la legalidad de movimientos no modifica el estado del objeto.")
	void probarQueLaConsultaDeMovimientoLegalNoInvocaAMetodosDeCambioDeEstado(int fila, int columna, Figura figura) {
		partida.esJugadaLegalEnTurnoActual(fila, columna, figura);
		verify(partida, never().description("Al comprobar la legalidad de la jugada no deberíamos cambiar de turno.")).cambiarTurno();
		verify(partida, never().description("Al comprobar la legalidad de la jugada no deberíamos colocar piezas sobre el tablero.")).colocarPiezaEnTurnoActual(anyInt(), anyInt(), any());
	}
	
	/** 
	 * Comprueba que la consulta de partida acabada no utiliza métodos que modifiquen el estado de la partida. 
	 */
	@Test
	@Tag("WhiteBox")
	@DisplayName("Comprobar que la consulta de partida acabada no modifica el estado del objeto.")
	void probarQueLaConsultaDePartidaAcabadaNoInvocaAMetodosDeCambioDeEstado() {
		partida.estaAcabadaPartida();
		verify(partida, never().description("Al comprobar partida acabada no deberíamos cambiar de turno.")).cambiarTurno();
		verify(partida, never().description("Al comprobar partida acabada no deberíamos colocar piezas sobre el tablero.")).colocarPiezaEnTurnoActual(anyInt(), anyInt(), any());
	}
	
	
	/** 
	 * Comprueba que la consulta de turno actual bloqueado no utiliza métodos que modifiquen el estado de la partida.
	 * 
	 * Añadido el mocking del método consultarPiezasDisponibles en Caja.
	 * 
	 *  @version 1.2 20221109
	 */
	@Test
	@Tag("WhiteBox")
	@DisplayName("Comprobar que la consulta de turno actual bloqueado no modifica el estado del objeto.")
	void probarQueLaConsultaDeTurnoActualBloqueadoNoInvocaAMetodosDeCambioDeEstado() {
		Pieza[] piezasDisponibles = {
				new Pieza(Figura.CILINDRO, Color.BLANCO), 
				new Pieza(Figura.CILINDRO, Color.BLANCO), 
				new Pieza(Figura.CONO, Color.BLANCO),
				new Pieza(Figura.CONO, Color.BLANCO),
				new Pieza(Figura.CUBO, Color.BLANCO),
				new Pieza(Figura.CUBO, Color.BLANCO),
				new Pieza(Figura.ESFERA, Color.BLANCO),
				new Pieza(Figura.ESFERA, Color.BLANCO)
				};
		lenient().when(cajaBlancas.consultarPiezasDisponibles()).thenReturn(piezasDisponibles);
		partida.estaBloqueadoTurnoActual();
		verify(partida, never().description("Al comprobar turno actual bloqueado no deberíamos cambiar de turno.")).cambiarTurno();
		verify(partida, never().description("Al comprobar turno actual bloqueado no deberíamos colocar piezas sobre el tablero.")).colocarPiezaEnTurnoActual(anyInt(), anyInt(), any());
	}
	
	/** 
	 * Comprueba que la consulta de existencia de grupo completo no utiliza métodos que modifiquen el estado de la partida. 
	 */
	@Test
	@Tag("WhiteBox")
	@DisplayName("Comprobar que la consulta de existencia de grupo completo no modifica el estado del objeto.")
	void probarQueLaConsultaDeGrupoCompletoNoInvocaAMetodosDeCambioDeEstado() {
		partida.hayAlgunGrupoCompleto();
		verify(partida, never().description("Al comprobar existencia de grupo completo no deberíamos cambiar de turno.")).cambiarTurno();
		verify(partida, never().description("Al comprobar existencia de grupo completo no deberíamos colocar piezas sobre el tablero.")).colocarPiezaEnTurnoActual(anyInt(), anyInt(), any());
	}
	
}
