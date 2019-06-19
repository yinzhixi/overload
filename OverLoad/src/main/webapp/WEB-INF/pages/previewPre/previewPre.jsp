<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">

    <link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />

    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jQuery.print.js"></script>

    <style type="text/css">
        .layui-table-box{
            position: relative;
            overflow: hidden;
            height: 500px;
            overflow-y: scroll;
        }
    </style>

</head>

<body class="childrenBody">
    <div class="admin-main">
    <form class="layui-form" action="">
        <blockquote class="layui-elem-quote">
        <div class="layui-form-item">
            <!-- 开始时间 -->
            <div class="layui-inline">
              <label class="layui-form-label">开始时间：</label>
                 <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="startDate" name="startDate" placeholder="yyyy-MM-dd HH:mm:ss">
                 </div>
            </div>
           <!-- 结束时间 -->
            <div class="layui-inline">
                <label class="layui-form-label">结束时间：</label>
                   <div class="layui-input-inline">
                       <input type="text" class="layui-input" id="endDate" name="endDate" placeholder="yyyy-MM-dd HH:mm:ss">
                   </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">站点：</label>
                <div class="layui-input-inline">
                    <select name="station" id="station">
                        <option value="">请选择站点</option>
                        <c:forEach var="item" items="${station}">
                            <option value="${item.stationName }" hassubinfo="true" >${item.stationName }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="layui-inline">
                   <label class="layui-form-label">车道：</label>
                   <div class="layui-input-inline">
                      <input type="tel" id="lane" name="lane" lay-verify="required|phone" autocomplete="off" class="layui-input">
                   </div>
            </div>
            
                <div class="layui-inline" >
                     <label class="layui-form-label">车牌号：</label>
                       <div class="layui-input-inline">
                          <input type="tel" name="carNum" id ="carNum" lay-verify="required|phone" autocomplete="off" class="layui-input">
                       </div>
                </div>
                
                <div class="layui-inline" >
                     <label class="layui-form-label">车牌识别：</label>
                       <div class="layui-input-inline">
                           <select style="width:61%;height:30px" name ="recognition" id="recognition">
                               <option value="">请选择识别状态</option>
                                <option value="1">已识别</option>
                                <option value="0">未识别</option>
                            </select>
                       </div>
                </div>

                <div class="layui-inline" >
                     <label class="layui-form-label">超限率:</label>
                       <div class="layui-input-inline">
                           <select name="quiz1" style="width:50%;height:30px" name="overRate" id="overRate">
                                <option value="">请选择超限率</option>
                                <option value="1">5%以下</option>
                                <option value="2" >5%-30%</option>
                                <option value="3" >30%-50%</option>
                                <option value="4">50%-100%</option>
                                <option value="5">100%-200%</option>
                                <option value="6">>200%</option>
                            </select>
                       </div>
                </div>
                
                <div class="layui-inline">
                    <label class="layui-form-label">超限率：</label>
                    <div class="layui-input-inline" style="width: 90px;">
                          <select name="quiz1" style="width:70px;height:30px" name="overRate_op" id="overRate_op">
                                <option value="">操作符</option>
                                <option value="0">&gt;</option>
                                <option value="1">&lt;</option>
                                <option value="2" >&gt;=</option>
                                <option value="3" >&lt;=</option>
                                <option value="4">=</option>
                            </select>
                    </div>
                    <div class="layui-input-inline" style="width:90px;" >
                          <input type="number" style="width:90px;" id ="overRate_num" name="overRate_num" lay-verify="required|phone" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">轴数：</label>
                    <div class="layui-input-inline" style="width: 90px;">
                        <select name="quiz1" style="width:70px;height:30px" name="axleCnt_op" id="axleCnt_op">
                            <option value="">操作符</option>
                            <option value="0">&gt;</option>
                            <option value="1">&lt;</option>
                            <option value="2" >&gt;=</option>
                            <option value="3" >&lt;=</option>
                            <option value="4" selected="selected">=</option>
                        </select>
                    </div>
                    <div class="layui-input-inline" style="width: 90px;">
                        <input type="number" id ="axleCnt" name="axleCnt" lay-verify="required|phone" autocomplete="off" class="layui-input">
                    </div>
                </div>
            
                <div class="layui-inline" >
                     <label class="layui-form-label">起始吨位：</label>
                       <div class="layui-input-inline">
                          <input type="tel" id="startWeight" name="startWeight" lay-verify="required|phone" autocomplete="off" class="layui-input">
                       </div>
                </div>
                
                <div class="layui-inline">
                     <label class="layui-form-label">截止吨位：</label>
                       <div class="layui-input-inline">
                          <input type="tel" id="endWeight" name="endWeight" lay-verify="required|phone" autocomplete="off" class="layui-input">
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
                
                <div class="layui-inline">
                     <label class="layui-form-label"></label>
                       <div class="layui-input-inline">
                         <button id="search" name="search" class="layui-btn  layui-btn-normal" type="button" data-type="reload" style="width:80px;height:35px">查询</button>
                             <shiro:hasPermission name="preview:export"> 
                         <button  lay-event="detail" type="reset" class="layui-btn  layui-btn-normal" type="button" style="width:80px;height:35px" id="export">导出</button>
                             </shiro:hasPermission>
                        </div>
                </div>
        </div>
    </blockquote>
    </form>
    
        <fieldset class="layui-elem-field">
            <legend>精检明细表</legend>
            <div class="layui-field-box layui-form table">
                <table class="layui-table" id="table1" lay-filter="demo"></table>
            </div>    
        </fieldset>
    </div>

    <script type="text/html" id="barDemo">
            <shiro:hasPermission name="reviewed:delete">
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </shiro:hasPermission>
    </script>
    
    
    <script>
    layui.use(['jquery','form', 'layedit', 'laydate','table'], function(){
        var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate;
        
      //日期
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
          elem: '#table1',
          data : tableContent,
          url:'/after/quaryAll',
          cols: [
                [
                //{type:'checkbox',fixed: 'left'}, 
                 {
                    field: 'previewId',
                    title: '预检序号',
                    width: 200,
                    event: 'setSign',
                
                 },{
                    field: 'lane',
                    title: '车道',
                    width: 80,
                    event: 'setSign',
                    
                }, {
                    field: 'carNum',
                    title: '车牌',
                    width: 150,
                    event: 'setSign',
                    
                }, {
                    field: 'axleCnt',
                    title: '轴数',
                    width: 60,
                    event: 'setSign',
                    
                }, {
                    field: 'dateTime',
                    title: '时间',
                    width: 200,
                    sort: true,
                    event: 'setSign',
                    
                },{
                    field: 'speed',
                    title: '车速km/h',
                    width: 100,
                    event: 'setSign',
                    
                    sort:true
                    
                },{
                    field: 'sumWeight',
                    title: '总重(kg)',
                    width: 120,
                    event: 'setSign',
                
                },{
                    field: 'limitWeight',
                    title: '限重(kg)',
                    width: 120,
                    event: 'setSign',

                },{
                    field: 'overRage',
                    title: '超限(kg)',
                    width: 120,
                    event: 'setSign',
                
                } , {
                    field: 'overLoadRate',
                    title: '超限率%',
                    width: 150,
                    event: 'setSign',
                    
                }  ,{
                    field: 'venifyTime',
                    title: '审核时间',
                    width: 200,
                    event: 'setSign',
                    sort: true,
                    
                }  
                  ,{fixed: 'right',    title: '操作', width: 150, align:'center', toolbar: '#barDemo'}
                ]
            ]
          ,page: true
          ,limit:10,
        });
        var $ = layui.$;
        $("#search").click(function(){
             var station= $('#station').val();
            table.reload('calc',{
                where:{
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val(),
                    station: $('#station').val(),
                    startWeight: $('#startWeight').val(),
                    endWeight: $('#endWeight').val(),
                    lane: $('#lane').val(),
                    carNum: $('#carNum').val(),
                    axleCnt: $('#axleCnt').val(),
                    overRate: $('#overRate').val(),
                    axleCnt_op: $('#axleCnt_op').val(),
                    overRate_num:$('#overRate_num').val(),
                    overRate_op:$('#overRate_op').val(),
                    recognition: $('#recognition').val(),
                    direction:$('#direction').val()
                }
            });
        }); 
               
         $("#export").click(function(){
              window.location = "${pageContext.request.contextPath}/after/export2?startDate="
                      +$("#startDate").val()+
                      "&endDate="+$("#endDate").val()+
                      "&station="+$("#station").val()+
                      "&startWeight="+ $('#startWeight').val()+
                      "&endWeight="+$('#endWeight').val()+
                      "&lane="+$('#lane').val()+
                      "&carNum="+$('#carNum').val()+
                      "&axleCnt="+$('#axleCnt').val()+
                      "&overRate="+$('#overRate').val()+
                      "&recognition="+$('#recognition').val()+
                      "&axleCnt_op="+$('#axleCnt_op').val()+
                      "&overRate_num="+$('#overRate_num').val()+
                      "&overRate_op="+$('#overRate_op').val();
            });
            
            //监听工具条事件
              table.on('tool(demo)', function(obj){
                var data = obj.data;
                if(obj.event === 'detail'){
                  layer.msg('ID：'+ data.id + ' 的查看操作');
                } else if(obj.event === 'del'){
                          layer.confirm('确定删除吗？', function(index){
                              $.ajax({
                                    url:'/after/delReviewed',
                                    type:'post',
                                    async: false,
                                    data:{previewId:data.previewId}, 
                                    dataType:'json',
                                    success:function(result){
                                        if(result.falt){
                                            //window.location.reload();
                                            layer.msg(result.hint)
                                        }else{
                                            layer.msg(result.hint)
                                        }
                                        
                                   /*  $.each(result,function(index,elel){
                                    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>")
                             }) */
                         },error:function(data){
                             layer.msg("服务器内部异常");
                         }
                    });
                    obj.del();
                    layer.close(index);
                  });
                } /* else if(obj.event === 'editUser'){
                   $($(this)).attr('data-url','preview/getByPreviewid?previewId=' + data.previewId);
                     window.parent.addTab($($(this)));
                }  */
              
              
          else if(obj.event === 'setSign'){
              var url = '${pageContext.request.contextPath}/preview/printPage?carNum='+data.carNum+"&previewId="+data.previewId+"&dateTime="+data.dateTime; 
                    layer.open({
                         type: 2,
                         title: '照片显示', 
                         fix: false, 
                         shadeClose: true, 
                         maxmin: true, 
                         area: ['80%', '100%'],
                         content:encodeURI(url)
                      });
              }
        });
  });
    
    
    </script>
    
</body>

</html>