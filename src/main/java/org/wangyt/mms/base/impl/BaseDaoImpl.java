package org.wangyt.mms.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.util.GenericsUtils;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * @author 王永涛
 * 
 * @since 2012-11-13 上午9:15:54
 * 
 *        Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 * @param <T>
 */
@SuppressWarnings("all")
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	/**
	 * T(领域模型)的类档案。
	 */
	private Class<T> clazz;

	/**
	 * 注解方式注入sessionFactory
	 * 
	 * 每个继承BaseDaoImpl的子类都会继承该方法。
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 构造方法用于初始化 class.
	 */
	public BaseDaoImpl() {

		// 进入if块说明是BaseDaoImpl子类调用的该构造方法
		if (!(this.getClass().equals(BaseDaoImpl.class))) {
			// Class sonClazz = this.getClass();
			// ParameterizedType type = (ParameterizedType)
			// (sonClazz.getGenericSuperclass());
			// Type[] tArray = type.getActualTypeArguments();
			// clazz = (Class<T>) tArray[0];

			clazz = GenericsUtils.getSuperClassGenricType(this.getClass(), 0);
		}
	}

	public void save(T entity) {
		this.getHibernateTemplate().persist(entity);
		// this.getHibernateTemplate().save(entity);
	}

	public void delete(String id) {
		this.getHibernateTemplate().delete(getById(id));
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public T getById(String id) {
		// System.out.println("clazz: " + clazz);
		return (T) this.getSession().get(clazz, id);
		// return (T) this.getSession().load(clazz, id);
	}

	public void update(T entity) {
		this.getHibernateTemplate().merge(entity);
		// this.getHibernateTemplate().update(entity);
	}

	@Override
	public List<T> list() {
		Session session = this.getSessionFactory().getCurrentSession();
		List<T> objs = session.createQuery("from " + clazz.getName()).list();
		return objs;
	}

	@Override
	public List<T> findByQueryBuilder(QueryBuilder qb) {
		Session session = this.getSessionFactory().getCurrentSession();

		return qb.queryList(session);
	}

	/**
	 * 查询分页数据 (可加各种条件)
	 * 
	 * @param queryBuilder
	 *            hql生成工具
	 * 
	 * @param pageNum
	 *            第几页
	 * 
	 * @param pageSize
	 *            每页记录数
	 * 
	 * @return
	 */
	@Override
	public PageItemsJson getPageView(QueryBuilder queryBuilder, int pageNum, int pageSize) {
		Session session = this.getSessionFactory().getCurrentSession();
		long count = queryBuilder.queryCount(session);
		List list = queryBuilder.queryList(session, pageNum, pageSize);

		PageItemsJson pij = new PageItemsJson();
		pij.setTotal(count);
		pij.setRows(list);

		return pij;
	}

}
