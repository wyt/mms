<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String[] code = {"135792468","123698570","135792122","132392133","135792486","133698555","135792999","13236796","111279218","145698563","155792456","150392558"};//条形码内容
	//String code = "135792468";//条形码内容
//> 	String code1 = "123698569";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css" media="print">
.noprint { display:none;}
</style>
<script language="javascript">
	NS4 = (document.layers) ? 1 : 0;
	visble_property_prefix = (NS4) ? "document.layers." : "";
	visble_property_suffix = (NS4) ? ".visibility" : ".style.display";
	visble_property_true = (NS4) ? "show" : "block";
	visble_property_false = (NS4) ? "hide" : "none";
	visble_property_printview = visble_property_prefix + "viewpanel" + visble_property_suffix;
	
	function nowprint() {
		window.print();
	}
	 function onbeforeprint() {
		eval(visble_property_printview + " = \"" + visble_property_false + "\"");
	}
	  function onafterprint() {
		eval(visble_property_printview + " = \"" + visble_property_true + "\"");
	}
	 
</script>
</head>
<body topmargin="0px" leftmargin="0px" rightmargin="0px" bottommargin="0px">
	<%	
		StringBuffer barCode = new StringBuffer();
		for(int i=0;i<code.length;i++){
			barCode.append("<img src='");
			barCode.append(request.getContextPath());
			barCode.append("/CreateBarCode?code=");
			barCode.append(code[i]);
			barCode.append("&barType=CODE39&checkCharacter=n&checkCharacterInText=n'>");
			System.out.println(barCode.toString());
			
		}
		out.println(barCode.toString());
	%>
		
		
	<div id="viewpanel" align="center">
		<input name="bequery" type="button" class="noprint" value="打  印" style="cursor:hand;" onclick="nowprint();">
	</div>
</body>
</html>