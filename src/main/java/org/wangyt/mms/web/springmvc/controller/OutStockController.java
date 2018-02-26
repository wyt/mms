package org.wangyt.mms.web.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 出库单管理
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:52:42
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/OutStockController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("outStockController")
public class OutStockController
{
    /**
     * 物资出库单index
     * 
     * @return
     */
    @RequestMapping(value = "stock/outstock/index")
    public String index()
    {
        return "stock/outstock/index";
    }
}
