<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ page import="java.net.URLEncoder" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <c:set var="staticPath" value="/static/uploads/"></c:set>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<link type="text/css" rel="stylesheet" href="../css/overrun.css" />		
	 <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>
		
	<style type="text/css">
			.max{
			width: 1000px;
			height: 700px;
		}
		.min{
			width: 450px;
			height: 300px;
		} 
		
	.lb{font-weight: 700;}	
		
	.img-info{background:#f4f4f4; padding: 5px; text-align:left; height: 48px;width: 438px;border: 1px solid #ccc;margin-left:100px}
	</style>


	</head>
	<body>
	    <div class="cen" >	  	   
	   	    <div class="content" id="header">	 
	   	     
	   	     <div class="timg" >
	   	        <img src="${staticPath}${preview885.frontPic}" class ='min' id="img0"/>	
	   	    </div>	   	    
	   	    <div class="img-info">
                           <div>   
                           	    <span class="lb">${preview885.carNum }</span>&nbsp;&nbsp;&nbsp;             
	                            <span class="lb">总重</span>&nbsp;${preview885.sumWeight}&nbsp;&nbsp;
	                            <span class="lb">限重</span>&nbsp;${preview885.limitWeight }&nbsp;&nbsp;&nbsp;
	                            <span class="lb">超重</span>&nbsp;${preview885.overRage }&nbsp;
                           </div>
                        <div style="color: red;">
                        	${preview885.dateTime }
                         </div>
             </div>
	   	
	   	        <div class=" b-label" style="margin-bottom: 10px;">
					<div style=" float: left;">
						<span>车牌号：</span>
						<input type="text" class="ccsq-clxx" value="${preview885.carNum }" disabled="disabled"></input>
					</div>
					<span >轴数：</span><input class="ccsq-clxx" type="text" name="userName" value="${preview885.lane }" disabled="disabled">
				</div>
				
				<div class=" b-label" style="margin-bottom: 10px;">
					<div style="float: left;">
						<span>限重：</span>
						<input type="text" class="ccsq-clxx" value="${preview885.limitWeight }" disabled="disabled"></input>
					</div>
					<span >超限重：</span><input class="ccsq-clxx" type="text" name="userName" value="${preview885.overRage }" disabled="disabled">
				</div>
				
				<div class=" b-label" style="margin-bottom: 10px;">
					<div style=" float: left;">
						<span>超限率：</span>
						<input type="text" class="ccsq-clxx" value="${preview885.overLoadRate }" id="overLoadRate" disabled="disabled"></input>
					</div>
					<span >是否超限：</span><input class="ccsq-clxx" type="text" name="" value="" id="orRage" disabled="disabled">
				</div>
				
				<div class=" b-label" style="margin-bottom: 10px;">
					<div style="float: left;">
						<span>抓拍地址：</span>
						<input type="text" class="ccsq-clxx" value="${preview885.station }" disabled="disabled"></input>
					</div>
					<span >行车方向：</span><input class="ccsq-clxx" type="text" name="userName" value="${car.outLane }" disabled="disabled">
				</div>
				
				<div class=" b-label" style="margin-bottom: 10px;">
					<div style="float: left;">
						<span>道路运输证号：</span>
						<input type="text" class="ccsq-clxx" value="${car.raodCard }"	disabled="disabled" ></input>
					</div>
					<span >发证机构：</span><input class="ccsq-clxx" type="text" name="userName" value="${car.agencyCard }" disabled="disabled">
				</div>
				
				<div class=" b-label" style="margin-bottom: 10px;">
					<div style="float: left;">
						<span>车架号：</span>
						<input type="text" class="ccsq-clxx" value="${car.vinNumber }" disabled="disabled"></input>
					</div>
					<span>许可证号：</span><input class="ccsq-clxx" type="text" name="userName" value="${car.licenseNumber }" disabled="disabled">
				</div>
				
				<div class=" b-label" style="margin-bottom: 10px;">
					<div style=" float: left; margin-bottom: 10px;">
						<span>运输公司(或车主)名称：</span>
						<input type="text" class="ccsq-clxx" value="${car.ownerName }" disabled="disabled"></input>
					</div>
					<span>运输公司(或车主)地址：</span>
						<input type="text" class="ccsq-clxx" value="${car.ownerAddress }" disabled="disabled"></input>
				</div>
	
			 <div class=" b-label" style="margin-bottom: 10px;">
					<div style=" float: left; margin-bottom: 10px;text-align: left;">
						<span>运输公司(或车主)电话：</span>
						<input type="text" class="ccsq-clxx" value="${car.telephone }" disabled="disabled"></input>
					</div>
					<span>车辆状态：</span><input class="ccsq-clxx" type="text" id="status" value= "${car.status }" disabled="disabled">
				</div>	 
			
	   	    </div>
	   	    <div class="btn" style="margin-top: 10px">
					<input class="bc" type="button" value="关闭" onclick='ss()'/>&nbsp;&nbsp;
					<input class="tj" type="button" value="打印" onclick="test('header');"/>
			</div>
	    </div>	
	    
	 <script type="text/javascript">	
		 var index;
	 	function ss(){
	 	index = parent.layer.getFrameIndex(window.name);	 	
	 	parent.layer.close(index); 	 	
	 		} 
	 	 
	    var len = document.getElementById("overLoadRate").value;	
	    if(len>5){
	    	  $("#orRage").val("是");
	    }else{
	    	 $("#orRage").val('否');
	    }	   
	   if('${car.status}' == '1'){	   
		   $("#status").val("异常");
	   }if('${car.status}' == '0'){
		   $("#status").val("正常");
	   }		    
		$(function(){
			$("#img0").click(function(){			
				$(this).toggleClass('max');
				$(this).toggleClass('min');				
			})
		})		
		 function test(MyDiv)
					 {
		 //if (confirm('确定打印吗？')) {
  	 	 var newstr = document.getElementById(MyDiv).innerHTML;
   		 var oldstr = document.body.innerHTML;
   		 document.body.innerHTML = newstr;
   		 window.print();
   		 document.body.innerHTML = oldstr;
    		return false;
	// } 
	} 	    
	    </script>
	</body>
</html>
