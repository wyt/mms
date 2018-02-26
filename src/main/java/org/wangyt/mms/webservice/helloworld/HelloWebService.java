package org.wangyt.mms.webservice.helloworld;

import javax.jws.WebService;

@WebService
public interface HelloWebService {

  String sayHi(String text);
  
  String sayHello(String text);

}
