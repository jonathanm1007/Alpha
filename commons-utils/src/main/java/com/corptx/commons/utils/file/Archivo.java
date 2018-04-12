package com.corptx.commons.utils.file;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Alexander Quintero
 */
public class Archivo implements Serializable {

    /**
     * Representa el nombre del archivo, sin la extensión.
     */
    private String nombreArchivo;
    /**
     * Representa el la extensión con el punto que la antecede.
     */
    private String extensionArchivo;
    /**
     * Propiedad para guardar la ruta completa del archivo en disco.
     */
    private String rutaArchivo;
    /**
     * Representa el flujo de bytes que contiene el archivo.
     */
    private byte[] datosArchivo;
    /**
     * Propiedad para agregar alguna observación al archivo y que pueda ser
     * utilizada en algun momento.
     */
    private String observacion;
    /**
     * Se usa para guardar un archivo temporal
     */
    private File file;

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public byte[] getDatosArchivo() {
        return datosArchivo;
    }

    public void setDatosArchivo(byte[] datosArchivo) {
        this.datosArchivo = datosArchivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
