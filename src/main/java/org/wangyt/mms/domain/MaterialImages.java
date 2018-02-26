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
 * 物资图片
 * 
 * @author 王永涛
 * 
 * @date 2012-11-20 上午8:34:23
 * 
 * @version $Rev: 86 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/domain/MaterialImages.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_material_images")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class MaterialImages implements Serializable
{
    private static final long serialVersionUID = 4545110651970055348L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 图片名称(不包含扩展名)
     */
    @Column
    private String imageName;

    /**
     * 图片扩展名
     */
    @Column
    private String extName;

    /**
     * 图片完整名
     */
    @Column
    private String imageFullName;

    /**
     * 图片大小
     */
    @Column
    private String imageSize;

    /**
     * 保存路径
     */
    @Column
    private String savePath;

    /**
     * 图片与物资是多对一的关系
     * 
     * @return
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

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }

    public String getExtName()
    {
        return extName;
    }

    public void setExtName(String extName)
    {
        this.extName = extName;
    }

    public String getImageFullName()
    {
        return imageFullName;
    }

    public void setImageFullName(String imageFullName)
    {
        this.imageFullName = imageFullName;
    }

    public String getImageSize()
    {
        return imageSize;
    }

    public void setImageSize(String imageSize)
    {
        this.imageSize = imageSize;
    }

    public String getSavePath()
    {
        return savePath;
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
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
