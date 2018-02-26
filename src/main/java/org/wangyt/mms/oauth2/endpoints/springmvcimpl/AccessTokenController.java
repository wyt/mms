package org.wangyt.mms.oauth2.endpoints.springmvcimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wangyt.mms.oauth2.common.Constants;
import org.wangyt.mms.oauth2.service.OAuthService;

/**
 * <a>
 * http://localhost/mms/open/api/accessToken?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&client_secret=d8346ea2-6017-43ed-ad68-19c0f971738b&grant_type=authorization_code&code=96475a26d5dc33d806b91c8eef0de583&redirect_uri=http://localhost:9080/chapter17-client/oauth2-login
 * </a>
 * @author WANG YONG TAO
 *
 */
@SuppressWarnings("all")
@Controller("accessTokenController")
public class AccessTokenController {
    @Autowired
    private OAuthService oAuthService;
    
    @RequestMapping(value = "/accessToken", method = RequestMethod.POST)
    public void token(HttpServletRequest request, HttpServletResponse httpResponse)
            throws URISyntaxException, OAuthSystemException, IOException {
        try {
            // 构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

            // 检查提交的客户端id是否正确
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                OAuthResponse response =
                        OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                output(httpResponse, response);
            }

            // 检查客户端安全KEY是否正确
            if (!oAuthService.checkClientSecret(oauthRequest.getClientSecret())) {
                OAuthResponse response =
                        OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                output(httpResponse, response);
            }

            String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
            // 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
                    .equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (!oAuthService.checkAuthCode(authCode)) {
                    OAuthResponse response =
                            OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
                                    .setErrorDescription("错误的授权码").buildJSONMessage();
                    output(httpResponse, response);
                }
            }

            // 生成Access Token
            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oauthIssuerImpl.accessToken();
            oAuthService.addAccessToken(accessToken, oAuthService.getUsernameByAuthCode(authCode));

            // 生成OAuth响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(String.valueOf(oAuthService.getExpireIn())).buildJSONMessage();
            output(httpResponse, response);
        } catch (OAuthProblemException e) {
            e.printStackTrace();
            // 构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .error(e).buildJSONMessage();
            output(httpResponse, res);
        }
    }
    
    private void output(HttpServletResponse httpResponse, OAuthResponse oAuthResponse)
            throws IOException {
        httpResponse.setStatus(oAuthResponse.getResponseStatus());
        PrintWriter pw = httpResponse.getWriter();
        pw.print(oAuthResponse.getBody());
        pw.flush();
        pw.close();
    }

}
