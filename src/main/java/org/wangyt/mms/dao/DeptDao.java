package org.wangyt.mms.dao;

import java.util.List;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.domain.Dept;

/**
 * 部门
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:42:14
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/DeptDao.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface DeptDao extends BaseDao<Dept>
{
    /**
     * 查询没有上级部门的部门
     * 
     * @return
     */
    List<Dept> findDeptParentIsNull();

}
