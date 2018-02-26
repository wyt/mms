package org.wangyt.mms.service;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.Bill;

/**
 * @author 王永涛
 * 
 * @date 2012-11-22 上午10:15:01
 * 
 * @version $Rev: 134 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/BillService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface BillService extends BaseService<Bill>
{
    /**
     * 返回单据的下一个编码
     * 
     * @return
     */
    String findNextBillNo(String prefix);

    /**
     * 保存单据，并更新库存
     */
    String saveBill(Bill bill);
}
