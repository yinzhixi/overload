<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>执法站点</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    <style type="text/css">
        .layui-inline{padding:10px;}
    </style>
</head>
<body>

<form class="layui-form" action="" style="padding: 20px 10px;">
    <div class="layui-inline">
        <label class="layui-form-label">站点编码</label>
        <div class="layui-input-inline">
            <input type="text" name="stationCode" stationCode="id" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label">站点名称：</label>
        <div class="layui-input-inline" >
            <input type="text" id="stationName"  lay-verify="required|phone" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-inline">
        <div class="layui-input-block">
            <button type="button" class="layui-btn layui-btn-normal" id="query">查询</button>
            <button type="button" class="layui-btn layui-btn-normal" id="addStation">新增</button>
        </div>
    </div>
  </form>
  <div>
    <table class="layui-hide" id="table" lay-filter="action"></table>
  </div>
  
  

 <script type="text/html" id="barAction">
<shiro:hasPermission name="role:del"> 
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</shiro:hasPermission>

<shiro:hasPermission name="role:update">
 	<a class="layui-btn  layui-btn-xs" lay-event="editStation">修改站点</a>
</shiro:hasPermission>
</script>
</body>
    <script type="text/javascript">
    layui.use(['jquery','form', 'layedit', 'table'], function(){
          var form = layui.form
          ,layer = layui.layer
          ,layedit = layui.layedit;         
          var table = layui.table;
          table.render({
            id: 'stationTable',
            elem: '#table'
            ,even: true
            ,url:'/station/listStations'
            ,cols: [[
               {type: 'checkbox', fixed: 'left'}
              ,{field:'id', title: '站点id',align:'center',width:200}
              ,{field:'stationCode',  title: '站点编码',align:'center',width:200}
              ,{field:'province',  title: '所在省份',align:'center',width:200}
              ,{field:'city',  title: '所在城市',align:'center',width:200}
              ,{field:'stationName',  title: '站点名称',align:'center',width:300}
              ,{fixed: 'right',title: '操作', align:'center', toolbar: '#barAction',width:300}
            ]]
          });         
          var $ = layui.$;
        //监听工具条
          table.on('tool(action)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
              layer.confirm('确定要删除吗？', function(index){
            	  $.post("${contextPath}/station/del",{id:data.id},function(res){
            		  if(res.success){
            			  obj.del();
                          layer.close(index);
            		  }else{
            			  layer.open({
            				  title: '失败',
            				  content: res.hint
            			  });
            		  }
                      window.location.reload();
            	  });               
              });
            } else if(obj.event === 'editStation'){
                layer.open({
                    shadeClose: false, //点击遮罩关闭
                    type: 2,
                    title: '修改站点',
                    area: ['400px', '380px'],
                    shade: 0.4,
                    content:'${pageContext.request.contextPath}/station/toEditStation?id='+data.id,
                    btn: ['确认修改', '取消', ],
                    yes: function(index, layero){
                        //alert("sdfghah")
                        var datas = $(layero).find("iframe")[0].contentWindow.formData();
                        if(datas){
                            $.ajax({
                                url:'${pageContext.request.contextPath}/station/editStation',
                                data: datas,
                                type:'POST',
                                processData : false,
                                contentType : false,
                                //mimeType:"multipart/form-data",
                                success: function(rec){
                                    console.log(rec)
                                    //rec = JSON.parse(rec);
                                    if(rec.flat){
                                        layer.closeAll();
                                        layer.msg(rec.hint, {time:3000});
                                        window.location.reload();
                                    }else{
                                        layer.alert(rec.hint);
                                    }
                                },
                                error: function(rec){
                                    layer.alert("服务器异常！");
                                }
                            });
                        }
                    }
                });
            }

          });

        var $ = layui.$;
        $("#query").click(function(){
            // alert("dfb")
            table.reload('stationTable',{
                where:{
                    stationCode: $('#stationCode').val(),
                    stationName: $('#stationName').val()
                }
            });
        });
        $("#addStation").click(function(){
            layer.open({
                shadeClose: false, //点击遮罩关闭
                type: 2,
                title: '添加站点',
                area: ['500px', '280px'],
                shade: 0.4,
                content:'${pageContext.request.contextPath}/station/getToAddStation',
                btn: ['确认添加', '取消', ],
                yes: function(index, layero){
                    //alert("sdfghah")
                    var datas = $(layero).find("iframe")[0].contentWindow.formData();
                    console.log(datas)
                    if(datas){
                        $.ajax({
                            url:'${pageContext.request.contextPath}/station/addStation',
                            data: datas,
                            type:'POST',
                            processData : false,
                            contentType : false,
                            //mimeType:"multipart/form-data",
                            success: function(rec){
                                layer.alert(rec.flat);
                                //window.location.reload();
                            },
                            error: function(rec){
                                layer.alert("服务器异常！");
                            }
                        });
                    }
                }
            });
        });
    });
    
    
    </script>
</html>