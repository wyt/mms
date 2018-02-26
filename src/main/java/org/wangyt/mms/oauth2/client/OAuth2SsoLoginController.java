package org.wangyt.mms.oauth2.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.util.PropertiesUtils;

@SuppressWarnings("all")
@Controller("oAuth2SsoLoginController")
public class OAuth2SsoLoginController {
    
    private static final String clientId = "c1ebe466-1cdc-4bd3-ab69-77c3561b9dee";
    private static final String clientSecret = "";
    
    private static final String authorizationLocation = "http://localhost/mms/open/api/authorize";
    private static final String redirectUri = "http://localhost/mms/sso_login/action";

    // TODO
    @RequestMapping(value = "ssologin/action")
    public String login(HttpSession session, HttpServletRequest request,
                        HttpServletResponse response, User user, String code) throws OAuthSystemException, OAuthProblemException {
        
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        
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
        
        System.out.println(accessTokenRequest.getLocationUri());
        
        OAuthAccessTokenResponse oAuthResponse =   
                oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
//                oAuthClient.accessToken(accessTokenRequest, OAuthJSONAccessTokenResponse.class);
        
//        accessTokenRequest.setHeader(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);
        
        String accessToken = oAuthResponse.getAccessToken();  
        Long expiresIn = oAuthResponse.getExpiresIn();  
        
        System.out.println("accessToken: " + accessToken + ",expiresIn " + expiresIn);
        
        return null;
    }
}
