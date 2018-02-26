package org.wangyt.mms.openapi.controller.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wangyt.mms.openapi.controller.IOpenApiTest;
import org.wangyt.mms.util.CaptchaUtils;
import org.wangyt.mms.util.security.Coder;

/**
 * http://localhost/mms/open/api/captcha
 * 
 * http://192.168.9.30/mms/open/api/captcha
 * 
 * http://localhost/mms/open/api/captchaBase64
 * 
 * @author WANG YONG TAO
 *
 */
@SuppressWarnings("all")
@Controller("captchaController")
public class OpenApiTestController implements IOpenApiTest {

	/**
	 * 将图片以流的形式回写.
	 * 
	 * @param request
	 * @param httpResponse
	 * @throws IOException 
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		OutputStream out = response.getOutputStream();
		String captchaCode = CaptchaUtils.randerCaptcha(out);
		request.getSession().setAttribute("CAPTCHA_CODE", captchaCode);
		out.flush();
		out.close();
	}

	/**
	 * 将图片以Base64的形式返回.
	 * 
	 * @param request
	 * @param httpResponse
	 * @throws Exception 
	 */
	@RequestMapping(value = "/captchaBase64", method = RequestMethod.GET)
	public void captchaBase64(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String code = CaptchaUtils.randerCaptcha(out);
		String captcha = Coder.encryptBASE64(out.toByteArray());
		System.out.println(captcha);
	}
	
	/**
	 * 获取系统信息.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/systemProps", method = RequestMethod.GET)
	public void getSystemProperties(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Properties properties = System.getProperties();
		Set set = properties.entrySet();
		Iterator it = set.iterator();
		StringBuffer buffer = new StringBuffer();

		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			buffer.append(key + "=" + value + "\r\n");
		}

		PrintWriter pw = response.getWriter();
		pw.write(buffer.toString());
		pw.flush();
	}

}
