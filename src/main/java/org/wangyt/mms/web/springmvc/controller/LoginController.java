package org.wangyt.mms.web.springmvc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.UserService;

/**
 * 登录 Controller. <BR>
 * 
 * @author WANG YONG TAO
 * 
 * @date 2016-9-23 17:57:28
 * 
 * @version $Rev$
 * 
 * @Copyright (c) Copyright 2016 WANGYT, All rights reserved.
 * 
 */
@Controller("loginController")
public class LoginController {

  @Autowired
  private UserService userService;
  
  /**
   * 转到登录页面.
   * 
   * @return
   */
  @RequestMapping(value = "login/ui")
  public String loginUI() {
//    return "home/loginUI";
    return "home/loginsso";
  }

  /**
   * login method. <BR>
   * 
   * @param session
   * @param request
   * @param response
   * @param user
   * @return
   */
  @RequestMapping(value = "login/action")
  public String login(HttpSession session, HttpServletRequest request,
      HttpServletResponse response, User user) {
    if (user.getLoginName() == null || user.getPassword() == null) {
      // return "redirect:ui.mvc";
      return "redirect:login/ui";
    }

    User currentUser = (User) session.getAttribute("currentUser");

    if (currentUser != null) {
      return "home/index";
    } else {
      String loginName = user.getLoginName().trim();
      String password = user.getPassword().trim();

      User p_user =
          userService.findUserByLoginNameAndPassword(loginName, DigestUtils.md5Hex(password));

      if (p_user == null) {
        // 重定向
        return "redirect:ui.mvc";
      } else {
        // 登录成功 将当前登录用户放入session中
        session.setAttribute("currentUser", p_user);

        // 如果勾选了两周内自动登录
        if (user.isAutoLogin()) {
          Cookie c_login_name = new Cookie("c_login_name", loginName);
          Cookie c_password = new Cookie("c_password", DigestUtils.md5Hex(password));

          c_login_name.setPath(request.getContextPath() + "/");
          c_password.setPath(request.getContextPath() + "/");

          // 设置cookie的有效期为14天
          c_login_name.setMaxAge(14 * 1 * 24 * 3600);
          c_password.setMaxAge(7 * 1 * 24 * 3600);

          response.addCookie(c_login_name);
          response.addCookie(c_password);
        }
      }
    }
    
    Object beforeLoginUrl = session.getAttribute("before_login_url");
    if (beforeLoginUrl != null) {
        if (!"".equals((String) beforeLoginUrl)) {
            session.removeAttribute("before_login_url");
            return "redirect:" + (String) beforeLoginUrl;
        }
    }

    return "home/index";
  }

}
