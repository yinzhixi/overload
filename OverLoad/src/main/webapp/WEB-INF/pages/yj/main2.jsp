<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	pageContext.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<script type="text/javascript" src="${path }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=CCG4TV7m8qmu6Y99u7mnBjOWkMzsGVYn"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" charset="utf-8" src="${path}/layui/layui.js"></script>

<style type="text/css">  
    html{height:100%}    
    body{height:100%;margin:0px;padding:0px}    
    #container{height:100%}   
    #currData li{
    	margin-bottom:5px;
    } 
</style>
<title>监测地图</title>
</head>
<body>
<div id="container"></div>
<div style="width:200px;height:110px;border:1px solid red;position: absolute;right: 30px;top: 20px;background-color:black;opacity:0.7;color:red;">
     <ul>
         <li><input type="checkbox" id="No" value="非现场执法">非现场执法</li>
         <!-- <li><input type="checkbox" id="Start" value="源头治超">源头叶超</li> -->
         <li><input type="checkbox" id="Person" value="执法人员">执法人员</li>
         <li><input type="checkbox" id="Over" value="超限站">超限站</li>
         <li><input type="checkbox" id="Car" value="执法车辆">执法车辆</li>
     </ul>
</div>
<div style="width:350px;height:50px;position: absolute;right: 70.8%;top:1%;">
     <img alt="" src="/images/dustrank.png">
</div>
<!-- 点击监测地点弹窗 -->
<div style="display: none">
	<div id ="currData" style="width: 50%;">
		<ul>
			<li style="margin-bottom:4px;"><font color="blue">监测点:&nbsp;&nbsp;</font><span id="dName" style="font-size: 14px"></span></li>
			<li style="margin-bottom:4px;"><font color="blue">地址:&nbsp;&nbsp;</font><span id="address" style="font-size: 14px"></span></li>
			<li style="margin-bottom:4px;"><font color="blue">PM2.5:&nbsp;&nbsp;</font><span id="pm2_5"></span></li>
			<li style="margin-bottom:4px;"><font color="blue">PM10:&nbsp;&nbsp;</font><span id="pm10" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">温度:&nbsp;&nbsp;</font><span id="temp" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">湿度:&nbsp;&nbsp;</font><span id="h2" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">噪声:&nbsp;&nbsp;</font><span id="noise" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">风速:&nbsp;&nbsp;</font><span id="spd" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">风向:&nbsp;&nbsp;</font><span id="wd" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">压力:&nbsp;&nbsp;</font><span id="pressure" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">光强:&nbsp;&nbsp;</font><span id="tsp" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">设备情况:&nbsp;&nbsp;</font><span id="online" style="font-size: 14px"></span></li>
			<li style="margin-bottom:2px;"><font color="blue">更新时间:&nbsp;&nbsp;</font><span id="date_" style="font-size: 14px"></span></li>
		</ul>
			<ul style="padding-left: 10px"><a href="#" onclick="history()"><font size="2">查看该站点24小时内数据变化>></font></a></ul>
	</div>
	<div id ="weightData" style="width: 50%;">
		<ul>
			<li style="margin-bottom:4px;"><font color="blue">站点名称:&nbsp;&nbsp;</font><span id="dName2" style="font-size: 14px"></span></li>
			<li style="margin-bottom:4px;"><font color="blue">地址:&nbsp;&nbsp;</font><span id="address2" style="font-size: 14px"></span></li>
			<li style="margin-bottom:4px;"><font color="blue">秤号:&nbsp;&nbsp;</font><span id="deviceNo"></span></li>
			<li style="margin-bottom:4px;"><font color="blue">型号:&nbsp;&nbsp;</font><span id="dModel" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">是否在线:&nbsp;&nbsp;</font><span id="online2" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">添加人:&nbsp;&nbsp;</font><span id="operater" ></span></li>
			<li style="margin-bottom:4px;"><font color="blue">更新时间:&nbsp;&nbsp;</font><span id="date2" ></span></li>
		</ul>
	</div>
</div>
	<input type="hidden" id="dId">
</body>

<!-- 可以在外部js文件中使用path作为项目的根路径 -->
<script>var path = '${path}'; </script>
<script type="text/javascript" src="${path }/js/Trap.js"></script>
<script type="text/javascript">
layui.use(['form', 'layer'], function () {
	 var $ = layui.jquery,form = layui.form,layer = layui.layer;
	 
	 
}); 
  var map = new BMap.Map("container");// 创建Map实例
  var point = new BMap.Point(113.68853466281665,34.785674565552088);//设置中心点坐标
  map.centerAndZoom(point,16);//地图初始化，同时设置地图展示级别
  map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
  map.addControl(new BMap.NavigationControl()); 
  map.addControl(new BMap.GeolocationControl()); 
  map.addControl(new BMap.MapTypeControl()); 
  var centerDevice;
  /* $.ajax({
      url:'/device/findMapcenter',
      type:'post',
      async: false,
      data:{}, 
      dataType:'json',
      success:function(result){
      	if(result!=null){
      		centerDevice = result.device;
		  //2s后跳转至指定中心点
		  if(centerDevice!=null){
			   window.setTimeout(function(){  
			      map.panTo(new BMap.Point(centerDevice.lnt, centerDevice.lat)); //后期修改为从数据库中取得的一个监测点的位置  
			  }, 2000); 
		  }
      	}
	}
}) */
  //showData();
  //指定时间刷新一次数据
  setInterval(function(){
	  showData();
	 }, 1000*60*2);
  
 function showData(){
  $.ajax({
		url : path + '/device/queryAllDevice',
		type : 'post',
		data:{},
		success:function(result){
			var devices = result;
			$.each(devices,function(index, device){
				if(device.lnt==null||device.lat==null||device.lnt==''||device.lat==''){
					return;
				}
				var point = new BMap.Point(device.lnt,device.lat);
  				addMarker(point,device); 
			});
		}
	});	
}
	//创建图标对象 
  function addMarker(point,device){
		//console.log(dustData);
	  	var img;
	  	if(device.type==1){
		  	if(device.dustDatas==null||device.dustDatas==""||device.dustDatas.length==0){
		  		img = "/images/77.png";
		  	}else{
			  	var PM2_5 = device.dustDatas[0].pm2_5; 
			 	if(PM2_5>0 && PM2_5<50){
			 		img = "/images/11.png";
			 	}else if(PM2_5>=50 && PM2_5<100){
			 		img = "/images/22.png";
			 	}else if(PM2_5>=100 && PM2_5<150){
			 		img = "/images/33.png";
			 	}else if(PM2_5>=150 && PM2_5<200){
			 		img = "/images/44.png";
			 	}else if(PM2_5>=200 && PM2_5<300){
			 		img = "/images/55.png";
			 	}else if(PM2_5>=300){
			 		img = "/images/66.png";
			 	}else{
			 		img = "/images/77.png";
			 	}
		  	}
		  //设备是否在线
		 	if(device.online=="否"){
		 		img = "/images/77.png";
		 	}
	  	}else if (device.type==2||device.type==3) {
			img = "/images/cheng_red.png";
			//设备是否在线
		 	if(device.online=="否"){
		 		img = "/images/cheng_hui.png";
		 	}
		}
	 	//设备是否在线
	 	/* if(device.online=="否"){
	 		img = "/images/77.png";
	 	} */
	    var myIcon = new BMap.Icon(img, new BMap.Size(23, 25), {    
	        // 指定定位位置。   
	        // 当标注显示在地图上时，其所指向的地理位置距离图标左上    
	        // 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
	        // 图标中央下端的尖角位置。    
	        anchor: new BMap.Size(10, 25),    
	        // 设置图片偏移。   
	        // 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
	        // 需要指定大图的偏移位置，此做法与css sprites技术类似。    
	        imageOffset: new BMap.Size(-2, 0)   // 设置图片偏移    
    });      
	    // 创建标注对象并添加到地图   
	    var marker = new BMap.Marker(point, {icon: myIcon}); 
	    if(device.type=='1'){
		    if(device.dustDatas==null||device.dustDatas==""||device.dustDatas.length==0){
		    	marker.setTitle("PM2.5: 无数据");
		    }else{
		    	marker.setTitle("PM2.5: "+device.dustDatas[0].pm2_5);
		    }
	    }else if(device.type=='2'){
	    	marker.setTitle("称重设备");
	    }
	    var label = new BMap.Label(device.dId,{offset:new BMap.Size(0,-18)});
	    marker.setLabel(label);
	    map.addOverlay(marker);    
	    marker.addEventListener("click", getContent); 
}    
	var infoWindow;
	function getContent(e){
   	    var dId = this.z.label.content; 
   	    var device;
	   	$.ajax({
	 		url : path + '/device/queryById',
	 		type : 'post',
	 		data:{"dId":dId},
	 		success:function(result){
	 			device = result;
	 			var wdstr;
	 			if(device.type==1){
		 			if(device.dustDatas.length!=0&&device.dustDatas[0].wd!=""&&device.dustDatas[0].wd!=null&&device.dustDatas[0].wd!="undefined"){
		 				if(device.dustDatas[0].wd=="0"){
		 					wdstr = "北风";
		 				}else if(device.dustDatas[0].wd=="1"){
		 					wdstr = "东北风";
		 				}else if(device.dustDatas[0].wd=="2"){
		 					wdstr = "东风";
		 				}else if(device.dustDatas[0].wd=="3"){
		 					wdstr = "东南风";
		 				}else if(device.dustDatas[0].wd=="4"){
		 					wdstr = "南风";
		 				}else if(device.dustDatas[0].wd=="5"){
		 					wdstr = "西南风";
		 				}else if(device.dustDatas[0].wd=="6"){
		 					wdstr = "西风";
		 				}else if(device.dustDatas[0].wd=="7"){
		 					wdstr = "西北风";
		 				}else{
		 					wdstr = "--";
		 				}
		 			}else{
		 				wdstr = "--";
		 			}
	   				$("#dName").html(device.name);
	   				$("#dId").val(device.dId);
	   				$("#address").html(device.address);
	   				$("#online").html(device.online=="否"?"离线":"在线");
					$("#pm2_5").html(device.dustDatas.length==0?"--":((device.dustDatas[0].pm2_5==""||device.dustDatas[0].pm2_5==null)==true?"-- ":device.dustDatas[0].pm2_5)+" μg/m³");
		  			$("#pm10").html(device.dustDatas.length==0?"--":((device.dustDatas[0].pm10==""||device.dustDatas[0].pm10==null)==true?"-- ":device.dustDatas[0].pm10)+" μg/m³");
		  			$("#temp").html(device.dustDatas.length==0?"--":((device.dustDatas[0].temp==""||device.dustDatas[0].temp==null)==true?"-- ":device.dustDatas[0].temp)+" ℃");
		  			$("#pressure").html(device.dustDatas.length==0?"--":((device.dustDatas[0].pressure==""||device.dustDatas[0].pressure==null)==true?"-- ":device.dustDatas[0].pressure)+" Kpa");
		  			$("#tsp").html(device.dustDatas.length==0?"--":((device.dustDatas[0].tsp==""||device.dustDatas[0].tsp==null)==true?"-- ":device.dustDatas[0].tsp)+" Lux");
		  			$("#h2").html(device.dustDatas.length==0?"--":((device.dustDatas[0].h2==""||device.dustDatas[0].h2==null)==true?"-- ":device.dustDatas[0].h2)+" %RH");
		  			$("#noise").html(device.dustDatas.length==0?"--":((device.dustDatas[0].noise==""||device.dustDatas[0].noise==null)==true?"-- ":device.dustDatas[0].noise)+" dB");
		  			$("#spd").html(device.dustDatas.length==0?"--":((device.dustDatas[0].spd==""||device.dustDatas[0].spd==null)==true?"-- ":device.dustDatas[0].spd)+" m/s");
		  			$("#wd").html(wdstr);
		  			$("#date_").html(new Date(device.dustDatas.length==0?new Date():device.dustDatas[0].date).Format("yyyy-MM-dd HH:mm:ss"));
		  			
		  			var opts = {    
		  	    		    width : 180,     // 信息窗口宽度    
		  	    		    height: 480,     // 信息窗口高度    
		  	    		    title : "<font size='3' color='blue'><b>扬尘监测点详情</b></font>"  // 信息窗口标题   
		  	    		}  
		  	    		infoWindow = new BMap.InfoWindow($("#currData").html(), opts);  // 创建信息窗口对象    
	 			}else if(device.type==2){
	 				$("#dName2").html(device.name);
	   				$("#address2").html(device.address);
	   				$("#online2").html(device.online=="否"?"离线":"在线");
	   				$("#deviceNo").html(device.deviceNo);
	   				$("#dModel").html(device.model);
	   				$("#operater").html(device.operater);
	   				$("#date2").html(device.date);
	 				var opts2 = {    
		  	    		    width : 180,     // 信息窗口宽度    
		  	    		    height: 300,     // 信息窗口高度    
		  	    		    title : "<font size='3' color='blue'><b>称重点详情</b></font>"  // 信息窗口标题   
		  	    		}
		  	    		infoWindow = new BMap.InfoWindow($("#weightData").html(), opts2);  // 创建信息窗口对象    
		  	    	}
	 		}
	 	});	
    	
		  	   		map.openInfoWindow(infoWindow,new BMap.Point(e.point.lng,e.point.lat));   
	}
	
	 
		 function history(){
				var dId = $("#dId").val();
				layer.open({                
					  type: 2,                
					  // skin: 'layui-layer-molv',               
					  title: '当日扬尘数据分析',               
					  content:["/device/historychart?dId="+dId,'no'] ,
					  //不允许出现滚动条                
					  area:['980px', '500px'] 
				  	});    
			}
	
	
    //日期格式化
	Date.prototype.Format = function (fmt) {  
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	      //季度
	        "q+": Math.floor((this.getMonth() + 3) / 3),  
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
    
</script>
</html>