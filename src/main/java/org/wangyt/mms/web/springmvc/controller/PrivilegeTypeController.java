package org.wangyt.mms.web.springmvc.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Privilege;
import org.wangyt.mms.domain.PrivilegeType;
import org.wangyt.mms.service.PrivilegeTypeService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.EasyuiTreeNode;

/**
 * 权限分类管理控制器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-15 下午1:48:37
 * 
 * @version $Rev: 144 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/PrivilegeTypeController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("privilegeTypeController")
public class PrivilegeTypeController
{
    @Autowired
    private PrivilegeTypeService privilegeTypeService;

    /**
     * 权限分类管理index
     * 
     * @return
     */
    @RequestMapping(value = "sys/privilegeType/index")
    public String index()
    {
        return "sys/privilegetype/index";
    }

    /**
     * 权限类型数据集Json视图
     * 
     * @param dataGridPage
     * @return
     */
    @RequestMapping(value = "sys/privilegeType/getItems")
    @ResponseBody
    public List<PrivilegeType> getItems()
    {
        QueryBuilder qb = new QueryBuilder(PrivilegeType.class);
        return privilegeTypeService.findByQueryBuilder(qb);
    }

    /**
     * 保存权限分类
     * 
     * @param pt
     * @return
     */
    @RequestMapping(value = "sys/privilegeType/save")
    @ResponseBody
    public AjaxInfoJson save(PrivilegeType pt)
    {
        Integer itg = privilegeTypeService.findMaximumSort();
        if (itg == null)
        {
            pt.setSort(0);
        } else
        {
            pt.setSort(itg + 1);
        }
        privilegeTypeService.save(pt);

        return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }

    /**
     * 删除权限分类
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "sys/privilegeType/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id)
    {
        if (id != null && !("".equals(id)))
        {
            PrivilegeType pt = privilegeTypeService.findById(id);
            Set<Privilege> privileges = pt.getPrivileges();
            // 通过关系维护端Privilege 来解除关联关系(实际上是在删除前先执行    update privilege set privilege_type_id=null where privilege_type_id=?)
            for (Privilege p : privileges)
            {
                p.setPrivilegeType(null);
            }
            privilegeTypeService.delete(pt);
        }

        return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }

    /**
     * 更新权限分类
     * 
     * @param id
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "sys/privilegeType/edit")
    @ResponseBody
    public AjaxInfoJson edit(PrivilegeType pt) throws IllegalAccessException, InvocationTargetException
    {
        // System.out.println("id: " + pt.getId() + ",name: " + pt.getPrivilegeTypeName() + ",sort: " + pt.getSort() + ",des: " + pt.getDescription());
        PrivilegeType p_pt = privilegeTypeService.findById(pt.getId());
        pt.setPrivileges(p_pt.getPrivileges());
        BeanUtils.copyProperties(p_pt, pt);

        privilegeTypeService.update(p_pt);
        return AjaxInfoJson.getAjaxInfoJson(true, "更新成功!");
    }

    /**
     * 得到权限分类tree
     * 
     * @return
     */
    @RequestMapping(value = "sys/privilegeType/getTypes")
    @ResponseBody
    public List<EasyuiTreeNode> getTypes()
    {
        List<EasyuiTreeNode> allTree = new ArrayList<EasyuiTreeNode>();

        // 设置一个根节点
        EasyuiTreeNode root = new EasyuiTreeNode();
        root.setText("权限分类");

        // 得到所有的权限类型集合
        List<PrivilegeType> pts = null;
        QueryBuilder qb = new QueryBuilder(PrivilegeType.class);
        pts = privilegeTypeService.findByQueryBuilder(qb);

        // 将权限类型转换成json模型
        List<EasyuiTreeNode> ptsTree = new ArrayList<EasyuiTreeNode>();
        for (PrivilegeType pt : pts)
        {
            EasyuiTreeNode node = new EasyuiTreeNode();
            node.setId(pt.getId());
            node.setText(pt.getPrivilegeTypeName());
            ptsTree.add(node);
        }
        root.setChildren(ptsTree);
        allTree.add(root);

        return allTree;
    }
}
