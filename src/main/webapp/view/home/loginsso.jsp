<%--
	http://127.0.0.1/mms/view/home/loginsso.jsp
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
	</head>
	<body>
	  <div class="wrapper">
	    <form class="form-signin" action="<c:url value='/login/action.mvc'/>" method="post">
	    <%-- <form class="form-signin" action="<c:url value='/ssologin/action.mvc'/>" method="post"> --%>       
	      <h2 class="form-signin-heading">Sign In</h2>
	      <input type="text" class="form-control" name="loginName" placeholder="Username" required="" autofocus="" /><br/>
	      <input type="password" class="form-control" name="password" placeholder="Password" required=""/>      
	  	  <div class="checkbox">
			<label>
				<input id="login-remember" type="checkbox" name="remember" value="1"> Remember me
			</label>
		  </div>
	      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
	    </form>
	  </div>
	</body>
</html>