
package ar.com.almundo.callcenter.dispatcher;

import ar.com.almundo.callcenter.empleado.Director;
import ar.com.almundo.callcenter.empleado.Empleado;
import ar.com.almundo.callcenter.empleado.Operador;
import ar.com.almundo.callcenter.empleado.Supervisor;
import ar.com.almundo.callcenter.llamada.EstadoLlamada;
import ar.com.almundo.callcenter.llamada.Llamada;
import ar.com.almundo.callcenter.tools.GeneradorEmpleados;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olmedo
 */
public class DispatcherTest
{

    public DispatcherTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of recibirLlamadasConRestriccion method, of class Dispatcher.
     */
    @Test
    public void testRecibirLlamadasConRestriccion()
    {
        System.out.println("recibirLlamadasConRestriccion");
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(20, 100);
        int numLlamadasEsperaAntes = instance.getLlamadas().size();
        instance.dispatchCall();
        int numLlamadasEsperaDespues = instance.getLlamadas().size();

        System.out.println("Llamadas en espera " + numLlamadasEsperaAntes + " " + numLlamadasEsperaDespues);
        assertTrue(numLlamadasEsperaAntes == 10 && numLlamadasEsperaDespues == 0);
    }

    /**
     * Test of recibirLlamadasSinRestriccion method, of class Dispatcher.
     */
    @Test
    public void testRecibirLlamadasSinRestriccion()
    {
        System.out.println("recibirLlamadasSinRestriccion");
        int expResult = 20;
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasSinRestriccion(expResult, 100);
        int numLlamadasEspera = instance.getLlamadas().size();

        assertEquals(expResult, numLlamadasEspera);
    }

    /**
     * Test of dispatchCall method, of class Dispatcher.
     */
    @Test
    public void testDispatchCall()
    {
        System.out.println("dispatchCall");
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(10, 100);
        instance.dispatchCall();
        boolean opeLibres = instance.hayEmpleadosLibres(Operador.class);
        boolean supLibres = instance.hayEmpleadosLibres(Supervisor.class);
        boolean dirLibres = instance.hayEmpleadosLibres(Director.class);

        assertTrue(!opeLibres && !supLibres && !dirLibres);
    }

    /**
     * Test of hayOperariosLibres method, of class Dispatcher.
     */
    @Test
    public void testHayOperariosLibres()
    {
        System.out.println("hayOperariosLibres");
        boolean expResult = false;
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(10, 100);
        instance.dispatchCall();
        boolean opeLibres = instance.hayEmpleadosLibres(Operador.class);

        assertEquals(expResult, opeLibres);
    }

    /**
     * Test of haySupervisoresLibres method, of class Dispatcher.
     */
    @Test
    public void testHaySupervisoresLibres()
    {
        System.out.println("haySupervisoresLibres");
        boolean expResult = false;
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(10, 100);
        instance.dispatchCall();
        boolean supLibres = instance.hayEmpleadosLibres(Supervisor.class);

        assertEquals(expResult, supLibres);
    }

    /**
     * Test of hayDirectoresLibres method, of class Dispatcher.
     */
    @Test
    public void testHayDirectoresLibres()
    {
        System.out.println("hayDirectoresLibres");
        boolean expResult = false;
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(10, 100);
        instance.dispatchCall();
        boolean dirLibres = instance.hayEmpleadosLibres(Director.class);

        assertEquals(expResult, dirLibres);
    }

    /**
     * Test of llamadasEnEspera method, of class Dispatcher.
     */
    @Test
    public void testLlamadasEnEspera()
    {
        System.out.println("getLlamadasEnEspera");
        int expResult = 10;
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(10, 100);
        Queue<Llamada> llamadas = instance.getLlamadas();
        int contadorLlamadasEspera = 0;
        contadorLlamadasEspera = llamadas.stream().filter((llamada) -> ( llamada.getEstado() == EstadoLlamada.ENESPERA)).map((_item) -> 1).reduce(contadorLlamadasEspera, Integer::sum);

        assertEquals(expResult, contadorLlamadasEspera);
    }

    /**
     * Test of getLlamadas method, of class Dispatcher.
     */
    @Test
    public void testGetLlamadas()
    {
        System.out.println("getLlamadas");
        int expResult = 10;
        LinkedList<Empleado> empleados = GeneradorEmpleados.crear(5, 3, 2);
        Dispatcher instance = new Dispatcher(empleados);
        instance.recibirLlamadasConRestriccion(10, 100);
        Queue<Llamada> llamadas = instance.getLlamadas();
        int numLlamadas = llamadas.size();

        assertEquals(expResult, numLlamadas);
    }
}
