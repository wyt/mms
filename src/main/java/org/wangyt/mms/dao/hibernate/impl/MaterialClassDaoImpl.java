package org.wangyt.mms.dao.hibernate.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.wangyt.mms.base.impl.BaseDaoImpl;
import org.wangyt.mms.dao.MaterialClassDao;
import org.wangyt.mms.domain.MaterialClass;

@SuppressWarnings("all")
@Repository("MaterialClassDao")
public class MaterialClassDaoImpl extends BaseDaoImpl<MaterialClass> implements MaterialClassDao {

	@Override
	public List<MaterialClass> findMaterialClassParentIsNull() {
		Session session = this.getSessionFactory().getCurrentSession();
        String hql = "from MaterialClass m where m.parent is null";
        Query query = session.createQuery(hql);
        return query.list();
	}

}
