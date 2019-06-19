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
	

<title>Insert title here</title>
	<style type="text/css">
		.form-horizontal{
			position: absolute;/*绝对定位*/
			text-align: center;/*(让div中的内容居中)*/
			top: 50%;
			left: 50%;
			margin-top: -200px;
			margin-left: -150px;
		}
	</style>
</head>
<body >

<div class="childrenBody">
	  <div class="demoTable" style="right: auto ">
			<form class=".form-horizontal" id="addForm" autocomplete="off">
				<div class="layui-form-item">
					<label class="layui-form-label">所在省份：</label>
					<div class="layui-input-block">
						<input  name="province" type="text" class="layui-input" placeholder="请输入所在省份 XX省" style="width: 250px;">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">所在城市：</label>
					<div class="layui-input-block">
						<input  name="city" type="text" class="layui-input" placeholder="请输入所在城市 XX市" style="width: 250px;">
					</div>
				</div>
				<div class="layui-form-item">
	                <label class="layui-form-label">站点名称：</label>
	                <div class="layui-input-block">
		                <input  name="stationName" type="text" class="layui-input" placeholder="请输入站点名称" style="width: 250px;">
	                </div>
		        </div>
				<div class="layui-form-item">
					<label class="layui-form-label">站点编码：</label>
					<div class="layui-input-block">
						<input  name="stationCode" type="text" class="layui-input" placeholder="请输入站点编码" style="width: 250px;">
					</div>
				</div>
		</form>			
	</div>		
</div>


	<script type="text/javascript">

	function formData(){		
		var data = new FormData($('#addForm')[0]);			
		return data;
	}	 
	</script>	  	
</body>
		
	
</html>