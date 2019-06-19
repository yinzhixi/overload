<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="staticPath" value="/static/uploads/"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>预检实时数据表</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	
		<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
	
	 <script type="text/javascript" src="<%=path%>/layui/layui.js"></script>
</head>

<title>预检实时数据列表</title>

<body >
	
   <form class="layui-form" action="" style="padding: 20px 10px;">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始日期</label>
      <div class="layui-input-inline">
        <input type="text" name="startDate" id="startDate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">结束日期</label>
      <div class="layui-input-inline">
        <input type="text" name="endDate" id="endDate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
      </div>
    </div>
    
    <%-- <div class="layui-inline">
    <label class="layui-form-label">站&nbsp;&nbsp;点</label>
	    <div class="layui-input-inline">
	      <select name="station" id="station">
	        <option value=""></option>
	        <c:forEach items="${stations }" var="station">
	           <option value="${station.stationName }">${station.stationName }</option>
	        </c:forEach>
	      </select>
	    </div>
    </div> --%>
    
   				 <div class="layui-inline">
                     <label class="layui-form-label">设备名称：</label>
                           <div class="layui-input-inline" >
                              <input type="text" id="name" name="name" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>
  
    <div class="layui-inline">
      <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-normal" id="query">查询</button>
      <button type="button" class="layui-btn layui-btn-normal" id="export">导出</button>
      </div>
    </div>
    
    </div>
  </form>
			
		<fieldset class="layui-elem-field">
		<legend>设备明细表</legend>
		<div class="layui-field-box layui-form">
			<table class="layui-hide layui-table" id="table" lay-filter="demo"></table>
		</div>
	</fieldset>	
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" layui-btn-danger lay-event="editNodeType"><cite>修改</cite></a>

  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delNodeType">删除</a>

  <a class="layui-btn layui-btn-xs" layui-btn-danger lay-event="addNodeType"><cite>添加</cite></a>
</script>

	<script type="text/javascript">
	   layui.use(['jquery','form', 'layedit', 'laydate','table'], function(){
  	  var form = layui.form
  	  ,layer = layui.layer
  	  ,layedit = layui.layedit
  	  ,laydate = layui.laydate; 
  	  
  	  var table = layui.table;
  	  
  	//日期
	  laydate.render({
	    elem: '#startDate',
	  });
	  laydate.render({
	    elem: '#endDate',
	  });
  	 //监听工具条
  	  table.on('tool(demo)', function(obj){
  	    var data = obj.data;
  		  console.log(data)
  	    if(obj.event === 'addNodeType'){
  	    	
  	    	layer.open({
		    	shadeClose: true, //点击遮罩关闭
				  type: 1,
				  title: "添加设备类型",
				  area: ['800px', '400px'],
				  skin:'layui-layer-rim',
				  btn: ['确定', '取消', ],
				  yes: function(index, layero){
					  //alert("dghndghn")
					  var nId = $("#chao1").val();
					  var name = $("#chao2").val();
					  var pinyin = $("#chao3").val();
					  var desp = $("#chao4").val();
					  var version = $("#chao5").val();
					 					 
					  $.ajax({
					            url:'/type/addNodeType',
					            type:'post',						
					           data:{nId:nId,name:name,
					        	   pinyin:pinyin,desp:desp,version:version}, 					           					         
					            dataType:'json',
				
					            success:function(result){
					            	if(result.flat){
					            		layer.closeAll();			            				
			            				//window.location.reload();
			            				
			            				layer.msg(result.hint, {time:3000});
					            	}else{
					            		layer.msg("网络错误");
					            	}
					            	
		            				
					            /* 	layer.msg("添加成功",{time:3000});						            	
					            	var index =layer.getFrameIndex(window.name); //获取窗口索引
					            	layui.layer.close(index);
					            	window.location.reload(); */
					            						            							           	
						       /*  $.each(result,function(index,elel){
							    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>")
						 }) */
					 }
				})
				  },
				content:'<div class="layui-form-item"><label class="layui-form-label">设备型号</label><div class="layui-input-block"><input type="text" id="chao1"    class="layui-input" ></div></div>'
				+'<div class="layui-form-item"><label class="layui-form-label">设备名称</label><div class="layui-input-block"><input type="text" id="chao2"  class="layui-input" ></div></div>'
				+'<div class="layui-form-item"><label class="layui-form-label">英文名称</label><div class="layui-input-block"><input type="text" id="chao3"  class="layui-input" ></div></div>'
				+'<div class="layui-form-item"><label class="layui-form-label">设备描述</label><div class="layui-input-block"><input type="text" id="chao4"  class="layui-input" ></div></div>'
				+'<div class="layui-form-item"><label class="layui-form-label">设备版本</label><div class="layui-input-block"><input type="text" id="chao5"  class="layui-input" ></div></div>'
		    	});
  	    }
  	  else if(obj.event === 'editNodeType'){
	    	layer.open({
	    	shadeClose: false, //点击遮罩关闭
			  type: 1,
			  title: false,
			  area: ['800px', '400px'],
			  skin:'layui-layer-rim',
			  btn: ['确定', '取消', ],
			  yes: function(index, layero){				
					  var name = $("#chao2").val();
					  var pinyin = $("#chao3").val();
					  var desp = $("#chao4").val();
					  var version = $("#chao5").val();
				 
				  $.ajax({
				            url:'/type/editNodeType',
				            type:'post',						
				            data:{nId:data.nId,name:name,
					        	   pinyin:pinyin,desp:desp,version:version}, 
				            dataType:'json',
				            success:function(result){						            	
				            	if(result.flat){
				            		layer.closeAll();			            				
		            				window.location.reload();
		            				
		            				layer.msg("修改成功", {time:3000});
				            	}else{
				            		layer.msg("网络错误");
				            	}
				            						            							           	
					       /*  $.each(result,function(index,elel){
						    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>")
					 }) */
				 }
			})
			  },/* bit2:function(index){
				  layer.close(index);
			  }, */
			  cancel:function (index,layero) {
		            
		         },
			 
			 // closeBtn: 0,
			 // shadeClose: true,
			content:
			'<div class="layui-form-item"><label class="layui-form-label">设备名称</label><div class="layui-input-block"><input type="text" id="chao2"  value="'+data.name+'"  class="layui-input" ></div></div>'
			+'<div class="layui-form-item"><label class="layui-form-label">英文名称</label><div class="layui-input-block"><input type="text" id="chao3"  value="'+data.pinyin+'"  class="layui-input" ></div></div>'
			+'<div class="layui-form-item"><label class="layui-form-label">设备描述</label><div class="layui-input-block"><input type="text" id="chao4"  value="'+data.desp+'"  class="layui-input" ></div></div>'
			+'<div class="layui-form-item"><label class="layui-form-label">设备版本</label><div class="layui-input-block"><input type="text" id="chao5"  value="'+data.version+'"  class="layui-input" ></div></div>'		
			+'<div class="layui-form-item"><div class="layui-input-block"><input type="hidden"  value="'+data.nId+'"  id="lane6" name="previewId" class="layui-input" ></div></div>'
	    	
	    	});
	    		  
	    	
	    }
  		  
  	  else if(obj.event === 'delNodeType'){
	      layer.confirm('确定删除吗？', function(index){
	    	  $.ajax({
		            url:'/type/delNodeType',
		            type:'post',
		            async: false,
		            data:{nId:data.nId}, 
		            dataType:'json',
		            success:function(result){
		            	window.location.reload();
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
  	  });
  	  table.render({
  		id: 'calc',
  	    elem: '#table'
  	    ,url:'/type/queryAll'
  	    ,cols: [[
  	   
  	       {field:'nId', title: '设备型号',width:250}
  	       ,{field:'name',  title: '设备名称',width:150}
  	      ,{field:'pinyin', title: '英文名称',width:150}
  	      ,{field:'desp', title: '设备描述',width:200}  	      
  	      ,{field:'version', title: '设备版本',width:120}
  	    ,{field:'createTime', title: '创建时间',width:200}
  	  ,{field:'updateTime', title: '更改时间',width:200}
	      ,{field:'createUser',  title: '创建者',width:80} 
	      ,{fixed: 'right',	title: '操作', width:300, align:'center', toolbar: '#barDemo'}
  	    ]]
  	    ,page: true
  	    ,limit:10,
  	  });
  	  
  	 var $ = layui.$;
     $("#query").click(function(){
    	// alert("dfb")
          table.reload('calc',{
       	  where:{
                 startDate: $('#startDate').val(),
                 endDate: $('#endDate').val(),
                 name: $('#name').val()
             }
         }); 
     });
     $("#export").click(function(){
 	    window.location = "${contextPath}/type/export?startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&name="+$("#name").val();
   });
  	    	  
  });
	
	</script> 

</body>
</html>