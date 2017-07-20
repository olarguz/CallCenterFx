
package ar.com.almundo.callcenter.dispatcher;

import ar.com.almundo.callcenter.empleado.Director;
import ar.com.almundo.callcenter.empleado.Empleado;
import ar.com.almundo.callcenter.empleado.Operador;
import ar.com.almundo.callcenter.empleado.Supervisor;
import ar.com.almundo.callcenter.llamada.EstadoLlamada;
import ar.com.almundo.callcenter.tools.GeneradorLlamadas;
import ar.com.almundo.callcenter.llamada.Llamada;
import ar.com.almundo.callcenter.tools.Util;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Olmedo
 */
public class Dispatcher
{

    private LinkedList<Empleado> empleados;
    private LinkedList<Operador> operarios;
    private LinkedList<Supervisor> supervisores;
    private LinkedList<Director> directores;
    private Queue<Llamada> llamadas;

    /**
     * Constructor de la clase Dispatcher.
     *
     * Recibe como parametro una lista con todos los empleados que pueden
     * atender llamadas.
     *
     * Los empleados se clasifican en Operarios, Supervisores y Directores, esto
     * se hace para poder cumplir con la restriccion de asignacion de llamadas,
     * segun la consigna del ejercicio.
     *
     * @param empleados Listado sin clasificar, con todos los empleados que
     * pueden aterder llamadas.
     */
    public Dispatcher(LinkedList<Empleado> empleados)
    {
        this.empleados = empleados;

        operarios = (LinkedList<Operador>) Util.seleccionar(Operador.class, empleados);
        supervisores = (LinkedList<Supervisor>) Util.seleccionar(Supervisor.class, empleados);
        directores = (LinkedList<Director>) Util.seleccionar(Director.class, empleados);

        for (Empleado empleado : empleados)
        {
            empleado.start();
        }

        llamadas = new LinkedList<>();
    }

    /**
     * Este metodo permite crear un numero maximo de llamadas determinado por
     * numMax llamadas. Existe una probabilidad de creacion para cada nueva
     * llamada.
     *
     * El funcionamiento del metodo consiste en que hay un numero maximo de
     * llamadas que pueden ser creadas, cada llamada tiene una probabilidad de
     * ser creadas, por esta razon el numero de llamadas que se crea en cada
     * invocacion del metodo puede ser diferente.
     *
     * Una vez creadas las llamadas se empiezan a asignar en la cola de espera,
     * la cual tiene una restricion de cantidad de llamadas que pueden ser
     * atendidas, el tamano es 10, una vez se alcanza ese numero de llamadas en
     * la lista las demas se desechan, pues la capacidad de almacenamiento es
     * limitada.
     *
     * @param numMax Cantidad de llamadas maxima que pueden ser creadas.
     * @param probabilidad Probabilidad de creacion de cada llamada.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: recibirLlamadasConRestriccion(int, int)">
    public void recibirLlamadasConRestriccion(int numMax, int probabilidad)
    {
        LinkedList<Llamada> llamadasNuevas = GeneradorLlamadas.crearLlamadas(numMax, probabilidad);
        llamadasNuevas.forEach((llamada) ->
        {
            if (llamadas.size() < 10)
            {
                System.out.println("Llamada en espera");
                llamada.setEstado(EstadoLlamada.ENESPERA);
                llamadas.add(llamada);
            }
            else
            {
                System.out.println("LLamada no atendida");
                llamada.setEstado(EstadoLlamada.TERMINADA);
            }
        });
    }
    //</editor-fold>

    /**
     * Este metodo permite crear un numero maximo de llamadas determinado por
     * numMax llamadas. Existe una probabilidad de creacion para cada nueva
     * llamada.
     *
     * El funcionamiento del metodo consiste en que hay un numero maximo de
     * llamadas que pueden ser creadas, cada llamada tiene una probabilidad de
     * ser creadas, por esta razon el numero de llamadas que se crea en cada
     * invocacion del metodo puede ser diferente.
     *
     * Una vez creadas las llamadas se empiezan a asignar en la cola de espera,
     * sin nunguna restricion de cantidad de llamadas a dejar en espera.
     *
     * @param numMax Cantidad de llamadas maxima que pueden ser creadas.
     * @param probabilidad Probabilidad de creacion de cada llamada.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: recibirLlamadasSinRestriccion(int, int)">
    public void recibirLlamadasSinRestriccion(int numMax, int probabilidad)
    {
        LinkedList<Llamada> llamadasNuevas = GeneradorLlamadas.crearLlamadas(numMax, probabilidad);
        llamadasNuevas.forEach((llamada) ->
        {
            System.out.println("Llamada en espera");
            llamada.setEstado(EstadoLlamada.ENESPERA);
            llamadas.add(llamada);
        });
    }
    //</editor-fold>

    /**
     * Este metodo permite asinar las llamadas teniendo en cuenta el orden
     * establecido en la consigna:
     *
     * "El proceso de la atención de una llamada telefónica en primera instancia
     * debe ser atendida por un operador, si no hay ninguno libre debe ser
     * atendida por un supervisor, y de no haber tampoco supervisores libres
     * debe ser atendida por un director".
     *
     * Una vez las llamadas son asignadas se ajusta el tiempo que el queda al
     * usuario para la atención de la llamada que tiene asignada en el momento.
     * Un vez el tiempo se termine, la llamada termina, el empleado es liberado
     * y queda libre para atender una llamada que este en espera. Este proceso
     * se realiza de manera concurrente.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: dispatchCall()">
    public void dispatchCall()
    {
        // Primero se asingan los operarios
        if (Util.hayLibres(operarios))
        {
            Util.asignarLlamada(operarios, llamadas);
        }
        // Solo se asinan supervisores cuando no hay operarios disponibles
        if (Util.hayLibres(supervisores))
        {
            Util.asignarLlamada(supervisores, llamadas);
        }
        // Solo se asignan directores cuando estan ocupados todos los operarios y supervisores
        if (Util.hayLibres(directores))
        {
            Util.asignarLlamada(directores, llamadas);
        }

        // Se actualizan todas las llamadas que estan asignada a los empleados
        for (Empleado empleado : empleados)
        {
            Llamada llamadaFinalizada = empleado.getLlamadaFinalizada();
            if (llamadaFinalizada != null)
            {
                System.out.println("Llamada terminada");
                llamadaFinalizada = null;
            }
        }
    }
    //</editor-fold>

    /**
     * Permite identificar los empleados dependiendo del la subclase a la que
     * pertencen, y despues se pregunta si hay alguno libre. Este metodo es
     * usado solo en la clase de prueba.
     *
     * @param t Clase de empleado por la que se quiere averiguar si hay libres.
     * @return valor booleano que indica si hay al menos un empleado libre.
     */
    public boolean hayEmpleadosLibres(Class<? extends Empleado> t)
    {
        LinkedList<? extends Empleado> e = Util.seleccionar(t, empleados);
        return Util.hayLibres(e);
    }

    public Queue<Llamada> getLlamadas()
    {
        return llamadas;
    }
}
