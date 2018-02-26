<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div style="padding:20px">
	<table cellpadding="0" cellspacing="0" class="form-table">
		<tr>
			<td style="width:70px" align="right">物资编码:</td>
			<td style="width:200px">
				<input name="materialNo" style="width:150px"></input>
			</td>
			<td style="width:60px" align="right">物资分类:</td>
			<td>
				<input id="materialClass" name="mc.id" class="easyui-combotree" style="width:154px" required="true"
						data-options="
							animate : true,
							lines : true,
							url:'<c:url value='/data/materialClass/getItems.mvc'/>',
							onLoadSuccess : function(node,data){
								var t = $(this);
								if(data){
									$(data).each(function(index,d){
										t.tree('expandAll');
									});
								}
							}
						"></input>
			</td>
		</tr>
		<tr>
			<td align="right">物资名称:</td>
			<td><input name="materialName" class="easyui-validatebox" required="true" style="width:150px"></input></td>
			<td align="right">物资规格:</td>
			<td><input name="materialSpec" style="width:150px"></input></td>
		</tr>
		<tr>
			<td align="right">计量单位:</td>
			<td>
				<input name="measureUnitId" class="easyui-combobox" style="width:154px"
						url="<c:url value='/data/measureUnit/getItems.mvc'/>"
						valueField="id" textField="unitName" required="true" id="measureUnitId"></input>
			</td>
			<td align="right">型号:</td>
			<td><input name="materialModel" style="width:150px"></input></td>
		</tr>
		<tr>
			<td align="right">是否需消毒:</td>
			<td>
				<select class="easyui-combobox" name="disinfection" style="width:154px">
					<option value="F" selected="selected">否</option>
					<option value="T">是</option>
				</select>
			</td>
			<td align="right">高值耗材:</td>
			<td>
				<select class="easyui-combobox" name="highValue" style="width:154px">
					<option value="F" selected="selected">否</option>
					<option value="T">是</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">零售价:</td>
			<td><input name="sellPrice" style="width:150px" class="easyui-validatebox" validType="floatOrInt['#sellPrice']" id="sellPrice"/></td>
			<td align="right">拼音码:</td>
			<td><input name="pym" style="width:150px"></input></td>
		</tr>
		<tr>
			<td align="right">品牌:</td>
			<td><input name="materialBrand" style="width:150px"></input></td>
			<td align="right">生产厂家:</td>
			<td><input name="factory" style="width:150px"></input></td>
		</tr>
		<tr>
			<td align="right">库存上限:</td>
			<td><input name="upperLimit" style="width:150px" id="upperLimit" class="easyui-validatebox" validType="integer['#upperLimit']"/></td>
			<td align="right">库存下限:</td>
			<td><input name="lowerLimit" style="width:150px" id="lowerLimit" class="easyui-validatebox" validType="integer['#lowerLimit']"/></td>
		</tr>		
		<tr>
			<td align="right" valign="top">备注:</td>
			<td colspan="3"><textarea name="remark" rows="3" cols="65"></textarea></td>
		</tr>
	</table>
</div>