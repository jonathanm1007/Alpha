/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.math;

import com.corptx.commons.utils.collections.UtilList;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author Alexander
 */
public class UtilNumber {

    /**
     * Conpara un valor para saber si se encuentra en una lista de valores con
     * los cuales comparar
     *
     * @param valorAComparar
     * @param valores
     * @return verdadero si cualquiera de los valores de la lista coincide con
     * el valor a comparar
     */
    public static boolean containsAny(int valorAComparar, Integer... valores) {

        boolean encontrado = false;

        List<Integer> lista = new ArrayList<Integer>();
        if (valores != null) {
            CollectionUtils.addAll(lista, valores);
        }
        if (UtilList.isNotEmpty(lista)) {
            for (Integer valor : lista) {
                if (valorAComparar == valor.intValue()) {
                    encontrado = true;
                }
            }
        }
        return encontrado;
    }

    public static void main(String[] args) {


        double cuotaMensual = 14322780.00;
        double intereses = 2589.76;
        double cuotaMenssualFinal = cuotaMensual + intereses;

        double totalCuotaRedondeado = Redondeo.redondear(cuotaMenssualFinal, -2, Redondeo.ROUND_HALF_EVEN);

        intereses = Redondeo.redondear(intereses, 0, Redondeo.ROUND_HALF_EVEN);

        double abonoCap = totalCuotaRedondeado - intereses;
        double suma = abonoCap + intereses;
        System.out.println("suma = " + String.valueOf(suma));


    }
}
