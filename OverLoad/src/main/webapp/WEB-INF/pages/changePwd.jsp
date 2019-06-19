<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>修改密码</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	
	<script type="text/javascript" src="../../layui/layui.js"></script>
<!-- 	<script type="text/javascript" src="address.js"></script>
	<script type="text/javascript" src="user.js"></script> -->
	
	<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="../../css/user.css" media="all" />
	
 
</head>
<body class="childrenBody">
	<form  class="layui-form changePwd">
		<div style="margin:0 0 15px 110px;color:#f00;">新密码必须两次输入一致才能提交</div>
		
		<div class="layui-form-item">
		    <label class="layui-form-label">工号：</label>
		    <div class="layui-input-block">		    	
		  <input type="text" name="jobNum" value="${requestScope.emp.jobNum}" id="jobNum"  class="layui-input layui-disabled"> 		
		    </div>
		
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">原密码：</label>
		    <div class="layui-input-block">
		    	<input type="password" name="password1" placeholder="请输入旧密码" lay-verify="required|oldPwd" class="layui-input pwd" id="oldPassWord">
		    </div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">新密码：</label>
		    <div class="layui-input-block">
		    	<input type="password" name="passWord" placeholder="请输入新密码" lay-verify="required|newPwd" id="newPassWord" class="layui-input pwd">
		    </div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">密码确认：</label>
		    <div class="layui-input-block">
		    	<input type="password" name="password2" placeholder="请确认密码" id="queren" lay-verify="required|confirmPwd" class="layui-input pwd">
		    </div>
		</div>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		    	<button class="layui-btn  layui-btn-normal" lay-submit="" lay-filter="changePwd">提交</button>
		    </div>
		</div>
	</form>
	
		<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript">	
		layui.use('form', function(){
	  		var form = layui.form;  
			  //监听提交
			  form.on('submit(changePwd)', function(data){		
				
				  var oldPassWord = $("#oldPassWord").val();
				  
				  var jobNum = $("#jobNum").val();				  			  
				  var passWord = $("#newPassWord").val();			
						  
				  var datas=data.field;		
				 // debugger;
				  $.ajax({
					  url:"${pageContext.request.contextPath}/emp/updateP",
					  data:{"jobNum":jobNum,"passWord":passWord,"oldPassWord":oldPassWord},					
					  type:"POST",
					  dataType:"json",
					  success:function(msg){
						  if(msg.falg){
							  layer.msg(msg.hint,{time:3000})
							    $("#oldPassWord").val("");
                                  $("#newPassWord").val("");    
                                  $("#queren").val(""); 
							     
						  }
						  
							
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