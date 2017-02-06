<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
	<jsp:include page="topnav.jsp" />
	<jsp:include page="left.jsp" />
	<div class="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="main-title">
						<h5>管理员编辑</h5>
					</div>
				</div>
				<div class="col-sm-12">
					<sf:form action="save.do" method="post" modelAttribute="admin" cssClass="form-horizontal" role="form">
						<sf:hidden path="id" />
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-6 col-md-4">
								<c:if test="${not empty error_message }">
									<p class="text-danger">${error_message }</p>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label for="loginName" class="col-sm-3 control-label">登录名</label>
							<div class="col-sm-6 col-md-4">
								<c:choose>
									<c:when test="${not empty admin.id }">
										<p class="form-control-static">${admin.loginName }</p>
									</c:when>
									<c:otherwise>
										<sf:input path="loginName" cssClass="form-control required" autofocus="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-6 col-md-4">
								<c:choose>
									<c:when test="${not empty admin.id }">
										<sf:password path="password" cssClass="form-control" />
									</c:when>
									<c:otherwise>
										<sf:password path="password" cssClass="form-control required" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-floppy-disk"></span>
									&nbsp;提交
								</button>
								&nbsp;
								<button type="reset" class="btn btn-primary">
									<span class="glyphicon glyphicon-repeat"></span>
									&nbsp;重置
								</button>
								&nbsp;
								<button type="reset" class="btn btn-default back">
									<span class="glyphicon glyphicon-remove"></span>
									&nbsp;取消
								</button>
							</div>
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
