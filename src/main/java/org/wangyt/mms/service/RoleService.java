package org.wangyt.mms.service;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.Role;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:52:56
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/RoleService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface RoleService extends BaseService<Role>
{
    /**
     * 得到记录集中最大的排序号
     * 
     * @return
     */
    Integer findMaximumSort();
}
