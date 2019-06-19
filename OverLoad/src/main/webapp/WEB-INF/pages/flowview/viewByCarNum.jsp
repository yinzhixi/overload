<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="staticPath" value="/static/viewflow/"></c:set>
<%
String wsPath = "ws://"+request.getServerName() + ":" + request.getServerPort();//ws://localhost:8080
%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>视频轮放-按车牌</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
    <style type="text/css">
    .flex-row{
        display: flex;
        display: -webkit-flex;
        margin: auto;
        flex-wrap: wrap;
        justify-content: center;
        text-align: center;
    }

    .img-wrap img{
         cursor: pointer;
         transition: all 0.6s;
     }
    .img-wrap img:hover{
         transform: scale(1.6);
     }
    .flex-row .flex-cell{
        margin: 10px;
        height: 450px;
        flex: 0 0 400px;
    }
    
    .flex-row .flex-cell img{
        width: 100%;
        height: 100%;
    }
    
    .lb{font-weight: 700;}
    
    .img-info{background:#f4f4f4; padding: 5px; text-align:left; height: 48px;width: 387px;border: 1px solid #ccc;}
    </style>
</head>
<body>
    
<form class="layui-form" action="" style="padding: 20px 10px;width: 90%;text-align: center;">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">车牌号</label>
      <div class="layui-input-inline">
        <input type="text" name="carNum" id="carNum" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
    <label class="layui-form-label">站&nbsp;&nbsp;点</label>
        <div class="layui-input-inline">
          <select name="station" id="station">
            <option value=""></option>
            <c:forEach items="${stations }" var="station">
               <option value="${station.stationName }">${station.stationName }</option>
            </c:forEach>
          </select>
        </div>
    </div>
    <div class="layui-inline">
      <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-normal" style  = "width: 80px;height: 30px;line-height: 30px;" id="query">查询</button>
      </div>
    </div>
    
    </div>
  </form>
    <div id="views" class="flex-row">
    <%-- <div class="flex-cell">
        <div class="img-wrap">
            <img alt="" src="${contextPath}/images/v1.jpg">
        </div>
        <div class="img-info">
            <div>
            <span class="lb">蓝豫</span> &nbsp;HES559 &nbsp;&nbsp;
            <span class="lb">总重</span>&nbsp;6000&nbsp;&nbsp;
            <span class="lb">限重</span>&nbsp;2000&nbsp;&nbsp;
            <span class="lb">超重</span>&nbsp;2000
            </div>
            <div style="color: red;">
                2018/3/10 15:02:49
            </div>
        </div>
    </div> --%>
    </div>
    <div style="width: 90%;margin: auto;text-align: center;line-height: 60px;">
        <div id="pageView"></div>
    </div>
</body>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    
    <script type="text/javascript">
    var recive,pageQuery,count,items = [1,2];
    layui.use(['laypage','jquery','form'], function(){
          var form = layui.form
          ,layer = layui.layer
          ,laypage = layui.laypage
          ,$ = layui.$,
          page={
              pageSize: 9,
              page:1
          };
          
          //分页查询
          pageQuery = function () {
              $.get("${contextPath}/flowView/byCarNum/query",{page:page.page,limit:page.pageSize, 
            	  station:$("#station").val(),carNum:$("#carNum").val()},
            	  function(res){
                      renderItems(res.data.list);
                      count = res.count;
                      resetPage(res.count, page.page,false);
                      //t=setTimeout("pageQuery()",2000);
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
          
          var idx = 0;
          renderItems = function (items,append){
              if(!append){
                  append = false;
              }
              var html=[];
              $.each(items,function(i,item){
                  var ovStyle = item.overWeight >0 ?"color: red;":"";
                   var itemHtm = [
                       '<div class="flex-cell" id="'+item.previewId+'"  >',
                       ' <div class="img-wrap">',
                       '     <img alt="" id="'+item.imgUrl+'" src="${staticPath}?fileName='+item.imgUrl+'">',
                       ' </div>',
                        '<div class="img-info">',
                        '    <div style="'+ovStyle+'">',
                        '    <span class="lb">'+(!item.carNum?'':item.carNum)+'</span> &nbsp;&nbsp;',
                        '    <span class="lb">总重</span>&nbsp;'+(!item.totalWeight?'':(item.totalWeight))+'T&nbsp;&nbsp;',
                        '    <span class="lb">限重</span>&nbsp;'+(!item.limitWeight?'':(item.limitWeight))+'T&nbsp;&nbsp;',
                        '    <span class="lb">超重</span>&nbsp;'+(!item.overWeight?'':item.overWeight+'T'),
                        '    </div>',
                        
                        '    <div style="'+ovStyle+'">',
                        '    <span class="lb">车道</span>&nbsp;'+(!item.lane?'':item.lane)+'&nbsp;&nbsp;',
                        '    <span class="lb">轴数</span>&nbsp;'+(!item.axleCnt?'':item.axleCnt)+'&nbsp;&nbsp;',
                        '    <span class="lb">超限率</span>&nbsp;'+(!item.overRage?'':item.overRage)+'&nbsp;&nbsp;',
                        '    </div>',
                        
                        '    <div style="color: red;">'+(!item.date?'':item.date),
                        '    </div>',
                        '</div>',
                    '</div>'
                   ];
                   html.push(itemHtm.join(''));
              });
              if(append){
                  $("#views").prepend(html.join(''));
                  $("#views .flex-cell:gt(8)").remove();
              }else{
                  $("#views").html(html.join(''));
              }
          }
          
          

  	    var websocket = null;
  	    //判断当前浏览器是否支持WebSocket
  	    if ('WebSocket' in window) {
  	        websocket = new WebSocket("<%=wsPath%>/websocket");
  	    } else {
  	        alert('当前浏览器 Not support websocket')
  	    }
  	
  	    //连接发生错误的回调方法
  	    websocket.onerror = function (event) {
  	        setMessageInnerHTML("WebSocket连接发生错误");
  	    };
  		
  	    //连接成功建立的回调方法
  	    websocket.onopen = function () {
  	        setMessageInnerHTML("WebSocket连接成功");
  	    }
  		
  	    //接收到消息的回调方法
  	    websocket.onmessage = function (event) {
  	        if(typeof(event.data) == "string"){
  	        	 var obj = JSON.parse(event.data);
 	            if(obj.type=='vehicle'){
 	            	var item = JSON.parse(obj.data);
 	            	item.imgUrl = item.frontPic;
                    item.totalWeight = formatNum(item.sumWeight);
                    item.limitWeight = formatNum(item.limitWeight);
                    item.overWeight = formatNum(item.overRage);
 		            item.date = item.dateTime;
 		            var arr = [item];
 		            renderItems(arr,true);
 	            }
 	            if(obj.type=='image'){
	            	var node = document.getElementById(obj.data);
	            	if(node){
	            		node.src = "${staticPath}?fileName="+obj.data+"&_="+Math.random();
	            	}
 	            }
  	        }
  	    }
  	
  	    //连接关闭的回调方法
  	    websocket.onclose = function () {
  	        setMessageInnerHTML("WebSocket连接关闭");
  	    }
  	
  	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
  	    window.onbeforeunload = function () {
  	        closeWebSocket();
  	    }
  	
  	    //将消息显示在网页上
  	    function setMessageInnerHTML(innerHTML) {
  	        //document.getElementById('message').innerHTML += innerHTML + '<br/>';
  	    }
  	
  	    //关闭WebSocket连接
  	    function closeWebSocket() {
  	        websocket.close();
  	    }
  	
  	    //发送消息
  	    function send() {
  	        var message = document.getElementById('text').value;
  	        websocket.send(message);
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