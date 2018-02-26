package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.StockDao;
import org.wangyt.mms.domain.Stock;
import org.wangyt.mms.service.StockService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-22 上午10:32:32
 * 
 * @version $Rev: 111 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/StockServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("stockService")
public class StockServiceImpl extends BaseServiceImpl<Stock> implements StockService
{
    @Autowired
    private StockDao stockDao;

    @Override
    public BaseDao<Stock> getEntiryDao()
    {
        return stockDao;
    }

}
