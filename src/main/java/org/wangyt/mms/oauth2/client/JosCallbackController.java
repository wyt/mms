package org.wangyt.mms.oauth2.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.util.HttpClientUtil;
import org.wangyt.mms.util.PropertiesUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 作为OAuth2 client拦截到到OAuth Server返回的地址: http://localhost/mms/jos/callback?code=xxxxxx
 * 
 * @author WANG YONG TAO
 *
 */
@Controller("josCallbackController")
public class JosCallbackController {
    
    // TODO
    @RequestMapping(value = "jos/callback")
    public String josCallback(HttpSession session, HttpServletRequest request,
                        HttpServletResponse response, User user, String code) throws OAuthSystemException, OAuthProblemException {
        
        /**
         * http://localhost/mms/open/api/accessToken?
         * client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&
         * client_secret=d8346ea2-6017-43ed-ad68-19c0f971738b&
         * grant_type=authorization_code&
         * code=96475a26d5dc33d806b91c8eef0de583&
         * redirect_uri=http://localhost:9080/chapter17-client/oauth2-login 
         */
        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.auth.token.location"))
                .setClientId(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.client.id"))
                .setClientSecret(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.client.secret"))
                .setCode(code).setRedirectURI(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.redirect.uri"))
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .buildQueryMessage();
        
        String url = accessTokenRequest.getLocationUri();
        String json = HttpClientUtil.sendPostReq(url, null, "GBK"); System.out.println(json);
        
        JSONObject JD =  JSON.parseObject(json);
        System.out.println("access_token" + ", " + JD.get("access_token"));
        
        return null;
    }
}
