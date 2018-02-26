package org.wangyt.mms.web.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.UserService;
import org.wangyt.mms.util.PropertiesUtils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 权限拦截器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-20 下午1:53:20
 * 
 * @version $Rev: 93 $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
public class CheckPrivilegeInterceptor extends HandlerInterceptorAdapter {

	private static final Log LOG = LogFactory.getLog(CheckPrivilegeInterceptor.class);
	private static List<String> excludePath = new ArrayList<String>();

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

		LOG.info(userAgent.getOperatingSystem() + ", " + userAgent.getBrowser().getName() + ", "
				+ userAgent.getBrowserVersion() + "," + userAgent.getBrowser().getBrowserType() + ", "
				+ userAgent.getBrowser().getRenderingEngine() + ", " + userAgent.getBrowser().getManufacturer());

		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		String basePath = request.getContextPath();
		String url = request.getRequestURI();
		LOG.info("拦截的url: " + url + ", " + "contextPath: " + basePath);

		/** OAuth client */
		if ("on".equals(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.sso.login"))) {
			if (currentUser == null) {
				OAuthClientRequest ocRequest = OAuthClientRequest
						.authorizationLocation(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.auth.location"))
						.setClientId(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.client.id"))
						.setRedirectURI(PropertiesUtils.getProperty("/config/oauth.sso.properties", "oauth.redirect.uri"))
						.setResponseType(OAuth.OAUTH_CODE).buildQueryMessage();
				response.sendRedirect(ocRequest.getLocationUri());
				return false;
			}
		}

		for (int i = 0; i < excludePath.size(); i++) {
			String excludePah = excludePath.get(i);
			boolean flag = StringUtils.isNotBlank(excludePath.get(i));
			if (flag && (url.indexOf(request.getContextPath() + excludePah) > -1)) {
				return true;
			}
		}

		if (currentUser == null) {
			Cookie[] cookies = request.getCookies();
			String c_login_name = null;
			String c_md5_password = null;

			for (int i = 0; cookies != null && i < cookies.length; i++) {
				if (cookies[i].getName().equals("c_login_name")) {
					c_login_name = cookies[i].getValue();
				} else if (cookies[i].getName().equals("c_password")) {
					c_md5_password = cookies[i].getValue();
				}
			}

			if (c_login_name != null && c_md5_password != null) {
				currentUser = userService.findUserByLoginNameAndPassword(c_login_name, c_md5_password);
				if (currentUser != null) {
					System.out.println("通过cookie登录成功!");
					session.setAttribute("currentUser", currentUser);
					// 通过cookie登录成功跳转到首页上去
					response.sendRedirect(request.getContextPath() + "/index.mvc");
					return false;
				}
			}

			// 没有通过cookie登录成功的请求
//			if ("/login/ui.mvc".equals(url) || "/login/action.mvc".equals(url)) {
			if ((basePath + "/login/ui.mvc").equals(url) || (basePath + "/login/action.mvc").equals(url)) {
				return true;
			} else {
				// 跳转到登录界面
				response.sendRedirect(request.getContextPath() + "/login/ui.mvc");
				return false;
			}
		} else {
			// 已经登录的用户再访问登录的action，直接给他转到首页
			if ("/login/ui.mvc".equals(url) || "/login/action.mvc".equals(url)) {
				response.sendRedirect(request.getContextPath() + "/index.mvc");
				return false;
			} else if ("/logout/action.mvc".equals(url)) // 注销的url
			{
				return true;
			} else {
				/**
				 * 此处应根据用户拥有的权限资源判断放行还是不放行,暂时未做(以后做)。
				 * 
				 * 也即是说用户不拥有的资源他看不到，但是还能访问。
				 */
				return true;
			}
		}
	}

	public List<String> getExcludePath() {
		return excludePath;
	}

	public void setExcludePath(List<String> excludePath) {
		this.excludePath = excludePath;
	}

}
