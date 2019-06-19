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
                   <label class="layui-form-label">用户账户：</label>
                   <div class="layui-input-inline">
                      <input type="text" id="userId" name="userId" lay-verify="required|phone" autocomplete="off" class="layui-input">
                   </div>
            </div>
            
            <div class="layui-inline">
                   <label class="layui-form-label">模块：</label>
                   <div class="layui-input-inline">
                      <input type="text" id="module" name="module" lay-verify="required|phone" autocomplete="off" class="layui-input">
                   </div>
            </div>
            
            <div class="layui-inline">
                   <label class="layui-form-label">请求：</label>
                   <div class="layui-input-inline">
                      <input type="text" id="method" name="method" lay-verify="required|phone" autocomplete="off" class="layui-input">
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
            <legend>系统日志</legend>
            <div class="layui-field-box layui-form table">
                <table class="layui-table" id="logTable" lay-filter="logTable"></table>
            </div>    
        </fieldset>
    </div>

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
          id: 'logTable',
          elem: '#logTable',
          data : tableContent
          ,url:'/system/log/list/data'
          ,
        cols: [
                [
                 {
                    field: 'userId',
                    title: '账户',
                    width: 100,
                    event: 'setSign',
                 }
                 ,{
                    field: 'module',
                    title: '模块',
                    width: 200,
                }, {
                    field: 'method',
                    title: '请求',
                    width: 200,
                }, {
                    field: 'ip',
                    title: '访问ip',
                    width: 150,
                }, {
                    field: 'reqTime',
                    title: '请求时间',
                    width: 200,
                    sort: true,
                }, {
                    field: 'useTime',
                    title: '用时',
                    width: 100,
                    sort: true,
                },{
                    field: 'description',
                    width: 200,
                    title: '描述'
                } 
                ]
            ]
          ,page: true
          ,limit:100,
        });
       
        var $ = layui.$;
        $("#search").click(function(){
            table.reload('logTable',{
                where:{
                	userAccount: $('#userId').val(),
                    module: $('#module').val(),
                    method: $('#method').val()
                }
            });
        }); 
  });
    </script>
</body>

</html>