package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.UserDao;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.UserService;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:39:22
 * 
 * @version $Rev: 93 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/service/impl/UserServiceImpl.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public BaseDao<User> getEntiryDao() {
        return userDao;
    }

    @Override
    public User findUserByLoginNameAndPassword(String loginName, String password) {
        return userDao.getUserByLoginNameAndPassword(loginName, password);
    }

    @Override
    public User finUserByLoginName(String loginName) {
        return userDao.getUserByLoginName(loginName);
    }

}
