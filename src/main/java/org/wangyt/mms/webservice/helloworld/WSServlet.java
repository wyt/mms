package org.wangyt.mms.webservice.helloworld;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http://127.0.0.1/mms/wsServlet?wsdl=winc
 * 
 * Servlet implementation class WSServlet
 */
public class WSServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public WSServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    doPost(request, response);

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String what = request.getParameter("wsdl");

    if ("all".equals(what)) {
      request.getRequestDispatcher("/wsdl/reportWs/reportWs.all.xml").forward(request, response);
    } else if ("getReportData".equals(what)) {
      request.getRequestDispatcher("/wsdl/reportWs/reportWs.getReportData.xml").forward(request,
          response);
    } else if ("sayHello".equals(what)) {
      request.getRequestDispatcher("/wsdl/reportWs/reportWs.sayHello.xml").forward(request,
          response);
    } else if ("wincReport".equals(what)) {
      request.getRequestDispatcher("/wsdl/reportWs/wincReportWs.wsdl.xml").forward(request,
          response);
    }
  }
  
}
