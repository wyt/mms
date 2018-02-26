package org.wangyt.mms.webservice.helloworld;

import javax.jws.WebService;

@WebService
public interface ReportWs {

  String sayHello(String text,String text01);

  String getReportData(String text);

}
