package org.wangyt.mms.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 权限Entity,即可访问的url资源
 * 
 * @author 王永涛
 * 
 * @date 2012-11-15 下午2:55:21
 * 
 * @version $Rev: 93 $
 * 
 * @Copyright (c) Copyright 2012 WANGYT, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_privilege")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Privilege implements Serializable
{
    private static final long serialVersionUID = 5194081388341331040L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 权限名称
     */
    @Column
    private String privilegeName;

    /**
     * 权限描述
     */
    @Column
    private String description;

    /**
     * 对应资源路径
     */
    @Column
    private String url;

    /**
     * 排序号
     */
    @Column
    private int sort;

    /**
     * 权限与权限分类是多对一的关系
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "privilege_type_id")
    private PrivilegeType privilegeType;

    /**
     * 权限与角色是多对多的关系,并且角色为关系的维护端(因为我要给角色来设置权限).
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "privileges")
    private Set<Role> roles = new HashSet<Role>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPrivilegeName()
    {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName)
    {
        this.privilegeName = privilegeName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public PrivilegeType getPrivilegeType()
    {
        return privilegeType;
    }

    public void setPrivilegeType(PrivilegeType privilegeType)
    {
        this.privilegeType = privilegeType;
    }

    @JsonIgnore
    public Set<Role> getRoles()
    {
        return roles;
    }

    @JsonIgnore
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Privilege other = (Privilege) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
