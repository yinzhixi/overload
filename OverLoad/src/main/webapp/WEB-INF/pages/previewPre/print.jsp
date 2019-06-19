
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<c:set var="staticPath" value="/static/uploads/"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${printPageHead }</title>
<link rel="stylesheet" href="${contextPath}/css/gcmxd.css" media="all">

<link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
<link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jQuery.print.js"></script>
<style type="text/css">
input {
    background-color: transparent;
}
</style>
</head>
<body>

    <div id="header">
        <div class="h-cen">
            <div class="bt">
                <h2 style="font-size: 40px">${printTitle }</h2>
            </div>
            <div class="jcdh">
                <span>检测单号：</span> <input type="text" class="jcdh-sj" value="${prev.previewId }" style="border: none;"></input>
            </div>
        </div>

        <div class="f-cen">
            <div class="content">
                <div class="dd">
                    <span>地点：</span> <input type="text" class="ccsq-clxx" value="${printStationLocal }"></input>
                </div>
                <div class="rq">
                    <span>日期：</span> <input type="text" class="ccsq-clxx" value="${prev.dateTime }"></input>
                </div>

                <div class="photo">
                    <h3>检测现场照片</h3>
                    <c:if test="${imgCount eq 3 }">
                        <div class="ph-1">
                            <img src="${staticPath }${imgPath1}" />
                        </div>
                        <div class="ph-2">
                            <img src="${staticPath }${imgPath2}" style="margin-top: 0" /> <img src="${staticPath }${imgPath3}" />
                        </div>
                    </c:if>

                    <c:if test="${imgCount eq 2 }">
                        <div class="ph-3">
                            <img src="${staticPath }${imgPath1}" />
                        </div>
                        <div class="ph-4">
                            <img src="${staticPath }${imgPath2}" style="margin-top: 0" />
                        </div>
                    </c:if>
                </div>

                <div class="jcsj">
                    <h3>检测数据结果</h3>
                    <div class=" b-label">
                        <div>
                            <span>车牌：</span><input type="text" name="userName" disabled="disabled" value="${prev.carNum }">
                        </div>

                        <div>
                            <span>车道：</span><input type="text" name="userName" disabled="disabled" value="${prev.lane }">
                        </div>

                        <div>
                            <span>轴数：</span> <input type="text" style="border: none; width: 200px; font-size: 16px;" disabled="disabled" value="${prev.axleCnt }"></input>
                        </div>

                        <div>
                            <span>总重(kg)：</span><input type="text" name="userName" disabled="disabled" value="${prev.sumWeight }">
                        </div>

                        <div>
                            <span>限重(kg)：</span><input type="text" name="userName" disabled="disabled" value="${prev.limitWeight }">
                        </div>

                        <div>
                            <span>超重(kg)：</span> <input type="text" style="border: none; width: 200px; font-size: 16px;" disabled="disabled" value="${prev.overRage }"></input>
                        </div>

                        <div>
                            <span>超限率(%)：</span><input type="text" name="userName" disabled="disabled" value="${prev.overLoadRate }">
                        </div>

                        <div>
                            <span>车速(km/h)：</span><input type="text" name="userName" disabled="disabled" value="${prev.speed }">
                        </div>

                        <div>
                            <span>行车方向：</span> <input type="text" style="border: none; width: 200px; font-size: 16px;" disabled="disabled" value="${prev.directionDesc }" id="direction"></input>
                        </div>

                        <div>
                            <span>车架号：</span><input type="text" name="userName" disabled="disabled" value="${car.vinNumber }">
                        </div>

                        <div>
                            <span>许可证号：</span><input type="text" name="userName" disabled="disabled" value="${car.licenseNumber }">
                        </div>
                        <div>
                            <span>车辆状态：</span> <input type="text" style="border: none; width: 160px; font-size: 16px;" disabled="disabled" value="${car.status }"></input>
                        </div>

                        <div>
                            <span>运输公司(或车主)名称：</span><input type="text" name="userName" disabled="disabled" value="${car.ownerName }">
                        </div>
                        <div>
                            <span>运输公司(或车主)电话：</span><input type="text" name="userName" disabled="disabled" value="${car.telephone }">
                        </div>

                        <div>
                            <span>运输公司(或车主)地址：</span><input style="width: 400px;" type="text" name="userName" disabled="disabled" value="${car.ownerAddress }">
                        </div>
                        <div style="clear: both;"></div>
                    </div>
                </div>
                <div class="qrww" style="margin-top: 15px">
                    <div class="qrww-lf">
                        <h3 style="font-family: serif; font-size: 20px; height: 65px; line-height: 65px;">本人确认以上检测数据无误</h3>
                        <span>驾驶员签字：</span> <input type="text" class="ccsq-clxx" style="width: 70%;" disabled="disabled" value=""></input> <span>日期：</span> <input type="text" class="ccsq-clxx" style="width: 70%;" disabled="disabled" value=""></input>
                    </div>
                    <div class="qrww-rg">
                        <span style="margin-top: 0; height: 65px; line-height: 65px;">检测单位：</span> 
                            <input type="text" style="width: 50%;" class="ccsq-clxx" disabled="disabled" value="${printPart }"></input>
                        <span style="margin:0; height:50px;line-height:50px; float: left;width: 220px;"> 执法人员签名及证号：</span>
                        <c:choose>
                            <c:when test="${enforcement !=null}">
                                <input type="text" style="float:left;height:50px;line-height:50px;font-size: 20px;" class="ccsq-clxx" name="enforcement" value="${enforcement }"></input>
                            </c:when>
                            <c:otherwise >
                                <select class="ccsq-clxx" style="width:420px; height:50px;line-height:50px;font-size: 20px;" name="emp1" id="emp1">
                                    <option value="" hassubinfo="true"></option>
                                    <c:forEach var="item" items="${empList}">
                                        <c:if test="${enforcement eq item.empCode }">
                                            <option selected="selected" value="${item.empCode }" hassubinfo="true">${item.name }-${item.empCode }</option>
                                        </c:if>
                                        <c:if test="${enforcement ne item.empCode }">
                                            <option value="${item.empCode }" hassubinfo="true">${item.name }-${item.empCode }</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:otherwise>
                        </c:choose>

                        <span style="margin:0; height:50px;line-height:50px; float: left;width: 220px;">执法人员签名及证号：</span>
                        <c:choose>
                            <c:when test="${enforcementTwo !=null}">
                                <input type="text" style="float:left;height:50px;line-height:50px;font-size: 20px;" class="ccsq-clxx" name="enforcement" value="${enforcementTwo }"></input>
                            </c:when>
                            <c:otherwise >
                                <select class="ccsq-clxx" style="width:420px; height:50px;line-height:50px;font-size: 20px;" name="emp2" id="emp2">
                                    <option value="" hassubinfo="true"></option>
                                    <c:forEach var="item" items="${empList}">
                                        <c:if test="${enforcementTwo eq item.empCode }">
                                            <option selected="selected" value="${item.empCode }" hassubinfo="true">${item.name }-${item.empCode }</option>
                                        </c:if>
                                        <c:if test="${enforcementTwo ne item.empCode }">
                                            <option value="${item.empCode }" hassubinfo="true">${item.name }-${item.empCode }</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div style="clear: both;"></div>
                </div>

                <div style="clear: both;"></div>
            </div>

        </div>
    </div>

    <div class="btn no-print">
        <input class="bc" type="button" value="关闭" onclick='close_()' />&nbsp;&nbsp; 
        <input class="tj" type="button" value="打印" onclick="print_();" />
    </div>

    <script type="text/javascript">
                    var index;
                    function close_() {
                        index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                    function print_() {
                       $("#header").print();
                       //close_();
                        /* //if (confirm('确定打印吗？')) {
                        var newstr = document.getElementById(MyDiv).innerHTML;
                        var oldstr = document.body.innerHTML;
                        document.body.innerHTML = newstr;
                        window.print();
                        document.body.innerHTML = oldstr;
                        return false;
                        // }  */
                    }
                </script>
</body>
</html>
