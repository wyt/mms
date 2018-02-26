package org.wangyt.mms.web.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wangyt.mms.domain.MaterialClass;
import org.wangyt.mms.service.MaterialClassService;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.EasyuiTreeNode;

/**
 * 物资分类
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:08:47
 * 
 * @version $Rev: 124 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/MaterialClassController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("materialClassController")
public class MaterialClassController{
	 @Autowired
	 private MaterialClassService materialClassService;
    /**
     * 物资分类 index
     * 
     * @return
     */
    @RequestMapping(value = "data/materialClass/index")
    public String index(){
        return "data/materialClass/index";
    }
    /**
     * 得到物资分类tree
     * @return
     */
    @RequestMapping(value = "data/materialClass/getItems")
    @ResponseBody
    public List<EasyuiTreeNode> getItems(String id){
    	 List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
    	 List<MaterialClass> MCList = null;
    	 if (id != null && !("".equals(id.trim()))){
    		 QueryBuilder qb = new QueryBuilder(MaterialClass.class)//
             .addWhereCondition("parent=?", materialClassService.findById(id));
    		 MCList = materialClassService.findByQueryBuilder(qb);
    	 }else{
    		 MCList= materialClassService.findMaterialClassParentIsNull();
    	 }
    	 
    	 for(MaterialClass mc:MCList){
    		 EasyuiTreeNode node = new EasyuiTreeNode();
    		 node.setId(mc.getId());
             node.setText(mc.getMaterialClassName());
             if (mc.getChildren() != null && mc.getChildren().size() > 0){
                 node.setState("closed");
             }
             tree.add(node);
    	 }
    	 return tree;
    }
    /**
     * 保存物资分类
     * @param materialClass
     * @return
     */
    @RequestMapping(value = "data/materialClass/save")
    @ResponseBody
    public AjaxInfoJson save(MaterialClass materialClass){
    	String mcId = materialClass.getParent().getId();
    	 if(mcId != null && !("".equals(mcId.trim()))){
    		 materialClass.setParent(materialClassService.findById(mcId));
         }else{
        	 materialClass.setParent(null);
         }
    	 materialClassService.save(materialClass);
    	return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }
    /**
     * 修改物资分类
     * @param materialClass
     * @return
     */
    @RequestMapping(value = "data/materialClass/edit")
    @ResponseBody
    public AjaxInfoJson edit(MaterialClass materialClass){
    	MaterialClass p_materialClass = materialClassService.findById(materialClass.getId());
        p_materialClass.setMaterialClassName(materialClass.getMaterialClassName());
        materialClassService.update(p_materialClass);
        return AjaxInfoJson.getAjaxInfoJson(true, "修改成功!");
    }
    /**
     * 删除物资分类
     * @param id
     * @return
     */
    @RequestMapping(value="data/materialClass/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id){
    	if( id != null && !("").equals(id)){
    		materialClassService.delete(materialClassService.findById(id));
    	}
    	return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }
}
