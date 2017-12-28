package com.ws.alpha.util;

import java.io.IOException;

import com.ws.alpha.entiy.UserInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonObject {

    private final static Logger logger = LoggerFactory.getLogger(JsonObject.class);

    public static JSONObject doGetJson(String url) {
        JSONObject jsonObject = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet= new HttpGet(url);
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity enyity=response.getEntity();
            if (enyity != null) {
                String result=EntityUtils.toString(enyity,"UTF-8");
                logger.info("JSONObject: {}",result);
                jsonObject=JSONObject.fromObject(result);
            }
            httpGet.releaseConnection();
        } catch (IOException e) {
            logger.error("方法doGetJson失败：{}", e.getMessage());
        }

        return jsonObject;
    }

}
