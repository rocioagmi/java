package quantik.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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
 * Tests sobre el grupo. Depende de Celda por lo que se utilizarán
 * mocks (objetos "burlones").
 * 
 * Por simplicidad se supone que los tests de Pieza se pasan correctamente y no se
 * utilizan Mocks sobre Pieza.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220703
 * 
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests sobre Grupo (depende de la implementación real de Pieza y en compilación de Celda, Figura y Color).")
@Tag("IntegrationTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class GrupoTest {

	/** Número de celdas por grupo. */
	private static final int NUM_CELDAS_EN_GRUPO = 4;

	/** Celda primera. */
	@Mock
	private Celda celda1;

	/** Celda segunda. */
	@Mock
	private Celda celda2;

	/** Celda tercera. */
	@Mock
	private Celda celda3;

	/** Celda cuarta. */
	@Mock
	private Celda celda4;

	/** Grupo. */
	private Grupo grupo;

	/** Inicialización. */
	@BeforeEach
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void inicializar() {
		Celda[] celdas = { celda1, celda2, celda3, celda4 };
		grupo = new Grupo(celdas);
	}

	/**
	 * Comprueba que el grupo tiene cuatro celdas.
	 */
	@DisplayName("Comprobar que se han instanciado las celdas del grupo.")
	@Test
	void comprobarCorrectoNumeroCeldasEnGrupo() {
		assertThat(
				"Número de celdas instanciadas en el grupo es incorrecto (revisar que se han instanciado las celdas individualmente y no el array).",
				grupo.consultarNumeroCeldas(), is(NUM_CELDAS_EN_GRUPO));
	}

	/**
	 * Comprueba que se puede colocar cualquier pieza en un grupo vacío.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@DisplayName("Comprobar que se permite colocar cualquier pieza en un grupo vacío.")
	@ParameterizedTest
	@MethodSource("proveerFiguraYColor")
	void comprobarConTodasLasCeldasVacias(Figura figura, Color color) {

		// Cuando las cuatro celdas están vacías...
		when(celda1.estaVacia()).thenReturn(true);
		when(celda2.estaVacia()).thenReturn(true);
		when(celda3.estaVacia()).thenReturn(true);
		when(celda4.estaVacia()).thenReturn(true);
		// Se cumple
		assertThat(grupo.existeMismaPiezaDelColorContrario(figura, color), is(false));
	}

	/**
	 * Proveedor de todas las combinaciones de figuras y colores.
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
	 * Comprueba que detecta que se puede colocar esfera en un grupo que NO la contiene.
	 */
	@DisplayName("Comprobar que permite esfera en grupo sin esfera.")
	@Test
	void comprobarQuePermiteEsferaEnGrupoSinEsfera() {
		// Cuando tres celdas están ocupadas por cilindro, cono y cubo
		when(celda1.estaVacia()).thenReturn(false);
		when(celda1.consultarPieza()).thenReturn(new Pieza(Figura.CILINDRO, Color.BLANCO));
		when(celda2.estaVacia()).thenReturn(false);
		when(celda2.consultarPieza()).thenReturn(new Pieza(Figura.CONO, Color.NEGRO));
		when(celda3.estaVacia()).thenReturn(false);
		when(celda3.consultarPieza()).thenReturn(new Pieza(Figura.CUBO, Color.BLANCO));
		when(celda4.estaVacia()).thenReturn(true);
		// Se cumple
		assertThat("No debería existir una pieza en una celda del grupo con dicha figura.", grupo.existeMismaPiezaDelColorContrario(Figura.ESFERA, Color.NEGRO), is(false));
	}

	/**
	 * Comprueba que no se puede colocar una esfera en un grupo lleno.
	 */
	@DisplayName("Comprobar que no se puede colocar una esfera en un grupo lleno que no contiene esfera.")
	@Test
	void comprobarQueNoPermiteEsferaEnGrupoLleno() {
		// Cuando tres celdas están ocupadas por cilindro, cono y cubo
		// lenient().when(celda1.estaVacia()).thenReturn(false);
		when(celda1.estaVacia()).thenReturn(false);
		when(celda1.consultarPieza()).thenReturn(new Pieza(Figura.CILINDRO, Color.BLANCO));
		when(celda2.estaVacia()).thenReturn(false);
		when(celda2.consultarPieza()).thenReturn(new Pieza(Figura.CONO, Color.BLANCO));
		when(celda3.estaVacia()).thenReturn(false);
		when(celda3.consultarPieza()).thenReturn(new Pieza(Figura.CUBO, Color.BLANCO));
		when(celda4.estaVacia()).thenReturn(false);
		when(celda4.consultarPieza()).thenReturn(new Pieza(Figura.CUBO, Color.NEGRO));

		// Se cumple
		assertAll("estado correcto con grupo completo sin repetir pieza",
				() -> assertThat("No debe existir una pieza con la misma figura del turno contrario.",
						grupo.existeMismaPiezaDelColorContrario(Figura.ESFERA, Color.BLANCO), is(false)),
				() -> assertThat("Incorrecto número de piezas en el grupo.", grupo.consultarNumeroPiezas(), is(NUM_CELDAS_EN_GRUPO)),
				() -> assertThat("El grupo no está completo con cuatro piezas con diferente figura.",
						grupo.estaCompletoConFigurasDiferentes(), is(false)));

	}

	/**
	 * Comprueba que no deja colocar una pieza en un grupo que solo contiene una
	 * pieza del color contrario.
	 * 
	 * Por ejemplo: en un grupo con solo una pieza ESFERA-NEGRO no se puede colocar
	 * una ESFERA-BLANCO.
	 * 
	 * @param figura figura
	 * @param color color
	 */
	@DisplayName("Comprobar que no se puede colocar una pieza en un grupo que contiene solo una pieza con la misma figura y color contrario.")
	@ParameterizedTest
	@MethodSource("proveerFiguraYColor")
	void comprobarQueNoPermiteMismaPiezaEnUnGrupoConSoloUnaPiezaDeColorContrario(Figura figura, Color color) {
		// Cuando una celda está ocupada por la figura y color indicada
		when(celda1.estaVacia()).thenReturn(false);
		when(celda1.consultarPieza()).thenReturn(new Pieza(figura, color));
		when(celda2.estaVacia()).thenReturn(true);
		when(celda3.estaVacia()).thenReturn(true);
		when(celda4.estaVacia()).thenReturn(true);

		// Se cumple
		assertAll("estado correcto con grupo incompleto con una única pieza",
				() -> assertThat("Debería existir una pieza con la misma figura del turno contrario.",
						grupo.existeMismaPiezaDelColorContrario(figura, color.obtenerContrario()), is(true)),
				() -> assertThat("Incorrecto número de piezas en el grupo.", grupo.consultarNumeroPiezas(), is(1)),
				() -> assertThat("El grupo no está completo con una pieza con diferente figura.",
						grupo.estaCompletoConFigurasDiferentes(), is(false)));

	}

	/**
	 * Comprueba que un grupo con cuatro piezas diferentes está completo.
	 */
	@DisplayName("Comprobar que un grupo está completo con cuatro piezas de diferente figura.")
	@Test
	void comprobarQueUnGrupoEstaCompleto() {
		// Cuando tres celdas están ocupadas por cilindro, cono y cubo
		when(celda1.estaVacia()).thenReturn(false);
		when(celda1.consultarPieza()).thenReturn(new Pieza(Figura.CILINDRO, Color.BLANCO));
		when(celda2.estaVacia()).thenReturn(false);
		when(celda2.consultarPieza()).thenReturn(new Pieza(Figura.CONO, Color.NEGRO));
		when(celda3.estaVacia()).thenReturn(false);
		when(celda3.consultarPieza()).thenReturn(new Pieza(Figura.CUBO, Color.BLANCO));
		when(celda4.estaVacia()).thenReturn(false);
		when(celda4.consultarPieza()).thenReturn(new Pieza(Figura.ESFERA, Color.NEGRO));

		// Se cumple
		assertAll("estado correcto con grupo completo con cuatro piezas de diferente figura",
				() -> assertThat("Incorrecto número de piezas en el grupo.", grupo.consultarNumeroPiezas(), is(NUM_CELDAS_EN_GRUPO)),
				() -> assertThat("El grupo no está completo con una pieza con diferente figura.",
						grupo.estaCompletoConFigurasDiferentes(), is(true)));
	}
	
	/**
	 * Comprueba la correcta clonación en profundidad del grupo.
	 */
	@Test
	@DisplayName("Comprobar la clonación de un grupo.")
	void comprobarClonacion() {
		when(celda1.clonar()).thenReturn(new Celda(0,0));
		when(celda2.clonar()).thenReturn(new Celda(1,1));
		when(celda3.clonar()).thenReturn(new Celda(2,2));
		when(celda4.clonar()).thenReturn(new Celda(3,3));
		Grupo clon = grupo.clonar();
		assertAll("comprobando clonación del grupo correcta", 
				() -> assertNotSame(grupo, clon, "El clon debería ser una referencia a un grupo diferente."),
				() -> assertThat("El número de celdas en el grupo clonado es incorrecto.", clon.consultarNumeroCeldas(), is(4)),
				() -> assertThat("El grupo clonado no contiene piezas.", clon.consultarNumeroPiezas(),is(0)),
				() -> assertThat("Debería contener esta celda.", clon.contieneCelda(new Celda(0,0)), is(true)),
				() -> assertThat("Debería contener esta celda.", clon.contieneCelda(new Celda(1,1)), is(true)),
				() -> assertThat("Debería contener esta celda.", clon.contieneCelda(new Celda(2,2)), is(true)),
				() -> assertThat("Debería contener esta celda.", clon.contieneCelda(new Celda(3,3)), is(true))				
			);
		
	}
	
	/**
	 * Comprueba que al clonar un grupo indirectamente se clonan las celdas del mismos.
	 * 
	 * Este test es de caja blanca.
	 */
	@Test
	@Tag("WhiteBox")
	@DisplayName("Comprobar la invocación a la clonación de celdas de un grupo.")
	void comprobarClonacionDeCeldas() {
		// when
		grupo.clonar();
		// then
		final String mensaje = "Solo se debería invocar al método clonar de esa celda una vez.";
		verify(celda1, times(1).description(mensaje)).clonar();
		verify(celda2, times(1).description(mensaje)).clonar();
		verify(celda3, times(1).description(mensaje)).clonar();
		verify(celda4, times(1).description(mensaje)).clonar();	
	}

}
