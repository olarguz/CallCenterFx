
package ar.com.almundo.callcenter.tools;

import ar.com.almundo.callcenter.empleado.Empleado;
import ar.com.almundo.callcenter.empleado.EstadoEmpleado;
import ar.com.almundo.callcenter.llamada.EstadoLlamada;
import ar.com.almundo.callcenter.llamada.Llamada;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Olmedo
 */
public class Util
{

    /**
     * Este metodo sirve para clasificar los elementos de la lista de empleados.
     *
     * Este metodo recibe como parametro el tipo de empleado que se quiere
     * clasificar de la lista de empleados general y recibe tambien la lista de
     * empleados.
     *
     * Este medoto esta escrito de tal forma que puede ser reutilizado usando
     * como parametro, cualquier subclase de Empleado, y esta pensado para no
     * tener que escribir un metodo de clasificacion para cada subclase de
     * Empleado. Se usa uno de las ventajas que tiene Java que es el uso de
     * Generics, y de POO como la herencia y el reuso de codigo.
     *
     * @param t Clase que es usada como filtro. Debe ser subclase de Empleado.
     * @param empleados Listado de todos los empleados del Callcenter que pueden
     * atender llamadas.
     * @return Listado de los empleados clasificados, segun la clase a la que
     * pertencen.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: seleccionar(Class<? extends Empleado>, LinkedList<Empleado>) -> LinkedList<? extends Empleado>">
    public static LinkedList<? extends Empleado> seleccionar(Class<? extends Empleado> t, LinkedList<Empleado> empleados)
    {
        LinkedList<Empleado> l = new LinkedList<>();

        empleados.stream().filter((empleado) -> (t.isInstance(empleado))).forEachOrdered((empleado) ->
        {
            l.add(empleado);
        });

        return l;
    }
    //</editor-fold>

    /**
     * Metodo para verificar si en una lista de objetos que pertenezcan a alguna
     * subclase de empleados hay algun empleado libre.
     *
     * El funcionamiento del metodo es el siguiente, ser recorren todos los
     * elementos de la lista preguntando a cada uno por su estado. El estado
     * puede ser OCUPADO o LIBRE, en caso que encuentre algun empleado libre el
     * proceso termina de forma anticipada haciendo que este esa mas eficiente,
     * el valor de retorno del metodo en este caso es verdadero.
     *
     * En caso que todos los empleados esten ocupados, el proceso recorre todos
     * los elementos de la lista y al final returna falso.
     *
     * @param empleados
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: hayLibres(LinkedList<? extends Empleado>) -> boolean">
    public static boolean hayLibres(LinkedList<? extends Empleado> empleados)
    {
        boolean resp = false;

        for (Iterator<? extends Empleado> it = empleados.iterator(); !resp && it.hasNext();)
        {
            Empleado empleado = it.next();
            if (empleado.darEstado() == EstadoEmpleado.LIBRE)
            {
                resp = true;
            }

        }

        return resp;
    }
    //</editor-fold>

    /**
     * Este metodo permite asignar las llamadas que estan en la cola de espera a
     * alguno de los empleados que se encuentren disponbiles.
     *
     * Es de notar que los empleados estan clasificados, esto permite asignar de
     * forma independiente los empleados, segun a la subclase que pertenezcan.
     * Las llamadas se asignan mientras haya un empleado disponible, en caso que
     * todos los usuarios esten ocupados, no se hace ninguna nueva asignacion.
     * Tambien se tiene en cuenta que la cola tenga llamadas para asignar, en
     * caso contrario se detiene el proceso de asignacion.
     *
     * Cuando una llamada es asignada, la llamada pasa de estar en estado
     * ENESPERA a estado ENPROCESO y el usuario para de estar DISPONIBLE a
     * OCUPADO.
     *
     * @param empleados Listado de empleados clasificados a los cuales se les
     * pretende asignar llamaddas.
     * @param llamadas Listado de llamadas en espera.
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: 'asignarLlamada(LinkedList<? extends Empleado>, Queue<Llamada>) -> boolean'">
    public static boolean asignarLlamada(LinkedList<? extends Empleado> empleados, Queue<Llamada> llamadas)
    {
        boolean detenerCiclo = false;

        for (Iterator<? extends Empleado> it = empleados.iterator(); !detenerCiclo && it.hasNext();)
        {
            Empleado empleado = it.next();
            if (empleado.darEstado() == EstadoEmpleado.LIBRE)
            {
                Llamada llamadaAAtender = llamadas.poll();
                if (llamadaAAtender != null)
                {
                    System.out.println("Llamada asignada a " + empleado.getClass().getSimpleName() + " " + llamadaAAtender.getTiempoDuracion());
                    llamadaAAtender.setEstado(EstadoLlamada.ENPROCESO);
                    empleado.setLlamada(llamadaAAtender);
                }
                else
                {
                    detenerCiclo = true;
                }
            }
        }

        return detenerCiclo;
    }
    //</editor-fold>

    /**
     * Este metodo permite calcular el numero de llamada atendidas por todos los
     * empleados sin importar el tipo de empleado que es cada uno de ellos.
     *
     * @param empleados Lista con los empleados a los que se desea auditar el
     * nuemero de llamadas que atendieron.
     * @return numero de llamadas atendidas.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: 'alcularLlamadasAtendidas(LinkedList<? extends Empleado>) -> int'">
    public static int calcularLlamadasAtendidas(LinkedList<? extends Empleado> empleados)
    {
        int atendidas = 0;

        atendidas = empleados.stream().map((empleado) -> empleado.getAtendidos()).reduce(atendidas, Integer::sum);

        return atendidas;
    }
    //</editor-fold>
}
