package org.wangyt.mms.web.springmvc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登出 Controller. <BR>
 * 
 * @author WANG YONG TAO
 * 
 * @date 2016年9月23日18:18:08
 * 
 * @version $Rev$
 * 
 * @Copyright (c) Copyright 2016 WANGYT, All rights reserved.
 * 
 */
@Controller("logoutController")
public class LogoutController {

  /**
   * 注销登录.
   * 
   * @param session
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("logout/action")
  public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    
    // 清除session中的user
    session.removeAttribute("currentUser");
    // 销毁session
    session.invalidate();

    // 清除cookie中保存的用户名和密码
    Cookie c_login_name = new Cookie("c_login_name", "");
    Cookie c_password = new Cookie("c_password", "");

    c_login_name.setPath(request.getContextPath() + "/");
    c_password.setPath(request.getContextPath() + "/");
    c_login_name.setMaxAge(0);
    c_password.setMaxAge(0);
    response.addCookie(c_login_name);
    response.addCookie(c_password);

    return "redirect:/login/ui.mvc";
  }

}
