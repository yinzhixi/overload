<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="staticPath" value="/static/uploads/"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>预检实时数据表</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	
		<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
	
	 <script type="text/javascript" src="<%=path%>/layui/layui.js"></script>
</head>

<title>预检实时数据列表</title>

<body >
	
       <blockquote class="layui-elem-quote">
	  		<div class="admin-main">
				 <div class="layui-form-item" style="width:300px;height:35px;margin-left:30px;">
                   <div class="layui-input-inline">
                       <label class="layui-form-labe">站点:</label>
                              <select name="station" style="width:80%;height:30px" id="station">
                                     <option value="">请选择站点</option>                          
                                   <c:forEach var="item" items="${station}">
		                        <option value="${item.stationName }" >${item.stationName }</option>
	                       			 </c:forEach>
                          </select>
                 </div>
               </div>
               <div class="" style="width:500px;height:35px;margin-left:320px;margin-top:-50px;">
		 			 <!-- <button type="reset" class="layui-btn layui-btn-normal" style="width:140px;height:35px" id="test1" >最近5分钟数据</button>
					  --><button type="reset" class="layui-btn layui-btn-normal" style="width:140px;height:35px" id="test2" >最后50条数据</button>
					 <button type="reset" class="layui-btn layui-btn-normal" style="width:140px;height:35px" id="test3" >最后超限50条数据</button>
				</div>
			</div>
			</blockquote>
			
		<fieldset class="layui-elem-field">
		<legend>预检明细表</legend>
		<div class="layui-field-box layui-form">
			<table class="layui-hide layui-table" id="table" lay-filter="demo"></table>
		</div>
	</fieldset>	
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
</script>

	<script type="text/javascript">
	   layui.use(['jquery','form', 'layedit', 'laydate','table'], function(){
  	  var form = layui.form
  	  ,layer = layui.layer
  	  ,layedit = layui.layedit
  	  ,laydate = layui.laydate; 
  	  
  	  var table = layui.table;
  	 //监听工具条
  	  table.on('tool(demo)', function(obj){
  	    var data = obj.data;
  		  console.log(data)
  	    if(obj.event === 'detail'){	 
  	    	
  	  	var url ='${pageContext.request.contextPath}/preview/getAllImgs?previewId='+data.previewId+"&carNum="+data.carNum+"&dateTime="+data.dateTime; 	
	  	 	var str = encodeURI(url);		
	  	  	
  	    	 layer.open({ 
  	         type: 2,   	     
  	         title: '照片显示', 
  	          fix: false, 
  	          shadeClose: true, 
  	          maxmin: true, 
  	          area: ['85%', '100%'],    	          
  	      	  content:str
  	      	  
  	      //  content: '<img src=${staticPath}'+ data.frontPic +' width="500" height="350"/>'  	     
  	      });  
 	      
  	    }
  	  });
  	  table.render({
  		id: 'calc',
  	    elem: '#table'
  	    ,url:'/preview/showAll'
  	    ,cols: [[
  	        {field:'previewId', title: '序号',width:250},
  	       {field:'dateTime', title: '时间',width:250}
  	       ,{field:'lane',  title: '车道',width:150}
  	      ,{field:'carNum', title: '车牌号',width:250}
  	      ,{field:'sumWeight', title: '总重(kg)',width:200}  	      
  	      ,{field:'overRage', title: '超限(kg)',width:200}
	      ,{field:'frontPic',  title: '图片',toolbar: '#barDemo',width:200	      
	      }  
	    
  	    ]]
  	    ,page: true
  	  //,even: true
  	   ,limit:10,
  	  });
  	    	  
  	   var $ = layui.$;
        /* $("#test1").click(function(){ 
        	table.reload('calc',{
	          	  where:{	          	
	          		  station: $('#station').val(),	
	          		  time:5,
	          		//overRage:null,
	          	  }	          	  
	            });        	
      	  } 
        );  */
        
        $("#test2").click(function(){ 
     
        	var station = $('#station').val();
        	table.reload('calc',{
        		 url:'${pageContext.request.contextPath}/preview/getFinallyData?pageSize='+50+"&station="+station,
        				  
	          	  /*  where:{	          	
	          		  station: $('#station').val(),	
	          		  time:null,
	          		  ss:'ss',
	          			//overRage:null, 
	          	  }	      */        
	            });        	
      	  } 
        ); 
        
        $("#test3").click(function(){
        	var station = $('#station').val();
        	table.reload('calc',{
        		  url:'${pageContext.request.contextPath}/preview/getFinallyData?pageSize='+50+"&station="+station+"&overLoadRate="+1,
        		
	          	 /*  where:{	          	
	          		  station: $('#station').val(),	
	          		overRage:2,
	          	  	time:null,
	          
	          	  }	     */      	  
	            });        	
      	  } 
        );       
  });
	
	</script> 

</body>
</html>
