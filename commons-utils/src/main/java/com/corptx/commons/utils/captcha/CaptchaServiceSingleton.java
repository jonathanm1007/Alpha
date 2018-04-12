/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corptx.commons.utils.captcha;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import javax.servlet.http.HttpServletResponse;

/**
 *
 ** @author William Diaz Pabón
 */
public class CaptchaServiceSingleton {

    private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();

    public static ImageCaptchaService getInstance() {
        return instance;
    }

//    public HttpServletResponse generateImage(){
//        HttpServletResponse reponse = new HttpServletResponse();
//    }
}
