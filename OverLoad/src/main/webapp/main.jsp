<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
	<meta charset="utf-8">
	<title>首页图表--非现场执法治超检测平台</title>
	
    <script src="${contextPath }/login/echarts.js"></script>
</head>
<body>
<div style="width:100%;height:80%;">
<!-- 吨位分类 -->
 <div id="main" style="width:45%;height:400px;float:left;margin-left:35px;margin-top:30px;"></div>
 <script type="text/javascript">
 
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        
        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '吨位统计分析',
                x:'center'
            },
            tooltip: {},
            legend: {
                data:['吨']
            },
            xAxis: {
                data: ["0-10","10-20","20-30","30-40","40-50","50-60","60-70","70-80","80-90","90-100","其他"]
            },
            yAxis: {
            	max:'200',
            	type : 'value',
            },
            series: [{
                name: '数量(辆)',
                type: 'bar',
                data: [0, 20, 36, 10, 10, 20, 20, 20, 20, 20, 20]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
    
    <!-- 超限率分类 -->
    <div id="main1" style="width:45%;height:400px;float:right;margin-right:35px;margin-top:30px;"></div>
    <script type="text/javascript">
    var myChart1 = echarts.init(document.getElementById('main1'));
    option = {
    	    title : {
    	        text: '超限率统计分析',
                x: 'center'
    	    },
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
    	    },
    	    series : [
    	        {
    	            name: '访问来源',
    	            type: 'pie',
    	            radius : '55%',
    	            center: ['50%', '60%'],
    	            data:[
    	                {value:335, name:'直接访问'},
    	                {value:310, name:'邮件营销'},
    	                {value:234, name:'联盟广告'},
    	                {value:135, name:'视频广告'},
    	                {value:1548, name:'搜索引擎'}
    	            ],
    	            itemStyle: {
    	                emphasis: {
    	                    shadowBlur: 10,
    	                    shadowOffsetX: 0,
    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    	                }
    	            }
    	        }
    	    ]
    	};
    myChart1.setOption(option);
    </script>
     
     <!-- 车辆识别 -->
     <div id="main2" style="width:45%;height:400px;float:left;margin-left:35px;margin-top:30px"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('main2'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '车牌识别率分析',
                x: 'center',
                y:'down'
            },
            tooltip: {},
            legend: {
                data:['吨']
            },
            xAxis: {
                data: ["1","2","3","4","5","6"]
            },
            yAxis: {
            	type : 'value'
            },
            series: [{
                name: '数量(辆)',
                type: 'bar',
                data: [0, 20, 36, 10, 10, 20]
            },
            {
                name: '数量(辆)',
                type: 'bar',
                data: [0, 20, 36, 10, 10, 20]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
    </script>
    
    <!-- 4条预检统计 -->
         <div id="main3" style="width:45%;height:400px;float:right;margin-right:35px;margin-top:30px"></div>
 <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart3 = echarts.init(document.getElementById('main3'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '预检统计分析',
                x: 'center'
            },
            tooltip: {},
            legend: {
                data:['吨']
            },
            xAxis: {
                data: ["1","2","3","4","5","6"]
            },
            yAxis: {
            	type : 'value'
            },
            series: [{
                name: '数量(辆)',
                type: 'bar',
                data: [0, 20, 36, 10, 10, 20]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart3.setOption(option);
    </script>
    </div>
</body>
</html>
