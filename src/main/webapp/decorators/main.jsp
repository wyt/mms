<%-- 
    Document   : main主装饰页面
    Created on : 2012-11-13 13:29:54
    Author     : 王永涛
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<html style="height:100%">
    <head>
    	<title><decorator:title default="济宁第二人民医院物资管理系统" /></title>
    	
    	<!-- 引入公共的<head></head>内容 -->
        <jsp:include page="_head.jsp"></jsp:include>
        
        <!-- 读取被装饰页面的<head>中的内容 -->
        <decorator:head />
    </head>
    <body style="margin:0;padding:0;height:100%;overflow:hidden;background:#F2FBFF">
   		<div class="mainwrap">
   			<div id="mainlayout" class="easyui-layout" fit="true">
   				<div region="north" border="false" style="height:94px;background:#A1C4ff">
   					<!-- 引入页面的顶部模板 -->
   					<jsp:include page="_north.jsp"></jsp:include>
   				</div>
    			<div region="center" border="false" style="overflow:hidden">
    				<!-- 读取被装饰页面的<body>中的内容 -->
    				<decorator:body />
    			</div>
   				<div region="south" border="false">
   					<!-- 引入页面的底部模板 -->
   					<jsp:include page="_south.jsp"></jsp:include>
   				</div>
   			</div>
   		</div>
    </body>
</html>