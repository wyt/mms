package org.wangyt.mms.web.springmvc.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Privilege;
import org.wangyt.mms.domain.PrivilegeType;
import org.wangyt.mms.service.PrivilegeService;
import org.wangyt.mms.service.PrivilegeTypeService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.EasyuiTreeNode;

/**
 * 权限管理控制器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 下午7:02:00
 * 
 * @version $Rev: 95 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/PrivilegeController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("privilegeController")
public class PrivilegeController
{
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private PrivilegeTypeService privilegeTypeService;

    /**
     * 权限管理index
     * 
     * @return
     */
    @RequestMapping(value = "sys/privilege/index")
    public String index()
    {
        return "sys/privilege/index";
    }

    /**
     * 权限数据集Json视图
     * 
     * @param privilegeTypeId
     * @return
     */
    @RequestMapping(value = "sys/privilege/getItems")
    @ResponseBody
    public List<Privilege> getItems(String privilegeTypeId)
    {
        // System.out.println("privilegeTypeId: " + privilegeTypeId);
        QueryBuilder qb = null;
        if (privilegeTypeId == null || "".equals(privilegeTypeId))
        {
            qb = new QueryBuilder(Privilege.class);
        } else
        {
            qb = new QueryBuilder(Privilege.class)//
                    .addWhereCondition("privilegeType=?", privilegeTypeService.findById(privilegeTypeId));
        }
        return privilegeService.findByQueryBuilder(qb);
    }

    /**
     * 保存权限
     * 
     * @param p
     * @return
     */
    @RequestMapping(value = "sys/privilege/save")
    @ResponseBody
    public AjaxInfoJson save(Privilege p)
    {
        // System.out.println(p.getPrivilegeType().getId() + "," + p.getPrivilegeType().getPrivilegeTypeName());
        Integer itg = privilegeService.findMaximumSort();
        if (itg == null)
        {
            p.setSort(0);
        } else
        {
            p.setSort(itg + 1);
        }
        p.setPrivilegeType(privilegeTypeService.findById(p.getPrivilegeType().getId()));
        privilegeService.save(p);

        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }

    /**
     * 更新权限
     * 
     * @param p
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "sys/privilege/edit")
    @ResponseBody
    public AjaxInfoJson edit(Privilege p) throws IllegalAccessException, InvocationTargetException
    {
        Privilege p_p = privilegeService.findById(p.getId());
        p.setPrivilegeType(privilegeTypeService.findById(p.getPrivilegeType().getId()));
        BeanUtils.copyProperties(p_p, p);
        privilegeService.update(p_p);

        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }
    
    /**
     * 删除权限
     * @param id
     * @return
     */
    @RequestMapping(value="sys/privilege/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id){
    	if( id != null && !("").equals(id)){
    		privilegeService.delete(privilegeService.findById(id));
    	}
    	return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }
    
    /**
     * 得到权限tree
     * 
     * @return
     */
    @RequestMapping(value = "sys/privilege/getPrivileges")
    @ResponseBody
    public List<EasyuiTreeNode> getPrivileges()
    {
        List<EasyuiTreeNode> allTree = new ArrayList<EasyuiTreeNode>();

        // 设置一个根节点
        EasyuiTreeNode root = new EasyuiTreeNode();
        root.setText("权限分类");

        // 得到所有的权限类型集合
        List<PrivilegeType> pts = null;
        QueryBuilder qb = new QueryBuilder(PrivilegeType.class);
        pts = privilegeTypeService.findByQueryBuilder(qb);

        // 将权限类型(PrivilegeType)转换成json模型
        List<EasyuiTreeNode> ptsTree = new ArrayList<EasyuiTreeNode>();
        for (PrivilegeType pt : pts)
        {
            EasyuiTreeNode privilegeType = new EasyuiTreeNode();
            privilegeType.setId(pt.getId());
            privilegeType.setText(pt.getPrivilegeTypeName());

            // 如果权限类型下有权限
            if (pt.getPrivileges() != null && pt.getPrivileges().size() > 0)
            {
                List<EasyuiTreeNode> privileges = new ArrayList<EasyuiTreeNode>();
                for (Privilege p : pt.getPrivileges())
                {
                    EasyuiTreeNode privilege = new EasyuiTreeNode();
                    // 设置权限的id
                    privilege.setId(p.getId());
                    // 设置权限的名称
                    privilege.setText(p.getPrivilegeName());
                    // 设置勾选框
                    // privilege.setChecked(true);
                    // 设置小图标样式
                    privilege.setIconCls("icon-jnyw_mms_key");
                    privileges.add(privilege);
                }
                privilegeType.setChildren(privileges);
            }
            ptsTree.add(privilegeType);
        }
        root.setChildren(ptsTree);
        allTree.add(root);

        return allTree;
    }
}
