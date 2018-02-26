<!-- http://localhost/view/home/login.jsp -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>系统登录</title>
    <link href="<c:url value="/resources/easyui/jquery-1.8.0.min.js"/>" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!--[if lt IE 9]>
      <script src="<c:url value="/bootstrap-3.3.7-dist/js/html5shiv.min.js"/>"></script>
      <script src="<c:url value="/bootstrap-3.3.7-dist/js/respond.min.js"/>"></script>
    <![endif]-->
    <style type="text/css">
    	body{
    	}
    </style>
  </head>
  <body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	  <div class="container">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="#">现代浏览器博物馆</a>
	    </div>
	
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li class="active"><a href="#">综述<span class="sr-only">(current)</span></a></li>
	        <li><a href="#">简述</a></li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">特点 <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="#">chrome</a></li>
	            <li><a href="#">firefox</a></li>
	            <li><a href="#">safari</a></li>
	            <li role="separator" class="divider"></li>
	            <li><a href="#">opera</a></li>
	            <li role="separator" class="divider"></li>
	            <li><a href="#">IE</a></li>
	          </ul>
	        </li>
	      </ul>
	      <form class="navbar-form navbar-right">
	        <div class="form-group">
	          <input type="text" class="form-control" placeholder="Search">
	        </div>
	      </form>
	    </div>
	  </div>
	</nav>
    <script src="<c:url value="/resources/easyui_v143_pro/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
  </body>
</html>