<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
	
	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />
	<title>员工展示</title>
	


</head>
<body>
			<div class="demoTable">
					<div class="layui-inline" style="width:450px;height:15px;margin-top:10px;">
                     <label class="layui-form-label">工号：</label>
                           <div class="layui-input-inline" style="width:60%">
                              <input type="tel" id="jobNum" name="jobNum" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>


				<div style="width:500px;height:15px;margin-top:-10px;margin-left:480px;">
                     <label class="layui-form-label">名称：</label>
                           <div class="layui-input-inline" style="width:240px;height:10px;">
                              <input type="tel" id="empName" name="empName" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>
                
                <div class="layui-form-item"  style="width:450px;height:15px;margin-top:50px;">
					<label class="layui-form-label">在用：</label>
					<select name="isEmp" id="isEmp" style="width:61%;height:30px">
					<option value="1">请选择是否在用</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>


                   <div style="width:300px;height:15px;margin-top:-30px;margin-left:535px;">
                       <label class="layui-form-labe">站名：</label>
                              <select id="stationName" name="stationName" style="width:80%;height:30px;margin-left:10px;">
                                 <option value="">请选择站点</option>      
                              <c:forEach var="ite" items="${lis}">
                                  <option value="${ite.stationName}">${ite.stationName}</option>
                              </c:forEach>
                             </select>
                 </div>

				<div class="layui-inline" style="width:300px;height:35px;margin-left:800px;margin-top:-50px;">
				     <button id="search" name="search" class="layui-btn  layui-btn-normal" data-type="reload" style="width:100px;height:30px;margin-top:35px;">查询</button>
				</div>
			</div>
    <div class="layui-tab-item">
      <div id="pageDemo"></div>
    </div>
<div style="margin-top:40px;">
<table class="layui-hide" id="table" lay-filter="demo"></table>
</div>	

	
<script type="text/html" id="barDemo1">
  		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail1">查看</a>
</script>
	


<script type="text/html" id="barDemo">
    <shiro:hasPermission name="user:update">
      <a class="layui-btn layui-btn-xs" lay-event="edit"><cite>修改</cite></a>
    </shiro:hasPermission>


    <shiro:hasPermission name="user:del">
      <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </shiro:hasPermission>

    <shiro:hasPermission name="user:editRole">
      <a class="layui-btn  layui-btn-xs" lay-event="editUser">编辑角色</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:node">
        <a class="layui-btn  layui-btn-xs" lay-event="userNode">设备权限</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:station">
        <a class="layui-btn  layui-btn-xs" lay-event="userStation">站点权限</a>
    </shiro:hasPermission>
</script>
<script>
layui.use('table', function(){
	//返回整个表格
  var table = layui.table//表格
  ,laypage = layui.laypage; //分页
  //执行一个table实例
  table.render({
  	 id: 'calc'
    ,elem: '#table'
    ,url:'/emp/queryE' //数据接口
    ,page: true    //开启分页  
    ,cols: [[
      {field:'id', title: 'ID', width:80}
      ,{field:'jobNum',  title: '工号' , width:150}
      ,{field:'empName', title: '姓名',width:150}
      ,{field:'isEmp', title: '在用',width:80}
      ,{field:'stationName', title: '站名', width:150}
      ,{field:'', title: '拥有角色', width:150,toolbar: '#barDemo1'} 
      ,{fixed: 'right', title: '操作',width: 165, align:'center', toolbar: '#barDemo', width:400}
    ]]
  
  ,done: function(res, page, count){  
      //如果是异步请求数据方式，res即为你接口返回的信息。  
      //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度  
        
      //分类显示中文名称  
      $("[data-field='isEmp']").children().each(function(){  
              if($(this).text()=='1'){  
                 $(this).text("是")  
              }else if($(this).text()=='0'){  
                 $(this).text("否")  
              }
      })  
}         

  });

  var $ = layui.$;
  $("#search").click(function(){
      table.reload('calc',{
    	  where:{
              jobNum: $('#jobNum').val(),
              empName: $('#empName').val(),
              isEmp: $('#isEmp').val(),
              stationName: $('#stationName').val()
          }
      });
  });
  
//监听工具条事件
  table.on('tool(demo)', function(obj){
	 

	//obj表示行对象
	//data表示一行当中的数据
    var data = obj.data;
    if(obj.event === 'detail1'){  	    	
    	layer.open({
	    	shadeClose: true, //点击遮罩关闭
			  type: 2,
			  title: "拥有角色",
			  area: ['400px', '250px'],
			 content:'${pageContext.request.contextPath}/emp/showAllRoles?empName='+data.empName		
	    	});
    	
    }
    if(obj.event === 'detail'){
      layer.msg('ID：'+ data.id + ' 的查看操作');
    } else if(obj.event === 'del'){
    	 if(data.empName=="admin"){
			layer.msg("管理员信息不能被删除");
		}else{
			 layer.confirm('确定删除吗？', function(index){
		    	  $.ajax({
			            url:'/emp/delete',
			            type:'post',
			            async: false,
			            data:{id:data.id}, 
			            dataType:'json',
			            success:function(result){
			            	layer.msg("删除成功");			            	
				        /*  $.each(result,function(index,elel){
					    $("#data").append("<a href='delete?id="+elel.id+"'>删除</a>") 
				 }) */
			 }
		})
       obj.del();
       layer.close(index);
     });
		}   	
    }else if(obj.event === 'edit'){   
       $($(this)).attr('data-url','/emp/query?id=' + data.id);
  	   window.parent.addTab($($(this)));
  	    	   
    }else if(obj.event === 'editUser'){
    	layer.open({
		  type: 2,
		  title: false,
		  area: ['630px', '360px'],
		  closeBtn: 0,
		  shadeClose: true,
		content: '${contextPath}/emp/user/role?empName=' + data.empName
		});
    }else if(obj.event=='userNode'){
        layer.open({
            type: 2,
            title: false,
            area: ['830px', '460px'],
            closeBtn: 0,
            shadeClose: true,
            content: '${contextPath}/emp/user/node?userId=' + data.empName
        });
    }else if(obj.event=='userStation'){
        layer.open({
            type: 2,
            title: false,
            area: ['830px', '460px'],
            closeBtn: 0,
            shadeClose: true,
            content: '${contextPath}/emp/user/station?userId=' + data.id
        });
    }
    //分页
    laypage.render({
      elem: 'pageDemo' //分页容器的id
      ,count: 100 //总页数
      ,skin: '#1E9FFF' //自定义选中色值
      //,skip: true //开启跳页
      ,jump: function(obj, first){
        if(!first){
          layer.msg('第'+ obj.curr +'页');
        }
      }
    });
    
  });
  
});

</script>
</body>
</html>