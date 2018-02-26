package org.wangyt.mms.service;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.Privilege;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:06:26
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/PrivilegeService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface PrivilegeService extends BaseService<Privilege>
{
    /**
     * 得到记录集中最大的排序号
     * 
     * @return
     */
    Integer findMaximumSort();

}
