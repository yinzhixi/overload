<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>权限信息</title>
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
  <div class="layui-form-item"style="text-align:right;">
    <div class="layui-inline" >
   <shiro:hasPermission name="permission:add">
      <button type="button" class="layui-btn layui-btn-normal" id="add">新增</button>
    </shiro:hasPermission>
    </div>
    </div>
  </form>
  <form id="addForm" class="layui-form" action="" style="display:none; padding: 20px 10px;">
    <div class="layui-inline">
      <label class="layui-form-label">权限</label>
      <div class="layui-input-inline">
        <input type="text" name="id" id="id" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">权限描述</label>
      <div class="layui-input-inline">
        <input type="text" name="name" id="name" autocomplete="off" class="layui-input">
      </div>
    </div>
  </form>
  <div>
    <table class="layui-hide" id="table" lay-filter="action"></table>
  </div>
  
 <script type="text/html" id="barAction">
 <shiro:hasPermission name="permission:del">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
  </shiro:hasPermission>

 <shiro:hasPermission name="permission:update">
	<a class="layui-btn  layui-btn-xs" lay-event="editPerminss">修改权限</a>
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
            id: 'permissionTable',
            elem: '#table'
            ,even: true
            ,url:'${contextPath}/permission/list'
            ,cols: [[
              {field:'id', title: '权限', width:200}
              ,{field:'name',  title: '权限描述', width:200}
              ,{fixed: 'right', title: '操作' ,align:'center', toolbar: '#barAction', width:300}
            ]]
          ,page: true
    	    ,limit:10,  
          });
          
          var $ = layui.$;
          $("#add").click(function(){
        	  layer.open({
        		  title: '增加'
        		  ,content: $("#addForm").html()
        		  ,yes: function(index, layero){
        			  $.post("${contextPath}/permission/save",{id: $(layero).find("#id").val(),name:$(layero).find("#name").val()},function(res){
        				  if(res.success){
        					  layer.close(index);
        					  table.reload('permissionTable',{
        		              });
        				  }
        			  });
        	       }
        		});
          });
          
        //监听工具条
          table.on('tool(action)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
            } else if(obj.event === 'del'){
              layer.confirm('确定要删除吗？', function(index){
            	  $.post("${contextPath}/permission/del",{id:data.id},function(res){
            		  if(res.success){
            			  obj.del();
                          layer.close(index);
            		  }else{
            			  layer.open({
            				  title: '失败',
            				  content: res.msg
            			  });
            		  }
            	  });
                
              });
            } else if(obj.event === 'edit'){
              layer.alert('编辑行：<br>'+ JSON.stringify(data))
            } else if(obj.event === 'editPerminss'){
            	//alert("zfhg")          	
            	layer.open({
                	shadeClose: false, //点击遮罩关闭
            		  type: 1,
            		  title: false,
            		  area: ['300px', '150px'],
            		  skin:'layui-layer-rim',
        			  btn: ['确定', '取消', ],
        			  yes: function(index, layero){ 
        				  var id = $("#permiss").val();				     	
        				  $.ajax({
        				            url:'/permission/updatePermission',
        				            type:'post',
        				            
        				           data:{name:data.name,id:id
        				        	}, 					           				         
        				            dataType:'json',
        				            success:function(result){						            	
        				            	layer.msg(result.hint);
        				          	window.location.reload();
        				            						            							           	
        					       /*   $.each(result,function(index,elel){
        						    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>")
        					 })  */
        				 }
        			})
        			  },
            		  closeBtn: 0,
            		  shadeClose: true,
            		  content: 
            		  '<div class="layui-form-item"><label class="layui-form-label">权限</label><div class="layui-input-block"><input type="text" id="permiss" name="id" value="'+data.id+'"  class="layui-input" ></div></div>'
            		});                	
            }
          });
          
    });
    
    
    </script>
</html>