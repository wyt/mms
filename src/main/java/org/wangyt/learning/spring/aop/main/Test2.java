package org.wangyt.learning.spring.aop.main;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wangyt.learning.spring.aop.service.PersonService;

/**
 * 基于XML. <BR>
 * 
 * @author 王永涛
 * 
 * @since 2012-7-21 下午06:16:10
 * 
 */
@SuppressWarnings("all")
public class Test2 {
	
	@Test
	public void interceptorTest() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans2.xml");
		PersonService personService = (PersonService) cxt.getBean("personService");

		// 得到拦截方法的返回值
		personService.getPersonName(new Integer(2));
	}

	/**
	 * 测试例外通知
	 */
	@Test
	public void interceptorException() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans2.xml");
		PersonService personService = (PersonService) cxt.getBean("personService");
		
		personService.saveThrowException("xxx");
	}

	/**
	 * 得到拦截方法的参数
	 */
	@Test
	public void interceptorArgsTest() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("org/wangyt/learning/spring/aop/cfg/beans2.xml");
		PersonService personService = (PersonService) cxt.getBean("personService");

		personService.update("wangyongtao", new Integer(2));
	}

}
