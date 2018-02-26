<%-- 
    Description：角色管理的索引页
    Date：2012-11-13 18:50:35
    Author：王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
	<script type="text/javascript">
		$(function(){
			init();
		});
		function init(){
			$('#dlg').dialog({
				onOpen:function(){
					$('#tabs').tabs('resize');
				}
			});
		}

		var actionUrl;
		
		/**
		 * 打开新增角色Dialog
		 */
		function newItem(){
			$('#myform').form('clear');
			// 先清空权限tree之前选中的节点
			var nodes = $('#privilege-tree').tree('getChecked');
			for(var i=0;i<nodes.length;i++){
				$('#privilege-tree').tree('uncheck',nodes[i].target);
			}
			$('#dlg').dialog('setTitle', '新增角色').dialog('open');
			actionUrl = '<c:url value="/sys/role/save.mvc"/>';
		}
		
		/**
		 * 删除角色
		 */
		function removeItem(){
			var row = $('#t-roles').datagrid('getSelected');
			if(row){
	            $.messager.confirm('警告', '是否真的删除该角色?', function(r){
	                if (r){
						$.ajax({
							type: 'post',
							dataType: 'json',
							url: '<c:url value="/sys/role/delete.mvc"/>',
							data: 'id=' + row.id,
							success: function(data){
								if(data.success){
									bottomRight(data.msg);
								}else{
									bottomRight(data.msg);
								}
								$('#t-roles').datagrid('reload');
							},
							error: function(){
								// $.messager.alert('信息提示',arguments[1],'info'); // 打印parseerror表示json解析错误.
								$.messager.alert('错误提示','系统内部错误!','error'); // 后台抛出异常时
							}
						});
	                }
	            });  

			}
		}
		
		/**
		 * 修改角色
		 */
		function editItem(){
			var row = $('#t-roles').datagrid('getSelected');
			if (row){
				$('#myform').form('load', row);
				// 先清空权限tree之前选中的节点
				var nodes = $('#privilege-tree').tree('getChecked');
				for(var i=0;i<nodes.length;i++){
					$('#privilege-tree').tree('uncheck',nodes[i].target);
				}
				
				var privileges = row.privileges;
				if(privileges){
					for(var i=0;i<privileges.length;i++){
						var node = $('#privilege-tree').tree('find',privileges[i].id);
						$('#privilege-tree').tree('check',node.target);
					}
				}
				
				$('#dlg').dialog('setTitle', '修改角色').dialog('open');
				actionUrl = '<c:url value="/sys/role/edit.mvc"/>?id=' + row.id;
			}
		}
		/**
		 * 保存角色
		 */
		function saveItem(){
			var privileges = $('#privilege-tree').tree('getChecked');
			var privilegeIdArray = [];
			for(var i=0;i<privileges.length;i++){
				if(privileges[i].iconCls == 'icon-jnyw_mms_key'){
					privilegeIdArray.push(privileges[i].id);
				}
			}
			$('#privilegeIds').val(privilegeIdArray.join(','));
			$('#myform').form('submit', {
				url: actionUrl,
				dataType: 'json',
				onSubmit:function(){
					return $('#myform').form('validate');
				},
				success: function(data){
					$('#dlg').dialog('close');
					// 此处jQuery easyui并没有为我们将json字符串转换为对象，需要我们手动转换。
					var obj = jQuery.parseJSON(data);
					if(obj.success){
						bottomRight(obj.msg);
					}else{
						bottomRight(obj.msg);
					}
					$('#t-roles').datagrid('reload');
				}
			});
		}
		
		/**
		 * 记录排序
		 */
		function move(dir){
			var row = $('#t-roles').datagrid('getSelected');
			if (row){
				$.ajax({
					url:'<c:url value="/sys/role/move.mvc"/>',
					data:{
						id:row.id,
						dir:dir
					},
					type:'post',
					success:function(){
						$('#t-roles').datagrid('reload');
					}
				});
			}
		}
		
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
			<div class="subtitle">角色管理</div>
			<div class="toolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">新增角色</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改角色</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除角色</a>
				<a href="#" class="easyui-linkbutton" plain="true" onclick="move('up')">上移</a>
				<a href="#" class="easyui-linkbutton" plain="true" onclick="move('down')">下移</a>
			</div>
		</div>
		<div region="center" border="false">
			<table id="t-roles" class="easyui-datagrid"
					url="<c:url value='/sys/role/getItems.mvc'/>"
					rownumbers="true" singleSelect="true" idField="id" nowrap="false"
					pagination="true" fit="true" fitColumns="true" border="false">
				<thead>
					<tr>
						<th field="roleName" width="100">角色名称</th>
						<th field="description" width="200">角色描述</th>
						<th field="privileges" width="400" 
							data-options="
								formatter: function(value,row,index){
									if(value){
										var privilegeNames = [];
										for(var i=0;i<value.length;i++){
											privilegeNames.push(value[i].privilegeName);
										}
										return privilegeNames.join(',');
									}else{
										return '';
									}
								}
							">授权</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:300px;padding:5px;"
			closed="true" modal="true">
		<form id="myform" method="post" style="margin:0;padding:0;width:375px;height:210px;">
			<div id="tabs" class="easyui-tabs" fit="true" plain="true">
				<div title="角色" style="padding:20px;">
					<table>
						<tr>
							<td>角色名称</td>
							<td>
								<input type="hidden" id="privilegeIds" name="privilegeIds">
								<input type="text" class="easyui-validatebox"
										style="width:230px;border:1px solid #ccc"
										name="roleName" required="true">
							</td>
						</tr>
						<tr>
							<td>角色描述</td>
							<td>
								<textarea style="width:230px;height:100px;border:1px solid #ccc;font-size:12px;"
										name="description"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div title="授权" style="padding:10px;">
					<%--此处是一个权限树 --%>
					<%--
					<c:forEach items="${types }" var="type">
						<div>
							<span>${type.name }</span>
							<c:forEach items="${type.privileges }" var="p">
								<div style="padding-left:10px;">
									<input type="checkbox" id="privilege-${p.id }" name="pids" value="${p.id }"></input>
									<span>${p.name }</span>
								</div>
							</c:forEach>
						</div>
					</c:forEach>
					--%>
					<ul id="privilege-tree" class="easyui-tree" 
						data-options="
							animate:true,
							lines:true,
							checkbox:true,
							url:'<c:url value='/sys/privilege/getPrivileges.mvc'/>'
						"></ul>
				</div>
			</div>
		</form>
		<div style="margin-top:10px;text-align:center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
	</div>
</body>
