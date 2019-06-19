<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <c:set var="staticPath" value="/static/uploads/"></c:set>
 
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
</head>
<body >

<div  class="childrenBody">
	  <div class="demoTable">	    
			<form class="form-horizontal" id="addForm">			
	         <div class="layui-form-item layui-form-text">
			    <div class="layui-input-block">				  					      
			      		<c:forEach items="${img }" var="item">	
			      		<img style="width:300px;height:250px"  src="${pageContext.request.contextPath}${item.img }" />
			      		</c:forEach>
			      		<img style="width:300px;height:250px"  src="${staticPath}${previewId.frontPic }" />
			      		<img style="width:300px;height:250px"  src="${staticPath}${previewId.backPic }" />							    				    
			    </div>
			</div>  		        
		       <div class="form-group">
	                <label class="col-sm-2 control-label">预检序号：</label>
	                <div class="col-sm-6">
		                <input id="_specifications" name="previewId" value = "${previewId.previewId }" type="text" class="form-control">
	                </div>
		        </div> 
		        
		        <div class="form-group">
	                <label class="col-sm-2 control-label">超限率外键：</label>
	                <div class="col-sm-6">
		                <input id="_specifications" name="overLoadId" value = "${previewId.overLoadId }" type="text" class="form-control">
	                </div>
		        </div> 
		       		        		        
		         <div class="form-group">
	                <label class="col-sm-2 control-label">时间：</label>
	                <div class="col-sm-6">
		                <input id="_specifications" name="dateTime" value = "${previewId.dateTime }" type="text" class="form-control">
	                </div>
		        </div> 		        		        
          <div class="form-group">
	                <label class="col-sm-2 control-label">车道：</label>
	                <div class="col-sm-6">
		                <input id="_specifications" name="lane" value = "${previewId.lane }" type="text" class="form-control">
	                </div>
		        </div>
		        
		        <div class="form-group">
	                <label class="col-sm-2 control-label">车牌：</label>
	                <div class="col-sm-6">
		                <input id="_materialQuality" name="carNum" type="text" value = "${previewId.carNum }" class="form-control">
	                </div>
		        </div>
		        <div class="form-group">
	                <label class="col-sm-2 control-label">轴数：</label>
	                <div class="col-sm-6">
		                <input id="_price" name="axleCnt" type="text" value = "${previewId.axleCnt }" class="form-control">
	                </div>
		        </div>  
		         <div class="form-group">
	                <label class="col-sm-2 control-label">车速：</label>
	                <div class="col-sm-6">
		                <input id="_price" name="speed" type="text" value = "${previewId.speed }" class="form-control">
	                </div>
		        </div>    
		         <div class="form-group">
	                <label class="col-sm-2 control-label">总重(kg)：</label>
	                <div class="col-sm-6">
		                <input id="_price" name="sumWeight" type="text" value = "${previewId.sumWeight }" class="form-control">
	                </div>
		        </div> 
		        <div class="form-group">
	                <label class="col-sm-2 control-label">超限(kg)：</label>
	                <div class="col-sm-6">
		                <input id="_price" name="overRage" type="text" value = "${previewId.overRage }" class="form-control">
	                </div>
		        </div>
		        <div class="form-group">
	                <label class="col-sm-2 control-label">超限率%：</label>
	                <div class="col-sm-6">
		                <input id="_price" name="overLoadRate" type="text" value = "${previewId.overLoadRate }"  class="form-control">
	                </div>
		        </div> 
		        
		        <div class="layui-form-item" style="width:300px;height:35px;margin-left:800px;margin-top:-38px;">
                   <div class="layui-input-inline">
                       <label class="layui-form-labe">站点:</label>
                             <select name="station" style="width:80%;height:30px" id="station" >
                                     <option value="">请选择站点</option >                          
                                   <c:forEach var="item" items="${station}">
		                        <option value="${item.stationName }" hassubinfo="true" >${item.stationName }</option>
	                       			 </c:forEach>
                          </select>
                 </div>
              </div>                                             			 
		</form>			
	</div>		
</div>


	<script type="text/javascript">
	
	var sels = document.getElementById('station');
	//console.log(sels);
 	   for(var i=0; i<sels.length; i++){
 		var value = sels[i].value;	
 		if(value == '${previewId.station}'){			     			
 			sels[i].selected = true;
 		}
 	}   
	
	function formData(){		
		var data = new FormData($('#addForm')[0]);			
		return data;
	}	
	</script>	  	
</body>
		
	
</html>