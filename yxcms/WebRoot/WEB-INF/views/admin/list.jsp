<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mt" uri="/mytags"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="head.jsp" />
</head>
<body>
	<jsp:include page="topnav.jsp" />
	<jsp:include page="left.jsp" />
	<div class="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="main-title">
						<h5>管理员列表</h5>
					</div>
				</div>
				<div class="col-sm-12 main-banner">
					<div class="pull-right">
						<a href="edit.do" class="btn btn-sm btn-primary"> <span class="glyphicon glyphicon-plus"></span> &nbsp;添加
						</a>
					</div>
					<form class="form-inline" role="form">
						<div class="form-group">
							<label for="loginName">登录名</label>
							<input type="text" class="input-sm form-control" name="loginName" id="loginName" placeholder="登录名" value="${admin.loginName }">
						</div>
						<div class="form-group">
							<label>注册时间</label>
							<div class="input-group input-daterange">
								<input type="text" class="form-control input-sm" name="startDate" value="${startDate }" placeholder="开始日期" />
								<span class="input-group-addon">至</span>
								<input type="text" class="form-control input-sm" name="endDate" value="${endDate }" placeholder="结束日期" />
							</div>
						</div>
						<button type="submit" class="btn btn-sm btn-success">
							<span class="glyphicon glyphicon-search"></span>
							&nbsp;查询
						</button>
					</form>
				</div>
				<div class="col-sm-12">
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
							<tr>
								<th>登录名</th>
								<th>状态</th>
								<th>最近登录时间</th>
								<th>注册时间</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${paged.content }" var="admin">
								<tr>
									<td>${admin.loginName }</td>
									<td>${statuses.get(admin.status) }</td>
									<td>
										<fmt:formatDate value="${admin.lastLoginTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>
										<fmt:formatDate value="${admin.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td class="text-center">
										<a href="edit.do?id=${admin.id }" title="编辑"> <span class="glyphicon glyphicon-edit"></span>
										</a> <a class="delete" href="delete.do?id=${admin.id }" title="删除"> <span class="glyphicon glyphicon-trash"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
						<mt:page paged="${paged }" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
