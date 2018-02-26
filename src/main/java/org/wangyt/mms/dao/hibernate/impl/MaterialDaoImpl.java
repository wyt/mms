package org.wangyt.mms.dao.hibernate.impl;

import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.MaterialDao;
import org.wangyt.mms.domain.Material;

@Repository("MaterialDao")
public class MaterialDaoImpl extends BaseDaoImpl<Material> implements MaterialDao {

}
