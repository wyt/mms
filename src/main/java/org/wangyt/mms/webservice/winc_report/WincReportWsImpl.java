package org.wangyt.mms.webservice.winc_report;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebService;

@WebService(endpointInterface = "org.wangyt.mms.webservice.winc_report.WincReportWs", serviceName = "WincReportWs")
public class WincReportWsImpl implements WincReportWs {

  private String filePath = "D:/wangyt/CACHE/";

//  @Override
//  public WincReportInfo getReportUsedToClient(String reportId, String reportName) {
//    return null;
//  }

  @Override
  public WincReportInfo getReportUsedToServer(String args) {

    System.out.println("args: " + args);
    WincReportInfo wri = new WincReportInfo();

    wri.setReportId("010010101");
    wri.setReportName("myreport Name.");
    wri.setBinContent(new DataHandler(new FileDataSource(new File(filePath
        + "7396a53c-571c-4a85-bf23-d409689868f2.zip"))));

    return wri;
  }

}
