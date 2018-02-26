package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.PrivilegeTypeDao;
import org.wangyt.mms.domain.PrivilegeType;
import org.wangyt.mms.service.PrivilegeTypeService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-15 下午4:12:41
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/PrivilegeTypeServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("privilegeTypeService")
public class PrivilegeTypeServiceImpl extends BaseServiceImpl<PrivilegeType> implements PrivilegeTypeService
{
    @Autowired
    private PrivilegeTypeDao privilegeTypeDao;

    @Override
    public BaseDao<PrivilegeType> getEntiryDao()
    {
        return privilegeTypeDao;
    }

    @Override
    public Integer findMaximumSort()
    {
        return privilegeTypeDao.findMaximumSort();
    }

}
