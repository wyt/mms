package org.wangyt.mms.core.proxy.reverse;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;

/**
 * 拦截需要反向代理的请求.
 * 
 * @author WANG YONG TAO
 *
 */
@SuppressWarnings("all")
public class ReverseProxyFilter implements Filter {

	public static final String TARGET_HOSTNAME = "www.baidu.com";
	public static final int TARGET_PORT = 80;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		request.getMethod();

		/** 反向代理服务器 DMS <-> 目标服务器 STERM */
		Thread pwt = new ProxyWorkThread(new HttpHost(TARGET_HOSTNAME, TARGET_PORT),
				HttpConnPool.getEntry(new HttpHost(TARGET_HOSTNAME, TARGET_PORT), null), req, resp);
		pwt.setDaemon(false);
		pwt.start();
	}

	@Override
	public void destroy() {
	}

}
