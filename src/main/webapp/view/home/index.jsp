<%-- 
	http://127.0.0.1:8080/mms/view/home/index.jsp
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>首页</title>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/portal.css"/>">
		<style type="text/css">
			.title{
				font-size:16px;
				font-weight:bold;
				padding:20px 10px;
				background:#eee;
				overflow:hidden;
				border-bottom:1px solid #ccc;
			}
			.t-list{
				padding:5px;
			}
			
		</style>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.portal.js"/>"></script>
</head>
<body>
		<%--
		<p style="color: gray;">&nbsp;&nbsp;首页显示区加统计表格，图表，待办任务等portal。border:1px solid #0A246A;</p>
		<div id="pp" > 
			<div  >
				<div title="Clock" collapsible="true" closable="true" style="height:260px;padding:5px;">
				</div> 
			    <div title="Tutorials" collapsible="true" closable="true" style="height:260px;padding:5px;" >
			    </div>
			</div>
			<div >
				<div title="DataGrid" closable="true" collapsible="true"  style="height:260px;padding:5px;">
				</div>
				<div  title="DataPortal" collapsible="true" closable="true" style="height:260px;padding:5px">
				</div>
			</div>
		</div>
		--%>
	</body>
</html>
<script type="text/javascript">
<%--
	$(function(){
		$('#pp').portal({
			border:false,
			fit:true

		});
		// add();
		
	});
	function add(){
		for(var i=0; i<3; i++){
			var p = $('<div/>').appendTo('body');
			p.panel({
				title:'Title'+i,
				content:'<div style="padding:5px;">Content'+(i+1)+'</div>',
				height:100,
				closable:true,
				collapsible:true
			});
			$('#pp').portal('add', {
				panel:p,
				columnIndex:i
			});
		}
		$('#pp').portal('resize');
	}
	function remove(){
		$('#pp').portal('remove',$('#pgrid'));
		$('#pp').portal('resize');
	}
--%>
</script>