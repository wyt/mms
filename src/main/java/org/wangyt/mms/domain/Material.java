package org.wangyt.mms.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 物资实体(Material Entity)
 * 
 * @author 王永涛
 * 
 * @date 2012-11-19 下午3:43:00
 * 
 * @version $Rev: 115 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/Material.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_material")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Material implements Serializable
{
    private static final long serialVersionUID = -1111468037208971721L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 物资编码
     */
    @Column
    private String materialNo;

    /**
     * 物资名称
     */
    @Column
    private String materialName;

    /**
     * 物资规格
     */
    @Column
    private String materialSpec;

    /**
     * 计量单位
     */
    @Column
    private String measureUnitId;

    /**
     * 型号
     */
    @Column
    private String materialModel;

    /**
     * 是否需消毒
     */
    @Column
    private String disinfection;

    /**
     * 是否属于高值耗材
     */
    @Column
    private String highValue;

    /**
     * 零售价(有些物资是医院给病人使用的)
     */
    @Column
    private float sellPrice;

    /**
     * 品牌
     */
    @Column
    private String materialBrand;

    /**
     * 生产厂家
     */
    @Column
    private String factory;

    /**
     * 拼音码
     */
    @Column
    private String pym;

    /**
     * 该物资库存上限
     */
    @Column
    private int upperLimit;

    /**
     * 该物资库存下限
     */
    @Column
    private int lowerLimit;

    /**
     * 备注
     */
    //@Column(columnDefinition = "text")
    @Column
    private String remark;

    /**
     * 所属分类
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "material_class_id")
    private MaterialClass mc;
    
    @Transient
    private MeasureUnit measureUnit;
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMaterialNo()
    {
        return materialNo;
    }

    public void setMaterialNo(String materialNo)
    {
        this.materialNo = materialNo;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialSpec()
    {
        return materialSpec;
    }

    public void setMaterialSpec(String materialSpec)
    {
        this.materialSpec = materialSpec;
    }

    public String getMeasureUnitId()
    {
        return measureUnitId;
    }

    public void setMeasureUnitId(String measureUnitId)
    {
        this.measureUnitId = measureUnitId;
    }

    public String getMaterialModel()
    {
        return materialModel;
    }

    public void setMaterialModel(String materialModel)
    {
        this.materialModel = materialModel;
    }

    public String getDisinfection()
    {
        return disinfection;
    }

    public void setDisinfection(String disinfection)
    {
        this.disinfection = disinfection;
    }

    public String getHighValue()
    {
        return highValue;
    }

    public void setHighValue(String highValue)
    {
        this.highValue = highValue;
    }

    public float getSellPrice()
    {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice)
    {
        this.sellPrice = sellPrice;
    }

    public String getMaterialBrand()
    {
        return materialBrand;
    }

    public void setMaterialBrand(String materialBrand)
    {
        this.materialBrand = materialBrand;
    }

    public String getFactory()
    {
        return factory;
    }

    public void setFactory(String factory)
    {
        this.factory = factory;
    }

    public String getPym()
    {
        return pym;
    }

    public void setPym(String pym)
    {
        this.pym = pym;
    }

    public int getUpperLimit()
    {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit)
    {
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit()
    {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit)
    {
        this.lowerLimit = lowerLimit;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public MaterialClass getMc()
    {
        return mc;
    }

    public void setMc(MaterialClass mc)
    {
        this.mc = mc;
    }

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}
    
    
}
