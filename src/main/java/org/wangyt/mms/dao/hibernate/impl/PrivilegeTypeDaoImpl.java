package org.wangyt.mms.dao.hibernate.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.PrivilegeTypeDao;
import org.wangyt.mms.domain.PrivilegeType;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:10:05
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/hibernate/impl/PrivilegeTypeDaoImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Repository("privilegeTypeDao")
public class PrivilegeTypeDaoImpl extends BaseDaoImpl<PrivilegeType> implements PrivilegeTypeDao
{

    @Override
    public Integer findMaximumSort()
    {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select max(pt.sort) from PrivilegeType pt");
        // 没有记录是返回null
        Integer maximum = (Integer) query.uniqueResult();
        // System.out.println("max(sort): " + maximum);
        return maximum;
    }

}
