package quantik.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Tests sobre el gestor de grupos.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests sobre GestorGrupos (depende de implementaciones reales de Tablero, Pieza, Celda, Grupo, Figura y Color).")
@Tag("IntegrationTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS) // Time out global para todos los tests salvo los de ciclo de vida
public class GestorGruposTest {

	/** Tablero. */
	private Tablero tablero;

	/** Gestor de grupos. */
	@Spy
	private GestorGrupos gestorGrupos;

	/** Inicialización. */
	@BeforeEach
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void inicializar() {
		tablero = new Tablero();
		gestorGrupos = new GestorGrupos(tablero);
	}

	/**
	 * Comprobando el estado inicial.
	 */
	@Nested
	@DisplayName("Estado inicial")
	class ComprobandoEstadoInicial {
		/**
		 * Comprueba que no hay grupo ganador al inicio.
		 */
		@Test
		@DisplayName("Comprobar que no hay grupo ganador inicialmente.")
		void probarQueNoHayGrupoGanadorInicialmente() {
			assertThat("No debería haber grupo ganador inicialmente con un tablero vacío.",
					gestorGrupos.hayGrupoGanador(), is(false));
		}

		/**
		 * Comprueba que no hay conflictos colocando una pieza en celda al inicio con un tablero vacío.
		 * 
		 * @param fila fila 
		 * @param columna columna
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadas")
		@DisplayName("Comrpobar que no hay conflictos en un tablero vacío.")
		void probarQueNoHayConflictosEnUnTableroVacio(int fila, int columna) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			assertThat("Toda celda no debería estar en conflicto porque sus grupos están vacíos.",
					gestorGrupos.hayConflictoEnGruposDeCelda(celda, Figura.CILINDRO, Color.NEGRO), is(false));
		}
	}

	/**
	 * Comprobando corrección de grupos creados.
	 */
	@Nested
	@DisplayName("Correccion en grupos creados")
	class ComprobancoCorreccionDeGruposCreados {
		

		/**
		 * Comprueba que toda celda pertenece a 3 grupos.
		 * 
		 * @param fila fila 
		 * @param columna columna
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadas")
		@DisplayName("Comprobar asignación de toda celda a tres grupos.")
		void probarAsignacionGrupos(int fila, int columna) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			Grupo[] gruposRelacionados = gestorGrupos.obtenerGruposConteniendoCelda(celda);
			assertThat("La celda no tiene asignado un número de grupos correcto.", gruposRelacionados.length, is(3));
		}

		/**
		 * Comprueba que toda celda está correctamente asignada a su grupo horizontal.
		 * 
		 * @param fila fila 
		 * @param columna columna
		 * @param coordenadasDeGrupo coordenadas de otras celdas en el grupo horizontal
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadasyGruposHorizontales")
		@DisplayName("Comprobar que toda celda está correctamente asignado a su grupo horizontal.")
		void probarAsignacionGruposHorizontales(int fila, int columna, int[][] coordenadasDeGrupo) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			Grupo grupo = generarGrupo(coordenadasDeGrupo);
			Grupo[] gruposRelacionados = gestorGrupos.obtenerGruposConteniendoCelda(celda);
			assertThat("La celda " + celda.toString() + " no tiene asignado el grupo horizonal.",
					Arrays.asList(gruposRelacionados), hasItems(grupo));
		}

		/**
		 * Comprueba que toda celda está correctamente asignada a su grupo vertical.
		 * 
		 * @param fila fila 
		 * @param columna columna
		 * @param coordenadasDeGrupo coordenadas de otras celdas en el grupo vertical
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadasyGruposVerticales")
		@DisplayName("Comprobar que toda celda está correctamente asignado a su grupo vertical.")
		void probarAsignacionGruposVerticales(int fila, int columna, int[][] coordenadasDeGrupo) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			Grupo grupo = generarGrupo(coordenadasDeGrupo);
			Grupo[] gruposRelacionados = gestorGrupos.obtenerGruposConteniendoCelda(celda);
			assertThat("La celda " + celda.toString() + " no tiene asignado el grupo vertical.",
					Arrays.asList(gruposRelacionados), hasItems(grupo));
		}

		/**
		 * Comprueba que toda celda está correctamente asignada a su grupo cuadrado.
		 * 
		 * @param fila fila 
		 * @param columna columna
		 * @param coordenadasDeGrupo coordenadas de otras celdas en el grupo cuadrado
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerCoordenadasyGruposCuadrados")
		@DisplayName("Comprobar que toda celda está correctamente asignado a su grupo cuadrado.")
		void probarAsignacionGruposCuadrados(int fila, int columna, int[][] coordenadasDeGrupo) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			Grupo grupo = generarGrupo(coordenadasDeGrupo);
			Grupo[] gruposRelacionados = gestorGrupos.obtenerGruposConteniendoCelda(celda);
			assertThat("La celda " + celda.toString() + " no tiene asignado el grupo cuadrado.",
					Arrays.asList(gruposRelacionados), hasItems(grupo));
		}
		

		// Métodos de utilidad
		/**
		 * Convierte un array de coordenadas a un grupo con sus correspondientes celdas.
		 * 
		 * @param coordenadasDeGrupo filas y columnas de celdas de un grupo
		 * @return grupo correspondiente
		 */
		private Grupo generarGrupo(int[][] coordenadasDeGrupo) {
			Celda[] celdas = new Celda[4];
			for (int i = 0; i < 4; i++) {
				int fila = coordenadasDeGrupo[i][0];
				int columna = coordenadasDeGrupo[i][1];
				celdas[i] = tablero.obtenerCelda(fila, columna);
			}
			return new Grupo(celdas);
		}
	}

	/**
	 * Creando grupos ganadores.
	 */
	@Nested
	@DisplayName("Creando grupos ganadores")
	class CreandoGruposGanadores {
		
		/**
		 * Comprueba que al colocar cuatro piezas diferentes del mismo color en el grupo horizontal se tiene un ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo horizontal
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposHorizontales")
		@DisplayName("Comprobar que hay ganador en horizontal con piezas de igual color.")
		public void comprobarHayGrupoGanadorEnHorizontalesConPiezasDeIgualesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasDiferentesDelMismoColor(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que al colocar cuatro piezas diferentes de diferentes colores en el grupo horizontal se tiene un ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo horizontal
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposHorizontales")
		@DisplayName("Comprobar que hay ganador en horizontal con piezas de distintos colores.")
		public void comprobarHayGrupoGanadorEnHorizontalesConPiezasDeDiferentesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasDiferentesVariandoColores(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que al colocar cuatro piezas diferentes del mismo color en el grupo vertical se tiene un ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo vertical
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposVerticales")
		@DisplayName("Comprobar que hay ganador en vertical con piezas de igual color.")
		public void comprobarHayGrupoGanadorEnVerticalesConPiezasDeIgualesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasDiferentesDelMismoColor(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que al colocar cuatro piezas diferentes de diferentes colores en el grupo vertical se tiene un ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo vertical
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposVerticales")
		@DisplayName("Comprobar que hay ganador en vertical con piezas de distintos colores.")
		public void comprobarHayGrupoGanadorEnVerticalesConPiezasDeDiferentesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasDiferentesVariandoColores(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que al colocar cuatro piezas diferentes del mismo color en el grupo cuadrado se tiene un ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo cuadrado
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposCuadrados")
		@DisplayName("Comprobar que hay ganador en cuadrado con piezas de igual color.")
		public void comprobarHayGrupoGanadorEnCuadradosConPiezasDeIgualesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasDiferentesDelMismoColor(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que al colocar cuatro piezas diferentes de diferentes colores en el grupo cuadrado se tiene un ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo cuadrado
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposCuadrados")
		@DisplayName("Comprobar que hay ganador en cuadrado con piezas de distintos colores.")
		public void comprobarHayGrupoGanadorEnCuadradosConPiezasDeDiferentesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasDiferentesVariandoColores(coordenadasDeGrupo);
		}

		/**
		 * Coloca piezas de diferentes figuras y del mismo color en el grupo comprobando que hay ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de celdas en el grupo
		 */
		private void colocarCuatroPiezasDiferentesVariandoColores(int[][] coordenadasDeGrupo) {
			tablero.colocar(coordenadasDeGrupo[0][0], coordenadasDeGrupo[0][1],
					new Pieza(Figura.CILINDRO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[1][0], coordenadasDeGrupo[1][1], new Pieza(Figura.CONO, Color.NEGRO));
			tablero.colocar(coordenadasDeGrupo[2][0], coordenadasDeGrupo[2][1], new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[3][0], coordenadasDeGrupo[3][1], new Pieza(Figura.ESFERA, Color.NEGRO));
			assertThat("Debería haber grupo completo.", gestorGrupos.hayGrupoGanador(), is(true));
		}

		/**
		 * Coloca piezas de diferentes figuras y de distintos colores en el grupo comprobando que hay ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de celdas en el grupo
		 */
		private void colocarCuatroPiezasDiferentesDelMismoColor(int[][] coordenadasDeGrupo) {
			tablero.colocar(coordenadasDeGrupo[0][0], coordenadasDeGrupo[0][1],
					new Pieza(Figura.CILINDRO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[1][0], coordenadasDeGrupo[1][1], new Pieza(Figura.CONO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[2][0], coordenadasDeGrupo[2][1], new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[3][0], coordenadasDeGrupo[3][1], new Pieza(Figura.ESFERA, Color.BLANCO));
			assertThat("Debería haber grupo completo.", gestorGrupos.hayGrupoGanador(), is(true));
		}
	}
	
	/**
	 * Comprobando casos de no ganador con menos de cuatro piezas en el grupo.
	 */
	@Nested
	@DisplayName("Sin grupo ganador con menos de cuatro piezas")
	class ComprobarCasosSinGrupoGanadorConMenosDeCuatroPiezas {
		
		/**
		 * Comprueba que no hay ganador con cuatro piezas diferentes en figura y mismo color en las esquinas.
		 * 
		 */
		@Test
		@DisplayName("Comprobar con piezas del mismo color en las esquinas.")
		void comprobarConPiezasDelMismoColorEnEsquinas() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(0, 3, new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(3, 0, new Pieza(Figura.CONO, Color.BLANCO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.BLANCO));
			assertThat("No debería haber grupo ganador con cuatro piezas diferentes del mismo color en las esquinas.", gestorGrupos.hayGrupoGanador(), is(false));
		}
		
		/**
		 * Comprueba que no hay ganador con cuatro piezas diferentes en figura y diferente color en las esquinas.
		 * 
		 */
		@Test
		@DisplayName("Comprobar con piezas de diferente color en las esquinas.")
		void comprobarConPiezasDeDiferentesColoresEnEsquinas() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(0, 3, new Pieza(Figura.CUBO, Color.NEGRO));
			tablero.colocar(3, 0, new Pieza(Figura.CONO, Color.BLANCO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.NEGRO));
			assertThat("No debería haber grupo ganador con cuatro piezas diferentes variando colores en las esquinas.", gestorGrupos.hayGrupoGanador(), is(false));
		}
		
		/**
		 * Comprueba que no hay ganador con tres piezas diferentes en figura y mismo color en las esquinas.
		 * 
		 */
		@Test
		@DisplayName("Comprobar con piezas del mismo color con dos en vertical y horizontal.")
		void comprobarConPiezasDelMismoColorCon2PorVerticalYHorizontal() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(0, 1, new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(1, 0, new Pieza(Figura.CILINDRO, Color.BLANCO));
			
			tablero.colocar(2, 3, new Pieza(Figura.CONO, Color.NEGRO));
			tablero.colocar(3, 2, new Pieza(Figura.CUBO, Color.NEGRO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.NEGRO));
			assertThat("No debería haber grupo ganador con 3 piezas diferentes del mismo color en dos esquinas.", gestorGrupos.hayGrupoGanador(), is(false));
		}
		
		/**
		 * Comprueba que no hay ganador con tres piezas diferentes en figura y mismo color en horizontal.
		 * 
		 */
		@Test
		@DisplayName("Comprobar con piezas del mismo color con tres en horizontal.")
		void comprobarConPiezasDelMismoColorCon3PorHorizontal() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(0, 1, new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(0, 2, new Pieza(Figura.CILINDRO, Color.BLANCO));
			
			tablero.colocar(3, 1, new Pieza(Figura.CONO, Color.NEGRO));
			tablero.colocar(3, 2, new Pieza(Figura.CUBO, Color.NEGRO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.NEGRO));
			assertThat("No debería haber grupo ganador con 3 piezas diferentes del mismo color en dos horizontales.", gestorGrupos.hayGrupoGanador(), is(false));
		}
		
		/**
		 * Comprueba que no hay ganador con tres piezas diferentes en figura y mismo color en vertical.
		 * 
		 */
		@Test
		@DisplayName("Comprobar con piezas del mismo color con tres en vertical.")
		void comprobarConPiezasDelMismoColorCon3PorVertical() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(1, 0, new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(2, 0, new Pieza(Figura.CILINDRO, Color.BLANCO));
			
			tablero.colocar(1, 3, new Pieza(Figura.CONO, Color.NEGRO));
			tablero.colocar(2, 3, new Pieza(Figura.CUBO, Color.NEGRO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.NEGRO));
			assertThat("No debería haber grupo ganador con 3 piezas diferentes del mismo color en dos verticales.", gestorGrupos.hayGrupoGanador(), is(false));
		}
		
		/**
		 * Comprueba que no hay ganador con tres piezas diferentes en figura y diferente color en horizontal.
		 * 
		 */
		@Test
		@DisplayName("Comprobar que no hay ganador con tres piezas diferentes en figura y diferente color en horizontal.")
		void comprobarConPiezasDeDiferenteColorCon3PorHorizontal() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(0, 1, new Pieza(Figura.CUBO, Color.NEGRO));
			tablero.colocar(0, 2, new Pieza(Figura.CILINDRO, Color.BLANCO));
			
			tablero.colocar(3, 1, new Pieza(Figura.CONO, Color.NEGRO));
			tablero.colocar(3, 2, new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.NEGRO));
			assertThat("No debería haber grupo ganador con 3 piezas diferentes de diferentes colores en dos horizontales.", gestorGrupos.hayGrupoGanador(), is(false));
		}
		
		/**
		 * Comprueba que no hay ganador con tres piezas diferentes en figura y diferente color en vertical.
		 * 
		 */
		@Test
		@DisplayName("Comprobar con piezas de diferente color con tres en vertical.")
		void comprobarConPiezasDeDiferenteColorCon3PorVertical() {
			tablero.colocar(0, 0, new Pieza(Figura.ESFERA, Color.BLANCO));
			tablero.colocar(1, 0, new Pieza(Figura.CUBO, Color.NEGRO));
			tablero.colocar(2, 0, new Pieza(Figura.CILINDRO, Color.BLANCO));
			
			tablero.colocar(1, 3, new Pieza(Figura.CONO, Color.NEGRO));
			tablero.colocar(2, 3, new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(3, 3, new Pieza(Figura.CILINDRO, Color.NEGRO));
			assertThat("No debería haber grupo ganador con 3 piezas diferentes de diferentes colores en dos verticales.", gestorGrupos.hayGrupoGanador(), is(false));
		}
	}
	
	/**
	 * Creando falsos grupos ganadores con cuatro piezas pero no todas diferentes.
	 *
	 */
	@Nested
	@DisplayName("Falsos grupos ganadores con alguna pieza repetida")
	class CreandoFalsosGruposGanadoresConCuatroPiezasPeroAlgunaRepetida {
		
		/**
		 * Comprueba que no hay ganador con piezas del mismo color en horizontal repitiendo alguna figura.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposHorizontales")
		@DisplayName("Comprobar que no hay ganador en horizontal con piezas repetidas del mismo color.")
		public void comprobarNoHayGrupoGanadorEnHorizontalesConPiezasRepetidasDeIgualesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasPeroAlgunaRepetidaDelMismoColor(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que no hay ganador con piezas de diferente color en horizontal repitiendo alguna figura.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposHorizontales")
		@DisplayName("Comprobar que no hay ganador en horizontal con piezas repetidas de diferente color.")
		public void comprobarNoHayGrupoGanadorEnHorizontalesConPiezasDeDiferentesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasPeroAlgunaRepetidaVariandoColores(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que no hay ganador con piezas del mismo color en vertical repitiendo alguna figura.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposVerticales")
		@DisplayName("Comprobar que no hay ganador en vertical con piezas repetidas del mismo color.")
		public void comprobarNoHayGrupoGanadorEnVerticalesConPiezasDeIgualesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasPeroAlgunaRepetidaDelMismoColor(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que no hay ganador con piezas de diferente color en vertical repitiendo alguna figura.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposVerticales")
		@DisplayName("Comprobar que no hay ganador en vertical con piezas repetidas de diferente color.")
		public void comprobarNoHayGrupoGanadorEnVerticalesConPiezasDeDiferentesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasPeroAlgunaRepetidaVariandoColores(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que no hay ganador con piezas del mismo color en cuadrado repitiendo alguna figura.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposCuadrados")
		@DisplayName("Comprobar que no hay ganador en cuadrado con piezas repetidas del mismo color.")
		public void comprobarNoHayGrupoGanadorEnCuadradosConPiezasDeIgualesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasPeroAlgunaRepetidaDelMismoColor(coordenadasDeGrupo);
		}

		/**
		 * Comprueba que no hay ganador con piezas de diferente color en cuadrado repitiendo alguna figura.
		 * 
		 * @param coordenadasDeGrupo coordenadas de grupo
		 */
		@ParameterizedTest
		@MethodSource("quantik.modelo.Util#proveerGruposCuadrados")
		@DisplayName("Comprobar que no hay ganador en cuadrado con piezas repetidas de diferente color.")
		public void comprobarNoHayGrupoGanadorEnCuadradosConPiezasDeDiferentesColores(int[][] coordenadasDeGrupo) {
			colocarCuatroPiezasPeroAlgunaRepetidaVariandoColores(coordenadasDeGrupo);
		}

		/**
		 * Coloca piezas repitiendo alguna figura y del mismo color en el grupo comprobando que NO hay ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de celdas en el grupo
		 */
		private void colocarCuatroPiezasPeroAlgunaRepetidaVariandoColores(int[][] coordenadasDeGrupo) {
			// Repetimos cilindro
			tablero.colocar(coordenadasDeGrupo[0][0], coordenadasDeGrupo[0][1],
					new Pieza(Figura.CILINDRO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[1][0], coordenadasDeGrupo[1][1], new Pieza(Figura.CILINDRO, Color.NEGRO));
			tablero.colocar(coordenadasDeGrupo[2][0], coordenadasDeGrupo[2][1], new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[3][0], coordenadasDeGrupo[3][1], new Pieza(Figura.ESFERA, Color.NEGRO));
			assertThat("Debería haber grupo completo.", gestorGrupos.hayGrupoGanador(), is(false));
		}

		/**
		 * Coloca piezas repitiendo alguna figura y de diferente color en el grupo comprobando que NO hay ganador.
		 * 
		 * @param coordenadasDeGrupo coordenadas de celdas en el grupo
		 */
		private void colocarCuatroPiezasPeroAlgunaRepetidaDelMismoColor(int[][] coordenadasDeGrupo) {
			// Repetimos cilindro
			tablero.colocar(coordenadasDeGrupo[0][0], coordenadasDeGrupo[0][1],
					new Pieza(Figura.CILINDRO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[1][0], coordenadasDeGrupo[1][1], new Pieza(Figura.CONO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[2][0], coordenadasDeGrupo[2][1], new Pieza(Figura.CUBO, Color.BLANCO));
			tablero.colocar(coordenadasDeGrupo[3][0], coordenadasDeGrupo[3][1], new Pieza(Figura.CILINDRO, Color.BLANCO));
			assertThat("Debería haber grupo completo.", gestorGrupos.hayGrupoGanador(), is(false));
		}
	}


}
