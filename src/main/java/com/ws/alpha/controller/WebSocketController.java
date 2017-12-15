package com.ws.alpha.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.alpha.entiy.Message;
import com.ws.alpha.util.QrGenUtil;
import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author mumu
 */
@Controller
public class WebSocketController {

    private static Logger logger = Logger.getLogger(WebSocketController.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(String message) {
        simpMessagingTemplate.convertAndSend("/wechat/message", message);
    }


    @RequestMapping("/")
    public String chat(){
        return "index";
    }

    /**
     * 获取uuid及二维码地址
     * @param request
     * @param response
     */
    @RequestMapping("/qrgen")
    public void showQrGen(HttpServletRequest request, HttpServletResponse response) {
        //生成UUID随机数
        UUID randomUUID = UUID.randomUUID();

        //通过应用获取共享的uuid集合
        Map<String, Message> map = (Map) request.getServletContext().getAttribute("UUID_MAP");
        if (map == null) {
            map = new HashMap<>();
            request.getServletContext().setAttribute("UUID_MAP", map);
        }

        //把uuid放入map中
        map.put(randomUUID.toString(), null);

        String url = "http://127.0.0.1:8080/login?cmd=loginByQrGen&uuid="+ randomUUID;

        //生成二维码图片
        try {
            ByteArrayOutputStream qrOut = QrGenUtil.createQrGen(url);
            String fileName = randomUUID + ".jpg";
            OutputStream outputStream = new FileOutputStream(new File(request.getServletContext().getRealPath("/temp"), fileName));
            outputStream.write(qrOut.toByteArray());
            outputStream.flush();
            outputStream.close();

            //返回页面json字符串，uuid用于轮询检查时所带的参数， img用于页面图片显示
            String jsonStr = "{\"uuid\":\"" + randomUUID + "\",\"img\":\"" + "/temp/"+fileName + "\"}";
            OutputStream outStream = response.getOutputStream();
            outStream.write(jsonStr.getBytes());
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping("/login")
    public void loginByQrGen(HttpServletRequest req, HttpServletResponse resp) {
        // 获取二维码链接中的uuid
        String uuid = req.getParameter("uuid");
        // 通过应用获取共享的uuid集合
        Map uuidMap = (Map) req.getServletContext().getAttribute("UUID_MAP");
        // 如果集合内没有这个uuid，则响应结果
        if (uuidMap == null || !uuidMap.containsKey(uuid)) {
            resp.getOutputStream().write("二维码不存在或已失效！".getBytes());
            return;
        }
        // 根据微信传来的code来获取用户的openID
        String code = req.getParameter("code");
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=YOUR_APPID"
                    + "&secret=YOUR_SECRTC"
                    + "&grant_type=authorization_code" + "&code=" + code;
            Gson gson = new Gson();
            Map map = gson.fromJson(HttpUtil.get(url, "utf-8"),
                    new TypeToken<Map>() {}.getType());
            Object openID = map.get("openid");
            if (openID != null && !"".equals(openID)) {
                // 通过openID获取user对象
                User user = dao.getUserByOpenId(openID.toString());
                if (user != null) {
                    // 如果查询到某个user拥有该openID，则设置到map集合内
                    uuidMap.put(uuid, user);
                    // 并返回手机端扫描结果
                    resp.getOutputStream().write("登陆成功！".getBytes());
                    return;
                }
            }
            // 如果没有openID参数，或查询不到openID对应的user对象，则移除该uuid，并响应结果
            uuidMap.remove(uuid);
            resp.getOutputStream().write("你还未绑定，请关注微信号并绑定账号！并使用微信客户端扫描！".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
