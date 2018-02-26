package org.wangyt.mms.oauth2.service;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 
 * @author WANG YONG TAO
 *
 */
public interface TokenService {

  /**
   * validate the client_id.
   * 
   * @param clientId
   * @return
   */
  boolean validateClientId(String clientId);

  /**
   * validate client_secret.
   * 
   * @param clientId
   * @param clientSecret
   * @return
   */
  boolean validateClientSecret(String clientId, String clientSecret);


  /**
   * genarate one access token,and persist it.
   * 
   * @return
   * @throws OAuthSystemException 
   */
  String genarateAccessToken() throws OAuthSystemException;

  /**
   * get the grant type.
   * 
   * @return
   */
  String getGrantType();

}
