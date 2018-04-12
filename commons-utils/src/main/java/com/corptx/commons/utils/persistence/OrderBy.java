/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.persistence;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;


/**
 * Clase que permite indicar los objetos usados dentro de una consulta
 * soportando realciones entre objetos como Join, left join, etc.<br>
 *
 * @author William Diaz Pabón
 */
public class OrderBy implements Serializable {

    private String field = null;
    private TypeOrderBy typeOrderBy = null;

    public static enum TypeOrderBy {

        ASC, DESC
    };

    public OrderBy(String field, TypeOrderBy typeOrderBy) {
        this.field = field;
        this.typeOrderBy = typeOrderBy;
    }

    public String getOrderBy() {
//        logDebug(this, "Entro a getOrderBy ");
        String strOrderBy = null;

//        logDebug(FilterParameter.class, "this.field = " + this.field);
//        logDebug(FilterParameter.class, "this.typeOrderBy = " + this.typeOrderBy);

        if (this.typeOrderBy != null
                && StringUtils.isNotBlank(this.field)) {


            switch (this.typeOrderBy) {
                case ASC:
                    strOrderBy = this.field + " asc ";
                    break;
                case DESC:
                    strOrderBy = this.field + " desc ";
                    break;
            }
        }
//        logDebug(this, "Saliendo de getOrderBy ");
        return strOrderBy;
    }
}
