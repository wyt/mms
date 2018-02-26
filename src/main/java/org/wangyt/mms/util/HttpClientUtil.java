package org.wangyt.mms.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientUtil {

    /**
     * 执行post请求.
     * 
     * @param url 请求地址.
     * @param params 请求参数,封装成Map.
     * @param encode 编码.
     */
    public static String sendPostReq(String url, Map<String, String> params, String encode) {

      CloseableHttpClient httpclient = HttpClients.createDefault();
      CloseableHttpResponse response = null;
      InputStream instream = null;
      StringBuffer buff = new StringBuffer("");

      try {
        List<NameValuePair> paramspairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
          for (Map.Entry<String, String> entry : params.entrySet()) {
            paramspairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
          }
        }

        UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(paramspairs, encode);
        HttpPost httPost = new HttpPost(url);
        httPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httPost.setEntity(uefe);
        response = httpclient.execute(httPost);

        HttpEntity entity = response.getEntity();
        
        if (entity != null) {
          instream = entity.getContent();
          InputStreamReader isr = new InputStreamReader(instream, encode);
          char[] buffer = new char[1024];
          int readLength = 0;
          while ((readLength = isr.read(buffer)) > 0) {
              buff.append(new String(buffer, 0, readLength));
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      } finally {
        try {
          instream.close();
          response.close();
          httpclient.close();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
      return buff.toString();
    }
    
    private static final Log log = LogFactory.getLog(HttpClientUtil.class);
}
