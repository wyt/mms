package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.BillDetailDao;
import org.wangyt.mms.domain.BillDetail;
import org.wangyt.mms.service.BillDetailService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-22 上午10:25:07
 * 
 * @version $Rev: 111 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/BillDetailServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("billDetailService")
public class BillDetailServiceImpl extends BaseServiceImpl<BillDetail> implements BillDetailService
{
    @Autowired
    private BillDetailDao billDetailDao;

    @Override
    public BaseDao<BillDetail> getEntiryDao()
    {
        return billDetailDao;
    }

}
