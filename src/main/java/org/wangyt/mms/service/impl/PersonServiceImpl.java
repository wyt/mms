package org.wangyt.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.PersonDao;
import org.wangyt.mms.domain.Person;
import org.wangyt.mms.service.PersonService;

/**
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 上午9:49:40
 * 
 * @version $Rev: 54 $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService
{
    @Autowired
    private PersonDao personDao;

    @Override
    public BaseDao<Person> getEntiryDao()
    {
        return personDao;
    }

    @Override
    public void saveTwoPerson(List<Person> persons)
    {
        Person p1 = persons.get(0);
        Person p2 = persons.get(1);

        personDao.save(p1);
        System.out.println(3 / 0);
        personDao.save(p2);
    }

}
