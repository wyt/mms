package org.wangyt.mms.web.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Depot;
import org.wangyt.mms.service.DepotService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;

/**
 * 仓库资料
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:06:47
 * 
 * @version $Rev: 102 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/DepotController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("depotController")
public class DepotController
{
    @Autowired
    private DepotService depotService;

    /**
     * 仓库资料 index
     * 
     * @return
     */
    @RequestMapping(value = "data/depot/index")
    public String index()
    {
        return "data/depot/index";
    }

    /**
     * 仓库资料数据集Json视图
     * 
     * @param dataGridPage
     * @return
     */
    @RequestMapping(value = "data/depot/getItems")
    @ResponseBody
    public List<Depot> getItems()
    {
        QueryBuilder qb = new QueryBuilder(Depot.class);
        return depotService.findByQueryBuilder(qb);
    }

    /**
     * 保存仓库信息
     * 
     * @param depot
     * @return
     */
    @RequestMapping(value = "data/depot/save")
    @ResponseBody
    public AjaxInfoJson save(Depot depot)
    {
        depotService.save(depot);
        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }

    /**
     * 编辑仓库信息
     * 
     * @param dept
     * @return
     */
    @RequestMapping(value = "data/depot/update")
    @ResponseBody
    public AjaxInfoJson edit(Depot depot)
    {
        Depot p_depot = depotService.findById(depot.getId());
        p_depot.setDepotName(depot.getDepotName());
        p_depot.setDepotCode(depot.getDepotCode());
        p_depot.setDepotDirections(depot.getDepotDirections());
        depotService.update(p_depot);
        return AjaxInfoJson.getAjaxInfoJson(true, "修改成功!");
    }

    /**
     * 删除仓库信息
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "data/depot/destroy")
    @ResponseBody
    public AjaxInfoJson delete(String id)
    {
        if (id != null && !("".equals(id)))
        {
            depotService.delete(depotService.findById(id));
        }
        return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }

}
