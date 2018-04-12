/**
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.corptx.commons.utils.beans;

import com.corptx.commons.utils.lang.UtilString;
import com.corptx.commons.utils.reflection.UtilReflection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import javax.persistence.Version;
import sun.reflect.misc.FieldUtil;


import static com.corptx.commons.utils.log.UtilLog.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author William Diaz Pabón
 *
 */
public class UtilsBean {

    private static Log log = LogFactory.getLog(UtilsBean.class);
    private static int NIVEL_PROFUNNDIDAD_MAXIMO = 3;
    private static int NIVEL_PROFUNNDIDAD_DEFAULT = 0;

    public static boolean isNotEmpty(Object objeto) throws Exception {
        return !isEmpty(objeto);
    }

    public static boolean isEmpty(Object objeto) throws Exception {
        boolean isNullOrEmpty = true;
        try {
            if (objeto != null) {
                Map mapObject = BeanUtils.describe(objeto);

                Iterator it = mapObject.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    if (!entry.getKey().equals("class")
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue().toString())) {
                        isNullOrEmpty = false;
                        break;
                    }

                }
            }
        } catch (Exception e) {
            throw new Exception("No pudo verificar el objeto: "
                    + e.toString(), e);
        }
        return isNullOrEmpty;
    }

    public static boolean isPropertyEmpty(Object value) {
        boolean isEmpty = false;
        if ((value == null)
                || ((value != null)
                && (value instanceof String)
                && StringUtils.isBlank((String) value))) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static boolean isNotPropertyEmpty(Object value) {
        return !isPropertyEmpty(value);
    }

    public static Object getProperty(Object obj, String nameProperty) throws IllegalArgumentException, Exception {
        Object objValue = null;

        if (obj != null && StringUtils.isNotBlank(nameProperty)) {
            Field[] fields = FieldUtil.getDeclaredFields(obj.getClass());
            for (Field field : fields) {
                if (field != null && StringUtils.equals(field.getName(), nameProperty)) {

                    String strMethodGet =
                            "get" + UtilString.converterStrToNameClass(field.getName(), "_");

                    Method methodGet = UtilReflection.getMethodFromClass(obj.getClass(), strMethodGet);

                    if (methodGet != null) {
                        Object[] param = null;

                        // Obteniendo valor de la propiedad
                        objValue = methodGet.invoke(obj, param);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Argumentos no validos.");
        }
        return objValue;
    }

    /**
     * Permite copiar los valores de las propiedades que no sean nulos del
     * objOrigen en el objeto objDestino.
     *
     * @param objOrigen
     * @param objDestino
     * @return
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public static void copyProperties(Object objOrigen, Object objDestino) throws IllegalArgumentException, Exception {
        if (objOrigen != null && objDestino != null) {
            try {

                Field[] fields = FieldUtil.getDeclaredFields(objOrigen.getClass());
                for (Field field : fields) {
                    if (field.getAnnotation(Version.class) == null) {
                        if (!StringUtils.contains(field.getType().getName(), "List")
                                && !StringUtils.contains(field.getType().getName(), "entity")) {
                            Object valorOrigen = null;
                            try {
                                valorOrigen = UtilsBean.getProperty(objOrigen, field.getName());
                            } catch (Exception e) {
                                logError(UtilsBean.class, "Error obteniendo el valor de la propiedad origen" + field.getName());
                            }
                            if (valorOrigen != null) {
                                try {
                                    BeanUtils.copyProperty(objDestino, field.getName(), valorOrigen);
                                } catch (Exception e) {
                                    logError(UtilsBean.class, "Error copiando el valor " + valorOrigen + " en la propiedad destino " + field.getName(), e);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new Exception("Error copiando propiedades. ", e);
            }
        } else {
            throw new IllegalArgumentException("Los argumentos no son validos.");
        }
    }

    /**
     * Copia las propiedades del objeto origen al objeto destino incluyendo las
     * propiedas que son una entidad, al nivel qeu se indiqeu como parametro
     * objOrigen en el objeto objDestino.
     *
     * @param objOrigen
     * @param objDestino
     * @param nivelProfundidad [0-3] si esta fuera de ese rango lo realizara a
     * primer nivel
     * @return
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public static void copyProperties(Object objOrigen, Object objDestino, int nivelProfundidad) throws IllegalArgumentException, Exception {
        if (nivelProfundidad < NIVEL_PROFUNNDIDAD_DEFAULT || nivelProfundidad > NIVEL_PROFUNNDIDAD_MAXIMO) {
            nivelProfundidad = NIVEL_PROFUNNDIDAD_DEFAULT;
        }
        while (nivelProfundidad >= NIVEL_PROFUNNDIDAD_DEFAULT) {
            if (objOrigen != null && objDestino != null) {

                try {
                    // 
                    Field[] fieldsSuperClass = FieldUtil.getDeclaredFields(objOrigen.getClass().getSuperclass());

                    for (Field field : fieldsSuperClass) {

                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);

                        if (!StringUtils.contains(field.getType().getName(), "List")
                                && !StringUtils.contains(field.getType().getName(), "entity")
                                && field.getAnnotation(Version.class) == null) {

                            Object valueField = field.get(objOrigen);
                            BeanUtils.copyProperty(objDestino, field.getName(), valueField);
                        }
                        field.setAccessible(accessible);
                    }

                    Field[] fields = FieldUtil.getDeclaredFields(objOrigen.getClass());

                    for (Field field : fields) {

                        boolean accessible = field.isAccessible();

                        try {
                            field.setAccessible(true);
                            if (!StringUtils.contains(field.getType().getName(), "List")) {

                                if (!StringUtils.contains(field.getType().getName(), "entity")) {

                                    if (field.getAnnotation(Version.class) == null) {

                                        Object valorOrigen = null;
                                        try {
                                            valorOrigen = UtilsBean.getProperty(objOrigen, field.getName());

                                        } catch (Exception e) {
                                            logError(UtilsBean.class, "Error obteniendo el valor de la propiedad origen" + field.getName());
                                        }
                                        if (valorOrigen != null) {
                                            try {

                                                BeanUtils.copyProperty(objDestino, field.getName(), valorOrigen);

                                            } catch (Exception e) {
                                                logError(UtilsBean.class, "Error copiando el valor " + valorOrigen + " en la propiedad destino " + field.getName(), e);
                                            }
                                        }
                                    }
                                } else {
                                    Object newInstance = UtilReflection.newInstanceFactory(field.getType().getName());
                                    Object newOrigen = field.get(objOrigen);
                                    if (newOrigen != null && newInstance != null) {
                                        copyProperties(newOrigen, newInstance, nivelProfundidad--);
                                        BeanUtils.copyProperty(objDestino, field.getName(), newInstance);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            throw new Exception("Error copiando propiedades. ", ex);
                        } finally {
                            field.setAccessible(accessible);
                        }
                    }
                } catch (Exception e) {
                    throw new Exception("Error copiando propiedades. ", e);
                }
            } else {
                throw new IllegalArgumentException("Los argumentos no son validos.");
            }
            nivelProfundidad--;
        }

    }

    /*
     * 2013-05-01 Alexander Quintero
     * Método agregado para persistir una deuda predio nueva, evitando que caiga en recursividad, basado en el método copyProperties pero cambiada la condición inicial.
     */
    public static void copyPropertiesDeudaPredio(Object objOrigen, Object objDestino, int nivelProfundidad) throws IllegalArgumentException, Exception {
        if (nivelProfundidad > NIVEL_PROFUNNDIDAD_MAXIMO) {
            nivelProfundidad = NIVEL_PROFUNNDIDAD_DEFAULT;
        }
        while (nivelProfundidad >= NIVEL_PROFUNNDIDAD_DEFAULT) {
            if (objOrigen != null && objDestino != null) {

                try {
                    // 
                    Field[] fieldsSuperClass = FieldUtil.getDeclaredFields(objOrigen.getClass().getSuperclass());

                    for (Field field : fieldsSuperClass) {

                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);

                        if (!StringUtils.contains(field.getType().getName(), "List")
                                && !StringUtils.contains(field.getType().getName(), "entity")
                                && field.getAnnotation(Version.class) == null) {

                            Object valueField = field.get(objOrigen);
                            BeanUtils.copyProperty(objDestino, field.getName(), valueField);
                        }
                        field.setAccessible(accessible);
                    }

                    Field[] fields = FieldUtil.getDeclaredFields(objOrigen.getClass());

                    for (Field field : fields) {

                        boolean accessible = field.isAccessible();

                        try {
                            field.setAccessible(true);
//                            logDebug(UtilsBean.class, "Tipo: " + field.getType().getName() + "   nombre: " + field.getName() + "   valor:   " + UtilsBean.getProperty(objOrigen, field.getName()) + "  profundidad: " + nivelProfundidad);
                            if (!StringUtils.contains(field.getType().getName(), "List")) {

                                if (!StringUtils.contains(field.getType().getName(), "entity")) {

                                    if (field.getAnnotation(Version.class) == null) {

                                        Object valorOrigen = null;
                                        try {
                                            valorOrigen = UtilsBean.getProperty(objOrigen, field.getName());

                                        } catch (Exception e) {
                                            logError(UtilsBean.class, "Error obteniendo el valor de la propiedad origen" + field.getName());
                                        }
                                        if (valorOrigen != null) {
                                            try {

                                                BeanUtils.copyProperty(objDestino, field.getName(), valorOrigen);

                                            } catch (Exception e) {
                                                logError(UtilsBean.class, "Error copiando el valor " + valorOrigen + " en la propiedad destino " + field.getName(), e);
                                            }
                                        }
                                    }
                                } else {
                                    Object newInstance = UtilReflection.newInstanceFactory(field.getType().getName());
                                    Object newOrigen = field.get(objOrigen);
                                    if (newOrigen != null && newInstance != null) {
//                                        logDebug(UtilsBean.class, "copyPropertiesDeudaPredio(" + newOrigen + ", " + newInstance + ", " + nivelProfundidad + "--)");
                                        copyPropertiesDeudaPredio(newOrigen, newInstance, nivelProfundidad--);
                                        BeanUtils.copyProperty(objDestino, field.getName(), newInstance);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            throw new Exception("Error copiando propiedades. ", ex);
                        } finally {
                            field.setAccessible(accessible);
                        }
                    }
                } catch (Exception e) {
                    throw new Exception("Error copiando propiedades. ", e);
                }
            } else {
                throw new IllegalArgumentException("Los argumentos no son validos.");
            }
            nivelProfundidad--;
        }

    }
    
    
}
