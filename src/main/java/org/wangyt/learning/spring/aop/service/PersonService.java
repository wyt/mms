package org.wangyt.learning.spring.aop.service;

/**
 * @author 王永涛
 * 
 * @since 2012-7-19 下午02:19:04
 * 
 */
public interface PersonService {

	public void save();

	public void update(String name, Integer id);

	public String getPersonName(Integer id);

	public void saveThrowException(String name);

	public void aroundMethod();

}
