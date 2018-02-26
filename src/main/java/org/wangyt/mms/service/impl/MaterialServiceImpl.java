package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.MaterialDao;
import org.wangyt.mms.domain.Material;
import org.wangyt.mms.service.MaterialService;

@Service("MaterialService")
public class MaterialServiceImpl extends BaseServiceImpl<Material> implements MaterialService{
	@Autowired
	private MaterialDao materialDao;
	
	@Override
	public BaseDao<Material> getEntiryDao() {
		return materialDao;
	}
}
