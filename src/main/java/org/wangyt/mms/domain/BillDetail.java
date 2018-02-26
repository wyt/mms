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

import org.hibernate.annotations.GenericGenerator;

/**
 * 单据内容实体
 * 
 * @author 王永涛
 * 
 * @date 2012-11-21 上午10:03:35
 * 
 * @version $Rev: 117 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/BillDetail.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_bill_detail")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class BillDetail implements Serializable
{
    private static final long serialVersionUID = 3253130910187118706L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 一种物资的数量
     */
    @Column
    private Integer count;

    /**
     * 一种物资的单价
     */
    @Column
    private double price;

    /**
     * 一条物资(单向多对一)
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
    @JoinColumn(name = "material_id")
    private Material material;

    /**
     * 与单据多对一
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "bill_id")
    private Bill bill;

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

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    public Bill getBill()
    {
        return bill;
    }

    public void setBill(Bill bill)
    {
        this.bill = bill;
    }

}
