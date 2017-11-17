
package ar.com.almundo.callcenter.llamada;

/**
 *
 * @author Olmedo
 */
public class Llamada
{

    private int tiempoDuracion;
    private EstadoLlamada estado;

    public Llamada(int tiempoDuracion, EstadoLlamada estado)
    {
        this.tiempoDuracion = tiempoDuracion;
        this.estado = estado;
    }

    /**
     * Get the value of estado
     *
     * @return the value of estado
     */
    public EstadoLlamada getEstado()
    {
        return estado;
    }

    /**
     * Set the value of estado
     *
     * @param estado new value of estado
     */
    public void setEstado(EstadoLlamada estado)
    {
        this.estado = estado;
    }

    /**
     * Get the value of tiempoDuracion
     *
     * @return the value of tiempoDuracion
     */
    public int getTiempoDuracion()
    {
        return tiempoDuracion;
    }

    /**
     * Set the value of tiempoDuracion
     *
     * @param tiempoDuracion new value of tiempoDuracion
     */
    public void setTiempoDuracion(int tiempoDuracion)
    {
        this.tiempoDuracion = tiempoDuracion;
    }

    /**
     * Este metodo convierte los valores de un objeto en una celda de una tabla
     * <code>html</code>.
     *
     * @return
     */
    public String toHtml()
    {
        StringBuilder html = new StringBuilder();

        html.append("<td>").append("T. ").append(tiempoDuracion).append("</td>");

        return html.toString();
    }
}
