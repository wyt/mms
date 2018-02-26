package org.wangyt.mms.web.springmvc.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.Dept;
import org.wangyt.mms.domain.Role;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.DeptService;
import org.wangyt.mms.service.RoleService;
import org.wangyt.mms.service.UserService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.DataGridPage;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * 用户管理控制器
 * 
 * @author 王永涛
 * 
 * @date 2012-11-13 下午2:06:30
 * 
 * @version $Rev: 144 $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Controller("userController")
public class UserController {
  
   @Autowired
   private UserService userService;
   @Autowired
   private RoleService roleService;
   @Autowired
   private DeptService deptService;

   /**
    * 用户管理index
    */
   @RequestMapping(value = "sys/user/index")
   public String index() {
      return "sys/user/index";
   }

   /**
    * 个人设置
    * 
    * @return
    */
   @RequestMapping(value = "sys/user/userSet")
   public String userSet() {
      return "sys/user/userSet";
   }

   /**
    * 保存用户
    * 
    * @param user
    * @param roleIds
    */
   @RequestMapping(value = "sys/user/save")
   @ResponseBody
   public AjaxInfoJson save(User user, String roleIds) {
      // 设置所属部门
      if ("".equals(user.getDept().getId())) {
         user.setDept(null);
      }

      // 设置对应的角色
      if (roleIds != null && !("".equals(roleIds))) {
         Set<Role> roles = new HashSet<Role>();
         String[] roleId_array = roleIds.split(",");
         for (String id : roleId_array) {
            roles.add(roleService.findById(id));
         }
         user.setRoles(roles);
      }

      user.setPassword(DigestUtils.md5Hex(user.getPassword()));
      userService.save(user);

      return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
   }

   /**
    * 更新用户
    * 
    * @param user
    * @param roleIds
    * @return
    * @throws InvocationTargetException
    * @throws IllegalAccessException
    */
   @RequestMapping(value = "sys/user/edit")
   @ResponseBody
   public AjaxInfoJson edit(User user, String roleIds) throws IllegalAccessException,
         InvocationTargetException {
      // 设置更改后的部门(持久化对象)
      Dept p_dept = deptService.findById(user.getDept().getId());
      user.setDept(p_dept);
      // 设置对应的角色(持久化对象)
      if (roleIds != null && !("".equals(roleIds))) {
         Set<Role> roles = new HashSet<Role>();
         String[] roleId_array = roleIds.split(",");
         for (String id : roleId_array) {
            roles.add(roleService.findById(id));
         }
         user.setRoles(roles);
      }

      User p_user = userService.findById(user.getId());
      BeanUtils.copyProperties(p_user, user);

      userService.update(p_user);
      return AjaxInfoJson.getAjaxInfoJson(true, "更新成功 !");
   }

   /**
    * 删除用户信息
    * 
    * @param id
    * @return
    */
   @RequestMapping(value = "sys/user/delete")
   @ResponseBody
   public AjaxInfoJson delete(String id) {
      if (id != null && !("").equals(id)) {
         userService.delete(userService.findById(id));
      }
      return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
   }

   /**
    * 用户数据集JSON视图
    * 
    * @param dataGridPage
    * @return
    */
   @RequestMapping(value = "sys/user/getItems")
   @ResponseBody
   public PageItemsJson getItems(DataGridPage dataGridPage, String departmentId) {
      QueryBuilder qb = new QueryBuilder(User.class);
      if (departmentId != null || "".equals(departmentId)) {
         Dept d = deptService.findById(departmentId);
         if (d.getParent() != null) {
            qb.addWhereCondition("dept=?", d);
         }
      }
      // System.out.println("pageNum: " + dataGridPage.getPage() + ",pageSize: " +
      // dataGridPage.getRows());
      return userService.getPageView(qb, dataGridPage.getPage(), dataGridPage.getRows());
   }

   @InitBinder
   public void initBinder(ServletRequestDataBinder binder) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
   }

   /**
    * 查询用户登录名称是否重复
    * 
    * @param name
    * @return
    */
   @RequestMapping(value = "sys/user/queryName")
   @ResponseBody
   public boolean queryName(String name, String editUserId) {
      System.out.println("name: " + name + ",editUserId: " + editUserId);
      // 编辑用户的时候
      if (editUserId != null && !("".equals(editUserId.trim()))) {
         User p_user = userService.findById(editUserId);
         if (p_user.getLoginName().equals(name.trim())) {
            return true;
         } else {
            QueryBuilder qb = new QueryBuilder(User.class);
            qb.addWhereCondition("loginName=?", name);
            List<User> user = userService.findByQueryBuilder(qb);
            if (user != null && user.size() != 0) {
               return false;
            } else {
               return true;
            }
         }
      } else {
         QueryBuilder qb = new QueryBuilder(User.class);
         qb.addWhereCondition("loginName=?", name);
         List<User> user = userService.findByQueryBuilder(qb);
         if (user != null && user.size() != 0) {
            return false;
         } else {
            return true;
         }
      }
   }

   /**
    * 查询原密码
    * 
    * @param pass
    * @return
    */
   @RequestMapping(value = "sys/user/queryPass")
   @ResponseBody
   public boolean queryPass(String pass, HttpSession session) {
      User cuser = (User) session.getAttribute("currentUser");
      String d = DigestUtils.md5Hex(pass);
      if (!d.equals(cuser.getPassword())) {
         return false;
      } else {
         return true;
      }
   }

   /**
    * 个人设置，修改密码
    * 
    * @param u
    * @param session
    * @return
    */
   @RequestMapping(value = "sys/user/editPassword")
   @ResponseBody
   public AjaxInfoJson editPassword(User u, HttpSession session) {
      User cuser = (User) session.getAttribute("currentUser");
      User user = userService.findById(cuser.getId());
      user.setPassword(DigestUtils.md5Hex(u.getPassword()));
      userService.update(user);
      return AjaxInfoJson.getAjaxInfoJson(true, "密码修改成功!");
   }

   /**
    * 初始化用户密码
    * 
    * @param session
    * @return
    */
   @RequestMapping(value = "sys/user/editInitPass")
   @ResponseBody
   public AjaxInfoJson initPassword(String id) {
      User user = userService.findById(id);
      user.setPassword(DigestUtils.md5Hex("1234"));
      userService.update(user);
      return AjaxInfoJson.getAjaxInfoJson(true, "初始化完成!");
   }

}
