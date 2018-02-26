package org.wangyt.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.DeptDao;
import org.wangyt.mms.domain.Dept;
import org.wangyt.mms.service.DeptService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:45:33
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/DeptServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("deptService")
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService
{
    @Autowired
    private DeptDao deptDao;

    @Override
    public BaseDao<Dept> getEntiryDao()
    {
        return deptDao;
    }

    @Override
    public List<Dept> findDeptParentIsNull()
    {
        return deptDao.findDeptParentIsNull();
    }

}
