package org.wangyt.mms.dao;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.domain.PrivilegeType;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:09:23
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/PrivilegeTypeDao.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface PrivilegeTypeDao extends BaseDao<PrivilegeType>
{
    /**
     * 得到记录集中最大的排序号
     * 
     * @return
     */
    Integer findMaximumSort();

}
