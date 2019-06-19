<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="staticPath" value="/static/uploads/"></c:set>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>逆行记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
    
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
                       <select name="stationName" id="stationName">
                         <option value="">请选择站点</option>
                         <c:forEach var="item" items="${stations}">
                          <option value="${item.stationName }" hassubinfo="true" >${item.stationName }</option>
                         </c:forEach>
                       </select>
                   </div>
                </div>



            <div class="layui-inline">
                 <label class="layui-form-label">车牌含：</label>
                   <div class="layui-input-inline">
                      <input type="text" name="carNum" id ="carNum" lay-verify="required|phone" autocomplete="off" class="layui-input">
                   </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label"></label>
                <div class="layui-input-inline" >
                <button id="search" name="search" class="layui-btn  layui-btn-normal" type="button"data-type="reload" style="width:80px;height:35px">查询</button>
                 </div>
            </div>
        </div>
    </blockquote>
    </form>
    </div>
    <fieldset class="layui-elem-field">
        <legend>逆向记录</legend>
        <div class="layui-field-box layui-form table">
            <table class="layui-table" id="table1" lay-filter="demo"></table>
        </div>    
    </fieldset>
    
    <script type="text/html" id="barDemo">
<shiro:hasPermission name="reverse:show">
  <a class="layui-btn layui-btn-xs" lay-event="detail"><cite>查看</cite></a>
</shiro:hasPermission>

<shiro:hasPermission name="reverse:del"> 
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</shiro:hasPermission>
    </script>
    
    <script>
    
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
          elem: '#table1',
          data : tableContent
          ,url:'/preview/getReverseList'
        ,//even: true,
        cols: [
                [
                //{type:'checkbox',fixed: 'left'}, 
                 {
                    field: 'lane',
                    title: '车道',
                    width: 60,
                     align:'center',
                    event: 'setSign',
                
                }, {
                    field: 'carNum',
                    title: '车牌',
                    event: 'setSign',
                    align:'center',
                    width:150
                }, {
                    field: 'createTime',
                    title: '记录时间',
                    width: 200,
                    sort: true,
                    align:'center',
                    event: 'setSign',
                
                },{
                    field: 'frontPic',
                    title: '前抓拍路径',
                    width: 200,
                    event: 'setSign',
                    align:'center',
                    sort:true
                    
                },//{
                //     field: 'snapTime',
                //     title: 'snapTime',
                //     width: 200,
                //     align:'center',
                //     event: 'setSign',
                //
                {
                    field: 'stationName',
                    title: '站点',
                    width: 150,
                    align:'center',
                    event: 'setSign',
                    
                },{fixed: 'right',    title: '操作', width:250, align:'center', toolbar: '#barDemo'}
                ]
            ]
      ,done: function(res, page, count){  
        //如果是异步请求数据方式，res即为你接口返回的信息。  
        //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度  
          
        //分类显示中文名称  
        $("[data-field='venifyPreview']").children().each(function(){  
                if($(this).text()=='1'){  
                   $(this).text("已通过")  
                }else if($(this).text()=='0'){  
                   $(this).text("未审核")  
                }
        })  
        }         
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
                    stationName: $('#stationName').val(),
                    lane: $('#lane').val(),
                    carNum: $('#carNum').val()
                }
            ,page: {
                curr:1
            }
            });
        }); 
               
         $("#export").click(function(){         
              window.location = "${pageContext.request.contextPath}/preview/export2?startDate="
                      +$("#startDate").val()+"&endDate="+$("#endDate").val()+
                      "&station="+$("#station").val()+"&startWeight="+ 
                      $('#startWeight').val()+"&endWeight="+
                      $('#endWeight').val()+"&lane="+$('#lane').val()+
                      "&carNum="+$('#carNum').val()+
                      "&axleCnt="+$('#axleCnt').val()+"&overRate="+
                      $('#overRate').val()+"&recognition="+
                      $('#recognition').val()
                      +"&direction="+$("#direction").val()
                      +"&previewId="+$("#previewId").val()
                      +"&overRate_op="+$("#overRate_op").val()
                      +"&overRate_num="+$("#overRate_num").val()
                      +"&axleCnt_op="+$("#axleCnt_op").val();
            });
            
            
            //监听工具条事件
              table.on('tool(demo)', function(obj){
                //obj表示行对象
                //data表示一行当中的数据
                var data = obj.data;
                if(obj.event === 'detail'){

                    var url ='${pageContext.request.contextPath}/preview/reverDetail?id='+data.id;
                    var str = encodeURI(url);

                    layer.open({
                        type: 2,
                        title: '逆行明细',
                        fix: false,
                        shadeClose: true,
                        maxmin: true,
                        area: ['60%', '80%'],
                        content:str

                        //  content: '<img src=${staticPath}'+ data.frontPic +' width="500" height="350"/>'
                    });

                }else if(obj.event === 'del'){
                          layer.confirm('确定删除吗？', function(index){
                              $.ajax({
                                    url:'/preview/reverseDel',
                                    type:'post',
                                    async: false,
                                    data:{id:data.id},
                                    dataType:'json',
                                    success:function(result){
                                        //window.location.reload();
                                        layer.msg(result)
                                   /*  $.each(result,function(index,elel){
                                    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>")
                             }) */
                         }
                    })
                    obj.del();
                    layer.close(index);
                  });
                } 
                else if(obj.event === 'show'){
                    var url ='${pageContext.request.contextPath}/preview/updatePreview?previewId='+data.previewId; 
                    layer.open({
                    shadeClose: false, //点击遮罩关闭
                      type: 2,
                      content: url,
                      title: false,
                      area: ['70%', '90%'],
                      skin:'layui-layer-rim',
                      btn: ['确定', '取消', ],
                      yes: function(index, layero){
                          var datas = $(layero).find("iframe")[0].contentWindow.formData();
                          if(datas){
                              $.ajax({
                                       url:'/preview/doUpdatePreview',
                                       data:datas,
                                       type:'POST',
                                       processData : false, 
                                       contentType : false,
                                       //mimeType:"multipart/form-data",
                                        success:function(result){
                                            layer.msg("修改成功");
                                            var index =layer.getFrameIndex(window.name); //获取窗口索引
                                            layui.layer.close(index);
                                            //window.location.reload();
                                        }
                               });
                          }
                      },/* bit2:function(index){
                          layer.close(index);
                      }, */
                      cancel:function (index,layero) {
                            
                         },
                     
                     // closeBtn: 0,
                     // shadeClose: true,
                    /* content:'<form class="layui-form" style="padding:20px;" action=""><div class="layui-form-item"><label class="layui-form-label">车道</label><div class="layui-input-block"><input type="text" id="e_lane" name="lane" value="'+data.lane+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-form-item"><label class="layui-form-label">车牌</label><div class="layui-input-block"><input type="text" id="e_carNum" name="carNum" value="'+data.carNum+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-form-item"><label class="layui-form-label">车轴</label><div class="layui-input-block"><input type="text" id="e_axleCnt" name="axleCnt" value="'+data.axleCnt+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-form-item"><label class="layui-form-label">车速</label><div class="layui-input-block"><input type="text" id="e_speed" name="speed" value="'+data.speed+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-form-item"><label class="layui-form-label">总重(kg)</label><div class="layui-input-block"><input type="text" id="e_sumWeight" name="sumWeight" value="'+data.sumWeight+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-form-item"><label class="layui-form-label">超限(kg)</label><div class="layui-input-block"><input type="text" id="e_overRage" name="overRage" value="'+data.overRage+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-form-item"><label class="layui-form-label">超限率%</label><div class="layui-input-block"><input type="text" id="overLoadRate" name="overLoadRate" value="'+data.overLoadRate+'"  class="layui-input" ></div></div>'
                    +'<div class="layui-upload">'
                    +'    <button class="layui-btn" id="test1" type="button">上传图片</button>'
                    +'    <div class="layui-upload-list">'
                    +'      <img class="layui-upload-img" id="demo1">'
                    +'      <p id="demoText"></p>'
                    +'    </div>'
                    +'  </div> '
                    +'<div class="layui-form-item"><div class="layui-input-block"><input type="hidden"  value="'+data.previewId+'"  id="lane6" name="previewId" class="layui-input" ></div></div>'
                    +'</form>' */
                    
                    
                    });
                          
                    
                }else if(obj.event === 'setSign'){                    
                    /* var url ='${pageContext.request.contextPath}/preview/showPhoto?carNum='+data.carNum+"&previewId="+data.previewId;                    
                        var str2 = encodeURI(url);                        
                    //alert("sdfh")
                    layer.open({ 
                              type: 2, 
                              //skin: 'layui-layer-lan', 
                             title: '照片显示', 
                              fix: false, 
                              shadeClose: true, 
                             maxmin: true, 
                              area: ['1000px', '800px'],  
                          content: str2
                          //  content: '<img src=${staticPath}'+ data.frontPic +' width="500" height="350"/>'           
                          });   */
                }
        });
  });

    </script>
    <div id="printDiv" style="overflow: hidden;width: 1px;height: 1px;">
        <div id="printContent"></div>
    </div>
</body>

</html>