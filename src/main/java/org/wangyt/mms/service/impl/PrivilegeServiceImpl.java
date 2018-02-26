package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.PrivilegeDao;
import org.wangyt.mms.domain.Privilege;
import org.wangyt.mms.service.PrivilegeService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:07:11
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/PrivilegeServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("privilegeService")
public class PrivilegeServiceImpl extends BaseServiceImpl<Privilege> implements PrivilegeService
{
    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public BaseDao<Privilege> getEntiryDao()
    {
        return privilegeDao;
    }

    @Override
    public Integer findMaximumSort()
    {
        return privilegeDao.findMaximumSort();
    }

}
