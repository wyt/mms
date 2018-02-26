<%-- 
    Description：部门管理的索引页
    Date：2012-11-15 14:13:57
    Author：王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	String path  = request.getContextPath();
%>
<head>
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
				},
				// onClick: function(node){
					//alert(node.id);
					//window.location.href="<=path%>/view/sys/dept/pic.jsp";
				//}
			});
		});
		
		var actionUrl;
		
		/**
		 * 新建部门Dialog
		 */
		function createItem(){
			$('#myform').form('clear');
			$('#dlg').dialog('setTitle', '新建部门').dialog('open');
			var node = $('#deptTree').tree('getSelected');
			if (node){
				// $('#parentDept').combotree('setValue', node.id);
				$('#pname').html(node.text);
				actionUrl = '<c:url value="/sys/dept/save.mvc"/>' + '?parent.id=' + node.id;
			}else{
				actionUrl = '<c:url value="/sys/dept/save.mvc"/>' + '?parent.id=';
			}
		}
		
		/**
		 * 修改部门
		 */
		function editItem(){
			var node = $('#deptTree').tree('getSelected');
			if (node){
				var pnode = $('#deptTree').tree('getParent', node.target);
				$('#pname').html(pnode ? pnode.text : '');
				$('#myform input[name=deptName]').val(node.text);
				$('#dlg').dialog('open').dialog('setTitle', '修改部门');
				actionUrl = '<c:url value="/sys/dept/edit.mvc"/>?id=' + node.id;
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
		
		/**
		 * 删除部门
		 */
		function removeItem(){
			var node = $('#deptTree').tree('getSelected');
			if(node){
	            $.messager.confirm('警告', '是否真的删除该部门?', function(r){
	                if (r){
						$.ajax({
							type: 'post',
							dataType: 'json',
							url: '<c:url value="/sys/dept/delete.mvc"/>',
							data: 'id=' + node.id,
							success: function(data){
								if(data.success){
									bottomRight(data.msg);
								}else{
									bottomRight(data.msg);
								}
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
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">组织机构</div>
			<div class="toolbar">
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="createItem()">新增部门</a>
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="editItem()">修改部门</a>
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="removeItem()">删除部门</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="javascript:$('#deptTree').tree('reload')">刷新</a>
			</div>
		</div>
		<div region="center" border="false" style="padding:10px;">
			<!-- 部门tree -->
			<ul id="deptTree" class="easyui-tree" url="<c:url value='/sys/dept/getDepts.mvc'/>"></ul>
		</div>
	</div>
	
	<div id="dlg" style="width:300px;height:180px;padding:20px 0px 10px 20px;"
			class="easyui-dialog"
			title="添加" closed="true" modal="true">
		<form id="myform" method="post" enctype="application/x-www-form-urlencoded">
			<table style="font-size:12px;">
				<tr>
					<td>上级部门：</td>
					<%--
					<td>
						<span id="parentDept" name="parent.id" class="easyui-combotree" data-options="url:'<c:url value='/sys/dept/getDepts.mvc'/>'"></span>
					</td>
					--%>
					<td><span id="pname"></span></td>
				</tr>
				<tr>
					<td>部门名称：</td>
					<td><input name="deptName" class="easyui-validatebox" required="true"></input></td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;margin-top:20px;">
			<a href="#" class="easyui-linkbutton" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">取消</a>
		</div>
	</div>

</body>
