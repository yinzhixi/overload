<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
	<meta charset="utf-8">
	<title>主页--非现场执法治超检测平台</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=0.75, maximum-scale=1">
		
	<link rel="icon" href="favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" media="all" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" media="all" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftNav.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
</head>
<body class="main_body" style="overflow: hidden;">
	<div class="layui-layout layui-layout-admin">
		<!-- 顶部 -->
		<div class="layui-header header" style="background-size:100% 100%;background-image: url('${pageContext.request.contextPath}/images/page-img2.png'); margin-right: 0; ">
			<%-- <div class="layui-main">
 			 <img src="${pageContext.request.contextPath}/images/page1-img1.png" alt="">	
			</div> --%>
		</div>
		
		<!-- 左侧导航 -->
		<div class="layui-side layui-bg-black"style="overflow: hidden;padding-top: 33px;">
		
			<div class="navBar layui-side-scroll"></div>
		</div>
		<!-- 右侧内容 -->
		<div class="layui-body layui-form">
			<div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
				<ul class="layui-tab-title top_tab" id="top_tabs">
					<li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>首页</cite></li>
				</ul>
				<ul class="layui-nav closeBox">
				
				
				 <li class="layui-nav-item">
				    <a href="javascript:;"><i class="iconfont icon-caozuo"></i> 页面操作</a>
				    <dl class="layui-nav-child">
					  <dd><a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i> 刷新当前</a></dd>
				      <dd><a href="javascript:;" class="closePageOther"><i class="iconfont icon-prohibit"></i> 关闭其他</a></dd>
				      <dd><a href="javascript:;" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a></dd>
				        <dd><a href="${pageContext.request.contextPath}/emp/logout"><i class="layui-icon"></i>退出</a></dd>
				    </dl>
				  </li> 
				  
				  
				</ul>
				<div class="layui-tab-content clildFrame">

					<div class="layui-tab-item layui-show">
						<iframe src="${pageContext.request.contextPath}/main"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-footer footer">
		
		<p>版权所有 &copy;2018 今迈衡器 &nbsp &nbsp &nbsp 服务电话：0371-65751528 &nbsp &nbsp 服务手机：13838261976 &nbsp &nbsp地址：郑州市花园路27号河南省科技厅主楼13层，&nbsp 邮政编码：450000 &nbsp<a href=http://www.hnjinmai.com/>联系我们</a> &nbsp &nbsp 豫ICP备17013954号</p>
		
		</div>
		<!-- 底部 -->
		<!-- <div class="layui-footer footer">
		
			<p>copyright @2018</p>
		</div> -->

	</div>
	<!-- 移动导航 -->
	<div class="site-tree-mobile layui-hide"><i class="layui-icon">&#xe602;</i></div>
	<div class="site-mobile-shade"></div>
</body>
</html>
