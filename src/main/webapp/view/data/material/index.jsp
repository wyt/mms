<%-- 
    Document   : main
    Created on : 2012-11-13 13:29:54
    Author     : 王永涛
--%>
<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%
	String path = request.getContextPath();
%>   
<html style="height:100%">
    <head>
        <jsp:include page="/decorators/_head.jsp"></jsp:include>
       	<script type="text/javascript" src="<c:url value='/js/commons.js'/>"></script>
       	<%--可展开的DataGrid所需要的js扩展文件 --%>
       	<script src="<c:url value="/resources/js/datagrid-detailview.js"/>"></script>
       	<script src="<c:url value="/resources/js/validate.js"/>"></script>
		<script type="text/javascript">
			$(function(){
				$('#t-types').tree({
					onClick:function(){
						normalQuery();
					}
				});
				
				$('#dt-goods').datagrid({
					view: detailview,
					detailFormatter:function(index,row){
						return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
					},
					onExpandRow: function(index,row){
						$('#ddv-'+index).panel({
							height:368,
							border:false,
							cache:false,
							//href:'<c:url value="/data/material/detail.mvc"/>',
							content:'<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="<%=path%>/data/material/detail.mvc?id='+row.id+'" style="width:100%;height:100%;overflow:hidden" ></iframe>',
							onLoad:function(){
								$('#dt-goods').datagrid('fixDetailRowHeight',index);
							}
						});
						$('#dt-goods').datagrid('fixDetailRowHeight',index);
					},
					onCollapseRow:function(index,row){
						var frame = $('iframe', this);
						try {
							if (frame.length > 0) {
								for ( var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
								}
								frame.remove();
								if ($.browser.msie) {
									CollectGarbage();
								}
							}
						} catch (e) {
						}
					}
				});
			});
			function normalQuery(value){
				var node = $('#t-types').tree('getSelected');
				$('#dt-goods').datagrid('load',{
					goodTypeId: (node?node.id:""),
					q: value
				});
			}
			function advanceQuery(){
				showQueryDialog({
					width:350,
					height:250,
					form:'<c:url value="/view/data/material/_query.jsp"/>',
					callback:function(data){
						var node = $('#t-types').tree('getSelected');
						data.goodTypeId = (node?node.id:"");
						//$('#q').val(data.name);
						$('#q').searchbox('setValue',data.name);
						$('#dt-goods').datagrid('load',data);
					}
				});
			}
			
			var url;
			function newItem(){
				$('#dlg').dialog('setTitle','新增商品资料').dialog('open');
				$('#myform').form('clear');
				$('#myform #sellPrice').val('0.00');
				$('#myform #upperLimit').val('0');
				$('#myform #lowerLimit').val('0');
				var node = $('#t-types').tree('getSelected');
				if (node){
					$('#goodTypeId').combotree('setValue',node.id);
				}
				url = '<c:url value="/data/material/save.mvc"/>';
			}
			function editItem(){
				var row = $('#dt-goods').datagrid('getSelected');
				if (row){
					$('#myform').form('load',row);
					$('#materialClass').combotree('setValue',row.mc.id);
					$('#measureUnitId').combobox('setValue',row.measureUnit.id);
					$('#dlg').dialog('setTitle','修改商品资料').dialog('open');
					url = '<c:url value="/data/material/edit.mvc"/>?id=' + row.id;
				} else {
					$.messager.show({
						title:'提示',
						msg:'请先选择商品资料，再进行修改。'
					});
				}
			}
			function saveItem(){
				$('#myform').form('submit',{
					url:url,
					onSubmit:function(){return $(this).form('validate');},
					success:function(){
						$('#dlg').dialog('close');
						$('#dt-goods').datagrid('reload');
					}
				});
			}
			function deleteItem(){
				var node = $('#dt-goods').datagrid('getSelected');
				if (node){
					 $.messager.confirm('警告', '是否真的删除该分类?', function(r){
			                if (r){
								$.ajax({
									type: 'post',
									dataType: 'json',
									url: '<c:url value="/data/material/delete.mvc"/>',
									data: 'id=' + node.id,
									success: function(data){
										$('#deptTree').tree('reload');
									},
									error: function(){
										$.messager.alert('错误提示','系统内部错误!','error'); // 后台抛出异常时
									}
								});
			                }
			            });  
				}
			}
		</script>
    </head>
    <body style="margin:0;padding:0;height:100%;background:#F2FBFF">
    	<div id="loading-mask"><div id="loading-msg">正在加载...</div></div>
    	<div class="mainwrap">
    		<div id="mainlayout" class="easyui-layout" fit="true">
    			<div region="north" border="false" style="height:94px;background:#A1C4ff">
					<jsp:include page="/decorators/_north.jsp"></jsp:include>
    			</div>
    			<div region="center" border="false" style="overflow:hidden">
					<div class="easyui-layout" fit="true">
						<div region="north" border="false">
							<div class="subtitle">物资详情</div>
							<div class="toolbar">
								<table cellpadding="0" cellspacing="0" style="width:100%">
									<tr>
										<td>
											<a href="javascript:newItem()" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
											<a href="javascript:editItem()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
<!-- 											<a href="javascript:deleteItem()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a> -->
										</td>
										<td style="text-align:right">
											<input id="q" class="easyui-searchbox" prompt="按编码|名称查询" searcher="normalQuery"></input>
											<a href="javascript:advanceQuery()" class="easyui-linkbutton" plain="true">高级查询</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div region="west" border="false" style="width:150px;padding:5px;border-right:1px solid #92B7D0">
							<ul id="t-types" url="<c:url value='/data/materialClass/getItems.mvc'/>"></ul>
						</div>
						<div region="center" border="false">
							<table id="dt-goods" class="easyui-datagrid" url="<c:url value='/data/material/getItems.mvc'/>"
									fit="true" fitColumns="true" border="false" 
									pagination="true" singleSelect="true">
								<thead>
									<tr>
										<th field="mc" width="80" sortable="true" 
											data-options="
												formatter: function(value,row,index){
													if(value){
														return value.materialClassName;
													}else{
														return '';
													}
												}
											">类别</th>
										<th field="materialNo" width="80" sortable="true">编码</th>
										<th field="materialName" width="100" sortable="true">名称</th>
										<th field="measureUnit" width="60"
											data-options="
												formatter: function(value,row,index){
													if(value){
														return value.unitName;
													}else{
														return '';
													}
												}
											">单位</th>
										<th field="materialModel" width="100">型号</th>
										<th field="materialSpec" width="100">规格</th>
										<th field="factory" width="100">厂家</th>
										<th field="materialBrand" width="100">品牌</th>
									</tr>
								</thead>
							</table>
							<script type="text/javascript">
								function formatType(value,row){
									if (row.goodType){
										return row.goodType.name;
									} else {
										return value;
									}
								}
							</script>
						</div>
					</div>
					<div id="dlg" class="easyui-dialog" style="width:550px;height:370px;"
							closed="true" modal="true" buttons="#dlg-buttons">
						<form id="myform" method="post">
							<jsp:include page="_form.jsp"></jsp:include>
						</form>
						<div id="dlg-buttons" style="text-align:center">
							<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
						</div>
					</div>	
    			</div>
   				<div region="south" border="false">
   					<jsp:include page="/decorators/_south.jsp"></jsp:include>
   				</div>
    		</div>
    	</div>
    </body>
</html>