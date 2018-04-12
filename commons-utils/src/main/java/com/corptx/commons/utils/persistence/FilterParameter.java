package com.corptx.commons.utils.persistence;

import com.corptx.commons.utils.beans.UtilsBean;
import static com.corptx.commons.utils.log.UtilLog.logError;
import java.io.Serializable;
import org.apache.commons.lang.StringUtils;

/**
 * Clase que permite indicar los campos y valores usada dentro del WHERE para
 * realizar busquedas dinamicas.<br> La propiedad like tiene valor por defecto
 * de false<br> La propiedad upper tiene valor por defecto de true<br>
 *
 ** @author William Diaz PabÃ³n
 */
public class FilterParameter implements Serializable {

    /**
     * Propiedad dejada pro compatibilidad
     *
     * @deprecated Usar mejor el TypeComparisonOperators
     */
    private boolean like = false;
    /**
     * Propiedad dejada pro compatibilidad
     *
     * @deprecated Usar mejor el TypeFunctions
     */
    private boolean upper = true;
    private String field = null;
    private Object value = null;
    private Object valueFin = null;
    private boolean orderBy = false;
    private int initialPosition = -1;
    private int numberCharacters = -1;
    private String alias = null;
    private TypeOrderBy typeOrderBy = null;
    private TypeComparisonOperators typeComparisonOperators = null;
    private TypeFunctions typeFunctions = null;
    private TypeLogicalOperators typeLogicalOperators = null;
    private boolean validarParam = true;
    /**
     * Esta propiedad permite construir una condicion OR combinado con AND.
     * <p>ej: (campo_1 = x or campo_1 = y or campo_1 = z) and campo_2 = xx</p>
     * <p>Los diferentes valores de campo_1 son los que se agregan en esta
     * lista</p>
     */
    private FilterParameter[] filterParameterGroup;
    /**
     * Esta propiedad indica si el valor por el cuál comparar en el WHERE,
     * consiste en una atributo de alguna entidad de la consulta.
     */
    private boolean esPropiedad = false;

    public static enum TypeOrderBy {

        ASC, DESC
    };

    /**
     * Estos son los operadores soportados EQUAL => " = " <br> GREATER => " > "
     * <br> GREATER_EQUAL => " >= " <br> LESS => " < " <br> LESS_EQUAL => " <= "
     * <br> NOT_EQUAL => " <> " <br> BETWEEN => " between value and valueFin "
     * <br> LIKE => " like value " <br> LIKE_WILDCARD_PERCENT_LEFT => " like
     * %value " <br> LIKE_WILDCARD_PERCENT_RIGTH => " like value% " <br>
     * LIKE_WILDCARD_PERCENT_BOTH => " like %value% " <br> IS_NOT_NULL => " is
     * not null " <br> IS_NULL => " is null " <br> IS_EMPTY => " is empty " <br>
     * IS_NOT_EMPTY => " is not empty " <br>
     */
    public static enum TypeComparisonOperators {

        /**
         * EQUAL => " = " <br>
         */
        EQUAL,
        /**
         * GREATER => " > " <br>
         */
        GREATER,
        /**
         * GREATER_EQUAL => " >= " <br>
         */
        GREATER_EQUAL,
        /**
         * LESS => " < " <br>
         */
        LESS,
        /**
         * LESS_EQUAL => " <= " <br>
         */
        LESS_EQUAL,
        /**
         * NOT_EQUAL => " <> " <br>
         */
        NOT_EQUAL,
        /**
         * BETWEEN => " between value and valueFin " <br>
         */
        BETWEEN,
        /**
         * LIKE => " like value " <br>
         */
        LIKE,
        /**
         * LIKE_WILDCARD_PERCENT_LEFT => " like %value " <br>
         */
        LIKE_WILDCARD_PERCENT_LEFT,
        /**
         * LIKE_WILDCARD_PERCENT_RIGTH => " like value% " <br>
         */
        LIKE_WILDCARD_PERCENT_RIGTH,
        /**
         * LIKE_WILDCARD_PERCENT_BOTH => " like %value% " <br>
         */
        LIKE_WILDCARD_PERCENT_BOTH,
        /**
         * IS_NULL => " is null " <br>
         */
        IS_NULL,
        /**
         * IS_NOT_NULL => " is not null " <br>
         */
        IS_NOT_NULL,
        /**
         * NOT_IN => " not in (?)"<br>
         */
        NOT_IN,
        /**
         * IN => " in (?)"<br>
         */
        IN,
        /**
         * IS_EMPTY => " is empty "<br>
         */
        IS_EMPTY,
        /**
         * IS_NOT_EMPTY => " is not empty "<br>
         */
        IS_NOT_EMPTY
    };

    public static enum TypeLogicalOperators {

        AND, OR
    };

    public static enum TypeFunctions {

        UPPER, SUBSTRING
    };

    /**
     * Constructor que inicializa las propiedades field y value.<br> La
     * propiedad like queda en false<br> La propiedad upper queda en true<br>
     *
     * @param field
     * @param value
     */
    public FilterParameter(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    /**
     * Constructor que inicializa las propiedades de la clase<br>
     *
     * @param field, nombre de la propiedad de la entidad a consultar
     * @param value, valor de la propiedad de la entidad a consultar
     * @param like, true para usar like, false para usa =
     */
    public FilterParameter(String field, Object value, boolean like) {
        this.field = field;
        this.value = value;
        this.like = like;
    }

    /**
     * Constructor para inicializar todas las propiedades de la clase
     *
     * @param field
     * @param value
     * @param like
     * @param upper
     */
    public FilterParameter(String field, Object value, boolean like, boolean upper) {
        this.field = field;
        this.value = value;
        this.like = like;
        this.upper = upper;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, Object value, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.value = value;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, Object value, TypeLogicalOperators typeLogicalOperators, TypeOrderBy typeOrderBy) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.value = value;
        this.typeLogicalOperators = typeLogicalOperators;
        this.typeOrderBy = typeOrderBy;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, Object value, TypeOrderBy typeOrderBy) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.value = value;
        this.typeOrderBy = typeOrderBy;

    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, Object value) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.value = value;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, Object value, TypeOrderBy typeOrderBy, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.value = value;
        this.typeOrderBy = typeOrderBy;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions, Object value, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, Object value, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;

    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions, Object value, TypeOrderBy typeOrderBy, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;
        this.typeOrderBy = typeOrderBy;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions, Object value, Object valueFin, int initialPosition, int numberCharacters, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;
        this.valueFin = valueFin;
        this.initialPosition = initialPosition;
        this.numberCharacters = numberCharacters;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions, Object value, int initialPosition, int numberCharacters, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;
        this.initialPosition = initialPosition;
        this.numberCharacters = numberCharacters;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions, Object value, int initialPosition, int numberCharacters, TypeLogicalOperators typeLogicalOperators, TypeOrderBy typeOrderBy) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;
        this.initialPosition = initialPosition;
        this.numberCharacters = numberCharacters;
        this.typeLogicalOperators = typeLogicalOperators;
        this.typeOrderBy = typeOrderBy;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, TypeFunctions typeFunctions, Object value, Object valueFin, TypeOrderBy typeOrderBy, TypeLogicalOperators typeLogicalOperators) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.typeFunctions = typeFunctions;
        this.value = value;
        this.valueFin = valueFin;
        this.typeOrderBy = typeOrderBy;
        this.typeLogicalOperators = typeLogicalOperators;
    }

    public FilterParameter(String field, TypeComparisonOperators typeComparisonOperators, Object value, TypeLogicalOperators typeLogicalOperators, boolean esPropiedad) {
        this.field = field;
        this.typeComparisonOperators = typeComparisonOperators;
        this.value = value;
        this.typeLogicalOperators = typeLogicalOperators;
        this.esPropiedad = esPropiedad;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the like
     */
    public boolean isLike() {
        return like;
    }

    /**
     * @param like the like to set
     */
    public void setLike(boolean like) {
        this.like = like;
    }

    /**
     * @return the upper
     */
    public boolean isUpper() {
        return upper;
    }

    /**
     * @param upper the upper to set
     */
    public void setUpper(boolean upper) {
        this.upper = upper;
    }

    public String getCondition() {
        StringBuffer sbCondition = null;
        boolean group = false;
        try {
            if (StringUtils.isNotBlank(this.field)) {
                String function = this.getFunction();
                String operator = this.getOperator();

                if (StringUtils.isNotBlank(operator)
                        && StringUtils.isNotBlank(function)) {

                    if (sbCondition == null) {
                        sbCondition = new StringBuffer();
                    }

                    sbCondition.append(function).append(operator);
                }

                // Si hay mas condiciones que toca agrupar
                if (this.filterParameterGroup != null) {
                    group = true;

                    for (FilterParameter filterParameter : filterParameterGroup) {
                        function = null;
                        operator = null;

                        function = filterParameter.getFunction();
                        operator = filterParameter.getOperator();
                        if (StringUtils.isNotBlank(operator)
                                && StringUtils.isNotBlank(function)) {

                            if (sbCondition == null) {
                                sbCondition = new StringBuffer();
                            }
                            sbCondition.append(filterParameter.getLogicalOperations()).append(function).append(operator);

                        }
                    }
                }

            }
        } catch (Exception e) {
            logError(this, "Error en el metodo getCondition. " + e.getMessage(), e);
            sbCondition = null;
        }
        return group ? " ( " + sbCondition.toString() + " ) " : sbCondition.toString();
    }

    /**
     * Metodo que valida el tipo de operador y lo concatena con el valor. Si el
     * operador es between, lo concatena con el valor inicial y final
     *
     * @return
     */
    private String getOperator() {
        String operator = null;
        try {
            if (this.typeComparisonOperators != null) {
                String strValue = null;

                /*
                 * se remplazo este if por el de abajo para tener encuenta la
                 * funcion UPPER si es de tipo string el valor se le hace un
                 * uppercase
                 */
//                if (UtilsBean.isNotPropertyEmpty(this.value)) {
//                    strValue = this.value instanceof String
//                            ? "'" + this.value + "'"
//                            : String.valueOf(this.value);
//                }

                if (UtilsBean.isNotPropertyEmpty(this.value)) {

                    if (this.value instanceof String) {

                        if (this.typeFunctions != null
                                && this.typeFunctions.equals(TypeFunctions.UPPER)) {

                            this.value = StringUtils.upperCase((String) this.value);
                        }
//                        System.out.println("this.value " + this.value);
                        if (!this.esPropiedad) {
                            strValue = "'" + this.value + "'";
                        } else {
                            strValue = this.value.toString();
                        }
                    } else {
                        strValue = String.valueOf(this.value);
                    }
                }

                switch (this.typeComparisonOperators) {
                    case EQUAL:
                        operator = StringUtils.isNotBlank(strValue) ? " = " + strValue + " " : null;
//                        logDebug(this,"Entro por EQUAL :> operator = " + operator);
                        break;
                    case GREATER:
                        operator = StringUtils.isNotBlank(strValue) ? " > " + strValue + " " : null;
                        break;
                    case GREATER_EQUAL:
                        operator = StringUtils.isNotBlank(strValue) ? " >= " + strValue + " " : null;
                        break;
                    case LESS:
                        operator = StringUtils.isNotBlank(strValue) ? " < " + strValue + " " : null;
                        break;
                    case LESS_EQUAL:
                        operator = StringUtils.isNotBlank(strValue) ? " <= " + strValue + " " : null;
                        break;
                    case NOT_EQUAL:
                        operator = StringUtils.isNotBlank(strValue) ? " <> " + strValue + " " : null;
                        break;
                    case LIKE:
                        if (UtilsBean.isNotEmpty(this.value)
                                && this.value instanceof String) {
                            operator = " like " + strValue + " ";
                        }
                        break;
                    case LIKE_WILDCARD_PERCENT_LEFT:
                        if (UtilsBean.isNotEmpty(this.value)
                                && this.value instanceof String) {
                            operator = " like '%" + this.value + "' ";
                        }
                        break;
                    case LIKE_WILDCARD_PERCENT_RIGTH:
                        if (UtilsBean.isNotEmpty(this.value)
                                && this.value instanceof String) {
                            operator = " like '" + this.value + "%' ";
                        }
                        break;
                    case LIKE_WILDCARD_PERCENT_BOTH:
                        if (UtilsBean.isNotEmpty(this.value)
                                && this.value instanceof String) {
                            operator = " like '%" + this.value + "%' ";
                        }
                        break;
                    case BETWEEN:
                        if (UtilsBean.isNotEmpty(this.value)
                                && UtilsBean.isNotEmpty(this.valueFin)) {
                            if (this.value instanceof String && this.valueFin instanceof String) {
                                operator = " between '" + this.value + "' and '" + this.valueFin + "' ";
                            } else {
                                operator = " between " + this.value + " and " + this.valueFin + " ";
                            }
                        }
                        break;
                    case IS_NULL:
//                        logDebug(this,"Entro por IS_NULL");
                        operator = " is null ";
                        break;
                    case IS_NOT_NULL:
//                        logDebug(this,"Entro por NOT IS_NULL");
                        operator = " is not null ";
                        break;
                    case NOT_IN:
                        if (UtilsBean.isNotEmpty(this.value)
                                && this.value instanceof String) {
                            operator = " not in (" + this.value + ") ";
                        }
                        break;
                    case IN:
                        if (UtilsBean.isNotEmpty(this.value)
                                && this.value instanceof String) {
                            operator = " in (" + this.value + ") ";
                        }
                        break;
                    case IS_EMPTY:
                        operator = " is empty ";
                        break;
                    case IS_NOT_EMPTY:
                        operator = " is not empty ";
                        break;
                }
            }

        } catch (Exception e) {
            logError(this, "Error en el metodo getOperator. " + e.getMessage(), e);
            operator = null;
        }
        return operator;
    }

    /**
     * Metodo que analiza si se usa una funcion y arma la cadena con la función
     * de lo contrario retorna la propiedad con su alias
     *
     * @return
     */
    private String getFunction() {
        String function = null;
        try {
            if (this.typeFunctions != null
                    && StringUtils.isNotBlank(this.field)) {
                switch (this.typeFunctions) {
                    case UPPER:
                        function = " upper("
                                + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() + "." : "")
                                + this.field + ") ";
//                        logDebug(this,"this.typeFunctions :> UPPER :> function = " + function);
                        break;
                    case SUBSTRING:
                        if (this.initialPosition > 0 && this.numberCharacters > 0) {
                            function = " substring("
                                    + (StringUtils.isNotBlank(this.getAlias()) ? this.getAlias() + "." : "")
                                    + this.field + ","
                                    + this.initialPosition + ","
                                    + this.numberCharacters + ") ";
//                            logDebug(this,"this.typeFunctions :> SUBSTRING :> function = " + function);
                        }
                        break;
                }
            } else {
                function = StringUtils.isNotBlank(this.getAlias())
                        ? this.getAlias() + "." + this.field
                        : this.field;
            }

        } catch (Exception e) {
            logError(this, "Error en el metodo getFunction. " + e.getMessage(), e);
            function = null;
        }
        return function;
    }

    public String getLogicalOperations() {
        String logicalOperations = null;

        if (this.typeLogicalOperators != null) {
            switch (this.typeLogicalOperators) {
                case AND:
                    logicalOperations = " and ";
                    break;
                case OR:
                    logicalOperations = " or ";
                    break;
            }
        }

        return logicalOperations;
    }

    public String getOrderBy() {
//        logDebug(this, "Entro a getOrderBy ");
        String strOrderBy = null;

//        logDebug(FilterParameter.class, "this.field = " + this.field);
//        logDebug(FilterParameter.class, "this.alias = " + this.alias);
//        logDebug(FilterParameter.class, "this.typeOrderBy = " + this.typeOrderBy);

//        if (this.typeOrderBy != null
//                && StringUtils.isNotBlank(this.field)
//                && StringUtils.isNotBlank(this.getAlias())) {
        if (this.typeOrderBy != null
                && StringUtils.isNotBlank(this.field)) {

            String strCampo =
                    StringUtils.isNotBlank(this.alias)
                    ? this.alias + "." + this.field
                    : this.field;

//            logDebug(FilterParameter.class, "strCampo = " + strCampo);

            switch (this.typeOrderBy) {
                case ASC:
                    strOrderBy = strCampo + " asc ";
                    break;
                case DESC:
                    strOrderBy = strCampo + " desc ";
                    break;
            }
        }
//        logDebug(this, "Saliendo de getOrderBy ");
        return strOrderBy;
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
     * @return the validarParam
     */
    public boolean isValidarParam() {
        this.validarParam = true;
        if (this.typeComparisonOperators != null) {
            switch (this.typeComparisonOperators) {
                case IS_NULL:
                case IS_NOT_NULL:
                    this.validarParam = false;
            }
        }
        return validarParam;
    }

    /**
     * @return the filterParameterGroup
     */
    public FilterParameter[] getFilterParameterGroup() {
        return filterParameterGroup;
    }

    /**
     * @param filterParameterGroup the filterParameterGroup to set
     */
    public void setFilterParameterGroup(FilterParameter... filterParameterGroup) {
        this.filterParameterGroup = filterParameterGroup;
    }
}
