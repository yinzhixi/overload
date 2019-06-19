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
	<title>设备数据表</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	
		<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
	
	 <script type="text/javascript" src="<%=path%>/layui/layui.js"></script>
</head>

<title>设备数据列表</title>

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
                              <input type="text" id="sss"  lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>
  
    <div class="layui-inline">
      <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-normal" id="query">查询</button>
      <button type="button" class="layui-btn layui-btn-normal" id="export">导出</button>
      <button type="button" class="layui-btn layui-btn-normal" id="addNode">添加</button>
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
        <shiro:hasPermission name="node:edit">
            <a class="layui-btn layui-btn-xs" lay-event="editNode"><cite>修改</cite></a>
        </shiro:hasPermission>

        <shiro:hasPermission name="node:del">
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delNode">删除</a>
        </shiro:hasPermission>

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
  		
  	    if(obj.event === 'addNode'){
  	    	
  	  	layer.open({
	    	shadeClose: false, //点击遮罩关闭
			  type: 2,
			  title: '添加设备',
			  area: ['600px', '500px'],
			  shade: 0.4,								
			 content:'${pageContext.request.contextPath}/node/getToAddNode',
			  btn: ['确认添加', '取消', ],
			  yes: function(index, layero){
				  //alert("sdfghah")
		        	var datas = $(layero).find("iframe")[0].contentWindow.formData();
		        	console.log(datas)
		    		if(datas){
		            	$.ajax({
		            		url:'${pageContext.request.contextPath}/node/addNode',
		            		data: datas,				            	
		            		type:'POST',
		            		processData : false, 
		            		contentType : false,
		            		//mimeType:"multipart/form-data",
		            		success: function(rec){
		            			//console.log(rec)
		            			//rec = JSON.parse(rec);
		            			if(rec.flat){
		            				
		            				layer.closeAll();
		            				layer.msg(rec.hint, {time:3000});
		            				//window.location.reload();
		            				
		            				
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
  	  else if(obj.event === 'editNode'){
			//	alert("bsxfb")
			  	layer.open({
			    	shadeClose: false, //点击遮罩关闭
					  type: 2,
					  title: '修改设备',
					  area: ['500px', '550px'],
					  shade: 0.4,								
					 content:'${pageContext.request.contextPath}/node/getToEditNode?id='+data.id,
					  btn: ['确认修改', '取消', ],
					  yes: function(index, layero){
						  //alert("sdfghah")
				        	var datas = $(layero).find("iframe")[0].contentWindow.formData();
				        	console.log(datas)
				    		 if(datas){
				            	$.ajax({
				            		url:'${pageContext.request.contextPath}/node/editNode',
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
  		  
  	  else if(obj.event === 'delNode'){
	      layer.confirm('确定删除吗？', function(index){
	    	  $.ajax({
		            url:'/node/delNode',
		            type:'post',
		            async: false,
		            data:{id:data.id}, 
		            dataType:'json',
		            success:function(result){
		            	if(result.flat){
		            		layer.closeAll();			            				
            				//window.location.reload();            				
            				layer.msg(result.hint, {time:3000});
		            	}else{
		            		layer.msg("网络错误");
		            	}
		            	//window.location.reload();
		            	//layer.msg("删除成功")
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
  	    ,url:'/node/queryAll'
  	    ,cols: [[
          {field:'id', title: '设备id',width:100}
          ,{field:'nodeCode', title: '设备编号',width:200}
  	     ,{field:'nodeName', title: '设备名称',width:200}
  	      ,{field:'pinyin', title: '英文名称',width:150}
  	      ,{field:'online', title: '是否在线',width:120}  	      
  	    ,{field:'lat', title: '经度',width:100}  	      
	      ,{field:'lon', title: '纬度',width:100}
  	    ,{field:'createTime', title: '创建时间',width:200}
	      ,{field:'createUser',  title: '创建者',width:80} 
	      ,{field:'name',  title: '所属类型',width:150}
	      ,{fixed: 'right',	title: '操作', width:300, align:'center', toolbar: '#barDemo'}
  	    ]]
  	 ,done: function(res, page, count){  
  	      //如果是异步请求数据方式，res即为你接口返回的信息。  
  	      //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度  
  	        
  	      //分类显示中文名称  
  	      $("[data-field='online']").children().each(function(){  
  	              if($(this).text()=='1'){  
  	                 $(this).text("是")  
  	              }else if($(this).text()=='0'){  
  	                 $(this).text("否")  
  	              }
  	      })  
  	}         
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
                 nodeName: $('#sss').val()
             }
         }); 
     });
       $("#addNode").click(function(){
           layer.open({
               shadeClose: false, //点击遮罩关闭
               type: 2,
               title: '添加设备',
               area: ['500px', '550px'],
               shade: 0.4,
               content:'${pageContext.request.contextPath}/node/getToAddNode',
               btn: ['确认添加', '取消', ],
               yes: function(index, layero){
                   //alert("sdfghah")
                   var datas = $(layero).find("iframe")[0].contentWindow.formData();
                   console.log(datas)
                   if(datas){
                       $.ajax({
                           url:'${pageContext.request.contextPath}/node/addNode',
                           data: datas,
                           type:'POST',
                           processData : false,
                           contentType : false,
                           //mimeType:"multipart/form-data",
                           success: function(rec){
                               //console.log(rec)
                               //rec = JSON.parse(rec);
                               if(rec.flat){

                                   layer.closeAll();
                                   layer.msg(rec.hint, {time:3000});
                                   //window.location.reload();


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
       });
     $("#export").click(function(){
 	    window.location = "${contextPath}/node/export?startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&nodeName="+$("#sss").val();
   });
  	    	  
  });
	
	</script> 

</body>
</html>