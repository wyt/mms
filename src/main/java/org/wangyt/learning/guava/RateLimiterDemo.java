package org.wangyt.learning.guava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {

	public static void main(String[] args) throws InterruptedException {
		testWithRateLimiter(true);
	}

	public static void testWithRateLimiter(final boolean flag) {

		ExecutorService tPool = Executors.newFixedThreadPool(3);
		Long start = System.currentTimeMillis();

		// 每秒不超过10个任务被提交 (不加final提示: Cannot refer to the non-final local
		// variable limiter defined in an enclosing scope)
		// final RateLimiter limiter = RateLimiter.create((1.0 / 60.0));

		// 5个任务3个线程执行
		for (int i = 0; i < 5; i++) {
			final int s = i;
			tPool.execute(new Runnable() {
				@Override
				public void run() {
					final RateLimiter limiter = RateLimiter.create((1.0 / 60.0));
					// synchronized (limiter) {
					for (int j = 0; j < 10; j++) {
						if (flag == true) {
							// 请求RateLimiter, 超过permits会被阻塞
							System.out.println(limiter.acquire());
						}
						System.out.println(
								"线程 " + Thread.currentThread().getName() + " 执行第  " + s + " 个任务中的第 " + j + " 步骤");
					}
					// }
				}
			});
		}

		tPool.shutdown();

		while (true) {
			if (tPool.isTerminated()) {
				long end = System.currentTimeMillis();
				System.out.println("用时: " + (end - start) + "ms");
				break;
			}
		}
	}
}