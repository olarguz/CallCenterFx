
package ar.com.almundo.callcenter.tools;

/**
 *
 * @author Olmedo
 */
public class UtilHTML
{

    public static StringBuilder empleadosHeader()
    {
        StringBuilder header = new StringBuilder();

        header.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
        header.append("<style>");
        header.append("td {background-color: #cccccc; font-family: Arial, Helvetica, san-serif;}");
        header.append(".operadorlibre {background-color: #00ff80; font-family: Arial, Helvetica, san-serif;}");
        header.append(".operadorocupado {background-color: #007180; font-family: Arial, Helvetica, san-serif;}");
        header.append(".supervisor {background-color: #aaaaaa; font-family: Arial, Helvetica, san-serif;}");
        header.append(".supervisorlibre {background-color: #46A3ff; font-family: Arial, Helvetica, san-serif;}");
        header.append(".supervisorocupado {background-color: #004993; font-family: Arial, Helvetica, san-serif;}");
        header.append(".director {background-color: #888888; font-family: Arial, Helvetica, san-serif;}");
        header.append(".directorlibre {background-color: #C46DF8; font-family: Arial, Helvetica, san-serif;}");
        header.append(".directorocupado {background-color: #600794; font-family: Arial, Helvetica, san-serif;}");
        header.append("</style>");
        header.append("</head>");

        return header;
    }

    public static StringBuilder llamadasHeader()
    {
        StringBuilder header = new StringBuilder();

        header.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
        header.append("<style>");
        header.append("td {background-color: #0080ff; font-family: Arial, Helvetica, san-serif;}");
        header.append("</style>");
        header.append("</head>");

        return header;
    }
}
