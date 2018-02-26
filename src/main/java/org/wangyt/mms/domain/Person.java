package org.wangyt.mms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王永涛
 * 
 * @date 2012-11-13 上午9:41:12
 * 
 * @version $Rev: 54 $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Entity
@Table(name = "jnyw_mms_person")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class Person
{
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(length = 32)
    private String id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_sex")
    private String sex;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the sex
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(String sex)
    {
        this.sex = sex;
    }

}