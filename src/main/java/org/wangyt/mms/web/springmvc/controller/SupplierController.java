package org.wangyt.mms.web.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Supplier;
import org.wangyt.mms.service.SupplierService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.DataGridPage;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * 供应商管理
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:13:19
 * 
 * @version $Rev: 102 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/SupplierController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("supplierController")
public class SupplierController
{
    @Autowired
    private SupplierService supService;

    /**
     * 供应商管理 index
     * 
     * @return
     */
    @RequestMapping(value = "data/supplier/index")
    public String index()
    {
        return "data/supplier/index";
    }

    /**
     * 
     * @param dataGridPage
     * @return
     */

    /**
     * 供应商数据集JSON视图
     * 
     * @param dataGridPage
     * @return
     */
    @RequestMapping(value = "data/supplier/getItems")
    @ResponseBody
    public PageItemsJson getItems(DataGridPage dataGridPage, String q, String code, String name, String addr)
    {
        QueryBuilder qb = new QueryBuilder(Supplier.class);
        if (q != null && !"".equals(q))
        {
            qb.addWhereCondition("code like ? or shortName like ?  or help like ?", new Object[] { "%" + q + "%", "%" + q + "%", "%" + q + "%" });
        }

        if (code != null && !"".equals(code))
        {
            qb.addWhereCondition("code like ?", "%" + code + "%");
        }
        if (name != null && !"".equals(name))
        {
            qb.addWhereCondition("shortName like ?", "%" + name + "%");
        }
        if (addr != null && !"".equals(addr))
        {
            qb.addWhereCondition("addr like ?", "%" + addr + "%");
        }

        return supService.getPageView(qb, dataGridPage.getPage(), dataGridPage.getRows());
    }

    /**
     * 保存供应商信息
     * 
     * @param sup
     * @return
     */
    @RequestMapping(value = "data/supplier/save")
    @ResponseBody
    public AjaxInfoJson save(Supplier sup)
    {
        supService.save(sup);
        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }

    /**
     * 更新供应商信息
     * 
     * @param sup
     * @return
     */
    @RequestMapping(value = "data/supplier/edit")
    @ResponseBody
    public AjaxInfoJson edit(Supplier sup)
    {
        supService.update(sup);
        return AjaxInfoJson.getAjaxInfoJson(true, "更新成功!");
    }

    /**
     * 删除供应商
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "data/supplier/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id)
    {
        if (id != null && !("").equals(id))
        {
            supService.delete(supService.findById(id));
        }
        return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }

}
