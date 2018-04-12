/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.persistence;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

// Imports Estaticos

/**
 *
 ** @author William Diaz Pabón
 */
public class UtilJPA {

    public static enum TypeFunctions {

        COUNT
    };

    public static FilterParameter[] getFilterParameterNotBlank(FilterParameter[] params) {
        FilterParameter[] parametros = null;
        List<FilterParameter> lista = null;

        if (params != null && params.length > 0) {
            lista = new ArrayList();
            for (FilterParameter fP : params) {
                String valueParam = null;

                if (fP.isValidarParam()) {

                    if (fP.getValue() instanceof String) {
                        if ((((String) fP.getValue()).trim().length() == 0)
                                || StringUtils.isBlank((String) fP.getValue())) {
                            fP.setValue(null);
                        }
                    }

                    if (fP.isLike()) {
                        if (StringUtils.isNotBlank((String) fP.getValue())) {
                            valueParam = (String) fP.getValue();
                            if (fP.isUpper()) {
                                valueParam = valueParam.toUpperCase();
                            }
                            valueParam = valueParam.replace(' ', '%');
                            valueParam = "%" + valueParam + "%";
                            fP.setValue(valueParam);
                        } else {
                            fP.setValue(null);
                        }
                    }

                    if (fP.getValue() != null) {
                        lista.add(fP);
                    }
                } else {
                    lista.add(fP);
                }
            }

            parametros = lista.toArray(new FilterParameter[0]);
        }
        return parametros;
    }

    /**
     * Metodo que crea dinamicamente el SQL para realizar una consulta de un
     * entity por los campos que posean valores, el metodo verifica que los
     * parametros no esten vacios, si detecta un parametro null o empty no lo
     * tiene en cuenta para el sql
     * <br>
     * @param entity, clase de la entidad en la que se realizará la consulta
     * @param sortColumn, nombre de la columna por la cual se desea ordenar
     * @param ascending, true = ascendente, false = descendente
     * @param or, true usa el operador logico OR, false usa el operador logico AND
     * @param params, vector de FilterParameter que contiene los parametros a consultar
     * <br>
     * @return Object[] donde en la posición 0 se almacena el SQL generado y en
     * la posición 1 se almacena el vector de FilterParameter[] conlos
     * parametros ajustdos para la consulta
     */
    public static Object[] generateSQLSelect(Class entity, String sortColumn, boolean ascending, boolean or, FilterParameter[] params) {
        final String entityName = entity.getSimpleName();
        StringBuffer sbQuery = new StringBuffer("select e from ").append(entityName).append(" e");
        FilterParameter[] paramsReal, paramsSQL = null;
        boolean first = true;
        int i = 0;
        if (params != null && params.length > 0) {
            paramsReal = UtilJPA.getFilterParameterNotBlank(params);
            paramsSQL = new FilterParameter[paramsReal.length];
            for (FilterParameter fP : paramsReal) {
                String operator = " = ";
                if ((fP.getValue() instanceof String)) {
                    operator = " like ";
                }

                if (fP.getValue() != null) {
                    if (first) {
                        sbQuery.append(" where e.").append(fP.getField()).append(operator).append(" :").append("p_").append(fP.getField().replace(".", "_"));
                    } else {
                        sbQuery.append(or ? " or" : " and").append(" e.").append(fP.getField()).append(operator).append(" :").append("p_").append(fP.getField().replace(".", "_"));
                    }
                    first = false;

                    FilterParameter fPAux = new FilterParameter("p_" + fP.getField().replace(".", "_"), fP.getValue());
                    paramsSQL[i] = fPAux;
                    i++;
                }
            }// Fin de for
        }//Fin del if

        if (ascending) {
            sbQuery.append(" order by e.").append(sortColumn).append(" asc");
        } else {
            sbQuery.append(" order by e.").append(sortColumn).append(" desc");
        }

        Object[] rta = new Object[]{sbQuery.toString(), paramsSQL};
        return rta;
    }

    public static String generateSQLSelect(Class entity, TypeFunctions typeFunctions, String alias, FilterParameter[] params) {
        final String entityName = entity.getSimpleName();
        StringBuffer sbQuery = null;
//        FilterParameter[] paramsReal, paramsSQL = null;
        FilterParameter[] paramsReal = null;
        boolean first = true;
        String strOrderBy = null;

        // Si no hay un alias definido, se colocar por defecto e.
        alias = StringUtils.isBlank(alias) ? "e" : alias;

        // Armando el SELECT
        sbQuery = new StringBuffer("select ");

        // Verifica si esta usando alguna función
        if (typeFunctions != null) {
            switch (typeFunctions) {
                case COUNT:
                    sbQuery.append(" count(").append(alias).append(") ");
                    break;
            }
        } else {
            sbQuery.append(alias);
        }

        // Armando el FROM
        sbQuery.append(" from ").append(entityName).append(" ").append(alias);

        // Armando el WHERE
        if (params != null && params.length > 0) {
            // Descarta los parametros donde el valor sea vacio o nulo
            paramsReal = UtilJPA.getFilterParameterNotBlank(params);
//            logDebug(UtilJPA.class, "paramsReal.length = " + paramsReal.length);

            // Se crea el vector de la cantidad real de parametros
//            paramsSQL = new FilterParameter[paramsReal.length];

            for (FilterParameter fP : paramsReal) {
//                logDebug(UtilJPA.class, "fP.getField() = " + fP.getField());
//                logDebug(UtilJPA.class, "fP.getValue() = " + fP.getValue());
                // Pasandole el alias al FilterParameter para su uso
                fP.setAlias(alias);
//                logDebug(UtilJPA.class, "first = " + first);
//                logDebug(UtilJPA.class, "fP.getAlias() = " + fP.getAlias());
                if (first) {
                    first = false;

                    // Adicionando la palabra reservada WHERE
                    sbQuery.append(" where ");

                    // Adicionando la condición que arma el FilterParameter
                    sbQuery.append(fP.getCondition());
                } else {
                    // Adicionando el operador logico
                    sbQuery.append(fP.getLogicalOperations());

                    // Adicionando la condición que arma el FilterParameter
                    sbQuery.append(fP.getCondition());
                }
//                logDebug(UtilJPA.class, "sbQuery = " + sbQuery.toString());

                // Si hay order by
                if (StringUtils.isBlank(strOrderBy)) {
                    strOrderBy = fP.getOrderBy();
                } else {
                    strOrderBy += ", " + fP.getOrderBy();
                }
            }// Fin de for
        }//Fin del if

        // Armando el ORDER BY
        if (StringUtils.isNotBlank(strOrderBy)) {
            sbQuery.append(" order by ").append(strOrderBy);
        }

        return sbQuery != null ? sbQuery.toString() : null;
    }

    /**
     * Metodo que genera un NameQuery con base en el objeto SQL de forma dinamica.
     * <p>Soporta varios objetos en el From</p>
     * <p>Soporta Join y Left join entro los objetos del From</p>
     * <p>Soporta condiciones del Where dinamicos</p>
     * <p>Soporta condiciones del Where combinar or con and, ej: (campo_1 = x or campo_1 = y or campo_1 = z) and campo_2 = xx</p>
     * <p>Cada condición del Where debe tener el alias usado en el from por la tabla que lo contiene.</p>
     * @param sql
     * @return
     * @throws Exception
     */
    public static String generateSQLSelect(SQL sql) throws Exception {
        StringBuffer sbQuery = null;

        if (sql != null && sql instanceof SQL) {

            // Variables requeridas para el proceso
            FilterParameter[] paramsReal = null;
            boolean first = true;
            String strOrderBy = null;
            StringBuffer sbFrom = null;
            FromParameter[] vctFromParameter = new FromParameter[sql.getFrom().length];

            // Armando el SELECT
            sbQuery = new StringBuffer("select ");
            if (sql.getFrom() != null 
                    && sql.getFrom().length > 0) {
                sbFrom = new StringBuffer(" from ");
                for (FromParameter fromParameter : sql.getFrom()) {
                    // Almacenando el from de usar
                    sbFrom.append(fromParameter.getFrom());

                    // Almacenando el alias en el orden a mostrar
                    if (fromParameter.getPosColumSelect() != FromParameter.NO_VISIBLE_SELECT
                            && fromParameter.getPosColumSelect() > -1
                            && fromParameter.getPosColumSelect() <= vctFromParameter.length) {
                        vctFromParameter[fromParameter.getPosColumSelect()] = fromParameter;
                    }
                }

                // Armando el select
                first = true;
                for (FromParameter fromParameter : vctFromParameter) {
                    if (fromParameter != null) {
                        if (first) {
                            first = false;
                            sbQuery.append(" ").append(fromParameter.getSelect());
                        } else {
                            sbQuery.append(", ").append(fromParameter.getSelect());
                        }
                    }
                }
            }
            //logDebug(UtilJPA.class, " subQuery: "+sbQuery);

            // Armando el FROM
            if (sbFrom != null) {
                sbQuery.append(sbFrom.toString());
            } else {
                throw new Exception("Error al generar el From");
            }

            // Armando el WHERE
            if (sql.getWhere() != null 
                    && sql.getWhere().length > 0) {
                first = true;
                // Descarta los parametros donde el valor sea vacio o nulo
                paramsReal = UtilJPA.getFilterParameterNotBlank(sql.getWhere());

                for (FilterParameter fP : paramsReal) {
                    if (first) {
                        first = false;

                        // Adicionando la palabra reservada WHERE
                        sbQuery.append(" where ");

                        // Adicionando la condición que arma el FilterParameter
                        sbQuery.append(fP.getCondition());
                    } else {
                        // Adicionando el operador logico
                        sbQuery.append(fP.getLogicalOperations());

                        // Adicionando la condición que arma el FilterParameter
                        sbQuery.append(fP.getCondition());
                    }
//                    logDebug(UtilJPA.class,"sbQuery = " + sbQuery.toString());

                    // Si hay order by
//                    if (StringUtils.isBlank(strOrderBy)) {
//                        strOrderBy = fP.getOrderBy();
//                    } else {
//                        strOrderBy += ", " + fP.getOrderBy();
//                    }
                }// Fin de for
            }//Fin del if

            // Armando el GROUP BY
            first = true;
            if (vctFromParameter != null) {
                for (FromParameter fromParameter : vctFromParameter) {
                    if (fromParameter != null 
                            && fromParameter.isGroupBy()) {
                        if (first) {
                            first = false;
                            sbQuery.append(" group by ");
                        } else {
                            sbQuery.append(", ");
                        }

                        sbQuery.append(StringUtils.isNotBlank(fromParameter.getAlias()) ? fromParameter.getAlias() : fromParameter.getNameObject()).append(" ");
                        //logDebug(UtilJPA.class, "SbQuery" + sbQuery);
                    }
                }
            }

            // Armando el GROUP BY 2 (Cadena quemada, pero en objetos)
            //logDebug(UtilJPA.class, "sql.getGroupBy() -> " + sql.getGroupBy());
            if(sql.getGroupBy() != null && !sql.getGroupBy().trim().equals("")){
                sbQuery.append(sql.getGroupBy());
            }/*else{
                logDebug(UtilJPA.class, "Sentencia group by estática vacia ");
            }*/
            
            // Armando el ORDER BY
            strOrderBy = "";
            if (sql.getOrderBy() != null) {
                for (OrderBy orderBy : sql.getOrderBy()) {
                    if (StringUtils.isNotBlank(strOrderBy)) {
                        strOrderBy += ", ";
                    }
                    strOrderBy += orderBy.getOrderBy();
                }
            }

            if (StringUtils.isNotBlank(strOrderBy)) {
                sbQuery.append(" order by ").append(strOrderBy);
            }

        } else {
            throw new IllegalArgumentException("El parametro sql no es valido");
        }

        return sbQuery != null ? sbQuery.toString() : null;
    }
}
