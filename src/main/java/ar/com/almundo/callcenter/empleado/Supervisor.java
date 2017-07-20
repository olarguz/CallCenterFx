
package ar.com.almundo.callcenter.empleado;

/**
 *
 * @author Olmedo
 */
public class Supervisor extends Empleado
{

    @Override
    public String toHtml()
    {
        StringBuilder html = new StringBuilder("<tr>");
        
        String css_class = " class=\"supervisorlibre\"";
        String estado = "Libre";
        if (this.darEstado() == EstadoEmpleado.OCUPADO)
        {
            css_class = " class=\"supervisorocupado\"";
            estado = "T. : " + getTiempoRestante() + " s.";
        }

        html.append("<td class=\"supervisor\">").append(this.getClass().getSimpleName()).append("</td>");
        html.append("<td").append(css_class).append(">");
        html.append(estado);

        html.append("</td>");
        html.append("</tr>");
        
        return html.toString();
    }
    
}
