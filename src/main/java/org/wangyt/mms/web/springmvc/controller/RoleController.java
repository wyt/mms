package org.wangyt.mms.web.springmvc.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Privilege;
import org.wangyt.mms.domain.Role;
import org.wangyt.mms.service.PrivilegeService;
import org.wangyt.mms.service.RoleService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.DataGridPage;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * 角色管理控制器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 下午6:39:30
 * 
 * @version $Rev: 63 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/RoleController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("roleController")
public class RoleController
{
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 角色管理index
     */
    @RequestMapping(value = "sys/role/index")
    public String index()
    {
        return "sys/role/index";
    }

    /**
     * 保存角色
     * 
     * @param role
     */
    @RequestMapping(value = "sys/role/save")
    @ResponseBody
    public AjaxInfoJson save(Role role, String privilegeIds)
    {
        System.out.println("privilegeIds: " + privilegeIds);
        AjaxInfoJson aij = AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
        try
        {
            Integer itg = roleService.findMaximumSort();
            if (itg == null)
            {
                role.setSort(0);
            } else
            {
                role.setSort(itg + 1);
            }

            // 设置角色对应的权限
            if (privilegeIds != null && !("".equals(privilegeIds)))
            {
                Set<Privilege> privileges = new HashSet<Privilege>();
                String[] privilegeIds_array = privilegeIds.split(",");
                for (String privilegeId : privilegeIds_array)
                {
                    privileges.add(privilegeService.findById(privilegeId));
                }
                role.setPrivileges(privileges);
            }

            roleService.save(role);
        } catch (Exception e)
        {
            aij = AjaxInfoJson.getAjaxInfoJson(false, "保存失败!");
            e.printStackTrace();
        } finally
        {
            return aij;
        }

    }

    /**
     * 修改角色
     * 
     * @param role
     * @param privilegeIds
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "sys/role/edit")
    @ResponseBody
    public AjaxInfoJson edit(Role role, String privilegeIds) throws IllegalAccessException, InvocationTargetException
    {
        // System.out.println("roleId: " + role.getId() + ",privilegeIds: " + privilegeIds);
        Role p_role = roleService.findById(role.getId());

        // 设置更改后的权限
        if (privilegeIds != null && !("".equals(privilegeIds)))
        {
            Set<Privilege> privileges = new HashSet<Privilege>();
            String[] privilegeIds_array = privilegeIds.split(",");
            for (String privilegeId : privilegeIds_array)
            {
                privileges.add(privilegeService.findById(privilegeId));
            }
            role.setPrivileges(privileges);
        }
        // 设置对应该角色的user还是原来的user
        role.setSort(p_role.getSort());
        role.setUsers(p_role.getUsers());
        BeanUtils.copyProperties(p_role, role);
        roleService.update(p_role);

        return AjaxInfoJson.getAjaxInfoJson(true, "更新成功 !");
    }

    /**
     * 角色数据集Json视图(分页，按照sort升序排列)
     * 
     * @param dataGridPage
     * @return
     */
    @RequestMapping(value = "sys/role/getItems")
    @ResponseBody
    public PageItemsJson getItems(DataGridPage dataGridPage)
    {
        // System.out.println("currentPage: " + dataGridPage.getPage() + ",pageSize: " + dataGridPage.getRows());
        QueryBuilder qb = new QueryBuilder(Role.class).addOrderProperty("sort", false);
        return roleService.getPageView(qb, dataGridPage.getPage(), dataGridPage.getRows());
    }

    /**
     * 角色数据集Json视图(为分页)
     * 
     * @return
     */
    @RequestMapping(value = "sys/role/getItems2")
    @ResponseBody
    public List<Role> getItems()
    {
        QueryBuilder qb = new QueryBuilder(Role.class);
        return roleService.findByQueryBuilder(qb);
    }

    /**
     * 删除角色
     * 
     * @param id
     */
    @RequestMapping(value = "sys/role/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id)
    {
        if (id != null && !("".equals(id)))
        {
            roleService.delete(roleService.findById(id));
        }

        return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }

    /**
     * 为记录排序(适用于数据量小的表)
     * 
     * @param id
     * @param dir
     * @return
     */
    @RequestMapping(value = "sys/role/move")
    @ResponseBody
    public AjaxInfoJson move(String id, String dir)
    {
        // 移动角色对象
        Role item = roleService.findById(id);
        // 被移动角色对象
        Role target = null;
        QueryBuilder qb = null;
        if ("down".equals(dir))
        {
            qb = new QueryBuilder(Role.class)//
                    .addWhereCondition("sort>?", item.getSort())//
                    .addOrderProperty("sort", false); // false asc order.
        } else
        {
            qb = new QueryBuilder(Role.class)//
                    .addWhereCondition("sort<?", item.getSort())//
                    .addOrderProperty("sort", true); // true desc order.
        }

        List<Role> roles = roleService.findByQueryBuilder(qb);
        if (roles != null && roles.size() > 0)
        {
            target = roles.get(0);
        }

        if (target != null)
        {
            // 交换排序号
            Integer tmp = target.getSort();
            target.setSort(item.getSort());
            roleService.update(target);
            item.setSort(tmp);
            roleService.update(item);
        }
        // 不需要跳转到某个页面
        return null;
    }
}
