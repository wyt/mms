package org.wangyt.mms.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.UserService;

public class AppDataInitTest {

	private ApplicationContext ctx;
	private static UserService userService;

	/** 初始化Spring容器 . */
	@Before
	public void before() {
		try {
			ctx = new ClassPathXmlApplicationContext(
					new String[] { "spring/spring-core.xml", "spring/spring-hbm.xml", "spring/spring-cache.xml" });

			userService = (UserService) ctx.getBean("userService");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateDDL() {
		AnnotationSessionFactoryBean sf = (AnnotationSessionFactoryBean) ctx.getBean("&sessionFactory");
		SchemaExport dbExport = new SchemaExport(sf.getConfiguration());
		dbExport.setFormat(true);
		dbExport.setOutputFile("src/main/resources/mms.sql");
		dbExport.create(true, false);
	}

	/** 添加一个admin用户 密码为1. */
	@Test
	public void addAdminUser() {
		User admin = userService.finUserByLoginName("admin");
		if (admin == null) {
			admin = new User();
			admin.setUserName("系统管理员");
			admin.setSex("男");
			admin.setDept(null);
			admin.setLoginName("admin");
			admin.setPassword(DigestUtils.md5Hex("1"));
			userService.save(admin);
		}
	}

}
