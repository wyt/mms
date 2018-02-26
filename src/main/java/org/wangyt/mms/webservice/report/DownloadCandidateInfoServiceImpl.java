package org.wangyt.mms.webservice.report;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebService;

/**
 * 服务实现类，提供下载面试人信息的类
 * 
 * @author Administrator
 *
 */
@WebService(endpointInterface = "org.wangyt.mms.webservice.report.DownloadCandidateInfoService")
public class DownloadCandidateInfoServiceImpl implements DownloadCandidateInfoService {

  private String resumeRepositoryPath = "D:/wangyt/CACHE/";

  /**
   * 去人才库去下载指定的面试人信息，然后返回给客户端
   */
  public CandidateInfo downloadCandidateInfo(String name) {

    CandidateInfo ci = new CandidateInfo();
    if (name.trim().equals("Charles")) {
      ci.setName("Charles");
      ci.setJob("System Architect");
      ci.setResume(new DataHandler(new FileDataSource(new File(resumeRepositoryPath
          + "7396a53c-571c-4a85-bf23-d409689868f2.zip"))));
      // ci.setResume(new DataHandler(new FileDataSource(new File(resumeRepositoryPath +
      // "charles_cv.doc"))));
    } else {
      ci.setName("Kevin");
      ci.setJob("Senior Developer");
      ci.setResume(new DataHandler(new FileDataSource(new File(resumeRepositoryPath
          + "kevin_cv.doc"))));
    }

    return ci;
  }

}
