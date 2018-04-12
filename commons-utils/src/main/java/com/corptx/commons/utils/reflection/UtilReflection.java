/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang.StringUtils;
import sun.reflect.misc.FieldUtil;

/**
 *
 ** @author William Diaz Pabón
 */
public class UtilReflection {

    public static int TIPO_OBJETO_ENTITY = 1;
    public static int TIPO_OBJETO_LISTA_DE_ENTITYS = 2;
    public static int TIPO_OBJETO_LISTA = 3;
    public static int TIPO_OBJETO_LISTA_VACIA = 4;

    /**
     * Obtiene de una clase el metodo con el nombre indicado como parametro
     *
     * @param clase Objeto de tipo Class de la cual se desea obtener el metodo
     * @param strNameMethod Cadena de texto con el nombre del metodo que se
     * desea obtener
     * @return Objeto de tipo Method con la instancia del metodo o null si no se
     * encuentra
     */
    public static Method getMethodFromClass(Class clase, String strNameMethod) {
        Method method = null;
        if (clase != null) {
            Method[] methods = clase.getMethods();
            for (int j = 0; j < methods.length; j++) {
                Method newMethod = methods[j];
                if (newMethod.getName().equalsIgnoreCase(strNameMethod)) {
                    method = newMethod;
                    break;
                }
            }
        }
        return method;
    }

    /**
     * Metodo que determina si existe el nombre del metodo en la clase indicada
     *
     * @param clase
     * @param strNameMethod
     * @return Si existe retorna true de lo contrario retorna false
     */
    public static boolean existMethod(Class clase, String strNameMethod) {
        boolean exist = false;
        if (clase != null) {
            Method[] methods = clase.getMethods();
            for (int j = 0; j < methods.length; j++) {
                Method newMethod = methods[j];
                if (newMethod.getName().equalsIgnoreCase(strNameMethod)) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    /**
     * Metodo que invoca un metodo de un obheto determinado
     *
     * @param method metodo hacer invocado
     * @param target instancia del objeto donde esta el metodo
     * @param args parametros qeu sel le envia al metodo qeu se va a invocar
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Method method, Object target, Object... args) throws Exception {
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw (Exception) ex.getCause();
        }
    }

    /**
     * Metodo que invoca un metodo de un obheto determinado
     *
     * @param strNameMethod nombre del metodo qeu se va a invocar
     * @param target instancia del objeto donde esta el metodo
     * @param args parametros que se envia al metodo que se va a invocar
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(String strNameMethod, Object target, Object... args) throws Exception {
        try {
            if (StringUtils.isNotBlank(strNameMethod)
                    && target != null) {
                if (existMethod(target.getClass(), strNameMethod)) {
                    Method method = getMethodFromClass(target.getClass(), strNameMethod);
                    return method.invoke(target, args);
                } else {
                    throw new Exception("El metodo: " + strNameMethod + "no existe.");
                }
            } else {
                throw new IllegalArgumentException("IllegalArgumentException: parametros nulos.");
            }

        } catch (Exception ex) {
            throw (Exception) ex.getCause();
        }
    }

    public static Object newInstanceFactory(String strNameEntity) throws Exception {
        Object instanceEntity = null;
        if (!StringUtils.isBlank(strNameEntity)) {
            Class classEntity = null;
            try {
                classEntity = Class.forName(strNameEntity);
                instanceEntity = classEntity.newInstance();
            } catch (Exception e) {
                String msg = "No se puede crear la clase " + strNameEntity;
                throw new Exception(msg, e);
            }

        }
        return instanceEntity;
    }

    /*public static List<Class> getRelatedClasses(Class newClass) {
     System.out.println("getRelatedClasses <<INICIO>> ");
     List<Class> listaEntidadesRelacionados = new ArrayList<Class>();
     Field[] fields = FieldUtil.getDeclaredFields(newClass);
     for (Field field : fields) {
    
     Class claseBuscada = null;
     try {
     Type friendsGenericType = field.getGenericType();
     if (friendsGenericType instanceof ParameterizedType) {
     ParameterizedType friendsParameterizedType = (ParameterizedType) friendsGenericType;
     Type[] friendsType = friendsParameterizedType.getActualTypeArguments();
     claseBuscada = (Class) friendsType[0];
     } else {
     claseBuscada = (Class) field.getGenericType();
     }
     if (field.getAnnotation(OneToMany.class) != null) {
     listaEntidadesRelacionados.add(claseBuscada);
     }
     if (field.getAnnotation(ManyToOne.class) != null) {
     listaEntidadesRelacionados.add(claseBuscada);
     }
     if (field.getAnnotation(OneToOne.class) != null) {
     listaEntidadesRelacionados.add(claseBuscada);
     }
     if (field.getAnnotation(ManyToMany.class) != null) {
     listaEntidadesRelacionados.add(claseBuscada);
     }
     } catch (Exception e) {
     System.out.println(e.getMessage() + e);
     }
     }
     System.out.println("getRelatedClasses <<FIN>> ");
     return listaEntidadesRelacionados;
     }*/
    /*public static List<Object> getRelatedClasses(Class newClass) {
     System.out.println("getRelatedClasses <<INICIO>> ");
     List<Object> listaEntidadesRelacionados = new ArrayList<Object>();
     Field[] fields = FieldUtil.getDeclaredFields(newClass);
     for (Field field : fields) {
     List<Object> listaAtributosField = new ArrayList<Object>();
     Class claseBuscada = null;
     try {
     Type friendsGenericType = field.getGenericType();
     if (friendsGenericType instanceof ParameterizedType) {
     ParameterizedType friendsParameterizedType = (ParameterizedType) friendsGenericType;
     Type[] friendsType = friendsParameterizedType.getActualTypeArguments();
     claseBuscada = (Class) friendsType[0];
     } else {
     claseBuscada = (Class) field.getGenericType();
     }
     //Cambiar a una sola sentencia con las 4 condiciones separadas por ||
     if (field.getAnnotation(OneToMany.class) != null) {
     listaAtributosField.add(claseBuscada);
     listaAtributosField.add(field.getName());
     listaEntidadesRelacionados.add(listaAtributosField);
     } else if (field.getAnnotation(ManyToOne.class) != null) {
     listaAtributosField.add(claseBuscada);
     listaAtributosField.add(field.getName());
     listaEntidadesRelacionados.add(listaAtributosField);
     } else if (field.getAnnotation(OneToOne.class) != null) {
     listaAtributosField.add(claseBuscada);
     listaAtributosField.add(field.getName());
     listaEntidadesRelacionados.add(listaAtributosField);
     } else if (field.getAnnotation(ManyToMany.class) != null) {
     listaAtributosField.add(claseBuscada);
     listaAtributosField.add(field.getName());
     listaEntidadesRelacionados.add(listaAtributosField);
     }
     } catch (Exception e) {
     System.out.println(e.getMessage() + e);
     }
     }
     System.out.println("getRelatedClasses <<FIN>> ");
     return listaEntidadesRelacionados;
     }*/
    /**
     * Método que devuelve una lista de Objectos, cuyos objetos son Array de dos
     * posiciones. La primera posición contiene un objeto tipo Class que
     * corresponde a la clase que representa. La segunda posición contiene un
     * String con el nombre del campo en el objeto recibido, que representa la
     * clase buscada.
     *
     * El objetivo es obtener los atributos de la clase, que corresponden a
     * relaciones con otras clases.
     *
     */
    public static List<Object[]> getRelatedClasses(Class newClass) {
        System.out.println("getRelatedClasses <<INICIO>> ");
        List<Object[]> listaEntidadesRelacionados = new ArrayList<Object[]>();
        Field[] fields = FieldUtil.getDeclaredFields(newClass);
        for (Field field : fields) {
            Object[] atributosFieldArray = new Object[2];
            Class claseBuscada = null;
            try {
                Type buscadoGenericType = field.getGenericType();
                if (buscadoGenericType instanceof ParameterizedType) {
                    ParameterizedType buscadoParameterizedType = (ParameterizedType) buscadoGenericType;
                    Type[] buscadoType = buscadoParameterizedType.getActualTypeArguments();
                    claseBuscada = (Class) buscadoType[0];
                } else {
                    claseBuscada = (Class) field.getGenericType();
                }
                if ((field.getAnnotation(OneToMany.class) != null)
                        || (field.getAnnotation(ManyToOne.class) != null)
                        || (field.getAnnotation(OneToOne.class) != null)
                        || (field.getAnnotation(ManyToMany.class) != null)) {
                    atributosFieldArray[0] = (claseBuscada);
                    atributosFieldArray[1] = (field.getName());
                    listaEntidadesRelacionados.add(atributosFieldArray);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + e);
                e.printStackTrace();
            }
        }
        System.out.println("getRelatedClasses <<FIN>> ");
        return listaEntidadesRelacionados;
    }

    /**
     * Método que retorna un listado de nombres, correspondientes a los campos
     * que tiene la Clase recibida por parámetros.
     *
     * Se valida que los campos retornados NO correspondan a relaciones con
     * otras clases, ejemplo: reciboCajaCollection, oiPredioList
     *
     */
    public static List<String> getFields(Class newClass) {
//        System.out.println("getFields <<INICIO>> ");
        List<String> listaCampos = new ArrayList<String>();
        Field[] fields = FieldUtil.getDeclaredFields(newClass);
        for (Field field : fields) {
            //Permitir para poder hacer comparaciones 
            //especialmente en relaciones ciclicas Ej. Liq_corregida dentro de Liq
            /*if ((field.getAnnotation(OneToMany.class) == null)
             && (field.getAnnotation(ManyToOne.class) == null)
             && (field.getAnnotation(OneToOne.class) != null)
             && (field.getAnnotation(ManyToMany.class) == null)) {
             listaCampos.add(field.getName());
             }*/
            listaCampos.add(field.getName());
        }
//        System.out.println("listaCampos -> " + (UtilList.isNotEmpty(listaCampos) ? listaCampos.size() : "vacía"));
//        System.out.println("getFields <<FIN>> ");
        return listaCampos;
    }

    /**
     * Método que verifica el tipo de objeto de un determinado Object. retorna
     * un valor entero que identifica el tipo de objeto encontrado.
     *
     * @param objectToVerify
     * @return mumeroIndicanteDelTipoDeObjetoEncontrado
     */
    public static int verifyObjetType(Object objectToVerify) {

        int tipoObjeto = 0;
        String superClass = objectToVerify.getClass().getSuperclass().getName();

        if (StringUtils.contains(superClass, "entity")) {

            tipoObjeto = TIPO_OBJETO_ENTITY;

        } else if (StringUtils.contains(superClass, "List")
                || StringUtils.contains(superClass, "Vector")) {


            tipoObjeto = TIPO_OBJETO_LISTA;

            if (StringUtils.contains(superClass, "List")) {

                if (((List) objectToVerify).isEmpty()) {

                    tipoObjeto = TIPO_OBJETO_LISTA_VACIA;

                } else if (verifyObjetType(((List) objectToVerify).get(0)) == TIPO_OBJETO_ENTITY) {

                    tipoObjeto = TIPO_OBJETO_LISTA_DE_ENTITYS;
                }
            } else {
                if (((Vector) objectToVerify).isEmpty()) {

                    tipoObjeto = TIPO_OBJETO_LISTA_VACIA;

                } else if (verifyObjetType(((Vector) objectToVerify).get(0)) == TIPO_OBJETO_ENTITY) {

                    tipoObjeto = TIPO_OBJETO_LISTA_DE_ENTITYS;
                }
            }
        }
        return tipoObjeto;
    }
}
