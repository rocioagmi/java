import quantik.modelo.Celda;
import quantik.modelo.Pieza;
import quantik.util.Figura;
import quantik.util.Color;

public class TestLore {
    public static void main(String[] args) {
        Celda test= new Celda(1,1);
        System.out.println("Creamos celda 1");
        System.out.println(test.consultarFila());
        System.out.println(test.consultarColumna());
        test.colocar(new Pieza(Figura.CILINDRO,Color.BLANCO ));
        System.out.println(test.consultarPieza().aTexto());
        Celda test1=test.clonar();
        System.out.println("Creamos celda 2");
        System.out.println(test1.consultarFila());
        System.out.println(test1.consultarColumna());
        System.out.println(test1.consultarPieza().aTexto());
        test.colocar(new Pieza(Figura.CONO,Color.NEGRO));
        System.out.println("Celda 1 modificada");
        System.out.println(test.consultarFila());
        System.out.println(test.consultarColumna());
        System.out.println(test.consultarPieza().aTexto());
        System.out.println("Celda 2");
        System.out.println(test1.consultarFila());
        System.out.println(test1.consultarColumna());
        System.out.println(test1.consultarPieza().aTexto());

    }
}
