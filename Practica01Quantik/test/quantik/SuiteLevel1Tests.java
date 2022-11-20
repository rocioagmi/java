package quantik;


import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 1 de la práctica Quantik 1.0 (ver README.txt).
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"quantik.util"})
@IncludeTags("UnitTest")
@Suite
@SuiteDisplayName("Tests unitarios de enumeraciones (con dependencias mínimas de compilación).")
public class SuiteLevel1Tests {

}
