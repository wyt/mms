package org.wangyt.mms.webservice.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

@SuppressWarnings("all")
public class ReportWsOutInterceptor extends AbstractPhaseInterceptor {

  public ReportWsOutInterceptor() {
    super(Phase.PRE_STREAM);
  }

  public void handleMessage(Message message) {
    try {
      // message.put(Message.ENCODING, "UTF-8");
      OutputStream os = message.getContent(OutputStream.class);
      CachedStream cs = new CachedStream();
      message.setContent(OutputStream.class, cs);
      message.getInterceptorChain().doIntercept(message);
      CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);

      InputStream in = csnew.getInputStream();

      // 编码一定要是ISO-8859-1
      String xml = IOUtils.toString(in, "ISO-8859-1");
      System.out.println("-------------------------------------------");
      System.out.println("outgoing message is " + xml);
      System.out.println("-------------------------------------------");

      // 这里对xml做处理，处理完后同理，写回流中
      IOUtils.copy(new ByteArrayInputStream(changeContent(xml).getBytes("ISO-8859-1")), os);

      cs.close();
      os.flush();

      message.setContent(OutputStream.class, os);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private String changeContent(String soapMessage) {
//    soapMessage = soapMessage.replaceAll("getReportDataResponse", "sayHelloResponse");
     soapMessage = soapMessage.replaceAll("getReportUsedToServerResponse", "getReportUsedToClientResponse");
    System.out.println("-------------------------------------------");
    System.out.println("After outgoing message is " + soapMessage);
    System.out.println("-------------------------------------------");
    return soapMessage;
  }

  private class CachedStream extends CachedOutputStream {

    public CachedStream() {
      super();
    }

    protected void doFlush() throws IOException {
      currentStream.flush();
    }

    protected void doClose() throws IOException {}
    protected void onWrite() throws IOException {}

  }

}
