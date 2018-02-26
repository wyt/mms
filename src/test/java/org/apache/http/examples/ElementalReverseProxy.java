/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.examples;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;

/**
 * Elemental HTTP/1.1 reverse proxy.
 */
public class ElementalReverseProxy {

	/** 目标主机信息 baidu. */
	public static final String TARGET_HOSTNAME = "119.75.213.61";
	public static final int TARGET_PORT = 80;
	/** 反向代理服务器端口. */
	public static final int REVERSE_PROXY_PORT = 8888;

	private static final String HTTP_IN_CONN = "http.proxy.in-conn";
	private static final String HTTP_OUT_CONN = "http.proxy.out-conn";
	private static final String HTTP_CONN_KEEPALIVE = "http.proxy.conn-keepalive";

	public static void main(final String[] args) throws Exception {
		final HttpHost target = new HttpHost(TARGET_HOSTNAME, TARGET_PORT);
		final Thread t = new RequestListenerThread(REVERSE_PROXY_PORT, target);
		t.setDaemon(false);
		t.start();
	}

	static class ProxyHandler implements HttpRequestHandler {

		private final HttpHost target;
		private final HttpProcessor httpproc;
		private final HttpRequestExecutor httpexecutor;
		private final ConnectionReuseStrategy connStrategy;

		public ProxyHandler(final HttpHost target, final HttpProcessor httpproc,
				final HttpRequestExecutor httpexecutor) {
			super();
			this.target = target;
			this.httpproc = httpproc;
			this.httpexecutor = httpexecutor;
			this.connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
		}

		public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context)
				throws HttpException, IOException {

			final HttpClientConnection conn = (HttpClientConnection) context.getAttribute(HTTP_OUT_CONN);

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

			System.out.println("<< Response: " + response.getStatusLine());

			final boolean keepalive = this.connStrategy.keepAlive(response, context);
			context.setAttribute(HTTP_CONN_KEEPALIVE, new Boolean(keepalive));
		}

	}

	static class RequestListenerThread extends Thread {

		private final HttpHost target;
		private final ServerSocket serversocket;
		private final HttpService httpService;

		public RequestListenerThread(final int port, final HttpHost target) throws IOException {
			this.target = target;
			this.serversocket = new ServerSocket(port);

			// (为进入链接建立http协议处理器) Set up HTTP protocol processor for incoming connections
			final HttpProcessor inhttpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
					new RequestContent(), new RequestTargetHost(), new RequestConnControl(),
					new RequestUserAgent("Test/1.1"), new RequestExpectContinue(true) });

			// (为外出链接建立http协议处理器) Set up HTTP protocol processor for outgoing connections
			final HttpProcessor outhttpproc = new ImmutableHttpProcessor(
					new HttpResponseInterceptor[] { new ResponseDate(), new ResponseServer("Test/1.1"),
							new ResponseContent(), new ResponseConnControl() });

			// (建立对外请求执行器) Set up outgoing request executor
			final HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

			// (建立进入请求handler) Set up incoming request handler
			final UriHttpRequestHandlerMapper handlerMapper = new UriHttpRequestHandlerMapper();

			HttpRequestHandler proxyHandler = new ProxyHandler(this.target, outhttpproc, httpexecutor);
			handlerMapper.register("*", proxyHandler);

			// (建立 http服务) Set up the HTTP service
			this.httpService = new HttpService(inhttpproc, handlerMapper);
		}

		@Override
		public void run() {
			System.out.println("Listening on port " + this.serversocket.getLocalPort() + ", server address: "
					+ this.serversocket.getInetAddress());
			while (!Thread.interrupted()) {
				try {
					final int bufsize = 8 * 1024;
					// (设置传入的HTTP连接) Set up incoming HTTP connection
					final Socket insocket = this.serversocket.accept();
					final DefaultBHttpServerConnection inconn = new DefaultBHttpServerConnection(bufsize);
					System.out.println("Incoming connection from " + insocket.getInetAddress());
					inconn.bind(insocket);

					// (设置传出的HTTP连接) Set up outgoing HTTP connection
					final Socket outsocket = new Socket(this.target.getHostName(), this.target.getPort());
					final DefaultBHttpClientConnection outconn = new DefaultBHttpClientConnection(bufsize);
					outconn.bind(outsocket);
					System.out.println("Outgoing connection to " + outsocket.getInetAddress());

					// (启动工作线程) Start worker thread
					final Thread t = new ProxyThread(this.httpService, inconn, outconn);
					t.setDaemon(true);
					t.start();
				} catch (final InterruptedIOException ex) {
					break;
				} catch (final IOException e) {
					System.err.println("I/O error initialising connection thread: " + e.getMessage());
					break;
				}
			}
		}
	}

	/**
	 * 工作线程,反向代理请求
	 */
	static class ProxyThread extends Thread {

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
			context.setAttribute(HTTP_IN_CONN, this.inconn);
			context.setAttribute(HTTP_OUT_CONN, this.outconn);

			try {
				while (!Thread.interrupted()) {
					if (!this.inconn.isOpen()) {
						this.outconn.close();
						break;
					}

					this.httpservice.handleRequest(this.inconn, context);

					final Boolean keepalive = (Boolean) context.getAttribute(HTTP_CONN_KEEPALIVE);
					if (!Boolean.TRUE.equals(keepalive)) {
						this.outconn.close();
						this.inconn.close();
						break;
					}
				}
			} catch (final ConnectionClosedException ex) {
				System.err.println("Client closed connection");
			} catch (final IOException ex) {
				System.err.println("I/O error: " + ex.getMessage());
				ex.printStackTrace();
			} catch (final HttpException ex) {
				System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());
			} finally {
				try {
					this.inconn.shutdown();
				} catch (final IOException ignore) {
				}
				try {
					this.outconn.shutdown();
				} catch (final IOException ignore) {
				}
			}
		}
	}

}
