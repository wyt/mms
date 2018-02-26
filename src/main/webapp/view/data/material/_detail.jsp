<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	String path = request.getContextPath();
%>   
<!DOCTYPE script PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<%-- 
		<link rel="stylesheet" href="<%=path %>/easyui/themes/default/easyui.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="<%=path %>/easyui/themes/icon.css" type="text/css" media="screen" />
		<script type="text/javascript" src="<%=path %>/easyui/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/easyui/locale/easyui-lang-zh_CN.js"></script>
		--%>
		
		<%-- 将easyui 1.3.1 替换为1.4.3 --%>
		<link rel="stylesheet" href="<c:url value="/resources/easyui_v143_pro/themes/bootstrap/easyui.css"/>" type="text/css" media="screen" />
		<link rel="stylesheet" href="<c:url value="/resources/easyui_v143_pro/themes/icon.css"/>" type="text/css" media="screen" />
		
		<script src="<c:url value="/resources/easyui_v143_pro/jquery.min.js"/>"></script>
		<script src="<c:url value="/resources/easyui_v143_pro/jquery.easyui.min.js"/>"></script>
		<script src="<c:url value="/resources/easyui_v143_pro/locale/easyui-lang-zh_CN.js"/>"></script>
		
		<%--图片播放插件 --%>
		<script type="text/javascript" src="<%=path %>/resources/js/jQuery.Slide.js"></script>
		<script type="text/javascript" src="<%=path %>/resources/js/Slide.js"></script>
		<style type="text/css">
			*{
				font-size:12px;
			}
			#detail_table tr{
				height: 25px;
			}
			.td_title{
			}
		    .bigImgContaint img{
		        width:400px;
		        height:325px;
		    }
		</style>
	</head>
	<body class = "easyui-layout">
		<div data-options="region:'center',border:false">
			<div id="div_BigImg" style="width:400px;height:325px;overflow-y:hidden;overflow-x:hidden;" class="bigImgContaint">
				<!--此处的Img一定要改在同一行，否则在IE7、8中每个图片后面会有空隙-->
		        <div style="width:5052px;overflow:hidden;">
			        <c:if test="${!empty miList}">
			    		<c:forEach items="${miList}" var="mi">
			        		<img id="${mi.id}"  src="<%=path %>/${mi.imageFullName}" title="${mi.imageName}"/>
			        	</c:forEach>
			        </c:if>
			        <c:if test="${empty miList}">
			        	<img id="img_Big_1" src="<%=path %>/images/1.jpg" title="${mi.imageName}"/>
			        </c:if>
		        </div>
		    </div>
		    <div id="div_Slide" style="width:280px;height:70px;border:solid 0px #fff;position:absolute;">
		    	<c:if test="${!empty miList}">
		    		<c:forEach items="${miList}" var="mi">
		        		<img src="<%=path %>/${mi.imageFullName}" data="${mi.id}" title="${mi.imageName}" imageId="${mi.id}"/>
		        	</c:forEach>
		        </c:if>
		        <c:if test="${empty miList}">
		        	<img src="<%=path %>/resources/images/1.jpg" data="img_Big_1" title="${mi.imageName}"/>
		        </c:if>
		    </div>
		    <table width="100%">
		    	<tr>
		    		<td >
		    			<input type="text" id="imageName"> <input type="button" value="删除图片" onclick="deleteImage();">
		    			<input type="hidden" id="imageId">
		    		</td>
		    	</tr>
		    </table>
	    </div>
	    <div data-options="region:'east'" style="width: 340px;">
	    	<table cellpadding="0" cellspacing="0" width="100%" style="border-bottom: 1px solid #ddd" id="detail_table">
				<tr>
					<td align="right" width="23%" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">物资编码:</td>
					<td  width="28%">
						${material.materialNo }
					</td>
					<td  align="right" width="20%" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">物资分类:</td>
					<td width="29%">
						${material.mc.materialClassName }
					</td>
				</tr>
				<tr>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">物资名称:</td>
					<td>
						${material.materialName}
					</td>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">物资规格:</td>
					<td>
						${material.materialSpec}
					</td>
				</tr>
				<tr>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">计量单位:</td>
					<td>
						${material.measureUnit.unitName}
					</td>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">型号:</td>
					<td>
						${material.materialModel}
					</td>
				</tr>
				<tr>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">是否需消毒:</td>
					<td>
						${material.disinfection=="T"?"是":"否"}
					</td>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">高值耗材:</td>
					<td>
						${material.highValue=="T"?"是":"否"}
					</td>
				</tr>
				<tr>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">零售价:</td>
					<td>
						${material.sellPrice}
					</td>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">拼音码:</td>
					<td>
						${material.pym}
					</td>
				</tr>
				<tr>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">品牌:</td>
					<td>
						${material.materialBrand }
					</td>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">生产厂家:</td>
					<td>
						${material.factory }
					</td>
				</tr>
				<tr>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">库存上限:</td>
					<td>
						${material.upperLimit }
					</td>
					<td align="right" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">库存下限:</td>
					<td>
						${material.lowerLimit }
					</td>
				</tr>		
				<tr style="height: 118px;">
					<td align="right" valign="top" style="border-bottom: 1px solid #FFFFFF" bgcolor="#e5e5e5">备注:</td>
					<td align="left" valign="top" colspan="3" style="word-break:break-all;">${material.remark }</td>
				</tr>
			</table>
			<form id="saveFile" method="post" enctype="multipart/form-data">
				<table  border="0" width="100%" cellspacing="0" cellpadding="0" align="center">
					<tbody>
						<tr>
							<td align="left" colspan="2">图片上传：</td>
							<td></td>
						</tr>
						<tr>
							<td width="80%"> 
								<input type="hidden" name="mid" value="${material.id }">
								<input name="uploadFile" type="File" id="filePath">
							</td>
							<td width="20%">
								<input type="button" value="上传图片" onclick="upFile();" >
							</td>
						</tr>
					</tbody>
				</table>
			</form>
	    </div>
	    <script type="text/javascript">
		    function upFile(){
				if($('#filePath').val()!=null&&$('#filePath').val()!=''){
					console.info($('#saveFile'));
					$('#saveFile').form('submit', {   
					    url:'<%=path%>/data/material/saveFile.mvc',   
					    success:function(data){   
					    	window.location.reload();
					    }   
					});  
				}else{
					alert('请选择文件');
				}
			}
		    function deleteImage(){
				var val = $('#imageId').val();
				if (val){
					 $.messager.confirm('警告', '是否真的删除该图片?', function(r){
			                if (r){
								$.ajax({
									type: 'post',
									dataType: 'json',
									url: '<c:url value="/data/material/deleteImage.mvc"/>',
									data: 'id=' + val,
									success: function(data){
										window.location.reload();
									},
									error: function(){
										$.messager.alert('错误提示','系统内部错误!','error'); // 后台抛出异常时
									}
								});
			                }
			            });  
				}else{
					alert('请选择要删除的图片');
				}
			}
	    </script>
	</body>
</html>


    