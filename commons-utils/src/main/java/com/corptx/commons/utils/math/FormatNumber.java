/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jorge garcia
 */
public class FormatNumber {

    /**
     * Formato "$ ###,###.##"
     */
    public static final String FORMATO_MILES_CON_DECIMALES_PESOS_CO = "$ ###,##0.00";
    public static final String FORMATO_SIN_DECIMALES = "####.00";
    public static final String FORMATO_MILES_SIN_DECIMALES_PESOS_CO = "$ ###,##0";
    public static final String FORMAT_SIN_MILES_SIN_DECIMALES = "######";

    /**
     * Formatea un número en forma de String con el formato indicado como
     * parametro tomando como el Locale para Colombia
     *
     * @param number
     * @param formato
     * @return
     */
    public static String formatNumber(String number, String formato) {
        try {
            BigDecimal m = new BigDecimal(number);
            NumberFormat formatter = new DecimalFormat(formato);
//            NumberFormat formatter = new DecimalFormat(formato,DecimalFormatSymbols.getInstance(new Locale("es", "CO")));
            number = formatter.format(m);
            return number;
        } catch (Exception ex) {
            return number;
        }

    }

    /**
     * Formatea un número en forma de String con decimales como un String entero
     * sin decimales (created by Alexander Quintero Duarte)
     *
     * @param numberString
     * @param formato
     * @return integerString
     */
    public static String stringDecimalToStringInteger(String numberString) {
        String integerString = numberString;
        if (integerString != null && !"null".equals(integerString)) {
            String patron = "\\.[0-9]*";
            Pattern p = Pattern.compile(patron);
            Matcher matcher = p.matcher(integerString);
            if (matcher.find()) {
                integerString = matcher.replaceAll("");
            }
        }else{
            integerString = "";
        }
        return integerString;
    }

    public static void main(String[] args) {

//        String in = "23434566.90";
//        String res = "";
//        
//        double inicio = System.currentTimeMillis();
//        
//        double inicio1 = System.currentTimeMillis();
//        res = stringDecimalToStringInteger(in);
//        System.out.println((System.currentTimeMillis()-inicio1)+" res1: "+res);
//        
//        double inicio2 = System.currentTimeMillis();
//        res = in.split("[.]")[0];
//        System.out.println((System.currentTimeMillis()-inicio2)+" res2: "+res);


        double cuotaMensual = 14322780.00;
        double intereses = 2589.76;
        double cuotaMenssualFinal = cuotaMensual + intereses;

        double totalCuotaRedondeado = Redondeo.redondear(cuotaMenssualFinal, -2, Redondeo.ROUND_HALF_EVEN);

        intereses = Redondeo.redondear(intereses, 0, Redondeo.ROUND_HALF_EVEN);

        double abonoCap = totalCuotaRedondeado - intereses;
        double suma = abonoCap + intereses;
        System.out.println("numero = " + new BigDecimal(cuotaMensual).toPlainString());
        System.out.println("redondear = " + new BigDecimal(totalCuotaRedondeado).toPlainString());
        System.out.println("intereses = " + new BigDecimal(intereses).toPlainString());
        System.out.println("abonoCap = " + new BigDecimal(abonoCap).toPlainString());
        System.out.println("suma = " + new BigDecimal(suma).toPlainString());
        System.out.println("suma = " + String.valueOf(suma));


    }
}
