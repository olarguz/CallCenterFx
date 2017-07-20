
package ar.com.almundo.callcenter.app;

import ar.com.almundo.callcenter.dispatcher.Dispatcher;
import ar.com.almundo.callcenter.empleado.Empleado;
import ar.com.almundo.callcenter.llamada.Llamada;
import ar.com.almundo.callcenter.tools.GeneradorEmpleados;
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

    @FXML
    private Label tSegundo;
    @FXML
    private Label lNumLlamadas;
    @FXML
    private WebView viewEmpleados;
    private WebEngine engEmpleados;
    @FXML
    private WebView viewLlamadas;
    private WebEngine engLlamadas;
    private LinkedList<Empleado> empleados;
    private Dispatcher dispatcher;
    private int segundo;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        segundo = 0;
        engEmpleados = viewEmpleados.getEngine();
        engLlamadas = viewLlamadas.getEngine();
        
        // Se crean 15 empleados de los cuales 10 son empleados, 4 supervisores y 1 director.
        empleados = GeneradorEmpleados.crear(10, 4, 1);
        dispatcher = new Dispatcher(empleados);

        engEmpleados.loadContent(crearHtmlEmpleados());
        engLlamadas.loadContent(crearHtmlLlamadas());

        Timeline t = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) ->
        {
            segundo++;
            tSegundo.setText("" + segundo);

            // Las llamadas nuevas se crean con una probabilidad del 20%
//            dispatcher.recibirLlamadasConRestriccion(10, 20);
            dispatcher.recibirLlamadasSinRestriccion(10, 20);
            dispatcher.dispatchCall();

            engEmpleados.loadContent(crearHtmlEmpleados());
            engLlamadas.loadContent(crearHtmlLlamadas());
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();

    }

    private String crearHtmlEmpleados()
    {
        StringBuilder html = new StringBuilder("<html>");

        html.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
        html.append("<style>");
        html.append("td {background-color: #aaaaaa; font-family: Arial, Helvetica, san-serif;}");
        html.append(".operadorlibre {background-color: #00ff80; font-family: Arial, Helvetica, san-serif;}");
        html.append(".operadorocupado {background-color: #007180; font-family: Arial, Helvetica, san-serif;}");
        html.append(".supervisorlibre {background-color: #46A3ff; font-family: Arial, Helvetica, san-serif;}");
        html.append(".supervisorocupado {background-color: #004993; font-family: Arial, Helvetica, san-serif;}");
        html.append(".directorlibre {background-color: #C46DF8; font-family: Arial, Helvetica, san-serif;}");
        html.append(".directorocupado {background-color: #600794; font-family: Arial, Helvetica, san-serif;}");
        html.append("</style>");
        html.append("</head>");

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

    private String crearHtmlLlamadas()
    {
        Queue<Llamada> llamadas = dispatcher.getLlamadas();
        int numLlamadas = llamadas.size();
        lNumLlamadas.setText(""+numLlamadas);
        StringBuilder html = new StringBuilder("<html>");

        html.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
        html.append("<style>");
        html.append("td {background-color: #0080ff; font-family: Arial, Helvetica, san-serif;}");
        html.append("</style>");
        html.append("</head>");

        html.append("<body>");
        html.append("<table width=\"").append(10*numLlamadas).append("% \">");
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
}
