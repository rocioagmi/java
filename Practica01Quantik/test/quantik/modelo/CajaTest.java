package quantik.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests sobre la caja.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests sobre Caja (depende de las implementaciones reales de Pieza, Figura y Color")
@Tag("IntegrationTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class CajaTest {

	/** Caja. */
	private Caja caja;

	/** 
	 * Inicialización.
	 *  
	 * Se toma como valor por defecto el color blanco.
	 */
	@BeforeEach
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void inicializar() {
		caja = new Caja(Color.BLANCO);
	}

	/**
	 * Comprueba la inicialización correcta de la caja con sus 8 piezas.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar inicializaciones.")
	void comprobarInicializacion(Color color) {
		Caja cajaLocal = new Caja(color);
		assertAll("comprobar estado inicial de la caja con color y número de piezas correctas",
				() -> assertThat("Color mal inicializado en la caja.", cajaLocal.consultarColor(), is(color)),
				() -> assertThat("Incorrecto número de piezas iniciales en la caja.",
						cajaLocal.consultarPiezasDisponibles().length, is(8)));
	}

	/**
	 * Comprueba que hay piezas disponibles inicialmente de los cuatro tipos de figura.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar piezas disponibles en la caja inicialmente.")
	void comprobarPiezasDisponiblesDeCualquierFiguraInicialmente(Color color) {
		Caja cajaLocal = new Caja(color);
		assertAll("comprobar que las cuatro figuras están disponibles en la caja inicialmente",
				() -> assertThat("Figura no existente inicialmente en piezas de la caja.",
						cajaLocal.estaDisponible(Figura.CILINDRO), is(true)),
				() -> assertThat("Figura no existente inicialmente en piezas de la caja.",
						cajaLocal.estaDisponible(Figura.CONO), is(true)),
				() -> assertThat("Figura no existente inicialmente en piezas de la caja.",
						cajaLocal.estaDisponible(Figura.CUBO), is(true)),
				() -> assertThat("Figura no existente inicialmente en piezas de la caja.",
						cajaLocal.estaDisponible(Figura.ESFERA), is(true)));

	}

	/**
	 * Comprueba que están todas las piezas inicialmente.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar que están todas las piezas inicialmente.")
	void comprobarQueEstanTodasLasPiezasInicialmente(Color color) {
		Caja cajaLocal = new Caja(color);
		Pieza[] piezas = cajaLocal.consultarPiezasDisponibles();
		// Convertimos el array de piezas a una lista...
		List<Pieza> lista = Arrays.asList(piezas);

		// Comprobamos tamaño y que están todas
		assertAll("comprobar que todas las piezas están inicialmente y en el orden esperado",
				() -> assertThat("Tamaño correcto en número de piezas disponibles.", lista.size(), is(8)),
				() -> assertThat("No están todas las piezas en la caja.", lista,
						containsInAnyOrder(new Pieza(Figura.CILINDRO, color), new Pieza(Figura.CILINDRO, color),
								new Pieza(Figura.CONO, color), new Pieza(Figura.CONO, color),
								new Pieza(Figura.CUBO, color), new Pieza(Figura.CUBO, color),
								new Pieza(Figura.ESFERA, color), new Pieza(Figura.ESFERA, color))));

	}

	/**
	 * Comprueba que al retirar una pieza la caja cambia correctamente.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerFiguraYColor")
	@DisplayName("Comprobar que al retirar una pieza la caja cambia correctamente.")
	void comprobarQueAlRetirarUnaFiguraSeDecrementaElNumeroDePiezas(Figura figura, Color color) {
		Caja cajaLocal = new Caja(color);
		Pieza piezaRetirada = cajaLocal.retirar(figura);
		assertAll("comprobar que retira bien la figura",
			() -> assertThat("Figura incorrecta en la pieza retirada.", piezaRetirada.consultarFigura(), is(figura)),
			() -> assertThat("Color incorrecto en la pieza retirada.", piezaRetirada.consultarColor(), is(color)),
			() -> assertThat("Número incorrecto de piezas en una caja tras retirar una.",
				cajaLocal.consultarPiezasDisponibles().length, is(7)));
	}

	/**
	 * Comprueba que al retirar dos piezas de la misma figura la caja cambia correctamente.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerFiguraYColor")
	@DisplayName("Comprobar que al retirar dos piezas la caja cambia correctamente.")
	void comprobarQueAlRetirarDosVecesLaMismaFiguraCompruebaBienQueNoEstaDisponible(Figura figura, Color color) {
		Caja cajaLocal = new Caja(color);
		Pieza pieza1 = cajaLocal.retirar(figura);
		assertAll("comprobar que es correcto el estado al retirar una pieza",
				() -> assertThat("Figura incorrecta en la pieza retirada.", pieza1.consultarFigura(), is(figura)),
				() -> assertThat("Color incorrecto en la pieza retirada.", pieza1.consultarColor(), is(color)),
				() -> assertThat("Figura no disponible cuando solo hemos retirado una de la caja.",
						cajaLocal.estaDisponible(figura), is(true)),
				() -> assertThat("Tamaño incorrecto.", cajaLocal.contarPiezasActuales(), is(7)));
		Pieza pieza2 = cajaLocal.retirar(figura);
		assertAll("comprobar que es correcto el estado al retirar dos veces la misma figura",
				() -> assertThat("Figura incorrecta en la pieza retirada.", pieza2.consultarFigura(), is(figura)),
				() -> assertThat("Color incorrecto en la pieza retirada.", pieza2.consultarColor(), is(color)),
				() -> assertThat("Figura existente cuando hemos retirado dos de la caja.", cajaLocal.estaDisponible(figura),
						is(false)),
				() -> assertThat("Tamaño incorrecto.", cajaLocal.contarPiezasActuales(), is(6)));
	}

	/**
	 * Comprueba que al retirar todas las piezas la caja se queda vacía.
	 */
	@Test
	@DisplayName("Comprobar que al retirar todas las piezas la caja se queda vacía.")
	void comprobarQueRetiraTodasLasPiezasSolicitadas() {
		// eliminamos dos veces cada figura de la caja..
		for (Figura figura : Figura.values()) {
			caja.retirar(figura);
			caja.retirar(figura);
		}
		// comprobamos...
		assertAll("comprobando que es correcto el estado tras retirar dos veces cada una de las figuras en la enumeración",
				() -> assertThat("Figuras disponibles cuando no deberían estarlo.",
						caja.consultarPiezasDisponibles().length, is(0)),
				() -> assertThat("Tamaño incorrecto.", caja.contarPiezasActuales(), is(0)));
	}
	
	/**
	 * Comprueba que al retirar una pieza que no está devuelve nulo.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerFiguraYColor")
	@DisplayName("Comprobar que al intentar retirar una pieza que no está devuelve nulo.")
	void comprobarQueIntentarRetirarUnaPiezaQueNoEstaDevuelveNulo(Figura figura, Color color) {
		// eliminamos dos veces cada figura de la caja..
		Caja cajaLocal = new Caja(color);
		cajaLocal.retirar(figura);
		cajaLocal.retirar(figura);
		// intentamos retirar por tercera vez...
		Pieza pieza = cajaLocal.retirar(figura);
		// comprobamos...
		assertThat("Debería devolver un null al no poder retirar la pieza con la figura y color solicitado.", pieza, is(nullValue()));
	}
	
	/**
	 * Comprueba que la clonación de una caja recién inicializada es correcta.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar la clonación de una caja recién inicializada.")
	void comprobarClonacionConCajaInicialmenteConTodasLasPiezas(Color color) {
		Caja cajaLocal = new Caja(color);
		Caja cajaClon = cajaLocal.clonar();
		Pieza[] disponiblesEnCajaLocal = cajaLocal.consultarPiezasDisponibles();
		Pieza[] disponiblesEnCajaClon = cajaClon.consultarPiezasDisponibles();
		assertAll("comprobando clonación de caja completa",
			() -> assertNotSame(cajaLocal, cajaClon, "No deberían tener la misma referencia el original y el clon"),
			() -> assertThat("El contenido de las cajas debería ser equivalente", cajaLocal, is(cajaClon)),
			() -> assertThat("El número de piezas disponibles en el clon es incorrecto.", disponiblesEnCajaClon.length,is(8)),
			() -> assertNotSame(disponiblesEnCajaLocal, disponiblesEnCajaClon, "No deberían tener la misma referencia."),
			() -> assertArrayEquals(disponiblesEnCajaLocal, disponiblesEnCajaClon,"Los arrays de piezas disponibles no son iguales en contenido.")
			);
		// comprobando adicionalmente la clonación en profundidad de las piezas
		for(int i = 0; i < disponiblesEnCajaLocal.length; i++) {
			assertNotSame(disponiblesEnCajaLocal[i], disponiblesEnCajaClon[i], "No se han clonado en profundidad las piezas disponibles en la caja.");
		}
	}
	
	/**
	 * Comprueba que la clonación de una caja con la mitad de las piezas es correcta.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar la clonación de una caja con la mitad de las piezas.")
	void comprobarClonacionConCajaInicialmenteConLaMitadDeLasPiezas(Color color) {
		// given
		Caja cajaLocal = new Caja(color);
		cajaLocal.retirar(Figura.CILINDRO); // quitamos una pieza de cada figura
		cajaLocal.retirar(Figura.CONO);
		cajaLocal.retirar(Figura.CUBO);
		cajaLocal.retirar(Figura.ESFERA);
		// when
		Caja cajaClon = cajaLocal.clonar();
		// then
		Pieza[] disponiblesEnCajaLocal = cajaLocal.consultarPiezasDisponibles();
		Pieza[] disponiblesEnCajaClon = cajaClon.consultarPiezasDisponibles();
		assertAll("comprobando clonación de caja a la mitad",
			() -> assertNotSame(cajaLocal, cajaClon, "No deberían tener la misma referencia el original y el clon"),
			() -> assertNotSame(disponiblesEnCajaLocal, disponiblesEnCajaClon, "No deberían tener la misma referencia."),
			() -> assertThat("El número de piezas disponibles en el clon es incorrecto.", disponiblesEnCajaClon.length,is(4)),
			() -> assertArrayEquals(disponiblesEnCajaLocal, disponiblesEnCajaClon,"Los arrays de piezas disponibles no son iguales en contenido.")
			);
		// comprobando adicionalmente la clonación en profundidad de las piezas
		for(int i = 0; i < disponiblesEnCajaLocal.length; i++) {
			assertNotSame(disponiblesEnCajaLocal[i], disponiblesEnCajaClon[i], "No se han clonado en profundidad las piezas disponibles en la caja.");
		}
	}
	
	/**
	 * Comprueba que la clonación de una caja vacía es correcta.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar la clonación de una caja vacía.")
	void comprobarClonacionConCajaVaciada(Color color) {
		// given
		Caja cajaLocal = new Caja(color);
		cajaLocal.retirar(Figura.CILINDRO); // quitamos todas las piezas de cada figura
		cajaLocal.retirar(Figura.CILINDRO); 
		cajaLocal.retirar(Figura.CONO);
		cajaLocal.retirar(Figura.CONO);
		cajaLocal.retirar(Figura.CUBO);
		cajaLocal.retirar(Figura.CUBO);
		cajaLocal.retirar(Figura.ESFERA);
		cajaLocal.retirar(Figura.ESFERA);
		// when
		Caja cajaClon = cajaLocal.clonar(); // las cajas estarán vacías
		// then
		Pieza[] disponiblesEnCajaLocal = cajaLocal.consultarPiezasDisponibles();
		Pieza[] disponiblesEnCajaClon = cajaClon.consultarPiezasDisponibles();
		assertAll("Comprobando clonación de caja vacía",
			() -> assertNotSame(cajaLocal, cajaClon, "No deberían tener la misma referencia el original y el clon"),
			() -> assertNotSame(disponiblesEnCajaLocal, disponiblesEnCajaClon, "No deberían tener la misma referencia."),
			() -> assertThat("El número de piezas disponibles en el clon es incorrecto.", disponiblesEnCajaClon.length,is(0)),
			() -> assertArrayEquals(disponiblesEnCajaLocal, disponiblesEnCajaClon,"Los arrays de piezas disponibles no son iguales en contenido.")
			);
	}

}
