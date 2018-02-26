<%--
	http://127.0.0.1/mms/view/home/regist.jsp
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>SSO认证中心</title>
	<link href="<c:url value="/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>" rel="stylesheet">
		<!--[if lt IE 9]>
	      <script src="<c:url value='/bootstrap-3.3.7-dist/js/html5shiv.min.js'/>"></script>
	      <script src="<c:url value='/bootstrap-3.3.7-dist/js/respond.min.js'/>"</script>
	    <![endif]-->
		<style type="text/css">
			/* @import "bourbon"; */
			body {
				background: #eee !important;	
			}
			.wrapper {	
				margin-top: 80px;
			  margin-bottom: 80px;
			}
			.form-signin {
			  max-width: 380px;
			  padding: 15px 35px 45px;
			  margin: 0 auto;
			  background-color: #fff;
			  border: 1px solid rgba(0,0,0,0.1);  
			  .form-signin-heading,
				.checkbox {
				  margin-bottom: 30px;
				}
				.checkbox {
				  font-weight: normal;
				}
				.form-control {
				  position: relative;
				  font-size: 16px;
				  height: auto;
				  padding: 10px;
					@include box-sizing(border-box);
					&:focus {
					  z-index: 2;
					}
				}
				input[type="text"] {
				  margin-bottom: -1px;
				  border-bottom-left-radius: 0;
				  border-bottom-right-radius: 0;
				}
				input[type="password"] {
				  margin-bottom: 20px;
				  border-top-left-radius: 0;
				  border-top-right-radius: 0;
				}
			}
		</style>
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script src="<c:url value='/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js'/>"></script>
		<script type="text/javascript">  
	        function refreshImg(){
	            var img = document.getElementById("captchaImg");
	            img.src = "http://192.168.9.30/mms/open/api/captcha?rnd=" + Math.random();
	        }
	        function sendSMS(){
	        	alert(111);
	        }
	        
	    </script>  
	</head>
	<body>
	  <div class="wrapper">
	    <form class="form-signin form-group-lg" method="post">
	      <h2 class="form-signin-heading">用户注册</h2>
	      <input type="text" class="form-control" name="loginName" placeholder="手机号" autofocus="" /><br/>
	      <input type="text" class="form-control" name="loginName" placeholder="验证码" autofocus="" />
	      <!--
	      <input id = "captchaImg" type="image" src="http://192.168.9.30/mms/open/api/captcha" onclick="refreshImg();" style="float: right;margin-top: -43px;margin-right: 4px;">
		  -->
		  <img id = "captchaImg" src="http://192.168.9.30/mms/open/api/captcha" onclick="refreshImg();" style="float:right;margin-top:-43px;margin-right:4px;cursor:pointer">
	      <br/>
	      <div class="input-group">
			<input type="text" class="form-control" placeholder="短信验证码" autofocus="">
			<span class="input-group-btn">
				<button class="btn btn-lg" type="button" onclick="sendSMS();">发送</button>
			</span>
		  </div><br/>
	      <input type="password" class="form-control" name="password" placeholder="密码" /><br/>
	      <input type="password" class="form-control" name="password" placeholder="确认密码" /><br/>
	      <button class="btn btn-lg btn btn-block" type="submit">注册</button>   
	    </form>
	  </div>
	</body>
</html>