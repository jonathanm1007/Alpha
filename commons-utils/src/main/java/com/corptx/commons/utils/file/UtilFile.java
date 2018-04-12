/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.file;

import com.corptx.commons.utils.collections.UtilList;
import com.corptx.commons.utils.lang.UtilStrTokenizer;
import com.corptx.commons.utils.lang.UtilString;
import com.corptx.commons.utils.time.FormatDate;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang.StringUtils;
import org.pdfbox.PDFMerger;


// Imports Estaticos
import static com.corptx.commons.utils.log.UtilLog.*;
import java.io.*;
import java.nio.channels.FileChannel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase que realiza tareas adicionales a las librerias File existentes en el
 * API de java.<br> Para el correcto funcionamiento de esta clase es requerida
 * las siguientes librerias:<br> <ul> <li>commons-lang-2.4</li>
 * <li>commons-logging-1.1.1</li> </ul>
 *
 ** @author William Diaz Pabón Diaz Pabón
 */
public class UtilFile {

    private static Log log = LogFactory.getLog(UtilFile.class);

    /**
     * Abre un archivo plano y lo retorna en lista de String[]
     *
     * @param nameFile Nombre completo con URL del archivo plano a abrir
     * @param strSeparator Caracter de separación de columnas
     * @return Lista con registros String[] con el contenido del archivo
     * @throws Exception
     */
    public static List<String[]> ConverterFileToList(String nameFile, char strSeparator) throws Exception {
        List listFile = null;
        if (!StringUtils.isBlank(nameFile)) {
            listFile = new ArrayList();
            try {
                FileReader frFile = new FileReader(nameFile);
                BufferedReader brFile = new BufferedReader(frFile);
                String line = null;
                while ((line = brFile.readLine()) != null) {
                    if (!StringUtils.isBlank(line)) {
                        String[] tokens = (new UtilStrTokenizer(line, strSeparator)).getTokens();
                        listFile.add(tokens);
                    }
                }
            } catch (Exception e) {
                String msg = "Error convirtiendo archivo a lista";
                logError(UtilFile.class, msg, e);
                throw new Exception(msg, e);
            }
        }
        return listFile;
    }

    /**
     * Convierte el contenido de un archivo plano a una lista sin separador de
     * columnas
     *
     * @param nameFile
     * @return
     * @throws java.lang.Exception
     */
    public static List<String> ConverterFileToList(String nameFile) throws Exception {
        List listFile = null;
        if (!StringUtils.isBlank(nameFile)) {
            listFile = new ArrayList();
            try {
                FileReader frFile = new FileReader(nameFile);
                BufferedReader brFile = new BufferedReader(frFile);
                String line = null;
                while ((line = brFile.readLine()) != null) {
                    if (!StringUtils.isBlank(line)) {
                        listFile.add(line);
                    }
                }
            } catch (Exception e) {
                String msg = "Error convirtiendo archivo a lista";
                logError(UtilFile.class, msg, e);
                throw new Exception(msg, e);
            }
        }
        return listFile;
    }

    /**
     * M�todo que abre un archivo por el nombre indicado y retorna su contenido
     * como un array de bytes
     *
     * @param nameFile Nombre del archivo a abrir
     * @return Array de bytes con el contenido del archivo. <br> Si el parametro
     * llega nulo o vacio se retornar� NULL.
     */
    public static byte[] readFileToByte(String nameFile) {
        byte[] b = null;
        if (!StringUtils.isBlank(nameFile)) {
            File file = new File(nameFile);
            b = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(b);
            } catch (FileNotFoundException e) {
                logError(UtilFile.class, "Archvivo no encontrado", e);
            } catch (IOException e) {
                logError(UtilFile.class, "Error cargando el archivo", e);
            }
        }
        return b;
    }

    public static boolean saveFile(List<String[]> lista, String nameFileDest) {
        boolean exito = false;
        return exito;
    }

    /**
     * Metodo que crea un archivo desde una ruta base y recorriendo carpetas
     * hacia atras
     *
     * @param urlBase
     * @param urlAdd nueva ruta (sin separador de inicio y fin) que se adiciona
     * a la ruta base despues de retroceder la cantida dde carpetas indicadas
     * @param backFolder
     * @param nameNewFile
     * @return Objeto File con el nuevo archivo o null si no lo puede crear
     */
    public static File getFileFromURLBase(String urlBase, int backFolder, String urlAdd, String nameNewFile) {
        File file = null;
        if (StringUtils.isNotBlank(urlBase)
                && StringUtils.isNotBlank(nameNewFile)) {
            File f = new File(urlBase);
            for (int i = 0; i < backFolder; i++) {
                f = f.getParentFile();
            }
            logDebug(UtilFile.class, "f.getAbsolutePath() = " + f.getAbsolutePath());
            logDebug(UtilFile.class, "File.separator = " + File.separator);

            // Se adiciona al archivo la url a adicionar
            if (StringUtils.isNotBlank(urlAdd)) {
                nameNewFile = urlAdd + File.separator + nameNewFile;
            }
            // Creamos el archivo log
            file = new File(f.getAbsolutePath() + File.separator + nameNewFile);
        }
        return file;
    }

    /**
     * Metodo que imprime un mensaje usando la salida out la cual debe haber
     * sido redireccionada a un archivo log con anterioridad
     *
     * @param msg
     */
    public static void writeLogSout(String msg) {
        System.out.println(FormatDate.formatDateByMask(FormatDate.FORMAT_MINUS_YYYY_MM_DD_HH_MM_SS, new Date()) + " - " + msg);
    }

    public static boolean deleteFile(String pathFile) {
        logDebug(UtilFile.class, "Entro a deleteFile = ");
        boolean exito = false;
        try {
            File file = new File(pathFile);
            if (file.delete()) {
                exito = true;
            }
        } catch (Exception ex) {
            logDebug(UtilFile.class, "Error eliminando archivo = " + ex);
        }
        logDebug(UtilFile.class, "Salio de deleteFile = " + exito);
        return exito;
    }

    public static String crearFileTemporal(String nameFile, String extension) {
        String path = null;
        try {
            File tmpFile = File.createTempFile(nameFile, extension);
            path = tmpFile.getCanonicalPath();

        } catch (IOException ex) {
            ex.printStackTrace();
            path = null;
        }

        return path;
    }

    public static String crearFileTemporal(byte[] bites, String nameFile, String extension) {
        String path = null;
        try {
            File tmpFile = File.createTempFile(nameFile, extension);
            path = tmpFile.getCanonicalPath();
            FileOutputStream outputStream = new FileOutputStream(tmpFile);
            outputStream.write(bites);
            outputStream.close();
        } catch (IOException ex) {
            path = null;
        }

        return path;
    }

    public static boolean unirArchvosPDF(List<String> lista) {
        boolean retorno = false;
        try {
            String[] pathFile = UtilString.listToArrayVector(lista);
            if (pathFile != null) {
                PDFMerger.main(pathFile);
                retorno = true;
            }
        } catch (Exception e) {
            logError(UtilFile.class, "Error Uniendo Archivos PDF = ", e);
        }
        return retorno;
    }

    /**
     * Metodo que crea un archivo comprimido desde una lista de Object[] que
     * debe estar conformado asi: <p> Object[0] = Nombre de archivo con
     * extensi�n Ej: archivo_01.pdf Object[1] = Conjunto de byte que contiene la
     * informaci�n del archivo </p>
     *
     * @param listData
     * @return
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public static byte[] createZip(List<Object[]> listData) throws IllegalArgumentException, Exception {
        if (listData != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(baos);
                for (Object[] objects : listData) {
                    if (objects != null) {
                        zos.putNextEntry(new ZipEntry((String) objects[0]));
                        zos.write((byte[]) objects[1], 0, ((byte[]) objects[1]).length);
                    }
                }
                zos.closeEntry();
                zos.close();
                return baos.toByteArray();
            } catch (Exception e) {
                throw new Exception(e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("Parametros no validos");
        }
    }

    public static byte[] createZipPathName(List<String> listPathName) throws IllegalArgumentException, Exception {
        if (UtilList.isNotEmpty(listPathName)) {
            try {
                List<Object[]> listData = new ArrayList<Object[]>();
                for (String pathName : listPathName) {
                    byte[] newByte = readFileToByte(pathName);
                    if (newByte != null) {
                        File file = new File(pathName);
                        Object[] object = new Object[]{file.getName(), newByte};
                        listData.add(object);
                    }
                }
                return createZip(listData);
            } catch (Exception e) {
                throw new Exception(e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("Parametros no validos");
        }
    }

    /**
     * Metodo que crea un archivo comprimido desde una lista de Object que debe
     * pueden ser una lista se Strings con las rutas de los archivos o una lista
     * de Objetos File
     *
     * @param listPathsOrFiles
     * @return fileZip
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public static File createFileZipPathTemporal(List listPathsOrFiles) throws IllegalArgumentException, Exception {

        return createFileZipOnPath(listPathsOrFiles, null);
    }

    public static File createFileZipOnPath(List listPathsOrFiles, String pathFileZip) throws IllegalArgumentException, Exception {

        if (UtilList.isNotEmpty(listPathsOrFiles)) {
            try {

                File tmpFileZip;
                if (pathFileZip == null) {
                    tmpFileZip = File.createTempFile(RandomStringUtils.randomAlphanumeric(15), ".zip");
                } else {
                    tmpFileZip = new File(pathFileZip);
                }
                FileOutputStream fos = new FileOutputStream(tmpFileZip.getCanonicalPath());
                ZipOutputStream zos = new ZipOutputStream(fos);

                for (Object objetoArchivoORuta : listPathsOrFiles) {

                    if (objetoArchivoORuta instanceof String) {
                        addToZipFile((String) objetoArchivoORuta, zos);
                    } else if (objetoArchivoORuta instanceof File) {
                        addToZipFile(((File) objetoArchivoORuta).getCanonicalPath(), zos);
                    }
                }

                if (log.isDebugEnabled()) {
                    log.debug("tmpFileZip.getCanonicalPath() = " + tmpFileZip.getCanonicalPath());
                }

                zos.close();
                fos.close();
                return tmpFileZip;

            } catch (Exception e) {
                throw new Exception(e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("Parámetros no válidos");
        }
    }

    private static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

//    public static byte[] createZip(List<String> listData) throws IllegalArgumentException, Exception {
//        if (listData != null) {
//            try {
//                byte[] buffer = new byte[1024];
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ZipOutputStream zos = new ZipOutputStream(baos);
//                for (String patchFile : listData) {
//                    if (StringUtils.isNotBlank(patchFile)) {
//                        FileInputStream fis = new FileInputStream(patchFile);
//                        zos.putNextEntry(new ZipEntry(patchFile));
//
//                        int len;
//                        while ((len = fis.read(buffer)) > 0) {
//                            zos.write(buffer, 0, len);
//                        }
//                        fis.close();
//                    }
//                }
//                zos.closeEntry();
//                zos.close();
//                return baos.toByteArray();
//            } catch (Exception e) {
//                throw new Exception(e.getMessage(), e);
//            }
//        } else {
//            throw new IllegalArgumentException("Parametros no validos");
//        }
//    }
    public static boolean existFile(String pathFile) {
        boolean existFile = false;
        try {
            File file = new File(pathFile);
            if (!file.isDirectory()
                    && file.isFile()
                    && file.exists()) {
                existFile = true;
            }
        } catch (Exception ex) {
            logDebug(UtilFile.class, "Error en existFile,", ex);
            logError(UtilFile.class, "Error en existFile," + ex.getMessage());
        }
        return existFile;
    }

    /**
     * Copia un solo archivo
     *
     * @param source
     * @param destino
     * @throws IOException
     */
    public static boolean copyFile(File source, File destino) {
        try {
            FileChannel in = (new FileInputStream(source)).getChannel();
            FileChannel out = (new FileOutputStream(destino)).getChannel();
            in.transferTo(0, source.length(), out);
            in.close();
            out.close();
            return true;
        } catch (Exception e) {
            ;
        }
        return false;
    }

    public static boolean ifDontExistsCreateFile(String pathFile) {
        boolean createdFile = true;

        try {
            if (existFile(pathFile)) {
                createdFile = false;
            } else {
                File newFile = new File(pathFile);
                if (newFile != null) {
                    newFile.mkdirs();
                } else {
                    createdFile = false;
                }
            }
        } catch (Exception ex) {
            logDebug(UtilFile.class, "Error en ifDontExistsCreateFile " + ex.getMessage());
            logError(UtilFile.class, "Error en ifDontExistsCreateFile ", ex);
            createdFile = false;
        }
        return createdFile;
    }

    private static void saveObject(Object obj, String fileName) throws Exception {
        saveObject(obj, new File(fileName));
    }

    private static void saveObject(Object obj, File file) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bos.flush();
            fos.flush();
        } catch (IOException e) {
            throw new Exception("Error saving file : " + file, e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static void saveObject(Object obj, OutputStream os) throws Exception {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            throw new Exception("Error saving object to OutputStream", e);
        }
    }

    public static void writeToFile(String nombreArchivo, StringBuffer bufferEntrada) throws IOException {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(nombreArchivo));
            out.write(bufferEntrada.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            logError(UtilFile.class, "Se encontro un error en writeToFile, " + e.getMessage(), e);
        }
    }

    public static int filesInDirectory(String path) {
        int retorno = -2;
        try {
            File file;
            file = new File(path);
            if (file.exists()) {
                retorno = file.list().length;
            } else {
                retorno = -1;
            }
        } catch (Exception e) {
            logError(UtilFile.class, "Se encontro un error en filesInDirectory, " + e.getMessage(), e);
        } finally {
            return retorno;
        }
    }

    /**
     * Método generalizado que guarda un archivo en disco, dependiendo del tipo
     * que sea. (by Alexander Quintero)
     *
     *
     * @param objetoAGuardar objeto que se guardará en disco
     * @param dondeGuardar objeto que indica donde se guardará el
     * objetoAGuardar, puede ser un String con la ruta, un File o un OuputStream
     */
    public static void saveFileOnDisc(Object objetoAGuardar, Object dondeGuardar) {
        try {

            if (objetoAGuardar instanceof JasperPrint && dondeGuardar instanceof String) {

                JRSaver.saveObject(objetoAGuardar, (String) dondeGuardar);

            } else if (objetoAGuardar instanceof byte[] && dondeGuardar instanceof String) {

                FileOutputStream fos = new FileOutputStream((String) dondeGuardar);
                fos.write((byte[]) objetoAGuardar);
                fos.close();

            } else if (dondeGuardar instanceof File) {

                saveObject(objetoAGuardar, (File) dondeGuardar);

            } else if (dondeGuardar instanceof String) {

                saveObject(objetoAGuardar, (String) dondeGuardar);

            } else if (dondeGuardar instanceof OutputStream) {

                saveObject(dondeGuardar, (OutputStream) dondeGuardar);

            }

        } catch (Exception e) {
            logError(UtilFile.class, "Se encontro un error al intentar guardar el Objeto en Disco " + e.getMessage(), e);
        }
    }

    /**
     * <p>Metodo que crea un archivo comprimido desde una lista de Archivo, en
     * donde la propiedad nombreArchivo corresponde al Nombre de archivo con
     * extensión Ej: nombre_archivo_01.pdf, y la propiedad datosArchivo
     * corresponde al Conjunto de byte que contiene la informacón del archivo
     * </p>
     *
     * @param listaArchivos
     * @return byte[]
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public static byte[] createZipArchive(List<Archivo> listaArchivos) throws IllegalArgumentException, Exception {
        if (UtilList.isNotEmpty(listaArchivos)) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(baos);
                for (Archivo archivo : listaArchivos) {
                    if (archivo != null) {
                        zos.putNextEntry(new ZipEntry(archivo.getNombreArchivo()
                                + (archivo.getExtensionArchivo() != null ? archivo.getExtensionArchivo() : "")));
                        zos.write(archivo.getDatosArchivo(), 0, archivo.getDatosArchivo().length);
                    }
                }
                zos.closeEntry();
                zos.close();
                return baos.toByteArray();
            } catch (Exception e) {
                throw new Exception(e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("La lista de archivos esta vacia.");
        }
    }
}
