package quantik;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


/**
 * Suite ejecutando solo los tests de integración de la práctica Quantik 1.0.
 * 
 * Incluye los tests sobre la funcionalidad más compleja de la práctica.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"quantik.control",
	"quantik.modelo",
	"quantik.util"})
@IncludeTags("IntegrationTest")
@Suite
@SuiteDisplayName("Test de integración.")
public class SuiteIntegrationTest {

}
