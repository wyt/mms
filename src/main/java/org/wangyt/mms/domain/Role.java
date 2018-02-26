package org.wangyt.mms.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 角色Entity
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午10:02:01
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/Role.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_role")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Role implements Serializable
{
    private static final long serialVersionUID = 4634837562357223226L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 角色名称
     */
    @Column
    private String roleName;

    /**
     * 角色描述
     */
    //@Column(columnDefinition = "text")
    @Column
    private String description;

    /**
     * 排序号
     */
    @Column
    private int sort;

    /**
     * 角色与用户是多对多的关系，一个用户可以对应多种角色;一种角色也可以对应多个不同的用户。
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles")
    private Set<User> users = new HashSet<User>();

    /**
     * 角色与权限是多对多的关系
     */
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "jnyw_mms_middle_role_privilege", inverseJoinColumns = @JoinColumn(name = "privilege_id"), joinColumns = @JoinColumn(name = "role_id"))
    private Set<Privilege> privileges = new HashSet<Privilege>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
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
    public Set<User> getUsers()
    {
        return users;
    }

    @JsonIgnore
    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

    public Set<Privilege> getPrivileges()
    {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges)
    {
        this.privileges = privileges;
    }

}
