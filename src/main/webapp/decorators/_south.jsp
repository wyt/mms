<%--
		底部版权信息公共页面
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<div class="footer">
	<div border="false" style="height:4px;background:#A1C4ff"></div>
	<p style="margin: 12px 10px 0 10px;font-size: 12px;text-align: center;color: #ffffff;">系统设计: 济宁远望软件技术有限公司&nbsp;&nbsp;电话: 0537-3281578</p>
	<p style="margin: 7px 10px 0 10px;font-size: 12px;text-align: center;color: #ffffff;">版权所有: ©2012-<span id="bqyear"></span> 济宁市第二人民医院信息中心</p>
	<script type="text/javascript">
		var d = new Date();
		document.getElementById("bqyear").innerHTML = d.getFullYear();
	</script>
</div>				