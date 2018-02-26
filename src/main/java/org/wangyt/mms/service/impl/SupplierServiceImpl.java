package org.wangyt.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.domain.Supplier;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.SupplierDao;
import org.wangyt.mms.service.SupplierService;

@Service("supService")
public class SupplierServiceImpl extends BaseServiceImpl<Supplier> implements SupplierService{

	@Autowired
    private SupplierDao supDao;
	
	@Override
	public BaseDao<Supplier> getEntiryDao() {
		return supDao;
	}

}
