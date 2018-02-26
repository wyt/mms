package org.apache.http.examples.reverse.proxy;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultBHttpServerConnection;
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
 * 客户端请求监听器.
 * 
 * @author WANG YONG TAO.
 *
 */
class RequestListenerThread extends Thread {

	private final HttpHost target;
	private final ServerSocket serversocket;
	private final HttpService httpService;

	public RequestListenerThread(final int port, final HttpHost target) throws IOException {
		
		this.target = target;
		this.serversocket = new ServerSocket(port);

		// (为进入链接建立http协议处理器) Set up HTTP protocol processor for incoming connections
		final HttpProcessor inhttpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] { 
						new RequestContent(), 
						new RequestTargetHost(), 
						new RequestConnControl(),
						new RequestUserAgent("Test/1.1"), 
						new RequestExpectContinue(true) });

		// (为外出链接建立http协议处理器) Set up HTTP protocol processor for outgoing connections
		final HttpProcessor outhttpproc = new ImmutableHttpProcessor(new HttpResponseInterceptor[] { 
						new ResponseDate(),
						new ResponseServer("Test/1.1"), 
						new ResponseContent(), 
						new ResponseConnControl() });

		// (建立对外请求执行器) Set up outgoing request executor
		final HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		/** 注册映射请求处理Handler -- (建立进入请求handler) Set up incoming request handler */
		final UriHttpRequestHandlerMapper handlerMapper = new UriHttpRequestHandlerMapper();
		HttpRequestHandler proxyHandler = new ProxyHandler(this.target, outhttpproc, httpexecutor);
		handlerMapper.register("*", proxyHandler);

		// (建立 http服务) Set up the HTTP service
		this.httpService = new HttpService(inhttpproc, handlerMapper);
	}

	@Override
	public void run() {
		System.out.println("Listening on port " + this.serversocket.getLocalPort());
		while (!Thread.interrupted()) {
			try {
				final int bufsize = 8 * 1024;
				
				// 阻塞监听服务端请求
				final Socket insocket = this.serversocket.accept();
				
				/*
				 * (设置传入的HTTP连接) Set up incoming HTTP connection 
				 * 
				 * 客户端 <->反向代理服务器  0:0:0:0:0:0:0:1:8888<->0:0:0:0:0:0:0:1:60222
				 */
				final DefaultBHttpServerConnection inconn = new DefaultBHttpServerConnection(bufsize);
				inconn.bind(insocket);

				/*
				 * (设置传出的HTTP连接) Set up outgoing HTTP connection
				 * 
				 * 反向代理服务器 <->目标服务器 192.168.31.164:60252<->119.75.216.20:80
				 */
				// final Socket outsocket = new Socket(this.target.getHostName(), this.target.getPort());
				// final DefaultBHttpClientConnection outconn = new DefaultBHttpClientConnection(bufsize);
				// outconn.bind(outsocket);

				// 使用连接池方式
				DefaultBHttpClientConnection outconn = (DefaultBHttpClientConnection) HttpConnPool.getConn(new HttpHost(this.target.getHostName(), this.target.getPort()), null);

				// (启动工作线程) Start worker thread
				final Thread t = new ProxyThread(this.httpService, inconn, outconn);
				t.setDaemon(true);
				t.start();
			} catch (final InterruptedIOException ex) {
				System.err.println(ex.getMessage());
				break;
			} catch (final IOException e) {
				System.err.println("I/O error initialising connection thread: " + e.getMessage());
				break;
			}
		}
	}
}
