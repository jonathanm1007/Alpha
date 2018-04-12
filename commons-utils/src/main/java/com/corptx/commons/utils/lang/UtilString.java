/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.lang;

import com.corptx.commons.utils.collections.UtilList;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 *
 ** @author William Diaz Pabón
 */
public class UtilString {

    public static int LENGTH_YEAR_YYYY = 4;
    public static int LENGTH_YEAR_YY = 2;

    public static void main(String[] args) {

        String palabra = "ñÑ";
        String palabra2 = "aAsDfGrtTY4rtT";
        System.out.println(palabra + " " + isAlfaNumeric(palabra) + " ");

        String palabra3 = "aá¿ñÑ.,;_*+¬°|-éÄAsD¿'/&%#3456!#$%/&=¡kghjk^}`?¡:└┴┬├─┼ãÃ╚╔╩╦╠═╬¤ðÐÊËÈıÍÎ";
        System.out.println("normal:    " + palabra3 + " \nASCII:     " + convertToAscii(palabra3));

        System.out.println("solo alfanumericos: " + returnOnlyAlphaNumericCharacters(palabra3));

        System.out.println("sin acentos y sin los caracteres '?¿!¡: " + removeAccentsAndRemoveCharacters(palabra3, "'?¿!¡:"));

        System.out.println("sin los caracteres '?¿!¡ñÑ: " + removeCharacters(palabra3, "'?¿!¡:ñÑ"));



    }

    /**
     * Metodo que valida si un string es alfanumérico (created by Alexander
     * Quintero Duarte)
     *
     * @param cadena
     * @return boolean que indica el resultado de la validación
     */
    public static boolean isAlfaNumeric(String cadena) {
        boolean is = false;
        String patron = "[a-zA-Z0-9]*";
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(cadena);
        if (matcher.matches()) {
            is = true;
        }
        return is;
    }

    /**
     * Metodo que valida si un string no es alfanumérico (created by Alexander
     * Quintero Duarte)
     *
     * @param cadena
     * @return boolean que indica el resultado de la validación
     */
    public static boolean isNotAlfaNumeric(String cadena) {
        boolean is = true;
        if (isAlfaNumeric(cadena)) {
            is = false;
        }
        return is;
    }

    /**
     * Función que reemplaza acentos, tíldes y caracteres como la ñ y ç (created
     * by Alexander Quintero Duarte)
     *
     * @param cadena
     * @return cadena de texto con solo caracteres alfanuméricos.
     */
    public static String removeAccents(String cadena) {
        String original = "áàäéèëíìïóòöúùuÁÀÄÉÈËÍÌÏÓÒÖÚÙÜñÑçÇ";
        String reemplazo = "aaaeeeiiiooouuuAAAEEEIIIOOOUUUnNcC";
        String output = cadena;
        for (int i = 0; i < original.length(); i++) {
            output = output.replace(original.charAt(i), reemplazo.charAt(i));
        }
        return output;
    }

    /**
     * Función que reemplaza acentos, tíldes y caracteres como la ñ y ç,
     * eliminando demás caracteres de una cadena de texto. (created by Alexander
     * Quintero Duarte)
     *
     * @param cadena
     * @return cadena de texto con solo caracteres alfanuméricos.
     */
    public static String returnOnlyAlphaNumericCharacters(String cadena) {
        String output = removeAccents(cadena);
        if (isNotAlfaNumeric(output)) {
            String patron = "[^a-zA-Z0-9]*";
            Pattern p = Pattern.compile(patron);
            Matcher matcher = p.matcher(output);
            if (matcher.find()) {
                output = matcher.replaceAll("");
            }
        }
        return output;
    }

    /**
     * Función que reemplaza acentos, tíldes y caracteres como la ñ y ç, y
     * elimina caracteres especificados de una cadena de texto. (created by
     * Alexander Quintero Duarte)
     *
     * @param cadena
     * @return cadena de texto sin acentos y con caracteres específicos
     * eliminados.
     */
    public static String removeAccentsAndRemoveCharacters(String cadena, String characters) {
        String output = removeAccents(cadena);
        if (isNotAlfaNumeric(output)) {
            String patron = "[" + characters + "]*";
            Pattern p = Pattern.compile(patron);
            Matcher matcher = p.matcher(output);
            if (matcher.find()) {
                output = matcher.replaceAll("");
            }
        }

        return output;
    }

    /**
     * Función que elimina caracteres especificados de una cadena de texto.
     * (created by Alexander Quintero Duarte)
     *
     * @param cadena
     * @return cadena de texto sin los caracteres específicos eliminados.
     */
    public static String removeCharacters(String cadena, String characters) {
        String output = cadena;
        if (isNotAlfaNumeric(output)) {
            String patron = "[" + characters + "]*";
            Pattern p = Pattern.compile(patron);
            Matcher matcher = p.matcher(output);
            if (matcher.find()) {
                output = matcher.replaceAll("");
            }
        }

        return output;
    }

    /**
     * Función que convierte la cadena a caracteres del codigo ASCII. (add by
     * Alexander Quintero Duarte)
     *
     * @param input
     * @return cadena de texto limpia de acentos y caracteres especiales.
     */
    public static String convertToAscii(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\P{ASCII}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    /**
     * Metodo que convierte la cadena str a un estilo de nombre de clase de
     * java.
     * <pre>
     * UtilString.converterStrToNameClass("nombre_tabla","_") = "NombreTabla"
     * </pre>
     * <pre>
     * UtilString.converterStrToNameClass("fechaCambio","_") = "NombreTabla"
     * </pre>
     *
     * @param str
     * @param delim
     * @return
     */
    public static String converterStrToNameClass(String str, String delim) {
        String strName = str;

        if (StringUtils.isNotBlank(str)) {
            if (StringUtils.isNotBlank(delim) && StringUtils.contains(str, delim)) {
                // Pasa todas las letras a minusculas
                strName = StringUtils.lowerCase(strName);
                // Coloca la primera letra de cada palabra separada por _ en mayuscula
                char[] delimiter = {delim.charAt(0)};
                strName = WordUtils.capitalizeFully(strName, delimiter);
                // Quita los caracteres del strSeparator
                strName = StringUtils.replace(strName, delim, "");
            } else {
                strName = UtilString.capitalizeInitial(str);
            }
        }
        return strName;
    }

    /**
     * Metodo que convierte una lista a un vector de string
     *
     * @param lista
     * @return
     */
    public static String[] listToArrayVector(List lista) {
        System.out.println("Entro a  listToArrayVector");
        if (UtilList.isNotEmpty(lista)) {
            String string[] = new String[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                string[i] = (String) lista.get(i);
            }
            System.out.println("Salio a  listToArrayVector =" + string);
            return string;

        } else {
            System.out.println("Salio a  listToArrayVector = null");
            return null;
        }
    }

    /**
     * Verifica si la cadena de texto indicada en el parametro es un numero.
     * <p>Soporta números decimales</p> <p>Ejemplos:</p>
     *
     * <pre>
     * UtilString.isNumeric(null)       = false
     * UtilString.isNumeric("")         = false
     * UtilString.isNumeric(" ")        = false
     * UtilString.isNumeric("ab2c")     = false
     * UtilString.isNumeric("12-3")     = false
     * UtilString.isNumeric("123,4")    = false
     * UtilString.isNumeric("12.")      = false
     * UtilString.isNumeric("123")      = true
     * UtilString.isNumeric("123.4")    = true
     * </pre>
     *
     * @param strNum La cadena a verificar si es número
     * @return <code>true</code> si solo contiene digitos o separador de
     * decimales y si es no nulo
     */
    public static boolean isNumeric(String strNum) {
        boolean exito = true;

        if (StringUtils.isNotBlank(strNum)) {
            for (int i = 0; i < strNum.length(); i++) {
                if (strNum.charAt(i) != '.'
                        && Character.isDigit(strNum.charAt(i)) == false) {
                    exito = false;
                }
            }
            if (exito && (strNum.charAt(strNum.length() - 1) == '.' || strNum.charAt(0) == '.')) {
                exito = false;
            }
        } else {
            exito = false;
        }
        return exito;
    }

    /**
     * Convierte un String[] a String separando las columnas por el caracter de
     * separación
     *
     * @param array
     * @param strSeparador
     * @return
     */
    public static String ArrayStringToString(String[] array, String strSeparador) {
        System.out.println("Entro a ArrayStringToString");
        System.out.println("array = " + array);
        System.out.println("strSeparador = " + strSeparador);
        String string = "";
        String separador =
                StringUtils.isNotBlank(strSeparador) ? strSeparador : "";
        if (array != null) {
            for (String string1 : array) {
                if (StringUtils.isBlank(string1)) {
                    string1 = "";
                }
                string += string1 + separador;
            }
        }
        System.out.println("Saliendo de ArrayStringToString");
        return string;
    }

    public static String capitalizeInitial(String str) {
        String string = null;

        if (StringUtils.isNotBlank(str)) {
            string = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
//            System.out.println("string = -" + string+"-");
        }

        return string;
    }

    public static String capitalize(String str) {
        String string = "";
        if (StringUtils.isNotBlank(str)) {
            string = str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
        }

        return string;
    }

    /**
     * Valida un string como año de 4 cifras significa que está entre un rango
     * de [1000-9999]
     *
     * @param vigencia vigencia a validar
     * @param lengthYear numero de digitos de año a validar 2,4
     * @return true si, la vigencia es numerico y del numero de digitos pasados
     * como parametro lengthYear, false si no es numero o no es del numero de
     * digitos pasados como parametro lengthYear.
     */
    public static boolean validateYear(String vigencia, int lengthYear) {
        Integer vigenciaNumeric = null;
        boolean validate = false;
        try {
            if (StringUtils.isNotBlank(vigencia)) {
                vigencia = StringUtils.trim(vigencia);
                vigenciaNumeric = new Integer(vigencia);
                if (Integer.toString(vigenciaNumeric).length() == lengthYear) {
                    validate = true;
                }
            }
        } catch (NumberFormatException ex) {
            validate = false;
        } finally {
            return validate;
        }
    }

    /**
     * Metodo que orderna un String[] de string y devuelve el String[] ordenado
     *
     * @param str
     * @return
     */
    public static String[] orderString(String[] str) {
        String[] strvct = null;
        if (str != null) {
            List<String> lista = new ArrayList<String>();
            CollectionUtils.addAll(lista, str);
            Collections.sort(lista);
            strvct = lista.toArray(new String[0]);
        }
        return strvct;
    }

    /**
     * Metodo que ordena un String que contenga datos separados por algun
     * caracter y devuelve el string ordenado
     *
     * @param strDisordered string con los datos desordenados y separados por un
     * caracter.
     * @param delim char que contiene el caracter delimitador
     * @return String concatenado con el mismo delimitador pero los datos quedan
     * ordenados en forma ascendente
     */
    public static String orderString(String strDisordered, char delim) {
        String orderString = null;

        if (StringUtils.isNotBlank(strDisordered)) {
            String[] tokens = (new UtilStrTokenizer(strDisordered, delim)).getTokens();
            if (tokens != null) {
                String[] strOrd = orderString(tokens);

                if (strOrd != null) {
                    for (String string : strOrd) {
                        if (StringUtils.isBlank(orderString)) {
                            orderString = string;
                        } else {
                            orderString += delim + string;
                        }
                    }
                }
            }
        }
        return orderString;
    }

    /**
     *
     * Tiene como parametros un String y los separa por el parametro caracter
     * que se le envie retornando una lista de string
     *
     * @param nameFile
     * @param strSeparator
     * @return
     * @throws Exception
     */
    public static List<String> ConverterStringToList(String nameFile, char strSeparator) throws Exception {
        List<String> lista = new ArrayList<String>();
        if (!StringUtils.isBlank(nameFile)) {
            try {
                String[] tokens = (new UtilStrTokenizer(nameFile, strSeparator)).getTokens();
//                for (String tok : tokens) {
//                    lista.add(tok);
//                }
                lista.addAll(Arrays.asList(tokens));
            } catch (Exception e) {
                String msg = "Error convirtiendo la cadena a lista";
                throw new Exception(msg, e);
            }
        }
        return lista;
    }

    /**
     * Método que obtiene los ultimos caracteres de un String (by Alexander
     * Quintero Duarte)
     *
     * @param cadena string con la cadena de texto a la que se le extraerá los
     * últimos caracteres.
     * @param cantidadCaracteres String que contiene la cantidad de caracteres a
     * obtener
     * @return String con los ultimos caracteres de la cadena
     */
    public static String obtainLastCharacters(String cadena, int cantidadCaracteres) {
        String ultimosCaracteres = null;

        if (StringUtils.isNotBlank(cadena)) {
            if (cantidadCaracteres > cadena.length()) {
                ultimosCaracteres = cadena;
            } else {
                ultimosCaracteres = cadena.substring(cadena.length() - cantidadCaracteres);
            }
        }
        return ultimosCaracteres;
    }

    /*
     *Método que elimina los ceros a la izquierda de un string
     * (by Jonathan Méndez)
     * @param str string con la cadena de texto a la que se le extraerán los
     * ceros a la izquierda.
     * @return String sin ceros a la izquierda
     */
    public static String truncateLeftZerosFromString(String str) {
        String strName = "";
        char zero = '0';
        char caracter;
        int posicionPrimerDigito = 0;
        if (StringUtils.isNotBlank(str)) {
            for (int i = 0; i < str.length(); i++) {

                caracter = str.charAt(i);
                if (zero != caracter) {
                    posicionPrimerDigito = i;
                    break;
                }
            }

            for (int i = posicionPrimerDigito; i < str.length(); i++) {

                caracter = str.charAt(i);
                strName += caracter;
            }
        }
        return strName;
    }
}
