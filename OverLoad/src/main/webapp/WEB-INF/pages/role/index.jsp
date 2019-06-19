<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>角色信息</title>
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
    <shiro:hasPermission name="role:add"> 
      <button type="button" class="layui-btn layui-btn-normal" id="add">新增</button>
      </shiro:hasPermission>
    </div>
    </div>
  </form>
  <form id="addForm" class="layui-form" action="" style="display:none; padding: 20px 10px;">
    <div class="layui-inline">
      <label class="layui-form-label">id</label>
      <div class="layui-input-inline">
        <input type="text" name="id" id="id" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">名称</label>
      <div class="layui-input-inline">
        <input type="text" name="name" id="name" autocomplete="off" class="layui-input">
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

 <shiro:hasPermission name="role:editPermission"> 
  <a class="layui-btn  layui-btn-xs" lay-event="editPermission">编辑权限</a>
  </shiro:hasPermission> 

<shiro:hasPermission name="role:editMenu"> 
 <a class="layui-btn  layui-btn-xs" lay-event="editMenu">编辑菜单</a>
</shiro:hasPermission>

<shiro:hasPermission name="role:update"> 
 	<a class="layui-btn  layui-btn-xs" lay-event="editRole">修改角色</a>
</shiro:hasPermission>
</script>

<a href="javascript:;" data-url="/flowView/byTime" style="display:none;" id="editPermission"><cite>编辑权限</cite></a>
</body>
    <script type="text/javascript">
    layui.use(['jquery','form', 'layedit', 'table'], function(){
          var form = layui.form
          ,layer = layui.layer
          ,layedit = layui.layedit;         
          var table = layui.table;
          table.render({
            id: 'roleTable',
            elem: '#table'
            ,even: true
            ,url:'/role/list'
            ,cols: [[
              {field:'id', title: '编号',width:80}
              ,{field:'name',  title: '名称',width:200}
              ,{fixed: 'right',title: '操作', width: 165, align:'center', toolbar: '#barAction',width:300}
            ]]
          });         
          var $ = layui.$;
          $("#add").click(function(){
        	  layer.open({
        		  title: '增加'
        		  ,content: $("#addForm").html()
        		  ,yes: function(index, layero){
        			  $.post("${contextPath}/role/save",{id:$(layero).find("#id").val(),name:$(layero).find("#name").val()},function(res){
        				  if(res.flat){
        					  layer.close(index);
        					  layer.msg(res.hint,{time:2000})        					 
        					   table.reload('roleTable',{
        		              }); 
        				  }else{
        					  layer.msg(res.hint,{time:2000})
        				  }
        			  });       			        	         
        	       }
        		});
          });         
        //监听工具条
          table.on('tool(action)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
              layer.confirm('确定要删除吗？', function(index){
            	  $.post("${contextPath}/role/del",{id:data.id},function(res){
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
            } else if(obj.event === 'editPermission'){
            	layer.open({
        		  type: 2,
        		  title: false,
        		  area: ['630px', '360px'],
        		  closeBtn: 0,
        		  shadeClose: true,
        		  content: '${contextPath}/role/permissions/page?id=' + data.id
        		});
            }
            else if(obj.event === 'editMenu'){
            	layer.open({
          		  type: 2,
          		  title: "编辑菜单",
          		  area: ['330px', '560px'],
          		  closeBtn: 0,
          		  shadeClose: true,
          		  content: '${contextPath}/role/menu?id=' + data.id,
          		  btn:['提交','取消'],
        	        yes: function(index, layero){
        	        	var datas = $(layero).find("iframe")[0].contentWindow.subMenu();        	        	
        	        	//debugger;
        	    		 if(datas){
        	            	$.ajax({
        	            		url: '${contextPath}/role/roleMenu/change',
        	            		data: {roleId:data.id,menuIds:datas},
        	            		type:'POST',
        	            		success: function(rec){ 
        	            			if(rec.success){
        	            				layer.closeAll();
        	            				layer.msg(rec.msg,{time:3000});
        	            				
        	            				//location.reload(true);
        	            			}else{
        	            				layer.alert(rec.msg);
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
            else if(obj.event === 'editRole'){
            	//alert("dfgb")
            	layer.open({
            	shadeClose: false, //点击遮罩关闭
        		  type: 1,
        		  title: false,
        		  area: ['300px', '150px'],
        		  skin:'layui-layer-rim',
    			  btn: ['确定', '取消', ],
    			  yes: function(index, layero){ 
    				  var name = $("#chen3").val();				     	
    				  $.ajax({
    				            url:'/role/updateRole',
    				            type:'post',						
    				           data:{id:data.id,name:name
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
        		  '<div class="layui-form-item"><label class="layui-form-label">角色名称</label><div class="layui-input-block"><input type="text" id="chen3" name="name" value="'+data.name+'"  class="layui-input" ></div></div>'
        		});            	
            }                                   
          });          
    });
    
    
    </script>
</html>