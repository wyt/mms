package org.wangyt.mms.dao.hibernate.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.PrivilegeDao;
import org.wangyt.mms.domain.Privilege;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:02:18
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/hibernate/impl/PrivilegeDaoImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Repository("privilegeDao")
public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements PrivilegeDao
{

    @Override
    public Integer findMaximumSort()
    {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select max(p.sort) from Privilege p");
        Integer maximum = (Integer) query.uniqueResult();
        return maximum;
    }

}
