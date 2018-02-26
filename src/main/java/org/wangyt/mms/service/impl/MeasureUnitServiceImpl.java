package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.MeasureUnitDao;
import org.wangyt.mms.domain.MeasureUnit;
import org.wangyt.mms.service.MeasureUnitService;

/**
 * 计量单位service
 * @author ldd
 *
 */
@Service("unitService")
public class MeasureUnitServiceImpl extends BaseServiceImpl<MeasureUnit> implements MeasureUnitService {

	@Autowired
    private MeasureUnitDao unitDao;
	
	@Override
	public BaseDao<MeasureUnit> getEntiryDao() {
		return unitDao;
	}

}
