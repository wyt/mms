/**
 * 选择供应商的组合表格
 */
(function($){
	$.fn.combointercourse = function(options){
		options = options || {};
		return this.each(function(){
			var opts = $.extend({
				panelWidth:500,
				idField:'id',
				textField:'shortName',
				mode:'remote',
				pagination:true,
				fit:true,
				fitColumns:true,
				columns:[[
					  {field:'leibie',title:'类别',width:80,sortable:true,},
					  {field:'code',title:'单位编码',width:60,sortable:true},
					  {field:'shortName',title:'单位名称',width:150,sortable:true},
					  {field:'contactMan',title:'联系人',width:60,sortable:true},
					  {field:'answerMan',title:'负责人',width:60,sortable:true}
				]]
			}, options);
			
			$(this).combogrid(opts);
		});
	};
})(jQuery);