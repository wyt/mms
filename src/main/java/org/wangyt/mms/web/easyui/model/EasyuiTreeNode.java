package org.wangyt.mms.web.easyui.model;

import java.util.List;
import java.util.Map;

/**
 * easyui使用的tree模型
 * 
 * @author 王永涛
 * 
 * @date 2012-11-16 上午11:36:28
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/easyui/model/EasyuiTreeNode.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public class EasyuiTreeNode implements java.io.Serializable
{
    private static final long serialVersionUID = -1443095874670215029L;

    private String id;

    /**
     * 树节点名称
     */
    private String text;

    /**
     * 节点小图标
     */
    private String iconCls;

    /**
     * 是否勾选状态
     */
    private Boolean checked = false;

    /**
     * 其他参数
     */
    private Map<String, Object> attributes;

    /**
     * 子节点
     */
    private List<EasyuiTreeNode> children;

    /**
     * 是否展开(open,closed)
     */
    private String state = "open";

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Boolean getChecked()
    {
        return checked;
    }

    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }

    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes)
    {
        this.attributes = attributes;
    }

    public List<EasyuiTreeNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<EasyuiTreeNode> children)
    {
        this.children = children;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

}
