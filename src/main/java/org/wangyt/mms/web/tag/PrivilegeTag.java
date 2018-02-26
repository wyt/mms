package org.wangyt.mms.web.tag;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.wangyt.mms.domain.Privilege;
import org.wangyt.mms.domain.Role;
import org.wangyt.mms.domain.User;

/**
 * 权限控制 显示标签
 * 
 * @author 王永涛
 * 
 * @date 2012-11-20 下午6:23:28
 * 
 * @version $Rev: 93 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/tag/PrivilegeTag.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public class PrivilegeTag extends TagSupport
{
    private static final long serialVersionUID = 648166028953903733L;

    private String url;

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public int doStartTag() throws JspException
    {
        User user = (User) pageContext.getSession().getAttribute("currentUser");
        if (user == null) return SKIP_BODY;
        
        if ("admin".equals(user.getLoginName()))
        {
            return EVAL_BODY_INCLUDE;
        } else
        {
            Set<Privilege> privileges = new HashSet<Privilege>();
            for (Role role : user.getRoles())
            {
                privileges.addAll(role.getPrivileges());
            }

            for (Privilege p : privileges)
            {
                String p_url = p.getUrl();
                if (p_url != null && p_url.equals(url))
                {
                    return EVAL_BODY_INCLUDE;
                }
            }
        }
        return SKIP_BODY;
    }
}
