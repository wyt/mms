package org.wangyt.mms.util.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Hql 工具类
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 下午5:20:44
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/util/hibernate/QueryBuilder.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
public class QueryBuilder {
  private Logger log = Logger.getLogger(QueryBuilder.class);

  // clause 表示 子句的意思
  private String selectClause;
  private String fromClause;
  private String whereClause;
  private String orderByClause;

  private List<Object> parameters = new ArrayList<Object>();

  /** 生成Form子句 */
  public QueryBuilder(Class entityClass) {
    fromClause = "from " + entityClass.getName();
  }

  /** 拼凑 select 子句 */
  public QueryBuilder addSelectProperty(String property) {
    if (selectClause == null) {
      selectClause = "select " + property;
    } else {
      selectClause += " ," + property;
    }
    return this;
  }

  /** 拼接 where 子句 */
  public QueryBuilder addWhereCondition(String condition, Object... params) {
    if (whereClause == null) {
      whereClause = "where " + condition;
    } else {
      whereClause += " and " + condition;
    }

    // 保存参数
    if (params != null || params.length > 0) {
      for (Object param : params) {
        parameters.add(param);
      }
    }
    return this;
  }

  /** 拼接order by子句 reverse 为true 按desc排序;反之按asc排序 */
  public QueryBuilder addOrderProperty(String property, boolean reverse) {
    if (orderByClause == null) {
      orderByClause = "order by " + property + (reverse ? " desc" : " asc");
    } else {
      orderByClause += " ," + property + (reverse ? " desc" : " asc");
    }
    return this;
  }

  private void addClauseIfNotNull(StringBuffer hql, String clause) {
    if (clause != null) {
      hql.append(clause).append(" ");
    }
  }

  /** 查询总数量的HQL */
  public String toQueryCountHql() {
    StringBuffer hql = new StringBuffer();

    hql.append("select count(*) ");
    addClauseIfNotNull(hql, fromClause);
    addClauseIfNotNull(hql, whereClause);
    // 查询数量不需要排序
    log.info("查询总数量的sql: " + hql.toString());
    return hql.toString().trim();
  }

  /** 组装HQL */
  public String toQueryListHql() {
    StringBuffer hql = new StringBuffer();

    addClauseIfNotNull(hql, selectClause);
    addClauseIfNotNull(hql, fromClause);
    addClauseIfNotNull(hql, whereClause);
    addClauseIfNotNull(hql, orderByClause);

    log.info("QueryBuilder 生成的hql: " + hql.toString());
    return hql.toString().trim();
  }

  /** 查询总记录数 */
  public int queryCount(Session session) {
    Query countQuery = session.createQuery(toQueryCountHql());
    for (int i = 0; i < parameters.size(); i++) {
      countQuery.setParameter(i, parameters.get(i));
    }

    return ((Number) countQuery.uniqueResult()).intValue();
  }

  /** 查询指定页的数据 */
  public List queryList(Session session, int pageNum, int pageSize) {
    Query listQuery = session.createQuery(toQueryListHql());
    for (int i = 0; i < parameters.size(); i++) {
      listQuery.setParameter(i, parameters.get(i));
    }

    // 设置从哪一条记录开始(包括该条记录)
    listQuery.setFirstResult((pageNum - 1) * pageSize);
    // 设置一共取多少条
    listQuery.setMaxResults(pageSize);

    return listQuery.list();
  }

  /** 查询集合数据 */
  public List queryList(Session session) {
    Query listQuery = session.createQuery(toQueryListHql());
    for (int i = 0; i < parameters.size(); i++) {
      listQuery.setParameter(i, parameters.get(i));
    }
    return listQuery.list();
  }

}
