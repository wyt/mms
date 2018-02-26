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
 * 物资分类实体(MaterialClass Entiry)
 * 
 * @author 王永涛
 * 
 * @date 2012-11-19 下午3:40:44
 * 
 * @version $Rev: 84 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/MaterialClass.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_material_class")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class MaterialClass implements Serializable
{
    private static final long serialVersionUID = 7464837955039818443L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 分类名称
     */
    @Column
    private String materialClassName;

    /**
     * 当前分类与上级(父)分类是多对一的关系
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_id")
    private MaterialClass parent;

    /**
     * 当前分类与它的子分类是一对多的关系
     */
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "parent")
    private List<MaterialClass> children = new ArrayList<MaterialClass>();

    /**
     * 当前分类下所对应的具体物资(一对多)
     */
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "mc")
    private List<Material> materials = new ArrayList<Material>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMaterialClassName()
    {
        return materialClassName;
    }

    public void setMaterialClassName(String materialClassName)
    {
        this.materialClassName = materialClassName;
    }

    @JsonIgnore
    public MaterialClass getParent()
    {
        return parent;
    }

    @JsonIgnore
    public void setParent(MaterialClass parent)
    {
        this.parent = parent;
    }

    @JsonIgnore
    public List<MaterialClass> getChildren()
    {
        return children;
    }

    @JsonIgnore
    public void setChildren(List<MaterialClass> children)
    {
        this.children = children;
    }

    @JsonIgnore
    public List<Material> getMaterials()
    {
        return materials;
    }

    @JsonIgnore
    public void setMaterials(List<Material> materials)
    {
        this.materials = materials;
    }

}
