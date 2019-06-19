<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
	<meta charset="utf-8">
	<title>首页图表--非现场执法治超检测平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=2, maximum-scale=1">
	<!-- <meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	
	<link rel="stylesheet" href="../layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="../css/main.css" media="all" /> -->
	
	<!-- <script type="text/javascript" src="../layui/layui.js"></script> -->
	<!-- <script type="text/javascript" src="../js/main.js"></script> -->
	<!-- 引入 echarts.js -->
    
    <link rel="stylesheet" href="${contextPath }/css/global.css" media="all" />
    <script src="${contextPath }/js/jquery-1.11.1.min.js"></script>
    <script src="${contextPath }/login/echarts.js"></script>
    
    <style type="text/css">
        .flex-row{
            justify-content: space-around;
        }
        .flex-cell{
            height: 280px;
            border: 1px solid #ccc;
            margin: 30px;
            padding:30px;
            min-width: 550px;
        }
    </style>
</head>
<body>

<div class="flex-row">
<!-- 吨位分类 -->
<div id="main" class="flex-cell"></div>
<!-- 超限率分类 -->
<div id="main1" class="flex-cell"></div>
</div>
<div class="flex-row">
<!-- 车辆识别 -->
<div id="main2" class="flex-cell"></div>
<!-- 4条预检统计 -->
<div id="main3" class="flex-cell"></div>
</div>
 <script type="text/javascript">
 
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        
        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '吨位统计分析',
                subtext: '${curr}',
                x:'center'
            },
            textStyle:{
                fontSize:14
            },
            tooltip: {}
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.showLoading();
        $.get("${contextPath }/main/calc/byWeight",{},function(res){
        	myChart.hideLoading();
        	var data = [],rangs = [],ton={};
        	if(res && res.success){
        		$.each(res.tonRanges,function(i,item){
        			if(item.tonRegion != '合计'){
            			rangs.push(item.tonRegion);
            			ton[item.tonId] = i;
            			data.push(0);
        			}
        		});
        		$.each(res.list,function(index,item){
        			data[ton[item.ton]] = item.tonNum;
        		});
        	}
            myChart.setOption({
                xAxis: {
                    data: ["0-10","10-20","20-30","30-40","40-50","50-60","60-70","70-80","80-90","90-100","其他"],
                    name: '吨'
                },
                yAxis: {
                    type : 'value',
                    name: '数量（辆）'
                },
                series: [{
                    type: 'bar',
                    data: data
                }]
            });
            
        });
        
    var myChart1 = echarts.init(document.getElementById('main1'));
    option = {
    	    title : {
    	        text: '超限率统计分析',
                subtext: '${curr}',
                x: 'center'
    	    },
            textStyle:{
                fontSize:14
            },
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data: ['5%以下','5%-30%','30%-50%','50%-100%','>100%']
    	    
    	    }
    	};
    myChart1.setOption(option);
    myChart1.showLoading();
    
    $.get("${contextPath }/main/calc/byOverPercent",{},function(res){
    	myChart1.hideLoading();
    	var data = [];
    	if(res && res.success){
    		$.each(res.list,function(i,item){
    			data.push({name: item.name,value:item.num})
    		});
    	}
    	myChart1.setOption({
            series : [
                {
                    name: '超限统计',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        });
    });
    </script>
     
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('main2'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '车牌识别率分析',
                subtext: '${curr}',
                x: 'center',
                y:'down'
            },
            textStyle:{
                fontSize:14
            },
            tooltip: {},
            legend: {
                orient: 'vertical',
                right: 'right',
                data: ['白天识别率','夜晚识别率']
            }
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
        
        myChart2.showLoading();
        $.get("${contextPath }/main/calc/byRecognitionRate",{},function(res){
        	myChart2.hideLoading();
        	var data1=[],data2=[],lanes=["1","2","3","4","5","6","7"];
        	$.each(lanes,function(i,item){
        		data1[i] = 0;
        		data2[i] = 0;
        	});
        	
        	if(res && res.success){
        		$.each(res.list,function(i,item){
        			data1[item.lane-1] = item.d_rate*100;
        			data2[item.lane-1] = item.n_rate*100;
        		});
        	}
        	myChart2.setOption({
                xAxis: {
                    data: lanes,
                    name: '车道'
                },
                yAxis: {
                    type : 'value',
                    name: '识别率'
                },
                series: [{
                    name: '白天识别率',
                    type: 'bar',
                    data: data1
                },
                {
                    name: '夜晚识别率',
                    type: 'bar',
                    data: data2
                }]
            });
        });
    </script>
    
 <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart3 = echarts.init(document.getElementById('main3'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '预检统计分析',
                subtext: '${curr}',
                x: 'center'
            },
            textStyle:{
                fontSize:14
            },
            tooltip: {},
            legend: {
                data:['吨']
            }
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart3.setOption(option);
        
        myChart3.showLoading();
        $.get("${contextPath }/main/calc/byHour",{},function(res){
        	myChart3.hideLoading();
        	var hours = ["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"],
        	values =[];
        	$.each(hours,function(i,item){
        		values.push(0);
        	});
        	if(res && res.success){
        		$.each(res.list,function(i,item){
        			values[item.hour] = item.num;
        		});
        	}
        	myChart3.setOption({
                xAxis: {
                    data: hours,
                    name: '时'
                },
                yAxis: {
                    type : 'value',
                    name: '数量（辆）'
                },
                series: [{
                    name: '数量(辆)',
                    type: 'bar',
                    data: values
                }]
            });
        });
    </script>

</body>
</html>
