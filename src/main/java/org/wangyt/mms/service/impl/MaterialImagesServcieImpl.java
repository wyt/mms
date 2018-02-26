package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.MaterialImagesDao;
import org.wangyt.mms.domain.MaterialImages;
import org.wangyt.mms.service.MaterialImagesService;

@Service("materialImagesServcie")
public class MaterialImagesServcieImpl extends BaseServiceImpl<MaterialImages> implements MaterialImagesService {
	@Autowired
	private MaterialImagesDao materialImagesDao;
	
	@Override
	public BaseDao<MaterialImages> getEntiryDao() {
		return materialImagesDao;
	}

}
