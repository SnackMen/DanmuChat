package com.ws.alpha.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author mumu
 */
@Controller
public class WxValidateController {

    private static Logger logger = Logger.getLogger(WxValidateController.class);

    /**
     * 微信接口校验
     * @param request
     * @param response
     */
    @RequestMapping("/dchat")
    public void dchat(HttpServletRequest request, HttpServletResponse response){
        logger.info("接入成功");
        String token = "danmuchat";

        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        String echostr = request.getParameter("echostr");
        logger.info("参数 token:"+token+",timestamp:"+timeStamp+",nonce:"+nonce+",signature:"+signature+",echostr:"+echostr);
        System.out.println("参数 token:"+token+",timestamp:"+timeStamp+",nonce:"+nonce+",signature:"+signature+",echostr:"+echostr);
        //对参数进行字典排序
        String []para = {token,timeStamp,nonce};
        Arrays.sort(para);
        //排序之后转换成字符串
        StringBuilder stringBuilder = new StringBuilder("");
        for (String aPara : para) {
            stringBuilder.append(aPara);
        }
        logger.info("排序结果: " +stringBuilder.toString());

        //sha1加密工具
        MessageDigest messageDigest = null;
        try {
            //获取加密工具对象
            messageDigest = MessageDigest.getInstance("SHA-1");
            //加密并转换成十六进制字符串
            String mySignature = byteToStr(messageDigest.digest(stringBuilder.toString().getBytes()));
            if(mySignature.equals(signature.toUpperCase())){
                logger.info("=============验证成功=============");
                //原样返回微信服务器发送的参数，同样不需要加密
                PrintWriter out = response.getWriter();
                out.print(echostr);
                logger.info("============写出返回值成功==============");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("获取加密对象失败: "+e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("输出流失败: "+e);
            e.printStackTrace();
        }
    }

    /**
     * 将自己转换成十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }


    /**
     * 将字节数组转换成十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {

        StringBuilder strDigest = new StringBuilder();
        for (byte aByteArray : byteArray) {
            strDigest.append(byteToHexStr(aByteArray));
        }
        return strDigest.toString();

    }

}
