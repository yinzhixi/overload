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
    <title>异常明细表</title>
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

<body class="childrenBody" >
    <div class="admin-main" >
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
                
                <div class="layui-inline" >
                     <label class="layui-form-label">车道：</label>
                   <div class="layui-input-inline">
                      <input type="number" id="lane" name="lane" lay-verify="required|phone" class="layui-input">
                   </div>
                </div>
                
            <div class="layui-inline">
                 <label class="layui-form-label">车牌号：</label>
                   <div class="layui-input-inline">
                      <input type="text" name="carNum" id ="carNum" lay-verify="required|phone" autocomplete="off" class="layui-input">
                   </div>
            </div>
            
            <div class="layui-inline">
                <label class="layui-form-label">车牌识别：</label>
                <div class="layui-input-inline">
                      <select style="width:61%;height:30px" name ="recognition" id="recognition">
                           <option value="">请选择识别状态</option>
                            <option value="1">已识别</option>
                            <option value="0">未识别</option>
                        </select>
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
                <label class="layui-form-label">行车方向：</label>
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
                    <div class="layui-input-inline" style="width:400px;height:35px">
                        <button id="search" name="search" class="layui-btn  layui-btn-normal" type="button"data-type="reload" style="width:60px;height:35px">查询</button>
                        <button id="dels" name="dels" class="layui-btn  layui-btn-normal" type="button"data-type="reload" style="width:90px;height:35px">批量删除</button>
                   </div>
            </div>
        </div>
    </blockquote>
    </form>
    </div>
        <fieldset class="layui-elem-field"  >
            <legend>异常数据明细表</legend>
            <div class="layui-field-box layui-form table" style="">
                <table class="layui-table" id="table1" lay-filter="demo"></table>
            </div>
        </fieldset>

    <script type="text/html" id="barDemo">
        <shiro:hasPermission name="abnormal:del">
          <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="abnormal:detail">
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="detail">异常明细</a>
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
          ,url:'/abnormal/abnormalList'
          ,align:'center'
        // ,even: true,
       , cols: [
                [
                 {type:'checkbox',fixed: 'left'},
                 {
                    field: 'abnormalid',
                    title: '异常序号',
                    width: 160,
                    event: 'setSign',
                 }
                 ,{
                    field: 'lane',
                    title: '车道',
                    width: 60,
                    event: 'setSign',
                }, {
                    field: 'carnum',
                    title: '车牌',
                    event: 'setSign',
                    width:150
                }, {
                    field: 'axlecnt',
                    title: '轴数',
                    width: 60,
                    event: 'setSign',
                }, {
                    field: 'datetime',
                    title: '时间',
                    width: 200,
                    sort: true,
                    event: 'setSign',
                
                },{
                    field: 'speed',
                    title: '车速(km/h)',
                    width: 120,
                    event: 'setSign',
                    sort:true
                    
                },{
                    field: 'sumweight',
                    title: '总重(kg)',
                    width: 100,
                    event: 'setSign',
                    
                },{
                    field: 'limitweight',
                    title: '限重(kg)',
                    width: 100,
                    event: 'setSign',
                    
                },{
                    field: 'overrage',
                    title: '超限(kg)',
                    width: 100,
                    event: 'setSign',
                    
                } , {
                    field: 'overloadrate',
                    title: '超限率%',
                    width: 100,
                    event: 'setSign',
                    
                } ,{fixed: 'right',    title: '操作', width:250, align:'center', toolbar: '#barDemo'}
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
                    station: $('#station').val(),
                    startWeight: $('#startWeight').val(),
                    endWeight: $('#endWeight').val(),
                    lane: $('#lane').val(),
                    carNum: $('#carNum').val(),
                    axleCnt: $('#axleCnt').val(),
                    axleCnt_op: $('#axleCnt_op').val(),
                    overRate: $('#overRate').val(),
                    overRate_op:$('#overRate_op').val(),
                    overRate_num:$('#overRate_num').val(),
                    recognition: $('#recognition').val(),
                    direction:$("#direction").val(),
                    previewId:$("#previewId").val(),
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
        $("#dels").click(function(){//批量删除
            var $ = layui.$;
            var checkStatus = table.checkStatus('calc')
                ,data = checkStatus.data;
            //layer.alert(JSON.stringify(data));
            var previewIds=[];
            for (var i = 0; i < data.length; i++) {//循环筛选出id
                previewIds.push(data[i].previewId);
            }
            $.ajax({
                url:"/preview/delBatches",
                data:{previewIds:previewIds},
                dataType:"json",
                type:"post",
                traditional:true,//防止深度序列化
                success:function(){
                    layer.msg('正在提交请稍候。。。', {icon: 16,time: 600,shade : [0.5 , '#000' , true]},function(){
                        });
                    window.location.reload();

                }
            });
        });
            
            //监听工具条事件
              table.on('tool(demo)', function(obj){
                //obj表示行对象
                //data表示一行当中的数据
                var data = obj.data;
                if(obj.event === 'detail'){
                  layer.msg('ID：'+ data.abnormalid + ' 的查看操作');
                    var url ='${pageContext.request.contextPath}/abnormal/getAllImgs?abnormalid='+data.abnormalid;
                    var str = encodeURI(url);

                    layer.open({
                        type: 2,
                        title: '异常车辆明细',
                        fix: false,
                        shadeClose: true,
                        maxmin: true,
                        area: ['85%', '100%'],
                        content:str
                        //  content: '<img src=${staticPath}'+ data.frontPic +' width="500" height="350"/>'
                    });
                } else if(obj.event === 'del'){
                          layer.confirm('确定删除吗？', function(index){
                              $.ajax({
                                    url:'/abnormal/delAbnormal',
                                    type:'post',
                                    async: false,
                                    data:{abnormalid:data.abnormalid},
                                    dataType:'json',
                                    success:function(result){
                                        //window.location.reload();
                                        layer.msg("删除成功")
                                   /*  $.each(result,function(index,elel){
                                    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>")
                             }) */
                         }
                    })
                    obj.del();
                    layer.close(index);
                  });
                } 
                else if(obj.event === 'editUser'){
                    if(data.venifyPreview=='1'){
                        layer.alert("该记录已审核通过,无法修改！");
                        return;
                    }
                    var url ='${pageContext.request.contextPath}/preview/updatePreview?previewId='+data.previewId; 
                    layer.open({
                    shadeClose: false, //点击遮罩关闭
                      type: 2,
                      content: url,
                      title: false,
                      area: ['60%', '90%'],
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
                else if(obj.event === 'reviewedPreview'){
                        var url ='${pageContext.request.contextPath}/preview/getAllImgs?previewId='+data.previewId+"&carNum="+data.carNum+"&dateTime="+data.dateTime; 
                          var str2 = encodeURI(url);
                        layer.open({
                            shadeClose: false, //点击遮罩关闭
                              type: 2,
                              title: '抓拍照片显示',
                              area: ['70%', '90%'],
                              shade: 0.4,
                              content:str2,
                              btn: ['确认审核并打印','确认审核', '取消'],
                              yes: function(index,layero){
                                  var datas = $(layero).find("iframe")[0].contentWindow.formData();
                                  if(datas){
                                      $.ajax({
                                          url:'${pageContext.request.contextPath}/after/addReviewedPreview',
                                          data: datas,
                                          type:'POST',
                                          processData : false,
                                          contentType : false,
                                          success: function(rec){
                                              if(rec.flat){
                                                  //console.log(rec);
                                                  layer.closeAll();
                                                  layer.msg(rec.hint, {time:500},function(){
                                                      $(".layui-laypage-btn").click();
                                                  });
                                                  data.enforcement=rec.enforcement;
                                                  data.enforcementTwo=rec.enforcementTwo;
                                                  printContent(data);
                                              }else{
                                                  layer.alert(rec.hint);
                                              }
                                          },
                                          error: function(rec){
                                              layer.alert("服务器异常！");
                                          }
                                      });
                                  }
                              },
                              btn2: function(index, layero){
                                    var datas = $(layero).find("iframe")[0].contentWindow.formData();
                                    if(datas){
                                        $.ajax({
                                            url:'${pageContext.request.contextPath}/after/addReviewedPreview',
                                            data: datas,
                                            type:'POST',
                                            processData : false, 
                                            contentType : false,
                                            success: function(rec){
                                                if(rec.flat){
                                                    layer.closeAll();
                                                    layer.msg(rec.hint, {time:500},function(){
                                                        $(".layui-laypage-btn").click();
                                                    });
                                                }else{
                                                    layer.alert(rec.hint);
                                                }
                                            },
                                            error: function(rec){
                                                layer.alert("服务器异常！");
                                            }
                                        });
                                    }
                                },
                                btn3:function(){
                                    layer.closeAll();
                                }
                                
                            });
                };
        });
  });

    function printContent(data){
        var printPageUrl = '${pageContext.request.contextPath}/preview/printPage?carNum='+data.carNum+"&previewId="+data.previewId+"&dateTime="+data.dateTime+"&enforcement="+data.enforcement+"&enforcementTwo="+data.enforcementTwo;
        $.get(encodeURI(printPageUrl),function(data){
            $("#printContent").html(data);
            $("#printContent").print();
        });
    }

    //单击行勾选checkbox事件
    $(document).on("click",".layui-table-body table.layui-table tbody tr", function () {
        var index = $(this).attr('data-index');
        var tableBox = $(this).parents('.layui-table-box');
        //存在固定列
        if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length>0) {
            tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
        } else {
            tableDiv = tableBox.find(".layui-table-body.layui-table-main");
        }
        var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
        if (checkCell.length>0) {
            checkCell.click();
        }
    });

    $(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function (e) {
        e.stopPropagation();
    });
    </script>
    <div id="printDiv" style="overflow: hidden;width: 1px;height: 1px;">
        <div id="printContent"></div>
    </div>
</body>

</html>