package org.wangyt.mms.webservice.winc_report;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * 定义报表Vo对象.
 * 
 * @author wangyongtao
 *
 */
@XmlType(name = "wincReportInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class WincReportInfo {

  /** 报表ID. */
  private String reportId;
  /** 报表名称. */
  private String reportName;

  @XmlMimeType("application/octet-stream")
  private DataHandler binContent;

  public String getReportId() {
    return reportId;
  }

  public void setReportId(String reportId) {
    this.reportId = reportId;
  }

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public DataHandler getBinContent() {
    return binContent;
  }

  public void setBinContent(DataHandler binContent) {
    this.binContent = binContent;
  }

}
