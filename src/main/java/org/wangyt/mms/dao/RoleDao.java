package org.wangyt.mms.dao;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.domain.Role;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:48:55
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/RoleDao.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface RoleDao extends BaseDao<Role>
{
    /**
     * 得到记录集中最大的排序号
     * 
     * @return
     */
    Integer findMaximumSort();


}
