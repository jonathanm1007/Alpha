/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author William
 */
public class ConverterJAXB {

    public static String converterBeanToXML(Object classBean) throws Exception {
        
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        JAXBContext jaxbContext = JAXBContext.newInstance(classBean.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(classBean, arrayOutputStream);

        return arrayOutputStream.toString();

    }

    public static <T> T converterXMLToBean(Class<T> classBean, String xmlBean) throws Exception {
        
        if (null == classBean) {
            throw new IllegalArgumentException("El parametro classBean es nullo.");
        }
        if (StringUtils.isBlank(xmlBean)) {
            throw new IllegalArgumentException("El parametro xmlBean es nullo o vacio.");
        }

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(xmlBean.getBytes());
        
        JAXBContext jaxbContext = JAXBContext.newInstance(classBean);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        T t = (T) jaxbUnmarshaller.unmarshal(arrayInputStream);

        return t;

    }
}
