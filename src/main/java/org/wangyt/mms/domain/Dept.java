package org.wangyt.mms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 组织机构(部门)Entity
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午9:33:06
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/Dept.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_dept")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Dept implements Serializable
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
     * 部门名称
     */
    @Column
    private String deptName;

    /**
     * 与当前部门的上级(父)部门 - 多对一
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_id")
    private Dept parent;

    /**
     * 与当前部门的下级(子)部门 - 一对多
     */
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Dept> children = new ArrayList<Dept>();

    /**
     * 与用户一对多
     * 
     * Cascadetype.REMOVE 写上这句，删除部门是，也会把部门下的用户删除，Hibernate会先删除外键，在删除用户和部门
     * 
     * 不写这句，删除部门的话，如果该部门下有员工，怎么因为有外键引用，故而删除不掉。
     */
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "dept")
    //@OneToMany(cascade = { CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "dept")
    private List<User> users = new ArrayList<User>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public Dept getParent()
    {
        return parent;
    }

    public void setParent(Dept parent)
    {
        this.parent = parent;
    }

    @JsonIgnore
    public List<Dept> getChildren()
    {
        return children;
    }

    @JsonIgnore
    public void setChildren(List<Dept> children)
    {
        this.children = children;
    }

    @JsonIgnore
    public List<User> getUsers()
    {
        return users;
    }

    @JsonIgnore
    public void setUsers(List<User> users)
    {
        this.users = users;
    }

}
