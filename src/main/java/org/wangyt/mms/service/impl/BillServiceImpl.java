package org.wangyt.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.BillDao;
import org.wangyt.mms.dao.StockDao;
import org.wangyt.mms.domain.Bill;
import org.wangyt.mms.domain.BillDetail;
import org.wangyt.mms.domain.Stock;
import org.wangyt.mms.service.BillService;
import org.wangyt.mms.util.ext.ExactValue;
import org.wangyt.mms.util.hibernate.QueryBuilder;

/**
 * @author 王永涛
 * 
 * @date 2012-11-22 上午10:16:56
 * 
 * @version $Rev: 134 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/BillServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("billService")
public class BillServiceImpl extends BaseServiceImpl<Bill> implements BillService
{
    @Autowired
    private BillDao billDao;
    @Autowired
    private StockDao stockDao;

    @Override
    public BaseDao<Bill> getEntiryDao()
    {
        return billDao;
    }

    @Override
    public String findNextBillNo(String prefix)
    {
        int serno = 1;
        String head = prefix + new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date());
        QueryBuilder qb = new QueryBuilder(Bill.class)//
                .addWhereCondition("billNo like ?", head + "%")//
                .addOrderProperty("billNo", true);
        List<Bill> bills = findByQueryBuilder(qb);
        Bill bill = null;
        if (bills != null && bills.size() > 0)
        {
            bill = bills.get(0);
        }
        if (bill != null)
        {
            String billNo = bill.getBillNo();
            serno = Integer.parseInt(billNo.substring(head.length())) + 1;
        }

        return head + new java.text.DecimalFormat("0000").format(serno);
    }

    @Override
    public String saveBill(Bill bill)
    {
        // 保存单据，将会级联保存单据内容
        save(bill);
        // 更新库存
        List<BillDetail> bds = bill.getBds();

        for (BillDetail bd : bds)
        {
            QueryBuilder qb = new QueryBuilder(Stock.class)//
                    .addWhereCondition("material=?", bd.getMaterial())//
                    .addWhereCondition("depotId=?", bill.getDepotId());//

            List<Stock> ss = stockDao.findByQueryBuilder(qb);
            Stock stock = null;

            if (ss != null && ss.size() > 0)
            {
                stock = ss.get(0);
            }

            // 如果库存表中没有这条记录则新插入
            if (stock == null)
            {
                stock = new Stock();
                stock.setCount(bd.getCount());
                stock.setInPrice(bd.getPrice());
                stock.setDepotId(bill.getDepotId());
                stock.setTotalPrice(ExactValue.multiply(bd.getCount(), bd.getPrice()));
                stock.setMaterial(bd.getMaterial());
                stockDao.save(stock);
            }
            // 如果有这条记录则更新
            else
            {
                stock.setCount(bd.getCount() + stock.getCount());
                stock.setTotalPrice(ExactValue.add(stock.getTotalPrice(), ExactValue.multiply(bd.getCount(), bd.getPrice())));
                stock.setInPrice(ExactValue.divide(stock.getTotalPrice(), stock.getCount()));
                stockDao.update(stock);
            }
        }
        
        return bill.getId();
    }
}
