package org.wangyt.mms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.wangyt.mms.util.date.JsonDateSerializer;
import org.wangyt.mms.util.date.JsonDateSerializer2;

/**
 * 单据实体
 * 
 * @author 王永涛
 * 
 * @date 2012-11-20 上午10:28:48
 * 
 * @version $Rev: 117 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/Bill.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_bill")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Bill implements Serializable
{
    private static final long serialVersionUID = 5764607190219981698L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 单据编号
     */
    @Column
    private String billNo;

    /**
     * 单据类型(入库单、出库单、还是退货单等)
     */
    @Column
    private String billType;

    /**
     * 往来单位(供应商或领用部门id)
     */
    @Column
    private String intercourseId;

    @Column
    private String intercourseName;

    /**
     * 仓库id
     */
    @Column
    private String depotId;

    @Column
    private String depotName;

    /**
     * 另一个仓库id(物资要从一个仓库迁移到另一个仓库)
     */
    @Column
    private String depot2Id;

    @Column
    private String depot2Name;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 单据上涉及到物资的总数量
     */
    @Column
    private Integer billCount;

    /**
     * 单据上涉及到物资的总金额
     */
    @Column
    private double billCost;

    /**
     * 制单人
     */
    @Column
    private String userId;

    /**
     * 制单时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date makeBillDate;

    /**
     * 单据日期
     */
    @Temporal(TemporalType.DATE)
    @Column
    private Date billDate;

    /**
     * 删除标记(0表示正常显示/1表示假设删除)
     */
    @Column
    private Integer deleteFlag;

    /**
     * 与单据内容(BillDetail)一对多
     */
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "bill")
    private List<BillDetail> bds = new ArrayList<BillDetail>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBillNo()
    {
        return billNo;
    }

    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillType()
    {
        return billType;
    }

    public void setBillType(String billType)
    {
        this.billType = billType;
    }

    public String getIntercourseId()
    {
        return intercourseId;
    }

    public void setIntercourseId(String intercourseId)
    {
        this.intercourseId = intercourseId;
    }

    public String getDepotId()
    {
        return depotId;
    }

    public void setDepotId(String depotId)
    {
        this.depotId = depotId;
    }

    public String getDepot2Id()
    {
        return depot2Id;
    }

    public void setDepot2Id(String depot2Id)
    {
        this.depot2Id = depot2Id;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getBillCount()
    {
        return billCount;
    }

    public void setBillCount(Integer billCount)
    {
        this.billCount = billCount;
    }

    public double getBillCost()
    {
        return billCost;
    }

    public void setBillCost(double billCost)
    {
        this.billCost = billCost;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getMakeBillDate()
    {
        return makeBillDate;
    }

    public void setMakeBillDate(Date makeBillDate)
    {
        this.makeBillDate = makeBillDate;
    }

    @JsonSerialize(using = JsonDateSerializer2.class)
    public Date getBillDate()
    {
        return billDate;
    }

    public void setBillDate(Date billDate)
    {
        this.billDate = billDate;
    }

    public Integer getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @JsonIgnore
    public List<BillDetail> getBds()
    {
        return bds;
    }

    @JsonIgnore
    public void setBds(List<BillDetail> bds)
    {
        this.bds = bds;
    }

    public String getIntercourseName()
    {
        return intercourseName;
    }

    public void setIntercourseName(String intercourseName)
    {
        this.intercourseName = intercourseName;
    }

    public String getDepotName()
    {
        return depotName;
    }

    public void setDepotName(String depotName)
    {
        this.depotName = depotName;
    }

    public String getDepot2Name()
    {
        return depot2Name;
    }

    public void setDepot2Name(String depot2Name)
    {
        this.depot2Name = depot2Name;
    }

}
