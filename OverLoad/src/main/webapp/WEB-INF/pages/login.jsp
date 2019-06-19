<%@ page language="java" import="java.util.*,java.net.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
	<meta charset="utf-8">
	<title>登录--非现场执法治超检测平台</title>
	
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" media="all" />
</head>
<body >		
 	 <%request.setCharacterEncoding("utf-8"); %>
 	 
		<div class="ms-title" ref="headerLogo">
	            <img style="width:100%;" src="${pageContext.request.contextPath}/images/login-img01.gif" alt="">
	    </div>
	    
		<div class="login" style="padding-top:90px;">
		    <form class="layui-form">
		    	<div class="layui-form-item" style="width:350px;margin-left:-20px;">
		    
					<input class="layui-input" id="empName" name="empName" placeholder="请输入用户账号" value="${cookie['empName'].value}" lay-verify="required" type="text" autocomplete="off">
			    </div>
			    <div class="layui-form-item" style="width:350px;margin-left:-20px;">
			 
					<input class="layui-input" id="passWord" name="passWord" placeholder="请输入密码" lay-verify="required" type="password" autocomplete="off">
			    </div>
			    
			  <div class="layui-form-item" style="width:120px;margin-left:-20px;height:30px"> 
					  <input type="checkbox" style="color:red;width:10px" id="ishave11"  lay-verify="required" autocomplete="off" ${cookie['remember'].value}/>&nbsp;记住账号
					
			     </div> 

				<button class="layui-btn layui-btn-normal login_btn" lay-submit="" lay-filter="login" style="width:350px;margin-left:-20px;margin-top:5px;">账号登录</button>
				
				
			
			</form>
			
		</div>
		
    
    <div id='demo' style="color:red;font-size: 10px" display:none></div>
    
	
		<div class="foot_line"><img style="width:100%;" src="${pageContext.request.contextPath}/images/login-img02.png" alt=""></div>
        
	<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>     
   <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/login/login.js"></script> --%>
   
		
</body>
   	
   	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript">	
	
	
		
		layui.use('form', function(){
			 //value="${cookie['remember'].value}"
	  		var form = layui.form;  
			  //监听提交
			  form.on('submit(login)', function(data){		
				
				  
				// var ss =  document.getElementById('ishave11');
				 var chao = "remember";
				  
				  var empName = $('#empName').val();
					var passWord =  $('#passWord').val();
					var ishave;
					var ishave1 = $('#ishave11').is(':checked');
					if(ishave1){
						ishave="remember";
					}else{
						ishave="sdg";
					}
											
					if(empName == '' || passWord == ''){
						layer.msg("用户名或密码不能为空！");
						return;
					}
					
					$.ajax({
						url: '/emp/login',
						dataType: 'json',
						data: {
							"empName": empName,
							"passWord": passWord,
							"ishave": ishave
						},
						type: 'POST',
						success: function(rs) {
							if(rs.flag) {
								//layer.msg("we're in");
								location.href = '/index.jsp';
							} else {
								//layer.msg(rs.hint,{time:5000});	
								
								layer.msg(rs.hint,{
			                         // offset:['50%'],
			                          time: 4000 //2秒关闭（如果不配置，默认是3秒）
			                    },function(){
			                    	if(rs.hint == "由于您的账号输错密码超过五次，现已被锁,请联系管理员或1分钟后再试"){
			                    		var m = new Date();		                    		
			                    		var countDownDate = new Date(m.getTime() + 1000 * 60);
			                    		var x = setInterval(function() {
			                    		    var now = new Date().getTime();			                    		    
			                    		    var distance = countDownDate - now;			                    		    
			                    		    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
			                    		    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
			                    		    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
			                    		    var seconds = Math.floor((distance % (1000 * 60)) / 1000);		
			                    		   // document.getElementById("demo").innerHTML =
			                    			  // +days + "d " + hours + "h "
					                    		 //   + minutes + "m "
			                    		 layer.msg( "解锁时间还剩:" + seconds + "s",{time:6000,icon: 1,
			                    			    shade: [0.8, '#393D49'],shift: -1}) 			                    		    
			                    		    if (distance < 0) {
			                    		        clearInterval(x);
			                    		        layer.msg("你的账号已解锁，请重试",{time:3000})
			                    		        $("#empName").val("");
			                    		        $("#passWord").val("");
			                    		       // $("#ishave11").val("").selected = false;			                    		        
			                    		    }
			                    		}, 1000);			                    											
			                    	}			                    	
			                    }); 				
							}
						}
					});
					return false; 				
			  });
			});
	
	</script>
<%-- <div class=""><img style="width:5%;" src="${pageContext.request.contextPath}/images/logo.jpg" alt=""></div> --%>
</html>