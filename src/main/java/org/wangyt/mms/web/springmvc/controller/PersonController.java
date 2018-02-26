package org.wangyt.mms.web.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;
import org.wangyt.mms.domain.Person;
import org.wangyt.mms.service.PersonService;

/**
 * 系统搭建时的测试控制器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 上午9:51:46
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/PersonController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("personController")
@RequestMapping(value = "person")
public class PersonController
{
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "addPerson")
    public ModelAndView addPerson(Person p)
    {
        personService.save(p);
        return null;
    }

    @RequestMapping(value = "test")
    public ModelAndView test()
    {
        return new ModelAndView("person/test");
    }

    @RequestMapping(value = "listPerson")
    public void listPerson()
    {
        List<Person> persons = personService.list();
        for (Person p : persons)
        {
            System.out.println(p.getName() + "," + p.getSex());
        }
    }

    @RequestMapping(value = "transaction")
    public void testTransaction()
    {
        List<Person> persons = new ArrayList<Person>();

        Person p1 = new Person();
        p1.setName("A");
        p1.setSex("男");
        Person p2 = new Person();
        p2.setName("B");
        p2.setSex("女");

        persons.add(p1);
        persons.add(p2);

        personService.saveTwoPerson(persons);
    }

}
