package org.apache.http.examples.reverse.proxy;

import java.io.IOException;

import org.apache.http.HttpHost;

/**
 * http://localhost:8888
 * 
 * @author WANG YONG TAO
 *
 */
public class Main {

	/** 目标主机信息 baidu. */
	public static final String TARGET_HOSTNAME = "119.75.213.61";
	public static final int TARGET_PORT = 80;
	/** 反向代理服务器端口. */
	public static final int REVERSE_PROXY_PORT = 8888;

	public static void main(String[] args) throws IOException {

		/** 创建httpHost,封装目标主机地址和端口. */
		final HttpHost target = new HttpHost(TARGET_HOSTNAME, TARGET_PORT);

		/** 客户端请求监听器. */
		final Thread t = new RequestListenerThread(REVERSE_PROXY_PORT, target);
		t.setDaemon(false);
		t.start();
	}

}
