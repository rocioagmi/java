package quantik;


import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando solo los tests unitarios de la práctica Quantik 1.0.
 * 
 * No se recomienda proseguir con la práctica hasta que no estén superados estos 
 * tests, con excepción de los de Partida que se pueden postergar a tener resueltos
 * los paquetes @{link quantik.modelo} y @{link quantil.util}.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"quantik.control",
	"quantik.modelo",
	"quantik.util"})
@IncludeTags("UnitTest")
@Suite
@SuiteDisplayName("Tests unitarios (con dependencias mínimas de compilación).")
public class SuiteUnitTest {

}
