package org.wangyt.mms.webservice.winc_report;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * http://127.0.0.1/mms/webservice/wincReportWs?wsdl
 * 
 * @author wangyongtao
 *
 */
@WebService
public interface WincReportWs {

  /**
   * 供给客户端调用的方法，正式发布的时候这个方法不需要存在，而是需要在wsdl中手动编写生成，此处编写的目的是为了自动生成wsdl.
   * 
   * @param reportId
   * @param reportName
   * @return
   */
//  WincReportInfo getReportUsedToClient(@WebParam(name = "report_id") String reportId,
//      @WebParam(name = "report_name") String reportName);

  /**
   * 真正的映射后被调用的方法.
   * 
   * @param args
   * @return
   */
  WincReportInfo getReportUsedToServer(@WebParam(name = "args") String args);

}
