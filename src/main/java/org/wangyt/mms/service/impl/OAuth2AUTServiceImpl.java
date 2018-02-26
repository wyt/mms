package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.OAuth2AUTDao;
import org.wangyt.mms.domain.OAuth2AUT;
import org.wangyt.mms.service.OAuth2AUTService;

/**
 * @author WANG YONG TAO
 *
 */
@Service("oAuth2AUTService")
public class OAuth2AUTServiceImpl extends BaseServiceImpl<OAuth2AUT> implements OAuth2AUTService {

    @Autowired
    private OAuth2AUTDao oAuth2AUTDao;

    @Override
    public BaseDao<OAuth2AUT> getEntiryDao() {
        return oAuth2AUTDao;
    }

}
