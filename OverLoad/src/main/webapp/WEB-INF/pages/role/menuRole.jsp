<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>角色权限</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   
      
    <link rel="stylesheet" href="${contextPath}/css/zTreeStyle.css" >
     <script type="text/javascript" charset="utf-8" src="${contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/js/jquery.ztree.all.min.js"></script>
   
</head>
<body>
  <%-- <div style="padding:10px;">角色：${role.name }</div>
  <div>
    <table class="layui-hide" id="table" lay-filter="action"></table>
  </div> --%>
  
     <ul id="treeDemo" class="ztree"></ul>
</body>
    <script type="text/javascript">
    
    
    var cur_role_data;
    
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, null);
    });
    
    var setting = {
    	    async: {
    	        enable: true,//采用异步加载
    	        dataFilter: ajaxDataFilter,    //预处理数据
    	        url : '${contextPath}/role/menu/data?id=${id}',
    	        dataType : "json"
    	    },
    	    data : {
    	        key : {
    	            //title : "name", //鼠标悬停显示的信息   
    	            name : "name"   //网页上显示出节点的名称
    	        },
    	        simpleData : {
    	            enable : true,
    	            idKey : "id", //修改默认的ID为自己的ID
    	            pIdKey : "pId", //修改默认父级ID为自己数据的父级ID
    	            rootPId : null   //根节点的ID
    	        }
    	    },
    	    check: {
    			enable: true,
    			chkStyle: "checkbox",
    			chkboxType: { "Y": "", "N": "" }
    		},
    	    callback : {
    	        onAsyncSuccess: zTreeOnAsyncSuccess //异步加载完成调用
    	    }
    	};
    	//获取返回的数据，进行预操作
    	function ajaxDataFilter(treeId, parentNode, responseData) {   
    		
    		 cur_role_data = responseData.cur_role_data;
    		
    			
    		    responseData = responseData.data;
    		    return responseData;
    	};
    	//异步加载完成时运行，此方法将所有的节点打开，并选中角色已拥有菜单
    	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    	    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    	    treeObj.expandAll(true);
    	    for(var i=0; i<cur_role_data.length; i++){
    		    var node = treeObj.getNodeByParam('id',cur_role_data[i].id,null);
    		    if(node != null){
    		    	treeObj.checkNode(node, true, false, false);
    		    }
    	    }
    	}
    	//提交选中的菜单
    	function subMenu(){
    		var treeObj = $.fn.zTree.getZTreeObj('treeDemo');
    		var nodes = treeObj.getCheckedNodes(true);
    		if(nodes.length == 0){
    			
    			alert("请选择菜单！");
    			return;
    		}
    		var menuIds = new Array();
    	
    		for(var i=0; i<nodes.length; i++){
    			menuIds.push(nodes[i].id);
    		}
    		return menuIds.join(",");
    	}
    
    
    </script>
</html>