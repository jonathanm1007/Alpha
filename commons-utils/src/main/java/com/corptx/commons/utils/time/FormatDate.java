/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.log;
//import org.apache.commons.logging.logFactory;

/**
 *
 ** @author William Diaz Pabón
 */
public class FormatDate {

//    public static final //log //log = //logFactory.get//log(FormatDate.class);
    /**
     * Formato "dd-MM-yyyy"
     */
    public static String FORMAT_ORACLE = "dd-MM-yyyy";
    /**
     * Formato "yyyy-MM-dd"
     */
    public static String FORMAT_MYSQL = "yyyy-MM-dd";
    /**
     * Formato "dd-MMMM-yyyy"
     */
    public static String FORMAT_MINUS_DD_MMMM_YYYY = "dd-MMMM-yyyy";
    /**
     * Formato "yyyy-MM-dd hh:mm:ss"
     */
    public static String FORMAT_MINUS_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    /**
     * Formato "dd-MMMM-yyyy hh:mm:ss"
     */
    public static String FORMAT_MINUS_DD_MMMM_YYYY_HH_MM_SS = "dd-MMMM-yyyy hh:mm:ss";
    /**
     * Formato "yyyy-MM-dd-hh-mm-ss"
     */
    public static String FORMAT_MINUS_FULL_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd-hh-mm-ss";
    public static String FORMAT_MINUS_FULL_YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd-HH-mm-ss-SSS";
    /**
     * Formato "dd/MM/yyyy hh:mm:ss"
     */
    public static String FORMAT_SLASH_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
    /**
     * Formato "dd/MM/yyyy"
     */
    public static String FORMAT_SLASH_DD_MM_YYYY = "dd/MM/yyyy";
    public static String FORMAT_MMMM = "MMMM";
    public static String FORMAT_DDMMYY = "DDMMYY";

    /**
     * Convierte una fecha tipo Date al formato indicado en la mascara. Tener en
     * cuenta que para los meses se usa MM y para los minutos de usa mm
     *
     * @param mask, cadena de texto que indica la mascara a usar como formato de
     * la fecha, Ejemplo dd/MM/yyyy
     * @param date, Fecha a formatear de tipo de dato Date
     * @return cadena de texto con la fecha formateada
     */
    public static String formatDateByMask(String mask, Date date) {
        String fecha = null;
        if (date != null && StringUtils.isNotBlank(mask)) {
            SimpleDateFormat formateador = new SimpleDateFormat(mask);
            fecha = formateador.format(date);
        }
        return fecha;
    }

    /**
     * Convierte una fecha tipo Date al formato indicado en la mascara. Tener en
     * cuenta que para los meses se usa MM y para los minutos de usa mm
     *
     * @param mask, cadena de texto que indica la mascara a usar como formato de
     * la fecha, Ejemplo dd/MM/yyyy
     * @param date, Fecha a formatear de tipo de dato Timestamp
     * @return cadena de texto con la fecha formateada
     */
    public static String formatDateByMask(String mask, Timestamp date) {
        String fecha = null;
        if (date != null) {
            SimpleDateFormat formateador = new SimpleDateFormat(mask);
            fecha = formateador.format(new Date(date.getTime()));
        }
        return fecha;
    }

    /**
     * Convierte una cadena con una fecha a un objeto Calendar<br> Ej:
     * formatStringToCalendar("10/10/2007", "dd/MM/yyyy")
     *
     * @param strDate Cadena con la fecha. Ej: "10/10/2007"
     * @param format Cadena con el formato que tiene el parametro strDate.<br>
     * Se debe tener en cuenta que MM (m en mayuscula) son meses y mm (m en
     * minuscula) son minutos.<br> Ej: "dd/MM/yyyy"
     * @return Objeto Calendar con la fecha o null si algunos de los parametros
     * estan nulos o vacios
     */
    public static Calendar formatStringToCalendar(String strDate, String format) {
        Calendar newDate = null;

        if (!StringUtils.isBlank(strDate) && !StringUtils.isBlank(format)) {
            try {
                DateFormat formatter = new SimpleDateFormat(format);
                Date date = (Date) formatter.parse(strDate);
                newDate = Calendar.getInstance();
                newDate.setTime(date);
            } catch (Exception ex) {
                newDate = null;
                //log.error("Error al convertir String a Calendar. " + ex.getMessage());
            }
        }
        return newDate;
    }

    /**
     * Convierte una cadena con una fecha a un objeto Date<br> Ej:
     * formatStringToDate("10/10/2007", "dd/MM/yyyy")
     *
     * @param strDate Cadena con la fecha. Ej: "10/10/2007"
     * @param format Cadena con el formato que tiene el parametro strDate.<br>
     * Se debe tener en cuenta que MM (m en mayuscula) son meses y mm (m en
     * minuscula) son minutos.<br> Ej: "dd/MM/yyyy"
     * @return Objeto Date con la fecha o null si algunos de los parametros
     * estan nulos o vacios
     */
    public static Date formatStringToDate(String strDate, String format) {
        Date newDate = null;

        if (!StringUtils.isBlank(strDate) && !StringUtils.isBlank(format)) {
            try {
                DateFormat formatter = new SimpleDateFormat(format);
                newDate = formatter.parse(strDate);
            } catch (ParseException ex) {
                //log.error("Error al convertir String a Date. " + ex.getMessage());
            }
        }
        return newDate;
    }

    /**
     * Convierte una cadena con una fecha a un objeto Timestamp<br> Ej:
     * formatStringToTimestamp("10/10/2007", "dd/MM/yyyy")
     *
     * @param strDate Cadena con la fecha. Ej: "10/10/2007"
     * @param format Cadena con el formato que tiene el parametro strDate.<br>
     * Se debe tener en cuenta que MM (m en mayuscula) son meses y mm (m en
     * minuscula) son minutos.<br> Ej: "dd/MM/yyyy"
     * @return Objeto Timestamp con la fecha o null si algunos de los parametros
     * estan nulos o vacios
     */
    public static Timestamp formatStringToTimestamp(String strDate, String format) {
        Timestamp newDate = null;

        if (!StringUtils.isBlank(strDate) && !StringUtils.isBlank(format)) {
            try {
                DateFormat formatter = new SimpleDateFormat(format);
                Date date = formatter.parse(strDate);
                newDate = new Timestamp(date.getTime());
            } catch (ParseException ex) {
                //log.error("Error al convertir String a Timestamp. " + ex.getMessage());
                newDate = null;
            }
        }
        return newDate;
    }

    /**
     * Metodo que convierte un Objeto Date a un Objeto Calendar
     *
     * @param date Objeto Date a convertir
     * @return Objeto Calendar con la fecha del parametro
     */
    public static Calendar formatDateToCalendar(Date date) {
        Calendar newDate = null;

        if (date != null) {
            try {
                newDate = Calendar.getInstance();
                newDate.setTime(date);
            } catch (Exception ex) {
                newDate = null;
                //log.error("Error al convertir Date a Calendar. " + ex.getMessage());
            }
        }
        return newDate;
    }

    public static String getNombreMes(int mes) {
        String nombreMes = "";
        switch (mes) {
            case 1:
                nombreMes = "Enero";
                break;
            case 2:
                nombreMes = "Febrero";
                break;
            case 3:
                nombreMes = "Marzo";
                break;
            case 4:
                nombreMes = "Abril";
                break;
            case 5:
                nombreMes = "Mayo";
                break;
            case 6:
                nombreMes = "Junio";
                break;
            case 7:
                nombreMes = "Julio";
                break;
            case 8:
                nombreMes = "Agosto";
                break;
            case 9:
                nombreMes = "Septiembre";
                break;
            case 10:
                nombreMes = "Octubre";
                break;
            case 11:
                nombreMes = "Noviembre";
                break;
            case 12:
                nombreMes = "Diciembre";
                break;
        }
        return nombreMes;
    }

    public static String getNombreDia(int dia) {
        String nombre = "";
        switch (dia) {
            case 1:
                nombre = "Domingo";
                break;
            case 2:
                nombre = "Lunes";
                break;
            case 3:
                nombre = "Martes";
                break;
            case 4:
                nombre = "Miércoles";
                break;
            case 5:
                nombre = "Jueves";
                break;
            case 6:
                nombre = "Viernes";
                break;
            case 7:
                nombre = "Sábado";
                break;
        }
        return nombre;
    }

    /**
     * Metodo que recibe un calendar como para metro y retorna un string de la
     * fecha en letras
     *
     * @param cFecha
     * @return string jueves 05 agosto 2010
     */
    public static String formatDateToString(Calendar cFecha) {
        String stringFecha = "";
        if (cFecha != null) {
            stringFecha = getNombreDia(cFecha.get(Calendar.DAY_OF_WEEK)) + " ";
            stringFecha += String.valueOf(cFecha.get(Calendar.DATE)) + " ";
            stringFecha += getNombreMes(cFecha.get(Calendar.MONTH) + 1) + " de ";
            stringFecha += String.valueOf(cFecha.get(Calendar.YEAR));
        }
        return stringFecha;
    }

    public static String formatDateToString(Date date) {
        Calendar cFecha = null;
        if (date != null) {
            cFecha = Calendar.getInstance();
            cFecha.setTime(date);
        }
        return formatDateToString(cFecha);


    }

    /**
     * Metodo que recibe un Date y devuelve el nombre del mes
     *
     * @param date
     * @return
     */
    public static String getNombreMes(Date date) {
        String strNombreMes = "";

        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            strNombreMes = getNombreMes(cal.get(Calendar.MONTH) + 1);
        }

        return strNombreMes;
    }

    /**
     * Metodo que recibe un Date y devuelve el año en cadena de texto <p> Ej:
     * para la fecha 2010/09/14 retorna "2010" </p>
     *
     * @param date
     * @return
     */
    public static String getNombreAnio(Date date) {
        String strNombreAnio = "";

        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);


            strNombreAnio = String.valueOf(cal.get(Calendar.YEAR));
        }

        return strNombreAnio;
    }

    /**
     * Retorna el nombre del mes segun el locale y el style
     *
     * @param mes numero entre 1 y 12 de lo contrario retoen null
     * @param local si es null se toma el por defecto
     * @param style Calendar.SHORT,Calendar.LONG por defecto Calendar.LONG
     * @return
     */
    public static String getNombreMes(int mes, Locale local, int style) {
        String nombreMes = null;
        if (mes >= 1 && mes <= 12) {
            int styleDefault = Calendar.LONG;
            switch (style) {
                case Calendar.SHORT:
                    styleDefault = style;
                    break;
            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, (mes - 1));
            nombreMes = cal.getDisplayName(Calendar.MONTH, styleDefault, local != null ? local : Locale.getDefault());
        }
        return nombreMes;
    }

    /**
     * Obtiene una cantidad de tiempo en milisegundos y la convierte en una
     * cadena de string con el formato dd:hh:mm:ss
     *
     * @param millis cantidad de tiempo
     * @return String cantidad de tiempo en formato dd:hh:mm:ss.
     * @throws IllegalArgumentException si el valor del parametro es menor que
     * cero
     */
    public static String getDurationTimeToString(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("La duracion debe ser mayor a cero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days > 9 ? days : ("0" + days));
        sb.append(" : ");
        sb.append(hours > 9 ? hours : ("0" + hours));
        sb.append(" : ");
        sb.append(minutes > 9 ? minutes : ("0" + minutes));
        sb.append(" : ");
        sb.append(seconds > 9 ? seconds : ("0" + seconds));
        return (sb.toString());
    }
}
