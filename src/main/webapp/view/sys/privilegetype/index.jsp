<%-- 
    Description：权限分类管理的索引页
    Date：2012-11-15 14:06:40
    Author：王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<head>
	<script src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.edatagrid.lang.js"/>"></script>
	<script type="text/javascript">
		var edg;
		$(function(){
			edg = $('#t-privileges').edatagrid({
				url:'<c:url value="/sys/privilegeType/getItems.mvc"/>',
				saveUrl:'<c:url value="/sys/privilegeType/save.mvc"/>',
				updateUrl:'<c:url value="/sys/privilegeType/edit.mvc"/>',
				destroyUrl:'<c:url value="/sys/privilegeType/delete.mvc"/>'
			});
		});
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">权限分类管理</div>
			<div class="toolbar">
				<a href="<c:url value='/sys/privilege/index.mvc'/>" class="easyui-linkbutton" iconCls="icon-back" plain="true">返回</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="javascript:$('#t-privileges').datagrid('reload')">刷新</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="edg.edatagrid('addRow')">新增</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="edg.edatagrid('saveRow')">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="edg.edatagrid('cancelRow')">取消</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="edg.edatagrid('destroyRow')">删除</a>
			</div>
		</div>
		<div region="center" border="false">
			<table id="t-privileges"
					border="false" singleSelect="true" fit="true"
					rownumbers="true">
				<thead>
					<tr>
						<th field="privilegeTypeName" width="150" editor="{type:'validatebox',options:{required:true}}">分类名称</th>
						<th field="description" width="350" editor="text">分类描述</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
