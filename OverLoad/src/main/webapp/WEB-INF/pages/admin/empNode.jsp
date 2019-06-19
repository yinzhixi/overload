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
         <label class="layui-form-label">设备名称：</label>
               <div class="layui-input-inline" >
                  <input type="text" id="sss"  lay-verify="required|phone" autocomplete="off" class="layui-input">
               </div>
    </div>
    <div class="layui-inline">
      <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-normal" id="query">查询</button>
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

  	  });
  	  table.render({
  		id: 'calc'
	  ,even: true
	  ,skin: 'row'
  	   ,elem: '#table'
  	    ,url:'/node/queryAllByUser?createUser=${userId}'
  	    ,cols: [[
          {type:'checkbox'}
          ,{field:'id', title: '设备型号',width:200}
  	      ,{field:'nodeName', title: '设备名称',width:200}
  	      ,{field:'pinyin', title: '英文名称',width:150}
  	      ,{field:'online', title: '是否在线',width:120}
	      ,{field:'createUser',  title: '创建者',width:80} 
	      ,{field:'name',  title: '所属类型',width:150}

  	    ]]
		 ,done: function(res, page, count){
			  //如果是异步请求数据方式，res即为你接口返回的信息。
			  //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
              //下面三句是通过更改css来实现选中的效果
			  for(var i=0;res.data.length;i++){
			      if(res.data[i].tag==1){
                      var index= res.data[i]['LAY_TABLE_INDEX'];
                      $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                      $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
				  }
			  }
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
                       nodeName: $('#sss').val()
                   }
               });
           });

       var $ = layui.$;
       table.on('checkbox(demo)', function(obj){
           $.post("${contextPath}/emp/user/changeNode",
               {userId: '${userId}',nodeId:obj.data.id,isAssigned: obj.checked,type:obj.type},
               function(res){
                   if(res.success){
                       layer.msg('正在提交请稍候。。。', {icon: 16,time: 600,shade : [0.5 , '#000' , true]},function(){
                           layer.msg("操作成功")
                       });
                   }else{
                       layer.alert(res.msg);
                   }
               });

           /* console.log(obj.checked); //当前是否选中状态
           console.log(obj.data); //选中行的相关数据
           console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one */
       });
  	    	  
  });
	
	</script> 

</body>
</html>