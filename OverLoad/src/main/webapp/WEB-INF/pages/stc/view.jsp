<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="staticPath" value="/static/uploads/"></c:set>
<%
String wsPath = "ws://"+request.getServerName() + ":" + request.getServerPort();//ws://localhost:8080
%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>静态称重-主页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${contextPath}/jqzoom/common.css" media="all">
    <link rel="stylesheet" type="text/css" href="${contextPath}/jqzoom/css/jqzoom.css" />
    <script type="text/javascript" src="${contextPath}/jqzoom/js/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/jqzoom/js/jquery.jqzoom-min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    <style type="text/css">
        .jqzoom{
            text-decoration:none;
            float:right;
            height: 100%;
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="layui-fluid" style="padding: 20px;">
        <div class="layui-row layui-col-space10" style="margin-left: 100px">
            <div class="layui-col-md8">
                <div class="layui-row" style="height: 200px;">
                    <div class="layui-col-md6" style="height: 100%;">
                        <div style="height:80%;background-color: black;">
                            <div style="height:20%;line-height:20px;text-align:right;padding:2px 10px; color: #ffffff;">取重</div>
                            <div id="viewWeight" style=" text-align:center;padding:0 10px;font-size: 50px;color: #7CFC00;"></div>
                        </div>
                        <div style="height: 20%;background-color: black;">
                            <div style="border-top: 1px solid #ffffff;color:white;font-size:12px;padding:0 10px; height: 30px;line-height: 30px;">
                                <span>(单位：公斤)称台</span>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md2 layui-col-space3"style="height: 100%;margin-left: 1px;border: 1px solid #ccc;text-align: center;font-size: 20px">
                        <div style="margin-top: 10px">称重方式</div>
                        <div class="layui-input-block" style="margin-top:10px;margin-left: 1px;color: #00b0f0;">
                            <input type="radio" name="sex" value="0" title="自动" style="size: A4">&nbsp;自&nbsp;动
                        </div>
                        <div class="layui-input-block" style="margin-top:10px;margin-left: 1px;color: #00b0f0;">
                            <input type="radio" name="sex" value="2" title="手动">&nbsp;手&nbsp;动
                        </div>
                    </div>
                    <div class="layui-col-md2 layui-col-space3" style="height: 100%;margin-left: 1px;border: 1px solid #ccc;text-align: center;font-size: 20px">
                        <div style="margin-top: 10px">手动方式</div>
                        <div class="layui-input-block" style="margin-top:10px;margin-left: 1px;color: #00b0f0;">
                            <input type="radio" name="sex" value="1" title="标准称重" style="size: A4">&nbsp;标准称重
                        </div>
                        <div class="layui-input-block" style="margin-top:10px;margin-left: 1px;color: #00b0f0;">
                            <input type="radio" name="sex" value="2" title="简单称重">&nbsp;简单称重
                        </div>
                        <div class="layui-input-block" style="margin-top:10px;margin-left: 1px;color: #00b0f0;">
                            <input type="radio" name="sex" value="3" title="直接称重">&nbsp;直接称重
                        </div>
                    </div>
                    <div class="layui-col-md2 layui-col-space3" style="height: 100%;margin-left: 1px;border: 1px solid #ccc;text-align: center;font-size: 20px">
                        <div style="margin-top: 10px">道闸控制</div>
                        <div style="margin-top:10px;">
                            <button type="button" class="layui-btn">抬&nbsp;&nbsp;起</button>
                        </div>
                        <div style="margin-top:10px;">
                            <button type="button" class="layui-btn layui-btn-danger">落&nbsp;&nbsp;下</button>
                        </div>
                    </div>
                </div>
                <form class="layui-row">
                        <div class="layui-col-md6" style="border: 1px solid #ccc;">
                            <input id="staticid" name="staticid" type="hidden"/>
                            <div class="layui-form-item" style="margin-top: 50px;text-align: center;">
                                <label class="layui-form-label">卡&#12288;&#12288;号</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="identity" name="identity" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item" style="text-align: center;">
                                <label class="layui-form-label">车&ensp;牌&ensp;号</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="carnum" name="carnum" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">货物名称</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="cargoname" name="cargoname" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">规格型号</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="specificationtype" name="specificationtype" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">发货单位</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="forwardunit" name="forwardunit" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">运输单位</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="trafficunit" name="trafficunit" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">司&#12288;&#12288;机</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="driver" name="driver" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item" style="margin-bottom: 50px;">
                                <label class="layui-form-label">备&#12288;&#12288;注</label>
                                <div class="layui-input-block" style="width: 70%">
                                  <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md6" style="border: 1px solid #ccc; ">
                            <div class="layui-col-md12">
                                <div class="layui-form-item"style="margin-top: 50px;">
                                    <label class="layui-form-label">毛&#12288;&#12288;重</label>
                                    <div class="layui-input-block" style="width: 70%">
                                        <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">皮&#12288;&#12288;重</label>
                                    <div class="layui-input-block" style="width: 70%">
                                        <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">扣&#12288;&#12288;杂</label>
                                    <div class="layui-input-block" style="width: 70%">
                                        <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item"style="margin-top: 20px;">
                                    <label class="layui-form-label">流水&#12288;号</label>
                                    <div class="layui-input-block" style="width: 70%">
                                        <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">净&#12288;&#12288;重</label>
                                    <div class="layui-input-block" style="width: 70%">
                                        <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">实际重量</label>
                                    <div class="layui-input-block" style="width: 70%">
                                        <input type="text" id="remark" name="remark" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-md12"style="height:60%;margin-bottom: 35px;">
                                <div style="margin-top:46px;margin-left: 150px;margin-bottom:18px;float: left">
                                    <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;</button>
                                </div>
                                <div style="margin-top:46px;margin-right:150px;margin-bottom:18px;float: right">
                                    <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">&nbsp;打&nbsp;&nbsp;&nbsp;印&nbsp;</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="layui-col-md3" style="height:100%;border: 1px solid #ccc;">
                    <div  style="height:350px ;width:100%;border: 1px solid #ccc;">

                    </div>
                    <div style="margin-top:5px;height:350px;width:100%;border: 1px solid #ccc;">

                    </div>
                </div>
        </div>
    </div>
</body>
    <script type="text/javascript">
    var recive,pageQuery,count,items = [1,2];
    var websocket = null;
    layui.use(['laypage','jquery','form','laydate'], function(){
          var form = layui.form
          ,layer = layui.layer
          ,laypage = layui.laypage
          ,$ = layui.$,
          page={
              pageSize: 9,
              page:1
          }
          ,laydate = layui.laydate;
            //监听提交
            form.on('submit(addStc)', function(data){
                var staticid = $("#staticid").val();
                var specificationtype = $("#specificationtype").val();
                var carnum = $("#carnum").val();
                var cargoname = $("#cargoname").val();
                var forwardunit = $("#forwardunit").val();
                var trafficunit = $("#trafficunit").val();
                var driver = $("#driver").val();
                var remark = $("#remark").val();
                var datas=data.field;
                $.ajax({
                    url:"${pageContext.request.contextPath}/staticWeight/edit",
                    data:{"staticid":staticid,
                          "specificationtype":specificationtype,
                          "carnum":carnum,
                          "cargoname":cargoname,
                          "forwardunit":forwardunit,
                          "trafficunit":trafficunit,
                          "driver":driver,
                          "remark":remark
                    },
                    type:"POST",
                    dataType:"json",
                    success:function(msg){
                        if(msg.code){
                            layer.msg('正在提交请稍候。。。', {icon: 16,time: 1000,shade : [0.5 , '#000' , true]},function(){
                                layer.msg("编辑成功",{time:3000});
                            });
                        }else{
                            layer.msg("编辑失败");
                        }
                    },
                    error:function(error){
                        layer.msg("服务器内部异常")
                    }
                });
                return false;
            });
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
          
          //日期
          /* laydate.render({
            elem: '#startDate',
            value: '${curDay}',
            isTime:true,
            
          });
          laydate.render({
            elem: '#endDate',
            value: '${nextDay}'
          }); */
          
          //分页查询
          pageQuery = function() {
        	  closeWebSocket();
        	  connect();
              $.get("${contextPath}/flowView/byTime/query",{page:page.page,limit:page.pageSize, 
                  station:$("#station").val(),startDate:$("#startDate").val(),endDate:$('#endDate').val(),direction:$("#direction").val()},
                  function(res){
                      renderItems(res.data.list);
                      $('.jqzoom').jqzoom({
                          zoomType: 'innerzoom',
                          preloadImages: false,
                          alwaysOn:false,
                          title: false
                      });
                      count = res.count;
                      resetPage(res.count, page.page,false);
                      //t= setTimeout("pageQuery()",2000);
                  }
              );
          }
          
          //重置分页(跳转分页)
          function resetPage(total, pageIndex,flush) {
            if(!flush){
                flush = true;
            }
            //调用分页
              laypage.render({
                elem: 'pageView'
                ,count: total
                ,limit: page.pageSize
                ,curr: pageIndex//当前页
                ,jump: function(obj,first){
                    if (!first) {
                        page.page = obj.curr;
                        if(flush){
                            pageQuery();
                        }
                    }
                }
              });
          }
          
          pageQuery();
          $("#query").click(function(){
             page.page = 1;
             pageQuery();
          });
          form.on('select(station)',function(data){
              page.page = 1;
              pageQuery();
              //var setting = {setting:{direction:data.value}};
              //reset(setting);
          });
          form.on('select(direction)',function(data){
              page.page = 1;
              pageQuery();
              //var setting = {setting:{direction:data.value}};
              //reset(setting);
          });
          
          function reset(data){
              var msg = {header: 'setting',content: JSON.stringify(data)};
              send(JSON.stringify(msg));
          }
          
          var idx = 0;
          //
          
       //connect();
       
       function connect(){
    	   var station = $("#station").val();
    	   var direction = $("#direction").val();
    	    //判断当前浏览器是否支持WebSocket
    	    
           if ('WebSocket' in window) {
               websocket = new WebSocket("<%=wsPath%>/websocket?station="+station+"&direction="+direction);
           } else {
               alert('当前浏览器 Not support websocket');
           }
           
           //连接发生错误的回调方法
           websocket.onerror = function (event) {
               setMessageInnerHTML("WebSocket连接发生错误");
           };
           
           //连接成功建立的回调方法
           websocket.onopen = function () {
               setMessageInnerHTML("WebSocket连接成功");
               //websocket.send("hi");
           };
           
           //接收到消息的回调方法
           websocket.onmessage = function (event) {
               if(typeof(event.data) == "string"){
                   var obj = JSON.parse(event.data);
                   console.log(obj);
                   $("#specificationType").val(obj.type);
                   if(obj.type=='vehicle'){
                	   var item = JSON.parse(obj.data);
                	   var weightStr = item.weight;
                	   var type = item.type;
                	   var staticid=item.staticId;
                	   $("#staticid").val(staticid);
                	   if(type == 'r'){
                           $("#viewWeight").html(weightStr);
                	   }
                       if(type == 'd'){
                    	   $("#viewWeight").append();
                    	   <%--$("#frontImg").attr("src",'${staticPath}'+item.frontPic);--%>
                           <%--$("#backImg").attr("src",'${staticPath}'+item.backPic);--%>
                       }
                   }
               }
           }
           
           //连接关闭的回调方法
           websocket.onclose = function () {
               setMessageInnerHTML("WebSocket连接关闭");
           }
           
       }
	
	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function () {
	        closeWebSocket();
	    }
	
	    //将消息显示在网页上
	    function setMessageInnerHTML(innerHTML) {
	        //document.getElementById('message').innerHTML += innerHTML + '<br/>';
	        console.log(innerHTML);
	    }
	
	    //关闭WebSocket连接
	    function closeWebSocket() {
	    	if(websocket != null){
    	        websocket.close();
	    	}
	    }
	
	    //发送消息
	    function send(message) {
	        //websocket.send("hi");
	    }
	    
	    function formatNum(num){
	    	var result = parseFloat(num);
	        if (isNaN(result)) {
    	        return false;
	        }else{
	        	result = result/1000;
	        }
	        return result.toFixed(2);
	    }
	    
    });
	</script>
</html>