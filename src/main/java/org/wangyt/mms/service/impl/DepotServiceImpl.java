package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.DepotDao;
import org.wangyt.mms.domain.Depot;
import org.wangyt.mms.service.DepotService;

@Service("depotService")
public class DepotServiceImpl extends BaseServiceImpl<Depot> implements DepotService
{
	@Autowired
	private DepotDao depotDao;

	@Override
	public BaseDao<Depot> getEntiryDao()
	{
		return depotDao;
	}
}
