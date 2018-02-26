package org.wangyt.mms.web.springmvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wangyt.mms.domain.Material;
import org.wangyt.mms.domain.MaterialClass;
import org.wangyt.mms.domain.MaterialImages;
import org.wangyt.mms.domain.MeasureUnit;
import org.wangyt.mms.service.MaterialClassService;
import org.wangyt.mms.service.MaterialImagesService;
import org.wangyt.mms.service.MaterialService;
import org.wangyt.mms.service.MeasureUnitService;
import org.wangyt.mms.util.UploadFileUtils;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.AjaxInfoJson;
import org.wangyt.mms.web.easyui.model.DataGridPage;
import org.wangyt.mms.web.easyui.model.PageItemsJson;
/**
 * 物资详情
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:09:21
 * 
 * @version $Rev: 133 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/MaterialController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("materialController")
public class MaterialController{
	 @Autowired
	 private MaterialService materialService;
	 @Autowired
	 private MaterialClassService materialClassService;
	 @Autowired
	 private MeasureUnitService measureUnitService;
	 @Autowired
	 private MaterialImagesService materialImagesService;
    /**
     * 物资详情 index
     * 
     * @return
     */
    @RequestMapping(value = "data/material/index")
    public String index()
    {
        return "data/material/index";
    }
    /**
     * 物资实体数据集Json视图
     * @param dataGridPage
     * @param mcId
     * @return
     */
    @RequestMapping(value = "data/material/getItems")
    @ResponseBody
    public PageItemsJson getItems(DataGridPage dataGridPage,String goodTypeId,String q,String code,String name,String spec,String model){
    	QueryBuilder qb = new QueryBuilder(Material.class);
    	if(goodTypeId != null && !("".equals(goodTypeId.trim()))){
    		MaterialClass materialClass=materialClassService.findById(goodTypeId);
    		if(materialClass!=null&&materialClass.getParent()!=null){
	//    		List<MaterialClass> mcList = new ArrayList<MaterialClass>();
	//    		mcList.add(materialClass);
	//    		getMaterialClass(materialClass,mcList);
	//    		StringBuffer value = new StringBuffer();
	//    		for(int i=0;i<mcList.size();i++){
	//    			value.append(",?");
	//    		}
	//     		qb.addWhereCondition("mc in ("+value.substring(1)+")",mcList.toArray());
	    		qb.addWhereCondition("mc=?",materialClass);
	     		queryAddWhere(qb,q,code,name,spec,model);
    		}else{
    			queryAddWhere(qb,q,code,name,spec,model);
    		}
    	}else{
    		queryAddWhere(qb,q,code,name,spec,model);
    	}
    	
    	PageItemsJson pj = materialService.getPageView(qb, dataGridPage.getPage(), dataGridPage.getRows());
    	if(pj!=null&&pj.getRows()!=null&&pj.getRows().size()>0){
    		for(int i=0;i<pj.getRows().size();i++){
    			Material material = (Material) pj.getRows().get(i);
    			MeasureUnit measureUnit = measureUnitService.findById(material.getMeasureUnitId());
    			material.setMeasureUnitId(measureUnit.getUnitName());
    			material.setMeasureUnit(measureUnit);
    		}
    		
    	}
        return pj;
    }
    /**
     * 保存物资实体
     * @param materialClass
     * @return
     */
    @RequestMapping(value = "data/material/save")
    @ResponseBody
    public AjaxInfoJson save(Material material){
   	 	if(material!=null&&material.getMc()!=null&&material.getMc().getId()!=null&&!material.getMc().getId().equals("")){
   	 		material.setMc(materialClassService.findById(material.getMc().getId()));
        }else{
        	material.setMc(null);
        }
   	 	materialService.save(material);
    	return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }
    /**
     * 查看物资实体
     * @return
     */
    @RequestMapping(value = "data/material/detail")
    public ModelAndView detail(ModelMap modelMap,String id){
    	Material material=materialService.findById(id);
    	material.setMeasureUnit(measureUnitService.findById(material.getMeasureUnitId()));
    	modelMap.put("material",material);
    	QueryBuilder qb = new QueryBuilder(MaterialImages.class);
    	qb.addWhereCondition("material=?",material);
    	List<MaterialImages> mi = materialImagesService.findByQueryBuilder(qb);
    	modelMap.put("miList", mi);
        return new ModelAndView("data/material/_detail", modelMap);
    }
    /**
     * 修改物资实体
     * @param material
     * @return
     */
    @RequestMapping(value = "data/material/edit")
    @ResponseBody
    public AjaxInfoJson edit(Material material){
    	if(material!=null&&material.getMc()!=null&&material.getMc().getId()!=null&&!material.getMc().getId().equals("")){
   	 		material.setMc(materialClassService.findById(material.getMc().getId()));
        }else{
        	material.setMc(null);
        }
   	 	materialService.update(material);
    	return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }
    
    /**
     * 删除物资分类
     * @param id
     * @return
     */
    @RequestMapping(value="data/material/delete")
    @ResponseBody
    public AjaxInfoJson delete(String id){
    	if( id != null && !("").equals(id)){
    		materialService.delete(materialService.findById(id));
    	}
    	return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }
    /**
     * 添加图片
     * @param material
     * @return
     */
    @RequestMapping(value = "data/material/saveFile")
    @ResponseBody
    public AjaxInfoJson saveFile(@RequestParam("uploadFile")MultipartFile file,String mid,HttpServletRequest request,MaterialImages mi){
		try {
			String basePath = request.getSession().getServletContext().getRealPath("/images");//
	    	String newFileName = UploadFileUtils.saveUploadImage(file.getInputStream(), basePath, getExtensionName(file.getOriginalFilename()));
	    	mi.setMaterial(materialService.findById(mid));
	    	mi.setImageFullName(newFileName.substring(newFileName.indexOf("images")));//图片完整名
	    	mi.setImageName(getFileNameNoEx(file.getOriginalFilename()));//图片名称(不包含扩展名)
	    	mi.setExtName(getExtensionName(file.getOriginalFilename()));//图片扩展名
	    	mi.setImageSize(String.valueOf(file.getSize()));
	    	mi.setSavePath(basePath);
	    	materialImagesService.save(mi); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AjaxInfoJson.getAjaxInfoJson(true, "保存成功!");
    }
    /**
     * 根据属查询的递归调用
     * @param materialClass
     * @param mcList
     */
    public void getMaterialClass(MaterialClass materialClass,List<MaterialClass> mcList){
    	if(materialClass!=null&&materialClass.getChildren()!=null&&materialClass.getChildren().size()>0){
    		for(MaterialClass mc:materialClass.getChildren()){
    			getMaterialClass(mc,mcList);
    		}
    	}else{
    		mcList.add(materialClass);
    	}
    }
    /** 
     * Java文件操作 获取文件扩展名 
     * 
     *  Created on: 2011-8-2 
     *      Author: blueeagle 
     */  
    public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    }   
    /** 
     * Java文件操作 获取不带扩展名的文件名 
     * 
     *  Created on: 2011-8-2 
     *      Author: blueeagle 
     */  
    public static String getFileNameNoEx(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length()))) {   
                return filename.substring(0, dot);   
            }   
        }   
        return filename;   
    }   
    /**
     * 生成查询条件
     */
    public void queryAddWhere(QueryBuilder qb,String q,String code,String name,String spec,String model){
    	if(q != null && !("".equals(q.trim()))){
    	   qb.addWhereCondition("materialNo like ? or materialName like ? ", new Object[] { "%" + q + "%", "%" + q + "%" });
    	}
    	if (code != null && !"".equals(code)){
           qb.addWhereCondition("materialNo like ?", "%" + code + "%");
        }
    	if (name != null && !"".equals(name)){
           qb.addWhereCondition("materialName like ?", "%" + name + "%");
        }
    	if (spec != null && !"".equals(spec)){
           qb.addWhereCondition("materialSpec like ?", "%" + spec + "%");
        }
    	if (model != null && !"".equals(model)){
           qb.addWhereCondition("materialModel like ?", "%" + model + "%");
        }
    }
    
    /**
     * 删除物资图片
     * @param id
     * @return
     */
    @RequestMapping(value="data/material/deleteImage")
    @ResponseBody
    public AjaxInfoJson deleteImage(String id){
    	if( id != null && !("").equals(id)){
    		materialImagesService.delete(materialImagesService.findById(id));
    	}
    	return AjaxInfoJson.getAjaxInfoJson(true, "删除成功!");
    }
}
