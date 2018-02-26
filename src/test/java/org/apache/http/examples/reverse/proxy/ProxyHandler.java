package org.apache.http.examples.reverse.proxy;

import java.io.IOException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.HttpRequestHandler;

/**
 * 代理请求捕获处理器.
 * 
 * @author WANG YONG TAO
 *
 */
class ProxyHandler implements HttpRequestHandler {

	private final HttpHost target;
	private final HttpProcessor httpproc;
	private final HttpRequestExecutor httpexecutor;
	private final ConnectionReuseStrategy connStrategy;

	public ProxyHandler(final HttpHost target, final HttpProcessor httpproc, final HttpRequestExecutor httpexecutor) {
		super();
		this.target = target;
		this.httpproc = httpproc;
		this.httpexecutor = httpexecutor;
		this.connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
	}

	public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context)
			throws HttpException, IOException {

		final HttpClientConnection conn = (HttpClientConnection) context.getAttribute(Constant.HTTP_OUT_CONN);

		context.setAttribute(HttpCoreContext.HTTP_CONNECTION, conn);
		context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST, this.target);

		System.out.println(">> Request URI: " + request.getRequestLine());

		// Remove hop-by-hop headers
		request.removeHeaders(HTTP.CONTENT_LEN);
		request.removeHeaders(HTTP.TRANSFER_ENCODING);
		request.removeHeaders(HTTP.CONN_DIRECTIVE);
		request.removeHeaders("Keep-Alive");
		request.removeHeaders("Proxy-Authenticate");
		request.removeHeaders("TE");
		request.removeHeaders("Trailers");
		request.removeHeaders("Upgrade");
		request.setHeader("Host", this.target.getHostName());

		this.httpexecutor.preProcess(request, this.httpproc, context);

		/** ------------打印请求头--------------- */
		HeaderIterator it = request.headerIterator();
		while (it.hasNext()) {
			System.out.println("Request header: " + it.next());
		}
		System.out.println("--------------");
		/** ------------打印请求头--------------- */

		final HttpResponse targetResponse = this.httpexecutor.execute(request, conn, context);
		this.httpexecutor.postProcess(response, this.httpproc, context);

		// Remove hop-by-hop headers
		targetResponse.removeHeaders(HTTP.CONTENT_LEN);
		targetResponse.removeHeaders(HTTP.TRANSFER_ENCODING);
		targetResponse.removeHeaders(HTTP.CONN_DIRECTIVE);
		targetResponse.removeHeaders("Keep-Alive");
		targetResponse.removeHeaders("TE");
		targetResponse.removeHeaders("Trailers");
		targetResponse.removeHeaders("Upgrade");

		response.setStatusLine(targetResponse.getStatusLine());
		response.setHeaders(targetResponse.getAllHeaders());
		response.setEntity(targetResponse.getEntity());
		
		/** ------------打印响应头--------------- */
        HeaderIterator it2 = response.headerIterator();
        while (it2.hasNext()) {
            System.out.println("Response header: " + it2.next());
        }
        /** ------------打印响应头--------------- */

		System.out.println("<< Response: " + response.getStatusLine());

		final boolean keepalive = this.connStrategy.keepAlive(response, context);
		context.setAttribute(Constant.HTTP_CONN_KEEPALIVE, new Boolean(keepalive));
	}

}
