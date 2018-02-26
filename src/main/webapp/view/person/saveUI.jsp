<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>人员注册</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<body>
		<h1>
			<font color="red">Save Person</font>
		</h1>
		<form method="post" action="${pageContext.request.contextPath }/person/addPerson.do">
			Name: <input type="text" name="name" /><br />
			Sex: <input type="text" name="sex" /><br />
			<input type="submit" name="提交" />
		</form>
	</body>
</html>