<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="staticPath" value="/static/uploads/"></c:set>
<%
   String wsPath = "ws://"+request.getServerName() + ":" + request.getServerPort();//ws://localhost:8080
%>
<html>
<head>
    <meta charset="utf-8">
    <title>实时数据</title>
    <meta name="viewport" content="width=device-width, initial-scale=2, maximum-scale=1">
    <link rel="stylesheet" href="${contextPath }/css/global.css" media="all" />
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    <style type="text/css">
        .flex-row{
            justify-content: space-around;
            height: 49%;
            width: 100%;
            padding-top: 10px;
        }
        .flex-cell{
            height: 100%;
            width: 70%;
            border: 1px solid #ccc;
            margin: 5px;
            padding:5px;
            overflow-y:auto;
            border-width:5px 0px 0px 1px;
        }
        .flex-cell1{
            height: 100%;
            width: 30%;
            border: 1px solid #ccc;
            margin: 5px;
        }
        .td{
            border:solid #add9c0; 
            border-width:0px 1px 1px 0px; 
            padding:10px 0px;
        }
        .table{
            width: 100%;
            border:solid #add9c0; 
            border-width:1px 0px 0px 1px;
        }
        .jqzoom{
            text-decoration:none;
            float:left;
        }
    </style>
</head>
<body>
       <div class="flex-row" id="${pageSize}">
            <!-- 最新正常拍照 -->
            <div id="main" class="flex-cell1">
            </div>
            <!-- 正常行驶列表 -->
            <div id="main1" class="flex-cell">
                <table border="1" class="table">
                  <tr>
                    <th class="td" style="width: 15%;align:center"><font color="blue">车牌号</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">总重</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">限重</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">超重</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">车道</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">车速</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">轴数</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">超限率</font></th>
                    <th class="td" style="align:center"><font color="blue">时间</font></th>
                  </tr>
                  <tbody  id="table1">
                  
                  </tbody>
                </table>
            </div>
        </div>
        <div class="flex-row">
            <!-- 最新超限拍照 -->
            <div id="main2" class="flex-cell1">
            </div>
            <!-- 超限行驶列表 -->
            <div id="main3" class="flex-cell"> 
                <table border="1" class="table">
                  <tr>
                    <th class="td" style="width: 15%;align:center"><font color="blue">车牌号</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">总重</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">限重</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">超重</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">车道</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">车速</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">轴数</font></th>
                    <th class="td" style="width: 9%;align:center"><font color="blue">超限率</font></th>
                    <th class="td" style="align:center"><font color="blue">时间</font></th>
                  </tr>
                  <tbody  id="table2">
                  
                  </tbody>
                </table>
            </div>
        </div>
</body>
    
    <link rel="stylesheet" type="text/css" href="${contextPath}/jqzoom/css/jqzoom.css" />
    <script type="text/javascript" src="${contextPath}/jqzoom/js/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/jqzoom/js/jquery.jqzoom-min.js"></script>
<script type="text/javascript">
    $(function() {
        $(".jqzoom").imagezoom();
        $("#thumblist li a").click(function() {
            $(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
            $(".jqzoom").attr('src', $(this).find("img").attr("mid"));
            $(".jqzoom").attr('rel', $(this).find("img").attr("big"));
        });
    });
</script>
<script type="text/javascript">
    window.onload=function()//用window的onload事件，窗体加载完毕的时候
    {
        init(1);//加载超限行驶车辆信息
        init(0);//加载正常行驶车辆信息
    }
    var recive,pageQuery,count,items = [1,2];
    var websocket = null;

             function renderItems(items,append){
                if(!append){
                    append = false;
                }
                
                var html2=[];
                var html1=[];
                var no1=0;
                var no2=0;
                $.each(items,function(i,item){
                    if(item.overWeight >0){
                        no1++;
                        if(append ==false && i==0 || append){
                            var str='<a href="${staticPath}'+item.imgUrl+'" class="jqzoom">'+
                            '<img alt="" id="img2" style="width:500px;height: 100%;" title="" src="${staticPath}'+item.imgUrl+'">'+
                            '</a>';
                            $("#main2").html(str);
                        }
                        var ovStyle = item.overWeight >0 ?"color: red;":"";
                        var imgz=item.imgUrl;
                        var itemHtm2 = [
                               '<tr id="' + item.imgUrl + '" onclick="imgUrl(this.id);">',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.carNum?'无车牌':item.carNum)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.totalWeight?'0':(item.totalWeight))+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.limitWeight?'0':(item.limitWeight))+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.overWeight?'0':item.overWeight)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.lane?'':item.lane)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.lane?'':item.lane)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.axleCnt?'':item.axleCnt)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.overRage?'':item.overRage)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="red">'+(!item.date?'':item.date)+'</font></th>',
                               '</tr>'
                        ];
                        var pageSize=$("flex-row").id;
                        if(append){
                            $('#table2').prepend(itemHtm2.join(''));
                        }else{
                            $('#table2').append(itemHtm2.join(''));
                        }
                        if($('#table2 tr').length >pageSize){
                            $('#table2 tr:last').remove();
                        }
                    }else{
                        no2++;
                        var imgz=item.imgUrl;
                        if(append ==false && i==0 || append){
                            var str='<a href="${staticPath}'+item.imgUrl+'" class="jqzoom">'+
                            '<img alt="" id="img1" style="width:500px;height: 100%" title="" src="${staticPath}'+item.imgUrl+'">'+
                            '</a>';
                            $("#main").html(str);
                        }
                        var itemHtm1 = [
                               '<tr id="' + item.imgUrl + '" onclick="imgUrl2(this.id);">',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.carNum?'无车牌':item.carNum)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.totalWeight?'0':(item.totalWeight))+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.limitWeight?'0':(item.limitWeight))+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.overWeight?'0':item.overWeight)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.lane?'':item.lane)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.lane?'':item.lane)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.axleCnt?'':item.axleCnt)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.overRage?'':item.overRage)+'</font></th>',
                                   '<th class="td" style="align:center"><font color="green">'+(!item.date?'':item.date)+'</font></th>',
                               '</tr>'
                        ];
                        var pageSize=$("flex-row").id;
                        if(append){
                            $('#table1').prepend(itemHtm1.join(''));
                        }else{
                            $('#table1').append(itemHtm1.join(''));
                        }
                        if($('#table1 tr').length >pageSize){
                            $('#table1 tr:last').remove();
                        }
                    }
                });
                
                zoomIt();
            }
             
            //超限行驶
            function imgUrl(value) {
                $("#main2 a").remove();
                var str='<a href="${staticPath}'+value+'" class="jqzoom">'+
                '<img alt="" id="img2" style="width:500px;" title="" src="${staticPath}'+value+'">'+
                '</a>';
                $("#main2").html(str);
                zoomIt();
            }
            
            //正常行驶
            function imgUrl2(value) {
                $("#main a").remove();
                var str='<a href="${staticPath}'+value+'" class="jqzoom">'+
                '<img alt="" id="img1" style="width:500px;" title="" src="${staticPath}'+value+'">'+
                '</a>';
                $("#main").html(str);
                zoomIt();
            }
            
            function zoomIt(){
                $('.jqzoom').jqzoom({
                    zoomType: 'innerzoom',
                    preloadImages: false,
                    alwaysOn:false,
                    title: false
                });
            }
          function init(overRage){
              closeWebSocket();
              connect();
              $.get("${contextPath}/flowView/byTime/queryOverRagePreview",{page:1,limit:6, 
                  overRage:overRage},
                  function(res){
                      count = res.count;
                      renderItems(res.data.list);
                  }
              );
          }
          //connect();
          function connect(){
              var station = "";
              var direction = "";
               //判断当前浏览器是否支持WebSocket
              if ('WebSocket' in window) {
                  websocket = new WebSocket("<%=wsPath%>/websocket?station="+station+"&direction="+direction);
              } else {
                  alert('当前浏览器 Not support websocket');
              }
             
              //接收到消息的回调方法
              websocket.onmessage = function (event) {
                  if(typeof(event.data) == "string"){
                      var obj = JSON.parse(event.data);
                      if(obj.type=='vehicle'){
                          init(1);//加载超限行驶车辆信息
                          init(0);//加载正常行驶车辆信息
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
                      }
                  }
              }
              //连接关闭的回调方法
              websocket.onclose = function () {
                  //setMessageInnerHTML("WebSocket连接关闭");
              }
          }       
           //关闭WebSocket连接
           function closeWebSocket() {
               if(websocket != null){
                   websocket.close();
               }
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
    </script>
</html>
