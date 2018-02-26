package org.wangyt.mms.service;

import java.util.List;

import org.wangyt.mms.base.BaseService;
import org.wangyt.mms.domain.MaterialClass;

public interface MaterialClassService extends BaseService<MaterialClass> {
	/**
	 * 查询没有上级分类的分类
	 * @return
	 */
	List<MaterialClass> findMaterialClassParentIsNull();
}
