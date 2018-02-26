package org.wangyt.mms.webservice.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

@SuppressWarnings("all")
public class WsAuthHandler implements CallbackHandler {

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    
    for(Callback c: callbacks){
      System.out.println(c.toString());
    }
    
    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
    
    String identifier = pc.getIdentifier();
    String password = pc.getPassword();
    
    if ("admin".equals(identifier) && "1".equals(password)) {
      System.out.println("验证通过！");
      System.out.println("identifier = " + identifier);
      System.out.println("password = " + password);
    } else {
      throw new SecurityException("Authentication failed!");
    }
  }
}
