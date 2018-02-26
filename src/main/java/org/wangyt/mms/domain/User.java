package org.wangyt.mms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.wangyt.mms.util.date.JsonDateSerializer2;

/**
 * 用户Entity
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午8:35:33
 * 
 * @version $Rev: 93 $
 * 
 * @Copyright (c) Copyright 2012 WANGYT, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_user")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class User implements Serializable
{
    private static final long serialVersionUID = -7362822570619325738L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 是否自动登录(不需要持久化，注意加@Transient 注解)
     */
    @Transient
    private boolean autoLogin;

    /**
     * 用户名称
     */
    @Column
    private String userName;

    /**
     * 登录账号
     */
    @Column
    private String loginName;

    /**
     * 登录密码
     */
    @Column
    private String password;

    /**
     * 性别
     */
    @Column
    private String sex;

    /**
     * 手机号码
     */
    @Column
    private String phoneNum;

    /**
     * 联系邮箱
     */
    @Column
    private String email;

    /**
     * 入职时间
     */
    @Temporal(TemporalType.DATE)
    @Column
    private Date inTime;

    /**
     * 离职时间
     */
    @Temporal(TemporalType.DATE)
    @Column
    private Date outTime;

    /**
     * 与部门-多对一
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    /**
     * 与角色(即岗位或者说职位)-多对多
     */
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "jnyw_mms_middle_user_role", inverseJoinColumns = @JoinColumn(name = "role_id"), joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles = new HashSet<Role>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean isAutoLogin()
    {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin)
    {
        this.autoLogin = autoLogin;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @JsonSerialize(using = JsonDateSerializer2.class)
    public Date getInTime()
    {
        return inTime;
    }

    public void setInTime(Date inTime)
    {
        this.inTime = inTime;
    }

    @JsonSerialize(using = JsonDateSerializer2.class)
    public Date getOutTime()
    {
        return outTime;
    }

    public void setOutTime(Date outTime)
    {
        this.outTime = outTime;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    @JsonIgnore
    public void setPassword(String password)
    {
        this.password = password;
    }

    public Dept getDept()
    {
        return dept;
    }

    public void setDept(Dept dept)
    {
        this.dept = dept;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

}
