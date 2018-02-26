package org.wangyt.mms.webservice.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Element;

@SuppressWarnings("all")
public class ReportWsInInterceptor extends AbstractPhaseInterceptor {

  public ReportWsInInterceptor() {
    super(Phase.RECEIVE);
  }

  public void handleMessage(Message message) {
    // message.put(Message.ENCODING, "UTF-8");
    InputStream is = message.getContent(InputStream.class);

    if (is != null) {
      CachedOutputStream bos = new CachedOutputStream();
      try {
        IOUtils.copy(is, bos);
        String soapMessage = new String(bos.getBytes());
        System.out.println("-------------------------------------------");
        System.out.println("incoming message is " + soapMessage);
        System.out.println("-------------------------------------------");
        bos.flush();
        message.setContent(InputStream.class, is);

        is.close();
        
        InputStream inputStream = null;
        if(!"".equals(soapMessage)){
//          inputStream = new ByteArrayInputStream(changeContent(soapMessage).getBytes());
           inputStream = new ByteArrayInputStream(changeCont(soapMessage).getBytes());
        }
        
        message.setContent(InputStream.class, inputStream);
        bos.close();
      } catch (Exception ioe) {
        ioe.printStackTrace();
      }
    }
  }

  private String changeContent(String soapMessage) {

    soapMessage = soapMessage.replaceAll("getReportUsedToClient", "getReportUsedToServer");

    // TODO 动态修改传入的参数
    soapMessage =
        soapMessage.replaceAll("<report_id>000000</report_id><report_name>Charles</report_name>",
            "<args>Charles</args>");

    System.out.println("After change incoming message is " + soapMessage);

    return soapMessage;
  }

  private String changeCont(String soapMessage) throws Exception {

    /** 先替换掉方法名 **/
    soapMessage = soapMessage.replaceAll("getReportUsedToClient", "getReportUsedToServer");

    /** String类型的xml转换成Doc **/
    Document doc = ReportWsInInterceptor.StrXmlToDoc(soapMessage);

    /** 获取指定命名空间的元素 **/
    NodeList nls =
        doc.getElementsByTagNameNS("http://winc_report.webservice.mms.yongtao.wang/",
            "getReportUsedToServer");

    /** 获取 <getReportUsedToServer>元素 **/
    Element gRUTS = (Element) nls.item(0);

    /** <getReportUsedToServer>元素子节点,注意回车换行节点 **/
    NodeList args = gRUTS.getChildNodes();

    /** 构建替换内容 **/
    StringBuffer sbuf = new StringBuffer();

    for (int i = 0; i < args.getLength(); i++) {
      sbuf.append(args.item(i).getTextContent() + ",");
    }

    if (sbuf.length() > 0) {
      sbuf.deleteCharAt(sbuf.length() - 1);
    }

    /** 删除<getReportUsedToServer>标签中的内容 */
    List<Node> my_nl = new ArrayList<Node>();

    for (int i = 0; i < args.getLength(); i++) {
      Node node = args.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        my_nl.add(node);
      }
    }

    for (Node n : my_nl) {
      n.getParentNode().removeChild(n);
    }

    /** 添加新元素 **/
    Element args_element = doc.createElement("args");
    args_element.setTextContent(sbuf.toString());
    gRUTS.appendChild(args_element);

    String alterSOAP = toFormatedXML(doc);
    System.out.println("alterSOAP: "+alterSOAP);
    
    return alterSOAP;
  }

  /**
   * 把Document对象转为String输出. 主要使用到StringWriter,构建到String buffer中.
   * 
   * @param object
   * @return
   * @throws Exception
   */
  public static String toFormatedXML(Document object) throws Exception {

    Document doc = (Document) object;

    TransformerFactory transFactory = TransformerFactory.newInstance();
    Transformer transFormer = transFactory.newTransformer();
    transFormer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

    DOMSource domSource = new DOMSource(doc);

    StringWriter sw = new StringWriter();
    StreamResult xmlResult = new StreamResult(sw);

    transFormer.transform(domSource, xmlResult);

    return sw.toString();

  }

  /**
   * 把String 对象转换成 doc输出.
   */
  public static Document StrXmlToDoc(String xmlContent) throws Exception {

    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    docBuilderFactory.setNamespaceAware(true);
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(new InputSource(new StringReader(xmlContent)));

    return doc;
  }

}
