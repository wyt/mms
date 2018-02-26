package org.wangyt.mms.webservice.helloworld;

import javax.jws.WebService;

@WebService(endpointInterface = "org.wangyt.mms.webservice.helloworld.ReportWs", serviceName = "ReportWs")
public class ReportWsImpl implements ReportWs {

  @Override
  public String sayHello(String text,String text01) {
    System.out.println("调用sayHello方法");
    return "Hello," + text;
  }

  public String getReportData(String text) {
    return "Haha, " + text + "," + "you got it!";
  }

}
