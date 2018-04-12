/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

/**
 *
 * @author William
 */
public class UtilLog {

    /* *****************************************
     * Metodos que reciben un Class y el mensaje
     * *****************************************/
    public static void logDebug(Class clazz, String msg) {
        Log log = LogFactory.getLog(clazz);
        if (log != null && log.isDebugEnabled()) {
            log.debug(msg);
        }
//        LogFactory.getLog(clazz).debug(msg);
    }

    public static void logError(Class clazz, String msg) {
        LogFactory.getLog(clazz).error(msg);
    }

    public static void logInfo(Class clazz, String msg) {
        LogFactory.getLog(clazz).info(msg);
    }
    /* **************************************** */


    /* *****************************************
     * Metodos que reciben un Class, el mensaje y la excepcion
     * *****************************************/
    public static void logDebug(Class clazz, String msg, Throwable t) {
        Log log = LogFactory.getLog(clazz);
        
        
        if (log != null && log.isDebugEnabled()) {
            log.debug(msg, t);
        }
//        LogFactory.getLog(clazz).debug(msg, t);
    }

    public static void logError(Class clazz, String msg, Throwable t) {
        LogFactory.getLog(clazz).error(msg, t);
    }

    public static void logInfo(Class clazz, String msg, Throwable t) {
        LogFactory.getLog(clazz).info(msg, t);
    }
    /* **************************************** */

    /* *****************************************
     * Metodos que reciben un Object y el mensaje
     * *****************************************/
    public static void logDebug(Object obj, String msg) {
        Log log = LogFactory.getLog(obj.getClass());
        if (log != null && log.isDebugEnabled()) {
            log.debug("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
        }
//        LogFactory.getLog(obj.getClass()).debug("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
    }

    public static void logError(Object obj, String msg) {
        LogFactory.getLog(obj.getClass()).error("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
    }

    public static void logInfo(Object obj, String msg) {
        LogFactory.getLog(obj.getClass()).info("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
    }

    public static void logTrace(Object obj, String msg) {
        LogFactory.getLog(obj.getClass()).trace("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
    }
    /* **************************************** */
    /* *****************************************
     * Metodos que reciben un Object, el mensaje y la excepcion
     * *****************************************/

    public static void logDebug(Object obj, String msg, Throwable t) {
        Log log = LogFactory.getLog(obj.getClass());
        if (log != null && log.isDebugEnabled()) {
            log.debug("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
        }
//        LogFactory.getLog(obj.getClass()).debug("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
    }

    public static void logError(Object obj, String msg, Throwable t) {
        LogFactory.getLog(obj.getClass()).error("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
    }

    public static void logInfo(Object obj, String msg, Throwable t) {
        LogFactory.getLog(obj.getClass()).info("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
    }

    public static void logTrace(Object obj, String msg, Throwable t) {
        LogFactory.getLog(obj.getClass()).trace("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
    }
    /* **************************************** */
    /* *****************************************
     * Metodos que reciben un Class, el mensaje y etiqueta
     * *****************************************/

    public static void logDebug(Class clazz, String msg, String valueLavel) {
        Log log = LogFactory.getLog(clazz);
        if (log != null && log.isDebugEnabled()) {
            MDC.put("clazz", valueLavel);
            log.debug(msg);
            MDC.remove("clazz");
        }
    }

    public static void logError(Class clazz, String msg, String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(clazz).error(msg);
        MDC.remove("clazz");
    }

    public static void logInfo(Class clazz, String msg, String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(clazz).info(msg);
        MDC.remove("clazz");
    }
    /* **************************************** */


    /* *****************************************
     * Metodos que reciben un Class, el mensaje y la excepcion
     * *****************************************/
    public static void logDebug(Class clazz, String msg, Throwable t,  String valueLavel) {
        Log log = LogFactory.getLog(clazz);
        if (log != null && log.isDebugEnabled()) {
            MDC.put("clazz", valueLavel);
            log.debug(msg, t);
            MDC.remove("clazz");
        }
    }

    public static void logError(Class clazz, String msg, Throwable t,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(clazz).error(msg, t);
        MDC.remove("clazz");
    }

    public static void logInfo(Class clazz, String msg, Throwable t,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(clazz).info(msg, t);
        MDC.remove("clazz");
    }
    /* **************************************** */

    /* *****************************************
     * Metodos que reciben un Object y el mensaje
     * *****************************************/
    public static void logDebug(Object obj, String msg,  String valueLavel) {
        Log log = LogFactory.getLog(obj.getClass());
        if (log != null && log.isDebugEnabled()) {
            MDC.put("clazz", valueLavel);
            log.debug("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
            MDC.remove("clazz");
        }
    }

    public static void logError(Object obj, String msg,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(obj.getClass()).error("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
        MDC.remove("clazz");
    }

    public static void logInfo(Object obj, String msg,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(obj.getClass()).info("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
        MDC.remove("clazz");
    }

    public static void logTrace(Object obj, String msg,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(obj.getClass()).trace("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg);
        MDC.remove("clazz");
    }
    /* **************************************** */
    /* *****************************************
     * Metodos que reciben un Object, el mensaje y la excepcion
     * *****************************************/

    public static void logDebug(Object obj, String msg, Throwable t,  String valueLavel) {
        Log log = LogFactory.getLog(obj.getClass());
        if (log != null && log.isDebugEnabled()) {
            MDC.put("clazz", valueLavel);
            log.debug("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
            MDC.remove("clazz");
        }
    }

    public static void logError(Object obj, String msg, Throwable t,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(obj.getClass()).error("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
        MDC.remove("clazz");
    }

    public static void logInfo(Object obj, String msg, Throwable t,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(obj.getClass()).info("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
        MDC.remove("clazz");
    }

    public static void logTrace(Object obj, String msg, Throwable t,  String valueLavel) {
        MDC.put("clazz", valueLavel);
        LogFactory.getLog(obj.getClass()).trace("{" + Integer.toHexString(obj.hashCode()) + "}::>" + msg, t);
        MDC.remove("clazz");
    }
    /* **************************************** */
}
