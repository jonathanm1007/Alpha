package com.corptx.commons.utils.collections;

//import com.corptx.sit.sitbase.entity.MenuItem;
import com.corptx.commons.utils.lang.UtilString;
import static com.corptx.commons.utils.log.UtilLog.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;

/**
 *
 ** @author William Diaz Pabón
 */
public class UtilList {

    public static enum SearchType {

        LARGEST_VALUE, LOW_VALUE
    };

    public static enum OrderType {

        ORDER_ASC, ORDER_DESC
    };

    public static boolean isNotEmpty(List lista) {
        boolean notEmpty = false;

        try {
            if (lista != null && lista.size() > 0) {
                notEmpty = true;
            }
        } catch (Exception e) {
            logError(UtilList.class, "Error validando la lista. " + e.getMessage());
//            logError(UtilList.class, "Error validando la lista. " + e.getMessage(), e);
            notEmpty = false;
        }

        return notEmpty;
    }

    public static boolean isEmpty(List lista) {
        return !isNotEmpty(lista);
    }

    /**
     * Permite encontrar un valor numerico dentro de una propiedad de un objeto
     * dentro de una lista que cumpla el tipo de busqueda. <p>Solo soporta por
     * el momento Tipos de Datos que se puedan convertir a BigDecimal</p>
     *
     * <p>Ejemplo:</p> <p>Clase que vamos a usar para la lista</p>
     * <pre>
     * public class Prueba {
     *   private BigDecimal valor;
     *
     *   public Prueba(BigDecimal valor){
     *     this.valor = valor;
     *   }
     *   public BigDecimal getValorFijo() {
     *       return valorFijo;
     *   }
     *   public void setValorFijo(BigDecimal valorFijo) {
     *      this.valorFijo = valorFijo;
     *   }
     * }
     * </pre>
     *
     * <p>Llenado la lista con los objetos a comparar</p>
     *
     * <pre>
     * List lista = new ArrayList();
     * lista.add(new Prueba(new BigDecimal("10")));
     * lista.add(new Prueba(new BigDecimal("30")));
     * lista.add(new Prueba(new BigDecimal("20")));
     * lista.add(new Prueba(new BigDecimal("100")));
     * lista.add(new Prueba(new BigDecimal("50")));
     * </pre>
     *
     * <p>Llamando al metodo para que retorne el valor mayor</p>
     * <pre>
     * Object[] vctValorMayor =
     *          UtilList.findValueList(lista, "valor", UtilList.SearchType.LARGEST_VALUE);
     * vctValorMayor[0] = 3; // Esta es la posición del mayor valor dentro de la lista
     * vctValorMayor[1] = 100; // Objeto BigDecimal que contiene el valor mayor
     * </pre>
     *
     * @param list
     * @param property
     * @param searchType
     * @return
     */
    public static Object[] findValueList(List list, String property, SearchType searchType) {
        Object[] vctObj = null;
        try {

            if (isNotEmpty(list) && StringUtils.isNotBlank(property)) {

                vctObj = new Object[2];
                BigDecimal foundObject = null;

                for (int i = 0; i < list.size(); i++) {

                    Object object = list.get(i);
                    logDebug(UtilList.class, "object = " + object);
                    logDebug(UtilList.class, "property = " + property);

                    try {

                        Object valueProperty = BeanUtils.getProperty(object, property);

                        if (valueProperty != null
                                && UtilString.isNumeric(valueProperty.toString())) {

                            logDebug(UtilList.class, "valueProperty.toString() = " + valueProperty.toString());

                            if (foundObject == null) {

                                foundObject = new BigDecimal(valueProperty.toString());
                                vctObj[0] = i;
                                vctObj[1] = foundObject;

                            } else {
                                BigDecimal big = new BigDecimal(valueProperty.toString());
                                if (big != null) {
                                    switch (searchType) {
                                        case LARGEST_VALUE:
                                            if (big.compareTo(foundObject) == 1) {
                                                foundObject = big;
                                                vctObj[0] = i;
                                                vctObj[1] = foundObject;
                                            }
                                            break;
                                        case LOW_VALUE:
                                            if (big.compareTo(foundObject) == -1) {
                                                foundObject = big;
                                                vctObj[0] = i;
                                                vctObj[1] = foundObject;
                                            }
                                            break;
                                    }
                                }
                            }
                            logDebug(UtilList.class, "foundObject = " + foundObject);
                        }
                    } catch (NoSuchMethodException e) {
                        vctObj = null;
                        logError(UtilList.class, "No se encuentra la propiedad dentro del objeto de la lista", e);
                    }
                }

            } else {
                logError(UtilList.class, "Alguna de las propiedades esta nula: list = " + list + ", property = " + property);
            }
        } catch (Exception e) {
            vctObj = null;
            logError(UtilList.class, "Error al encontrar objeto mayor en la lista", e);
        }

        return vctObj;
    }

    /**
     * Permite buscar dentro de una lista un objeto que en la propiedad indicada
     * sea igual al valor de la propiedad indicada y si lo encuentra el objeto
     * es reemplazado por el parametro nuevoObjecto.
     *
     * @param lista
     * @param propiedad
     * @param valorPropiedad
     * @param nuevoObjecto
     * @return retorna <code>true</code> si el proceso de reemplazar es
     * ejecutado con exito, de lo contrario retornara <code>false</code>
     */
    public static boolean remplace(List lista, String propiedad, String valorPropiedad, Object nuevoObjecto) {
        logDebug(UtilList.class, "Entro a remplace(List lista, String propiedad, String valorPropiedad, Object nuevoObjecto)");
        boolean exito = false;
        try {
            logDebug(UtilList.class, "lista = " + lista);
            logDebug(UtilList.class, "propiedad = " + propiedad);
            logDebug(UtilList.class, "valorPropiedad = " + valorPropiedad);
            logDebug(UtilList.class, "nuevoObjecto = " + nuevoObjecto);
            if (isNotEmpty(lista) && StringUtils.isNotBlank(propiedad)
                    && StringUtils.isNotBlank(valorPropiedad)
                    && nuevoObjecto != null) {
                logDebug(UtilList.class, "Todos los parametros OK.");
                for (int i = 0; i < lista.size(); i++) {
                    Object object = lista.get(i);
                    logDebug(UtilList.class, "object = " + object);
                    logDebug(UtilList.class, "StringUtils.equals(object.getClass().getName(), nuevoObjecto.getClass().getName()) = " + StringUtils.equals(object.getClass().getName(), nuevoObjecto.getClass().getName()));
                    if (StringUtils.equals(object.getClass().getName(), nuevoObjecto.getClass().getName())) {
                        String strValorpropiedadLista =
                                BeanUtils.getProperty(object, propiedad);
                        logDebug(UtilList.class, "strValorpropiedadLista = " + strValorpropiedadLista);
                        logDebug(UtilList.class, "StringUtils.equals(strValorpropiedadLista, valorPropiedad) = " + StringUtils.equals(strValorpropiedadLista, valorPropiedad));
                        if (StringUtils.equals(strValorpropiedadLista, valorPropiedad)) {
                            lista.set(i, nuevoObjecto);
                            exito = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            exito = false;
            logError(UtilList.class, "Error al reemplazar objeto dentro de una lista. ", e);
        }
        logDebug(UtilList.class, "Saliendo de remplace(List lista, String propiedad, String valorPropiedad, Object nuevoObjecto) => " + exito);
        return exito;
    }

    /**
     * Metodo que permite buscar un objeto dentro de una lista por el nombre y
     * valor de una propiedad que debe estar en String
     *
     * @param list
     * @param nameProperty
     * @param valueProperty
     * @return
     */
    public static Object find(List list, String nameProperty, String valueProperty) throws Exception {
        Object object = null;

        if (isNotEmpty(list)
                && StringUtils.isNotBlank(nameProperty)
                && StringUtils.isNotBlank(valueProperty)) {
            try {
                for (int i = 0; i < list.size(); i++) {
                    object = list.get(i);
                    logDebug(UtilList.class, "object = " + object);

                    String strValorPropiedadLista =
                            BeanUtils.getProperty(object, nameProperty);
                    logDebug(UtilList.class, "strValorPropiedadLista = " + strValorPropiedadLista);
                    logDebug(UtilList.class, "StringUtils.equals(strValorpropiedadLista, valorPropiedad) = " + StringUtils.equals(strValorPropiedadLista, valueProperty));
                    if (StringUtils.equals(strValorPropiedadLista, valueProperty)) {
                        break;
                    } else {
                        object = null;
                    }

                }
            } catch (Exception e) {
                throw new Exception("Error buscando objeto en la lista. ", e);
            }

        }
        return object;
    }

    /**
     * Metodo que busca un objeto de la lista donde la propiedad posea el valor
     * indicado como parametro y lo elimina de la lista, retornando el objeto
     * eliminado de la lista
     *
     * @param list
     * @param nameProperty
     * @param valueProperty
     * @return
     * @throws Exception
     */
    public static Object remove(List list, String nameProperty, String valueProperty) throws Exception {
        Object object = null;

        if (isNotEmpty(list)
                && StringUtils.isNotBlank(nameProperty)
                && StringUtils.isNotBlank(valueProperty)) {
            try {
                int i = 0;
                for (; i < list.size(); i++) {
                    object = list.get(i);
                    logDebug(UtilList.class, "object = " + object);

                    String strValorPropiedadLista =
                            BeanUtils.getProperty(object, nameProperty);
                    logDebug(UtilList.class, "strValorPropiedadLista = " + strValorPropiedadLista);
                    logDebug(UtilList.class, "StringUtils.equals(strValorpropiedadLista, valorPropiedad) = " + StringUtils.equals(strValorPropiedadLista, valueProperty));
                    if (StringUtils.equals(strValorPropiedadLista, valueProperty)) {
                        break;
                    }
                }

                if (i < list.size() && object != null) {
                    list.remove(object);
                }
            } catch (Exception e) {
                throw new Exception("Error buscando objeto en la lista. ", e);
            }

        }
        return object;
    }

    /**
     * Creado William Diaz Pabón - 2010-09-09 <p> Ordena una lista de JavaBeans
     * comparandolos por la propiedad indicada </p>
     *
     * @param list
     * @param nameProperty
     * @throws Exception
     */
    public static void sort(List list, final String nameProperty) throws Exception {

        if (list != null && StringUtils.isNotBlank(nameProperty)) {


            Collections.sort(list, new Comparator() {
                @Override
                public int compare(Object objSource, Object objTarget) {

                    Class classObjSource = objSource.getClass();

                    String strMethodGet = "get" + UtilString.converterStrToNameClass(nameProperty, "");
                    //logDebug(UtilList.class, "strMethodGet = " + strMethodGet);


                    try {
                        Method methodProperty = classObjSource.getMethod(strMethodGet);

                        if (methodProperty != null) {

                            Object valuePropertySource = methodProperty.invoke(objSource);
                            Object valuePropertyTarget = methodProperty.invoke(objTarget);

                            //Comparando las propiedades que soporten el Comparable
                            if (valuePropertySource instanceof Comparable
                                    && valuePropertyTarget instanceof Comparable) {

                                Comparable cmpValuePropertySource = (Comparable) valuePropertySource;
                                Comparable cmpValuePropertyTarget = (Comparable) valuePropertyTarget;

                                return cmpValuePropertySource.compareTo(cmpValuePropertyTarget);

                            } else { // Comparando con el equals
                                if (valuePropertySource.equals(valuePropertyTarget)) {
                                    return 0;
                                } else {
                                    return 1;
                                }
                            }

                        } else {
                            throw new Exception("No se encontro el metodo " + strMethodGet);
                        }

                    } catch (Exception e) {
                        logError(UtilList.class, "Error obteniendo el método " + strMethodGet);
                    }


                    return 0;
                }
            });
        } else {
            throw new IllegalArgumentException("Parametros no validos para ordenar la lista.");
        }
    }

    /**
     * Creado Alexander Quintero Duarte - 2014-04-01 <p> Ordena una lista de
     * JavaBeans comparandolos por la propiedad indicada y ordenandolos según el
     * tipo de orden establecido </p>
     * <p> Este método también permite ordenar la lista por una subpropiedad de
     * los objetos de la lista. Por ejemplo: llega una lista de
     * detalleDeudaPredio y se desea ordenar por la vigencia de la deudaPredio,
     * para esto se envía el nombre de la propiedad de la siquiente manera:
     * "deudaPredio.vigencia". Acepta cualquier nivel de profundidad.</p>
     *
     * @param list Lista de objetos a ordenar
     * @param nameProperty nombre de la propiedad de los objetos de la lista por
     * la que se va a ordenar
     * @param typeOrder tipo de orden que se utilizará para ordenar
     * @throws Exception
     */
    public static void sort(List list, final String nameProperty, final OrderType typeOrder) throws Exception {

        if (list != null && StringUtils.isNotBlank(nameProperty)) {

            Collections.sort(list, new Comparator() {
                @Override
                public int compare(Object objSource, Object objTarget) {

                    Object objSourceTmp = objSource;
                    Object objTargetTmp = objTarget;
                    Class classObjSource = objSourceTmp.getClass();
                    String strMethodGet = "";

                    if (nameProperty.contains(".")) {

                        try {
                            String[] valueProperties = nameProperty.split("[.]");

                            for (int i = 0; i < valueProperties.length - 1; i++) {

                                objSourceTmp = (classObjSource.getMethod("get" + UtilString.converterStrToNameClass(valueProperties[i], ""))).invoke(objSourceTmp);
                                objTargetTmp = (classObjSource.getMethod("get" + UtilString.converterStrToNameClass(valueProperties[i], ""))).invoke(objTargetTmp);
                                classObjSource = objSourceTmp.getClass();
                                strMethodGet = "get" + UtilString.converterStrToNameClass(valueProperties[i + 1], "");
                            }

                        } catch (Exception e) {
                            logError(UtilList.class, "Error intentando ordenar la lista por subpropiedades = " + e.getMessage());
                        }

                    } else {
                        strMethodGet = "get" + UtilString.converterStrToNameClass(nameProperty, "");
                    }

                    try {
                        Method methodProperty = classObjSource.getMethod(strMethodGet);

                        if (methodProperty != null) {

                            Object valuePropertySource = methodProperty.invoke(objSourceTmp);
                            Object valuePropertyTarget = methodProperty.invoke(objTargetTmp);

                            //Comparando las propiedades que soporten el Comparable
                            if (valuePropertySource instanceof Comparable
                                    && valuePropertyTarget instanceof Comparable) {

                                Comparable cmpValuePropertySource = (Comparable) valuePropertySource;
                                Comparable cmpValuePropertyTarget = (Comparable) valuePropertyTarget;

                                return cmpValuePropertySource.compareTo(cmpValuePropertyTarget) * (OrderType.ORDER_ASC.equals(typeOrder) ? 1 : -1);

                            } else { // Comparando con el equals
                                if (valuePropertySource.equals(valuePropertyTarget)) {
                                    return 0;
                                } else {
                                    return (OrderType.ORDER_ASC.equals(typeOrder) ? 1 : -1);
                                }
                            }

                        } else {
                            throw new Exception("No se encontro el metodo " + strMethodGet);
                        }

                    } catch (Exception e) {
                        logError(UtilList.class, "Error obteniendo el método " + strMethodGet);
                    }
                    return 0;
                }
            });

        } else {
            throw new IllegalArgumentException("Parametros no validos para ordenar la lista.");
        }
    }

    /**
     * Autor: William Diaz Pabón, 2012-07-10
     *
     * Metodo que permite buscar un objeto dentro de una lista por el nombre y
     * valor de una propiedad (Soporta cualquier tipo de dato de la propiedad)
     *
     * @param list
     * @param nameProperty
     * @param valueProperty
     * @return Retorna el Objeto encontrado dentro de lista de acuerdo a los
     * parametros dados.
     *
     */
    public static Object find(List list, String nameProperty, Object valueProperty) throws Exception {

        Object object = null;

        // Verificando el parametro de la lista
        if (isEmpty(list)) {
            throw new IllegalArgumentException("El parametro list no es valido. [list=" + list + "]");
        }

        // Verificando el parametro del nombre de la propiedad
        if (StringUtils.isBlank(nameProperty)) {
            throw new IllegalArgumentException("El parametro nameProperty no es valido. [nameProperty=" + nameProperty + "]");
        }

        // Verificando el parametro del valor de la propiedad
        if (valueProperty == null) {
            throw new IllegalArgumentException("El parametro valueProperty no es valido. [valueProperty=" + valueProperty + "]");
        }

        try {

            for (Object object1 : list) {

                // Obteniendo el valor de la propiedad 
                Object objValue = BeanUtilsBean.getInstance().getPropertyUtils().getProperty(object1, nameProperty);
                System.out.println("objValue = " + objValue);
                System.out.println("valueProperty = " + valueProperty);

                // Verificando si se encontro el objeto
                if (objValue.equals(valueProperty)) {
                    object = object1;
                    break;
                }

            }

        } catch (Exception e) {

            throw new Exception("Error buscando objeto en la lista. ", e);
        }

        return object;
    }
}
