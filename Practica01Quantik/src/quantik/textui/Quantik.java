package quantik.textui;

import java.util.Scanner;

import quantik.control.Partida;
import quantik.modelo.Caja;
import quantik.modelo.GestorGrupos;
import quantik.modelo.Pieza;
import quantik.modelo.Tablero;
import quantik.util.Color;
import quantik.util.Figura;

/**
 * Quantik en modo texto.
 * 
 * Se abusa del uso de static tanto en atributos como en métodos para comprobar
 * su similitud a variables globales y funciones globales de otros lenguajes.
 *
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
public class Quantik {

	/** Tamaño en caracteres de una jugada. */
	private static final int TAMAÑO_JUGADA = 4;

	/**
	 * Partida
	 */
	private static Partida partida;
	
	/**
	 * Caja blancas.
	 */
	private static Caja cajaBlancas;
	
	/**
	 * Caja negras.
	 */
	private static Caja cajaNegras;

	/**
	 * Lector por teclado.
	 */
	private static Scanner scanner;

	/**
	 * Método raíz.
	 * 
	 * @param args argumentos de entrada
	 */
	public static void main(String[] args) {
		// A COMPLETAR POR EL ALUMNADO 
		// REUTILIZANDO LOS MÉTODOS DISPONIBLES
	}
	
	/**
	 * Avanza el turno al siguiente jugador.
	 */
	private static void avanzarTurno() {
		partida.cambiarTurno();
	}

	/**
	 * Muestra en pantalla el mensaje de interrupción de partida.
	 */
	private static void mostrarInterrupcionPartida() {
		System.out.println("Interrumpida la partida, se concluye el juego.");
	}

	/**
	 * Muestra en pantalla el estado actual del tablero en formato texto.
	 */
	private static void mostrarTableroEnFormatoTexto() {
		System.out.println();
		System.out.println(partida.consultarTablero().aTexto());
	}

	/**
	 * Realiza la jugada introducida por teclado. Se supone que la jugada en
	 * cuanto al formato ya ha sido validada previamente y también que se ha
	 * comprobado la legalidad de la misma.
	 * 
	 * @param jugada jugada
	 */
	private static void realizarJugada(String jugada) {
		int fila = leerFila(jugada);
		int columna = leerColumna(jugada);
		Figura figura = leerFigura(jugada);		
		partida.colocarPiezaEnTurnoActual(fila, columna, figura);
	}
	
	/**
	 * Muestra en pantalla el mensaje de error si la jugada es ilegal.
	 */
	private static void mostrarErrorEnLegalidadJugada() {		
		System.out.println(
				"Jugada ILEGAL, compruebe piezas del jugador contrario y disponibilidad de sus propias piezas.");
		mostrarPiezasDisponibles(partida.consultarTurno());
		mostrarTableroEnFormatoTexto();
	}
	
	/**
	 * Muestra las piezas disponibles.
	 * 
	 * @param color color de la caja a mostrar
	 */
	private static void mostrarPiezasDisponibles(Color color) {
		Caja caja = color == Color.BLANCO ? cajaBlancas : cajaNegras; 
		System.out.print("Piezas disponibles para caja de color " + color + ": ");
		StringBuilder sb = new StringBuilder();
		for (Pieza pieza : caja.consultarPiezasDisponibles()) {
			sb.append(pieza.consultarFigura());
			sb.append("-");
		}
		String text = sb.substring(0,  sb.lastIndexOf("-"));
		System.out.println(text);		
	}
	
	/**
	 * Comprueba la legalidade de la jugada asumiendo que el formato es correcto.
	 * 
	 * @param jugada jugada en formato texto correcta
	 * @return true si puede realizarse la jugada en base a las reglas, false en caso contrario
	 */
	private static boolean validarLegalidad(String jugada) {
		int fila = leerFila(jugada);
		int columna = leerColumna(jugada);
		Figura figura = leerFigura(jugada);
		return partida.esJugadaLegalEnTurnoActual(fila, columna, figura);
	}

	/**
	 * Muestra el mensaje de bienvenida con instrucciones para finalizar la partida.
	 */
	private static void mostrarMensajeBienvenida() {
		System.out.println("Bienvenido al juego del Quantik 1.0");
		System.out.println("Para interrumpir partida introduzca \"salir\".");
		System.out.println("Disfrute de la partida...");
	}

	/**
	 * Mostrar al usuario información de error en el formato de entrada, mostrando
	 * ejemplos.
	 */
	private static void mostrarErrorEnFormatoDeEntrada() {
		System.out.println();
		System.out.println("Error en el formato de entrada.");
		System.out.println(
				"El formato debe ser numeronumeroletraletra, por ejemplo 12ES para colocar una ESfera en la fila 1, columna2");
		System.out.println(
				"Los números deben estar en el rango [0,3] y las dos letras coinciden con los tipos de figuras: CL (cilindro), CN (cono), CB (cubo) y ES (esfera).\n");
	}

	/**
	 * Comprueba si la partida está finalizada.
	 * 
	 * @return true se ha finalizado la partida, false en caso contrario
	 */
	private static boolean comprobarSiFinalizaPartida() {
		return partida.estaAcabadaPartida();
	}

	/**
	 * Finaliza la partida informando al usuario y cerrando recursos abiertos.
	 */
	private static void finalizarPartida() {
		if (partida.estaAcabadaPartida()) {
			System.out.printf("Ganada la partida por el jugador con turno %s.%n", partida.consultarTurno());
		}
		scanner.close();
	}

	/**
	 * Inicializa el estado de los elementos de la partida.
	 */
	private static void inicializarPartida() {
		// Inicializaciones de objetos
		cajaBlancas = new Caja(Color.BLANCO);
		cajaNegras = new Caja(Color.NEGRO);
		Tablero tablero = new Tablero();
		partida = new Partida(tablero, cajaBlancas, cajaNegras);
		// Abrimos la lectura desde teclado
		scanner = new Scanner(System.in);
	}

	/**
	 * Recoge jugada del teclado.
	 * 
	 * @return jugada jugada en formato texto
	 */
	private static String recogerJugada() {
		System.out.printf("Introduce jugada el jugador con turno %s (máscara nnll donde n es número y l es letra): ", partida.consultarTurno());
		return scanner.next();
	}

	/**
	 * Valida la corrección del formato de la jugada. Solo comprueba la corrección
	 * del formato de entrada en cuanto al tablero. La jugada tiene que tener cuatro
	 * caracteres y contener letras y números de acuerdo a las reglas de la notación
	 * algebraica.
	 * 
	 * Otra mejor solución alternativa es el uso de expresiones regulares (se verán
	 * en la asignatura de 3º Procesadores del Lenguaje).
	 * 
	 * @param jugada a validar
	 * @return true si el formato de la jugada es correcta según las coordenadas
	 *         disponibles del tablero
	 */
	private static boolean validarFormato(String jugada) {
		boolean estado = true;
		if (jugada.length() != TAMAÑO_JUGADA || esFiguraIncorrecta(jugada.substring(2))
				|| esNumeroInvalido(jugada.charAt(0)) || esNumeroInvalido(jugada.charAt(1))) {
			estado = false;
		}
		return estado;
	}

	/**
	 * Comprueba si el texto no se corresponde con una figura.
	 * 
	 * @param texto texto a comprobar
	 * @return true si el texto no se corresponde con el texto de alguna figura, false en caso contrario
	 */
	private static boolean esFiguraIncorrecta(String texto) {
		// evalúa si no es un cilindro, ni cubo, ni cono, ni esfera retornando dicha evaluación...
		return (!texto.equals(Figura.CILINDRO.aTexto()) && !texto.equals(Figura.CUBO.aTexto())
				&& !texto.equals(Figura.CONO.aTexto()) && !texto.equals(Figura.ESFERA.aTexto()));
	}

	/**
	 * Comprueba si el número está fuera del rango [0,3].
	 * 
	 * @param numero numero
	 * @return true si el número no está en el rango, false en caso contrario
	 */
	private static boolean esNumeroInvalido(char numero) {
		return numero < '0' || numero > '3';
	}

	/**
	 * Obtiene la fila de la jugada.
	 * 
	 * @param jugada jugada en formato nnll
	 * @return fila de la celda donde colocar la pieza
	 */
	private static int leerFila(String jugada) {
		return Integer.parseInt(jugada.substring(0, 1));
	}

	/**
	 * Obtiene la columna de la jugada.
	 * 
	 * @param jugada jugada en formato nnll
	 * @return columna de la celda donde colocar la pieza
	 */
	private static int leerColumna(String jugada) {
		return Integer.parseInt(jugada.substring(1, 2));
	}

	/**
	 * Lee la figura correspondiente de la jugada. 
	 * Se asume que se ha comprobado previamente la longitud de la cadena.
	 * 
	 * @param jugada jugada en formato nnll donde ll es el texto de la figura a extraer
	 * @return figura con la que obtener la pieza de la caja a colocar en el tablero
	 */
	private static Figura leerFigura(String jugada) {
		String figura = jugada.substring(2);
		
		if (figura.equals(Figura.CILINDRO.aTexto()))
			return Figura.CILINDRO;
		else if (figura.equals(Figura.CONO.aTexto()))
			return Figura.CONO;
		else if (figura.equals(Figura.CUBO.aTexto()))
			return Figura.CUBO;
		else if (figura.equals(Figura.ESFERA.aTexto()))
			return Figura.ESFERA;
		return null;
	}

}
