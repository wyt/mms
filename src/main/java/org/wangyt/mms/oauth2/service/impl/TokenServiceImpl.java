package org.wangyt.mms.oauth2.service.impl;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Service;
import org.wangyt.mms.oauth2.service.TokenService;

/**
 * @author WANG YONG TAO
 *
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

  @Override
  public boolean validateClientId(String clientId) {
    return true;
  }

  @Override
  public boolean validateClientSecret(String clientId, String clientSecret) {
    return true;
  }

  @Override
  public String genarateAccessToken() throws OAuthSystemException {

    OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    return oauthIssuerImpl.accessToken();
  }

  @Override
  public String getGrantType() {
    return GrantType.CLIENT_CREDENTIALS.toString();
  }

}
