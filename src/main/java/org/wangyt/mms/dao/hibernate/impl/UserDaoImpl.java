package org.wangyt.mms.dao.hibernate.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.UserDao;
import org.wangyt.mms.domain.User;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:36:45
 * 
 * @version $Rev: 93 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/hibernate/impl/UserDaoImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    
    @Override
    public User getUserByLoginNameAndPassword(String loginName, String password) {
        Session session = this.getSessionFactory().getCurrentSession();

        Query query = session.createQuery("from User u where u.loginName=? and u.password=?")//
                .setParameter(0, loginName)//
                .setParameter(1, password);

        return (User) query.uniqueResult();
    }

    @Override
    public User getUserByLoginName(String loginName) {
        
        Session session = this.getSessionFactory().getCurrentSession();
        
        Query query = session.createQuery("from User u where u.loginName=?")//
                .setParameter(0, loginName);
        
        return (User) query.uniqueResult();
    }

}
