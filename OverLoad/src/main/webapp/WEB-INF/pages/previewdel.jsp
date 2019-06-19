<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>预检明细表</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

	<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
	<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>
	<%-- <script type="text/javascript" charset="utf-8" src="${contextPath}/js/layer/layer.js"></script> --%>

	<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />
</head>

<body class="childrenBody">
	<div class="admin-main">

		<blockquote class="layui-elem-quote">
			<!-- 查询按钮 -->
			<div class="demoTable">
				<div class="layui-inline">
			<!-- 开始时间 -->
            <div class="layui-inline" style="margin-left:115px;float:left;">
              <label class="layui-form-label">开始时间：</label>
                 <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="test5" placeholder="yyyy-MM-dd HH:mm:ss">
                 </div>
            </div>
           <!-- 结束时间 -->
            <div class="layui-inline" style="margin-left:30px;float:right;">
                <label class="layui-form-label">结束时间：</label>
                   <div class="layui-input-inline">
                       <input type="text" class="layui-input" id="test5-1" placeholder="yyyy-MM-dd HH:mm:ss">
                   </div>
            </div>
				</div>

				<div class="layui-form-item" style="width:300px;height:35px;margin-left:800px;margin-top:-38px;">
                   <div class="layui-input-inline">
                       <label class="layui-form-labe">站点:</label>
                              <select name="station" style="width:80%;height:30px">
                                  <option value="">治超站</option>
                                  <option value="浙江" selected="">浙江省</option>
                                  <option value="你的工号">江西省</option>
                                  <option value="你最喜欢的老师">福建省</option>
                             </select>
                 </div>
               </div>
               
				<div class="layui-inline" style="width:300px;height:35px;margin-left:1000px;margin-top:-100px;">
                     <label class="layui-form-label">车道：</label>
                           <div class="layui-input-inline" style="width:60%;height:30px">
                              <input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>
                
				<div class="layui-inline"  style="width:300px;height:35px;margin-left:-1190px;">
                     <label class="layui-form-label">车牌含：</label>
                           <div class="layui-input-inline">
                              <input type="tel" name="carNum" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>
                
                <div class="layui-form-item"  style="width:300px;height:35px;margin-left:439px;margin-top:-20px;">
					<label class="layui-form-label">车牌识别：</label>
					<select name="carNumrecog" style="width:61%;height:30px">
						<option value="1">已识别</option>
						<option value="0">未识别</option>
					</select>
				</div>

				<div class="layui-input-inline" style="width:300px;height:35px;margin-left:730px;margin-top:-100px;">
					<label class="layui-form-label">重量:</label>
					<select name="quiz1" style="width:50%;height:30px">
						<option value="0">超限大于10%</option>
						<option value="1" selected="">超限大于20%</option>
						<option value="2" selected="">超限大于30%</option>
						<option value="3">超限大于40%</option>
						<option value="4">超限大于50%</option>
						<option value="5">超限大于60%</option>
						<option value="6">超限大于70%</option>
						<option value="7">超限大于80%</option>
					</select>
				</div>

				<div class="layui-inline" style="width:300px;height:35px;margin-left:1000px;margin-top:-165px;">
                     <label class="layui-form-label">轴数：</label>
                           <div class="layui-input-inline" style="width:60%;height:30px">
                              <input type="tel" name="axleCnt" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>

                <div class="layui-inline" style="width:300px;height:35px;margin-left:-1190px;margin-top:-30px;">
                     <label class="layui-form-label">起始吨位：</label>
                           <div class="layui-input-inline">
                              <input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>
                
                <div class="layui-inline" style="width:300px;height:35px;margin-left:19px;margin-top:-30px;">
                     <label class="layui-form-label">截止吨位：</label>
                           <div class="layui-input-inline">
                              <input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">
                           </div>
                </div>

				<div class="layui-inline" style="width:300px;height:35px;margin-left:60px;margin-top:-30px;">
				     <button id="search" name="search" class="layui-btn  layui-btn-normal" data-type="reload" style="width:80px;height:35px">查询</button>
				     <button type="reset" class="layui-btn  layui-btn-normal" style="width:80px;height:35px">导出</button>
				</div>
			</div>
	</div>
	</blockquote>
	<fieldset class="layui-elem-field">
		<legend>预检明细表</legend>
		<div class="layui-field-box layui-form">
			<table class="layui-hide layui-table" id="LAY_table_user" lay-filter="user"></table>
		</div>
	</fieldset>
	</div>
	<script>
		layui.config({
			base: '${contextPath }/layui/'
		});
		layui.use(['form', 'laypage','layedit', 'layer', 'table'], function() {
			var $ = layui.jquery,
				table = layui.table,
				form = layui.form,
				laypage = layui.laypage,
				layer = layui.layer;

			table.set({
				elem: '#LAY_table_user',
				id: 'idTest',
				cols: [
					[{
						checkbox: true,
						/* 	fixed: true */
					}, {
						field: 'id',
						title: '序号',
						width: 120,
						sort: true,
						/* 	fixed: true */
					}, {
						field: 'previewId',
						title: '预检序号',
						width: 260
					}, {
						field: 'lane',
						title: '车道',
						width: 140
					}, {
						field: 'carNum',
						title: '车牌',
						width: 240
					}, {
						field: 'speed',
						title: '轴数',
						width: 200
					}, {
						field: 'speed',
						title: '时间',
						width: 200,
						sort: true
					},{
						field: 'speed',
						title: '车速km/h',
						width: 200
					},{
						field: 'sumWeight',
						title: '总重(kg)',
						width: 180
					},{
						field: 'overload',
						title: '超限(kg)',
						width: 200
					}]
				],
				page: true,
				width: '1600',
				height: 'full',
				limits: [5, 10, 20, 30, 40],
				limit: 10,
				skin: 'row',
				//even: true,
				loading: true
			});


			//方法级渲染
			//全部查询信息
			table.render({
				elem: '#LAY_table_user',
				url: '${contextPath}/PreviewList/previewList',
				where: {}, //如果无需传递额外参数，可不加该参数
				method: 'get',
				request: {
					pageName: 'pageNum',
					limitName: 'pageSize'
				}, //如果无需自定义请求参数，可不加该参数
				response: {
					statusName: 'code', //数据状态的字段名称，默认：code
					statusCode: 200, //成功的状态码，默认：0
					msgName: 'msg', //状态信息的字段名称，默认：msg
					countName: 'count', //数据总数的字段名称，默认：count
					dataName: 'data', //数据列表的字段名称，默认：data
				}, //如果无需自定义数据响应名称，可不加该参数
				done: function(res, curr, count) {
					//如果是异步请求数据方式，res即为你接口返回的信息。
					//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
					console.log(res);
					//得到当前页码
					console.log(curr);
					//得到数据总量
					console.log(count);
				}
			});

			

			//搜索
			$('#search').on('click', function() {
				table.reload('idTest', {
					elem: '#LAY_table_user',
					url: '${contextPath}/PreviewList/previewList',
					where: { //设定异步数据接口的额外参数，任意设
						id: $('#start_time').val(),
					},
					method: 'get',
					request: {
						pageName: 'pageNum',
						limitName: 'pageSize'
					}, //如果无需自定义请求参数，可不加该参数
					response: {
						statusName: 'code', //数据状态的字段名称，默认：code
						statusCode: 200, //成功的状态码，默认：0
						msgName: 'msg', //状态信息的字段名称，默认：msg
						countName: 'count', //数据总数的字段名称，默认：count
						dataName: 'data', //数据列表的字段名称，默认：data
					}, //如果无需自定义数据响应名称，可不加该参数
					done: function(res, curr, count) {
						//如果是异步请求数据方式，res即为你接口返回的信息。
						//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
						console.log(res);
						//得到当前页码
						console.log(curr);
						//得到数据总量
						console.log(count);
					}
				});
				$('#lane').val("");
			});

			//监听表格复选框选择
			table.on('checkbox(user)', function(obj) {
				console.log(obj)
			});
		});
	</script>
	<script>
layui.use('laydate', function(){
  var laydate = layui.laydate;
  
//时间选择器
  laydate.render({
    elem: '#test5'
    ,type: 'datetime'
  });
  laydate.render({
	    elem: '#test5-1'
	    ,type: 'datetime'
	  });
});
</script>
</body>

</html>