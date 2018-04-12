/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.security;

import java.security.MessageDigest;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author jorge-garcia
 */
public class Encrypt {

    public static enum HashAlgorithm {

        MD5
    };

    public static String encryptPasswordToHex(String passwd, HashAlgorithm hashAlgorithm) throws Exception {
        String encryptPassword = null;
        String hash;

        if (StringUtils.isNotBlank(passwd)) {

            switch (hashAlgorithm) {
                case MD5:
                    hash = "MD5";
                    break;
                default:
                    throw new Exception("HashAlgorithm no soportado");
            }

            try {
                MessageDigest md = MessageDigest.getInstance(hash);
                byte[] array = md.digest(passwd.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
                }
                encryptPassword = sb.toString();

            } catch (java.security.NoSuchAlgorithmException e) {
                throw e;
            } catch (Exception ex) {
                throw ex;
            }
        }

        return encryptPassword;
    }

    public static void main(String[] args) {

        try {
            String pass = encryptPasswordToHex("jegarcia", HashAlgorithm.MD5);
            System.out.println("pass = " + pass);
        } catch (Exception ex) {
        }

    }
}
