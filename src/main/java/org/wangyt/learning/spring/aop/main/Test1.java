package org.wangyt.learning.spring.aop.main;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wangyt.learning.spring.aop.service.PersonService;

/**
 * 基于注解.
 * 
 * 注意这个项目要用jdk1.6编译并运行，当前AOP jar包可能跟jdk1.7不兼容。
 * 
 * @author 王永涛
 * 
 * @since 2012-7-21 下午06:16:10
 * 
 */
@SuppressWarnings("all")
public class Test1 {
	
	/**
	 * 测试无参数方法的前置通知和后置通知
	 */
	@Test
	public void interceptorTest() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans.xml");
		PersonService personService = (PersonService) cxt.getBean("personServiceAnno");

		personService.save();
	}

	/**
	 * 测试例外通知
	 */
	@Test
	public void interceptorException() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans.xml");
		PersonService personService = (PersonService) cxt.getBean("personServiceAnno");

		personService.saveThrowException("zzz");
	}

	/**
	 * 得到拦截方法的参数
	 */
	@Test
	public void interceptorArgsTest() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans.xml");
		PersonService personService = (PersonService) cxt.getBean("personServiceAnno");

		personService.update("wangyongtao", new Integer(2));
	}

	/**
	 * 得到拦截方法的返回值
	 */
	@Test
	public void interceptorReturnsTest() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans.xml");
		PersonService personService = (PersonService) cxt.getBean("personServiceAnno");

		personService.getPersonName(new Integer(2));
	}

	/**
	 * 测试环绕通知
	 */
	@Test
	public void interceptorAroundTest() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans.xml");
		PersonService personService = (PersonService) cxt.getBean("personServiceAnno");

		personService.aroundMethod();
	}

}
