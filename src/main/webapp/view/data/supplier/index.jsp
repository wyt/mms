<%-- 
    Description：供应商管理的索引页
    Date：2012-11-19 9:44:14
    Author：王永涛
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
	<script type="text/javascript" src="<c:url value='/js/commons.js'/>"></script>
	<script type="text/javascript">
		$(function(){
			$('#t-types').tree({
				onClick:function(){
					normalQuery();
				}
			});
		});
		function normalQuery(value){
			$('#dt-intercourses').datagrid('reload',{
				q: value
			});
		}
		function advanceQuery(){
			showQueryDialog({
				width:350,
				height:230,
				form:'<c:url value="/view/data/supplier/_query.jsp"/>',
				callback:function(data){
					$('#dt-intercourses').datagrid('reload',{
						code : data.code,
						name : data.name,
						addr : data.addr
					});
				}
			});
		}
		
		var url;
		function newItem(){
			$('#dlg').dialog('setTitle','新增供应商').dialog('open');
			$('#myform').form('clear');
			var node = $('#t-types').tree('getSelected');
			if (node){
				$('#intercourseTypeId').combotree('setValue',node.id);
			}
			url = '<c:url value="/data/supplier/save.mvc"/>';
		}
		function editItem(){
			var row = $('#dt-intercourses').datagrid('getSelected');
			if (row){
				$('#dlg').dialog('setTitle','修改供应商').dialog('open');
				$('#myform').form('load',row);
				url = '<c:url value="/data/supplier/edit.mvc"/>?id=' + row.id;
			} else {
				$.messager.show({
					title:'提示',
					msg:'请先选择供应商，再进行修改。'
				});
			}
		}
		function saveItem(){
			$('#myform').form('submit',{
				url:url,
				onSubmit:function(){return $(this).form('validate');},
				success:function(){
					$('#dlg').dialog('close');
					$('#dt-intercourses').datagrid('reload');
				}
			});
		}
		function deleteItem(){
			var row = $('#dt-intercourses').datagrid('getSelected');
			if(row){
	            $.messager.confirm('警告', '是否真的删除该供应商?', function(r){
	                if (r){
						$.ajax({
							type: 'post',
							dataType: 'json',
							url: '<c:url value="/data/supplier/delete.mvc"/>',
							data: 'id=' + row.id,
							success: function(data){
								if(data.success){
									bottomRight(data.msg);
								}else{
									bottomRight(data.msg);
								}
								$('#dt-intercourses').datagrid('reload');
							},
							error: function(){
								$.messager.alert('错误提示','系统内部错误!','error'); // 后台抛出异常时
							}
						});
	                }
	            });  
			}
		}
		function bottomRight(msg){
			$.messager.show({
				title:'消息提示',
				msg: msg,
				showType:'show'
			});
		}
	</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" border="false">
			<div class="subtitle">供应商管理</div>
			<div class="toolbar">
				<table cellpadding="0" cellspacing="0" style="width:100%">
					<tr>
						<td>
							<a href="javascript:newItem()" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
							<a href="javascript:editItem()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
							<a href="javascript:deleteItem()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
						</td>
						<td style="text-align:right">
							<%--
								search指定当点击搜索框放大镜所要执行的函数
							--%>
							<input id="q" class="easyui-searchbox" prompt="按编码|名称|助记码查询" searcher="normalQuery"></input>
							<a href="javascript:advanceQuery()" class="easyui-linkbutton" plain="true">高级查询</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div region="center" border="false">
			<table id="dt-intercourses" class="easyui-datagrid"
					url="<c:url value='/data/supplier/getItems.mvc'/>"
					fit="true" fitColumns="true" border="false" 
					pagination="true" rownumbers="true"
					singleSelect="true">
				<thead>
					<tr>
						<th field="leibie" width="80" sortable="true" formatter="formatType">类别</th>
						<th field="code" width="80" sortable="true">编码</th>
						<th field="shortName" width="100" sortable="true">名称</th>
						<th field="postcode" width="60" sortable="true">邮编</th>
						<th field="addr" width="200" sortable="true">地址</th>
						<th field="phone" width="100" sortable="true">电话</th>
						<th field="fax" width="100" sortable="true">传真</th>
						<th field="contactMan" width="100" sortable="true">联系人</th>
					</tr>
				</thead>
			</table>
			<script type="text/javascript">
				function formatType(value,row){
					if (row.intercourseType){
						return row.intercourseType.name;
					} else {
						return value;
					}
				}
			</script>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width:550px;height:360px"
			closed="true" modal="true" buttons="#dlg-buttons">
		<form id="myform" method="post">
			<jsp:include page="_form.jsp"></jsp:include>
		</form>
		<div id="dlg-buttons" style="text-align:center">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
	</div>
</body>
