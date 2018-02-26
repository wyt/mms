<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<head>
	<script src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.edatagrid.lang.js"/>"></script>
	<script type="text/javascript">
		var tree;
		$(function(){
			tree = $("#deptTree").tree({
				animate : true,
				lines : true,
				onLoadSuccess : function(node,data){
					var t = $(this);
					if(data){
						$(data).each(function(index,d){
							t.tree('expandAll');
						});
					}
				}
			});
		});
		var actionUrl;
		function createItem(){
			$('#myform').form('clear');
			$('#dlg').dialog('setTitle', '新建部门').dialog('open');
			var node = $('#deptTree').tree('getSelected');
			if (node){
				// $('#parentDept').combotree('setValue', node.id);
				$('#pname').html(node.text);
				actionUrl = '<c:url value="/data/materialClass/save.mvc"/>' + '?parent.id=' + node.id;
			}else{
				actionUrl = '<c:url value="/data/materialClass/save.mvc"/>' + '?parent.id=';
			}
		}
		
		function saveItem(){
			$('#myform').form('submit', {
				url:actionUrl,
				onSubmit: function(){
					return $('#myform').form('validate');
				},
				success:function(data){
					$('#deptTree').tree('reload');
					$('#dlg').dialog('close');
				}
			});
		}
		
		function editItem(){
			var node = $('#deptTree').tree('getSelected');
			if (node){
				var pnode = $('#deptTree').tree('getParent', node.target);
				$('#pname').html(pnode ? pnode.text : '');
				$('#myform input[name=materialClassName]').val(node.text);
				$('#dlg').dialog('open').dialog('setTitle', '修改部门');
				actionUrl = '<c:url value="/data/materialClass/edit.mvc"/>?id=' + node.id;
			}
		}
		function deleteItem(){
			var node = $('#deptTree').tree('getSelected');
			if (node){
				 $.messager.confirm('警告', '是否真的删除该分类?', function(r){
		                if (r){
							$.ajax({
								type: 'post',
								dataType: 'json',
								url: '<c:url value="/data/materialClass/delete.mvc"/>',
								data: 'id=' + node.id,
								success: function(data){
									$('#deptTree').tree('reload');
								},
								error: function(){
									$.messager.alert('错误提示','系统内部错误!','error'); // 后台抛出异常时
								}
							});
		                }
		            });  
			}
		}
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">物资分类</div>
			<div class="toolbar">
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="createItem()">新增物资分类</a>
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="editItem()">修改物资分类</a>
<!-- 				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="deleteItem()">删除物资分类</a> -->
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="javascript:$('#deptTree').tree('reload')">刷新</a>
			</div>
		</div>
		<div region="center" border="false">
			<ul id="deptTree" class="easyui-tree" url="<c:url value='/data/materialClass/getItems.mvc'/>"></ul>
		</div>
	</div>
	<div id="dlg" style="width:300px;height:180px;padding:20px 0px 10px 20px;"
			class="easyui-dialog"
			title="添加" closed="true" modal="true">
		<form id="myform" method="post" enctype="application/x-www-form-urlencoded">
			<table style="font-size:12px;">
				<tr>
					<td>上级分类：</td>
					<%--
					<td>
						<span id="parentDept" name="parent.id" class="easyui-combotree" data-options="url:'<c:url value='/sys/dept/getDepts.mvc'/>'"></span>
					</td>
					--%>
					<td><span id="pname"></span></td>
				</tr>
				<tr>
					<td>分类名称：</td>
					<td><input name="materialClassName" class="easyui-validatebox" required="true"></input></td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;margin-top:20px;">
			<a href="#" class="easyui-linkbutton" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">取消</a>
		</div>
	</div>
</body>
