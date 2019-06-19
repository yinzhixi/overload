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
    <title>视频轮放-按时间</title>
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
    
    .img-info{background:#f4f4f4; padding: 5px; text-align:left; border: 1px solid #ccc;left: 0;bottom: 0;}
    
.jqzoom{
    text-decoration:none;
    float:left;
}

.clearfix:after{clear:both;content:".";display:block;font-size:0;height:0;line-height:0;visibility:hidden;}
.clearfix{display:block;zoom:1;margin: 10px;}

</style>
</head>
<body>
    
<form class="layui-form" action="" style="padding: 20px 10px;text-align: center;">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始时间</label>
      <div class="layui-input-inline">
        <input type="text" name="startDate" id="startDate" lay-verify="date" placeholder="YYYY-MM-DD hh:mm:ss" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">结束时间</label>
      <div class="layui-input-inline">
        <input type="text" name="endDate" id="endDate" lay-verify="date" placeholder="YYYY-MM-DD hh:mm:ss" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
    <label class="layui-form-label">站&nbsp;&nbsp;点</label>
        <div class="layui-input-inline">
          <select name="station" id="station" lay-filter="station">
            <option value=""></option>
            <c:forEach items="${stations }" var="station">
               <option value="${station.stationName }">${station.stationName }</option>
            </c:forEach>
          </select>
        </div>
    </div>
    <div class="layui-inline">
    <label class="layui-form-label">车&nbsp;&nbsp;向</label>
        <div class="layui-input-inline">
          <select name="direction" id="direction" lay-filter="direction">
            <option value=""></option>
            <c:forEach items="${directions }" var="direction">
               <option value="${direction.id }">${direction.name }</option>
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
    <link rel="stylesheet" type="text/css" href="${contextPath}/jqzoom/css/jqzoom.css" />
    <script type="text/javascript" src="${contextPath}/jqzoom/js/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/jqzoom/js/jquery.jqzoom-min.js"></script>
    
    <script type="text/javascript">
    var recive,pageQuery,count,items = [1,2];
    var websocket = null;

     function renderItems(items,append){
        if(!append){
            append = false;
        }
        var html=[];
        $.each(items,function(i,item){
            var ovStyle = item.overWeight >0 ?"color: red;":"";
             var itemHtm = [
                 '<div class="clearfix" id="'+item.previewId+'">',
                  '<a href="${staticPath}'+item.imgUrl+'" class="jqzoom" title="">',
                  '   <img alt="" id="'+(item.imgId)+'" style="width:500px;" title="" src="${staticPath}'+item.imgUrl+'">',
                  '</a>',
                  '<div class="img-info">',
                  '    <div style="'+ovStyle+'">',
                  '    <span class="lb">'+(!item.carNum?'无车牌':item.carNum)+'</span> &nbsp;&nbsp;',
                  '    <span class="lb">总重</span>&nbsp;'+(!item.totalWeight?'0':(item.totalWeight))+'T&nbsp;&nbsp;',
                  '    <span class="lb">限重</span>&nbsp;'+(!item.limitWeight?'0':(item.limitWeight))+'T&nbsp;&nbsp;',
                  '    <span class="lb">超重</span>&nbsp;'+(!item.overWeight?'0':item.overWeight)+'T',
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
            $("#views .clearfix:gt(8)").remove();
        }else{
            $("#views").html(html.join(''));
        }
        
    }
    
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
                   if(obj.type=='vehicle'){
                       var item = JSON.parse(obj.data);
                       var rate = ((item.sumWeight-item.limitWeight)/item.limitWeight*100).toFixed(2);;
                       
                       item.imgUrl = item.frontPic;
                       item.imgId = item.frontPic;
                       var over = item.sumWeight - item.limitWeight;
                       over = over > 0?formatNum(over):0;
                       
                       item.totalWeight = formatNum(item.sumWeight);
                       item.limitWeight = formatNum(item.limitWeight);
                       item.overWeight = over;
                       
                       if(rate > 0){
                           item.overRage = rate+"%";
                       }else{
                           item.overRage = "0%";
                       }
                       
                       item.date = item.dateTime;
                       var arr = [item];
                       renderItems(arr,true);
                       $('.jqzoom').jqzoom({
                           zoomType: 'innerzoom',
                           preloadImages: false,
                           alwaysOn:false,
                           title: false
                       });
                   }
                   if(obj.type=='image'){
                       var node = document.getElementById(obj.data);
                       console.log(obj.data);
                       if(node){
                           console.log("找到："+obj.data);
                           node.src = "${staticPath}?fileName="+obj.data/* +"&_p="+Math.random() */;
                           node.parentNode.href=node.src;
                           $('.jqzoom').jqzoom({
                               zoomType: 'innerzoom',
                               preloadImages: false,
                               alwaysOn:false,
                               title: false
                           });
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