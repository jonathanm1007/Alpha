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
public class FromParameter implements Serializable {

    public static final int NO_VISIBLE_SELECT = -1;
    private int initialPosition = -1;
    private int numberCharacters = -1;
    private String nameObject;
    private String alias;
    private int posColumSelect;
    private boolean groupBy = false;
    private boolean orderBy = false;
    private TypeRelationships typeRelationships = null;
    private TypeFunctions typeFunctions = null;

    /**
     * @return the groupBy
     */
    public boolean isGroupBy() {
        return groupBy;
    }

    /**
     * @return the orderBy
     */
    public boolean isOrderBy() {
        return orderBy;
    }

    public static enum TypeRelationships {

        JOIN, LEFT_JOIN
    };

    public static enum TypeFunctions {

        UPPER, SUBSTRING, COUNT, COUNT_DISTINCT, SUM, DISTINCT, SUM_DISTINCT
    };

    public FromParameter(String nameObject, String alias, int posColumSelect) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
    }

    public FromParameter(String nameObject, String alias, int posColumSelect, boolean groupBy) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.groupBy = groupBy;
    }

    /**
     * Metodo que ademas de agrupar permite poner en una funcion el campo,
     * Ej: select count(oiIae) from OiIae oiIae join oiIae.deudaIaeCollection deudaIae where deudaIae.estado = 'PENDIENTE' Group by oiIae
     * @param nameObject
     * @param alias
     * @param posColumSelect
     * @param groupBy
     * @param typeFunctions
     */
    public FromParameter(String nameObject, String alias, int posColumSelect, boolean groupBy, TypeFunctions typeFunctions) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.groupBy = groupBy;
        this.typeFunctions = typeFunctions;
    }

    public FromParameter(String nameObject, String alias, int posColumSelect, TypeFunctions typeFunctions) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.typeFunctions = typeFunctions;
    }

    public FromParameter(String nameObject, String alias, int posColumSelect, TypeFunctions typeFunctions, int initialPosition, int numberCharacters) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.typeFunctions = typeFunctions;
        this.initialPosition = initialPosition;
        this.numberCharacters = numberCharacters;
    }

    public FromParameter(String nameObject, String alias, int posColumSelect, TypeFunctions typeFunctions, TypeRelationships typeRelationships) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.typeFunctions = typeFunctions;
        this.typeRelationships = typeRelationships;
    }

    public FromParameter(String nameObject, String alias, int posColumSelect, TypeRelationships typeRelationships) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.typeRelationships = typeRelationships;
    }

    public FromParameter(String nameObject, String alias, int posColumSelect, TypeRelationships typeRelationships, boolean groupBy) {
        this.nameObject = nameObject;
        this.alias = alias;
        this.posColumSelect = posColumSelect;
        this.typeRelationships = typeRelationships;
        this.groupBy = groupBy;
    }

    /**
     * @return the nameObject
     */
    public String getNameObject() {
        return nameObject;
    }

    /**
     * @param nameObject the nameObject to set
     */
    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the posColumSelect
     */
    public int getPosColumSelect() {
        return posColumSelect;
    }

    /**
     * @param posColumSelect the posColumSelect to set
     */
    public void setPosColumSelect(int posColumSelect) {
        this.posColumSelect = posColumSelect;
    }

    public String getTypeRelationships() {
        String strTypeRelationships = null;
        if (this.typeRelationships != null) {
            switch (this.typeRelationships) {
                case JOIN:
                    strTypeRelationships = "Join ";
                    break;
                case LEFT_JOIN:
                    strTypeRelationships = "Left Join ";
                    break;
            }
        }
        return strTypeRelationships;
    }

    /**
     * Metodo que analiza si se usa una funcion y arma la cadena con la función
     * de lo contrario retorna la entidad con su alias
     * @return
     */
    private String getFunction() {
        String function = null;
        try {
            if (this.typeFunctions != null) {
                switch (this.typeFunctions) {
                    case UPPER:
                        function = " upper("
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject)
                                + ") ";
                        break;
                    case SUBSTRING:
                        if (this.initialPosition > 0 && this.numberCharacters > 0) {
                            function = " substring("
                                    + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject)
                                    + ","
                                    + this.initialPosition + ","
                                    + this.numberCharacters + ") ";
                        }
                        break;
                    case COUNT:
                        function = " count("
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject)
                                + ") ";
                        break;
                    case COUNT_DISTINCT:
                        function = " count( distinct "
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject)
                                + ") ";
                        break;
                    case DISTINCT:
                        function = " distinct "
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject);
                        break;
                    case SUM:
                        function = " sum("
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject)
                                + ") ";
                        break;
                    case SUM_DISTINCT:
                        function = " sum( distinct "
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() : this.nameObject)
                                + ") ";
                        break;
                }
            } else {
                function = StringUtils.isNotBlank(this.getAlias())
                        ? this.alias + " "
                        : this.nameObject + " ";
            }

        } catch (Exception e) {
            function = null;
        }
        return function;
    }

    public String getFrom() {
        String from = null;

        if (StringUtils.isNotBlank(this.nameObject)) {
            // Coloca el tipo de relación Join o Left join
            from = this.getTypeRelationships() != null ? this.getTypeRelationships() : "";
            // colocando el nombre del objeto
            from += StringUtils.isNotBlank(this.getAlias())
                    ? this.nameObject + " " + this.alias + " "
                    : " ";
            //se modifica porque no se encuentra uso y se asigna el anterior para poder hacer el sum
//            from += StringUtils.isNotBlank(this.getAlias())
//                    ? this.nameObject + " " + this.alias + " "
//                    this.nameObject + : " ";

        }
//        logDebug(this, "from = " + from);
        return from;
    }

    public String getSelect() {
        String select = null;

        if (StringUtils.isNotBlank(this.nameObject)) {
            // colocando el nombre del objeto
            select = this.getFunction();
        }
//        logDebug(this, "from = " + from);
        return select;
    }
}
