package org.wangyt.mms.webservice.report;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Administrator
 *
 */
@WebService
public interface DownloadCandidateInfoService {

  /**
   * 下载面试人的信息
   */
  CandidateInfo downloadCandidateInfo(@WebParam(name = "name") String name);
  
}
