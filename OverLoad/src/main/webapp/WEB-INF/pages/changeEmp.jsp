<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
	<meta charset="utf-8">
	<title>修改用户</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>		
	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/js/layer/layer.js"></script>
	
	
	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />
	<style type="text/css">
		.layui-form-item .layui-inline{ width:33.333%; float:left; margin-right:0;}
		@media(max-width:1240px){
			.layui-form-item .layui-inline{ width:100%; float:none; }
		}
	</style>
	
</head>
<body class="childrenBody" style="margin-left:700px;margin-top:80px;">
<!-- 	<form action="/emp/updateE" class="layui-form1" style="width:400px;"> -->
	
		<form  class="layui-form1" style="width:400px;">
	<c:forEach var="item" items="${empl}">
		<div class="layui-form-item">
			<label class="layui-form-label">工号：</label>
			<div class="layui-input-block">
		<%-- 	<input type="hidden" name="id" value="<%=request.getParameter("id") %>"> --%>
			
			<input type="hidden" name="id" value="${item.id}" id="id">
				<input type="text" name="jobNum" id="jobNum" value="${item.jobNum}" class="layui-input userName" lay-verify="required" placeholder="请输入工号">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">名称：</label>
			<div class="layui-input-block">
				<input type="text" name="empName" id="empName" value="${item.empName}" class="layui-input userEmail" lay-verify="email" placeholder="请输入名称">
			</div>
		</div>
		
		
	    
	    <div class="layui-form-item">
					<label class="layui-form-label">再用：</label>
					<select name="isEmp" style="width:71%;height:30px" id="isEmp">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
				
	    <div class="layui-form-item" style="margin-left:55px;">
                   <div class="layui-input-inline">
                       <label class="layui-form-labe">站名：</label>
                          <select name="stationName" style="width:71%;height:30px" id="stationName">
                              <option value="">请选择站点</option>        
                          <c:forEach var="item" items="${lis}">                          
                                  <option value="${item.stationName }">${item.stationName}</option>                         
                            </c:forEach>
                       </select>
                 </div>
               </div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button  style="width:280px;height:35px" class="layui-btn  layui-btn-normal" lay-submit="" lay-filter="changeUser">确定</button>
		    </div>
		</div>
		</c:forEach>
	</form>
	<script>
	
	var sels = document.getElementById('stationName');
	//console.log(sels);
 	   for(var i=0; i<sels.length; i++){
 		var value = sels[i].value;	
 		if(value == '${type.stationName}'){			     			
 			sels[i].selected = true;
 		}
 	}   
 
 	  var len = document.getElementById('isEmp');
 		//console.log(sels);
 	 	   for(var i=0; i<len.length; i++){
 	 		var value = len[i].value;	
 	 		if(value == '${type.isEmp}'){			     			
 	 			len[i].selected = true;
 	 		}
 	 	}   
	
	//Demo
		layui.use('form', function(){
  		var form = layui.form;  
		  //监听提交
		  form.on('submit(changeUser)', function(data){		
			  var id = $("#id").val();	
			  var jobNum = $("#jobNum").val();			
			  var empName = $("#empName").val();			  
			  var isEmp = $("#isEmp").val();			  	
			  var stationName = $("#stationName").val();			  
			  var datas=data.field;		
			 // debugger;
			  $.ajax({
				  url:"${pageContext.request.contextPath}/emp/updateE",
				  data:{"id":id,"jobNum":jobNum,"empName":empName,"isEmp":isEmp,"stationName":stationName},					
				  type:"POST",
				  dataType:"json",
				  success:function(msg){
					  
		  layer.msg('正在提交请稍候。。。', {icon: 16,time: 2000,shade : [0.5 , '#000' , true]},function(){
				layer.msg("修改成功")
				
				
		  });						
			 // window.location.href="main";
			  },
			  error:function(error){
				 layer.msg("服务器内部异常")			
			  }
			  });			  
			  			  
		    return false;
		  });
		});
</script>
</body>
</html>