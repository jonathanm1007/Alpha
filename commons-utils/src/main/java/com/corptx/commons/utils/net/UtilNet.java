/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.net;

import org.apache.commons.lang.StringUtils;
import java.io.File;
import com.jcraft.jsch.*;

/**
 * Clase que permite realizar conexiones remotas usando protocolos como HTTP,
 * HTTPS, FTP, etc
 *
 * @author william
 */
public class UtilNet {

    private static final String PROTOCOL_HTTPS = "https";
    public static final String PROTOCOL_SFTP = "sftp";

    /**
     * Metodo que permite a un HttpClient aceptar cualquier certificado para
     * conexiones SSL
     *
     * @param base
     * @return
     */
//    private static HttpClient wrapClient(HttpClient base) {
//        try {
//            SSLContext ctx = SSLContext.getInstance("TLS");
//            X509TrustManager tm = new X509TrustManager() {
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            };
//            ctx.init(null, new TrustManager[]{tm}, null);
//            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
//            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            ClientConnectionManager ccm = base.getConnectionManager();
//            SchemeRegistry sr = ccm.getSchemeRegistry();
//            sr.register(new Scheme("https", ssf, 443));
//            return new DefaultHttpClient(ccm, base.getParams());
//
//        } catch (Exception ex) {
//            logError(UtilNet.class, "Error procesanso el HttpClient para que acepte cualquier certificado. " + ex.getMessage(), ex);
//            return null;
//        }
//    }
//    public static File getFileURL(URL url, String user, String pass) throws IllegalArgumentException, Exception {
//        File file = null;
//
//        if (url == null) {
//            throw new IllegalArgumentException("No existe el parametro url.");
//        }
//        if (StringUtils.isBlank(url.getHost())) {
//            throw new IllegalArgumentException("No existe un Host en el parametro url " + url);
//        }
//
//        logDebug(UtilNet.class, "url.getProtocol() = " + url.getProtocol());
//
//        if (StringUtils.isNotBlank(url.getProtocol())) {
//
//            if (StringUtils.equals(url.getProtocol(), PROTOCOL_HTTPS)) {
//
//                file = getFileHttps(url, user, pass);
//
//            } else {
//
//                throw new IllegalArgumentException("La URL "
//                        + url
//                        + " contiene el protocolo "
//                        + url.getProtocol()
//                        + ", el cual no se encuentra soportado en estos momentos.");
//            }
//
//        } else {
//            throw new IllegalArgumentException("La URL "
//                    + url
//                    + " no contiene un protocolo valido.");
//        }
//
//        return file;
//    }
    /**
     * Metodo que obtiene un archivo desde una url con conexión segura SSL. <br>
     * Este metodo es usado internamente, por tal motivo no se valida el
     * parametro de la URL
     *
     * @param url
     * @param user
     * @param pass
     * @return
     */
//    private static File getFileHttps(URL url, String user, String pass) throws IllegalArgumentException, Exception {
//
//        File file = null;
//        DefaultHttpClient httpclient = null;
//
//        if (StringUtils.isBlank(user)) {
//            throw new IllegalArgumentException("Parametro user no valido");
//        }
//        if (StringUtils.isBlank(pass)) {
//            throw new IllegalArgumentException("Parametro pass no valido");
//        }
//
//        // Obteniendo el Host 
//        String host = url.getHost();
//
//        // Obteniendo el Puerto
//        int port = url.getPort();
//        if (port == -1) {
//            port = url.getDefaultPort();
//        }
//
//        // Configurando el HttpClient para que acepte cualquier certificado
//        httpclient = (DefaultHttpClient) wrapClient(new DefaultHttpClient());
//
//        // Asignando datos para la validación del SSL
//        httpclient.getCredentialsProvider().setCredentials(
//                new AuthScope(host, port),
//                new UsernamePasswordCredentials(user, pass));
//
//        // Obtener el reponse
//        HttpResponse response = httpclient.execute(new HttpGet(url.toURI()));
//        if (response == null) {
//            throw new Exception("No se pudo obtener el HttpResponse de la url " + url);
//        }
//
//        // Obtener el entity 
//        HttpEntity entity = response.getEntity();
//        if (entity == null) {
//            throw new Exception("No se pudo obtener el HttpEntity de la url " + url);
//        }
//        logInfo(UtilNet.class, "Tamaño del archivo en la url = " + entity.getContentLength());
//
//        // Descargando archivo
//        file = File.createTempFile("receiveFile", ".part");
//        ReadableByteChannel rbc = Channels.newChannel(entity.getContent());
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        fileOutputStream.getChannel().transferFrom(rbc, 0, 1 << 24);
//
//        return file;
//    }
    public static void transferDataToServer(String host, int port, String username, String password,
            String protocolo, String remoteDirectory, String localDirectory, String extension) throws Exception {

        if (StringUtils.isBlank(host)
                || StringUtils.isBlank(username)
                || StringUtils.isBlank(protocolo)
                || StringUtils.isBlank(remoteDirectory)
                || StringUtils.isBlank(localDirectory)) {
            throw new IllegalArgumentException("Parametros no validos: host,username, protocolo, remoteDirectory, localDirectory son requeridos.");
        }

        // Validando que directorio local sea un carpeta existente.

        File localDirectoryFile = new File(localDirectory);
        if (!localDirectoryFile.exists()) {
            throw new Exception("No existe el " + (localDirectoryFile.isDirectory() ? " directorio" : "archivo") + " con la ruta: " + localDirectory);
        }




        // estableciendo conexion con el servidor
        JSch jsch = new JSch();
        Session session = null;

        session = port > 0 ? jsch.getSession(username, host, port) : jsch.getSession(username, host);
        if (session == null) {
            throw new Exception("No se estableció una sessión con el  host: " + host + (port > 0 ? ":" + String.valueOf(port) : "") + " y el usuario: " + username);
        }

        session.setPassword(password);
        java.util.Properties properties = new java.util.Properties();
        properties.put("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        if (!session.isConnected()) {
            throw new Exception("No se establecio una conexión con el host: " + host + (port > 0 ? ":" + String.valueOf(port) : "") + " y el usuario: " + username);
        }
        Channel channel = session.openChannel(protocolo);
        if (channel == null) {
            throw new Exception("No se  estableció un canal de conexión: " + host + (port > 0 ? ":" + String.valueOf(port) : "") + " y el usuario: " + username);
        }

        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        // pasando a la carpeta de destino
        sftpChannel.cd(remoteDirectory);
        if (localDirectoryFile.isDirectory()) {
            try {
                // crando el direcorio de destino
                //System.out.println("localDirectoryFile.getName() = " + localDirectoryFile.getName());
                sftpChannel.mkdir(localDirectoryFile.getName());
            } catch (Exception ex) {
                throw new Exception("No fue posible crear el direcotorio : " + localDirectoryFile.getName() + " en la ruta: " + remoteDirectory);
            }

            File[] list = localDirectoryFile.listFiles();
            for (File file : list) {
                if (file.isFile()
                        && !file.isHidden()) {
                    //System.out.println("uploading....  " + file.getName());
                    if (extension != null) {
                        if (file.getName().toLowerCase().endsWith(extension)) {
                            sftpChannel.put(file.getAbsolutePath(), (remoteDirectory + System.getProperty("file.separator") + localDirectoryFile.getName()));
                        }
                    } else {
                        sftpChannel.put(file.getAbsolutePath(), (remoteDirectory + System.getProperty("file.separator") + localDirectoryFile.getName()));
                    }
                }
            }

        } else {
            if (extension != null) {
                if (localDirectory.toLowerCase().endsWith(extension.toUpperCase())) {
                    sftpChannel.put(localDirectory, remoteDirectory);
                }
            } else {
                sftpChannel.put(localDirectory, remoteDirectory);
            }

        }

        sftpChannel.exit();
        session.disconnect();
    }

    public static void main(String[] args) {

//        //String host = "192.168.2.3";
//        String host = "190.146.238.160";
//        String username = "root";
//        //String password = "publicador";
//        String password = "serverdb2011";
//        int port = 22;
//        String protocolo = "sftp";
////        String remoteDirectory = "/opt/sftp/tmp/";
//        String remoteDirectory = "/var/www/html/grupotx/sit/liquidacion_masiva/";
//        String localDirectory = "/home/jorge-garcia/Aplicaciones/jboss-5.1.0.GA/server/default/reportes/liquidacion_predial/00000002%[null-null]-2012-01-24-07-05-22/liquidacion_predial-11681-000000020001000.pdf";
//        if (StringUtils.isNotBlank(host)) {
//            System.out.println("localDirectory = " + localDirectory);
//        }
        String host = "10.0.12.5";
        String username = "root";
        //String password = "publicador";
        String password = "IpM8h2flxoJpL4D6";
        int port = 22;
        String protocolo = "sftp";
//        String remoteDirectory = "/opt/sftp/tmp/";
        String remoteDirectory = "/opt/lampp/htdocs/grupotx/sit/ocana_fiscalizacion_predial_masiva";
        String localDirectory = "/home/johant/liquidacion-427_oficio-777.pdf";
        if (StringUtils.isNotBlank(host)) {
            System.out.println("localDirectory = " + localDirectory);
        }
        try {
            transferDataToServer(host, port, username, password, protocolo, remoteDirectory, localDirectory, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
