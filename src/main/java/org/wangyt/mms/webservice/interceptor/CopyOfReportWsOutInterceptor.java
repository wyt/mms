package org.wangyt.mms.webservice.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("all")
public class CopyOfReportWsOutInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

  private SAAJInInterceptor saa = new SAAJInInterceptor();

  public CopyOfReportWsOutInterceptor() {
//    super(Phase.PRE_PROTOCOL);
    super(Phase.SEND);
    getAfter().add(SAAJInInterceptor.class.getName());
  }

  @Override
  public void handleMessage(SoapMessage message) throws Fault {
    message.put(Message.ENCODING, "UTF-8");
    InputStream is = message.getContent(InputStream.class);
    
    if (is != null) {
      CachedOutputStream bos = new CachedOutputStream();
      try {
        
        IOUtils.copy(is, bos);
        String soapMessage = new String(bos.getBytes());
        System.out.println("-------------------------------------------");
        System.out.println("outgoing message is " + soapMessage);
        System.out.println("-------------------------------------------");
        bos.flush();
        message.setContent(InputStream.class, is);

        is.close();
        InputStream inputStream = new ByteArrayInputStream(changeName(soapMessage).getBytes());
        message.setContent(InputStream.class, inputStream);
        bos.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }
  
  private String changeName(String soapMessage) {
    soapMessage = soapMessage.replaceAll("sayHello", "getReportData");
    System.out.println("After change incoming message is " + soapMessage);
    return soapMessage;
  }
  
  public void handleMessage2(SoapMessage message) throws Fault {

    SOAPMessage saaj = message.getContent(SOAPMessage.class);

    if (saaj == null) {
      saa.handleMessage(message);
      saaj = message.getContent(SOAPMessage.class);
    }

    try {
      if (saaj != null) {
        trace("The sent SOAP message:", saaj);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void trace(String s, SOAPMessage m) {
    System.out.println("\n");
    System.out.println(s);
    try {
      m.writeTo(System.out);
    } catch (SOAPException e) {
      System.err.println(e);
    } catch (IOException e) {
      System.err.println(e);
    }
  }

}
