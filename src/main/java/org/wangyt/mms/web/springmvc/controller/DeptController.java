package org.wangyt.mms.web.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Dept;
import org.wangyt.mms.service.DeptService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.EasyuiTreeNode;

/**
 * 部门管理控制器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 下午7:05:26
 * 
 * @version $Rev: 95 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/DeptController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("deptController")
public class DeptController
{
    @Autowired
    private DeptService deptService;

    /**
     * 部门管理
     */
    @RequestMapping("sys/dept/index")
    public String index()
    {
        return "sys/dept/index";
    }

    /**
     * 保存部门
     * 
     * @return
     */
    @RequestMapping(value = "sys/dept/save")
    @ResponseBody
    public AjaxInfoJson save(Dept dept)
    {
        String deptId = dept.getParent().getId();
        if (deptId != null && !("".equals(deptId.trim())))
        {
            dept.setParent(deptService.findById(deptId));
        } else
        {
            dept.setParent(null);
        }

        deptService.save(dept);
        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }

    /**
     * 修改部门 直线先修改部门名称
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "sys/dept/edit")
    @ResponseBody
    public AjaxInfoJson edit(Dept dept)
    {
        Dept p_dept = deptService.findById(dept.getId());
        p_dept.setDeptName(dept.getDeptName());
        deptService.update(p_dept);
        return AjaxInfoJson.getAjaxInfoJson(true, "修改成功!");
    }
    
    /**
     * 删除部门信息
     * @param id
     * @return
     */
    @RequestMapping(value="sys/dept/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id){
    	if( id != null && !("").equals(id)){
    		deptService.delete(deptService.findById(id));
    	}
    	return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }

    /**
     * 得到部门tree
     * 
     * @return
     */
    @RequestMapping(value = "sys/dept/getDepts")
    @ResponseBody
    public List<EasyuiTreeNode> getDepts(String id)
    {
        System.out.println("id: " + id);
        List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
        List<Dept> depts = null;

        if (id != null && !("".equals(id.trim())))
        {
            QueryBuilder qb = new QueryBuilder(Dept.class)//
                    .addWhereCondition("parent=?", deptService.findById(id));

            depts = deptService.findByQueryBuilder(qb);
        } else
        {
            depts = deptService.findDeptParentIsNull();
        }

        for (Dept d : depts)
        {
            EasyuiTreeNode node = new EasyuiTreeNode();
            node.setId(d.getId());
            node.setText(d.getDeptName());

            if (d.getChildren() != null && d.getChildren().size() > 0)
            {
                node.setState("closed");
            }
            tree.add(node);
        }
        return tree;
    }

}
