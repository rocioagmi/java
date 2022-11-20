package quantik.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import quantik.modelo.Caja;
import quantik.modelo.Pieza;
import quantik.modelo.Tablero;
import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests de integración sobre la partida.
 * 
 * Para poder pasar los tests debe estar completada toda la funcionalidad del
 * resto de paquetes.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.1 20221109
 */
@DisplayName("Tests sobre Partida (depende de TODO el resto de implementaciones reales de clases).")
@Tag("IntegrationTest")
@Timeout(value = 3, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class PartidaIntegrationTest {

	/** Tablero. */
	private Tablero tablero;

	/** Caja de piezas blancas. */
	private Caja cajaBlancas;

	/** Caja de piezas negras. */
	private Caja cajaNegras;

//	/** Gestor de grupos. */
//	private GestorGrupos gestorGrupos;

	/** Partida. */
	private Partida partida;

	/**
	 * Inicialización.
	 */
	@Timeout(value = 1, unit = TimeUnit.SECONDS)
	@BeforeEach
	void inicializar() {
		tablero = new Tablero();
		cajaBlancas = new Caja(Color.BLANCO);
		cajaNegras = new Caja(Color.NEGRO);
		partida = new Partida(tablero, cajaBlancas, cajaNegras);
	}

	/**
	 * Inicialización de la partida.
	 *
	 */
	@Nested
	@DisplayName("Iniciando partida.")
	class InicioDePartida {

		/**
		 * Comprueba que no hay ganador al iniciar una partida.
		 */
		@Test
		@DisplayName("Comprobar que no hay ganador al inicio.")
		void probarGanadorNuloEnConstructor() {
			assertThat("En un unicio de partida no debería haber ganador.", partida.consultarGanador(),
					is(nullValue()));
		}

		/**
		 * Comprueba que no hay ningún grupo ganador al iniciar.
		 */
		@Test
		@DisplayName("Comprobar que no hay ningún grupo con cuatro piezas de figuras diferentes al comienzo de la partida")
		void probarNoHayGrupoConCuatroPiezasDiferentesAlIniciar() {
			assertThat("En una partida iniciada nunca debería estar bloqueado el turno inicla.",
					partida.hayAlgunGrupoCompleto(), is(false));
		}

		/**
		 * Comprueba que no está bloqueado el turno inicial.
		 */
		@Test
		@DisplayName("Comprobar que no está bloqueado el turno inicial al comienzo de la partida")
		void probarNoBloqueadoTurnoInicialAlIniciar() {
			assertThat("En una partida iniciada nunca debería estar bloqueado el turno inicla.",
					partida.estaBloqueadoTurnoActual(), is(false));
		}

		/**
		 * Comprueba que no está acabada al iniciar una partida.
		 */
		@Test
		@DisplayName("Comprobar que no está acabada al iniciar la partida")
		void probarNoAcabadaAlIniciar() {
			assertThat("Una partida iniciada nunca debería estar acabada.", partida.estaAcabadaPartida(), is(false));
		}

		/**
		 * Comprueba que es legal colocar cualquier pieza en cualquier posición al
		 * inicio de la partida.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 * @param pieza   pieza
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadasYPiezas")
		@DisplayName("Comprobar que es legal colocar cualquier pieza en una partida con el tablero vacío.")
		void probarColocarPiezasInicialmente(int fila, int columna, Pieza pieza) {
			assertThat("", partida.esJugadaLegalEnTurnoActual(fila, columna, pieza.consultarFigura()), is(true));
		}
	}

	/**
	 * Cambiando turno con algunas jugadas iniciales.
	 *
	 */
	@Nested
	@DisplayName("Cambio de turno con unas pocas jugadas iniciales.")
	class CambioDeTurno {

		/**
		 * Comprueba el turno inicial.
		 */
		@Test
		@DisplayName("Comprobar que el turno inicial es correcto.")
		void comprobarTurnoInicial() {
			assertThat("El turno inicial antes de realizar jugadas debería ser BLANCO.", partida.consultarTurno(),
					is(Color.BLANCO));
		}

		/**
		 * Comprueba el cambio de turno tras una jugada.
		 */
		@Test
		@DisplayName("Comprobar que se cambia el turno tras jugada.")
		void comprobarCambioTurnoTrasPrimerJugada() {
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // turno blanco
			partida.cambiarTurno();
			assertThat("No cambia turno tras primera jugada de color BLANCO.", partida.consultarTurno(),
					is(Color.NEGRO));
		}

		/**
		 * Comprueba cambios de turno sucesivos con dos jugadas.
		 */
		@Test
		@DisplayName("Comprobar que se cambia el turno en dos jugadas sucesivas.")
		void comprobarCambioDeTurnosSucesivosConDosJugadas() {
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // turno blanco
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CILINDRO); // turno negro
			partida.cambiarTurno();
			assertThat("No cambia turno ante dos jugadas sucesivas de ambos colores.", partida.consultarTurno(),
					is(Color.BLANCO));
		}

		/**
		 * Comprueba cambios de turno sucesivos con tres jugadas.
		 */
		@Test
		@DisplayName("Comprobar que se cambia el turno en tres jugadas sucesivas.")
		void comprobarCambioDeTurnosSucesivosConTresJugadas() {
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // turno blanco
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CILINDRO); // turno negro
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 1, Figura.CONO); // turno blanco
			partida.cambiarTurno();
			assertThat("No recupera el turno ante tres jugadas sucesivas de ambos colores.", partida.consultarTurno(),
					is(Color.NEGRO));
		}
	}

	/**
	 * Comprobando jugadas ilegales colocando fuera del tablero.
	 *
	 */
	@Nested
	@DisplayName("Jugadas ilegales colocando fuera del tablero")
	class JugadasIlegalesColocandoFueraDelTablero {

		/**
		 * Comprueba que no se puede jugar en coordenadas fuera del tablero.
		 * 
		 * @param fila fila
		 * @param columna columna 
		 */ 
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadasIncorrectas")
		@DisplayName("Comprobar que no deja jugar fuera de los límites del tablero")
		void comprobarQueNoDejaJugarFueraDelTablero(int fila, int columna) {
			assertThat("No se puede jugar a coordenadas fuera del tablero.",
					partida.esJugadaLegalEnTurnoActual(fila, columna, Figura.CILINDRO), is(false));
		}

	}
	
	/**
	 * Comprobando jugadas ilegales al intentar colocar piezas no disponibles
	 *
	 */
	@Nested
	@DisplayName("Jugadas ilegales colocando piezas no disponibles")
	class JugadasIlegalesColocandoPiezasNoDisponibles {

		/**
		 * Comprueba que no se puede colocar una tercera pieza no disponible.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Comprobar que no deja colocar una tercera pieza no disponible")
		void comprobarQueNoDejaColocarUnaTerceraPiezaNoDisponible(Figura figura) {
			// no cambiamos turno para colocar todas la piezas del mismo color
			partida.colocarPiezaEnTurnoActual(0, 0, figura); 
			partida.colocarPiezaEnTurnoActual(1, 1, figura);			
			assertThat("No se puede colocar una tercera pieza de esa figura y color",
					partida.esJugadaLegalEnTurnoActual(2, 2, figura), is(false));
		}

	}

	// Comprobar colocando en cuatro esquinas...

	/**
	 * Comprobando jugadas ilegales colocando en esquinas.
	 *
	 */
	@Nested
	@DisplayName("Jugadas ilegales colocando pieza en las esquinas.")
	class JugadasIlegalesColocandoPiezaEnCuatroEsquinas {

		/**
		 * Comprueba que una vez colocada una figura en la esquina superior izquierda no
		 * se pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas ilegales colocando en la esquina superior izquierda.")
		void comprobarIlegalidadJugadaConFiguraEnEsquinaSuperiorIzquierda(Figura figura) {
			partida.colocarPiezaEnTurnoActual(0, 0, figura);
			partida.cambiarTurno();
			assertAll("ilegalidad en celdas cerradas por la figura colocada en la esquina superior izquierda",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 1, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 2, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 1, figura), is(false)));
		}

		/**
		 * Comprueba que una vez colocada una figura en la esquina superior derecha no
		 * se pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas ilegales colocando en la esquina superior derecha.")
		void comprobarIlegalidadJugadaConFiguraEnEsquinaSuperiorDerecha(Figura figura) {
			partida.colocarPiezaEnTurnoActual(0, 3, figura);
			partida.cambiarTurno();
			assertAll("ilegalidad en celdas cerradas por la figura colocada en la esquina superior derecha",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 1, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 2, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 2, figura), is(false)));
		}

		/**
		 * Comprueba que una vez colocada una figura en la esquina inferior izquierda no
		 * se pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas ilegales colocando en la esquina inferior izquierda.")
		void comprobarIlegalidadJugadaConFiguraEnEsquinaInferiorIzquierda(Figura figura) {
			partida.colocarPiezaEnTurnoActual(3, 0, figura);
			partida.cambiarTurno();
			assertAll("ilegalidad en celdas cerradas por la figura colocada en la esquina inferior izquierda",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 1, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 2, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 1, figura), is(false)));
		}

		/**
		 * Comprueba que una vez colocada una figura en la esquina inferior derecha no
		 * se pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas ilegales colocando en la esquina inferior derecha.")
		void comprobarIlegalidadJugadaConFiguraEnEsquinaInferiorDerecha(Figura figura) {
			partida.colocarPiezaEnTurnoActual(3, 3, figura);
			partida.cambiarTurno();
			assertAll("ilegalidad en celdas cerradas por la figura colocada en la esquina inferior derecha",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 0, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 1, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 2, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 3, figura), is(false)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 2, figura), is(false)));
		}
	}

	/**
	 * Comprobando jugadas legales colocando en esquinas.
	 */
	@Nested
	@DisplayName("Jugadas legales colocando pieza en las esquinas.")
	class JugadasLegalesColocandoPiezaEnCuatroEsquinas {
		// Comprobar legalidad en el resto tras colocar esquinas

		/**
		 * Comprueba que una vez colocada una figura en la esquina superior izquierda se
		 * pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos NO compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas legales colocando en la esquina superior izquierda.")
		void comprobarLegalidadJugadaConFiguraEnEsquinaSuperiorIzquierda(Figura figura) {
			partida.colocarPiezaEnTurnoActual(0, 0, figura);
			partida.cambiarTurno();
			assertAll("legalidad en celdas abiertas por la figura colocada en la esquina superior izquierda",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 3, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 3, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 3, figura), is(true)));
		}

		/**
		 * Comprueba que una vez colocada una figura en la esquina superior derecha se
		 * pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos NO compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		void comprobarLegalidadJugadaConFiguraEnEsquinaSuperiorDerecha(Figura figura) {
			partida.colocarPiezaEnTurnoActual(0, 3, figura);
			partida.cambiarTurno();
			assertAll("legalidad en celdas abiertas por la figura colocada en la esquina superior derecha",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 0, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 0, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 0, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(3, 2, figura), is(true)));
		}

		/**
		 * Comprueba que una vez colocada una figura en la esquina inferior izquierda se
		 * pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos NO compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas legales colocando en la esquina inferior izquierda.")
		void comprobarLegalidadJugadaConFiguraEnEsquinaInferiorIzquierda(Figura figura) {
			partida.colocarPiezaEnTurnoActual(3, 0, figura);
			partida.cambiarTurno();
			assertAll("legalidad en celdas abiertas por la figura colocada en la esquina inferior izquierda",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 3, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 3, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 3, figura), is(true)));
		}

		/**
		 * Comprueba que una vez colocada una figura en la esquina inferior derecha se
		 * pueden colocar piezas con la misma figura y color contrario en celdas de
		 * grupos NO compartidos.
		 * 
		 * @param figura figura
		 */
		@ParameterizedTest
		@EnumSource(Figura.class)
		@DisplayName("Jugadas legales colocando en la esquina inferior derecha.")
		void comprobarLegalidadJugadaConFiguraEnEsquinaInferiorDerecha(Figura figura) {
			partida.colocarPiezaEnTurnoActual(3, 3, figura);
			partida.cambiarTurno();
			assertAll("legalidad en celdas abiertas por la figura colocada en la esquina inferior derecha",
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 0, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(0, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 0, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 1, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(1, 2, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 0, figura), is(true)),
					() -> assertThat(partida.esJugadaLegalEnTurnoActual(2, 1, figura), is(true)));
		}
	}

	/**
	 * Comprobando partidas completas con victoria tras algunas jugadas.
	 *
	 */
	@Nested
	@DisplayName("Partidas completas con victoria tras varias jugadas.")
	class PartidasComplejasConVictoria {
		// Partidas...

		/**
		 * Comprueba victoria en 5 jugadas.
		 */
		@Test
		@DisplayName("Victoria rápida de negras en 4 jugadas")
		void partidaConVictoriaRapidaEn4Jugadas() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CONO); // 01CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 2, Figura.CUBO); // 02CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 3, Figura.ESFERA); // 03ESN con victoria de negras
			// then
			assertAll("estado finalizado corercto con cuatro jugadas",
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(4)),
					() -> assertThat(
							"Piezas restantes en caja blanca incorrectas (hemos consumido un cilindro y un cubo).",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.BLANCO),
									new Pieza(Figura.CONO, Color.BLANCO), new Pieza(Figura.CONO, Color.BLANCO),
									new Pieza(Figura.CUBO, Color.BLANCO), new Pieza(Figura.ESFERA, Color.BLANCO),
									new Pieza(Figura.ESFERA, Color.BLANCO))),
					() -> assertThat(
							"Piezas restantes en caja negra incorrectas (hemos consumido un cono y una esfera).",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO),
									new Pieza(Figura.CILINDRO, Color.NEGRO), new Pieza(Figura.CUBO, Color.NEGRO),
									new Pieza(Figura.CUBO, Color.NEGRO), new Pieza(Figura.CONO, Color.NEGRO),
									new Pieza(Figura.ESFERA, Color.NEGRO))),
					() -> assertThat("Ganador incorrecto.", partida.consultarGanador(), is(Color.NEGRO)),
					() -> assertThat("Turno no debería avanzar en victoria.", partida.consultarTurno(),
							is(Color.NEGRO)),
					() -> assertThat("La partida debería estar finalizada por tener un grupo completo.",
							partida.hayAlgunGrupoCompleto(), is(true)),
					() -> assertThat("La partida no debería estar finalizada por bloqueo del turno actual.",
							partida.estaBloqueadoTurnoActual(), is(false)),
					() -> assertThat("La partida debería estar finalizada.", partida.estaAcabadaPartida(), is(true)));

		}

		/**
		 * Comprueba victoria en 6 jugadas.
		 */
		@Test
		@DisplayName("Victoria rápida de negras en 6 jugadas")
		void partidaConVictoriaRapidaEn6Jugadas() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 3, Figura.CONO); // 03CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 1, Figura.ESFERA); // 31ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.CUBO); // 12CBN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 2, Figura.ESFERA); // 02ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 3, Figura.CILINDRO); // 13CLN con victoria de negras

			// En caja blancas quedan un cilindro, dos conos y dos cubos

			// En caja negras quedan un cilindro, un cono, un cubo y dos esferas

			// Estado del tablero al finalizar la partida...
			/* @formatter:off
			     * 
			     *   	  0 	 1 	      2 	  3
				 * 0	-CLB-	-----	-ESB-	-CNN-	
				 * 1	-----	-----	-CBN-	-CLN-	
				 * 2	-----	-----	-----	-----	
				 * 3	-----	-ESB-	-----	-----	
			     *   	
			     * @formatter:on
			    */

			// then
			assertAll("estado finalizado correcto con 6 jugadas",
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(6)),
					() -> assertThat(
							"Piezas restantes en caja blanca incorrectas (hemos consumido un cilindro y dos esferas).",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.BLANCO),
									new Pieza(Figura.CONO, Color.BLANCO), new Pieza(Figura.CONO, Color.BLANCO),
									new Pieza(Figura.CUBO, Color.BLANCO), new Pieza(Figura.CUBO, Color.BLANCO))),
					() -> assertThat(
							"Piezas restantes en caja negra incorrectas (hemos consumido un cilindro, un cono y un cubo).",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO),
									new Pieza(Figura.CONO, Color.NEGRO), new Pieza(Figura.CUBO, Color.NEGRO),
									new Pieza(Figura.ESFERA, Color.NEGRO), new Pieza(Figura.ESFERA, Color.NEGRO))),
					() -> assertThat("Ganador incorrecto.", partida.consultarGanador(), is(Color.NEGRO)),
					() -> assertThat("Turno no debería avanzar en victoria.", partida.consultarTurno(),
							is(Color.NEGRO)),

					() -> assertThat("La partida debería estar finalizada por tener un grupo completo.",
							partida.hayAlgunGrupoCompleto(), is(true)),
					() -> assertThat("La partida no debería estar finalizada por bloqueo del turno actual.",
							partida.estaBloqueadoTurnoActual(), is(false)),
					() -> assertThat("La partida debería estar finalizada.", partida.estaAcabadaPartida(), is(true)));

		}

		/**
		 * Comprueba victoria en 7 jugadas.
		 */
		@Test
		@DisplayName("Victoria rápida de negras en 7 jugadas")
		void partidaConVictoriaRapidaEn7Jugadas() {
			// when
			partida.colocarPiezaEnTurnoActual(1, 0, Figura.CONO); // 10CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CILINDRO); // 01CLN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 1, Figura.CUBO); // 21CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 2, Figura.ESFERA); // 32ESN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 0, Figura.CILINDRO); // 30CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 1, Figura.CONO); // 31CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 1, Figura.ESFERA); // 11ESB con victoria de blancas

			// En caja blancas quedan un cilindro, un cono, un cubo y una esfera

			// En caja negras quedan un cilindro, un cono, dos cubos y una esfera

			// Estado del tablero al finalizar la partida...
			/* @formatter:off
			     * 
			     *   	  0 	 1 	      2 	  3
				 * 0	-----	-CLN-	-----	-----	
				 * 1	-CNB-	-ESB-	-----	-----	
				 * 2	-----	-CBB-	-----	-----	
				 * 3	-CLB-	-CNN-	-ESN-	-----	
			     *   	
			     * @formatter:on
			     */

			// then
			assertAll("estado finalizado correcto con 7 jugadas",
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(7)),
					() -> assertThat(
							"Piezas restantes en caja blanca incorrectas (hemos consumido un cilindro, un cubo, un cono y una esfera).",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.BLANCO),
									new Pieza(Figura.CONO, Color.BLANCO), new Pieza(Figura.CUBO, Color.BLANCO),
									new Pieza(Figura.ESFERA, Color.BLANCO))),
					() -> assertThat(
							"Piezas restantes en caja negra incorrectas (hemos consumido un cilindro, un cono y una esfera).",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO),
									new Pieza(Figura.CONO, Color.NEGRO), new Pieza(Figura.CUBO, Color.NEGRO),
									new Pieza(Figura.CUBO, Color.NEGRO), new Pieza(Figura.ESFERA, Color.NEGRO))),
					() -> assertThat("Turno no debería avanzar en victoria.", partida.consultarTurno(),
							is(Color.BLANCO)),
					() -> assertThat("Ganador incorrecto.", partida.consultarGanador(), is(Color.BLANCO)),
					() -> assertThat("La partida debería estar finalizada por tener un grupo completo.",
							partida.hayAlgunGrupoCompleto(), is(true)),
					() -> assertThat("La partida no debería estar finalizada por bloqueo del turno actual.",
							partida.estaBloqueadoTurnoActual(), is(false)),
					() -> assertThat("La partida debería estar finalizada.", partida.estaAcabadaPartida(), is(true)));

		}

		// Partidas...

		/**
		 * Comprueba victoria en 10 jugadas.
		 * 
		 * @version 1.1 20221109
		 */
		@Test
		@DisplayName("Victoria de negras en 10 jugadas")
		void partidaConVictoriaLentaEn10Jugadas() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.CILINDRO); // 12CLN (Corregido comentario)
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 1, Figura.CUBO); // 11CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 3, Figura.CONO); // 03CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 1, Figura.ESFERA); // 31ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 2, Figura.ESFERA); // 02ESN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 3, Figura.CILINDRO); // 33CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 0, Figura.CUBO); // 20CBN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 0, Figura.CONO); // 10CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 3, Figura.ESFERA); // 13ESN victoria de negras (Corregido comentario)

			// En caja blancas quedan cono, cubo y esfera

			// En caja negras quedan cilindro, cono y cubo

			// Estado del tablero al finalizar la partida...
			/* @formatter:off
			     * 
			     *   	  0 	 1 	      2 	  3
			     * 0	-CLB-	-----	-ESN-	-CNN-
			     * 1	-CNB-	-CBB-	-CLN-	-ESN-
			     * 2	-CBN-	-----	-----	-----
			     * 3	-----	-ESB-	-----	-CLB-
			     *   	
			     * @formatter:on
			    */

			// then
			assertAll("estado finalizado en 10 jugadas",
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(10)),
					() -> assertThat("Piezas restantes en caja blanca incorrectas.",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CONO, Color.BLANCO),
									new Pieza(Figura.CUBO, Color.BLANCO), new Pieza(Figura.ESFERA, Color.BLANCO))),
					() -> assertThat("Piezas restantes en caja negra incorrectas.",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO),
									new Pieza(Figura.CONO, Color.NEGRO), new Pieza(Figura.CUBO, Color.NEGRO))),
					() -> assertThat("Turno no debería avanzar en victoria.", partida.consultarTurno(),
							is(Color.NEGRO)),
					() -> assertThat("Ganador incorrecto.", partida.consultarGanador(), is(Color.NEGRO)),
					() -> assertThat("La partida debería estar finalizada por tener un grupo completo.",
							partida.hayAlgunGrupoCompleto(), is(true)),
					() -> assertThat("La partida no debería estar finalizada por bloqueo del turno actual.",
							partida.estaBloqueadoTurnoActual(), is(false)),
					() -> assertThat("La partida debería estar finalizada.", partida.estaAcabadaPartida(), is(true)));

		}

		/**
		 * Comprueba victoria en 12 jugadas.
		 */
		@Test
		@DisplayName("Victoria lenta de negras en 12 jugadas")
		void partidaConVictoriaLentaEn12Jugadas() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 1, Figura.CONO); // 11CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 2, Figura.CUBO); // 02CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 0, Figura.CUBO); // 20CBN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 1, Figura.ESFERA); // 31ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.ESFERA); // 12ESN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 3, Figura.CILINDRO); // 23CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 3, Figura.CUBO); // 33CBN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 0, Figura.CONO); // 30CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CONO); // 01CNN
			partida.cambiarTurno();
			// OJO Bug en versión previa, no coincidía el comentario con los datos reales
			// colocando en una celda que había ya una pieza...
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CONO); // 22CNB // OJO Bug en versión previa
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 0, Figura.ESFERA); // 10ESN victoria de negras

			// En caja blancas quedan cubo y esfera

			// En caja negras quedan cilindro y cilindro (corregido el comentario)

			// Estado del tablero al finalizar la partida...
			/* @formatter:off
			     * 
			     *   	  0 	 1 	      2 	  3
			     *   0	-CLB-	-CNN-	-CBB-	-----
			     *   1	-ESN-	-CNN-	-ESN-	-----
			     *   2	-CBN-	-----	-CNB-	-CLB-
			     *   3	-CNB-	-ESB-	-----	-CBN-	
			     *   	
			     * @formatter:on
			     */

			// then
			assertAll("estado finalizado en 12 jugadas",
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(12)),
					() -> assertThat("Piezas restantes en caja blanca incorrectas.",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CUBO, Color.BLANCO),
									new Pieza(Figura.ESFERA, Color.BLANCO))),
					() -> assertThat("Piezas restantes en caja negra incorrectas.",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO),
									new Pieza(Figura.CILINDRO, Color.NEGRO))),
					() -> assertThat("Turno no debería avanzar en victoria.", partida.consultarTurno(),
							is(Color.NEGRO)),
					() -> assertThat("Ganador incorrecto.", partida.consultarGanador(), is(Color.NEGRO)),
					() -> assertThat("La partida debería estar finalizada por tener un grupo completo.",
							partida.hayAlgunGrupoCompleto(), is(true)),
					() -> assertThat("El jugador con turno actual está bloqueado (al no avanzar turno en victoria).",
							partida.estaBloqueadoTurnoActual(), is(true)),
					() -> assertThat("La partida debería estar finalizada.", partida.estaAcabadaPartida(), is(true)));

		}

		/**
		 * Comprueba victoria en 13 jugadas.
		 */
		// Partidas...
		@Test
		@DisplayName("Victoria lenta de blancas en 13 jugadas")
		void partidaConVictoriaLentaEn13Jugadas() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.CILINDRO); // 12CLN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 1, Figura.ESFERA); // 31ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 0, Figura.CONO); // 20CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CONO); // 01CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CUBO); // 22CBN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 3, Figura.CUBO); // 03CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 3, Figura.CONO); // 33CNN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 2, Figura.ESFERA); // 32ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 0, Figura.CUBO); // 30CBN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 1, Figura.CONO); // 11CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 3, Figura.ESFERA); // 13ESN
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 1, Figura.CILINDRO); // 21CLB victoria de blancas

			// En caja blancas queda cubo

			// En caja negras quedan cilindro y esfera

			// Estado del tablero al finalizar la partida...
			/* @formatter:off
				 * 
				 *   	  0 	 1 	      2 	  3
				 * 0	-CLB-	-CNB-	-----	-CBB-	
				 * 1	-----	-CNB-	-CLN-	-ESN-
				 * 2	-CNN-	-CLB-	-CBN-	-----
				 * 3	-CBN-	-ESB-	-ESB-	-CNN-		
				 *   	
				 * @formatter:on
				 */

			// then
			assertAll("estado finalizado en 13 jugadas",
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(13)),
					() -> assertThat("Piezas restantes en caja blanca incorrectas.",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CUBO, Color.BLANCO))),
					() -> assertThat("Piezas restantes en caja negra incorrectas.",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO),
									new Pieza(Figura.ESFERA, Color.NEGRO))),
					() -> assertThat("Ganador incorrecto.", partida.consultarGanador(), is(Color.BLANCO)),

					() -> assertThat("Turno no debería avanzar en victoria.", partida.consultarTurno(),
							is(Color.BLANCO)),
					() -> assertThat("La partida debería estar finalizada por tener un grupo completo.",
							partida.hayAlgunGrupoCompleto(), is(true)),
					() -> assertThat("El jugador con turno actual está bloqueado (al no avanzar turno en victoria).",
							partida.estaBloqueadoTurnoActual(), is(true)),
					() -> assertThat("La partida debería estar finalizada.", partida.estaAcabadaPartida(), is(true)));

		}
	}

	/**
	 * Comprobando el rellando del tablero dando situaciones de bloqueo.
	 *
	 */
	@Nested
	@DisplayName("Partidas rellenando por completo el tablero con piezas sin grupo ganador.")
	class PartidaRellenandoTablero {

		// Partidas...
		/**
		 * Comprueba que al rellenar el tablero se da como ganador al jugador que inició
		 * la partida.
		 */
		@Test
		@DisplayName("Comprobar que al rellenar el tablero se da como ganador al jugador que inició la partida.")
		void partidaRellenadoTablero() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 3, Figura.CILINDRO); // 33CLN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(1, 0, Figura.CILINDRO); // 10CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 3, Figura.CILINDRO); // 23CLN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CONO); // 01CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 2, Figura.CONO); // 32CNN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(1, 1, Figura.CONO); // 11CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CONO); // 22CNN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(2, 0, Figura.ESFERA); // 20ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 3, Figura.ESFERA); // 13ESN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(2, 1, Figura.ESFERA); // 21ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.ESFERA); // 12ESN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 0, Figura.CUBO); // 30CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 3, Figura.CUBO); // 03CBN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 1, Figura.CUBO); // 31CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 2, Figura.CUBO); // 02CBB tablero completo sin piezas disponibles
																	// en ninguna caja ni ganador
			partida.cambiarTurno();

			// En caja blancas vacía

			// En caja negras vacía

			// Estado del tablero al finalizar la partida, con tablero completo y sin
			// ganador...
			/* @formatter:off
				 * 
				 *   	  0 	 1 	      2 	  3
				 * 0	-CLB-	-CNB-	-CBN-	-CBN-	
				 * 1	-CLB-	-CNB-	-ESN-	-ESN-	
				 * 2	-ESB-	-ESB-	-CNN-	-CLN-	
				 * 3	-CBB-	-CBB-	-CNN-	-CLN-	
	 			 *
				 * @formatter:on
				 */

			// then
			assertAll("estado finalizado rellenando tablero",
					() -> assertThat("Caja blanca debería estar vacía.",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()), is(empty())),
					() -> assertThat("Caja negra debería estar vacía.",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()), is(empty())),
					() -> assertThat(
							"Ganador rellenando el tablero sin grupo ganador, se dará como ganador al último turno, al no poder mover el jugador actual.",
							partida.consultarGanador(), is(Color.NEGRO)),
					() -> assertThat("El turno actual debería estar bloqueado en sus jugadas.",
							partida.estaBloqueadoTurnoActual(), is(true)),
					() -> assertThat("No debería haber grupo ganador completado en el tablero.",
							partida.hayAlgunGrupoCompleto(), is(false)),
					() -> assertThat("La partida debería estar finalizada puesto que no hay jugadas legales posibles.",
							partida.estaAcabadaPartida(), is(true)),
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(16)));

		}
	}

	/**
	 * Bloqueando posibles jugadas del jugador contrario.
	 *
	 */
	@Nested
	@DisplayName("Partidas donde se bloquea jugadas del jugador contrario")
	class PartidaBloqueandoJugadasDelContrario {

		// Partidas...
		/**
		 * Comprueba bloqueo con 14 jugadas.
		 */
		@Test
		@DisplayName("Comprobar que el jugador con blancas no puede mover cono en su penúltima jugada.")
		void partidaBloqueandoABlancas() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 3, Figura.CILINDRO); // 33CLN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(0, 3, Figura.CONO); // 03CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 0, Figura.CONO); // 10CNN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(0, 2, Figura.ESFERA); // 02ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.CUBO); // 12CBN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 0, Figura.CUBO); // 30CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CUBO); // 22CBN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 1, Figura.CUBO); // 31CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 0, Figura.CONO); // 20CNN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CILINDRO); // 01CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 1, Figura.ESFERA); // 11ESN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 2, Figura.ESFERA); // 32ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 1, Figura.ESFERA); // 21ESN sin posibilidad de colocar cono blanco
																	// en celdas libres...
			partida.cambiarTurno();

			// En caja blancas queda un cono

			// En caja negras queda un cilindro

			// Estado del tablero al finalizar la partida, sin posibilidad de colocar cono
			// blanco...
			/* @formatter:off
				 * 
				 *   	  0 	 1 	      2 	  3
			     * 0	-CLB-	-CLB-	-ESB-	-CNB-	
				 * 1	-CNN-	-ESN-	-CBN-	-----	
				 * 2	-CNN-	-ESN-	-CBN-	-----	
				 * 3	-CBB-	-CBB-	-ESB-	-CLN-
	 			 *
				 * @formatter:on
				 */

			// then
			assertAll("estado finalizado en bloqueo a jugador con blancas",
					() -> assertThat("Caja blanca solo debería tener un cono.",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CONO, Color.BLANCO))),
					() -> assertThat("Caja negra solo debería tener un cilindro.",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.CILINDRO, Color.NEGRO))),
					() -> assertThat(
							"Se debería considera ganador al jugador que no está bloqueado en el turno actual.",
							partida.consultarGanador(), is(Color.NEGRO)),
					() -> assertThat("El turno actual debería estar bloqueado en sus jugadas.",
							partida.estaBloqueadoTurnoActual(), is(true)),
					() -> assertThat("No debería haber grupo ganador completado en el tablero.",
							partida.hayAlgunGrupoCompleto(), is(false)),
					() -> assertThat("La partida debería estar finalizada puesto que no hay jugadas legales posibles.",
							partida.estaAcabadaPartida(), is(true)),
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(14)));

		}

		/**
		 * Comprueba bloqueo con 15 jugadas.
		 */
		@Test
		@DisplayName("Comprobar que el jugador con negras no puede mover esfera en última jugada.")
		void partidaBloqueandoANegras() {
			// when
			partida.colocarPiezaEnTurnoActual(0, 0, Figura.CILINDRO); // 00CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 2, Figura.CILINDRO); // 22CLN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(0, 1, Figura.CILINDRO); // 01CLB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(3, 2, Figura.CILINDRO); // 32CLN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(0, 3, Figura.CONO); // 03CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 0, Figura.CONO); // 10CNN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 0, Figura.CUBO); // 30CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 2, Figura.CUBO); // 12CBN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 1, Figura.CUBO); // 31CBB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(1, 3, Figura.CUBO); // 13CBN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(3, 3, Figura.CONO); // 33CNB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(2, 0, Figura.CONO); // 20CNN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(1, 1, Figura.ESFERA); // 11ESB
			partida.cambiarTurno();
			partida.colocarPiezaEnTurnoActual(0, 2, Figura.ESFERA); // 02ESN
			partida.cambiarTurno();

			partida.colocarPiezaEnTurnoActual(2, 3, Figura.ESFERA); // 23ESNB
			partida.cambiarTurno(); // bloqueado el jugador con turno negras, no puede colocar su cono en la única
									// celda libre

			// En caja blancas está vacía

			// En caja negras queda una esfera

			// Estado del tablero al finalizar la partida, sin posibilidad de colocar cono
			// blanco...
			/* @formatter:off
				 * 
				 *   	  0 	 1 	      2 	  3
				 * 0	-CLB-	-CLB-	-ESN-	-CNB-	
				 * 1	-CNN-	-ESB-	-CBN-	-CBN-	
				 * 2	-CNN-	-----	-CLN-	-ESB-	
				 * 3	-CBB-	-CBB-	-CLN-	-CNB-
	 			 *
				 * @formatter:on
				 */

			// then
			assertAll("estado finalizado en bloqueo a jugador con negras",
					() -> assertThat("Caja blanca debería estar vacía.",
							Arrays.asList(cajaBlancas.consultarPiezasDisponibles()), is(empty())),
					() -> assertThat("Caja negra solo debería tener una esfera",
							Arrays.asList(cajaNegras.consultarPiezasDisponibles()),
							containsInAnyOrder(new Pieza(Figura.ESFERA, Color.NEGRO))),
					() -> assertThat(
							"Se debería considera ganador al jugador que no está bloqueado en el turno actual.",
							partida.consultarGanador(), is(Color.BLANCO)),
					() -> assertThat("El turno actual debería estar bloqueado en sus jugadas.",
							partida.estaBloqueadoTurnoActual(), is(true)),
					() -> assertThat("No debería haber grupo ganador completado en el tablero.",
							partida.hayAlgunGrupoCompleto(), is(false)),
					() -> assertThat("La partida debería estar finalizada puesto que no hay jugadas legales posibles.",
							partida.estaAcabadaPartida(), is(true)),
					() -> assertThat("El número de jugadas es incorrecto.", partida.consultarNumeroJugada(), is(15)));

		}

	}
}
