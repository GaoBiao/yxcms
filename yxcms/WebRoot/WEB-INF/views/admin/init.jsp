<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="head.jsp" />
<script type="text/javascript">
	$(function() {
		$("form").validate();
	});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-3 col-sm-1"></div>
			<div class="col-md-6 col-xs-12 col-sm-10">
				<div class="login-box">
					<form class="form-horizontal" method="post" role="form">
						<div class="form-group">
							<h3 class="col-sm-5">初始化管理员</h3>
							<div class="col-sm-7 error" id="messageBox">
								<c:if test="${not empty error_message }">
									<span class="shake"> ${error_message } </span>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control required" title="请输入用户名" id="username" name="loginName" autofocus placeholder="输入用户名" required />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control required" title="请输入密码" id="password" name="password" placeholder="输入密码" required />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-lg btn-primary btn-block">注&nbsp;&nbsp;&nbsp;&nbsp;册</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-3 col-sm-1"></div>
		</div>
	</div>
</body>
</html>
