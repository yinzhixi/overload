<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>超限率分类报表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="<%=path%>/css/global.css" media="all">
</head>
    
<body class="childrenBody">
        <div class="admin-main">
        <form class="layui-form" action="">
        <blockquote class="layui-elem-quote">
            <!-- 查询按钮 -->
        <div class="layui-form-item">
            
           <!-- 开始时间 -->
            <div class="layui-inline">
              <label class="layui-form-label">开始时间：</label>
                 <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="startDate" id = "startDate" placeholder="yyyy-MM-dd HH:mm:ss">
                 </div>
            </div>
            
           <!-- 结束时间 -->
            <div class="layui-inline">
                <label class="layui-form-label">结束时间：</label>
                   <div class="layui-input-inline">
                       <input type="text" class="layui-input" name="endDate" id="endDate" placeholder="yyyy-MM-dd HH:mm:ss">
                   </div>
            </div>
            
            <div class="layui-inline">
                <label class="layui-form-label">站点:</label>
               <div class="layui-input-inline">
                    <select name="station" style="width:80%;height:30px" id="station">
                        <option value="">请选择站点</option>
                        <c:forEach var="item" items="${stations}">
                        <option value="${item.stationName }" hassubinfo="true" >${item.stationName }</option>
                        </c:forEach>
                  </select>
               </div>
            </div>
            
            <div class="layui-inline" >
                <label class="layui-form-label">行车方向:</label>
                <div class="layui-input-inline" >
                    <select name="direction" id="direction" lay-filter="direction">
                    <option value="">选择行车方向</option>
                    <c:forEach items="${directions }" var="direction">
                       <option value="${direction.id }">${direction.name }</option>
                    </c:forEach>
                  </select>
                </div>
            </div>
            
           <div class="layui-inline" >
                <label class="layui-form-label"></label>
               <div class="layui-input-inline">
                    <button id="search" name="search" class="layui-btn  layui-btn-normal" type="button" data-type="reload" style="width:80px;height:35px">查询</button>
                    <button type="reset" class="layui-btn  layui-btn-normal"  type="button" style="width:80px;height:35px" id="export">导出</button>
               </div>
            </div>
            
          </div>
        </blockquote>
        </form>
         <fieldset class="layui-elem-field">
            <legend>超限率分类报表</legend>
            <div class="layui-field-box layui-form">
                <table class="layui-hide layui-table" id="table" lay-filter="user"></table>
            </div>
        </fieldset>
    </div>
    
    <!-- 存放数据 -->
      <div>
          <table class="layui-hide" id="test"></table>
      </div>
    
    <script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>   
    <script type="text/javascript" src="<%=path%>/layui/layui.js"></script>
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
            ,url:'/overRate/getAllByOverRate'
            ,cols: [[
              {field:'overLoadId', title: '序号',width:200}
              ,{field:'overLoadRateRegion',  title: '超限率',width:200}
              ,{field:'overNum', title: '数量（辆）',width:200}
              ,{field:'percent', title: '比例%',width:200}
            ]]
            ,page: true
            ,limit:10,
            done : function(res, curr, count){
                 var ids = [];
                 $.each(res.data, function(index, item){
                    ids.push(item.overLoadId);
                 });
            }
          });
          
          var $ = layui.$;
          $("#search").click(function(){
              table.reload('calc',{
                  where:{
                      startDate: $('#startDate').val(),
                      endDate: $('#endDate').val(),
                      station: $('#station').val(),
                      direction: $('#direction').val()
                  }
              });
          });
              
          $("#export").click(function(){
              window.location = "${pageContext.request.contextPath}/overRate/exports?startDate="+
                $("#startDate").val()+
                "&endDate="+$("#endDate").val()+
                "&station="+$("#station").val()+
                "&direction="+$("#direction").val();
        });
       
    });
    
</script>

</body>

</html>