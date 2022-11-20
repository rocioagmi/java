package quantik.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests sobre la celda.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests sobre Celda (depende en compilación de Pieza).")
@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class CeldaTest {

	/** Pieza. */
	@Mock
	Pieza pieza;

	/**
	 * Comprueba que la celda se inicializa vacía.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@DisplayName("Comprobar que la celda se inicializa correctamente vacía sin pieza.")
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	void probarInicializacionDeDatosEnCelda(int fila, int columna) {
		Celda celda = new Celda(fila, columna);		
		assertAll("probando consulta a los datos de una celda",
				() -> assertThat("La fila es incorrecta.", celda.consultarFila(), is(fila)),
				() -> assertThat("La columna es incorrecta.", celda.consultarColumna(), is(columna)),
				() -> assertThat("La celda debería estar vacía.", celda.estaVacia(), is(true)),
				() -> assertThat("No debería haber una pieza colocada.", celda.consultarPieza(), is(nullValue())));
	}

	/**
	 * Comprueba que coloca una pieza correctamente en la celda. 
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@DisplayName("Comprobar que una vez colocada una pieza el estado es correcto.")
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	void colocar(int fila, int columna) {
		// given
		Celda celda = new Celda(fila, columna);
		// when
		celda.colocar(pieza);
		when(pieza.clonar()).thenReturn(pieza); // no clonamos el mock object, devolvemos el mismo...
		// then
		assertAll("probando tras colocar pieza en la celda",
				() -> assertThat("La celda (" + fila + "/" + columna + " no debería estar vacía.", celda.estaVacia(),
						is(false)),
				() -> assertThat("La celda (" + fila + "/" + columna + " no contiene la pieza colocada.",
						celda.consultarPieza(), is(pieza)));
	}

	/**
	 * Comprueba que la clonación de una celda vacía es correcta. 
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que la clonación de una celda vacía es correcta.")
	void comprobarClonacionDeCeldasVacias(int fila, int columna) {
		// given
		Celda celda = new Celda(fila, columna);
		// when
		Celda clon = celda.clonar();
		// then
		assertAll("",
				() -> assertNotSame(celda, clon,
						"Ambas referencias apuntan al mismo objeto, no se ha clonado correctamente"),
				() -> assertThat("Los contenidos son iguales superficialmente.", clon, is(celda)),
				() -> assertThat("La fila del clon es incorrecta.", clon.consultarFila(), is(celda.consultarFila())),
				() -> assertThat("La columna del clon es incorrecta.", clon.consultarColumna(),
						is(celda.consultarColumna())),
				() -> assertThat("El clon debería estar vacío.", clon.estaVacia(), is(true)),
				() -> assertThat("No debería haber una pieza colocada en el clon.", clon.consultarPieza(),
						is(nullValue())));
	}

	/**
	 * Comprueba que la clonación de una celda con pieza es correcta.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que la clonación de una celda con pieza es correcta.")
	void comprobarClonacionDeCeldasConPieza(int fila, int columna) {
		// given
		Celda celda = new Celda(fila, columna);
		// when
		when(pieza.clonar()).thenReturn(pieza); // no clonamos el mock object, devolvemos el mismo...
		celda.colocar(pieza);
		Celda clon = celda.clonar();
		// then
		assertAll("",
				() -> assertNotSame(celda, clon,
						"Ambas referencias apuntan al mismo objeto, no se ha clonado correctamente"),
				() -> assertThat("Los contenidos son iguales superficialmente.", clon, is(celda)),
				() -> assertThat("La fila del clon es incorrecta.", clon.consultarFila(), is(celda.consultarFila())),
				() -> assertThat("La columna del clon es incorrecta.", clon.consultarColumna(),
						is(celda.consultarColumna())),
				() -> assertThat("El clon no debería estar vacío.", clon.estaVacia(), is(false)),
				() -> assertThat("Debería haber una pieza equivalente colocada en el clon.", clon.consultarPieza(),
						is(pieza)));
	}

	/**
	 * Comprueba que la comparación de igualdad de celdas vacías es correcta.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que la comparación con equals de una celda vacía es correcta.")
	void comprobarComparacionDeIgualdadDeCeldasVacias(int fila, int columna) {
		// given
		Celda celda1 = new Celda(fila, columna);
		Celda celda2 = new Celda(fila, columna);
		// then
		assertAll("comprobando comparación de igualdad de celdas vacías",
				() -> assertThat("La comparación de igualdad no se cumple.", celda1.equals(celda2), is(true))
			);
	}
	
	/**
	 * Comprueba que la comparación de igualdad de celdas con pieza es correcta.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que la comparación con equals de una celda con pieza es correcta.")
	void comprobarComparacionDeIgualdadDeCeldasConLaMismaPieza(int fila, int columna) {
		// given
		Celda celda1 = new Celda(fila, columna);
		Celda celda2 = new Celda(fila, columna);
		// when
		celda1.colocar(pieza);
		celda2.colocar(pieza);
		// then
		assertAll("comprobando comparación de igualdad de celdas con pieza",
				() -> assertThat("La comparación de igualdad no se cumple.", celda1.equals(celda2), is(true))
			);
	}
	
	/**
	 * Comprueba que en la clonación profunda de una celda se clona la pieza 
	 * correspondiente en profundidad.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@Tag("WhiteBox")
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que en la clonación profunda de la celda se invoca a la clonación profunda de la pieza.")
	void comprobarEnClonacionProfundaDeLaCeldaSeInvocaALaClonacionProfundaDeLaPieza(int fila, int columna) {
		// given
		Celda celda = new Celda(fila, columna);
		// when
		celda.colocar(pieza);
		celda.clonar();
		// then
		verify(pieza, times(1).description("Se debería invocar a la clonación profunda de la pieza al clonar en profundidad la celda.")).clonar();
	}
	
	/**
	 * Comprueba que el método de consulta de la pieza clona en profundidad.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 */
	@ParameterizedTest
	@Tag("WhiteBox")
	@MethodSource("quantik.modelo.Util#proveerCoordenadas")
	@DisplayName("Comprobar que la consulta de la pieza clona en profundidad la pieza.")
	void comprobarEnConsultaPiezaSeClonaEnProfundidad(int fila, int columna) {
		// given
		Celda celda = new Celda(fila, columna);
		// when
		celda.colocar(pieza);
		verify(pieza, times(0).description("No se debería invocar a la clonación profunda de la pieza al colocar.")).clonar();
		celda.consultarPieza();
		// then
		verify(pieza, times(1).description("Se debería invocar a la clonación profunda de la pieza al consultar la pieza en la celda.")).clonar();
	}


}
