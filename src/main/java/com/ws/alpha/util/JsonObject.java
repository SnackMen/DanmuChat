package com.ws.alpha.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class JsonObject {

    public static JSONObject doGetJson(String url) throws IOException{
        JSONObject jsonObject = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet= new HttpGet(url);
        HttpResponse response=httpClient.execute(httpGet);
        HttpEntity enyity=response.getEntity();
        if (enyity != null) {
            String result=EntityUtils.toString(enyity,"UTF-8");
            jsonObject=JSONObject.fromObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

}
