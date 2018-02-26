package org.wangyt.mms.webservice.helloworld;

import javax.jws.WebService;

@WebService(endpointInterface = "org.wangyt.mms.webservice.helloworld.HelloWebService", serviceName = "HelloWebService")
public class HelloWebServiceImpl implements HelloWebService {

  public String sayHi(String name) {
    return "Hello," + name;
  }

  @Override
  public String sayHello(String text) {
    System.out.println("调用sayHello方法");
    return "Hello," + text;
  }

}
