package org.wangyt.mms.service;

import java.util.List;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.Dept;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:44:31
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/DeptService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface DeptService extends BaseService<Dept>
{
    /**
     * 查询没有上级部门的部门
     * 
     * @return
     */
    List<Dept> findDeptParentIsNull();
}
