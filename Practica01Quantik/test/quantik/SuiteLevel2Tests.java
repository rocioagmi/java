package quantik;


import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 2 de la práctica Quantik 1.0 (ver README.txt).
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.1
 */
@SelectPackages({
	"quantik.modelo",
	"quantik.util"})
@ExcludeClassNamePatterns({"^.*CajaTest?$", "^.*TableroTest?$", "^.*GrupoTest?$", "^.*GruposTest?$"})
@Suite
@SuiteDisplayName("Tests unitarios de clases básicas (con dependencias mínimas de compilación).")
public class SuiteLevel2Tests {

}
