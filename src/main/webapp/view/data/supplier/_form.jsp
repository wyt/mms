<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<script type="text/javascript">
$(function(){
	$.extend($.fn.validatebox.defaults.rules, {
		//验证电话或手机
		phone:{
			validator : function(value) {
				return /^(13|14|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
			},
			message:'请填入手机或电话号码,如13688888888或020-8888888'
		},
		zip : {// 验证邮政编码
			validator : function(value) {
				return /^[1-9]\d{5}$/i.test(value);
			},
			message : '邮政编码格式不正确'
		},
		fax:{// 验证传真
			validator : function(value) {
				return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
			},
			message : '传真号码不正确'
		},
		number : {// 验证整数
			validator : function(value) {
				return /^[+]?[1-9]+\d*$/i.test(value);
			},
			message : '请输入数字'
		},
		
	});
});
</script>

<div style="padding:20px">
	<table cellpadding="0" cellspacing="0" class="form-table">
		<tr>
			<td style="width:60px">编码</td>
			<td style="width:200px"><input name="code" style="width:150px"></input></td>
			<td style="width:60px">类别</td>
			<td>
				<input name="leibie" style="width:150px"></input>
			</td>
		</tr>
		<tr>
			<td>简称</td>
			<td><input name="shortName" class="easyui-validatebox" required="true" style="width:150px"></input></td>
			<td>全称</td>
			<td><input name="fullName" style="width:150px"></input></td>
		</tr>
		<tr>
			<td>地址</td>
			<td colspan="3"><input name="addr" style="width:410px"></input></td>
		</tr>
		<tr>
			<td>邮编</td>
			<td><input name="postcode" style="width:150px" class="easyui-validatebox e-input" validType="zip['#postcode']"></input></td>
			<td>电话</td>
			<td><input name="phone" style="width:150px" class="easyui-validatebox e-input" validType="phone['#phone']" ></input></td>
		</tr>
		<tr>
			<td>传真</td>
			<td><input name="fax" id="fax" style="width:150px" class="easyui-validatebox e-input" validType="fax['#fax']"></input></td>
			<td>网址</td>
			<td><input name="www" style="width:150px"></input></td>
		</tr>
		<tr>
			<td>电子邮箱</td>
			<td><input name="email" class="easyui-validatebox e-input" data-options="validType:'email'" style="width:150px"></input></td>
			<td>负责人</td>
			<td><input name="answerMan" style="width:150px"></input></td>
		</tr>
		<tr>
			<td>联系人</td>
			<td><input name="contactMan" style="width:150px"></input></td>
			<td>营业执照号</td>
			<td><input name="licence" style="width:150px"></input></td>
		</tr>
		<tr>
			<td>开户银行</td>
			<td><input name="bank"  style="width:150px"></input></td>
			<td>税务编码</td>
			<td><input name="taxCode" style="width:150px"></input></td>
		</tr>
		<tr>
			<td>银行帐号</td>
			<td><input name="account"  class="easyui-validatebox e-input" validType="number['#account']" style="width:150px"></input></td>
			<td>助记码</td>
			<td><input name="help" style="width:150px"></input></td>
		</tr>
		<tr>
			<td>备注</td>
			<td colspan="3"><input name="remark" style="width:410px"></input></td>
		</tr>
	</table>
</div>
