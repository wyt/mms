package org.wangyt.learning.spring.aop.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 定义一个切面，定义好后，需要交给spring容器管理(通过注解)。
 * 
 * @author WANG YONG TAO
 * 
 * @since 2012-7-21 下午03:35:45
 * 
 */
@Aspect
@Component("personServiceAspect")
public class PersonServiceAspect {

	/** 声明一个切入点,拦截save()方法 */
	@Pointcut("execution (* org.wangyt.learning.spring.aop.service.impl.PersonServiceImpl.save(..))")
	private void save() {
	};

	/** 定义前置通知(不带参数) */
	@Before("save()")
	public void doAccessCheck() {
		System.out.println("我是前置通知!");
	}

	/** 定义后置通知 */
	@AfterReturning("save()")
	private void doAfterReturning() {
		System.out.println("我是后置通知!");
	}

	/** 定义最终通知 */
	@After("save()")
	public void doAfter() {
		System.out.println("我是最终通知!");
	}

	/** 声明一个切入点,拦截saveThrowException()方法 */
	@Pointcut("execution (* org.wangyt.learning.spring.aop.service.impl.PersonServiceImpl.saveThrowException(..))")
	private void saveThrowException() {
	};

	/** 定义例外通知 */
	@AfterThrowing(pointcut = "saveThrowException()", throwing = "e")
	public void doAfterThrowing(Exception e) {
		System.out.println("我是例外通知! " + e.getMessage());
	}

	/** 声明一个切入点,拦截update()方法 */
	@Pointcut("execution (* org.wangyt.learning.spring.aop.service.impl.PersonServiceImpl.update(..))")
	private void update() {
	};

	/** 定义前置通知(带参数) */
	@Before("update() && args(name,id)") // args(name,id) 跟前面的条件合并, 且要与下面方法中的参数名保持一致
	public void doAccessCheckOwnerArgs(String name, Integer id) {
		System.out.println("我是前置通知!" + "name: " + name + " id: " + id);
	}

	/** 声明一个切入点,拦截getPersonName()方法 */
	@Pointcut("execution (* org.wangyt.learning.spring.aop.service.impl.PersonServiceImpl.getPersonName(..))")
	private void getPersonName() {
	};

	/** 定义后置通知，拦截返回值 */
	@AfterReturning(pointcut = "getPersonName()", returning = "result") // 将被拦截到的方法的返回值设置到该方法的参数中
	private void doAfterReturning(String result) {
		System.out.println("我是后置通知!" + "result: " + result);
	}

	/** 声明一个切入点,拦截aroundMethod()方法 */
	@Pointcut("execution (* org.wangyt.learning.spring.aop.service.impl.PersonServiceImpl.aroundMethod(..))")
	private void aroundMethod() {
	};

	/** 定义环绕通知 */
	@Around("aroundMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		try {
			System.out.println("(环绕)前置通知");
			result = pjp.proceed(); // pjp.proceed() 表示执行被拦截的方法
			System.out.println("(环绕)后置通知");
			return result;
		} catch (Exception e) {
			System.out.println("(环绕)例外通知");
			return result;
		} finally {
			System.out.println("(环绕)最终通知");
		}
	}

}