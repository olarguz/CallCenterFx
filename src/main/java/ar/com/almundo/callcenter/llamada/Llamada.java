
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

    public String toHtml()
    {
        StringBuilder html = new StringBuilder("<td>");
        
        html.append("T. ").append(tiempoDuracion);
        
        html.append("</td>");
        return html.toString();
    }

}
