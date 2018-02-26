<%-- 
    Document   : 顶部logo和导航条公共页面
    Created on : 2012-11-13 13:29:54
    Author     : 王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="sdwyt@foxmail.com" prefix="mms" %>
<div class="header">
	<div style="padding:10px;text-align:right;">
		<span style="color:#ddd">欢迎您，${currentUser.userName }</span>
		<a href="<c:url value='/logout/action.mvc'/>" style="color:#fff">退出</a>
		<%--
		<script type="text/javascript">
			function setMainWrapWidth(){
				alert($('#mainCss').attr("href"));
				var mainwrapWidth = $(window).width()+'px';
				$('.mainwrap').css('width',mainwrapWidth);
			}
		</script>
		<a href="javascript:setMainWrapWidth();" style="color:#fff">切换到宽屏</a>
		--%>
	</div>
	<div id="jneylogo" style="height: 50px;width: 50px;">
		<img alt="logo" height="50" width="50" src="<c:url value="/resources/style/images/jneylogo.png" />" />
	</div>
	<div class="toptitle">济宁市第二人民医院物资管理系统</div>
</div>
<div class="topmenu">
	<mms:privilege url="/index.mvc">
		<a href="<c:url value="/index.mvc"/>" class="easyui-linkbutton" plain="true">首页</a>
	</mms:privilege>
	<a href="#" class="easyui-menubutton" menu="#menu-stock">业务单据</a>
	<a href="#" class="easyui-menubutton" menu="#menu-data">数据维护</a>
	<a href="#" class="easyui-menubutton" menu="#menu-system">系统管理</a>
	<a href="#" class="easyui-menubutton" menu="#menu-userset">个人设置</a>
</div>
<div id="menu-stock" class="easyui-menu" style="width:150px">
	<mms:privilege url="/stock/instock/">
		<div href="<c:url value="/stock/instock/index.mvc"/>">物资入库单</div>
	</mms:privilege>
	<mms:privilege url="/stock/outstock/">
		<%--
		<div href="<c:url value="/stock/outstock/index.mvc"/>">物资出库单</div>
		--%>
		<div href="#">物资出库单</div>
	</mms:privilege>
</div>
<div id="menu-data" class="easyui-menu" style="width:150px">
	<mms:privilege url="/data/depot/">
		<div href="<c:url value="/data/depot/index.mvc"/>">仓库资料</div>
	</mms:privilege>
	<mms:privilege url="/data/measureunit/">
		<div href="<c:url value="/data/measureunit/index.mvc"/>">计量单位</div>
	</mms:privilege>
	<div class="menu-sep"></div>
	<mms:privilege url="/data/materialClass/">
		<div href="<c:url value="/data/materialClass/index.mvc"/>">物资分类</div>
	</mms:privilege>
	<mms:privilege url="/data/material/">
		<div href="<c:url value="/data/material/index.mvc"/>">物资详情</div>
	</mms:privilege>
	<div href="">条码录入</div>
	<div href="">物资追溯</div>
	<div class="menu-sep"></div>
	<mms:privilege url="/data/supplier/">
		<div href="<c:url value="/data/supplier/index.mvc"/>">供应商管理</div>
	</mms:privilege>
</div>
<div id="menu-system" class="easyui-menu" style="width:150px">
	<mms:privilege url="/sys/user/">
		<div href="<c:url value="/sys/user/index.mvc"/>">用户管理</div>
	</mms:privilege>
	<mms:privilege url="/sys/role/">
		<div href="<c:url value="/sys/role/index.mvc"/>">角色管理</div>
	</mms:privilege>
	<mms:privilege url="/sys/privilege/">
		<div href="<c:url value="/sys/privilege/index.mvc"/>">权限管理</div>
	</mms:privilege>
	<mms:privilege url="/sys/dept/">
		<div href="<c:url value="/sys/dept/index.mvc"/>">组织机构</div>
	</mms:privilege>
</div>
<div id="menu-userset" class="easyui-menu" style="width:150px">
	<mms:privilege url="/sys/user/userSet.mvc">
		<div href="<c:url value="/sys/user/userSet.mvc"/>">密码修改</div>
	</mms:privilege>
</div>				