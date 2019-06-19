<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="staticPath" value="/static/uploads/"></c:set>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>预检明细修改</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${contextPath}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${contextPath}/css/global.css" media="all">
    <script type="text/javascript" charset="utf-8" src="${contextPath}/layui/layui.js"></script>
    <script type="text/javascript" src="${contextPath}/tinyImgUpload/js/tinyImgUpload.js"></script>    
    <link rel="stylesheet" href="${contextPath}/tinyImgUpload/css/tinyImgUpload.css"> 
    <style type="text/css"> 
	    #labelt{
            position: relative;
        }
        .fileUp{
            position: absolute;            
            left: 0;
            top: 0;
            opacity: 0;
        }
        #btn{
             padding: 5px 10px;
            background: #00b0f0;
            color: #FFF;
            border: none;
            border-radius: 5px;

        }
	</style>    
</head>

<body>
    <div class="admin-main">
        <form class="layui-form" style="padding: 20px;" id="updateForm" action="">
            <input type="hidden" id="previewId" name="previewId" value="${preview.previewId }">
            <div class="layui-form-item">
                <label class="layui-form-label">车道</label>
                <div class="layui-input-block">
                    <input type="text" id="lane" name="lane" value="${preview.lane }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">车牌</label>
                <div class="layui-input-block">
                    <input type="text" id="carNum" name="carNum" value="${preview.carNum }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">车轴</label>
                <div class="layui-input-block">
                    <input type="text" id="axleCnt" name="axleCnt" value="${preview.axleCnt }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">车速</label>
                <div class="layui-input-block">
                    <input type="text" id="speed" name="speed" value="${preview.speed }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">总重(kg)</label>
                <div class="layui-input-block">
                    <input type="text" id="sumWeight" name="sumWeight" value="${preview.sumWeight }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">限重(kg)</label>
                <div class="layui-input-block">
                    <input type="text" id="overRage" name="limitWeight" value="${preview.limitWeight }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">超限(kg)</label>
                <div class="layui-input-block">
                    <input type="text" id="overRage" name="overRage" value="${preview.overRage }" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">超限率%</label>
                <div class="layui-input-block">
                    <input type="text" id="overLoadRate" name="overLoadRate" value="${preview.overLoadRate }" class="layui-input">
                </div>
            </div>
            <!-- 抓拍时间 -->
            <div class="layui-form-item">
                <label class="layui-form-label">抓拍时间</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input" id="dateTime" name="dateTime" placeholder="yyyy-MM-dd HH:mm:ss" value="${preview.dateTime }">
                </div>
            </div>
            <div style="display:inline;text-align:center;" >
		            <div style="float:left;width:200px;text-align:center;height: 400px;position: relative;left: 30px;" >
                       <img id="demo1" style="width:180px;height: 380px;" src="/static/uploads${preview.frontPic }">
		               <p id="demoText1">前抓拍图</p>
	                </div>
	                <div style="float:left;width:200px;text-align:center;height: 400px;position: relative;left: 65px;">
                       <img id="demo2" style="width:180px;height: 380px;" src="/static/uploads${preview.backPic }">
		               <p id="demoText2">后抓拍图</p>
	                </div>
	                <div style="float:left;width:200px ;text-align:center;height: 400px;position: relative;left: 110px;">
                       <img id="demo3" style="width:180px;height: 380px;" src="/static/uploads${preview.sidePic }">
		               <p id="demoText3">侧抓拍图</p>
	                </div>
	                <div style="float:right;width:200px;text-align:center;height: 400px;position: relative;left: 10px;">
                       <img id="demo4" style="width:180px;height: 380px;" src="/static/uploads${preview.upPic }">
		               <p id="demoText4">顶抓拍图</p>
	                </div>
            </div>
            <div class="layui-form-item">
	                <label for="test1" id="labelt" style="left:90px">
	                    <input type="button" id="btn" value="前抓拍图">
	                    <input class="fileUp"  id="test1" name="files" type="file" onchange="filechange(event)" multiple="multiple"></input>
	                </label>
	                <label for="test1" id="labelt" style="left:250px">
	                    <input type="button" id="btn" value="后抓拍图">
				        <input class="fileUp" id="test2" name="files" type="file" onchange="filechange1(event)" multiple="multiple"></input>
	                </label>
	                <label for="test1" id="labelt" style="left:410px">
	                    <input type="button" id="btn" value="侧抓拍图">
				        <input class="fileUp"  id="test3" name="files" type="file" onchange="filechange2(event)" multiple="multiple"></input>
	                </label>
	                <label for="test1" id="labelt" style="left:580px">
	                    <input type="button" id="btn" value="顶抓拍图">
				        <input class="fileUp" id="test4" name="files" type="file" onchange="filechange3(event)" multiple="multiple"></input>
	                </label>
			     <!--   后抓拍图片<input class="fileUp" id="test2" name="files" type="file" onchange="filechange1(event)" multiple="multiple" style="width:200px;color: transparent;" ></input>
			                侧抓拍图片<input class="fileUp"  id="test3" name="files" type="file" onchange="filechange2(event)" multiple="multiple" style="width:200px"></input>
			                顶抓拍图片<input class="fileUp" id="test4" name="files" type="file" onchange="filechange3(event)" multiple="multiple" style="width:200px"></input>-->
            </div>
            <div class="layui-upload">
                <!--  <button class="layui-btn" id="test1" type="button">上传图片</button>-->
                <div class="layui-upload-list">
                    <input type="hidden" id="frontPic" name="frontPic" class="layui-input">
                    <input type="hidden" id="backPic" name="backPic" class="layui-input">
                    <input type="hidden" id="sidePic" name="sidePic" class="layui-input">
                    <input type="hidden" id="upPic" name="upPic" class="layui-input">
                </div>
            </div>
        </form>
    </div>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
    <script>
    layui.use(['jquery','form', 'layedit', 'laydate','table'], function(){
        var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate;

        //日期时间选择器
        laydate.render({
            elem: '#dateTime'
            ,type: 'datetime'
        });
    });
    function formData() {
        var data = new FormData($('#updateForm')[0]);
        return data;
    }
    </script>
    <script>
    /*前抓拍预览上传*/
    var fileQ=document.querySelector(".fileUp");
    var filechange=function(event){
        var files = event.target.files, file;
        if (files && files.length > 0) {
            // 获取目前上传的文件
            file = files[0];// 文件大小校验的动作
            if(file.size > 1024 * 1024 * 2) {
                alert('图片大小不能超过 2MB!');
                return false;
            }
            // 获取 window 的 URL 工具
            var URL = window.URL || window.webkitURL;
            // 通过 file 生成目标 url
            var imgURL = URL.createObjectURL(file);
            //用attr将img的src属性改成获得的url
            $("#demo1").attr("src",imgURL);
            // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
            // URL.revokeObjectURL(imgURL);
        }
        var formData = new FormData();
        formData.append('file', $('#test1')[0].files[0]);
        formData.append('tag', 1);
        $.ajax({
            url: '/preview/fileUpload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {
        	console.log(res);            
        	alert("上传成功");
            //var data=JSON.parse(res);
        	//alert(res.pt)
             $("#frontPic").attr("value", res.pt);
        }).fail(function(res) {
            alert("上传失败,请重新上传图片");
        });
        
    };
    /*后抓拍预览上传*/
    var filechange1=function(event){
        var files = event.target.files, file;
        if (files && files.length > 0) {
            // 获取目前上传的文件
            file = files[0];// 文件大小校验的动作
            if(file.size > 1024 * 1024 * 2) {
                alert('图片大小不能超过 2MB!');
                return false;
            }
            // 获取 window 的 URL 工具
            var URL = window.URL || window.webkitURL;
            // 通过 file 生成目标 url
            var imgURL = URL.createObjectURL(file);
            //用attr将img的src属性改成获得的url
            $("#demo2").attr("src",imgURL);
            // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
            // URL.revokeObjectURL(imgURL);
        }
        var formData = new FormData();
        formData.append('file', $('#test2')[0].files[0]);
        formData.append('tag', 2);
        $.ajax({
            url: '/preview/fileUpload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {
        	//alert(res)
            alert("上传成功")
            //var data=JSON.parse(res);
             $("#backPic").attr("value", res.pt);
        }).fail(function(res) {
            alert("上传失败");
        });
    };
    /*侧抓拍预览上传*/
    var filechange2=function(event){
        var files = event.target.files, file;
        if (files && files.length > 0) {
            // 获取目前上传的文件
            file = files[0];// 文件大小校验的动作
            if(file.size > 1024 * 1024 * 2) {
                alert('图片大小不能超过 2MB!');
                return false;
            }
            // 获取 window 的 URL 工具
            var URL = window.URL || window.webkitURL;
            // 通过 file 生成目标 url
            var imgURL = URL.createObjectURL(file);
            //用attr将img的src属性改成获得的url
            $("#demo3").attr("src",imgURL);
            // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
            // URL.revokeObjectURL(imgURL);
        }
        var formData = new FormData();
        formData.append('file', $('#test3')[0].files[0]);
        formData.append('tag', 3);
        $.ajax({
            url: '/preview/fileUpload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {
        	//alert(res)
            alert("上传成功")
            //var data=JSON.parse(res);
             $("#sidePic").attr("value", res.pt);
        }).fail(function(res) {
            alert("上传失败");
        });
    };
    /*顶抓拍预览上传*/
    var filechange3=function(event){
        var files = event.target.files, file;
        if (files && files.length > 0) {
            // 获取目前上传的文件
            file = files[0];// 文件大小校验的动作
            if(file.size > 1024 * 1024 * 2) {
                alert('图片大小不能超过 2MB!');
                return false;
            }
            // 获取 window 的 URL 工具
            var URL = window.URL || window.webkitURL;
            // 通过 file 生成目标 url
            var imgURL = URL.createObjectURL(file);
            //用attr将img的src属性改成获得的url
            $("#demo4").attr("src",imgURL);
            // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
            // URL.revokeObjectURL(imgURL);
        }
        var formData = new FormData();
        formData.append('file', $('#test4')[0].files[0]);
        formData.append('tag', 4);
        $.ajax({
            url: '/preview/fileUpload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {
        	//alert(res)
            alert("上传成功")
            //var data=JSON.parse(res);
             $("#upPic").attr("value", res.pt);
        }).fail(function(res) {
            alert("上传失败");
        });
    };
    //修改前图片
    $("#demo1").click(function () {
		$("#test1").click();
	})
	//修改后图片
    $("#demo2").click(function () {
		$("#test2").click();
	})
	//修改后图片
    $("#demo3").click(function () {
		$("#test3").click();
	})
	//修改后图片
    $("#demo4").click(function () {
		$("#test4").click();
	})
	
    </script>
</body>

</html>