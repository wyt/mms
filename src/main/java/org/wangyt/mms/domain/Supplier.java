package org.wangyt.mms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 供应商实体类
 * 
 * @author ldd
 * 
 */
@Entity
@Table(name = "jnyw_mms_supplier")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Supplier implements Serializable
{
    private static final long serialVersionUID = -4697368448288531840L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 编码
     */
    @Column
    private String code;

    /**
     * 类别
     */
    @Column
    private String leibie;

    /**
     * 简称
     */
    @Column
    private String shortName;

    /**
     * 全称
     */
    @Column
    private String fullName;

    /**
     * 地址
     */
    @Column
    private String addr;

    /**
     * 邮编
     */
    @Column
    private String postcode;

    /**
     * 电话
     */
    @Column
    private String phone;

    /**
     * 传真
     */
    @Column
    private String fax;

    /**
     * 网址
     */
    @Column
    private String www;

    /**
     * 电子邮箱
     */
    @Column
    private String email;

    /**
     * 复制人
     */
    @Column
    private String answerMan;

    /**
     * 联系人
     */
    @Column
    private String contactMan;

    /**
     * 营业执照
     */
    @Column
    private String licence;

    /**
     * 开会银行
     */
    @Column
    private String bank;

    /**
     * 税务编码
     */
    @Column
    private String taxCode;

    /**
     * 银行账号
     */
    @Column
    private String account;

    /**
     * 助记码
     */
    @Column
    private String help;

    /**
     * 备注
     */
    @Column
    private String remark;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getLeibie()
    {
        return leibie;
    }

    public void setLeibie(String leibie)
    {
        this.leibie = leibie;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getAddr()
    {
        return addr;
    }

    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getWww()
    {
        return www;
    }

    public void setWww(String www)
    {
        this.www = www;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAnswerMan()
    {
        return answerMan;
    }

    public void setAnswerMan(String answerMan)
    {
        this.answerMan = answerMan;
    }

    public String getContactMan()
    {
        return contactMan;
    }

    public void setContactMan(String contactMan)
    {
        this.contactMan = contactMan;
    }

    public String getLicence()
    {
        return licence;
    }

    public void setLicence(String licence)
    {
        this.licence = licence;
    }

    public String getBank()
    {
        return bank;
    }

    public void setBank(String bank)
    {
        this.bank = bank;
    }

    public String getTaxCode()
    {
        return taxCode;
    }

    public void setTaxCode(String taxCode)
    {
        this.taxCode = taxCode;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getHelp()
    {
        return help;
    }

    public void setHelp(String help)
    {
        this.help = help;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

}
