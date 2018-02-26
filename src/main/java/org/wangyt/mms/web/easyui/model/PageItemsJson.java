package org.wangyt.mms.web.easyui.model;

import java.util.List;

/**
 * 分页Josn的对象模型
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 下午3:26:13
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/easyui/model/PageItemsJson.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
public class PageItemsJson
{
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 数据集
     */
    private List rows;

    public Long getTotal()
    {
        return total;
    }

    public void setTotal(Long total)
    {
        this.total = total;
    }

    public List getRows()
    {
        return rows;
    }

    public void setRows(List rows)
    {
        this.rows = rows;
    }
}
