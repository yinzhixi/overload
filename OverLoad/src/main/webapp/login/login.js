layui.config({
	base: "js/"
}).use(['form','layedit', 'layer'],
function() {
	var form = layui.form,
	layer = parent.layer === undefined ? layui.layer : parent.layer,
	$ = layui.jquery;
	//video背景
	$(window).resize(function() {
		if($(".video-player").width() > $(window).width()) {
			$(".video-player").css({
				"height": $(window).height(),
				"width": "auto",
				"left": -($(".video-player").width() - $(window).width()) / 2
			});
		} else {
			$(".video-player").css({
				"width": $(window).width(),
				"height": "auto",
				"left": -($(".video-player").width() - $(window).width()) / 2
			});
		}
	}).resize();

	$("body").keydown(function(event) {
		if (event.keyCode == "13") {//keyCode=13是回车键
		  $('#btnSumit').click();
		}
	});

	//登录按钮事件
	form.on("submit(login)",function(data){
		//layer.msg(JSON.stringify(data.field));
		
		var empName = $('#empName').val();
		var passWord =  $('#passWord').val();
		if(empName == '' ||passWord == ''){
			layer.msg("用户名或密码不能为空！");
			return;
		}
		
		$.ajax({
			url: '/emp/login',
			dataType: 'json',
			data: {
				"empName": empName,
				"passWord": passWord
			},
			type: 'POST',
			success: function(rs) {
				if(rs.flag) {
					//layer.msg("we're in");
					location.href = '/index.jsp';
				} else {
					layer.msg(rs.hint);
				}
			}
		});
		return false;
	})
})