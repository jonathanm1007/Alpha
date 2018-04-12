/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.mail;

import com.corptx.commons.utils.collections.UtilList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 ** @author William Diaz Pabón
 */
public class UtilMail {

    public static void sendSimpleEmail(String host, String port, boolean ssl, String from, String user, String password, String subject, String msg, String... to) throws Exception {
        if (StringUtils.isNotBlank(host)) {
            if (StringUtils.isNotBlank(port)) {
                if (StringUtils.isNotBlank(from)) {
                    if (to != null) {

                        // Creando la instancia
                        SimpleEmail email = new SimpleEmail();

                        // Colocando el servidor de correo
                        email.setHostName(host);

                        // Colocando el puerto de salida
//                        if (ssl) {
//                            email.setTLS(true);
//                            email.setSslSmtpPort(port);
//                        } else {
//                            email.setSmtpPort(Integer.parseInt(port));
//                        }
                        if (ssl) {
                            email.setTLS(true);
                        }
                        email.setSmtpPort(Integer.parseInt(port));

                        // Colocando la cuenta origen
                        email.setFrom(from);

                        // Verifica si hay que hacer autenticacion
                        if (StringUtils.isNotBlank(user)) {
                            if (StringUtils.isNotBlank(password)) {
                                email.setAuthentication(user, password);
                            } else {
                                throw new IllegalArgumentException("No esta especificada la clave de logueo del correo origen");
                            }
                        }

                        // Adicionando las cuentas destino
                        for (String string : to) {
                            email.addTo(string);
                        }

                        // Colocando el asunto del mensaje
                        if (StringUtils.isNotBlank(subject)) {
                            email.setSubject(subject);
                        }

                        // Colocando el cuerpo del mensaje
                        if (StringUtils.isNotBlank(msg)) {
                            email.setMsg(msg);
                        }

                        // Enviando el correo
                        email.send();
                    } else {
                        throw new IllegalArgumentException("No esta especificada las cuentas de correo destino");
                    }
                } else {
                    throw new IllegalArgumentException("No esta especificado correo origen (from)");
                }
            } else {
                throw new IllegalArgumentException("No esta especificado el puerto de salida");
            }
        } else {
            throw new IllegalArgumentException("No esta especificado el servidor de correo");
        }
    }

    public static void sendHtmlEmail(String host, String port, boolean ssl, String from, String user, String password, String subject, List<String> listMsg, String... to) throws Exception {
        if (StringUtils.isNotBlank(host)) {

            if (StringUtils.isNotBlank(port)) {

                if (StringUtils.isNotBlank(from)) {

                    if (to != null) {

                        // Creando la instancia
                        HtmlEmail email = new HtmlEmail();

                        // Colocando el servidor de correo
                        email.setHostName(host);

                        // Colocando el puerto de salida
                        if (ssl) {
                            email.setTLS(true);
                            email.setSslSmtpPort(port);
                        } else {
                            email.setSmtpPort(Integer.parseInt(port));
                        }

                        // Colocando la cuenta origen
                        email.setFrom(from);

                        // Verifica si hay que hacer autenticacion
                        if (StringUtils.isNotBlank(user)) {

                            if (StringUtils.isNotBlank(password)) {
                                email.setAuthentication(user, password);
                            } else {
                                throw new IllegalArgumentException("No esta especificada la clave de logueo del correo origen");
                            }

                        }

                        // Adicionando las cuentas destino
                        for (String string : to) {
                            email.addTo(string);
                        }

                        // Colocando el asunto del mensaje
                        if (StringUtils.isNotBlank(subject)) {
                            email.setSubject(subject);
                        }

                        // Colocando el cuerpo del mensaje
                        if (UtilList.isNotEmpty(listMsg)) {
                            StringBuilder htmlMsg = new StringBuilder();
                            htmlMsg.append("<html>");
                            for (String string : listMsg) {
                                htmlMsg.append(string + "<br>");
                            }
                            htmlMsg.append("</html>");
                            email.setHtmlMsg(htmlMsg.toString());

                            // set the alternative message
                            email.setTextMsg("Your email client does not support HTML messages");

                        }

                        // Enviando el correo
                        email.send();
                    } else {
                        throw new IllegalArgumentException("No esta especificada las cuentas de correo destino");
                    }
                } else {
                    throw new IllegalArgumentException("No esta especificado correo origen (from)");
                }
            } else {
                throw new IllegalArgumentException("No esta especificado el puerto de salida");
            }
        } else {
            throw new IllegalArgumentException("No esta especificado el servidor de correo");
        }
    }

}
