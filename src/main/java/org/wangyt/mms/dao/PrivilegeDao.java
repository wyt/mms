package org.wangyt.mms.dao;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.domain.Privilege;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:00:48
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/PrivilegeDao.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface PrivilegeDao extends BaseDao<Privilege>
{
    /**
     * 得到记录集中最大的排序号
     * 
     * @return
     */
    Integer findMaximumSort();

}
