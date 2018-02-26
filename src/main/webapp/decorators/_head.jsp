<%-- 
    Document   : 公共head页面模板
    Created on : 2012-11-13 13:29:54
    Author     : 王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%-- easyui 1.3.1
<link rel="stylesheet" href="<c:url value="/easyui/themes/metro/easyui.css"/>" type="text/css" media="screen" />
<link rel="stylesheet" href="<c:url value="/easyui/themes/icon.css"/>" type="text/css" media="screen" />
<link id="mainCss" rel="stylesheet" href="<c:url value="/style/main.css"/>" type="text/css" media="screen" />

<script src="<c:url value="/easyui/jquery-1.8.0.min.js"/>"></script>
<script src="<c:url value="/easyui/jquery.easyui.min.js"/>"></script>
<script src="<c:url value="/easyui/locale/easyui-lang-zh_CN.js"/>"></script>
--%>

<%-- 将easyui 1.3.1 替换为1.4.3 --%>
<link rel="stylesheet" href="<c:url value="/resources/easyui_v143_pro/themes/bootstrap/easyui.css"/>" type="text/css" media="screen" />
<link rel="stylesheet" href="<c:url value="/resources/easyui_v143_pro/themes/icon.css"/>" type="text/css" media="screen" />
<link id="mainCss" rel="stylesheet" href="<c:url value="/resources/style/main.css"/>" type="text/css" media="screen" />

<script src="<c:url value="/resources/easyui_v143_pro/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/easyui_v143_pro/jquery.easyui.min.js"/>"></script>
<script src="<c:url value="/resources/easyui_v143_pro/locale/easyui-lang-zh_CN.js"/>"></script>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	
	<%-- 
		用于未完成渲染页面时的遮罩效果
		$.parser 用于解析easyui组件的jQuery扩展插件
		onComplete表示解析完成后执行
	 --%>
	$.parser.onComplete = function(){
	   	$('body').css('visibility','visible');
	   	setTimeout(function(){
	    	$('#loading-mask').remove();
	   	},50);
	};
	
	$(function(){
	   	$(window).resize(function(){
	       	$('#mainlayout').layout('resize');
	   	});
	});
</script>