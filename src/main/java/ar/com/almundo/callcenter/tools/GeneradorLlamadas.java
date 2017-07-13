
package ar.com.almundo.callcenter.tools;

import ar.com.almundo.callcenter.llamada.EstadoLlamada;
import ar.com.almundo.callcenter.llamada.Llamada;
import java.util.LinkedList;

/**
 *
 * @author Olmedo
 */
public class GeneradorLlamadas
{

    /**
     * Este metodo permite crear una llamada.
     *
     * Las llamadas se crean tendiendo en cuenta la probabilidad. La
     * probabilidad es un numero de 0 a 100. Este valor es comparado con un
     * valor que se genera dentro del metodo y si el valor es menor que la
     * probabilidad se crea una nueva llamada con un tiempo de espera que puede
     * ser entre 5 y 10 segundos. En caso que no se cumpla la condicion de la
     * restriccion no se crea ninguna llamada y se retorna un valor nulo.
     *
     * @param probabilidad Valor de la probabilidad para crear una nueva
     * llamada.
     * @return Una llamada nueva o null, segun la probabilidad de creacion de
     * llamadas.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: crear(int) -> Llamada">
    public static Llamada crear(int probabilidad)
    {
        int tiempoDuracion = 5 + (int) (Math.random() * 5);
        boolean crear = 100 * Math.random() < probabilidad;

        return crear ? new Llamada(tiempoDuracion, EstadoLlamada.TIMBRAMDO) : null;
    }
    //</editor-fold>

    /**
     * Este metodo permite crear un numero maximo de llamadas determinado por
     * cantidad, las llamadas se crean de forma aleatoria segun el valor
     * probabilidad.
     *
     * Cada nueva llamada se almacena en una lista la cual es retornada al
     * terminar el proceso.
     *
     * @param cantidad Numero maximo de llamadas que se pueden crear.
     * @param probabilidad Probabilidad de creacion de cada llamada.
     * @return Listado de llamadas creadas.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: crearLlamadas(int, int) -> LinkedList<Llamada>">
    public static LinkedList<Llamada> crearLlamadas(int cantidad, int probabilidad)
    {
        LinkedList<Llamada> l = new LinkedList<>();

        for (int i = 0; i < cantidad; i++)
        {
            Llamada nuevaLlamada = GeneradorLlamadas.crear(probabilidad);
            if (nuevaLlamada != null)
            {
                l.add(nuevaLlamada);
            }
        }

        return l;
    }
    //</editor-fold>
}
