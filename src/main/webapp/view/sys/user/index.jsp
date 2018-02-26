<%-- 
    Description：用户管理的索引页
    Date：2012-11-13 18:49:38
    Author：王永涛
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
	<style type="text/css">
		.e-input{
			width:198px;
			border:1px solid #A4BED4;
			height:18px;
			line-height:18px;
		}
	</style>
	<script type="text/javascript">
		$(function()
		{
			init();
		});
		
		function init()
		{
			$('#dlg').dialog({
				onOpen:function(){
					$('#dt-roles').datagrid('resize');
				}
			});
			
			$('#t-departments').tree({
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
				onClick: function(node){
					$('#t-users').datagrid('reload', {departmentId:node.id});
				}
			});
			
			// 扩展验证
			$.extend($.fn.validatebox.defaults.rules, {
				confirm:{
					validator: function(value,param){
						return value == $(param[0]).val();
					},
					message:'密码确认不对'
				}
			});
			
			$.extend($.fn.validatebox.defaults.rules, {
				loginName: {
					// param 参数集合
					validator: function (value,param) {
						if (!/^[\w]+$/.test(value)) {
							$.fn.validatebox.defaults.rules.loginName.message = '用户名只能英文字母、数字及下划线的组合！';
							return false;
						} else {
							var result = $.ajax({
								url: '<c:url value="/sys/user/queryName.mvc"/>',
								data: 'name=' + value +"&editUserId=" + $(param[0]).val(),
								type: 'post',
								dataType: 'json',
								async: false,
								cache: false
							}).responseText;
							if (result == 'false') {
								$.fn.validatebox.defaults.rules.loginName.message = '用户名已存在！';
								return false;
							} else {
								return true;
							}
						}
					},
					message: ''
				}
			});
		}

		var actionUrl;
		
		/**
		 *	打开新增用户dialog
		 */
		function newItem(){
			// 打开dialog
			$('#dlg').dialog('setTitle', '新增用户资料').dialog('open');
			// 清空form的值
			$('#myform').form('clear');
			// 取消角色表格勾选的行
			$('#dt-roles').datagrid('clearSelections');
			// 定义新增用户的action
			actionUrl = '<c:url value="/sys/user/save.mvc"/>';
		}
		
		/**
		 * 编辑用户
		 */
		function editItem(){
			var t = $('#t-users');
			var row = t.datagrid('getSelected');
			$('#editUserId').val(row.id);
			if (row){
				var deptId = row.dept == null ? null : row.dept.id;
				$('#myform').form('load',{
					"id": row.id,
					"password": row.password,
					"userName": row.userName,
					"sex": row.sex,
					"email": row.email,
					"phoneNum": row.phoneNum,
					"inTime": row.inTime,
					"outTime": row.outTime,
					"loginName": row.loginName,
				});
				$('#confirmPassword').val(row.password);
				var dt = $('#dt-roles');
				dt.datagrid('clearSelections');
				var roles = row.roles;
				if (roles){
					for(var i=0;i<row.roles.length;i++){
						dt.datagrid('selectRecord',roles[i].id);
					}
				}
				$('#suoShuBuMen').combotree('setValue', deptId);
				$('#dlg').dialog('setTitle', '修改用户资料').dialog('open');
				actionUrl = '<c:url value="/sys/user/edit.mvc"/>?id=' + row.id;
			}
		}
		
		/**
		 *	保存用户
		 */
		function saveItem(){
			var ids = [];
			var roles = $('#dt-roles').datagrid('getSelections');
			for(var i=0; i<roles.length; i++){
				ids.push(roles[i].id);
			}
			$('#roleIds').val(ids.join(','));
			
			$('#myform').form('submit', {
				url:actionUrl,
				onSubmit:function(){
					return $('#myform').form('validate');
				},
				success:function(data){
					$('#dlg').dialog('close');
					$('#t-users').datagrid('reload');
				}
			});
		}
		
		/**
		 * 删除用户
		 */
		function removeItem(){
			var row = $('#t-users').datagrid('getSelected');
			if(row){
	            $.messager.confirm('警告', '是否真的删除该用户?', function(r){
	                if (r){
						$.ajax({
							type: 'post',
							dataType: 'json',
							url: '<c:url value="/sys/user/delete.mvc"/>',
							data: 'id=' + row.id,
							success: function(data){
								if(data.success){
									bottomRight(data.msg);
								}else{
									bottomRight(data.msg);
								}
								$('#t-users').datagrid('reload');
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
		 * 初始化密码
		 */
		function initPassword(){
			var t = $('#t-users');
			var row = t.datagrid('getSelected');
			if (row){
				  $.messager.confirm('警告', '确定初始化密码?', function(r){
		                if (r){
		                	$.ajax({
		        				type: 'post',
		        				dataType: 'json',
		        				url: '<c:url value="/sys/user/editInitPass.mvc"/>',
		        				data: 'id=' + row.id,
		        				success: function(data){
		        					if(data.success){
		        						bottomRight(data.msg);
		        					}else{
		        						bottomRight(data.msg);
		        					}
		        				}
		        			});
		                }
		            }); 
			}else{
				$.messager.show({
					title:'提示',
					msg:'请先选择用户，再初始化密码。'
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
			<div class="subtitle">用户管理</div>
			<div class="toolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">新增用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="initPassword()">初始化密码</a>
			</div>
		</div>
		<div region="west" border="false" style="border-right:1px solid #92B7D0;width:150px;padding:5px;">
			<ul id="t-departments" url="<c:url value='/sys/dept/getDepts.mvc'/>"></ul>
		</div>
		<div region="center" border="false">
			<table id="t-users" class="easyui-datagrid"
					url="<c:url value='/sys/user/getItems.mvc'/>"
					singleSelect="true" pagination="true" rownumbers="true"
					border="false" fit="true" fitColumns="true" nowrap="false">
				<thead>
					<tr>
						<th field="userName" width="70" sortable="true">用户名称</th>
						<th field="sex" width="25">性别</th>
						<th field="loginName" width="70">登录帐号</th>
						<th field="phoneNum" width="70" sortable="true">手机号码</th>
						<th field="email" hidden="true" width="80" sortable="true">联系邮箱</th>
						<th field="inTime" hidden="true" width="80" sortable="true">入职日期</th>
						<th field="outTime" hidden="true" width="80" sortable="true">离职日期</th>
						<th field="dept" width="70" sortable="true" 
							data-options="
								formatter: function(value,row,index){
									if(value){
										return value.deptName;
									}else{
										return '';
									}
								}
							">所属部门</th>
						<th field="roles" width="150" 
							data-options="formatter:function(value,row,index){
								if(value){
									var roles = [];
									for(var i=0;i<value.length;i++){
										roles.push(value[i].roleName);
									}
									return roles.join(',');
								}else{
									return '';
								}
							}">角色</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id="dlg" style="width:600px;height:400px;"
			class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttons">
		<div style="padding:10px;">
			<div style="float:left">
				<form id="myform" method="post" style="margin:0;padding:0">
					<table>
						<tr>
							<td style="width:80px">用户名称</td>
							<td><input type="text" class="easyui-validatebox e-input" name="userName" required="true"></input></td>
						</tr>
						<tr>
							<td>性别</td>
							<td>
								<select class="easyui-combobox" name="sex" panelHeight="60" style="width:60px">
									<option>男</option>
									<option>女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>所属部门</td>
							<td>
								<%--
								<input type="text" class="easyui-combotree"
										style="width:200px;"
										name="dept.id" 
										required="true"
										url="<c:url value='/system/department/getItems'/>">
								--%>
								<input id="suoShuBuMen" type="text" class="easyui-combotree" required="true"
										style="width:200px;"
										name="dept.id" 
										data-options="
											animate : true,
											lines : true,
											url:'<c:url value='/sys/dept/getDepts.mvc'/>',
											onLoadSuccess : function(node,data){
												var t = $(this);
												if(data){
													$(data).each(function(index,d){
														t.tree('expandAll');
													});
												}
											}
										">
							</td>
						</tr>
						<tr>
							<td>手机号码</td>
							<td><input type="text" class="e-input" name="phoneNum"></input></td>
						</tr>
						<tr>
							<td>联系邮箱</td>
							<td><input type="text" class="easyui-validatebox e-input" data-options="validType:'email'" name="email"></input></td>
						</tr>
						<tr>
							<td>入职时间</td>
							<td><input type="text" class="easyui-datebox e-input" name="inTime"></input></td>
						</tr>
						<tr>
							<td>离职时间</td>
							<td><input type="text" class="easyui-datebox e-input" name="outTime"></input></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<input type="hidden" id="roleIds" name="roleIds">
							</td>
						</tr>
						<tr>
							<td>登录帐号<input type="hidden" id="editUserId" /></td>
							<td><input type="text" class="easyui-validatebox e-input"  id="loginName" name="loginName" required="true" validType="loginName['#editUserId']"></input></td>
						</tr>
						<tr>
							<td>登录密码</td>
							<td><input type="password" class="easyui-validatebox e-input" id="password" name="password" required="true"></input></td>
						</tr>
						<tr>
							<td>确认密码</td>
							<td><input type="password" class="easyui-validatebox e-input" id="confirmPassword" name="confirmPassword" required="true" validType="confirm['#password']"></input></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="float:right">
				<table id="dt-roles" class="easyui-datagrid" style="width:200px;height:280px;"
						url="<c:url value='/sys/role/getItems2.mvc'/>"
						idField="id" fitColumns="true">
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th field="roleName" width="80">角色</th>
						</tr>
					</thead>
				</table>
			</div>
			<div style="clear:both"></div>
		</div>
		<div id="dlg-buttons" style="text-align:center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
	</div>
</body>