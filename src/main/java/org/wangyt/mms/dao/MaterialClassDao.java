package org.wangyt.mms.dao;

import java.util.List;

import org.wangyt.mms.base.BaseDao;
import org.wangyt.mms.domain.MaterialClass;

public interface MaterialClassDao extends BaseDao<MaterialClass> {
	/**
	 * 查询没有上级分类的分类
	 * @return
	 */
	List<MaterialClass> findMaterialClassParentIsNull();
}
