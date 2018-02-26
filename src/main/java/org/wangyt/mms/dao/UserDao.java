package org.wangyt.mms.dao;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.domain.User;

/**
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:35:48
 * 
 * @version $Rev: 93 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/dao/UserDao.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public interface UserDao extends BaseDao<User>
{
    User getUserByLoginNameAndPassword(String loginName, String password);
    
    User getUserByLoginName(String loginName);
    
}
