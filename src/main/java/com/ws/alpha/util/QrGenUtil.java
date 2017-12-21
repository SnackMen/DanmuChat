package com.ws.alpha.util;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author mumu
 */
public class QrGenUtil {

    public static ByteArrayOutputStream createQrGen(String url) throws IOException {

        //如果有中文，可以使用withCharset("UTF-8")方法
        return QRCode.from(url).withSize(270,270).to(ImageType.JPG).stream();
    }
}
