package org.wangyt.mms.web.easyui.model;

/**
 * Ajax消息数据模型
 * 
 * @author 王永涛
 * 
 * @date 2012-11-15 上午10:44:35
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/easyui/model/AjaxInfoJson.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public class AjaxInfoJson
{
    /**
     * 业务逻辑是否执行成功
     */
    private boolean success;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 保留默认构造方法
     */
    public AjaxInfoJson()
    {
    }

    /**
     * 私有有参构造方法
     * 
     * @param success
     * @param msg
     */
    private AjaxInfoJson(boolean success, String msg)
    {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    /**
     * 得到赋值AjaxInfoJson对象
     * 
     * @param success
     * @param msg
     * @return
     */
    public static AjaxInfoJson getAjaxInfoJson(boolean success, String msg)
    {
        return new AjaxInfoJson(success, msg);
    }

}
