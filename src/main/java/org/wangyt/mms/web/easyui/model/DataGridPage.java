package org.wangyt.mms.web.easyui.model;

/**
 * easyui datagrid page model !
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 下午3:37:22
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/easyui/model/DataGridPage.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public class DataGridPage
{
    /**
     * 第几页
     */
    private int page;

    /**
     * 页面显示数据集的最大个数
     */
    private int rows;

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

}
