package ar.com.almundo.callcenter.tools;

import ar.com.almundo.callcenter.empleado.Director;
import ar.com.almundo.callcenter.empleado.Empleado;
import ar.com.almundo.callcenter.empleado.Operador;
import ar.com.almundo.callcenter.empleado.Supervisor;
import java.util.LinkedList;

/**
 *
 * @author Olmedo
 */
public class GeneradorEmpleados
{

    //<editor-fold defaultstate="collapsed" desc="Metodo :: crear(int, int, int) -> LinkedList<Empleado>">
    public static LinkedList<Empleado> crear(int numOperarios, int numSupervisores, int numDirectores)
    {
        LinkedList<Empleado> l = new LinkedList<>();
        
        for (int i = 0; i < numOperarios; i++)
        {
            l.add(new Operador());
        }
        for (int i = 0; i < numSupervisores; i++)
        {
            l.add(new Supervisor());
        }
        for (int i = 0; i < numDirectores; i++)
        {
            l.add(new Director());
        }
        
        return l;
    }
    //</editor-fold>

}
