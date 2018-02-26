#使用spring进行aop开发

先看基于xml(beans2.xml)

aop就是拦截到方法，在方法之前做些事情，在方法之后做些事情，在方法抛出异常时的行为等。

一、	基于注解(beans.xml)方式

	配置文件中加入：
	
	1). aop命名空间;
	
	2). <aop:aspectj-autoproxy />
	
	
	Aspect(切面): 其实就是类，在AOP编程中成为切面
	joinpoint(连接点): 连接点其实就是业务方法
	Pointcut(切入点): 就是实施拦截的连接点
	Advice(通知): 连接到切入点之后所做的事情
	Target(目标对象): 代理的目标对象
	Weave(织入): 指将切面应用到Target对象并导致proxy对象创建的过程称为织入
	Introduction(引入): 在不修改类代码的前提下, Introduction可以在运行期为类动态地添加一些方法或Field.
	