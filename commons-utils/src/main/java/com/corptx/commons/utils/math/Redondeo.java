/**
 *
 * @author Jorge garcia
 */

/*
 * 2013-02-05 - AlexanderQ - Agregada constante y metodos para un nuevo tipo de redondeo en el cual no redondea cuando el valor esta en la mitad.
 */
package com.corptx.commons.utils.math;

import com.corptx.commons.utils.log.UtilLog;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.lang.StringUtils;

public class Redondeo {

    /**
     * Modo de Redondeo hacia el infinito positivo. Si el BigDecimal es
     * positivo, se comporta como ROUND_UP; si es negativo, se comporta como
     * ROUND_DOWN. Tenga en cuenta que este modo de redondeo no disminuye el
     * valor calculado.
     */
    public static int ROUND_CEILING = BigDecimal.ROUND_CEILING;
    /**
     * Modo de Redondeo hacia cero. Incrementos de la cifra nunca antes de una
     * fracción (es decir, trunca los decimales). Tenga en cuenta que este modo
     * de redondeo no aumenta la magnitud del valor calculado.
     */
    public static int ROUND_DOWN = BigDecimal.ROUND_DOWN;
    /**
     * Modo de Redondeo hacia el infinito negativo. Si el BigDecimal es
     * positiva, se comportan como ROUND_DOWN; si es negativo, se comportan como
     * ROUND_UP. Tenga en cuenta que este modo de redondeo no aumenta el valor
     * calculado.
     */
    public static int ROUND_FLOOR = BigDecimal.ROUND_FLOOR;
    /**
     * Modo Redondeo hacia la "vecino más cercano" a menos que tanto los vecinos
     * son equidistantes, en cuyo caso redondear. Se comporta como si ROUND_UP
     * para la fracción descartada es> 0,5, de lo contrario, se comporta como
     * para ROUND_DOWN.
     */
    public static int ROUND_HALF_DOWN = BigDecimal.ROUND_HALF_DOWN;
    /**
     * Modo de Redondeo hacia la "vecino más cercano" a menos que tanto los
     * vecinos son equidistantes, en cuyo caso redondear. Se comporta como si
     * ROUND_UP para la fracción descartada es> = 0.5, de lo contrario, se
     * comporta como ROUND_DOWN.
     */
    public static int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
    /**
     * Redonde hacia arriba siempre
     */
    public static int ROUND_UP = BigDecimal.ROUND_UP;
    /**
     * Modo de redondeo hacia el {@literal "vecino más próximo"} a menos que
     * ambos vecinos sean equidistantes, en cuyo caso, se redondea al vecino
     * par. Se comporta como {@code ROUND_HALF_UP} si el dígito a la izquierda
     * de la fracción descartada es impar. Se comporta como
     * {@code ROUND_HALF_DOWN} si es par. Nótese que éste modo de redondeo
     * minimiza los errores acumulados cuando es aplicado sobre secuencias de
     * cálculos repetitivos.
     *
     */
    public static int ROUND_HALF_EVEN = BigDecimal.ROUND_HALF_EVEN;
    /**
     * Modo Redondeo hacia la "vecino más cercano" a menos que tanto los vecinos
     * son equidistantes, en cuyo caso no redondear. Se comporta como si
     * ROUND_UP para la fracción descartada si es > 50, cuando es menor a 50 se
     * comporta como ROUND_DOWN, cuando es 50 no se redondea (en el caso de
     * redondeo al 100).
     *
     */
    public static int ROUND_HALF_UP_WITHOUT_MIDDLE = 8;

    /**
     *
     * @param numero Valor BigDecimal al que se le desea aplicar el redondeo
     * @param decimales numero de decimales con los que se desea hacer el
     * redondeo, del punto hacia la derecha <br>decimales</br>
     * @param tipoRedondeo tipo de redondeo a aplicarse
     * @return
     */
    public static BigDecimal redondear(BigDecimal numero, int decimales, int tipoRedondeo) {
        numero = numero.setScale(decimales, tipoRedondeo);
        return numero;
    }

    /**
     * Método para redondear numeros enteros, los redondeos aplican a cifras de
     * parte entera, es decir redondear desde la decena hacia numeros mas
     * grandes.
     *
     * @param numero Valor BigDecimal al que se le desea aplicar el redondeo
     * @param decimales numero de digitos con los que se desea hacer el
     * redondeo, del punto hacia la izquierda (a la decena, centena, etc.)
     * @param tipoRedondeo tipo de redondeo a aplicarse
     * @return el valor redondeado
     */
    public static BigInteger redondear(BigInteger numero, int decimales, int tipoRedondeo) {
        BigDecimal res = new BigDecimal(numero).setScale(decimales, tipoRedondeo);
        return res.toBigInteger();
    }

    /**
     *
     * @param numero Valor double al que se le desea aplicar el redondeo
     * @param decimales numero de decimales con los que se desea hacer el
     * redondeo, del punto hacia la derecha <br>decimales</br>
     * @param tipoRedondeo
     * @return
     */
    public static double redondear(double numero, int decimales, int tipoRedondeo) {
        double resultado;
        BigDecimal res;
        res = new BigDecimal(numero).setScale(decimales, tipoRedondeo);
        resultado = res.doubleValue();
        return resultado;
    }

    public static double redondear(double numero, String tipoDecimal, int tipoRedondeo) {
        double resultado = numero;
        Integer redondeo = getTipoDecimalRedondeo(tipoDecimal);
        if (redondeo != null) {
            resultado = redondear(numero, redondeo, tipoRedondeo);
        }
        return resultado;
    }

    public static BigDecimal redondearSinMitad(BigDecimal numero, int decimales, int tipoRedondeo) {
//        double resultado;
        BigDecimal res;
        if (tipoRedondeo == ROUND_HALF_UP_WITHOUT_MIDDLE) {

            double valorDeComparacion = 0;
            double cantidadDeRedondeo = getRedondeoTipoDecimal(decimales);
            valorDeComparacion = cantidadDeRedondeo * 5;

            if (numero.doubleValue() % valorDeComparacion == 0) {
                res = numero;
            } else {
                res = redondear(numero, decimales, ROUND_HALF_UP);
            }

        } else {
            res = redondear(numero, decimales, tipoRedondeo);
        }
//        resultado = res.doubleValue();
        return res;
    }

    public static double redondearSinMitad(double numero, int decimales, int tipoRedondeo) {
        double resultado;
        BigDecimal res = redondearSinMitad(new BigDecimal(numero), decimales, tipoRedondeo);
        resultado = res.doubleValue();
        return resultado;
    }

    public static Double getRedondeoTipoDecimal(int cantidadRedondeo) {
        Double decimalesRedondeo = null;
        switch (cantidadRedondeo) {
            case -6:
                decimalesRedondeo = 100000.0;
                break;
            case -5:
                decimalesRedondeo = 10000.0;
                break;
            case -4:
                decimalesRedondeo = 1000.0;
                break;
            case -3:
                decimalesRedondeo = 100.0;
                break;
            case -2:
                decimalesRedondeo = 10.0;
                break;
            case -1:
                decimalesRedondeo = 1.0;
                break;
            case 0:
                decimalesRedondeo = 0.1;
                break;
            case 1:
                decimalesRedondeo = 0.01;
                break;
            case 2:
                decimalesRedondeo = 0.001;
                break;
            case 3:
                decimalesRedondeo = 0.0001;
                break;
            case 4:
                decimalesRedondeo = 0.00001;
                break;
            case 5:
                decimalesRedondeo = 0.000001;
                break;
            case 6:
                decimalesRedondeo = 0.0000001;
                break;
            default:
                decimalesRedondeo = 1.0;
                break;

        }
        return decimalesRedondeo;

    }

    public static Integer getTipoDecimalRedondeo(String decimalesRedondeo) {
        Integer redondeo = null;
        if (StringUtils.isNotBlank(decimalesRedondeo)) {
            try {
                redondeo = getTipoDecimalRedondeo(Integer.parseInt(decimalesRedondeo.trim()));
            } catch (Exception ex) {
                UtilLog.logDebug(Redondeo.class, "valor no válido :" + decimalesRedondeo + " to parse int", ex);
            }
        }
        return redondeo;
    }

    public static Integer getTipoDecimalRedondeo(int decimalesRedondeo) {
        Integer redondeo = null;
        switch (decimalesRedondeo) {
            case 1000000:
                redondeo = -6;
                break;
            case 100000:
                redondeo = -5;
                break;
            case 10000:
                redondeo = -4;
                break;
            case 1000:
                redondeo = -3;
                break;
            case 100:
                redondeo = -2;
                break;
            case 10:
                redondeo = -1;
                break;
            case 1:
                redondeo = 0;
                break;
            case -10:
                redondeo = 1;
                break;
            case -100:
                redondeo = 2;
                break;
            case -1000:
                redondeo = 3;
                break;
            case -10000:
                redondeo = 4;
                break;
            case -100000:
                redondeo = 5;
                break;
            case -1000000:
                redondeo = 6;
                break;
            default:
                redondeo = 0;
                break;

        }
        return redondeo;

    }

    public static void main(String[] args) {

        double redondeado = redondear(2500, -3, ROUND_HALF_UP);
        System.out.println("redondeado = " + redondeado);
//          
//          redondeado=redondear(944500, -4, ROUND_HALF_UP);
//          System.out.println("redondeado ROUND_HALF_UP -4 = " + redondeado);
//          
//          redondeado=redondear(944500, -4, ROUND_HALF_DOWN);
//          System.out.println("redondeado ROUND_HALF_DOWN -4 = " + redondeado);
//          
//          redondeado=redondear(944500, -4, ROUND_HALF_EVEN);
//          System.out.println("redondeado ROUND_HALF_EVEN -4 = " + redondeado);
//          
//          redondeado=redondear(944500, -3, ROUND_HALF_UP);
//          System.out.println("redondeado ROUND_HALF_UP -3 = " + redondeado);
//          
//          redondeado=redondear(944500, -3, ROUND_HALF_DOWN);
//          System.out.println("redondeado ROUND_HALF_DOWN -3 = " + redondeado);
//          
//          redondeado=redondear(944500, -3, ROUND_HALF_EVEN);
//          System.out.println("redondeado ROUND_HALF_EVEN -3 = " + redondeado);
//      
//          redondeado=redondear(944500, -2, ROUND_HALF_UP);
//          System.out.println("redondeado ROUND_HALF_UP -2 = " + redondeado);
//          
//          redondeado=redondear(944500, -2, ROUND_HALF_DOWN);
//          System.out.println("redondeado ROUND_HALF_DOWN -2 = " + redondeado);
//          
//          redondeado=redondear(944500, -2, ROUND_HALF_EVEN);
//          System.out.println("redondeado ROUND_HALF_EVEN -2 = " + redondeado);
//      
        System.out.println("\n");
//        redondeado = redondear(944500, -3, ROUND_HALF_UP);
//        System.out.println("redondeado " + 944500 + " ROUND_HALF_UP -3 = " + redondeado);
//
//        redondeado = redondear(944500, -3, ROUND_HALF_DOWN);
//        System.out.println("redondeado " + 944500 + " ROUND_HALF_DOWN -3 = " + redondeado);
//
//        redondeado = redondear(944500, -3, ROUND_HALF_EVEN);
//        System.out.println("redondeado " + 944500 + " ROUND_HALF_EVEN -3 = " + redondeado);
//        System.out.println("\n");
//        redondeado = redondear(943500, -3, ROUND_HALF_UP);
//        System.out.println("redondeado " + 943500 + " ROUND_HALF_UP -3 = " + redondeado);
//
//        redondeado = redondear(943500, -3, ROUND_HALF_DOWN);
//        System.out.println("redondeado " + 943500 + " ROUND_HALF_DOWN -3 = " + redondeado);
//
//        redondeado = redondear(943500, -3, ROUND_HALF_EVEN);
//        System.out.println("redondeado " + 943500 + " ROUND_HALF_EVEN -3 = " + redondeado);
//        System.out.println("\n");
//        redondeado = redondear(946500, -3, ROUND_HALF_UP);
//        System.out.println("redondeado " + 946500 + " ROUND_HALF_UP -3 = " + redondeado);
//
//        redondeado = redondear(946500, -3, ROUND_HALF_DOWN);
//        System.out.println("redondeado " + 946500 + " ROUND_HALF_DOWN -3 = " + redondeado);
//
//        redondeado = redondear(946500, -3, ROUND_HALF_EVEN);
//        System.out.println("redondeado " + 946500 + " ROUND_HALF_EVEN -3 = " + redondeado);
//        System.out.println("\n");
//        redondeado = redondear(947500, -3, ROUND_HALF_UP);
//        System.out.println("redondeado " + 947500 + " ROUND_HALF_UP -3 = " + redondeado);
//
//        redondeado = redondear(947500, -3, ROUND_HALF_DOWN);
//        System.out.println("redondeado " + 947500 + " ROUND_HALF_DOWN -3 = " + redondeado);
//
//        redondeado = redondear(947500, -3, ROUND_HALF_EVEN);
//        System.out.println("redondeado " + 947500 + " ROUND_HALF_EVEN -3 = " + redondeado);
        redondeado = redondear(943500, -3, ROUND_HALF_UP);
        System.out.println("redondeado " + 943500 + " ROUND_HALF_UP -3 = " + redondeado);

        redondeado = redondear(943500, -3, ROUND_HALF_DOWN);
        System.out.println("redondeado " + 943500 + " ROUND_HALF_DOWN -3 = " + redondeado);

        redondeado = redondear(943500, -3, ROUND_HALF_EVEN);
        System.out.println("redondeado " + 943500 + " ROUND_HALF_EVEN -3 = " + redondeado);
        System.out.println("\n");

        redondeado = redondear(943499, -3, ROUND_HALF_UP);
        System.out.println("redondeado " + 943499 + " ROUND_HALF_UP -3 = " + redondeado);

        redondeado = redondear(943499, -3, ROUND_HALF_DOWN);
        System.out.println("redondeado " + 943499 + " ROUND_HALF_DOWN -3 = " + redondeado);

        redondeado = redondear(943499, -3, ROUND_HALF_EVEN);
        System.out.println("redondeado " + 943499 + " ROUND_HALF_EVEN -3 = " + redondeado);
        System.out.println("\n");
        System.out.println("2012-11-28- En conclusión NO debe usarse ROUND_HALF_EVEN en reglas del SIT. Debe usarse ROUND_HALF_UP.");

        double mesesMora = 0.0;
        mesesMora = (2781.0 / 30.0);
        System.out.println("mesesMora " + mesesMora);
        mesesMora = Redondeo.redondear((92.37584), 0, Redondeo.ROUND_HALF_UP);
        System.out.println("mesesMora " + mesesMora);





        System.out.println("\n\n");


        redondeado = redondearSinMitad(103605.5, 0, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 103605.5 + " al 0 = " + redondeado);

        redondeado = redondearSinMitad(10365, -1, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 10365 + " al -1 = " + redondeado);

        redondeado = redondearSinMitad(10350, -2, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 10350 + " al -2 = " + redondeado);

        redondeado = redondearSinMitad(10500, -3, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 10500 + " al -3 = " + redondeado);

        redondeado = redondearSinMitad(15000, -4, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 15000 + " al -4 = " + redondeado);

        redondeado = redondearSinMitad(150000, -5, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 150000 + " al -5 = " + redondeado);

        redondeado = redondearSinMitad(1500000, -6, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 1500000 + " al -6 = " + redondeado);


        redondeado = redondearSinMitad(103605.05, 1, ROUND_HALF_UP);
        System.out.println("redondeado " + 103605.05 + " ROUND_HALF_UP al 1 = " + redondeado);

        redondeado = redondearSinMitad(103605.55, -3, ROUND_HALF_UP);
        System.out.println("redondeado " + 103605.55 + " ROUND_HALF_UP al -3 = " + redondeado);

        redondeado = redondearSinMitad(1407000, -6, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 1407000 + " al -6 = " + redondeado);

        redondeado = redondearSinMitad(1509000, -6, ROUND_HALF_UP_WITHOUT_MIDDLE);
        System.out.println("redondeado " + 1509000 + " al -6 = " + redondeado);

        BigDecimal bigDecimal = new BigDecimal("3668526.643459008320");
        bigDecimal = redondear(bigDecimal, -2, ROUND_HALF_UP);
        System.out.println("bigDecimal " + bigDecimal.toPlainString());
        //-2 -> 3668500
        //2 -> 3668526.64

    }
}
