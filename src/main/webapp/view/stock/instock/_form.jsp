<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%--按钮Bar --%>
<script language="javascript" src="<c:url value='/resources/js/LodopFuncs.js'/>"></script> 
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>  
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed> 
</object>
<div style="padding:5px;background:#fafafa;border-bottom:1px solid #eee;">
	<table cellpadding="0" cellspacing="0" style="width:100%">
		<tr>
			<td>
				<c:if test="${editable==true }">
					<a id="btn-submit1" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="javascript:submitBill(1)">保存单据</a>
					<a id="btn-submit2" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-saves" onclick="javascript:submitBill(2)">保存并新建</a>
				</c:if>
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-print" onclick="preview();">打印</a>
			</td>
			<td style="text-align:right">
				<c:if test="${editable==true }">
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="javascript:$('#dg-details').detailgrid('addGood')">添加物资</a>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="javascript:$('#dg-details').detailgrid('delGood')">删除物资</a>
				</c:if>
			</td>
		</tr>
	</table>
</div>

<div style="padding:10px">
	<div style="padding-left:90px">
		<table cellpadding="0" cellspacing="0" class="form-table">
			<tr>
				<td style="width:60px">单据编号</td>
				<td style="width:260px"><input name="billNo" value="${bill.billNo }" readOnly="true" style="width:174px" id="billNo"></input></td>
				<td style="width:60px">单据日期</td>
				<td><input name="billDate" class="easyui-datebox" value="${bill.billDate }" id="billDate"></input></td>
			</tr>
			<tr>
				<td>供应商</td>
				<td>
					<input id="intercourseId" name="intercourseId" value="${bill.intercourseId }" required="true" style="width:180px"></input>
				</td>
				<td>入库仓库</td>
				<td>
					<input name="depotId" class="easyui-combobox" style="width:180px"
							value="${bill.depotId}"
							url="<c:url value='/data/depot/getItems.mvc'/>"
							valueField="id" textField="depotName"
							required="true"></input>
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td colspan="3"><input name="remark" value="${bill.remark }" style="width:494px"></input></td>
			</tr>
		</table>
	</div>
	<div id="printBill" style="margin:10px 0;">
		<table id="dg-details" style="height:200px"
				url="<c:url value="${detailUrl}"/>"
				fitColumns="true" singleSelect="true"
				rownumbers="true" showFooter="true">
			<thead>
				<tr>
					<th field="materialNo" width="100">物资编码</th>
					<th field="materialName" width="120">物资名称</th>
					<th field="materialModel" width="100">型号</th>
					<th field="materialSpec" width="100">规格</th>
					<th field="measureUnitId" width="60">单位</th>
					<th field="billCount" width="80" align="right" editor="numberbox">数量</th>
					<th field="billPrice" width="100" align="right" formatter="formatDecimal" editor="{type:'numberbox',options:{precision:2}}">单价</th>
					<th field="billCost" width="100" align="right" formatter="formatDecimal">合价</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="margin-top:10px">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="width:50px;text-align:right">制单人：</td>
				<td style="width:100px;text-align:center;border-bottom:1px solid #ccc">${makeBillUser.userName }
				<input type="hidden" id="userName" value="${makeBillUser.userName }"></td>
				<td style="width:80px;text-align:right">制单时间：</td>
				<td style="width:150px;text-align:center;border-bottom:1px solid #ccc">${bill.makeBillDate}</td>
				<td>&nbsp;</td>
				<td style="width:50px;text-align:right"></td>
				<td style="width:50px;text-align:center;border-bottom:0px solid #ccc"></td>
				<td style="width:80px;text-align:right"></td>
				<td style="width:130px;text-align:center;border-bottom:0px solid #ccc"></td>
			</tr>
		</table>
	</div>
</div>
<input id="goods" name="goods" type="hidden"></input>
<input id="bill-editable" type="hidden" value="${editable }"></input>
<script type="text/javascript">
	$('#intercourseId').combointercourse({
		url:'<c:url value="/data/supplier/getItems.mvc"/>',
		pageList:[5,10,20]
	});
	<%--
	$('#intercourseId').combogrid('setText','${bill.intercourse.shortName}');
	--%>
	$('#dg-details').detailgrid({
		editable: ($('#bill-editable').val()=='true')
	});
	
	function preview(){
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_样式风格");
		LODOP.ADD_PRINT_HTM(0,0,"100%","100%",setPrintView());
		LODOP.PREVIEW();
	}
	
	function setPrintView(){
		var info = [];
		info.push("<style>");
			info.push("*{font-size:12px;}");
			info.push("#tablebody{height:350px;}");
			info.push("#details {border-collapse: collapse; border-style: solid;border-width:2px;border-color: black;}");
			info.push("#details th {border-collapse: collapse; border-style: solid;border-width:1px;border-color: black; font-size:16px;}");
			info.push("#details td {border-collapse: collapse; border-style: solid;border-width:1px;border-color: black;height:20px;}");
			info.push("#billTitle {font-size:18px;}");
			info.push("#billHead td{font-size:14px;}");
			info.push("#billFoot td{font-size:14px;}");
		info.push("</style>");
		var conunt=$('#dg-details').datagrid('getRows').length;
		var page = 0;
		var pageSize=5;
		if (conunt%pageSize > 0 ){ 
			page = Math.floor(conunt/pageSize) + 1; 
		} else{
			page = Math.floor(conunt/pageSize); 
		}
		// alert(page);
		for(var x=0;x<page;x++){
			info.push("<table border=0 width=100% id=tablebody>");
				info.push("<tr>");
					info.push("<td align=center valign=top><br>");
					info.push(setContent(x,pageSize));
					info.push("</td>");
				info.push("<tr>");
			info.push("</table>");
		}
		return info.join('');
	}
	function setContent(x,pageSize){
		var info = [];
		info.push("<table border=0 width=100%>");
			info.push("<tr>");
				info.push("<td align=center id=billTitle>");
					info.push("济宁市第二人民医院设备科-材料&nbsp;入库单");
				info.push("</td>");
			info.push("</tr>");
		info.push("</table>");	
		info.push("<table border=0 width=100% id=billHead>");
			info.push("<tr>");
				info.push("<td width=10%>");
					info.push('供应商：');
				info.push("</td>");
				info.push("<td align=left width=30%>");
					info.push($("#intercourseId").combobox('getText'));
				info.push("</td>");
				info.push("<td width=10%>");
					info.push('入库日期：');
				info.push("</td>");
				info.push("<td align=left width=30%>");
					info.push($("#billDate").datebox('getText'));
				info.push("</td>");
				info.push("<td width=20% align=left>");
				info.push("</td>");
			info.push("</tr>");
		info.push("</table>");
		info.push("<table border=0 id=details>");
		info.push(document.getElementById("dg-details").innerHTML);
		for(var i=(x)*pageSize;i<(x+1)*pageSize;i++){
			var row = $('#dg-details').datagrid('getRows')[i];
			info.push("<tr><td>");
			if(row!=null&&row.materialNo!=null){
				info.push(row.materialNo);
			}
			info.push("</td><td>");
			if(row!=null&&row.materialName!=null){
				info.push(row.materialName);
			}
			info.push("</td><td>");
			if(row!=null&&row.materialModel!=null){
				info.push(row.materialModel);
			}
			info.push("</td><td>");
			if(row!=null&&row.materialSpec!=null){
				info.push(row.materialSpec);
			}
			info.push("</td><td>");
			if(row!=null&&row.measureUnitId!=null){
				info.push(row.measureUnitId);
			}
			info.push("</td><td align=right>");
			if(row!=null&&row.billCount!=null){
				info.push(row.billCount);
			}
			info.push("</td><td align=right>");
			if(row!=null&&row.billPrice!=null){
				info.push(row.billPrice);
			}
			info.push("</td><td align=right>");
			if(row!=null&&row.billCost!=null){
				info.push(row.billCost);
			}
			info.push("</td><tr>");
		}
		var rows = $('#dg-details').datagrid('getFooterRows');
		info.push("<tr><td>");
		info.push("合计：");
		info.push("</td><td>");
		info.push("</td><td>");
		info.push("</td><td>");
		info.push("</td><td>");
		info.push("</td><td align=right>");
		info.push(rows[0].billCount);
		info.push("</td><td align=right>");
		info.push("</td><td align=right>");
		info.push(rows[0].billCost);
		info.push("</td><tr>");
		info.push("</table>");
		info.push("<table border=0 width=100% id=billFoot>");
			info.push("<tr>");
				info.push("<td align=right width=20%>");
					info.push('会计：');
				info.push("</td>");
				info.push("<td align=left width=20%>");
					info.push($("#userName").val());
				info.push("</td>");
				info.push("<td align=right width=10%>");
					info.push('保管：');
				info.push("</td>");
				info.push("<td align=left width=20%>");
				info.push("</td>");
				info.push("<td align=right width=10%>");
					info.push('采购人：');
				info.push("</td>");
				info.push("<td align=left width=20%>");
				info.push("</td>");
			info.push("</tr>");
		info.push("</table>");
		return info.join('');
	}
	function accDiv(arg1,arg2){
		var t1=0,t2=0,r1,r2;
		try{t1=arg1.toString().split(".")[1].length}catch(e){}
		try{t2=arg2.toString().split(".")[1].length}catch(e){}
		with(Math){
			r1=Number(arg1.toString().replace(".",""))
			r2=Number(arg2.toString().replace(".",""))
			return (r1/r2)*pow(10,t2-t1);
		}
	}


</script>