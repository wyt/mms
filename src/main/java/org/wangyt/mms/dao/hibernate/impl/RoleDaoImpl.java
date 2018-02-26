package org.wangyt.mms.dao.hibernate.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.RoleDao;
import org.wangyt.mms.domain.Role;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:49:45
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/hibernate/impl/RoleDaoImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao
{

    @Override
    public Integer findMaximumSort()
    {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select max(r.sort) from Role r");
        Integer maximum = (Integer) query.uniqueResult();
        return maximum;
    }

}
