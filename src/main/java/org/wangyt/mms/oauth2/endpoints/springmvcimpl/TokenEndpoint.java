package org.wangyt.mms.oauth2.endpoints.springmvcimpl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wangyt.mms.oauth2.service.TokenService;

/**
 * <a>/auth/oauth2/token</a>
 * 
 * @author WANG YONG TAO
 *
 */
@SuppressWarnings("all")
@RequestMapping("/oauth2")
@Controller("tokenEndpoint")
public class TokenEndpoint {

  public static final String INVALID_CLIENT_DESCRIPTION =
      "Client authentication failed (e.g., unknown client, no client authentication included, or unsupported authentication method).";

  @Autowired
  private TokenService tokenService;

  @RequestMapping(value = "/token", method = RequestMethod.POST)
  public void authorize(HttpServletRequest request, HttpServletResponse response)
      throws OAuthSystemException {

    OAuthTokenRequest oauthRequest = null;

    try {
      oauthRequest = new OAuthTokenRequest(request);

      // check if clientId is valid
      if (!tokenService.validateClientId(oauthRequest.getClientId())) {
        OAuthResponse oAuthResponse =
            OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                .setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                .buildJSONMessage();

        outputOAuthResp(response, oAuthResponse);
      }

      // check if client_secret is valid
      if (!tokenService.validateClientSecret(oauthRequest.getClientId(),
          oauthRequest.getClientSecret())) {
        OAuthResponse oAuthResponse =
            OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                .setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                .buildJSONMessage();

        outputOAuthResp(response, oAuthResponse);
      }

      OAuthResponse oAuthResponse =
          OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
              .setAccessToken(tokenService.genarateAccessToken())
              .setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString())
              .setExpiresIn("3600")
              .buildJSONMessage();

      outputOAuthResp(response, oAuthResponse);

    } catch (OAuthProblemException e) {
      OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
          .buildJSONMessage();
      outputOAuthResp(response, oAuthResponse);
    }
    
  }

  /**
   * Output the oAuth response.
   * 
   * @param response
   * @param oAuthRes
   */
  private void outputOAuthResp(HttpServletResponse response, OAuthResponse oAuthRes) {
    response.setStatus(oAuthRes.getResponseStatus());
    PrintWriter pw = null;
    try {
      pw = response.getWriter();
      pw.print(oAuthRes.getBody());
      pw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      pw.close();
    }
  }

}
