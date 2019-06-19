<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="staticPath" value="/static/uploads/"></c:set>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>逆行明细查看</title>
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
            <div>
                <img style="width: 400px; height: 300px" src="${staticPath}${reverse.frontPic}" />
            </div>
            
            <div class="content" style="padding-left: 0;">
                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                       <span>车道：</span><input class="ccsq-clxx" type="text" name="lane" value="${reverse.lane }">
                    </div>
                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>车牌：</span> <input type="text" class="ccsq-clxx" name="carNum" value="${reverse.carNum }"></input>
                    </div>

                </div>

                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>站点：</span> <input type="text" class="ccsq-clxx" name="dateTime" value="${reverse.stationName }"></input>
                    </div>
                </div>
                <div class=" b-label" style="margin-bottom: 10px;">
                    <div style="float: left;">
                        <span>snapTime时间：</span> <input type="text" class="ccsq-clxx" name="dateTime" value="${reverse.snapTime }"></input>
                    </div>
                </div>


                <div class=" b-label" style="margin-bottom: 10px;">000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
                    <div style="float: left;">
                        <span>创建时间：</span> <input type="text" class="ccsq-clxx" name="dateTime" value="${reverse.createTime }"></input>
                    </div>
                </div>

            </div>
        </form>
    </div>


</body>
</html>
