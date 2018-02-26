package org.wangyt.mms.webservice.interceptor;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 方法路由拦截器,用于修改报文中原始方法路由到其他方法.
 * 
 * @author wangyongtao
 *
 */
public class MethodRouteInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

  private SAAJInInterceptor saa = new SAAJInInterceptor();

  public MethodRouteInterceptor() {
    super(Phase.PRE_PROTOCOL);
    getAfter().add(SAAJInInterceptor.class.getName());
  }

  @Override
  public void handleMessage(SoapMessage message) throws Fault {
    

    SOAPMessage saaj = message.getContent(SOAPMessage.class);

    if (saaj == null) {
      saa.handleMessage(message);
      saaj = message.getContent(SOAPMessage.class);
    }

    try {

      if (saaj != null) {
        SOAPBody body = saaj.getSOAPBody();
        NodeList nl = body.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
          Node n = nl.item(i);
          body.removeChild(n);
          System.out.println(n.toString());
        }

        // SOAPElement method = body.addChildElement("getReportData", "hel");
        SOAPElement method =
            body.addChildElement("getReportData", "hel",
                "http://helloworld.webservice.mms.yongtao.wang/");
        method.addChildElement("arg0").addTextNode("?");

        // saaj.saveChanges();
        // message.setContent(SOAPMessage.class, saaj);
      }


    } catch (SOAPException e) {
      e.printStackTrace();
    }

    // Document doc = saaj.getSOAPPart(); // This actually returns a
    // SOAPPart instance but it does
    // implement the w3c Document interface

  }
}
