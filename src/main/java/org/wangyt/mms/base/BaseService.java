package org.wangyt.mms.base;

import java.util.List;

import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * @author 王永涛
 * 
 * @date 2012-11-12 下午6:11:25
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/base/BaseService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 * @param <T>
 */
public interface BaseService<T>
{
    void save(T entity);

    void delete(T entity);

    T findById(String id);

    void update(T entity);

    List<T> list();

    /**
     * 根据QueryBuilder 生成的hql查询，前提当然是返回List的hql
     * 
     * @param qb
     * @return
     */
    List<T> findByQueryBuilder(QueryBuilder qb);

    /**
     * 根据不确定的条件查询分页数据
     * 
     * @param queryBuilder
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageItemsJson getPageView(QueryBuilder queryBuilder, int pageNum, int pageSize);
}
