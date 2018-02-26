package org.wangyt.mms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 仓库实体
 * 
 * @author 王永涛
 * 
 * @date 2012-11-19 上午8:03:12
 * 
 * @version $Rev: 84 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/Depot.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_depot")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Depot implements Serializable
{
    private static final long serialVersionUID = 5011904633224456692L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;
    /**
     * 仓库编码
     */
    @Column
    private String depotCode;
    /**
     * 仓库名称
     */
    @Column
    private String depotName;
    /**
     * 说明
     */
    @Column
    @Lob
    private String depotDirections;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDepotCode()
    {
        return depotCode;
    }

    public void setDepotCode(String depotCode)
    {
        this.depotCode = depotCode;
    }

    public String getDepotName()
    {
        return depotName;
    }

    public void setDepotName(String depotName)
    {
        this.depotName = depotName;
    }

    public String getDepotDirections()
    {
        return depotDirections;
    }

    public void setDepotDirections(String depotDirections)
    {
        this.depotDirections = depotDirections;
    }

}
