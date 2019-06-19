$("#No").click(function(){
	var No = $("#No").val();
	if($("#No").is(":checked") == true){
		NocheckboxOnclick(No);
	}else{
		deletePoint("非现场");
//		map.clearOverlays();
	}
});
$("#Start").click(function(){
	var Start = $("#Start").val();
    if($("#Start").is(":checked") == true){
    	StartcheckboxOnclick(Start);
    }else{
	map.clearOverlays();
}
});
$("#Person").click(function(){
	var Person = $("#Person").val();
if($("#Person").is(":checked") == true){
	PersoncheckboxOnclick(Person);
}else{
	deletePoint("执法人员");
//	map.clearOverlays();
}
});
$("#Over").click(function(){
	var Over = $("#Over").val();
if($("#Over").is(":checked") == true){
	OvercheckboxOnclick(Over);
}else{
//	map.clearOverlays();
	deletePoint("超限站");
}
});
$("#Car").click(function(){
	var Car = $("#Car").val();
if($("#Car").is(":checked") == true){
	CarcheckboxOnclick(Car);
}else{
//	map.clearOverlays();
	deletePoint("执法车");
}
});
function NocheckboxOnclick(No){
	var result = [  
	    new BMap.Point(113.661228,34.797076), new BMap.Point(113.717282,34.795238),  
	    new BMap.Point(113.663959,34.775553), new BMap.Point(113.688114,34.787899),  
	    new BMap.Point(113.687485,34.787973), new BMap.Point(113.690477,34.776858),  
	    new BMap.Point(113.686263,34.788269), new BMap.Point(113.685904,34.788566),  
	    new BMap.Point(113.682141,34.782194), new BMap.Point(113.680344,34.760133),  
	];
    var point = new Array(); //存放标注点经纬信息的数组
	  var marker = new Array(); //存放标注点对象的数组
	  var markers = []; // 创建一个数组
	  var info = new Array(); //存放提示信息窗口对象的数组
	  var myIcon = new BMap.Icon(path + "/images/fxc.png", new BMap.Size(50, 50), {
				  offset: new BMap.Size(23, 25), // 指定定位位置
				  imageOffset: new BMap.Size(0,0) // 设置图片偏移
			  });
    for (var i = 0; i < result.length; i++) {
//          var p0 = result[i].cLongitude; 
//          var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
          /*point[i] = new window.BMap.Point(p0, p1);*/ //循环生成新的地图点
    	
          markers.push(new window.BMap.Marker(result[i],{icon:myIcon}))//点聚合
          marker[i] = new window.BMap.Marker(result[i],{icon:myIcon}); //按照地图点坐标生成标记
  	      marker[i].setTitle("非现场");
          map.addOverlay(marker[i]);// 将标注添加到地图中
    }
//    var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});

		/*$.ajax({
			url : path + '/Coordinate/getNo',
			type : 'post',
			data:{'No':No},
			dataType : "json",
			success:function(result){
				      var point = new Array(); //存放标注点经纬信息的数组
					  var marker = new Array(); //存放标注点对象的数组
					  var markers = []; // 创建一个数组
					  var info = new Array(); //存放提示信息窗口对象的数组
					  var myIcon = new BMap.Icon(path + "/images/fxc.png", new BMap.Size(50, 50), {
								  offset: new BMap.Size(23, 25), // 指定定位位置
								  imageOffset: new BMap.Size(0,0) // 设置图片偏移
							  });
				      for (var i = 0; i < result.length; i++) {
	                        var p0 = result[i].cLongitude; 
	                        var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
	                        point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
	                        markers.push(new window.BMap.Marker(point[i],{icon:myIcon}))//点聚合
	                        marker[i] = new window.BMap.Marker(point[i],{icon:myIcon}); //按照地图点坐标生成标记
	                        map.addOverlay(marker[i]);// 将标注添加到地图中
				      }
				      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
			}
		});	*/
};
function StartcheckboxOnclick(Start){
	var result = [  
	    new BMap.Point(113.787758,39.787326), new BMap.Point(113.988689,34.987602),  
	    new BMap.Point(113.787329,39.787773), new BMap.Point(113.988114,34.987899),  
	    new BMap.Point(113.786485,39.787973), new BMap.Point(113.986658,34.988106),  
	    new BMap.Point(113.787263,39.788269), new BMap.Point(113.985904,34.988566),  
	    new BMap.Point(113.785565,39.788551), new BMap.Point(113.984287,34.988759),  
	    new BMap.Point(113.785269,39.7895), new BMap.Point(113.684323,34.789522)   
	];

	//map.clearOverlays();
	      var point = new Array(); //存放标注点经纬信息的数组
		  var marker = new Array(); //存放标注点对象的数组
		  var info = new Array(); //存放提示信息窗口对象的数组
		  var markers = []; // 创建一个数组
		  var myIcon = new BMap.Icon(path + "/images/源头治超.png", new BMap.Size(50, 50), {
					  offset: new BMap.Size(23, 25), // 指定定位位置
					  imageOffset: new BMap.Size(0,0) // 设置图片偏移
				  });
	      for (var i = 0; i < result.length; i++) {
//                var p0 = result[i].cLongitude; 
//                var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
//	    	  result[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                markers.push(new window.BMap.Marker(result[i],{icon:myIcon}))//点聚合
                marker[i] = new window.BMap.Marker(result[i],{icon:myIcon}); //按照地图点坐标生成标记,并赋予新图标
                map.addOverlay(marker[i]);// 将标注添加到地图中
                }
//	      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});

	/*$.ajax({
		url : path + '/Coordinate/getStart',
		type : 'post',
		data:{'Start':Start},
		dataType : "json",
		//traditional:true,
		success:function(result){
			//map.clearOverlays();
			      var point = new Array(); //存放标注点经纬信息的数组
				  var marker = new Array(); //存放标注点对象的数组
				  var info = new Array(); //存放提示信息窗口对象的数组
				  var markers = []; // 创建一个数组
				  var myIcon = new BMap.Icon(path + "/images/源头治超.png", new BMap.Size(50, 50), {
							  offset: new BMap.Size(23, 25), // 指定定位位置
							  imageOffset: new BMap.Size(0,0) // 设置图片偏移
						  });
			      for (var i = 0; i < result.length; i++) {
                        var p0 = result[i].cLongitude; 
                        var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
                        point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                        markers.push(new window.BMap.Marker(point[i],{icon:myIcon}))//点聚合
                        marker[i] = new window.BMap.Marker(point[i],{icon:myIcon}); //按照地图点坐标生成标记,并赋予新图标
                        map.addOverlay(marker[i]);// 将标注添加到地图中
                        }
			      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		}
	});	*/
};
function PersoncheckboxOnclick(Person){
	var result = [  
	    new BMap.Point(113.698758,34.887326), new BMap.Point(113.698689,34.787602),  
	    new BMap.Point(113.698329,34.887773), new BMap.Point(113.698114,34.797899),  
	    new BMap.Point(113.697485,34.887973), new BMap.Point(113.696658,34.798106),  
	    new BMap.Point(113.696263,34.888269), new BMap.Point(113.695904,34.798566),  
	    new BMap.Point(113.694565,34.888551), new BMap.Point(113.694287,34.798759),  
	    new BMap.Point(113.694269,34.78958), new BMap.Point(113.694323,34.799522)   
	];
	//map.clearOverlays();
	      var point = new Array(); //存放标注点经纬信息的数组
		  var marker = new Array(); //存放标注点对象的数组
		  var info = new Array(); //存放提示信息窗口对象的数组
		  var markers = []; // 创建一个数组
		  var myIcon = new BMap.Icon(path + "/images/执法人员.png", new BMap.Size(50, 50), {
					  offset: new BMap.Size(23, 25), // 指定定位位置
					  imageOffset: new BMap.Size(0,0) // 设置图片偏移
				  });
	      for (var i = 0; i < result.length; i++) {
//                var p0 = result[i].cLongitude; 
//                var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
//                point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                markers.push(new window.BMap.Marker(result[i],{icon:myIcon}))//点聚合
                marker[i] = new window.BMap.Marker(result[i],{icon:myIcon}); //按照地图点坐标生成标记
                marker[i].setTitle("执法人员");
                map.addOverlay(marker[i]);// 将标注添加到地图中
                }
//	     var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});

	/*$.ajax({
		url : path + '/Coordinate/getPerson',
		type : 'post',
		data:{'Person':Person},
		dataType : "json",
		//traditional:true,
		success:function(result){
			//map.clearOverlays();
			      var point = new Array(); //存放标注点经纬信息的数组
				  var marker = new Array(); //存放标注点对象的数组
				  var info = new Array(); //存放提示信息窗口对象的数组
				  var markers = []; // 创建一个数组
				  var myIcon = new BMap.Icon(path + "/images/执法人员.png", new BMap.Size(50, 50), {
							  offset: new BMap.Size(23, 25), // 指定定位位置
							  imageOffset: new BMap.Size(0,0) // 设置图片偏移
						  });
			      for (var i = 0; i < result.length; i++) {
                        var p0 = result[i].cLongitude; 
                        var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
                        point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                        markers.push(new window.BMap.Marker(point[i],{icon:myIcon}))//点聚合
                        marker[i] = new window.BMap.Marker(point[i],{icon:myIcon}); //按照地图点坐标生成标记
                        map.addOverlay(marker[i]);// 将标注添加到地图中
                        }
			     var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		}
	});	*/
};
function OvercheckboxOnclick(Over){
	var result = [  
	    new BMap.Point(113.686758,34.786326), new BMap.Point(113.686689,34.747602),  
	    new BMap.Point(113.686329,34.786773), new BMap.Point(113.686814,34.747899),  
	    new BMap.Point(113.687485,34.786973), new BMap.Point(113.686658,34.748106),  
	    new BMap.Point(113.686263,34.786269), new BMap.Point(113.685904,34.748566),  
	    new BMap.Point(113.664565,34.788551), new BMap.Point(113.684287,34.748759),  
	    new BMap.Point(113.664269,34.7895), new BMap.Point(113.684323,34.789522)   
	];
	//map.clearOverlays();
	      var point = new Array(); //存放标注点经纬信息的数组
		  var marker = new Array(); //存放标注点对象的数组
		  var info = new Array(); //存放提示信息窗口对象的数组
		  var markers = []; // 创建一个数组
		  var myIcon = new BMap.Icon(path + "/images/超限站.png", new BMap.Size(50, 50), {
					  offset: new BMap.Size(23, 25), // 指定定位位置
					  imageOffset: new BMap.Size(0,0) // 设置图片偏移
				  });
	      for (var i = 0; i < result.length; i++) {
//                var p0 = result[i].cLongitude; 
//                var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
//                point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                markers.push(new window.BMap.Marker(result[i],{icon:myIcon}))//点聚合
                marker[i] = new window.BMap.Marker(result[i],{icon:myIcon}); //按照地图点坐标生成标记
                marker[i].setTitle("超限站");
                map.addOverlay(marker[i]);// 将标注添加到地图中
                }
//	      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});

	/*$.ajax({
		url : path + '/Coordinate/getOver',
		type : 'post',
		data:{'Over':Over},
		dataType : "json",
		//traditional:true,
		success:function(result){
			//map.clearOverlays();
			      var point = new Array(); //存放标注点经纬信息的数组
				  var marker = new Array(); //存放标注点对象的数组
				  var info = new Array(); //存放提示信息窗口对象的数组
				  var markers = []; // 创建一个数组
				  var myIcon = new BMap.Icon(path + "/images/超限站.png", new BMap.Size(50, 50), {
							  offset: new BMap.Size(23, 25), // 指定定位位置
							  imageOffset: new BMap.Size(0,0) // 设置图片偏移
						  });
			      for (var i = 0; i < result.length; i++) {
                        var p0 = result[i].cLongitude; 
                        var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
                        point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                        markers.push(new window.BMap.Marker(point[i],{icon:myIcon}))//点聚合
                        marker[i] = new window.BMap.Marker(point[i],{icon:myIcon}); //按照地图点坐标生成标记
                        map.addOverlay(marker[i]);// 将标注添加到地图中
                        }
			      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		}
	});	*/
};
function CarcheckboxOnclick(Car){
	var result = [  
	    new BMap.Point(113.738758,34.787326), new BMap.Point(113.838689,34.787602),  
	    new BMap.Point(113.738329,34.987773), new BMap.Point(113.638114,34.687899),  
	    new BMap.Point(113.737485,34.787973), new BMap.Point(113.636658,34.788106),  
	    new BMap.Point(113.763623,34.988269), new BMap.Point(113.835904,34.688566),  
	    new BMap.Point(113.734565,34.988551), new BMap.Point(113.634287,34.788759),  
	    new BMap.Point(113.734269,34.7895), new BMap.Point(113.684323,34.769522)   
	];
	//map.clearOverlays();
	      var point = new Array(); //存放标注点经纬信息的数组
		  var marker = new Array(); //存放标注点对象的数组
		  var info = new Array(); //存放提示信息窗口对象的数组
		  var markers = []; // 创建一个数组
		  var myIcon = new BMap.Icon(path + "/images/执法车辆.png", new BMap.Size(50, 50), {
					  offset: new BMap.Size(23, 25), // 指定定位位置
					  imageOffset: new BMap.Size(0,0) // 设置图片偏移
				  });
	      for (var i = 0; i < result.length; i++) {
//                var p0 = result[i].cLongitude; 
//                var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
//                point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                markers.push(new window.BMap.Marker(result[i],{icon:myIcon}))//点聚合
                marker[i] = new window.BMap.Marker(result[i],{icon:myIcon}); //按照地图点坐标生成标记
                marker[i].setTitle("执法车");
                map.addOverlay(marker[i]);// 将标注添加到地图中
                }
	      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});

	/*$.ajax({
		url : path + '/Coordinate/getCar',
		type : 'post',
		data:{'Car':Car},
		dataType : "json",
		//traditional:true,
		success:function(result){
			//map.clearOverlays();
			      var point = new Array(); //存放标注点经纬信息的数组
				  var marker = new Array(); //存放标注点对象的数组
				  var info = new Array(); //存放提示信息窗口对象的数组
				  var markers = []; // 创建一个数组
				  var myIcon = new BMap.Icon(path + "/images/执法车辆.png", new BMap.Size(50, 50), {
							  offset: new BMap.Size(23, 25), // 指定定位位置
							  imageOffset: new BMap.Size(0,0) // 设置图片偏移
						  });
			      for (var i = 0; i < result.length; i++) {
                        var p0 = result[i].cLongitude; 
                        var p1 = result[i].cLatitude; //按照原数组的point格式将地图点坐标的经纬度分别提出来
                        point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
                        markers.push(new window.BMap.Marker(point[i],{icon:myIcon}))//点聚合
                        marker[i] = new window.BMap.Marker(point[i],{icon:myIcon}); //按照地图点坐标生成标记
                        map.addOverlay(marker[i]);// 将标注添加到地图中
                        }
			      var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		}
	});	*/
	      
};
function deletePoint(n){
	        var allOverlay = map.getOverlays();
		 
		         for (var i = 0; i < allOverlay.length; i++){
			 if (allOverlay[i].toString() == "[object Marker]") {
			 console.log(allOverlay[i]);
			 console.log(allOverlay[i].getTitle);
			 console.log(allOverlay[i].z.title);
//			 if(allOverlay[i].getLabel().content == n){
				 if(allOverlay[i].z.title == n){
	                map.removeOverlay(allOverlay[i]);
//	                break;
	            }
			         }
	 }
	    }
