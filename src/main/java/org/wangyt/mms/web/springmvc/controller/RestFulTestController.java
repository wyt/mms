package org.wangyt.mms.web.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.UserService;
import org.wangyt.mms.web.openapi.model.BaseResultDataDTO;

/**
 * SpringMVC实现RestFul风格
 * 
 * <a>http://host:port/root/open/api/restFulTest/getReportData/2020324/</a>
 * 
 * @author 王永涛
 * 
 * @date 2016-7-2 19:51:59
 * 
 * @version $Rev: 63 $
 * 
 * @URL $URL$
 * 
 * @Copyright (c) Copyright 2016 WANGYT, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@RequestMapping("/restFulTest")
@Controller("restFulTestController")
public class RestFulTestController {

	public final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{rptCode}/reportData", method = RequestMethod.GET)
	public String getReportData(Model model,
			@PathVariable("rptCode") String rptCode) {
		return null;
	}

	/**
	 * <a>http://localhost/mms/open/api/restFulTest/402880ed561d626201561d6266e80000/userInfo?access_token=1234ABD1234ABD&oauth_consumer_key=12345&openid=B08D412EEC4000FFC37CAABBDC1234CC&format=json</a>
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{userId}/userInfo", 
				 	method = RequestMethod.GET,
				 	produces = { "application/json;charset=UTF-8" })
	public BaseResultDataDTO<User> getUserInfo(Model model,
			@PathVariable("userId") String userId,HttpServletRequest requset) {
		showQueryString(requset);
		BaseResultDataDTO<User> result;
		try {
			User user = userService.findById(userId);
			result = new BaseResultDataDTO<User>(true, user);
		} catch (Exception e) {
			String errMsg = e.getMessage();
			log.error(errMsg, e);
			result = new BaseResultDataDTO<User>(false, errMsg);
		}

		return result;
	}
	
	public void showQueryString(HttpServletRequest requset) {
		String[] query_string = requset.getQueryString().split("&");
		for (int i = 0; i < query_string.length; i++) {
			log.info(query_string[i]);
		}
	}

}
