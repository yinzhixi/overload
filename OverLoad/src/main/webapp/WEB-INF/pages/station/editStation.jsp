<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>
	
	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />
	
<title>编辑执法站点</title>
</head>
<body >

<div  class="childrenBody">
	  <div class="demoTable" style="margin:0 auto">
			<form class="form-horizontal" id="addForm" autocomplete="off">
	        	        
		        <div class="layui-form-item">
	                <label class="layui-form-label">站点编号：</label>
	                <div class="layui-input-block">
		                <input  name="stationCode" value="${station.stationCode }"  type="text" class="layui-input" style="width: 250px;"placeholder="请输入所在省份 XX省">
	                </div>
		        </div>
		        
		        <div class="layui-form-item">
	                <label class="layui-form-label">设备名称：</label>
	                <div class="layui-input-block">
		                <input  name="stationName" type="text" value="${station.stationName }" class="layui-input" style="width: 250px;">
		                  <input  name="id"  type="hidden" value="${station.id}"  >
	                </div>
		        </div> 
		      	        
		         <div class="layui-form-item">
	                <label class="layui-form-label">所在省份：</label>
	                <div class="layui-input-block">
		                <input  name="province"  type="text" value="${station.province}" placeholder="请输入所在省份 XX省" class="layui-input" style="width: 250px;">
	                </div>
		        </div>
				<div class="layui-form-item">
					<label class="layui-form-label">所在城市：</label>
					<div class="layui-input-block">
						<input  name="city"  type="text" value="${station.city}" placeholder="请输入所在省份 XX市" class="layui-input" style="width: 250px;">
					</div>
				</div>
		</form>			
	</div>		
</div>


	<script type="text/javascript">
	
	/*  var len = document.getElementById('online');
		//console.log(sels);
	 	   for(var i=0; i<len.length; i++){
	 		var value = len[i].value;	
	 		if(value == '${node.online}'){			     			
	 			len[i].selected = true;
	 		}
	 	}    */
	 	   
	 	  var len = document.getElementById('nId');
			//console.log(sels);
		 	   for(var i=0; i<len.length; i++){
		 		var value = len[i].value;	
		 		if(value == '${node.nId}'){			     			
		 			len[i].selected = true;
		 		}
		 	} 

	function formData(){		
		var data = new FormData($('#addForm')[0]);			
		return data;
	}	 
	</script>	  	
</body>
		
	
</html>