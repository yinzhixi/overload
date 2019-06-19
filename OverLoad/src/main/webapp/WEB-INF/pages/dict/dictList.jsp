<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>高速预检统计表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
</head>
<body>
    
<form class="layui-form" action="" style="padding: 20px 10px;">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始日期</label>
      <div class="layui-input-inline">
        <input type="text" name="startDate" id="startDate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">结束日期</label>
      <div class="layui-input-inline">
        <input type="text" name="endDate" id="endDate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
    <label class="layui-form-label">类&nbsp;&nbsp;型</label>
	    <div class="layui-input-inline">
	        <input type="text" name="type" id="type"  class="layui-input">
	    </div>
    </div>
  
    <div class="layui-inline">
      <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-normal" id="query">查询</button>
      <button type="button" class="layui-btn layui-btn-primary" id="export">导出</button>
      </div>
    </div>
    
    </div>
  </form>
  
  <div>
	<table class="layui-hide" id="table"></table>
  </div>
 
</body>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    
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
    	  
    	  var table = layui.table;
    	  table.render({
    		id: 'calc',
    	    elem: '#table'
            ,even: true
    	    ,url:'/dict/query'
    	    ,cols: [[
    	      {field:'createTime', title: '创建日期',width: 250}
    	      ,{field:'type',  title: '类型',width: 250}
    	      ,{field:'name',  title: '名称',width: 250}
    	      ,{field:'code', title: '类型代码）',width: 250}
    	      ,{field:'sq', title: 'sq',width: 250}
    	    ]]
    	  ,page: true    //开启分页  
    	  });
    	  
    	  var $ = layui.$;
          $("#query").click(function(){
              table.reload('calc',{
            	  where:{
                      startDate: $('#startDate').val(),
                      endDate: $('#endDate').val(),
                      type: $('#type').val()
                  }
              });
          });
          
           $("#export").click(function(){
        	    window.location = "${contextPath}/preview/calc/query/export?startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&station="+$("#station").val();
          });
    });
    
    
    </script>
</html>