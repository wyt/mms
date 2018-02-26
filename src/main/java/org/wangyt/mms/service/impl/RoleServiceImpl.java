package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.RoleDao;
import org.wangyt.mms.domain.Role;
import org.wangyt.mms.service.RoleService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:54:15
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/RoleServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService
{
    @Autowired
    private RoleDao roleDao;

    @Override
    public BaseDao<Role> getEntiryDao()
    {
        return roleDao;
    }

    @Override
    public Integer findMaximumSort()
    {
        return roleDao.findMaximumSort();
    }

}
