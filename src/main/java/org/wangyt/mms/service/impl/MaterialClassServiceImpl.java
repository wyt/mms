package org.wangyt.mms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.base.impl.BaseServiceImpl;
import org.wangyt.mms.dao.MaterialClassDao;
import org.wangyt.mms.domain.MaterialClass;
import org.wangyt.mms.service.MaterialClassService;

@Service("MaterialClassService")
public class MaterialClassServiceImpl extends BaseServiceImpl<MaterialClass> implements MaterialClassService
{
    @Autowired
    private MaterialClassDao materialClassDao;

    @Override
    public BaseDao<MaterialClass> getEntiryDao()
    {
        return materialClassDao;
    }

    @Override
    public List<MaterialClass> findMaterialClassParentIsNull()
    {
        return materialClassDao.findMaterialClassParentIsNull();
    }

}
