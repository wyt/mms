package org.wangyt.mms.webservice.report;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * 我们这里定义一个VO，关于面试人的信息，比如姓名，职位，简历（是一个word文档）
 * 
 * @author Administrator
 *
 */
@XmlType(name = "candidateInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CandidateInfo {

  private String name; // 面试人姓名
  private String job; // 面试人要应聘的职位

  @XmlMimeType("application/octet-stream")
  private DataHandler resume; // 面试人的简历，这是一个word文档

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public DataHandler getResume() {
    return resume;
  }

  public void setResume(DataHandler resume) {
    this.resume = resume;
  }
  
}
