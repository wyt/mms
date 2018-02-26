package org.wangyt.learning.spring.aop.service.impl;

import org.springframework.stereotype.Component;
import org.wangyt.learning.spring.aop.service.PersonService;

/**
 * @author 王永涛
 * 
 * @since 2012-7-19 下午02:21:12
 * 
 */
@Component("personServiceAnno")
public class PersonServiceImpl implements PersonService {

   public PersonServiceImpl() {}

   public String getPersonName(Integer id) {
      System.out.println(this + " 我是getPersonName方法 ");
      return "wangyongtao " + id;
   }

   public void save() {
      System.out.println("我是save方法");
   }

   public void saveThrowException(String name) {
      throw new RuntimeException("抛出异常");
   }

   public void update(String name, Integer id) {
      System.out.println("我是update方法");
   }

   @Override
   public void aroundMethod() {
      throw new RuntimeException("我是aroundMethod方法!");
   }
   
}
