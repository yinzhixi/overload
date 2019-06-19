<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>车牌识别率报表</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>
	<%-- <script type="text/javascript" charset="utf-8" src="${contextPath}/js/layer/layer.js"></script> --%>

	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />
	
</head>

<body class="childrenBody">
	<div class="admin-main">
		<blockquote class="layui-elem-quote">
			<!-- 查询按钮 -->
			 <div class="demoTable">
				 <div class="layui-inline">
	   				<!-- 开始时间 -->
            <div class="layui-inline" style="margin-left:115px;">
              <label class="layui-form-label">开始时间：</label>
                 <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="startDate" name="startDate" placeholder="yyyy-MM-dd HH:mm:ss">
                 </div>
            </div>
           <!-- 结束时间 -->
            <div class="layui-inline" style="margin-left:30px;">
                <label class="layui-form-label">结束时间：</label>
                   <div class="layui-input-inline">
                       <input type="text" class="layui-input" id="endDate" name="endDate" placeholder="yyyy-MM-dd HH:mm:ss">
                   </div>
            </div>
			 </div>
				 <div class="layui-form-item" style="width:300px;height:5px;margin-left:800px;margin-top:-30px;">
                   <div class="layui-input-inline">
                       <label class="layui-form-labe">站点:</label>
                               <select name="station" style="width:80%;height:30px" id="station">
                                     <option value="">请选择站点</option>                          
                                   <c:forEach var="item" items="${station}">
		                        <option value="${item.stationName }" hassubinfo="true" >${item.stationName }</option>
	                       			 </c:forEach>
                        	  </select>
                 </div>
               </div>
				<div class="layui-inline" style="width:300px;height:35px;margin-left:1100px;margin-top:-55px;">
				     <button id="search" name="search" class="layui-btn  layui-btn-normal" data-type="reload" style="width:80px;height:35px">查询</button>
				     <button type="reset" class="layui-btn  layui-btn-normal" style="width:80px;height:35px" id="export1">导出</button>
				</div>
 			 </div>
			</div>
		</blockquote>
		<fieldset class="layui-elem-field">
			<legend>车牌识别率报表</legend>
			<div class="layui-field-box layui-form">
				<table class="layui-hide layui-table" id="table" lay-filter="user"></table>
			</div>
		</fieldset>	
	</div>
	
	<script type="text/javascript">
	 layui.use(['jquery','form', 'layedit', 'laydate','table'], function(){
   	  var form = layui.form
   	  ,layer = layui.layer
   	  ,layedit = layui.layedit
   	  ,laydate = layui.laydate;
   	var nowTime = new Date().valueOf();
    var startTime = new Date();
    startTime.setHours(0);
    startTime.setMinutes(0);
    startTime.setSeconds(0);
    var endTime = new Date();
    endTime.setHours(23);
    endTime.setMinutes(59);
    endTime.setSeconds(59);
    
    var max = null;
    var start = laydate.render({
        elem: '#startDate',
        type: 'datetime',
        max: nowTime,
        value: startTime,
        btns: ['clear', 'confirm'],
        done: function(value, date){
            endMax = end.config.max;
            end.config.min = date;
            end.config.min.month = date.month -1;
        }
    });
    
    var end = laydate.render({
        elem: '#endDate',
        type: 'datetime',
        max: nowTime,
        //value: '${nextDay}',
        value: endTime,
        done: function(value, date){
            if($.trim(value) == ''){
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
            }
            start.config.max = date;
            start.config.max.month = date.month -1;
        }
    });
   	  var tableContent = new Array();
   	  var table = layui.table;
   	  table.render({
   		id: 'calc',
   	    elem: '#table',
   	    data : tableContent
   	    ,url:'/preview/getByCarNum'
   	    ,cols: [[      	    
   	      {field:'lane', title: '车道',width:200}
   	      ,{field:'distingByday',  title: '白天识别率%',width:200}
   	      ,{field:'distingByNight', title: '夜晚识别率%',width:200}
   	      ,{field:'allDisting', title: '全天识别率%',width:200}   	      	    
   	    ]]
   	    ,page: true
   	    ,limit:10,   	    
   	    done : function(res, curr, count){
   	    	//console.log(res)    	      	     
   	    	 var ids = [];
   	    	 $.each(res.data, function(index, item){
   				ids.push(item.overLoadId);
   				console.log(ids)
   			});   
   	    }           
   	  });
   	  
   	  var $ = layui.$;
         $("#search").click(function(){
             table.reload('calc',{
           	  where:{
           		  startDate: $('#startDate').val(),
           		  endDate: $('#endDate').val(),
           		  station: $('#station').val()
           	  }
           	  
             });
         });
         	
         $("#export1").click(function(){        	
     	    window.location = "${pageContext.request.contextPath}/preview/exportsss?startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&station="+$("#station").val();
       });

					
   });
	
	</script>


</body>

</html>