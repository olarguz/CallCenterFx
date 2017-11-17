
package ar.com.almundo.callcenter.app;

import ar.com.almundo.callcenter.dispatcher.Dispatcher;
import ar.com.almundo.callcenter.empleado.Director;
import ar.com.almundo.callcenter.empleado.Empleado;
import ar.com.almundo.callcenter.empleado.Operador;
import ar.com.almundo.callcenter.empleado.Supervisor;
import ar.com.almundo.callcenter.llamada.Llamada;
import ar.com.almundo.callcenter.tools.GeneradorEmpleados;
import ar.com.almundo.callcenter.tools.Util;
import ar.com.almundo.callcenter.tools.UtilHTML;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class FXMLController implements Initializable
{

    //--------------------------------
    //      Objetos de la GUI
    //--------------------------------
    //<editor-fold defaultstate="collapsed" desc="'Objetos GUI - Vista'">
    @FXML
    private Label tSegundo;
    @FXML
    private Label lNumLlamadas;
    @FXML
    private Label lNumAtendidas;
    @FXML
    private Label lNumAtendidasOperarios;
    @FXML
    private Label lNumAtendidasSupervisores;
    @FXML
    private Label lNumAtendidasDirectores;
    @FXML
    private WebView viewEmpleados;
    private WebEngine engEmpleados;
    @FXML
    private WebView viewLlamadas;
    private WebEngine engLlamadas;
    //</editor-fold>
    //--------------------------------
    //      Objetos del modelo de datos
    //--------------------------------
    //<editor-fold defaultstate="collapsed" desc="Objetos de Datos - Modelo">
    private LinkedList<Empleado> empleados;
    private Dispatcher dispatcher;
    private int segundo;
    //</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        segundo = 0;
        engEmpleados = viewEmpleados.getEngine();
        engLlamadas = viewLlamadas.getEngine();

        // Se crean 15 empleados de los cuales 12 son empleados, 2 supervisores y 1 director.
        empleados = GeneradorEmpleados.crear(12, 2, 1);
        // Se crea el Dispatcher y se le pasan los empleados que atenderan las llamadas entrantes.
        dispatcher = new Dispatcher(empleados);

        engEmpleados.loadContent(crearHtmlEmpleados());
        engLlamadas.loadContent(crearHtmlLlamadas());

        // Se crean los objetos de referencias para los empleados por tipo, para contar las llamadas atendidas.
        LinkedList<Operador> operarios = dispatcher.getOperarios();
        LinkedList<Supervisor> supervisores = dispatcher.getSupervisores();
        LinkedList<Director> directores = dispatcher.getDirectores();
        // Ciclo de vida del programa.
        Timeline t = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) ->
        {
            segundo++;
            tSegundo.setText("" + segundo);

            // Las llamadas nuevas se crean con una probabilidad del 20%
//            dispatcher.recibirLlamadasConRestriccion(10, 20);
            dispatcher.recibirLlamadasSinRestriccion(10, 10);
            dispatcher.dispatchCall();

            // Se muestra el estado de los empleados
            engEmpleados.loadContent(crearHtmlEmpleados());
            // Se muestra el estado de las llamadas en espera, si las hay.
            engLlamadas.loadContent(crearHtmlLlamadas());
            // Se calcula el numero de llamadas que atiende cada grupo de empleados
            int nAtOp = Util.calcularLlamadasAtendidas(operarios);
            int nAtSup = Util.calcularLlamadasAtendidas(supervisores);
            int nAtDir = Util.calcularLlamadasAtendidas(directores);
            lNumAtendidas.setText("" + (nAtOp + nAtSup + nAtDir));
            lNumAtendidasOperarios.setText("" + nAtOp);
            lNumAtendidasSupervisores.setText("" + nAtSup);
            lNumAtendidasDirectores.setText("" + nAtDir);
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();

    }

    /**
     * Metodo que permite crear una tabla con la información clasificada de los
     * empleados segun su estado
     *
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: crearHtmlEmpleados() -> String">
    private String crearHtmlEmpleados()
    {
        StringBuilder html = new StringBuilder("<html>");

        html.append(UtilHTML.empleadosHeader());

        html.append("<body>");
        html.append("<table width=\"100%\">");
        empleados.forEach((empleado) ->
        {
            html.append(empleado.toHtml());
        });
        html.append("</table>");
        html.append("</body>");

        html.append("</html>");
        return html.toString();
    }
    //</editor-fold>

    /**
     * Metodo que permite ver las llamadas en la cola de espera.
     *
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="Metodo :: crearHtmlLlamadas() -> String">
    private String crearHtmlLlamadas()
    {
        Queue<Llamada> llamadas = dispatcher.getLlamadas();
        int numLlamadas = llamadas.size();
        lNumLlamadas.setText("" + numLlamadas);
        StringBuilder html = new StringBuilder("<html>");

        html.append(UtilHTML.llamadasHeader());

        html.append("<body>");
        html.append("<table width=\"").append(10 * numLlamadas).append("% \">");
        html.append("<tr>");
        llamadas.forEach((llamada) ->
        {
            html.append(llamada.toHtml());
        });
        html.append("</tr>");
        html.append("</table>");
        html.append("</body>");

        html.append("</html>");
        return html.toString();
    }
    //</editor-fold>
}
