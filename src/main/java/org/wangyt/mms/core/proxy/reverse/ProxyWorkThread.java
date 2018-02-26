package org.wangyt.mms.core.proxy.reverse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.pool.BasicPoolEntry;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;

@SuppressWarnings("all")
class ProxyWorkThread extends Thread {

	private HttpHost target;
	private BasicPoolEntry entry;
	private HttpProcessor httpproc;
	private ServletRequest servletReq;
	private ServletResponse servletResp;
	private static final HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

	ProxyWorkThread(final HttpHost target, final BasicPoolEntry entry, ServletRequest servletReq,
			ServletResponse servletResp) {
		super();
		this.target = target;
		this.entry = entry;
		this.servletReq = servletReq;
		this.servletResp = servletResp;
		this.httpproc = HttpProcessorBuilder.create()//
				.add(new RequestContent()) //
				.add(new RequestTargetHost()) //
				.add(new RequestConnControl()) //
				.add(new RequestUserAgent("WINC REVERSE PROXY/1.1")) //
				.add(new RequestExpectContinue(true)) //
				.build();
	}

	@Override
	public void run() {
		ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
		HttpCoreContext coreContext = HttpCoreContext.create();
		coreContext.setTargetHost(this.target);
		boolean reusable = false;

		try {
			HttpEntityEnclosingRequest request = reqRefact(servletReq);
			httpexecutor.preProcess(request, httpproc, coreContext);
			HttpResponse response = httpexecutor.execute(request, entry.getConnection(), coreContext);
			httpexecutor.postProcess(response, httpproc, coreContext);
			respRefact(servletResp, response);
			
			reusable = connStrategy.keepAlive(response, coreContext);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reusable) {
				System.out.println("Connection kept alive...");
			}
			HttpConnPool.release(entry, reusable);
		}
	}

	private HttpEntityEnclosingRequest reqRefact(ServletRequest req) throws IOException {

		HttpServletRequest oriReq = (HttpServletRequest) req;
//		HttpEntityEnclosingRequest proxyReq = new BasicHttpEntityEnclosingRequest(oriReq.getMethod(), oriReq.getRequestURI());
		HttpEntityEnclosingRequest proxyReq = null;

		Enumeration<String> headerNames = oriReq.getHeaderNames();
		// 将Servlet请求处理成代理请求.
		while (headerNames.hasMoreElements()) {
			proxyReq.addHeader(headerNames.nextElement(), oriReq.getHeader(headerNames.nextElement()));
		}

		// Remove hop-by-hop headers.
		proxyReq.removeHeaders(HTTP.CONTENT_LEN);
		proxyReq.removeHeaders(HTTP.TRANSFER_ENCODING);
		proxyReq.removeHeaders(HTTP.CONN_DIRECTIVE);
		proxyReq.removeHeaders("Keep-Alive");
		proxyReq.removeHeaders("Proxy-Authenticate");
		proxyReq.removeHeaders("TE");
		proxyReq.removeHeaders("Trailers");
		proxyReq.removeHeaders("Upgrade");
		proxyReq.setHeader("Host", target.getHostName() + ":" + target.getPort());
		proxyReq.setEntity(new InputStreamEntity(oriReq.getInputStream(), getContentLength(oriReq)));
		return proxyReq;
	}

	private void respRefact(ServletResponse servletResponse, HttpResponse proxyResponse) throws IOException {

		HttpServletResponse originalResponse = (HttpServletResponse) servletResponse;

		for (Header header : proxyResponse.getAllHeaders()) {
			originalResponse.addHeader(header.getName(), header.getValue());
		}

		HttpEntity entity = proxyResponse.getEntity();
		if (entity != null) {
			OutputStream servletOutputStream = originalResponse.getOutputStream();
			entity.writeTo(servletOutputStream);
		}
	}

	private long getContentLength(HttpServletRequest request) {
		String contentLengthHeader = request.getHeader("Content-Length");
		if (contentLengthHeader != null) {
			return Long.parseLong(contentLengthHeader);
		}
		return -1L;
	}

}
