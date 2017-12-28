package com.ws.alpha.controller;

import com.ws.alpha.rabbit.RabbitSender;
import com.ws.alpha.util.Constant;
import com.ws.alpha.util.JsonObject;
import com.ws.alpha.util.QrGenUtil;
import com.ws.alpha.util.WechatConstant;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URLEncoder;


/**
 * @author laowang
 */
@Controller
public class WebSocketController {

    private final static Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private Map<String, String> uuidMap = new HashMap<>();

//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RabbitSender rabbitSender;

    @MessageMapping("/chat")
    public void handleChat(String message) {
        logger.info("Server-side bullet message forwarding");
        //将消息发送到消息队列中
        rabbitSender.send(message);

        //将消息转发到/wechat/message下面
//        simpMessagingTemplate.convertAndSend("/wechat/message", message);
    }

    @RequestMapping("/danmu")
    public String danmu(HttpServletRequest request, Model model) {
        logger.info("Scanned login barrage Hall");
        JSONObject userInfo = (JSONObject) request.getAttribute("list");
        if(userInfo != null) {
            String headUrl = userInfo.getString("headimgurl");
            logger.info(headUrl);
            logger.info(userInfo.getString("nickname"));
            model.addAttribute("headUrl", headUrl);
        }

        return "danmu";
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
        //获取页面传递过来的uuid
        String uuid = request.getParameter("uuid");
        //判断uuid是否为空，分别走不通路线
        if(uuid == null || "".equals(uuid)) {
            //生成UUID随机数
            UUID randomUUID = UUID.randomUUID();
            //把uuid放入map中
            uuidMap.put(randomUUID.toString(), Constant.OFFLINE);

//            String backUrl = "localhost?uuid="+ randomUUID;
            String backUrl = WechatConstant.URL+ randomUUID;
            //生成二维码图片
            try {
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConstant.APPID
                        + "&redirect_uri="+URLEncoder.encode(backUrl,"UTF-8")
                        + "&response_type=code"
                        + "&scope=snsapi_userinfo"
                        + "&state=STATE#wechat_redirect";
                ByteArrayOutputStream qrOut = QrGenUtil.createQrGen(url);
                String fileName = randomUUID + ".jpg";
//                OutputStream outputStream = new FileOutputStream(new File("E:\\GitWareHouse\\SpringBoot\\DanmuChat\\target\\classes\\static\\pic", fileName));
                OutputStream outputStream = new FileOutputStream(new File("/home/tomcat/apache-tomcat-8.5.23/webapps/wx/WEB-INF/classes/static/pic", fileName));
                outputStream.write(qrOut.toByteArray());
                outputStream.flush();
                outputStream.close();

                //返回页面json字符串，uuid用于轮询检查时所带的参数， img用于页面图片显示
                String jsonStr = "{\"uuid\":\"" + randomUUID + "\",\"img\":\"" + "/pic/"+fileName + "\",\"status\":\"" + Constant.OFFLINE + "\"}";
                OutputStream outStream = response.getOutputStream();
                outStream.write(jsonStr.getBytes());
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                logger.error("生成二维码失败: {}", e.getMessage());
            }
        }else if(Constant.OFFLINE.equals(uuidMap.get(uuid))){
            String fileName = uuid + ".jpg";
            //返回页面json字符串，uuid用于轮询检查时所带的参数， img用于页面图片显示
            String jsonStr = "{\"uuid\":\"" + uuid + "\",\"img\":\"" + "/pic/"+fileName + "\",\"status\":\"" + Constant.OFFLINE + "\"}";
            try {
                OutputStream outStream = response.getOutputStream();
                outStream.write(jsonStr.getBytes());
                outStream.flush();
                outStream.close();
            }catch (IOException e) {
                logger.error("请求最新的二维码失败：{}", e.getMessage());
            }
        }

    }

    @RequestMapping("/login")
    public void login(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatConstant.APPID
                + "&secret=" + WechatConstant.SECRET
                + "&code=" + code
                + "&grant_type=authorization_code";
        try {
            JSONObject jsonObject = JsonObject.doGetJson(url);
            String openid=jsonObject.getString("openid");
            String token=jsonObject.getString("access_token");
            logger.info(openid + "," + token);
            String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+token
                    + "&openid="+openid
                    + "&lang=zh_CN";
            JSONObject userInfo = JsonObject.doGetJson(infoUrl);
            logger.info(userInfo.toString());
            req.setAttribute("list", userInfo);
            req.getRequestDispatcher("/danmu").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("用户登录出现失败: {}", e.getMessage());
        }

    }

}
