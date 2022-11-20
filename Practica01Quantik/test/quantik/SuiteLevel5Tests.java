package quantik;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 5 de la práctica Quantik 1.0 (ver README.txt).
 * Equivalente a ejecutar {@link quantik.SuiteAllTests} con todos lo tests.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"quantik.control",
	"quantik.modelo",
	"quantik.util"})
@Suite
@SuiteDisplayName("Tests unitarios y de integración de paquetes control, modelo y util completos.")
public class SuiteLevel5Tests {

}
