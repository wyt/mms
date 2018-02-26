<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
	<script type="text/javascript">
	
		function openEdit(){
			$('#myform').form('clear');
			$('#dlg').dialog('setTitle', '修改密码').dialog('open');
		}
		
		function saveItem(){
			$('#myform').form('submit', {
				url: '<c:url value="/sys/user/editPassword.mvc"/>',
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
				}
			});
		}
		
		function bottomRight(msg){
			$.messager.show({
				title:'消息提示',
				msg: msg,
				showType:'show'
			});
		}
		
		$(function(){
			init();
		});
		function init(){
			
			$.extend($.fn.validatebox.defaults.rules,{
				pwd : {
					// param 参数集合
					validator: function (value) {
						var result = $.ajax({
							url: '<c:url value="/sys/user/queryPass.mvc?pass='+value+'"/>',
							data: value,
							type: 'post',
							dataType: 'json',
							async: false,
							cache: false
						}).responseText;
						if (result == 'false') {
							$.fn.validatebox.defaults.rules.pwd.message = '原密码不正确！';
							return false;
						} else {
							return true;
						}
					},
					message: ''
				},
				confirm:{
					validator: function(value,param){
						return value == $(param[0]).val();
					},
					message:'两次密码不一致'
				}
			});
		}
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">个人设置</div>
			<div class="toolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEdit()">修改密码</a>
			</div>
		</div>
		<div region="center" border="false">
		</div>
		
		<div id="dlg" class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttons" style="padding:10px;width:350px;height:210px;">
		<form id="myform" action="" method="post">
			<table>
				<tr>
					<td>原密码</td>
					<td><input type="password" class="easyui-validatebox e-input" id="password" name="oldpassword" 
						style="width:230px;border:1px solid #ccc" required="true" validType="pwd['#password']" ></input>
						
					</td>
				</tr>
				<tr>
					<td>新密码</td>
					<td><input type="password" class="easyui-validatebox e-input" id="newPassword" name="password" 
					style="width:230px;border:1px solid #ccc" required="true"></input></td>
				</tr>
				<tr>
					<td>确认密码</td>
					<td><input type="password" class="easyui-validatebox e-input" id="confirmPassword" name="confirmPassword" 
					style="width:230px;border:1px solid #ccc" required="true" validType="confirm['#newPassword']"></input></td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons" style="text-align:center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
	</div>
		
	</div>
</body>