package org.wangyt.mms.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 物资库存表(入库/出库/等操作会影响到库存)
 * 
 * @author 王永涛
 * 
 * @date 2012-11-21 上午11:23:19
 * 
 * @version $Rev: 125 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/Stock.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_stock")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Stock implements Serializable
{
    private static final long serialVersionUID = 1304596494390592068L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 物资的库存数量
     */
    @Column
    private Integer count;

    /**
     * 物资的进价(是一个平均价，同一种物品不同时期价格可能会变)
     */
    @Column
    private double inPrice;

    /**
     * 总价
     */
    @Column
    private double totalPrice;

    /**
     * 存放仓库
     */
    @Column
    private String depotId;

    /**
     * 一条物资(单向多对一)
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
    @JoinColumn(name = "material_id")
    private Material material;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public double getInPrice()
    {
        return inPrice;
    }

    public void setInPrice(double inPrice)
    {
        this.inPrice = inPrice;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getDepotId()
    {
        return depotId;
    }

    public void setDepotId(String depotId)
    {
        this.depotId = depotId;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

}
