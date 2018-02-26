package org.wangyt.mms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 计量单位实体
 * 
 * @author 王永涛
 * 
 * @date 2012-11-19 上午8:04:38
 * 
 * @version $Rev: 84 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/MeasureUnit.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_measureunit")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class MeasureUnit implements Serializable
{
    private static final long serialVersionUID = -8043247070411451581L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 单位名称
     */
    @Column
    private String unitName;

    /**
     * 单位编码
     */
    @Column
    private String code;

    /**
     * 计量单位描述
     */
    //@Column(columnDefinition = "text")
    @Column
    private String description;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

}
