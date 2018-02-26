package org.wangyt.mms.dao.hibernate.impl;

import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.MaterialImagesDao;
import org.wangyt.mms.domain.MaterialImages;

@Repository("materialImagesDao")
public class MaterialImagesDaoImpl extends BaseDaoImpl<MaterialImages>
		implements MaterialImagesDao {

}
