<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="staticPath" value="/static/uploads/"></c:set>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title></title>
<link type="text/css" rel="stylesheet" href="../css/overrun.css" />

<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.all.js"></script>

<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/laydate/default/laydate.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/layui/css/modules/layer/default/layer.css" />

</head>
<body>
    <div class="cen" style="margin-top: 15px">
        <form action="" id="addForm">
            <!--   <div class="timg"> -->
            <c:forEach items="${img }" var="item">
                <img style="width: 400px; height: 300px" src="${staticPath}${item.img}" onclick="viewFullImg(this);" />
            </c:forEach>
            <c:if test="${not empty previewId.frontPic}">
                <img style="width: 400px; height: 300px" src="${staticPath}${previewId.frontPic }" onclick="viewFullImg(this);" /> 
            </c:if>
            <c:if test="${not empty previewId.backPic}">
                <img style="width: 400px; height: 300px" src="${staticPath}${previewId.backPic }" onclick="viewFullImg(this);" /> 
            </c:if>
            <c:if test="${not empty previewId.sidePic}">
                <img style="width: 400px; height: 300px" src="${staticPath}${previewId.sidePic }" onclick="viewFullImg(this);" /> 
            </c:if>
            <c:if test="${not empty previewId.upPic}">
                <img style="width: 400px; height: 300px" src="${staticPath}${previewId.upPic }" onclick="viewFullImg(this);" /> 
            </c:if>
            <c:if test="${not empty previewId.platePic}">
                <img style="width: 112px; height: 64px" src="${staticPath}${previewId.platePic }" onclick="viewFullImg(this);" /> 
            </c:if>
            
            <div class="content" style="padding-left: 0;">
                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>预检序号：</span> <input type="text" class="ccsq-clxx" name="previewId" id="_specifications11" value="${previewId.previewId }"></input>
                    </div>
                    <span>车道：</span><input class="ccsq-clxx" type="text" name="lane" value="${previewId.lane }">
                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>车牌：</span> <input type="text" class="ccsq-clxx" name="carNum" value="${previewId.carNum }"></input>
                    </div>
                    <span>轴数：</span><input class="ccsq-clxx" type="text" name="axleCnt" value="${previewId.axleCnt }">
                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>车速(km/h)：</span> <input type="text" class="ccsq-clxx" name="speed" value="${previewId.speed }"></input>
                    </div>
                    <span>限重(kg)：</span> <input type="text" class="ccsq-clxx" name="limitWeight" value="${previewId.limitWeight }"></input> <input class="ccsq-clxx" type="hidden" name="overLoadId" value="${previewId.overLoadId }" /> <input class="ccsq-clxx" type="hidden" name="direction" value="${previewId.direction }" />
                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>车货总重(kg)：</span> <input type="text" class="ccsq-clxx" name="sumWeight" value="${previewId.sumWeight }"></input>
                    </div>
                    <span>超重(kg)：</span><input class="ccsq-clxx" type="text" name="overRage" value="${previewId.overRage }" />
                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>时间：</span> <input type="text" class="ccsq-clxx" name="dateTime" value="${previewId.dateTime }"></input>
                    </div>
                    <span>超限率(%)：</span><input class="ccsq-clxx" type="text" name="overLoadRate" value="${previewId.overLoadRate }" />
                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style = "float: left;">
                        <span>站点：</span> 
                        <select name="station" id="station">
                            <option value="">请选择站点</option>
                            <c:forEach var="item" items="${station}">
                                <option value="${item.stationName }" hassubinfo="true">${item.stationName }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div style = "float: right">
                        <span > 执法人员签名及证号：</span>
                        <select class="ccsq-clxx" name="enforcement" id="enforcement">
                            <option value="" hassubinfo="true"></option>
                            <c:forEach var="item" items="${empList}">
                                <option value="${item.name }-${item.empCode }" hassubinfo="true">${item.name }-${item.empCode }</option>
                            </c:forEach>
                        </select>
                    <div>
                </div>
                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left">
                        <span > 执法人员签名及证号：</span>
                        <select class="ccsq-clxx" name="enforcementTwo" id="enforcementTwo">
                            <option value="" hassubinfo="true"></option>
                            <c:forEach var="item" items="${empList}">
                                <option value="${item.name }-${item.empCode }" hassubinfo="true">${item.name }-${item.empCode }</option>
                            </c:forEach>
                        </select>
                     <div>
                </div>
            </div>
        </form>
    </div>
    <script type="text/javascript">
                    function viewFullImg(me) {
                        var src = me.src;
                        layer.open({
                            type : 1,
                            title : "照片详情",
                            fix : false,
                            shadeClose : false,
                            area : [ '100%', '100%' ],
                            content : '<img src="'+src+'" >'
                        });
                    }
                    /*
                    function test1(){			
                      	 var previewId= $("#_specifications11").val();
                      	//alert("sg")
                      	layer.open({ 
                    	          type: 2, 	  	    
                    	         title: "照片详情", 
                    	          fix: false, 
                    	          shadeClose: false, 
                    	       //  maxmin: true, 
                    	     area: ['100%', '100%'],
                    		 content:'${pageContext.request.contextPath}/preview/showPhoto3?previewId='+previewId	
                    	      });  	   	
                      }
                      
                        
                        function test2(){
                        	var previewId= $("#_specifications11").val();
                        	//alert("sg")
                        	layer.open({ 
                      	          type: 2, 	  	    
                      	         title: "照片详情", 
                      	          fix: false, 
                      	          shadeClose: false, 
                      	         maxmin: true, 
                      	     area: ['80%', '100%'],
                      		 content:'${pageContext.request.contextPath}/preview/showPhoto4?previewId='+previewId	
                      	      });  		    	
                        }
                        
                        function test3(id){
                             // var previewId= $("#_specifications11").val();
                              //alert("sg")
                       
                              layer.open({ 
                                    type: 2,          
                                   title: "照片详情", 
                                    fix: false, 
                                    shadeClose: false, 
                                   maxmin: true, 
                               area: ['80%', '100%'],
                               content:'${pageContext.request.contextPath}/preview/showPhoto5?id='+id   
                                });               
                          }
                     */

                    var sels = document.getElementById('station');
                    for (var i = 0; i < sels.length; i++) {
                        var value = sels[i].value;
                        if (value == '${previewId.station}') {
                            sels[i].selected = true;
                        }
                    }

                    function formData() {
                        var data = new FormData($('#addForm')[0]);
                        return data;
                    }
                </script>

</body>
</html>
