package org.wangyt.mms.service;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.PrivilegeType;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:11:50
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/PrivilegeTypeService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface PrivilegeTypeService extends BaseService<PrivilegeType>
{
    /**
     * 得到记录集中最大的排序号
     * 
     * @return
     */
    Integer findMaximumSort();
}
