<%--
	http://127.0.0.1:8080/mms/view/home/loginUI.jsp
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<title>济宁第二人民医院物资管理系统</title>
		<link rel="stylesheet" href="<c:url value="/resources/easyui_v143_pro/themes/bootstrap/easyui.css"/>" type="text/css" media="screen" />
		<link rel="stylesheet" href="<c:url value="/resources/easyui_v143_pro/themes/icon.css"/>" type="text/css" media="screen" />
		<link id="mainCss" rel="stylesheet" href="<c:url value="/resources/style/main.css"/>" type="text/css" media="screen" />
		<script src="<c:url value="/resources/easyui_v143_pro/jquery.min.js"/>"></script>
		<script src="<c:url value="/resources/easyui_v143_pro/jquery.easyui.min.js"/>"></script>
		<script src="<c:url value="/resources/easyui_v143_pro/locale/easyui-lang-zh_CN.js"/>"></script>
		<script type="text/javascript">
			//添加"回车"事件
	 		$(document).keydown(function(e){
	 			if(e.keyCode === 13){
	 				$('#mms-login').submit();
	 			}
	 		});
		</script>
	</head>
	<body style="visibility:visible">
		<div id="dlg" class="easyui-dialog" title="欢迎使用物资管理系统" style="width:280px;height:180px;padding:0px"
            data-options="
                buttons: [{
                    text:'登录',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var flag = $('#mms-login').form('validate')
                    	if(flag){
                    		$('#mms-login').submit();
                    	}
                    }
                },{
                    text:'重置',
                    handler:function(){
                    	$('#mms-login').form('clear');
                    }
                }]
            ">
			<form id="mms-login" action="<c:url value='/login/action.mvc'/>" method="post" style="padding-top:13px;padding-left:30px;">
				<table>
					<tr>
						<td>登录帐号: </td>
						<td>
							<input class="easyui-validatebox" required="true" name="loginName" style="width:125px;"/>
						</td>
					</tr>
					<tr>
						<td>登录密码: </td>
						<td>
							<input class="easyui-validatebox" required="true" type="password" name="password" style="width:125px;"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input id="autoLogin" name="autoLogin" type="checkbox"><label id="remAutoLoginTxt" for="autoLogin">两周内自动登录</label>
						</td>
					</tr>
				</table>
			</form>
    	</div>
	</body>
</html>