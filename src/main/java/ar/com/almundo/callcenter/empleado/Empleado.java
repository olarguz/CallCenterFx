
package ar.com.almundo.callcenter.empleado;

import ar.com.almundo.callcenter.llamada.EstadoLlamada;
import ar.com.almundo.callcenter.llamada.Llamada;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olmedo
 */
public abstract class Empleado extends Thread
{

    private Llamada llamada;
    private Llamada llamadaFinalizada;
    private int tiempoRestante;
    private int atendidos;

    public Empleado()
    {
        llamada = null;
        tiempoRestante = 0;
        llamadaFinalizada = null;
        atendidos = 0;
    }

    /**
     * Get the value of atendidos
     *
     * @return the value of atendidos
     */
    public int getAtendidos()
    {
        return atendidos;
    }

    /**
     * Set the value of atendidos
     *
     * @param atendidos new value of atendidos
     */
    public void setAtendidos(int atendidos)
    {
        this.atendidos = atendidos;
    }

    /**
     * Get the value of tiempoRestante
     *
     * @return the value of tiempoRestante
     */
    public int getTiempoRestante()
    {
        return tiempoRestante;
    }

    /**
     * Set the value of tiempoRestante
     *
     * @param tiempoRestante new value of tiempoRestante
     */
    public void setTiempoRestante(int tiempoRestante)
    {
        this.tiempoRestante = tiempoRestante;
    }

    /**
     * Get the value of llamada
     *
     * @return the value of llamada
     */
    public Llamada getLlamada()
    {
        return llamada;
    }

    /**
     * Set the value of llamada.
     *
     * Permite asignar una llamada, cambiar el estado del empleado y asignar el
     * tiempo de atencion para la nueva llamada.
     *
     * @param llamada new value of llamada
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: setLlamada(Llamada)">
    public void setLlamada(Llamada llamada)
    {
        this.llamada = llamada;
        tiempoRestante = llamada.getTiempoDuracion();
    }
    //</editor-fold>

    /**
     * Permite conocer el estado de un Empleado, si tiene una llamada asignada
     * el estado sera OCUPADO, en caso contrario el estado sera LIBRE.
     *
     * @return Estado del empleado.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: darEstado()">
    public EstadoEmpleado darEstado()
    {
        return llamada == null ? EstadoEmpleado.LIBRE : EstadoEmpleado.OCUPADO;
    }
    //</editor-fold>

    /**
     * Este metodo permite actualizar el estado de la llamada y el empleado con
     * esta.
     *
     * Cada vez que se invoca el tiempo de atencion disminuye, cuando el tiempo
     * de atencion se hace 0, la llamada cambia su estado a TERMINADO, se libera
     * el atributo de llamada y se retorna la llamada que se va a finalizar.
     *
     * En caso que el tiempo de atencion se diferente a 0, no hay llamada lista
     * para ser terminada. El metodo retorna null.
     *
     * Se incrementa la variable atendidos cada vez que se termina de procesar
     * una llamada.
     *
     * @return Llamada finalizada o null.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: actualizarLlamada() -> Llamada">
    public Llamada actualizarLlamada()
    {
        Llamada llamadaTerminada = null;

        tiempoRestante--;
        if (tiempoRestante == 0)
        {
            llamada.setEstado(EstadoLlamada.TERMINADA);
            llamadaTerminada = llamada;
            llamada = null;
            atendidos++;
        }

        return llamadaTerminada;
    }
    //</editor-fold>

    /**
     * Este metodo permite retirar una llamada finalizada, usando las referncias para 
     * librear la variable llamadaFinalizada.
     * 
     * @return La llamada que ya termino.
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: 'getLlamadaFinalizada() -> Llamada'">
    public Llamada getLlamadaFinalizada()
    {
        Llamada aux = llamadaFinalizada;
        llamadaFinalizada = null;
        return aux;
    }
    //</editor-fold>

    /**
     * Metodo abstracto para presentar un empleado en formato html.
     *
     * @return
     */
    public abstract String toHtml();

    @Override
    public void run()
    {
        super.run();
        while (true)
        {
            try
            {
                sleep(1000);
                actualizarLlamada();
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + " " + darEstado();
    }
}
