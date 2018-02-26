<%-- 
    Description：权限管理的索引页
    Date：2012-11-15 14:14:39
    Author：王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
	<style type="text/css">
		.e-input{
			border:1px solid #ccc;
			width:300px;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			init();
		});
		function init(){
			$('#t-types').tree({
				lines:true,
				onClick:function(node){
					$('#t-privileges').datagrid('reload', {privilegeTypeId:node.id});
				}
			});
			$('#t-privileges').datagrid({
				loadMsg:''/*,
				onRowContextMenu:function(e,index,row){
					e.preventDefault();
					$('#t-privileges').datagrid('clearSelections');
					$('#t-privileges').datagrid('selectRow', index);
					$('#mm').menu('show', {
						left:e.pageX,
						top:e.pageY
					});
				}*/
			});
		}
		function formatUrls(value, row){
			return '<pre style="margin:0">'+value+'</pre>';
		}
		function formatType(value, row){
			if (row.privilegeType) return row.privilegeType.name;
			return value;
		}

		var actionUrl;
		function newItem(){
			$('#dlg').dialog('setTitle', '新增权限').dialog('open');
			$('#myform').form('clear');
			actionUrl = '<c:url value="/sys/privilege/save.mvc"/>';
		}
		function editItem(){
			var t = $('#t-privileges');
			var row = t.datagrid('getSelected');
			if (row){
				// $('#myform').form('load', row);
				// $('#myform').form('load', row.privilegeType.id);
				$('#myform').form('load', {
					"id": row.id,
					"description": row.description,
					"sort": row.sort,
					"url": row.url,
					"privilegeName": row.privilegeName,
					"privilegeType.id": row.privilegeType.id
				});
				$('#dlg').dialog('setTitle', '修改权限').dialog('open');
				actionUrl = '<c:url value="/sys/privilege/edit.mvc"/>?id=' + row.id;
			}
		}
		function saveItem(){
			$('#myform').form('submit', {
				url:actionUrl,
				onSubmit:function(){
					return $('#myform').form('validate');
				},
				success:function(data){
					$('#dlg').dialog('close');
					$('#t-privileges').datagrid('reload');
				}
			});
		}
		
		/**
		 * 删除权限
		 */
		 function removeItem(){
		 	var row = $('#t-privileges').datagrid('getSelected');
			if(row){
	            $.messager.confirm('警告', '是否真的删除该权限?', function(r){
	                if (r){
						$.ajax({
							type: 'post',
							dataType: 'json',
							url: '<c:url value="/sys/privilege/delete.mvc"/>',
							data: 'id=' + row.id,
							success: function(data){
								if(data.success){
									bottomRight(data.msg);
								}else{
									bottomRight(data.msg);
								}
								$('#t-privileges').datagrid('reload');
							},
							error: function(){
								$.messager.alert('错误提示','系统内部错误!','error'); // 后台抛出异常时
							}
						});
	                }
	            });  
			}	
		}
			
		/**
		 *消息提示
		 */
		function bottomRight(msg){
			$.messager.show({
				title:'消息提示',
				msg: msg,
				showType:'show'
			});
		}
		 
		 
		/*
		function move(dir){
			var row = $('#t-privileges').datagrid('getSelected');
			$.getJSON('<c:url value="/system/privilege/move"/>', {id:row.id,dir:dir}, function(){
				$('#t-privileges').datagrid('reload');
			});
		}*/
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">权限管理</div>
			<div class="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">新增权限</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改权限</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除权限</a>
			<%--
			<a href="#" class="easyui-linkbutton" plain="true" onclick="move('up')">上移</a>
			<a href="#" class="easyui-linkbutton" plain="true" onclick="move('down')">下移</a>
			--%>
			</div>
		</div>
		<div region="west" border="false" style="border-right:1px solid #92B7D0;width:150px;">
			<div style="background:#fafafa;padding:5px;">
				权限分类
				<a href="<c:url value='/sys/privilegeType/index.mvc'/>">修改</a>
			</div>
			<div style="padding:5px;">
				<ul id="t-types" url="<c:url value='/sys/privilegeType/getTypes.mvc'/>"></ul>
			</div>
		</div>
		<div region="center" border="false">
			<table id="t-privileges"
					url="<c:url value='/sys/privilege/getItems.mvc'/>"
					singleSelect="true" rownumbers="true"
					idField="id"
					border="false" fit="true" fitColumns="true">
				<thead>
					<tr>
						<th field="privilegeName" width="100" sortable="true">权限名称</th>
						<th field="description" width="300">权限描述</th>
						<th field="url" width="300" formatter="formatUrls">可访问路径</th>
						<th field="privilegeType" width="100" sortable="true" 
							data-options="
								formatter: function(value,row,index){
									if(value){
										return value.privilegeTypeName;
									}else{
										return '';
									}
								}
							">分类</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttons" style="padding:10px;width:480px;height:280px;">
		<form id="myform" method="post">
			<table>
				<tr>
					<td style="width:80px;">权限分类</td>
					<td>
						<input type="text" id="privilegeTypeId" name="privilegeType.id" class="easyui-combotree" url="<c:url value='/sys/privilegeType/getTypes.mvc'/>" 
							   data-options="
							   		required:true,
							   		lines:true,
							   		onClick:function(node){
							   			if(!node.id){
								   			$('#privilegeTypeId').combotree('setValue', null);
							   			}
							   		}
							   ">
					</td>
				</tr>
				<tr>
					<td>权限名称</td>
					<td><input type="text" name="privilegeName" class="easyui-validatebox e-input" required="true"></input></td>
				</tr>
				<tr>
					<td>权限描述</td>
					<td><input type="text" name="description" class="e-input"></input></td>
				</tr>
				<tr>
					<td>可访问路径</td>
					<td><textarea name="url" class="e-input" style="height:80px;"></textarea></td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons" style="text-align:center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
	</div>
	<%--
	 // 右键菜单
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div iconCls="icon-add" onclick="newItem()">新增权限</div>
		<div iconCls="icon-edit" onclick="editItem()">修改权限</div>
		<div class="menu-sep"></div>
		<div onclick="move('up')">上移</div>
		<div onclick="move('down')">下移</div>
	</div>
	--%>
</body>
