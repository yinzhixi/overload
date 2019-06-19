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

    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />
    
</head>

<body class="childrenBody">
    <div class="admin-main">
    <form class="layui-form" action="">
        <blockquote class="layui-elem-quote">
        <div class="layui-form-item">
            
            <div class="layui-inline">
                   <label class="layui-form-label">路径名称：</label>
                   <div class="layui-input-inline">
                      <input type="text" id="pathname" name="pathname" autocomplete="off" class="layui-input">
                   </div>
            </div>
            
            <div class="layui-inline">
                   <label class="layui-form-label">路径代号：</label>
                   <div class="layui-input-inline">
                      <input type="text" id="pathCode" name="pathCode" autocomplete="off" class="layui-input">
                   </div>
            </div>
            
            <div class="layui-inline">
                     <label class="layui-form-label"></label>
                       <div class="layui-input-inline">
                         <button id="search" name="search" class="layui-btn  layui-btn-normal" type="button" data-type="reload" style="width:80px;height:35px">查询</button>
                        </div>
                </div>
        </div>
            
    </blockquote>
    </form>
    
        <fieldset class="layui-elem-field">
            <legend>系统设置</legend>
            <div class="layui-field-box layui-form table">
                <table class="layui-table" id="setTable" lay-filter="setTable"></table>
            </div>    
        </fieldset>

        <script type="text/html" id="barDemo">
            <shiro:hasPermission name="path:del">
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delPath">删除</a>
            </shiro:hasPermission>

        </script>
    </div>
    <script type="text/html" id="checkboxTpl">
        <!-- 这里的 checked 的状态只是演示 -->
        <input type="checkbox" name="status" value="{{d.status}}" title="正常" lay-filter="lockDemo" {{ d.status =='1' ? 'checked' : '' }}>
    </script>

    <script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>
    <script>
    layui.use(['jquery','form', 'layedit', 'table'], function(){
        var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit;
       
        var tableContent = new Array();
        var table = layui.table;
       table.render({
          id: 'setTable',
          elem: '#setTable',
           align:'center',
          data : tableContent
           ,url:'${contextPath}/system/path/list/data'
          ,cols: [
                [
                 {
                    field: 'id',
                    title: 'ID',
                     width: 50
                 }
                 ,{
                    field: 'pathname',
                    title: '路径名称',
                    width: 200,
                }, {
                    field: 'pathCode',
                    title: '路径编码',
                    width: 300,
                    edit: 'text',
                }, {
                    field: 'path',
                    title: '物理路径',
                    width: 300,
                    edit: 'text',
                }, {
                    field: 'status',
                    title: '所处状态', width:110, templet: '#checkboxTpl', unresize: true
                }, {
                    field: 'createtime',
                    edit: 'text',
                    width: 400,
                    readonly:"readonly",
                    title: '创建时间',
                    event: 'setSign',

                },{fixed: 'right',	title: '操作', width:300, align:'center', toolbar: '#barDemo'}
                ]
            ]
          ,page: true
          ,limit:100,
        });
        //监听锁定操作
        form.on('checkbox(lockDemo)', function(obj){
            //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
            if(obj.elem.checked){
                this.value='0';
                obj.data.status='0'
            }else{
                this.value='1';
                obj.data.status='1'
            }
        });
       
     //监听单元格编辑
       table.on('edit(setTable)', function(obj) {
           var value = obj.value //得到修改后的值
           , data = obj.data //得到所在行所有键值
           , field = obj.field; //得到字段
           $.ajax({
               url : '${contextPath}/system/path/doUpdate',
               dataType : 'json',
               type : 'post',
               data : {
                   "path":data.path,
                   "status" : data.status,
                   "pathCode": data.pathCode,
                   "pathname" : data.pathname,
                   "id" : data.id
               },
               success : function(result) {
                   if (result >= 1) {
                       layer.msg("已保存", {
                           icon : 6
                       });
                       reloadCacheTable();
                   } else {
                       layer.msg("保存失败", {
                           icon : 5
                       });
                   }
               },
               error : function() {
                   layer.alert("系统错误");
               }
           });
       });
     
        var $ = layui.$;
        $("#search").click(function(){
            table.reload('setTable',{
                where:{
                    pathname: $('#pathname').val(),
                    pathCode: $('#pathCode').val()
                }
            });
        }); 
        
        
  });
    </script>
</body>

</html>