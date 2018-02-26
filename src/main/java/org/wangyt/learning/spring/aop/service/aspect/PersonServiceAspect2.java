package org.wangyt.learning.spring.aop.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 定义一个切面，定义好后，需要交给spring容器管理(通过xml配置)。
 * 
 * @author 王永涛
 * 
 * @since 2012-7-21 下午03:35:45
 * 
 */
@SuppressWarnings("all")
public class PersonServiceAspect2 {

	public void doAccessCheck() {
		System.out.println("我是前置通知!");
	}

	private void doAfterReturning() {
		System.out.println("我是后置通知!");
	}

	public void doAfterThrowing() {
		System.out.println("我是例外通知!");
	}

	public void doAfter() {
		System.out.println("我是最终通知!");
	}

	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		try {
			System.out.println("(环绕)前置通知");
			// 表示执行被拦截的方法
			System.out.println("target: " + pjp.getTarget());;
			result = pjp.proceed();
			System.out.println("(环绕)后置通知 " + result);
			return result;
		} catch (Exception e) {
			System.out.println("(环绕)例外通知 " + result);
			return result;
		} finally {
			System.out.println("(环绕)最终通知");
		}
	}

}
