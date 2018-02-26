package org.wangyt.mms.openapi.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@SuppressWarnings("all")
@Component("openAPIAspect")
public class OpenAPIAspect {

	@Pointcut("execution (* org.wangyt.mms.openapi.controller.IOpenApiTest.*(..))")
	private void aroundMethod() {
	}

	@Around("aroundMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint point) throws Throwable {
		Object result = null;
		try {
			// 拦截的实体类 OrderHubRegistController
			Object target = point.getTarget();
			Class clazz = target.getClass();
			// 得到拦截的方法
			Method method = ((MethodSignature) point.getSignature()).getMethod();

			System.out.println("target: " + target);

			result = point.proceed();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// nothing..
		}
		return result;
	}

}
