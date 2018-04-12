package com.corptx.commons.utils.time;

import com.corptx.commons.utils.collections.UtilList;
import com.corptx.commons.utils.math.MathEvaluator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jorge Garcia
 */
public class UtilDate {

    private static Log log = LogFactory.getLog(UtilDate.class);
    public static final int GREATER_THAN = 1;
    public static final int LESS_THAN = 2;
    public static final int EQUALS = 3;
    public static final int LESS_EQUALS_THAN = 4;
    public static final int GREATER_EQUALS_THAN = 5;
    private static int mes = 12;
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    // Variables y constantes para el proceso de dias habiles
    private static final String LENGUAGE = "es";
    private static final String COUNTRY = "CO";
    // Formateadores de fechas
    /**
     * Formato "dd-MM-yyyy" => new SimpleDateFormat("dd-MM-yyyy")
     */
    public static SimpleDateFormat formatOracle = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Formato "yyyy-MM-dd" => new SimpleDateFormat("dd-MM-yyyy")
     */
    public static SimpleDateFormat formatMySQL = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Formato "dd-MMMM-yyyy" con Locale español - Colombia => new
     * SimpleDateFormat("dd-MMMM-yyyy", new java.util.Locale(LENGUAGE, COUNTRY))
     */
    public static SimpleDateFormat formatFecha_0 = new SimpleDateFormat("dd-MMMM-yyyy", new java.util.Locale(LENGUAGE, COUNTRY));
    /**
     * Formato "dd-MM-yyyy" con Locale español - Colombia => new
     * SimpleDateFormat("dd-MM-yyyy", new java.util.Locale(LENGUAGE, COUNTRY))
     */
    public static SimpleDateFormat formatFecha_1 = new SimpleDateFormat("dd-MM-yyyy", new java.util.Locale(LENGUAGE, COUNTRY));
    /**
     * Formato "dd-MMMM-yyyy hh:mm:ss" con Locale español - Colombia => new
     * SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss", new java.util.Locale(LENGUAGE,
     * COUNTRY))
     */
    public static SimpleDateFormat formatFecha_2 = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss", new java.util.Locale(LENGUAGE, COUNTRY));
    /**
     * Formato "dd/MM/yyyy hh:mm:ss" con Locale español - Colombia => new
     * SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new java.util.Locale(LENGUAGE,
     * COUNTRY))
     */
    public static SimpleDateFormat formatFecha_3 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new java.util.Locale(LENGUAGE, COUNTRY));
    /**
     * Formato "dd/MM/yyyy" con Locale español - Colombia => new
     * SimpleDateFormat("dd/MM/yyyy", new java.util.Locale(LENGUAGE, COUNTRY))
     */
    public static SimpleDateFormat formatFecha_4 = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale(LENGUAGE, COUNTRY));
    /**
     * Formato "yyyy-MM-dd'T'HH:mm:ss.SSSZ" con Locale español - Colombia => new
     * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new
     * java.util.Locale(LENGUAGE, COUNTRY))
     */
    public static SimpleDateFormat formatFecha_5 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new java.util.Locale(LENGUAGE, COUNTRY));
    /**
     * Formato formatFecha_4 => "dd/MM/yyyy" con Locale español - Colombia =>
     * new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale(LENGUAGE,
     * COUNTRY))
     */
    public static SimpleDateFormat formatoFecha_Predefinido = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale(LENGUAGE, COUNTRY));
    private static Locale _currentLocale = new Locale(LENGUAGE, COUNTRY);
    private static SimpleDateFormat dfFormatCurrent;
    private static List<Calendar> listaCalendarFestivos;
    public static TypePeriodicidad typePeriodicidad;

    public static enum TypePeriodicidad {

        MENSUAL,
        BIMENSUAL,
        TRIMESTRAL,
        CUATRIMESTRAL,
        SEMESTRAL,
    };

    public static int DiferenciaFechasEnDias(Calendar arg, Calendar arg1, int diasMes) throws Exception {
        if (diasMes < 1 && diasMes > 31) {
            throw new Exception("Parametro diasMes no valido ");
        }
        int ano = diasMes * mes;
        int diaInicial = arg.get(Calendar.DATE);
        int mesInicial = arg.get(Calendar.MONTH) + 1;
        int anoInicial = arg.get(Calendar.YEAR);
        int diaFinal = arg1.get(Calendar.DATE);
        int mesFinal = arg1.get(Calendar.MONTH) + 1;
        int anoFinal = arg1.get(Calendar.YEAR);
        if (diaInicial > 30) {
            diaInicial = 30;
        }
        return ((mes - mesInicial) * diasMes + (diasMes - diaInicial) + (((anoFinal - 1) - anoInicial) * ano) + ((mesFinal - 1) * diasMes) + diaFinal);

    }

    /**
     * @deprecated Este metodo es cambiado por FormatDate.formatStringToCalendar
     * @param strDate
     * @param format
     * @return
     */
    public static Calendar StringToCalendar(String strDate, String format) {
        try {
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat(format);
            date = (Date) formatter.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException ex) {
            return null;


        }
    }

    /**
     * Metodo que compara dos objetos Calendar. Las operaciones soportadas son:
     * <ul> <li>Mayor que</li> <li>Menor que</li> <li>Iguales</li> </ul>
     *
     * @param dateOrg
     * @param dateDes
     * @param operator
     * @return
     */
    public static boolean compareToDate(Calendar dateOrg, Calendar dateDes, int operator) {
        boolean compare = false;
        if (dateOrg != null && dateDes != null) {
            switch (operator) {
                case 1:
                    if (dateOrg.getTimeInMillis() > dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
                case 2:
                    if (dateOrg.getTimeInMillis() < dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
                case 3:
                    if (dateOrg.getTimeInMillis() == dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
            }
        }
        return compare;
    }

    /**
     * Metodo que compara dos objetos Calendar. Las operaciones soportadas son:
     * <ul> <li>Mayor que</li> <li>Menor que</li> <li>Iguales</li> </ul>
     *
     * @param dateOrg
     * @param dateDes
     * @param operator
     * @return
     */
    public static boolean compareToDate(Date dateOrg, Date dateDes, int operator) {
        boolean compare = false;
        if (dateOrg != null && dateDes != null) {
            switch (operator) {
                case 1:
                    if (dateOrg.getTime() > dateDes.getTime()) {
                        compare = true;
                    }
                    break;
                case 2:
                    if (dateOrg.getTime() < dateDes.getTime()) {
                        compare = true;
                    }
                    break;
                case 3:
                    if (dateOrg.getTime() == dateDes.getTime()) {
                        compare = true;
                    }
                    break;
            }
        }
        return compare;
    }

    /**
     * Metodo que compara dos objetos Calendar sin tener encuenta las
     * horas,minitos, segundos, milisegundos . Las operaciones soportadas son:
     * <ul> <li>Mayor que</li> <li>Menor que</li> <li>Iguales</li> </ul>
     *
     * @param dateOrg
     * @param dateDes
     * @param operator
     * @return
     */
    public static boolean compareToDateWhioutHours(Calendar dateOrg, Calendar dateDes, int operator) {
        boolean compare = false;
        if (dateOrg != null
                && dateDes != null) {
            dateOrg.set(dateOrg.get(Calendar.YEAR), dateOrg.get(Calendar.MONTH), dateOrg.get(Calendar.DATE), 0, 0, 0);
            dateOrg.clear(Calendar.MILLISECOND);
            dateDes.set(dateDes.get(Calendar.YEAR), dateDes.get(Calendar.MONTH), dateDes.get(Calendar.DATE), 0, 0, 0);
            dateDes.clear(Calendar.MILLISECOND);
            switch (operator) {
                case 1:
                    if (dateOrg.getTimeInMillis() > dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
                case 2:
                    if (dateOrg.getTimeInMillis() < dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
                case 3:
                    if (dateOrg.getTimeInMillis() == dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
                case 4:
                    if (dateOrg.getTimeInMillis() <= dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
                case 5:
                    if (dateOrg.getTimeInMillis() >= dateDes.getTimeInMillis()) {
                        compare = true;
                    }
                    break;
            }
        }
        return compare;
    }

    /**
     * <p>Metodo que compara dos objetos Date sin tener encuenta las
     * horas,minitos, segundos, milisegundos. </p> Las operaciones soportadas
     * son: <ul> <li>Mayor que</li> <li>Menor que</li> <li>Iguales</li> </ul>
     *
     * @param dateOrg
     * @param dateDes
     * @param operator
     * @return
     */
    public static boolean compareToDateWhioutHours(Date dateOrg, Date dateDes, int operator) {
        boolean compare = false;
        if (dateOrg != null && dateDes != null) {
            // Pasando el Data a Calendar
            Calendar calDateOrg = Calendar.getInstance();
            calDateOrg.setTime(dateOrg);
            Calendar calDateDes = Calendar.getInstance();
            calDateDes.setTime(dateDes);

            compare = compareToDateWhioutHours(calDateOrg, calDateDes, operator);
        }
        return compare;
    }

    /**
     * Metodo que calcula la cantidad de dias calendario que hay entre dos
     * fechas tipo Calendar
     *
     * @param arg fecha inicial
     * @param arg1 fecha final
     * @return dias de diferencia entre arg1-arg
     */
    public static int DiferenciaFechasDiasCalendario(Calendar arg, Calendar arg1) {
//        logDebug(this,"\n\n----------------------------");
//        logDebug(this,"Entro a DiferenciaFechasDiasCalendario(Calendar arg, Calendar arg1)");

        /**
         * Tener encuenta que los meses van desde 0-11
         */
        Calendar newArg = Calendar.getInstance();
        Calendar newArg1 = Calendar.getInstance();
        boolean isArgGreaterThanArg1 = false;
        if (compareToDate(arg, arg1, GREATER_THAN)) {
            isArgGreaterThanArg1 = true;
            newArg.set(arg1.get(Calendar.YEAR), arg1.get(Calendar.MONTH), arg1.get(Calendar.DATE));
            newArg1.set(arg.get(Calendar.YEAR), arg.get(Calendar.MONTH), arg.get(Calendar.DATE));
        } else {
            newArg.set(arg.get(Calendar.YEAR), arg.get(Calendar.MONTH), arg.get(Calendar.DATE));
            newArg1.set(arg1.get(Calendar.YEAR), arg1.get(Calendar.MONTH), arg1.get(Calendar.DATE));
        }

//        logDebug(this,"newArg.getTime() = " + newArg.getTime());
//        logDebug(this,"newArg1.getTime() = " + newArg1.getTime());

        int anioInicial = newArg.get(Calendar.YEAR);
        int anioFinal = newArg1.get(Calendar.YEAR);
        int dias = 0;
        Calendar aux = Calendar.getInstance();
        int dia = 31;
        int mess = 11;
        for (int i = anioInicial; i < anioFinal; i++) {
            aux.set(i, mess, dia);
            dias += aux.get(Calendar.DAY_OF_YEAR);
        }

//        logDebug(this,"dias = " + dias);
//        logDebug(this,"newArg.get(Calendar.DAY_OF_YEAR) = " + newArg.get(Calendar.DAY_OF_YEAR));
//        logDebug(this,"newArg1.get(Calendar.DAY_OF_YEAR) = " + newArg1.get(Calendar.DAY_OF_YEAR));

        dias = (dias - newArg.get(Calendar.DAY_OF_YEAR)) + (newArg1.get(Calendar.DAY_OF_YEAR));
        if (isArgGreaterThanArg1) {
            dias *= -1;
        }
//        logDebug(this,"Saliendo de DiferenciaFechasDiasCalendario(Calendar arg, Calendar arg1)");
//        logDebug(this,"----------------------------\n\n");
        return dias;
    }

    /**
     * Metodo que calcula la cantidad de dias calendario que hay entre dos
     * fechas tipo Date
     *
     * @param dateIni
     * @param dateFin
     * @return La cantidad de dias calendario entre las dos fechas
     */
    public static int DiferenciaFechasDiasCalendario(Date dateIni, Date dateFin) {
        Calendar cDateIni = Calendar.getInstance();
        Calendar cDateFin = Calendar.getInstance();

        cDateIni.setTime(dateIni);
        cDateFin.setTime(dateFin);

        return DiferenciaFechasDiasCalendario(cDateIni, cDateFin);
    }

    /**
     * Método que calcula la cantidad de dias hábiles que hay entre dos fechas
     * de tipo Calendar
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param listaFestivos
     * @return
     */
    public static int diferenciaFechasDiasHabiles(Calendar fechaInicial, Calendar fechaFinal, List<Calendar> listaFestivos) {

        int cantidadDiasCalendarioEntreFechas = 0;
        listaCalendarFestivos = listaFestivos;
        Calendar fechaInicialTmp = Calendar.getInstance();
        fechaInicialTmp.setTime(fechaInicial.getTime());
        int anoActualFecha = fechaInicialTmp.get(Calendar.YEAR);
        int mesActualFecha = fechaInicialTmp.get(Calendar.MONTH);
        int diaActualFecha = fechaInicialTmp.get(Calendar.DATE);

        while (compareToDateWhioutHours(fechaInicialTmp, fechaFinal, LESS_THAN)) {

            if (isWorkDay(fechaInicialTmp)) {
                cantidadDiasCalendarioEntreFechas++; // Se le suma uno al contador de dias habiles encontrados
            }
            diaActualFecha++; //se le suma uno al dia para recorrerlo no importa el numero que sea en la semana
            fechaInicialTmp.set(anoActualFecha, mesActualFecha, diaActualFecha);
        }

        return cantidadDiasCalendarioEntreFechas;//numero de dias finales
    }

    /**
     * Método que calcula la cantidad de dias hábiles que hay entre dos fechas
     * de tipo Date
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param listaFestivos
     * @return
     */
    public static int diferenciaFechasDiasHabiles(Date fechaInicial, Date fechaFinal, List<Calendar> listaFestivos) {

        Calendar cFechaInicial = Calendar.getInstance();
        Calendar cFechaFinal = Calendar.getInstance();
        cFechaInicial.setTime(fechaInicial);
        cFechaFinal.setTime(fechaFinal);

        return diferenciaFechasDiasHabiles(cFechaInicial, cFechaFinal, listaFestivos);
    }

    /**
     * Calcula la diferencia de dos fechas en anios
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getDateDiffInYears(Date startDate, Date endDate) {
        return getDateDiffInMonths(startDate, endDate) / 12;
    }

    /**
     * Calcula la diferencia de dos fechas en meses
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getDateDiffInMonths(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        int startYear = -1, startMonth = -1;
        int endYear = -1, endMonth = -1;
        int months = 0;
        int factor = 1;

        if (startDate.after(endDate)) {
            factor = -1;
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        } else {
            startCal.setTime(startDate);
            endCal.setTime(endDate);
        }

        startYear = startCal.get(Calendar.YEAR);
        startMonth = startCal.get(Calendar.MONTH) + 1;
        endYear = endCal.get(Calendar.YEAR);
        endMonth = endCal.get(Calendar.MONTH) + 1;

        if (startYear == endYear) {
            months = endMonth - startMonth;
        } else {
            months = 12 - startMonth;
            months += endMonth;
            --endYear;
            if (endYear - startYear > 0) {
                months += (endYear - startYear) * 12;
            }
        }
        months *= factor;
        return months;
    }

    /**
     * calcula la diferencia entre dos fechas retorna anios o meses dependiendo
     * del parametro field
     *
     * @param field tipo de diferenci year 0 mes 1
     * @param startDate fecha inical
     * @param endDate fecha final
     * @return
     */
    public static int getDateDiff(int field, Date startDate, Date endDate) {
        if (field == YEAR) {
            return getDateDiffInYears(startDate, endDate);
        } else if (field == MONTH) {
            return getDateDiffInMonths(startDate, endDate);
        } else {
            return 0;
        }
    }

    /**
     * Meotod que retorna el anio actual como String
     *
     * @return
     */
    public static String getCurrentYearAsString() {
        Calendar hoy = Calendar.getInstance();
        return String.valueOf(hoy.get(Calendar.YEAR));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Metodos que permiten sumar dias habiles a una fecha
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Metodo que recupera la fecha habil de un Date
     *
     * @param listaCalendarFestivos Lista de Calendar con los dias festivos
     * @param dateFecha La fecha base para el calculo de la fecha habil como un
     * Date
     * @param cntDias Cantidad de dias habiles que se desean sumar
     * @return fecha en formato dd/mm/yyyy
     */
    public static Date sumarDiasHabiles(String strFormato, List<Calendar> listaFestivos, Date dateFecha, int cntDias) {
        Date newDate = null;
        String strFecha =
                sumarDiasHabiles(strFormato, listaFestivos, FormatDate.formatDateToCalendar(dateFecha), cntDias);
        if (!StringUtils.isBlank(strFecha)) {
            newDate = FormatDate.formatStringToDate(strFecha, strFormato);
        }
        return newDate;
    }

    /**
     * Metodo que recupera la fecha habil de uns String
     *
     * @param listaCalendarFestivos Lista de Calendar con los dias festivos
     * @param strFecha La fecha de entrada para calcular la fecha habil como una
     * cadena de texto con la siguente sintaxis: dd/mm/yyyy
     * @param cntDias Cantidad de dias habiles que se desean sumar
     * @return fecha en formato dd/mm/yyyy
     */
    public static String sumarDiasHabiles(String strFormato, List<Calendar> listaFestivos, Calendar calFecha, int cntDias) {

//        logDebug(this,"strFormato = " + strFormato);
//        logDebug(this,"listaFestivos.size() = " + listaFestivos.size());
//        logDebug(this,"strFecha = -" + strFecha+"-");
//        logDebug(this,"cntDias = " + cntDias);

        int cntDiasCalendario = 0;
        _currentLocale = new Locale(LENGUAGE, COUNTRY);
        dfFormatCurrent = new SimpleDateFormat(strFormato, _currentLocale);
        listaCalendarFestivos = listaFestivos;

        cntDiasCalendario = cntDiasDistancia(calFecha, cntDias);//numero de dias finales

        //Obtiene la fecha
        int iYear = calFecha.get(Calendar.YEAR);
        int iMonth = calFecha.get(Calendar.MONTH);
        int iDay = calFecha.get(Calendar.DATE);

        Calendar cldFin = Calendar.getInstance(_currentLocale);
        cldFin.setFirstDayOfWeek(Calendar.MONDAY);
        cldFin.set(iYear, iMonth, iDay);

        cldFin.add(Calendar.DATE, cntDiasCalendario);

        return dfFormatCurrent.format(cldFin.getTime());

    }

    /**
     * Metodo que cuanta la cantidad de dias calendarios de distancia entre la
     * fecha inicial y la fecha deseada con los d�as habiles. <p>Ejemplo: si la
     * fecha inicial es 06/08/2010 y los d�as enviados como parametros son 25,
     * la cantidad de d�as retornado son 38, porque hay 38 d�as calendario para
     * que la fecha inicial se le puedan sumar 25 d�as habiles</p> <p>Toma como
     * primer dia de la semana el Lunes</p> <p></p>
     *
     * @ param FechaIni	Cadena con la fecha a calcular los dias inhabiles
     * @
     * param Dias Numero de dias habiles a sumar
     * @ return una Fecha con formato dd-mm-yyyy
     */
    private static int cntDiasDistancia(Calendar calFecha, int Dias) {

        //Esta es la Fecha que se recibe
        int countd = 0;
        int contapasadas = 0;
        int iYear = calFecha.get(Calendar.YEAR);
        int iMonth = calFecha.get(Calendar.MONTH);
        int iDay = calFecha.get(Calendar.DATE);

        // Objeto Calendar para los calculos
        Calendar cldInicio = Calendar.getInstance(_currentLocale);
        cldInicio.setFirstDayOfWeek(Calendar.MONDAY);

        //hacemos el recorrido de todos los dias quitando los sabados y domingos
        while (Dias >= countd) {
//            logDebug(this,"--------------------------------------------");
            contapasadas++;
            cldInicio.set(iYear, iMonth, iDay);

            if (isWorkDay(cldInicio)) {
//                logDebug(this,"La fecha " + dfFormatCurrent.format(cldInicio.getTime()) + " es habil");
                countd++; // Se le suma uno al contador de dias habiles encontrados
            } else {
//                logDebug(this,"La fecha " + dfFormatCurrent.format(cldInicio.getTime()) + " es NO habil");
            }
            iDay++; //se le suma uno al dia para recorrerlo no importa el numero quesea en la semana
//            logDebug(this,"--------------------------------------------");
        }

        // Se resta 1 a la cantidad de dias verificados porque se cuenta el dia inicial y no se debe sumar
        return contapasadas - 1;
    }

    /**
     * <p>Valida si la fecha es un dia habil o no</p>
     *
     * @param date Objeto Calendar que contiene la fecha a validar
     * @return true si es habil y false si no
     */
    private static boolean isWorkDay(Calendar date) {
        boolean workday = true;
        int iDiaSemana = 0;

        // Verifica si es un dia festivo
        iDiaSemana = date.get(Calendar.DAY_OF_WEEK);

        if (iDiaSemana == 1 || iDiaSemana == 7) {// Valida si es Sabado o Domingo
            workday = false;
        } else if (UtilList.isNotEmpty(listaCalendarFestivos)) {// Valida si es un dia festivo
            for (Calendar calendar : listaCalendarFestivos) {
//                logDebug(this,"isWorkDay ::> fecha: " + dfFormatCurrent.format(date.getTime()) + ", festivo: " + dfFormatCurrent.format(calendar.getTime()));
                if (UtilDate.compareToDateWhioutHours(date, calendar, UtilDate.EQUALS)) {
                    workday = false;
                    break;
                }
            }
        }
        return workday;
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Recibe una fecha y una lista de interes por rango de fechas y retorna
     * le valor del interes para la fecha pasado como parametro</p>
     *
     * @param cFecha
     * @param lista Lista del porcentajes de interes
     * -->FechaInicio,FechaFin,Porcentaje
     * @return porcentajeInteresMora
     */
    public static Double porcentajeInteresMoraByTrimestre(Calendar cFecha, List lista) {
        Double porcentajeInteresMora = null;
        if ((UtilList.isNotEmpty(lista))) {
            for (int i = 0; i < lista.size(); i++) {
                String[] strings = (String[]) lista.get(i);
                Calendar fechaInicio = FormatDate.formatStringToCalendar(strings[1], FormatDate.FORMAT_SLASH_DD_MM_YYYY);
                Calendar fechaFin = FormatDate.formatStringToCalendar(strings[2], FormatDate.FORMAT_SLASH_DD_MM_YYYY);
                if (!(compareToDateWhioutHours(cFecha, fechaInicio, LESS_THAN)) && (!(compareToDateWhioutHours(cFecha, fechaFin, GREATER_THAN)))) {
                    porcentajeInteresMora = Double.parseDouble(strings[3].replace(",", "."));
                }

            }
        }
        return porcentajeInteresMora;

    }

    public static String[] porcentajeInteresMoraByDiasPeriodo(Calendar cFecha, List lista) {
        String[] interesPeriodo = null;
        if ((UtilList.isNotEmpty(lista))) {
            for (int i = 0; i < lista.size(); i++) {
                String[] strings = (String[]) lista.get(i);
                Calendar fechaInicio = FormatDate.formatStringToCalendar(strings[1], FormatDate.FORMAT_SLASH_DD_MM_YYYY);
                Calendar fechaFin = FormatDate.formatStringToCalendar(strings[2], FormatDate.FORMAT_SLASH_DD_MM_YYYY);
                if (!(compareToDateWhioutHours(cFecha, fechaInicio, LESS_THAN))
                        && (!(compareToDateWhioutHours(cFecha, fechaFin, GREATER_THAN)))) {
                    interesPeriodo = strings;
                    interesPeriodo[3] = interesPeriodo[3].replace(",", ".");
                }
            }
        }
        return interesPeriodo;
    }

    /**
     * <p>Obtiene el trimestre al cual pertenece una fecha tipo Calendar</p>
     *
     * @param fecha tipo Calendar
     * @return Integer para cada trimestre 1,2,3,4,null si error
     */
    public static Integer trimestreOfYear(Calendar calendario) {
        Integer trimestre = null;
        if (calendario != null) {
            int newMes = calendario.get(Calendar.MONTH);
            if (newMes >= 0 && newMes <= 2) {
                trimestre = new Integer(1);
            }
            if (newMes >= 3 && newMes <= 5) {
                trimestre = new Integer(2);
            }
            if (newMes >= 6 && newMes <= 8) {
                trimestre = new Integer(3);
            }
            if (newMes >= 9 && newMes <= 11) {
                trimestre = new Integer(4);
            }
        }
        return trimestre;
    }

    public static boolean compareToYears(String strYearIni, String strYearFin, int operator) {
        boolean exito = false;
        if (!StringUtils.isBlank(strYearIni)
                && !StringUtils.isBlank(strYearFin)
                && operator > 0) {

            int intYearIni = Integer.parseInt(strYearIni);
            int intYearFin = Integer.parseInt(strYearFin);
            switch (operator) {
                case UtilDate.LESS_THAN:
                    if (intYearIni < intYearFin) {
                        exito = true;
                    }
                    break;
                case UtilDate.GREATER_THAN:
                    if (intYearIni > intYearFin) {
                        exito = true;
                    }
                    break;
                case UtilDate.EQUALS:
                    if (intYearIni == intYearFin) {
                        exito = true;
                    }
                    break;
                case UtilDate.LESS_EQUALS_THAN:
                    if (intYearIni <= intYearFin) {
                        exito = true;
                    }
                    break;
                case UtilDate.GREATER_EQUALS_THAN:
                    if (intYearIni >= intYearFin) {
                        exito = true;
                    }
                    break;
            }
        }
        return exito;
    }

    /**
     * Permite calcular el valor de la mora de una cantidad de dias determinados
     * basndose en la lista de intereses de mora por trimestre.
     *
     * @param cFecha
     * @param diasMora
     * @param listaInteres
     * @param porcentajeActual este porcentaje debe estar en forma decimal
     * porcentaje%/100
     * @param totalBase
     * @return
     */
    synchronized public static double calculaInteresMora(Calendar cFecha, int diasMora, List listaInteres, double porcentajeActual, Double totalBase) {

        /**
         * Funcionamiento del cálculo de mora: En el primer for por cada día se
         * llena un map en donde se guarda un array de string para cada
         * porcentaje de mora diferente que encuentre, guardando en la primera
         * posicion la suma de los dias de mora a calcular para ese porcentaje,
         * en la segunda posición guarda el porcentaje y en la tercera guarda un
         * booleando indicando si es por interés simple o compuesto. los
         * porcentajes no encontrados de la lista de interés para un día en
         * específico se marcan como días fuera de rango (a esos días se le
         * aplica el porcentaje que llega por el método). En el for donde se
         * recorre el map de porcentajes se calcula el interés con las formulas
         * dependiendo de si es simple o compuesto; los días fuera de rango se
         * calculan por interés simple.
         * --------------------------------------------------------------------
         * Las formulas: (porcentaje en forma decimal: porcentaje/100) --------
         * ((((1 + porcentaje)^dias) - 1) * BaseParaMora) --> interes compuesto.
         * ((porcentaje * dias) * BaseParaMora) --> interes simple.
         */
        Calendar cInteresSimple = Calendar.getInstance();
        cInteresSimple.set(2006, Calendar.JULY, 31);
        Calendar cInteresSimpleReformaTributaria = Calendar.getInstance();
        cInteresSimpleReformaTributaria.set(2013, Calendar.JANUARY, 1);
        Calendar cAux = Calendar.getInstance();
        double intereseMora = 0;
        cAux.setTime(cFecha.getTime());
        /**
         * Las fechas cInteresSimple y cInteresSimpleReformaTributaria son para
         * indicar cuando se debe aplicar interes compuesto, es decir, que los
         * dias que están entre esas fechas aplican interés compuesto. Desde el
         * 31 de julio hacia atrás y desde el 1 de enero hacia adelante aplica
         * interés simple.
         */
//        logInfo(UtilDate.class, "---------- Entro a calculaInteresMora. -----------------");
        Map mapInteres = new HashMap();
        int diasOutRango = 0;
        String[] diasPorcentaje = null;
        for (int i = 0; i < diasMora; i++) {
            String[] interesPeriodo = UtilDate.porcentajeInteresMoraByDiasPeriodo(cAux, listaInteres);
            String isInteresSimpleString = "";
            if (interesPeriodo != null) {
                if (mapInteres.get(interesPeriodo[0]) == null) {
                    isInteresSimpleString = compareToDateWhioutHours(cAux, cInteresSimple, LESS_EQUALS_THAN) || compareToDateWhioutHours(cAux, cInteresSimpleReformaTributaria, GREATER_EQUALS_THAN) ? "true" : "false";
                    diasPorcentaje = new String[]{"1", interesPeriodo[3], isInteresSimpleString};
                    mapInteres.put(interesPeriodo[0], diasPorcentaje);
                } else {
                    diasPorcentaje =
                            (String[]) mapInteres.get(interesPeriodo[0]);
                    MathEvaluator contador = new MathEvaluator(diasPorcentaje[0] + "+1");
                    diasPorcentaje[0] = contador.getValue().toString();
                }
            } else {
                diasOutRango++;
            }
            cAux.add(Calendar.DATE, -1);
        }
        Iterator it = mapInteres.entrySet().iterator();
        //((totalImpuestoPeriodoGravable)*(((1+(porcentaje))^(dias))-1)) --> interes mora compuesto.
        //((totalImpuestoPeriodoGravable)*(porcentaje * dias) --> interes mora simple.
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            String[] string = (String[]) e.getValue();
            Double porcentajeMora = 0.0;
            porcentajeMora = Double.valueOf(string[1]) / 100.0;
//            logInfo(UtilDate.class, "porcentajeMora = " + porcentajeMora);
            Boolean isInteresSimple = Boolean.valueOf(string[2]);
            String regla = "0";
            if (isInteresSimple.booleanValue()) {
                regla = totalBase + "*" + porcentajeMora.toString() + "*" + string[0];
            } else {
                double base = 1 + porcentajeMora;
                double elevado = Double.valueOf(string[0]);
                double potencia = Math.pow(base, elevado);
                regla = totalBase + "*(" + potencia + "-1)";
//                log.info("base = " + base
//                        + "\n\t" + "elevado " + elevado);
            }
            MathEvaluator interes = new MathEvaluator(regla);

//            log.info("regla " + regla
//                    + "\n\t" + "interes.getValue() = " + interes.getValue()
//                    + "\n\t" + "dias " + string[0]
//                    + "\n\t" + "porcentaje " + string[1]
//                    + "\n\t" + "interesSimple " + string[2]);

            intereseMora += interes.getValue();
//            logInfo(UtilDate.class, "regla = " + regla + " total ==>" + interes.getValue());
        }
//        logInfo(UtilDate.class, "intereseMora = " + intereseMora);
//        if (diasOutRango > 0) {
////            logInfo(UtilDate.class, "Cantidad de dias no encontrados en la tabla interes = " + diasOutRango);
//            double base = 1 + porcentajeActual;
//            double potencia = Math.pow(base, diasOutRango);
//            String regla = totalBase + "*(" + potencia + "-1)";
//            MathEvaluator interes = new MathEvaluator(regla);
//            intereseMora += interes.getValue();
//            log.info("interes fuera de rango compuesto = " + interes.getValue()
//                    + "\n\t" + "regla " + regla);
//        }

        //Ahora los dias fuera de rango se calculan por interés simple
        if (diasOutRango > 0) {
//            logInfo(UtilDate.class, "Cantidad de dias no encontrados en la tabla interes = " + diasOutRango);
            String regla = totalBase + "*" + porcentajeActual + "*" + diasOutRango;
            MathEvaluator interes = new MathEvaluator(regla);
            intereseMora += interes.getValue();
//            log.info("interes fuera de rango simple = " + interes.getValue()
//                    + "\n\t" + "regla = " + regla);
        }

//        logInfo(UtilDate.class, "interes mora: " + intereseMora);
//        logInfo(UtilDate.class, "diasOutRango = " + diasOutRango);
//        logInfo(UtilDate.class, "---------- Salio de calculaInteresMora. ------------------");
        return intereseMora;
    }

    public static double calculaInteresMora(Date fecha, int diasMora, List listaInteres, double porcentajeActual, Double totalBase) {
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);
        return calculaInteresMora(calFecha, diasMora, listaInteres, porcentajeActual, totalBase);
    }

    /**
     * Devuelve la cantidad de horas de un tiempo en milisegundos
     *
     * @param tiempoMilisegundos
     * @return
     */
    public static Long getHoras(Long tiempoMilisegundos) {
        if (tiempoMilisegundos != null) {
            long hora = 60 * 60 * 1000; // 1 Hora es = 60 minutos * 60 segundos * 1000 milisegundos
            return tiempoMilisegundos / hora;
        }
        return null;
    }

    /**
     * Devuelve la cantidad de minutos de un tiempo en milisegundos
     *
     * @param tiempoMilisegundos
     * @return
     */
    public static Long getMinutos(Long tiempoMilisegundos) {
        if (tiempoMilisegundos != null) {
            long hora = 60 * 60 * 1000; // 1 Hora es = 60 minutos * 60 segundos * 1000 milisegundos
            long minuto = 60 * 1000; // 1 Minuto es = 60 segundos * 1000 milisegundos
            return (tiempoMilisegundos % hora) / minuto;
        }
        return null;
    }

    /**
     * Devuelve la cantidad de segundos de un tiempo en milisegundos
     *
     * @param tiempoMilisegundos
     * @return
     */
    public static Long getSegundos(Long tiempoMilisegundos) {
        if (tiempoMilisegundos != null) {
            long hora = 60 * 60 * 1000; // 1 Hora es = 60 minutos * 60 segundos * 1000 milisegundos
            long minuto = 60 * 1000; // 1 Minuto es = 60 segundos * 1000 milisegundos
            long segundo = 1000; // 1 Segundo es = 1000 milisegundos
            return ((tiempoMilisegundos % hora) % minuto) / segundo;
        }
        return null;
    }

    /**
     * Devuelve la cantidad de segundos de un tiempo en milisegundos
     *
     * @param tiempoMilisegundos
     * @return
     */
    public static Long getMilisegundos(Long tiempoMilisegundos) {
        if (tiempoMilisegundos != null) {
            long hora = 60 * 60 * 1000; // 1 Hora es = 60 minutos * 60 segundos * 1000 milisegundos
            long minuto = 60 * 1000; // 1 Minuto es = 60 segundos * 1000 milisegundos
            long segundo = 1000; // 1 Segundo es = 1000 milisegundos
            return ((tiempoMilisegundos % hora) % minuto) % segundo;
        }
        return null;
    }

    public static Long converterHoraToMiliseconds(Long horas, Long minutos, Long segundos, Long milisegundos) {
        Long time = null;
        long hora = 60 * 60 * 1000; // 1 Hora es = 60 minutos * 60 segundos * 1000 milisegundos
        long minuto = 60 * 1000; // 1 Minuto es = 60 segundos * 1000 milisegundos
        long segundo = 1000; // 1 Segundo es = 1000 milisegundos

        if (horas != null) {
            time = horas * hora;
        }
        if (minutos != null) {
            time += minutos * minuto;
        }
        if (segundos != null) {
            time += segundos * segundo;
        }
        if (milisegundos != null) {
            time += milisegundos;
        }
        return time;
    }

    public static Long converterDiasToMiliseconds(Long dias, Long horas, Long minutos) {
        Long time = new Long(0);
        long dia = 24 * 60 * 60 * 1000; // 1 dia es =24 horas * 60 minutos * 60 segundos * 1000 milisegundos
        long hora = 60 * 60 * 1000; // 1 Hora es = 60 minutos * 60 segundos * 1000 milisegundos
        long minuto = 60 * 1000; // 1 Minuto es = 60 segundos * 1000 milisegundos

        if (dias != null) {
            time += dias * dia;
        }
        if (horas != null) {
            time += horas * hora;
        }
        if (minutos != null) {
            time += minutos * minuto;
        }

        return time;
    }

    public static Date ultimoDiaVigenciaActual() {
        Calendar cFechaUltimoVigenciaActual = Calendar.getInstance();
        cFechaUltimoVigenciaActual.set(Calendar.MONTH, cFechaUltimoVigenciaActual.getActualMaximum(Calendar.MONTH));
        cFechaUltimoVigenciaActual.set(Calendar.DATE, cFechaUltimoVigenciaActual.getActualMaximum(Calendar.DAY_OF_MONTH));
        cFechaUltimoVigenciaActual.set(Calendar.HOUR_OF_DAY, cFechaUltimoVigenciaActual.getActualMaximum(Calendar.HOUR_OF_DAY));
        cFechaUltimoVigenciaActual.set(Calendar.MINUTE, cFechaUltimoVigenciaActual.getActualMaximum(Calendar.MINUTE));
        cFechaUltimoVigenciaActual.set(Calendar.SECOND, cFechaUltimoVigenciaActual.getActualMinimum(Calendar.SECOND));
        return cFechaUltimoVigenciaActual.getTime();
    }

    /**
     * Metodo que le suma la cantidad dias calendario a la fecha inicial
     * indicada. <p> Si el parametro fechaInicial es null, el metodo retorna la
     * misma fecha inicial. </p>
     *
     * @param fechaInicial
     * @param cantidadDiasCalendario
     * @return Date con la fecha sumada si la cantidad de dias del parametro es
     * > 0
     */
    public static Date sumarDiasCalendario(Date fechaInicial, int cantidadDiasCalendario) {
        Date fechaFinal = null;

        if (fechaInicial != null) {
            Calendar cFecha = Calendar.getInstance();
            cFecha.setTime(fechaInicial);

            if (cantidadDiasCalendario > 0) {
                cFecha.add(Calendar.DATE, cantidadDiasCalendario);
            }
            fechaFinal = cFecha.getTime();
        }

        return fechaFinal;
    }

    /**
     * Metodo que calcula el ultimo dia habil del mes de la fecha indicada,
     * teniendo en cuenta la lista de festivos
     *
     * @param listaFestivos
     * @param fechaInicial
     * @return
     */
    public static Date ultimoDiaHabilDelMes(List<Calendar> listaFestivos, Date fechaInicial) {
        Date fechaFinal = null;

        if (UtilList.isNotEmpty(listaFestivos) && fechaInicial != null) {

//            logDebug(UtilDate.class, "fechaInicial = " + fechaInicial);

            Calendar cFechaTmp = Calendar.getInstance(_currentLocale);
            cFechaTmp.setTime(fechaInicial);

            // Sacar el anio, mes, dia inicial
            int iYear = cFechaTmp.get(Calendar.YEAR);
            int iMonth = cFechaTmp.get(Calendar.MONTH);
            int iDay = cFechaTmp.get(Calendar.DATE);

            // Sacar la cantidad de dias del mes
            int cntDiasDelMes = cFechaTmp.getActualMaximum(Calendar.DAY_OF_MONTH);

            listaCalendarFestivos = listaFestivos;

            Calendar cFechaUltimoDiaHabil = Calendar.getInstance();

            while (iDay <= cntDiasDelMes) {

                // Verifica si es un dia habil y guarda el ultimo dial habil validado
                if (isWorkDay(cFechaTmp)) {
                    cFechaUltimoDiaHabil.setTime(cFechaTmp.getTime());
                }

                cFechaTmp.set(iYear, iMonth, ++iDay);
            }

            if (cFechaUltimoDiaHabil != null) {
                fechaFinal = cFechaUltimoDiaHabil.getTime();
            }
        }
        return fechaFinal;
    }

    public static Calendar getCalendarWithoutHours(Calendar calendar) {
        if (calendar != null) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
            calendar.clear(Calendar.MILLISECOND);
        }
        return calendar;
    }

    public static Date getDateWithoutHours(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            getCalendarWithoutHours(calendar);
            date = calendar.getTime();
        }
        return date;
    }

    public static Calendar getCalendarWithoutHours(Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            getCalendarWithoutHours(calendar);

        }
        return calendar;
    }

    public static Date ultimoDiaCalendarioMes(Calendar cFechaFinMes) {
        cFechaFinMes.set(Calendar.DATE, cFechaFinMes.getActualMaximum(Calendar.DAY_OF_MONTH));
        cFechaFinMes.set(Calendar.HOUR_OF_DAY, cFechaFinMes.getActualMaximum(Calendar.HOUR_OF_DAY));
        cFechaFinMes.set(Calendar.MINUTE, cFechaFinMes.getActualMaximum(Calendar.MINUTE));
        cFechaFinMes.set(Calendar.SECOND, cFechaFinMes.getActualMinimum(Calendar.SECOND));
        return cFechaFinMes.getTime();
    }

    public static Calendar[] cicloPorPeriodicidad(TypePeriodicidad periodicidad, int ciclo, int ano) throws Exception {
        Calendar[] fechasCiclo = {Calendar.getInstance(), Calendar.getInstance()};
        switch (periodicidad) {
            case MENSUAL:
                fechasCiclo[0].set(ano, ciclo, fechasCiclo[0].getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
                fechasCiclo[1].set(ano, ciclo, fechasCiclo[0].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                break;
            case BIMENSUAL:
                switch (ciclo) {
                    case 1:
                        fechasCiclo[0].set(ano, 0, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 1, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 1, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 2:
                        fechasCiclo[0].set(ano, 2, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 3, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 3, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 3:
                        fechasCiclo[0].set(ano, 4, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 5, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 5, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 4:
                        fechasCiclo[0].set(ano, 6, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 7, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 7, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 5:
                        fechasCiclo[0].set(ano, 8, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 9, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 9, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 6:
                        fechasCiclo[0].set(ano, 10, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 11, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 11, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                }
                break;
            case TRIMESTRAL:
                switch (ciclo) {
                    case 1:
                        fechasCiclo[0].set(ano, 0, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 2, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 2, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 2:
                        fechasCiclo[0].set(ano, 3, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 5, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 5, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 3:
                        fechasCiclo[0].set(ano, 6, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 8, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 8, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 4:
                        fechasCiclo[0].set(ano, 9, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 11, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 11, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                }
                break;
            case CUATRIMESTRAL:
                switch (ciclo) {
                    case 1:
                        fechasCiclo[0].set(ano, 0, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 3, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 3, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 2:
                        fechasCiclo[0].set(ano, 4, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 7, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 7, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 3:
                        fechasCiclo[0].set(ano, 8, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 11, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 11, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                }
                break;
            case SEMESTRAL:
                switch (ciclo) {
                    case 1:
                        fechasCiclo[0].set(ano, 0, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 5, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 5, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                    case 2:
                        fechasCiclo[0].set(ano, 6, 1, 0, 0, 0);
                        fechasCiclo[1].set(ano, 11, 1, 23, 59, 59);
                        fechasCiclo[1].set(ano, 11, fechasCiclo[1].getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        break;
                }
                break;
        }
        return fechasCiclo;
    }

    /**
     * Calcular el valor promedio del porcentaje interes mora de una cantidad de
     * dias determinados basandose en la lista de intereses de mora por
     * trimestre.
     *
     * @param cAux
     * @param diasMora
     * @param listaInteres
     * @param porcentajeActual
     * @return el promedio de los dias de mora (? %InteresMora)/numero de
     * trimestres.
     */
    //=100*(POTENCIA(((0,061439/100)+1);365))-100
    synchronized public static double getPromedioInteresMora(Calendar cAux, int diasMora, List listaInteres) {
        Map mapInteres = new HashMap();
        double promedioIntereseMora = 0;
        double totalIntereseMora = 0;
        int cantidadTrimestres = 0;
        int diasAnio = 365;
        for (int i = 0; i < diasMora; i++) {
            String[] interesPeriodo = UtilDate.porcentajeInteresMoraByDiasPeriodo(cAux, listaInteres);
            if (interesPeriodo != null) {
                if (mapInteres.get(interesPeriodo[0]) == null) {
                    mapInteres.put(interesPeriodo[0], interesPeriodo[3]);
                }
            }
            cAux.add(Calendar.DATE, -1);
        }
        Iterator it = mapInteres.entrySet().iterator();
        while (it.hasNext()) {
            cantidadTrimestres++;
            Map.Entry e = (Map.Entry) it.next();
            String string = (String) e.getValue();
            Double porcentajeMora = 0.0;
            porcentajeMora = Double.valueOf(string) / 100;
            totalIntereseMora += porcentajeMora;
        }
        if (cantidadTrimestres > 0) {
            //=100*(POTENCIA(((ID%)+1);365))-100
            //ID=interes diario
            totalIntereseMora = totalIntereseMora / cantidadTrimestres;
            promedioIntereseMora = (100 * (Math.pow(totalIntereseMora + 1, diasAnio))) - 100;
        }

        //logInfo(UtilDate.class, "totalIntereseMora:  " + totalIntereseMora);
        //logInfo(UtilDate.class, "cantidadTrimestres:  " + cantidadTrimestres);
        //logInfo(UtilDate.class, "promedioIntereseMora:  " + promedioIntereseMora);

        return promedioIntereseMora;
    }

    /**
     * Metodo que calcula la proxima vigencia dependiendo de la periodicidad
     *
     * @param vigencia vigencia actual
     * @param freceunciaPeriodicidad tipo de periodicidad para calcular la
     * proxima vigencia dependiendo del periodo actual
     * @return Intger proxima vigencia
     * @throws Exception si la proxima vigencia es null o no es un valor
     * numerico valores de la periodicidad : <br> <ul> <li>1 MENSUAL</li> <li>2
     * BIMENSUAL</li> <li>3 TRIMESTRAL</li> <li>4 CUATRIMESTRAL</li> <li>6
     * SEMESTRAL</li> <li> PARA OTROS VALORES SE TOMARA COMO ANUAL</li> <br>
     * <b>Si la vigencia es nulla o no es numerica se lanza una exception</b>
     * <br> Ejemplos: fecha actual 2011-03-22 -YYYY-MM-DD
     * proximaVigenciaByTipoPeriodicidad(2011,1) =201104; MENSUAL
     * proximaVigenciaByTipoPeriodicidad(2011,2) =201102; BIMENSUAL
     * proximaVigenciaByTipoPeriodicidad(2011,6) =201101; SEMESTRAL
     * proximaVigenciaByTipoPeriodicidad(2011,12) =2012; ANUAL
     * proximaVigenciaByTipoPeriodicidad(201101,6) =201102; SEMESTRAL
     * proximaVigenciaByTipoPeriodicidad(201102,6) =201201; SEMESTRAL si la
     * vigencia
     */
    public static String proximaVigenciaByTipoPeriodicidad(String vigencia, int freceunciaPeriodicidad) throws Exception {
        String nextVigencia = null;
        int mesesAno = 12;
        try {
            if (StringUtils.isNotBlank(vigencia)) {
                try {
                    Integer.parseInt(vigencia);
                } catch (Exception ex) {
                    throw new Exception("La vigencia no es numerica.");
                }
                if (vigencia.length() == 4 || vigencia.length() == 6) {
                    String anoVigencia = vigencia.substring(0, 4);
                    String periodo = null;
                    if (vigencia.length() == 6) {
                        periodo = vigencia.substring(4, 6);
                    }

                    switch (freceunciaPeriodicidad) {
                        case 1://MENSUAL
                        case 2://BIMENSUAL
                        case 3://TRIMESTRAL
                        case 4://CUATRIMESTRAL
                        case 6://SEMESTRAL
                            if (StringUtils.isNotBlank(periodo)
                                    && Integer.parseInt(periodo) < (mesesAno / freceunciaPeriodicidad)) {
                                nextVigencia = anoVigencia + StringUtils.leftPad(String.valueOf(Integer.parseInt(periodo) + 1), 2, "0");
                            } else {
                                nextVigencia = (Integer.parseInt(anoVigencia) + 1) + "01";
                            }
                            break;
                        default:
                            nextVigencia = String.valueOf((Integer.parseInt(anoVigencia) + 1));
                            break;

                    }

                } else {
                    throw new Exception("La vigencia : " + vigencia + " no es valida no pertence a un año o perido valido.");
                }

            } else {
                throw new Exception("Los parametros resultaron null.");
            }
        } catch (Exception ex) {
            throw new Exception("Error generando la proxima vigencia, " + ex.getMessage());
        }
        return nextVigencia;
    }

    public static String anteriorVigenciaByTipoPeriodicidad(String vigencia, int freceunciaPeriodicidad) throws Exception {
        String anteriorVigencia = null;
        int mesesAno = 12;
        try {
            if (StringUtils.isNotBlank(vigencia)) {
                try {
                    Integer.parseInt(vigencia);
                } catch (Exception ex) {
                    throw new Exception("La vigencia no es numerica.");
                }
                if (vigencia.length() == 4 || vigencia.length() == 6) {
                    String anoVigencia = vigencia.substring(0, 4);
                    String periodo = null;
                    if (vigencia.length() == 6) {
                        periodo = vigencia.substring(4, 6);
                    }

                    switch (freceunciaPeriodicidad) {
                        case 1://MENSUAL
                        case 2://BIMENSUAL
                        case 3://TRIMESTRAL
                        case 4://CUATRIMESTRAL
                        case 6://SEMESTRAL
                            if (StringUtils.isNotBlank(periodo)
                                    && Integer.parseInt(periodo) > 1
                                    && Integer.parseInt(periodo) <= (mesesAno / freceunciaPeriodicidad)) {
                                anteriorVigencia = anoVigencia + StringUtils.leftPad(String.valueOf(Integer.parseInt(periodo) - 1), 2, "0");
                            } else {
                                anteriorVigencia = (Integer.parseInt(anoVigencia) - 1) + StringUtils.leftPad(String.valueOf((mesesAno / freceunciaPeriodicidad)), 2, "0");
                            }
                            break;
                        default:
                            anteriorVigencia = String.valueOf((Integer.parseInt(anoVigencia) - 1));
                            break;

                    }

                } else {
                    throw new Exception("La vigencia : " + vigencia + " no es valida no pertence a un año o perido valido.");
                }

            } else {
                throw new Exception("Los parametros resultaron null.");
            }
        } catch (Exception ex) {
            throw new Exception("Error generando la anterior vigencia, " + ex.getMessage());
        }
        return anteriorVigencia;
    }

    /**
     *
     * @param fecha
     * @param frecuenciaPeriodicidad
     * @return retora la fecha con el peridodo segun para la fecha pasada como
     * parametro
     * @throws Exception
     *
     * Ejemplo: si fecha es 2011-03-22 y la periodicidad mensual 201103 si fecha
     * es 2011-03-22 y la periodicidad semestral 201101
     */
    public static String getVigenciaPeriodoByDate(Calendar fecha, int frecuenciaPeriodicidad) throws Exception {
        String vigencia = null;
        if (fecha == null) {
            throw new IllegalArgumentException("la fehca resulto null.");
        }
//        logDebug(UtilDate.class, "fecha = " + fecha.getTime());
//        logDebug(UtilDate.class, "frecuenciaPeriodicidad = " + frecuenciaPeriodicidad);
        int mesFecha = fecha.get(Calendar.MONTH) + 1;
        switch (frecuenciaPeriodicidad) {
            case 1://MENSUAL
            case 2://BIMENSUAL
            case 3://TRIMESTRAL
            case 4://CUATRIMESTRAL
            case 6://SEMESTRAL
                int periodo = mesFecha / frecuenciaPeriodicidad;
                if (mesFecha % frecuenciaPeriodicidad != 0) {
                    periodo += 1;
                }
                vigencia = String.valueOf(fecha.get(Calendar.YEAR)) + StringUtils.leftPad(String.valueOf(periodo), 2, "0");
                break;
            default:
                vigencia = String.valueOf((fecha.get(Calendar.YEAR)));
                break;
        }
        return vigencia;
    }

    /**
     * Retorna la fecha recibida por parámetros, con la hora actual.
     *
     * @param Date originalDate
     * @return Date
     *
     */
    public static Date setActualHourMinuteSecondToDate(Date originalDate) throws Exception {
        Date transformado = null;
        if (originalDate != null) {
            Calendar inicialCalendar = Calendar.getInstance();
            inicialCalendar.setTime(originalDate);
            Calendar hoy = Calendar.getInstance();
            hoy.set(inicialCalendar.get(Calendar.YEAR), inicialCalendar.get(Calendar.MONTH), inicialCalendar.get(Calendar.DATE));
            transformado = hoy.getTime();
        }
        return transformado;
    }

    /**
     * Creado por: Jorge Garcia Garcia - 2012-03-01 <br>Permite calcular el
     * valor de la mora de una cantidad de dias determinados basndose en la
     * lista de intereses de mora por trimestre. <p>Ajustado por: William Diaz
     * Pabón - 2012-05-04 <br>Se ajusto que al momento de determinar el periodo
     * de mora se calcule de una vez la cantidad de dias de mora que caben en
     * dicho periodo para ahorar tiempo de procesamiento.
     *
     * @param cFecha Fecha maxima de la mora a cobrar
     * @param diasMora Cantidad de días de mora a cobrar
     * @param listaInteres Listado de tipo List &lt;String[]&gt; que contiene
     * los intereses configurados en el sistema con la siguiente estructura
     * <br>String[0]= Código del registro de la Base de Datos <br>String[1]=
     * Fecha inicial del periodo <br>String[2]= Fecha final del periodo
     * <br>String[3]= Valor del interes diario en formato porcentaje
     * @param porcentajeActual este porcentaje debe estar en forma decimal
     * porcentaje%/100 y es el porcentaje a usar con los días que no tengan
     * periodo. Esto es más usado cuando se hace proyección de dias con mora
     * @param mapBaseImpuesto Map &lt;Integer, Double[]&gt;. <br>El Key es el
     * código del accesorio base <br>El values contiene un vector En Double[0] =
     * valor de la Base, Double[1] = Valor interes calculado
     * @return
     */
    synchronized public static void calculaInteresMora(Calendar cFecha, int diasMora, List<String[]> listaInteres, double porcentajeActual, Map<Integer, Double[]> mapBaseImpuesto) throws Exception {

        if (cFecha == null
                || UtilList.isEmpty(listaInteres)
                || mapBaseImpuesto == null) {
            throw new Exception("Paramentros no validos.");
        }

        //logInfo(UtilDate.class, "---------- Entro a calculaInteresMora. ------------------");

        Calendar cAux = Calendar.getInstance();
        cAux.setTime(cFecha.getTime());

        Map<String, String[]> mapInteres = new HashMap<String, String[]>();
        int diasOutRango = 0;

        for (int i = 0; i < diasMora; i++) {


            // Calcula la cantidad de dias de mora que se encuentra en un periodo especifico
            String[] vctObj = calcularCantidadDiasPorPeriodoInteresMora(cAux, listaInteres, diasMora);

            if (vctObj != null) {

                // Obteniendo el vector desde el map si ya el periodo esta guardado
                String[] vctStrMap = (String[]) mapInteres.get(vctObj[3]);

                // Periodo ya guardado en el map
                if (vctStrMap != null) {
                    vctStrMap[0] = String.valueOf(Integer.valueOf(vctStrMap[0]) + Integer.valueOf(vctObj[0]));
                    mapInteres.put(vctObj[3], vctStrMap);
                } else {
                    // Periodo no esta guardado en el map
                    mapInteres.put(vctObj[3], new String[]{vctObj[0], vctObj[1], vctObj[2]});
                }

                final int cntDiasEnPeriodoMora = Integer.parseInt((String) vctObj[0]);

                cAux.add(Calendar.DATE, (cntDiasEnPeriodoMora + 1) * -1);

                diasMora -= cntDiasEnPeriodoMora;

            } else {

                diasOutRango++;
                diasMora--;
                cAux.add(Calendar.DATE, -1);
            }

        }

        //((totalImpuestoPeriodoGravable)*(((1+(porcentaje))^(dias))-1)) --> interes mora compuesto.
        //((totalImpuestoPeriodoGravable)*(porcentaje * dias) --> interes mora simple.

        // Ciclo que recorre todas los intereses de los periodos encontrados
        for (Map.Entry<String, String[]> entryMapInteres : mapInteres.entrySet()) {

            String[] vctStrValueMapInteres = entryMapInteres.getValue();

            Double porcentajeMora = Double.valueOf(vctStrValueMapInteres[1]) / 100.0;
            Boolean isInteresSimple = Boolean.valueOf(vctStrValueMapInteres[2]);
            String regla = "0";

            // Ciclo que recorre las bases de los accesorios
            for (Map.Entry<Integer, Double[]> entryMapBaseImpuesto : mapBaseImpuesto.entrySet()) {

                Double[] vctDblValueMapBaseImpuesto = entryMapBaseImpuesto.getValue();

                if (isInteresSimple.booleanValue()) {

                    regla = vctDblValueMapBaseImpuesto[0] + "*" + porcentajeMora.toString() + "*" + vctStrValueMapInteres[0];

                } else {
                    double base = 1 + porcentajeMora;
                    double elevado = Double.valueOf(vctStrValueMapInteres[0]);
                    double potencia = Math.pow(base, elevado);
                    regla = vctDblValueMapBaseImpuesto[0] + "*(" + potencia + "-1)";
                }

//                logDebug(this,"regla = " + regla);

                MathEvaluator interes = new MathEvaluator(regla);

                if (vctDblValueMapBaseImpuesto[1] == null) {
                    vctDblValueMapBaseImpuesto[1] = Double.valueOf(0.0);
                }
                vctDblValueMapBaseImpuesto[1] += interes.getValue();

                //logInfo(UtilDate.class, entryMapBaseImpuesto.getKey() + " : " + vctDblValueMapBaseImpuesto[0] + " : " + vctStrValueMapInteres[0] + " : " + porcentajeMora + " : " + interes.getValue());

            }
        }


        if (diasOutRango > 0) {
            //logInfo(UtilDate.class, "Cantidad de dias no encontrados en la tabla interes = " + diasOutRango);

            double base = 1 + porcentajeActual;
            double potencia = Math.pow(base, diasOutRango);

            for (Map.Entry<Integer, Double[]> entryMapBaseImpuesto : mapBaseImpuesto.entrySet()) {

                Double[] vctDblValueMapBaseImpuesto = entryMapBaseImpuesto.getValue();

                String regla = vctDblValueMapBaseImpuesto[0] + "*(" + potencia + "-1)";
                MathEvaluator interes = new MathEvaluator(regla);
                vctDblValueMapBaseImpuesto[1] += interes.getValue();

            }

        }

        for (Map.Entry<Integer, Double[]> entryMapBaseImpuesto : mapBaseImpuesto.entrySet()) {

            Integer intKeyMapBaseImpuesto = entryMapBaseImpuesto.getKey();
            Double[] vctDblValueMapBaseImpuesto = entryMapBaseImpuesto.getValue();

            //logInfo(UtilDate.class, "accesorio = " + intKeyMapBaseImpuesto + " valor base: " + vctDblValueMapBaseImpuesto[0] + " intereses: " + vctDblValueMapBaseImpuesto[1]);

        }

//        logInfo(UtilDate.class, "diasOutRango = " + diasOutRango);
//        logInfo(UtilDate.class, "---------- Salio de calculaInteresMora. ------------------");

    }

    /**
     * Método que determina la cantidad de días que hay desde la fecha del
     * parametro a la fecha de inicio del periodo donde se encuentre esa fecha.
     * <p> Este metodo tiene en cuenta que si la fecha del parametro es menor o
     * igual al 2006/06/31 se debe cobrar el interes de la forma simple
     *
     * @param cFecha Fecha maxima de la mora a cobrar
     * @param listaInteres Listado de tipo List<String[]> que contiene los
     * intereses configurados en el sistema con la siguiente estructura.
     * <p>String[0]= Código del registro de la Base de Datos <p>String[1]= Fecha
     * inicial del periodo <p>String[2]= Fecha final del periodo <p>String[3]=
     * Valor del interes diario en formato porcentaje
     * @param diasMora Cantidad de días de mora a cobrar
     * @return
     */
    public static String[] calcularCantidadDiasPorPeriodoInteresMora(Calendar cFecha, List<String[]> listaInteres, int diasMora) {

        // Variable que contiene la siguiente información
        // String[0]= Cantidad de dias dentro del periodo
        // String[1]= Valor del % de mora diaria a usar en el calculo
        // String[2]= Si el calculo es con e linterese simple (true, false)
        String[] interesPeriodo = null;

        if (cFecha != null
                && UtilList.isNotEmpty(listaInteres)
                && diasMora > 0) {

            /*
             * Esta fecha 31 julio de 2006 indica que apartir de este dia se
             * cobra impuesto compuesto antes de esa fecha se aplica impuesto
             * simple
             */
            Calendar cInteresSimple = Calendar.getInstance();
            cInteresSimple.set(2006, 6, 31);


            for (String[] vctString : listaInteres) {

                Calendar fechaInicio = FormatDate.formatStringToCalendar(vctString[1], FormatDate.FORMAT_SLASH_DD_MM_YYYY);
                Calendar fechaFin = FormatDate.formatStringToCalendar(vctString[2], FormatDate.FORMAT_SLASH_DD_MM_YYYY);

                // Verifica si la fecha del parametro esta en el rango evaluado
                if (compareToDateWhioutHours(cFecha, fechaInicio, GREATER_EQUALS_THAN)
                        && compareToDateWhioutHours(cFecha, fechaFin, LESS_EQUALS_THAN)) {

//                    logDebug(this,"\nfechaInicio = " + fechaInicio.getTime());
//                    logDebug(this,"cFecha = " + cFecha.getTime());

                    // Se suma 1 día a la fecha fin para que tome el dia de esa fecha fin
                    cFecha.add(Calendar.DATE, 1);
//                    logDebug(this,"cFecha sumado 1 dia = " + cFecha.getTime());

                    int cntDiasDif = DiferenciaFechasDiasCalendario(fechaInicio, cFecha);
//                    logDebug(this,"cntDiasDif = " + cntDiasDif);

                    if (diasMora > cntDiasDif) {

                        interesPeriodo = new String[]{String.valueOf(cntDiasDif), vctString[3].replace(",", "."), compareToDateWhioutHours(cFecha, cInteresSimple, LESS_EQUALS_THAN) == true ? "true" : "false", vctString[0]};

                    } else {

                        interesPeriodo = new String[]{String.valueOf(diasMora), vctString[3].replace(",", "."), compareToDateWhioutHours(cFecha, cInteresSimple, LESS_EQUALS_THAN) == true ? "true" : "false", vctString[0]};

                    }

                    if (interesPeriodo != null) {
//                        logDebug(UtilDate.class, "------------------------------------------");
//                        logDebug(UtilDate.class, "vctString[0] = " + vctString[0]);
//                        logDebug(UtilDate.class, "vctString[1] = " + vctString[1]);
//                        logDebug(UtilDate.class, "vctString[2] = " + vctString[2]);
//                        logDebug(UtilDate.class, "vctString[3] = " + vctString[3]);
//                        logDebug(UtilDate.class, "\ninteresPeriodo[0]=" + interesPeriodo[0]);
//                        logDebug(UtilDate.class, "interesPeriodo[1]=" + interesPeriodo[1]);
//                        logDebug(UtilDate.class, "interesPeriodo[2]=" + interesPeriodo[2]);
//                        logDebug(UtilDate.class, "------------------------------------------");


                        // Como ya se encontró el periodo de la fecha del parametro se debe salir del for
                        break;
                    }

                }

            }

        }
        return interesPeriodo;
    }

    public static void main(String[] args) {

        List<Calendar> listaDias = new ArrayList<Calendar>();
        Calendar indeCalendar = Calendar.getInstance();
        indeCalendar.set(Calendar.YEAR, 2012);
        indeCalendar.set(Calendar.DATE, 20);
        indeCalendar.set(Calendar.MONTH, 6);
        //listaDias.add(indeCalendar);

//        Calendar fecha = Calendar.getInstance();
//        fecha.set(Calendar.YEAR, 2012);
//        fecha.set(Calendar.MONTH, 6);
//        fecha.set(Calendar.DATE, 10);

        Calendar fecha = Calendar.getInstance();
        fecha.set(Calendar.YEAR, 2012);
        fecha.set(Calendar.MONTH, 11);
        fecha.set(Calendar.DATE, 8);
//listaDias.add(fecha);
//        Date date = UtilDate.sumarDiasHabiles(FormatDate.FORMAT_SLASH_DD_MM_YYYY, listaDias, fecha.getTime(), 10);
        Date date = UtilDate.sumarDiasHabiles(FormatDate.FORMAT_SLASH_DD_MM_YYYY, listaDias, new Date(), 9);
        System.out.println("date = " + date);

        System.out.println("new Date " + new Date());
        System.out.println("Fecha de Impresión : " + FormatDate.formatDateByMask(FormatDate.FORMAT_ORACLE, new Date()));

    }
}
