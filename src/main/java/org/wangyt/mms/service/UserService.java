package org.wangyt.mms.service;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.User;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:38:02
 * 
 * @version $Rev: 93 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/UserService.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface UserService extends BaseService<User>
{
    /**
     * 通过用户名和密码查询一个用户
     * 
     * @return
     */
    User findUserByLoginNameAndPassword(String loginName, String password);
    
    /**
     * 通过用户名查询一个用户
     * 
     */
    User finUserByLoginName(String loginName);
    
}
