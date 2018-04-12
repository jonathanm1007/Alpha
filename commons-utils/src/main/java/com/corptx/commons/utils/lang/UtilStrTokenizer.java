/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.lang;

import com.corptx.commons.utils.file.UtilFile;
import com.corptx.commons.utils.time.UtilDate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 ** @author William Diaz Pabón
 */
public class UtilStrTokenizer {

    public static final Log log = LogFactory.getLog(UtilFile.class);
    /** The text to work on. */
    private char chars[];
    /** The parsed tokens */
    private String tokens[];
    /** The current iteration position */
    private int tokenPos;
    private char delimitador;

    public UtilStrTokenizer(String str, char delim) {
        this.chars = str.toCharArray();
        this.delimitador = delim;
    }

    public boolean hasNext() {
        checkTokenized();
        return tokenPos < tokens.length;
    }

    /**
     * Checks if tokenization has been done, and if not then do it.
     */
    private void checkTokenized() {
        if (tokens == null) {
            if (chars == null) {
                // still call tokenize as subclass may do some work
                List<String> split = tokenize(null, 0, 0);
                tokens = split.toArray(new String[split.size()]);
            } else {
                List<String> split = tokenize(chars, 0, chars.length);
                tokens = split.toArray(new String[split.size()]);
            }
        }
    }

    protected List<String> tokenize(char[] chars, int offset, int count) {
        if (chars == null || count == 0) {
            return Collections.emptyList();
        }
        String buf = new String();
        List<String> tokens = new ArrayList<String>();
        int pos = offset;
        char ultimoChar = '\n';

        // loop around the entire buffer
        while (pos >= 0 && pos < count) {
//            System.out.println("chars[" + pos + "] = " + chars[pos]);
            if (chars[pos] != delimitador) {
                buf += chars[pos];
//                System.out.println("Adicionando caracter al buf = " + buf);
            } else {// Llego al delimitador
//                System.out.println("Encontro delimitador");
                if (StringUtils.isBlank(buf)) {
//                    System.out.println("El buf esta vacio, entonces adicionare un null");
                    buf = null;
                }
                tokens.add(buf);
                buf = "";
//                System.out.println("buf adicionado a la lista de tokens = " + tokens);
            }
            ultimoChar = chars[pos];
//            System.out.println("buf adicionado a la lista de tokens = " + tokens);
            pos++;
        }
        if ((pos == count)) {
//                System.out.println("Llego al final de la cadena con buf = "+buf);
            if (StringUtils.isBlank(buf) || (ultimoChar == delimitador)) {
//                System.out.println("No hay caracteres, entonces adicionare un null");
                buf = null;
            }
            tokens.add(buf);
//            System.out.println("buf adicionado a la lista de tokens = " + tokens);
        }
        return tokens;
    }

    public String nextToken() {
        if (hasNext()) {
            return tokens[tokenPos++];
        }
        return null;
    }

    public String[] getTokens() {
        checkTokenized();
        return tokens;
    }

    public static void main(String[] args) {

        /*String line = "06;79783544;1;NATURAL;;HASSAN;ALBERTO;SHALA;;8632032;;;;HASSAN@HOTMAIL.COM";
        char strSeparator = ';';
        System.out.println("-------------------------------------------");
        String[] tokens = (new UtilStrTokenizer(line, strSeparator)).getTokens();
        for (String string : tokens) {
            System.out.println("string = " + string);
        }
        System.out.println("-------------------------------------------");
         */
        Calendar c= Calendar.getInstance();
        c.set(2000, 2, 28);
        Calendar c1= Calendar.getInstance();
        c1.set(2009, 5, 5);
        try {
            //System.out.println("UtilDate.DiferenciaFechasEnDias(c, c1, 30) = " + UtilDate.DiferenciaFechasEnDias(c, Calendar.getInstance(), 30));
         

            String str_date="30/04/2009";
            DateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (Date)formatter.parse(str_date);
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            System.out.println("cal.get(Calendar.DATE) = " + cal.get(Calendar.DATE));
            System.out.println("cal.get(Calendar.MONTH) = " + cal.get(Calendar.MONTH));
            System.out.println("cal.get(Calendar.YEAR) = " + cal.get(Calendar.YEAR));
            System.out.println("date = " + date);

            Calendar ca=Calendar.getInstance();
            System.out.println("ca.get((Calendar.DATE)) = " + ca.get((Calendar.DATE)));
            System.out.println("ca.get((Calendar.DATE)) = " + ca.get((Calendar.MONTH)));
            System.out.println("ca.get((Calendar.DATE)) = " + ca.get((Calendar.YEAR)));
            System.out.println("UtilDate.DiferenciaFechasEnDias(c, c1, 30) = " + UtilDate.DiferenciaFechasEnDias(cal, Calendar.getInstance(), 30));

        } catch (Exception ex) {
            Logger.getLogger(UtilStrTokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
