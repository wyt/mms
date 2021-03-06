package org.wangyt.mms.oauth2.endpoints.springmvcimpl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.oauth2.common.Constants;
import org.wangyt.mms.oauth2.service.ClientService;
import org.wangyt.mms.oauth2.service.OAuthService;

/**
 * <a>http://localhost/mms/open/api/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:9080/chapter17-client/oauth2-login</a>
 * 
 * @author WANG YONG TAO
 *
 */
@SuppressWarnings("all")
@Controller("authorizeController")
public class AuthorizeController {

    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/authorize")
    public Object authorize(Model model, HttpSession session, HttpServletRequest request)
            throws URISyntaxException, OAuthSystemException {

        try {
            // 构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            // 检查传入的客户端id是否正确
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                OAuthResponse response =
                        OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                return new ResponseEntity(response.getBody(),
                        HttpStatus.valueOf(response.getResponseStatus()));
            }
            
            User currentUser = (User) session.getAttribute("currentUser");
            // 如果用户没有登录，跳转到登陆页面
            if(currentUser == null) {
                String requestURL = request.getRequestURL().toString();
                String queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";
                session.setAttribute("before_login_url", requestURL + queryString);
                
                return "redirect:login/ui";
            }

            String username = currentUser.getLoginName();
            
            // 生成授权码
            String authorizationCode = null;
            // responseType目前仅支持CODE，另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                oAuthService.addAuthCode(authorizationCode, username);
            }

            // 进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            // 设置授权码
            builder.setCode(authorizationCode);
            // 得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            // 构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            // 根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            // 出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                // 告诉客户端没有传入redirectUri直接报错
                return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }
            // 返回错误消息（如?error=）
            final OAuthResponse response =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
                            .location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }

}
