<%-- 
    Document   : system,比main多了相关操作
    Created on : 2012-11-13 13:29:54
    Author     : 王永涛
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<html style="height:100%">
    <head>
    	<jsp:include page="_head.jsp"></jsp:include>
        <decorator:head />
    </head>
    <body style="margin:0;padding:0;height:100%;background:#F2FBFF">
    	<div id="loading-mask"><div id="loading-msg">正在加载...</div></div>
    	<div class="mainwrap">
    		<div id="mainlayout" class="easyui-layout" fit="true">
    			<div region="north" border="false" style="height:94px;background:#A1C4ff">
					<jsp:include page="_north.jsp"></jsp:include>
    			</div>
    			<div region="east" border="false" style="padding:5px;width:170px;border-left:1px solid #92B7D0">
					<div class="subtitle">相关操作</div>
					<div class="submenu" style="padding:0 20px;">
						<a href="<c:url value='/sys/user/index.mvc'/>">用户管理</a>
						<a href="<c:url value='/sys/role/index.mvc'/>">角色管理</a>
						<a href="<c:url value='/sys/privilege/index.mvc'/>">权限管理</a>
						<a href="<c:url value='/sys/privilegeType/index.mvc'/>">权限分类管理</a>
						<a href="<c:url value='/sys/dept/index.mvc'/>">组织机构</a>
						<a href="<c:url value='/sys/user/index.mvc'/>">个人设置</a>
					</div>
    			</div>
    			<div region="center" border="false" style="overflow:hidden">
    				<decorator:body />
    			</div>
    			<div region="south" border="false">
   					<jsp:include page="_south.jsp"></jsp:include>
   				</div>
    		</div>
    	</div>
    </body>
</html>
