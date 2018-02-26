package org.wangyt.mms.base;

import java.util.List;

import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * @author 王永涛
 * 
 * @date 2012-11-12 下午6:11:13
 * 
 * @version $Rev: 54 $
 * 
 * @Copyright (c) Copyright 2012 Yongtao.Wang, All rights reserved.
 * 
 * @param <T>
 *            用于将实体(Entity)数据类型参数化
 */
public interface BaseDao<T>
{
	/**
	 * 保存
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	T getById(String id);

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 查询全部
	 * 
	 * @return
	 */
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
