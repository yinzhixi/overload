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
			top: 15%;
			margin-top: -50px;
		}
	</style>
</head>
<body >

<div  class="childrenBody">
	  <div class="demoTable" style="margin:0 auto">
			<form class="form-horizontal" id="addForm" autocomplete="off">
	        	        
		        <div class="layui-form-item">
	                <label class="layui-form-label">设备编号：</label>
	                <div class="layui-input-block">
		                <input  name="nodeCode" value="${node.nodeCode }"  type="text" class="layui-input" style="width: 250px;">
	                </div>
		        </div>
		        
		        <div class="layui-form-item">
	                <label class="layui-form-label">设备名称：</label>
	                <div class="layui-input-block">
		                <input  name="nodeName" type="text" value="${node.nodeName }" class="layui-input" style="width: 250px;">
		                  <input  name="id"  type="hidden" value="${node.id}"  >
	                </div>
		        </div> 
		      	        
		         <div class="layui-form-item">
	                <label class="layui-form-label">英文名称：</label>
	                <div class="layui-input-block">
		                <input  name="pinyin"  type="text" value="${node.pinyin}" class="layui-input" style="width: 250px;">
	                </div>
		        </div> 	
		        
		       <!--  <div class="layui-form-item" >
                   <div class="layui-input-inline">
                       <label class="layui-form-labe">是否在线:</label>
                             <select name="online" style="width:80%;height:30px" id="online" >
                                    <option value="0">否</option > 
                                     <option value="1">是</option >                                                          
                          </select>
                 </div>
              </div>       -->  
             		        
          <div class="layui-form-item">
	                <label class="layui-form-label">经度：</label>
	                <div class="layui-input-block">
		                <input  name="lat"  type="text" value="${node.lat}" class="layui-input" style="width: 250px;">
	                </div>
		        </div>
		        
		        <div class="layui-form-item">
	                <label class="layui-form-label">纬度：</label>
	                <div class="layui-input-block">
		                <input  name="lon" type="text" value="${node.lon}"  class="layui-input" style="width: 250px;">
	                </div>
		        </div>
		    
		        <!--<div class="layui-form-item">
	                <label class="layui-form-label">秘钥：</label>
	                <div class="layui-input-block">
		                <input  name="key" type="text" value="${node.key}" class="layui-input" style="width: 250px;">
	                </div>
		        </div>-->
		  
		        <div class="layui-form-item">
	                <label class="layui-form-label">创建者：</label>
	                <div class="layui-input-block">
		                <input  name="createUser" type="text" value="${node.createUser}" class="layui-input" style="width: 250px;">
	                </div>
		        </div>

		        <div class="layui-inline" style="float:left">
					<label class="layui-form-label">所属类型:</label>
					<div class="layui-input-inline">
							<select name="nId" style="width:90%;height:30px;width: 250px;" id="nId">
											 <option value="">请选择类型</option >
										   <c:forEach var="item" items="${type}">
										<option value="${item.nId }" hassubinfo="true" >${item.name }</option>
											 </c:forEach>
                          </select>
					</div>
				</div>

				<div class="layui-inline" style="float:left">
					<label class="layui-form-label">所属站点:</label>
					<div class="layui-input-inline">
						<select name="stationId" style="width:80%;height:30px;width: 250px;" >
							<option value="">请选择站点</option>
							<c:forEach var="item" items="${stations}">
								<option value="${item.id }" hassubinfo="true">${item.stationName }</option>
							</c:forEach>
						</select>
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