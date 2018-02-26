package org.wangyt.mms.base.impl;

import java.util.List;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * @author 王永涛
 * 
 * @date 2012-11-13 上午9:16:34
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/base/impl/BaseServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T>
{
    /**
     * 定义一个抽象方法,由子类实现。用于得到实体Dao。
     * 
     * @return
     */
    public abstract BaseDao<T> getEntiryDao();

    @Override
    public void save(T entity)
    {
        getEntiryDao().save(entity);
    }

    @Override
    public void delete(T entity)
    {
        getEntiryDao().delete(entity);
    }

    @Override
    public T findById(String id)
    {
        return (T) getEntiryDao().getById(id);
    }

    @Override
    public void update(T entity)
    {
        getEntiryDao().update(entity);
    }

    public List<T> list()
    {
        return getEntiryDao().list();
    }

    @Override
    public List<T> findByQueryBuilder(QueryBuilder qb)
    {
        return getEntiryDao().findByQueryBuilder(qb);
    }

    @Override
    public PageItemsJson getPageView(QueryBuilder queryBuilder, int pageNum, int pageSize)
    {
        return getEntiryDao().getPageView(queryBuilder, pageNum, pageSize);
    }

}
