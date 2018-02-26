package org.wangyt.mms.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 权限类型 Entity
 * 
 * @author 王永涛
 * 
 * @date 2012-11-15 下午2:44:42
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/PrivilegeType.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_privilege_type")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class PrivilegeType implements Serializable
{
    private static final long serialVersionUID = -2646359657400175061L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 权限分类名称
     */
    @Column
    private String privilegeTypeName;

    /**
     * 描述
     */
    @Column
    private String description;

    /**
     * 排序号
     */
    @Column
    private int sort;

    /**
     * 权限分类与权限是一对多的关系
     */
    @OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "privilegeType")
    private Set<Privilege> privileges;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPrivilegeTypeName()
    {
        return privilegeTypeName;
    }

    public void setPrivilegeTypeName(String privilegeTypeName)
    {
        this.privilegeTypeName = privilegeTypeName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    @JsonIgnore
    public Set<Privilege> getPrivileges()
    {
        return privileges;
    }

    @JsonIgnore
    public void setPrivileges(Set<Privilege> privileges)
    {
        this.privileges = privileges;
    }

}
