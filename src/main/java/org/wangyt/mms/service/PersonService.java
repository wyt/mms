package org.wangyt.mms.service;

import java.util.List;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.Person;

/**
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 上午9:44:57
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/PersonService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface PersonService extends BaseService<Person>
{
    /**
     * 用于测试事务
     * 
     * @param persons
     */
    void saveTwoPerson(List<Person> persons);
}
