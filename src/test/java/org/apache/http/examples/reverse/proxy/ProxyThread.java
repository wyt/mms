package org.apache.http.examples.reverse.proxy;

import java.io.IOException;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpService;

/**
 * 主要执行 HttpService#handleRequest(),并触发ProxyHandler#handle()
 * 
 * @author WANG YONG TAO
 *
 */
class ProxyThread extends Thread {

	private final HttpService httpservice;
	private final HttpServerConnection inconn;
	private final HttpClientConnection outconn;

	public ProxyThread(final HttpService httpservice, final HttpServerConnection inconn,
			final HttpClientConnection outconn) {
		super();
		this.httpservice = httpservice;
		this.inconn = inconn;
		this.outconn = outconn;
	}

	@Override
	public void run() {
		System.out.println("New connection thread");
		final HttpContext context = new BasicHttpContext(null);

		// Bind connection objects to the execution context
		context.setAttribute(Constant.HTTP_IN_CONN, this.inconn);
		context.setAttribute(Constant.HTTP_OUT_CONN, this.outconn);

		try {
			while (!Thread.interrupted()) {
				if (!this.inconn.isOpen()) {
					this.outconn.close();
					break;
				}

				this.httpservice.handleRequest(this.inconn, context);

				final Boolean keepalive = (Boolean) context.getAttribute(Constant.HTTP_CONN_KEEPALIVE);
				if (!Boolean.TRUE.equals(keepalive)) {
					this.outconn.close();
					this.inconn.close();
					break;
				}
			}
		} catch (final ConnectionClosedException ex) {
			ex.printStackTrace();
		} catch (final IOException ex) {
			ex.printStackTrace();
		} catch (final HttpException ex) {
			ex.printStackTrace();
		} finally {
			try {
				this.inconn.shutdown();
			} catch (final IOException ignore) {
				ignore.printStackTrace();
			}
			try {
				this.outconn.shutdown();
			} catch (final IOException ignore) {
				ignore.printStackTrace();
			}
		}
	}

}
