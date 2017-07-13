
package ar.com.almundo.callcenter.empleado;

/**
 *
 * @author Olmedo
 */
public class Director extends Empleado
{

    @Override
    public String toHtml()
    {
        StringBuilder html = new StringBuilder("<tr>");
        
        String css_class = " class=\"directorlibre\"";
        String estado = "Libre";
        if (this.darEstado() == EstadoEmpleado.OCUPADO)
        {
            css_class = " class=\"directorocupado\"";
            estado = "T. : " + getTiempoRestante() + " s.";
        }

        html.append("<td>").append(this.getClass().getSimpleName()).append("</td>");
        html.append("<td").append(css_class).append(">");
        html.append(estado);

        html.append("</td>");
        html.append("</tr>");
        
        return html.toString();
    }
    
}
