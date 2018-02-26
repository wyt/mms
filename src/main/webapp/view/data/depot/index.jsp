<%-- 
    Description：仓库管理的索引页
    Date：2012-11-19 13:44:41
    Author：王永涛
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>    

<head>
    <script src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.edatagrid.lang.js"/>"></script>
	<script type="text/javascript">
		var edg;
		$(function(){
			edg = $('#dt-depots').edatagrid({
				url:'<c:url value="/data/depot/getItems.mvc"/>',
				saveUrl:'<c:url value="/data/depot/save.mvc"/>',
				updateUrl:'<c:url value="/data/depot/update.mvc"/>',
				destroyUrl:'<c:url value="/data/depot/destroy.mvc"/>'
			});
		});
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">仓库资料</div>
			<div class="toolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="edg.edatagrid('reload')">刷新</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="edg.edatagrid('addRow')">新增</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="edg.edatagrid('saveRow')">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="edg.edatagrid('cancelRow')">取消</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="edg.edatagrid('destroyRow')">删除</a>
			</div>
		</div>
		<div region="center" border="false" style1="padding:0 5px 5px 5px">
			<table id="dt-depots" fit="true" fitColumns="true" singleSelect="true" rownumbers="true" border="false">
				<thead>
					<tr>
						<th field="depotCode" width="100" editor="text">仓库编码</th>
						<th field="depotName" width="200" editor="{type:'validatebox',options:{required:true}}">仓库名称</th>
						<th field="depotDirections" width="300" editor="text">说明</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
