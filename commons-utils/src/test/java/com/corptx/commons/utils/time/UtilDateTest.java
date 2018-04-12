/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.time;

import com.corptx.commons.utils.time.UtilDate.TypePeriodicidad;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author william
 */
public class UtilDateTest {

    public UtilDateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of DiferenciaFechasEnDias method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testDiferenciaFechasEnDias() throws Exception {
        System.out.println("DiferenciaFechasEnDias");
        Calendar arg = null;
        Calendar arg1 = null;
        int diasMes = 0;
        int expResult = 0;
        int result = UtilDate.DiferenciaFechasEnDias(arg, arg1, diasMes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of StringToCalendar method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testStringToCalendar() {
        System.out.println("StringToCalendar");
        String strDate = "";
        String format = "";
        Calendar expResult = null;
        Calendar result = UtilDate.StringToCalendar(strDate, format);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareToDate method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCompareToDate_3args_1() {
        System.out.println("compareToDate");
        Calendar dateOrg = null;
        Calendar dateDes = null;
        int operator = 0;
        boolean expResult = false;
        boolean result = UtilDate.compareToDate(dateOrg, dateDes, operator);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareToDate method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCompareToDate_3args_2() {
        System.out.println("compareToDate");
        Date dateOrg = null;
        Date dateDes = null;
        int operator = 0;
        boolean expResult = false;
        boolean result = UtilDate.compareToDate(dateOrg, dateDes, operator);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareToDateWhioutHours method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCompareToDateWhioutHours_3args_1() {
        System.out.println("compareToDateWhioutHours");
        Calendar dateOrg = null;
        Calendar dateDes = null;
        int operator = 0;
        boolean expResult = false;
        boolean result = UtilDate.compareToDateWhioutHours(dateOrg, dateDes, operator);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareToDateWhioutHours method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCompareToDateWhioutHours_3args_2() {
        System.out.println("compareToDateWhioutHours");
        Date dateOrg = null;
        Date dateDes = null;
        int operator = 0;
        boolean expResult = false;
        boolean result = UtilDate.compareToDateWhioutHours(dateOrg, dateDes, operator);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of DiferenciaFechasDiasCalendario method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testDiferenciaFechasDiasCalendario_Calendar_Calendar() {
        System.out.println("DiferenciaFechasDiasCalendario");
        Calendar arg = null;
        Calendar arg1 = null;
        int expResult = 0;
        int result = UtilDate.DiferenciaFechasDiasCalendario(arg, arg1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of DiferenciaFechasDiasCalendario method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testDiferenciaFechasDiasCalendario_Date_Date() {
        System.out.println("DiferenciaFechasDiasCalendario");
        Date dateIni = null;
        Date dateFin = null;
        int expResult = 0;
        int result = UtilDate.DiferenciaFechasDiasCalendario(dateIni, dateFin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDateDiff method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetDateDiff() {
        System.out.println("getDateDiff");
        int field = 0;
        Date startDate = null;
        Date endDate = null;
        int expResult = 0;
        int result = UtilDate.getDateDiff(field, startDate, endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentYearAsString method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetCurrentYearAsString() {
        System.out.println("getCurrentYearAsString");
        String expResult = "";
        String result = UtilDate.getCurrentYearAsString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sumarDiasHabiles method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testSumarDiasHabiles_4args_1() {
        System.out.println("sumarDiasHabiles");
        String strFormato = "";
        List<Calendar> listaFestivos = null;
        Date dateFecha = null;
        int cntDias = 0;
        Date expResult = null;
        Date result = UtilDate.sumarDiasHabiles(strFormato, listaFestivos, dateFecha, cntDias);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sumarDiasHabiles method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testSumarDiasHabiles_4args_2() {
        System.out.println("sumarDiasHabiles");
        String strFormato = "";
        List<Calendar> listaFestivos = null;
        Calendar calFecha = null;
        int cntDias = 0;
        String expResult = "";
        String result = UtilDate.sumarDiasHabiles(strFormato, listaFestivos, calFecha, cntDias);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of porcentajeInteresMoraByTrimestre method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testPorcentajeInteresMoraByTrimestre() {
        System.out.println("porcentajeInteresMoraByTrimestre");
        Calendar cFecha = null;
        List lista = null;
        Double expResult = null;
        Double result = UtilDate.porcentajeInteresMoraByTrimestre(cFecha, lista);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of porcentajeInteresMoraByDiasPeriodo method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testPorcentajeInteresMoraByDiasPeriodo() {
        System.out.println("porcentajeInteresMoraByDiasPeriodo");
        Calendar cFecha = null;
        List lista = null;
        String[] expResult = null;
        String[] result = UtilDate.porcentajeInteresMoraByDiasPeriodo(cFecha, lista);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trimestreOfYear method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testTrimestreOfYear() {
        System.out.println("trimestreOfYear");
        Calendar calendario = null;
        Integer expResult = null;
        Integer result = UtilDate.trimestreOfYear(calendario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareToYears method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCompareToYears() {
        System.out.println("compareToYears");
        String strYearIni = "";
        String strYearFin = "";
        int operator = 0;
        boolean expResult = false;
        boolean result = UtilDate.compareToYears(strYearIni, strYearFin, operator);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculaInteresMora method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCalculaInteresMora_5args_1() {
        System.out.println("calculaInteresMora");
        Calendar cFecha = null;
        int diasMora = 0;
        List listaInteres = null;
        double porcentajeActual = 0.0;
        Double totalBase = null;
        double expResult = 0.0;
        double result = UtilDate.calculaInteresMora(cFecha, diasMora, listaInteres, porcentajeActual, totalBase);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculaInteresMora method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCalculaInteresMora_5args_2() {
        System.out.println("calculaInteresMora");
        Date fecha = null;
        int diasMora = 0;
        List listaInteres = null;
        double porcentajeActual = 0.0;
        Double totalBase = null;
        double expResult = 0.0;
        double result = UtilDate.calculaInteresMora(fecha, diasMora, listaInteres, porcentajeActual, totalBase);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHoras method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetHoras() {
        System.out.println("getHoras");
        Long tiempoMilisegundos = null;
        Long expResult = null;
        Long result = UtilDate.getHoras(tiempoMilisegundos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinutos method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetMinutos() {
        System.out.println("getMinutos");
        Long tiempoMilisegundos = null;
        Long expResult = null;
        Long result = UtilDate.getMinutos(tiempoMilisegundos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSegundos method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetSegundos() {
        System.out.println("getSegundos");
        Long tiempoMilisegundos = null;
        Long expResult = null;
        Long result = UtilDate.getSegundos(tiempoMilisegundos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMilisegundos method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetMilisegundos() {
        System.out.println("getMilisegundos");
        Long tiempoMilisegundos = null;
        Long expResult = null;
        Long result = UtilDate.getMilisegundos(tiempoMilisegundos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of converterHoraToMiliseconds method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testConverterHoraToMiliseconds() {
        System.out.println("converterHoraToMiliseconds");
        Long horas = null;
        Long minutos = null;
        Long segundos = null;
        Long milisegundos = null;
        Long expResult = null;
        Long result = UtilDate.converterHoraToMiliseconds(horas, minutos, segundos, milisegundos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of converterDiasToMiliseconds method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testConverterDiasToMiliseconds() {
        System.out.println("converterDiasToMiliseconds");
        Long dias = null;
        Long horas = null;
        Long minutos = null;
        Long expResult = null;
        Long result = UtilDate.converterDiasToMiliseconds(dias, horas, minutos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ultimoDiaVigenciaActual method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testUltimoDiaVigenciaActual() {
        System.out.println("ultimoDiaVigenciaActual");
        Date expResult = null;
        Date result = UtilDate.ultimoDiaVigenciaActual();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sumarDiasCalendario method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testSumarDiasCalendario() {
        System.out.println("sumarDiasCalendario");
        Date fechaInicial = null;
        int cantidadDiasCalendario = 0;
        Date expResult = null;
        Date result = UtilDate.sumarDiasCalendario(fechaInicial, cantidadDiasCalendario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ultimoDiaHabilDelMes method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testUltimoDiaHabilDelMes() {
        System.out.println("ultimoDiaHabilDelMes");
        List<Calendar> listaFestivos = null;
        Date fechaInicial = null;
        Date expResult = null;
        Date result = UtilDate.ultimoDiaHabilDelMes(listaFestivos, fechaInicial);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCalendarWithoutHours method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetCalendarWithoutHours_Calendar() {
        System.out.println("getCalendarWithoutHours");
        Calendar calendar = null;
        Calendar expResult = null;
        Calendar result = UtilDate.getCalendarWithoutHours(calendar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDateWithoutHours method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetDateWithoutHours() {
        System.out.println("getDateWithoutHours");
        Date date = null;
        Date expResult = null;
        Date result = UtilDate.getDateWithoutHours(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCalendarWithoutHours method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetCalendarWithoutHours_Date() {
        System.out.println("getCalendarWithoutHours");
        Date date = null;
        Calendar expResult = null;
        Calendar result = UtilDate.getCalendarWithoutHours(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ultimoDiaCalendarioMes method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testUltimoDiaCalendarioMes() {
        System.out.println("ultimoDiaCalendarioMes");
        Calendar cFechaFinMes = null;
        Date expResult = null;
        Date result = UtilDate.ultimoDiaCalendarioMes(cFechaFinMes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cicloPorPeriodicidad method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCicloPorPeriodicidad() throws Exception {
        System.out.println("cicloPorPeriodicidad");
        TypePeriodicidad periodicidad = null;
        int ciclo = 0;
        int ano = 0;
        Calendar[] expResult = null;
        Calendar[] result = UtilDate.cicloPorPeriodicidad(periodicidad, ciclo, ano);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPromedioInteresMora method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetPromedioInteresMora() {
        System.out.println("getPromedioInteresMora");
        Calendar cAux = null;
        int diasMora = 0;
        List listaInteres = null;
        double expResult = 0.0;
        double result = UtilDate.getPromedioInteresMora(cAux, diasMora, listaInteres);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of proximaVigenciaByTipoPeriodicidad method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testProximaVigenciaByTipoPeriodicidad() throws Exception {
        System.out.println("proximaVigenciaByTipoPeriodicidad");
        String vigencia = "";
        int freceunciaPeriodicidad = 0;
        String expResult = "";
        String result = UtilDate.proximaVigenciaByTipoPeriodicidad(vigencia, freceunciaPeriodicidad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of anteriorVigenciaByTipoPeriodicidad method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testAnteriorVigenciaByTipoPeriodicidad() throws Exception {
        System.out.println("anteriorVigenciaByTipoPeriodicidad");
        String vigencia = "";
        int freceunciaPeriodicidad = 0;
        String expResult = "";
        String result = UtilDate.anteriorVigenciaByTipoPeriodicidad(vigencia, freceunciaPeriodicidad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVigenciaPeriodoByDate method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testGetVigenciaPeriodoByDate() throws Exception {
        System.out.println("getVigenciaPeriodoByDate");
        Calendar fecha = null;
        int frecuenciaPeriodicidad = 0;
        String expResult = "";
        String result = UtilDate.getVigenciaPeriodoByDate(fecha, frecuenciaPeriodicidad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActualHourMinuteSecondToDate method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testSetActualHourMinuteSecondToDate() throws Exception {
        System.out.println("setActualHourMinuteSecondToDate");
        Date originalDate = null;
        Date expResult = null;
        Date result = UtilDate.setActualHourMinuteSecondToDate(originalDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculaInteresMora method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testCalculaInteresMora_5args_3() throws Exception {


        System.out.println("calculaInteresMora");

        Calendar cFecha = Calendar.getInstance();

        int diasMora = 483;

        List<String[]> listaInteres = new ArrayList<String[]>();
        listaInteres.add(new String[]{"28", "01/04/2012", "30/06/2012", "0,0735465"});
        listaInteres.add(new String[]{"27", "01/01/2012", "31/03/2012", "0,0716533"});
        listaInteres.add(new String[]{"26", "01/10/2011", "31/12/2011", "0,069970"});
        listaInteres.add(new String[]{"25", "01/07/2011", "30/09/2011", "0,067538"});
        listaInteres.add(new String[]{"24", "01/04/2011", "30/06/2011", "0,06451074"});
        listaInteres.add(new String[]{"23", "01/01/2011", "31/03/2011", "0,05765565"});
        listaInteres.add(new String[]{"22", "01/10/2010", "31/12/2010", "0,05295108"});
        listaInteres.add(new String[]{"21", "01/07/2010", "30/09/2010", "0,05541422"});
        listaInteres.add(new String[]{"20", "01/04/2010", "30/06/2010", "0,056654"});
        listaInteres.add(new String[]{"19", "01/01/2010", "31/03/2010", "0,05941586"});
        listaInteres.add(new String[]{"18", "01/10/2009", "31/12/2009", "0,063164"});
        listaInteres.add(new String[]{"17", "26/02/2007", "31/03/2007", "0,051671"});
        listaInteres.add(new String[]{"16", "01/01/2007", "25/02/2007", "0,076279"});
        listaInteres.add(new String[]{"15", "01/10/2006", "31/12/2006", "0,055862"});
        listaInteres.add(new String[]{"14", "01/09/2006", "30/09/2006", "0,055795"});
        listaInteres.add(new String[]{"13", "01/08/2006", "31/08/2006", "0,055683"});
        listaInteres.add(new String[]{"12", "29/07/2006", "31/07/2006", "0,055884"});
        listaInteres.add(new String[]{"11", "01/01/1900", "28/07/2006", "0,056521"});
        listaInteres.add(new String[]{"10", "01/07/2009", "30/09/2009", "0,067613"});
        listaInteres.add(new String[]{"9", "01/04/2009", "30/06/2009", "0,072791"});
        listaInteres.add(new String[]{"8", "01/01/2009", "31/03/2009", "0,073400"});
        listaInteres.add(new String[]{"7", "01/10/2008", "31/12/2008", "0,075114"});
        listaInteres.add(new String[]{"6", "01/07/2008", "30/09/2008", "0,076653"});
        listaInteres.add(new String[]{"5", "01/04/2008", "30/06/2008", "0,077914"});
        listaInteres.add(new String[]{"4", "01/01/2008", "31/03/2008", "0,077646"});
        listaInteres.add(new String[]{"3", "01/10/2007", "31/12/2007", "0,075864"});
        listaInteres.add(new String[]{"2", "01/07/2007", "30/09/2007", "0,068746"});
        listaInteres.add(new String[]{"1", "01/04/2007", "30/06/2007", "0,061439"});

        double porcentajeActual = 0.0735465;

        Map<Integer, Double[]> mapBaseImpuesto = new HashMap<Integer, Double[]>();
        mapBaseImpuesto.put(17, new Double[]{9000.00, null});
        mapBaseImpuesto.put(25, new Double[]{3000.00, null});
        mapBaseImpuesto.put(28, new Double[]{0.0, null});


        UtilDate.calculaInteresMora(cFecha, diasMora, listaInteres, porcentajeActual, mapBaseImpuesto);

        for (Map.Entry<Integer, Double[]> entry : mapBaseImpuesto.entrySet()) {

            System.out.println(entry.getKey() + ": Double[0] = " + entry.getValue()[0] + ": Double[1] = " + entry.getValue()[1]);

        }

        fail("The test case is a prototype.");
    }

    /**
     * Test of calcularCantidadDiasPorPeriodoInteresMora method, of class
     * UtilDate.
     */
    @Test
    @Ignore
    public void testCalcularCantidadDiasPorPeriodoInteresMora() {
        System.out.println("calcularCantidadDiasPorPeriodoInteresMora");
        Calendar cFecha = null;
        List<String[]> listaInteres = null;
        int diasMora = 0;
        Object[] expResult = null;
        Object[] result = UtilDate.calcularCantidadDiasPorPeriodoInteresMora(cFecha, listaInteres, diasMora);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class UtilDate.
     */
    @Test
    @Ignore
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        UtilDate.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
