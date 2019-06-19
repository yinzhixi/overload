<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" media="all" />
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jasny-bootstrap.min.css" media="all" />
    	<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
    	<script type="text/javascript" src="${contextPath}/js/jasny-bootstrap.min.js"></script>
     	<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>

		<ul id="js-reset-tabs" class="nav tab-underline border-bottom">
            <li class="active border-primary"><a class="text-active-primary" href="#tab-mobile" data-toggle="tab">账号登录</a></li>
            <li class="border-primary"><a class="text-active-primary" href="#tab-email" data-toggle="tab">手机登录</a></li>
        </ul>
</head>
<body>
		<div class="tab-content">
    <div class="tab-pane fade in active" id="tab-user">
        <form class="form" method="post" id="reset-password-form-by-user" action="<?= $url('users/login') ?>">
            <div class="form-group">
                <label for="email">
                    帐号
                </label>
                <input name="username" type="text" value="<?= $e->attr($req['username']) ?>" class="form-control"
                       placeholder="用户名/邮箱" required/>
            </div>

            <div class="form-group">
                <label for="password">
                    密码
                </label>
                <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"
                       data-rule-required="true">
            </div>
            <div class="form-group">
                <div class="error-message text-danger text-center">
                    <?= $e($req['message']) ?>
                </div>
            </div>
          
            <div class="clearfix form-group m-t-md" style="margin-top: 1%!important;">
                <button type="submit" class="btn btn-primary btn-block">账号登录</button>                                     
            </div>
        </form>
    </div>

    <div class="tab-pane fade in" id="tab-mobile">
        <form class="form" method="post" id="reset-password-form-by-mobile">
            <div class="form-group">
                <label for="mobile" class="control-label">
                    手机号码
                    <span class="text-warning">*</span>
                </label>

                <div class="col-control">
                    <input type="tel" class="js-mobile form-control" id="mobile" name="mobile" placeholder="请输入注册手机号码">
                </div>
            </div>

            <div class="form-group" id="get-verify-code-group">
                <label for="verify-code" class="control-label">
                    验证码
                    <span class="text-warning">*</span>
                </label>

                <div class="col-control">
                    <div class="input-group">
                        <input type="tel" class="form-control" id="verify-code" name="verifyCode" placeholder="请输入验证码">
                        <span class="input-group-btn border-left">
                    <button type="button" class="js-verify-code-send text-primary btn btn-default form-link"
                            id="get-verify-code">获取验证码
                    </button>
                </span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="text-primary">
                    <a href="<?= $url('registration/forget') ?>">忘记密码</a>
                    <a style="float:right;" href="<?= $url('registration/editInsurance') ?>">食品安全责任险</a>
                </div>
            </div>

            <div class="clearfix form-group m-t-md" style="margin-top: 1%!important;">
                <button type="submit" class="btn btn-primary btn-block">手机登录</button>
                <div id="phone-prompt" align="center" class="phone-prompt" style="color: red;width:100%;font-size:1em!important;" > 为了您更好的使用体验，请在电脑端登录:su1010.cn</div>
                <?php if ($setting('admin.enableRegister')) : ?>
                    <div class="m-t text-center" style="margin-top: 1% !important;">
                        没有账号，点击<a href="<?= $url('registration/register') ?>">注册</a>
                    </div>
                <?php endif ?>
            </div>
        </form>![这里写图片描述](https://img-blog.csdn.net/20170728132842011?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM4MzAwOTY5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
    </div>
</div>
</body>
</html>