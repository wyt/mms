package org.wangyt.mms.dao.hibernate.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.DeptDao;
import org.wangyt.mms.domain.Dept;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:43:06
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/hibernate/impl/DeptDaoImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao
{
    @Override
    public List<Dept> findDeptParentIsNull()
    {
        Session session = this.getSessionFactory().getCurrentSession();
        String hql = "from Dept d where d.parent is null";
        Query query = session.createQuery(hql);
        return query.list();
    }
}
