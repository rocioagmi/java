package quantik.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests sobre la Figura.
 * 
 * La enumeracion es un elemento básico que debería ser implementado y probado en primer lugar
 * antes de proseguir con el resto de clases.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220803
 * 
 */
@DisplayName("Tests sobre Figura (sin dependencias de otras clases).")
@Tag("UnitTest")
@Timeout(value = 1, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class FiguraTest {
	
	/**
	 * Comprobar el correcto número de valores.
	 */
	@DisplayName("Comprobar el número de valores definidos.")
	@Test
	public void probarNumeroValores() {
		assertThat("La enumeración Figura debe tener exactamente CUATRO valores definidos (no importa el orden).", Figura.values().length, is(4));
	}
		
	/**
	 * Correctos textos para cada figura.
	 */
	@DisplayName("Comprobar los textos literales para cada valor.")
	@Test
	public void probarTextos() {
		assertAll("comprobando textos correctos para cada valor del tipo enumerado ",
			() -> assertThat("Texto mal definido para CILINDRO.", 
					Figura.CILINDRO.aTexto(), is("CL")),
			
			() -> assertThat("Texto mal definido para CONO.",
					Figura.CONO.aTexto(), is("CN")),
			
			() -> assertThat("Texto mal definido para CUBO.", 
					Figura.CUBO.aTexto(), is("CB")),			
			
			() -> assertThat("Texto mal definido para ESFERA.", 
					Figura.ESFERA.aTexto(), is("ES"))	

			);			
	}	
}
