<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<c:set var="staticPath" value="/static/uploads/"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

		<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>		
		
		<link rel="stylesheet" href="${contextPath}/css/ss.css" media="all" /> 
</head>
<body>

	<img id="cropbox" src="${staticPath }${pho.frontPic}" >	
			
	

	
</body>
</html>