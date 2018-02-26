package org.wangyt.mms.web.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.MeasureUnit;
import org.wangyt.mms.service.MeasureUnitService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;

/**
 * 计量单位
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:07:35
 * 
 * @version $Rev: 84 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/MeasureUnitController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("measureUnitController")
public class MeasureUnitController
{
    @Autowired
    private MeasureUnitService unitService;

    /**
     * 计量单位 index
     * 
     * @return
     */
    @RequestMapping(value = "data/measureunit/index")
    public String index()
    {
        return "data/measureunit/index";
    }

    /**
     * 计量单位数据集Json视图
     * 
     * @return
     */
    @RequestMapping(value = "data/measureUnit/getItems")
    @ResponseBody
    public List<MeasureUnit> getItems()
    {
        QueryBuilder qb = new QueryBuilder(MeasureUnit.class);
        return unitService.findByQueryBuilder(qb);
    }

    /**
     * 保存计量单位
     * 
     * @param unit
     * @return
     */
    @RequestMapping(value = "data/measureUnit/save")
    @ResponseBody
    public AjaxInfoJson save(MeasureUnit unit)
    {
        unitService.save(unit);
        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }

    /**
     * 删除计量单位
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "data/measureUnit/delete")
    @ResponseBody
    public AjaxInfoJson delelte(String id)
    {
        if (id != null && !("").equals(id))
        {
            unitService.delete(unitService.findById(id));
        }
        return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }

    /**
     * 更新计量单位
     * 
     * @param unit
     * @return
     */
    @RequestMapping(value = "data/measureUnit/edit")
    @ResponseBody
    public AjaxInfoJson edit(MeasureUnit unit)
    {
        // MeasureUnit m_mu = unitService.findById(unit.getId());
        unitService.update(unit);
        return AjaxInfoJson.getAjaxInfoJson(true, "更新成功!");
    }

    /**
     * 查询所有的计量单位
     * 
     * @return
     */
    @RequestMapping(value = "data/measureUnit/findAllUnit")
    @ResponseBody
    public List<MeasureUnit> findAllUnit()
    {
        QueryBuilder qb = new QueryBuilder(MeasureUnit.class);
        return unitService.findByQueryBuilder(qb);
    }

}
