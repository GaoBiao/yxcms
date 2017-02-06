<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mt" uri="/mytags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../head.jsp" />
</head>
<body>
	<jsp:include page="../topnav.jsp" />
	<jsp:include page="../left.jsp" />
	<div class="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="main-title">
						<h5>首页模块</h5>
					</div>
				</div>
				<div class="col-sm-12 main-banner">
					<form class="form-inline" action="save.do" method="post">
						<div class="form-group">
							<label>选择模块</label>
							<select name="moduleId" class="input-sm form-control">
								<c:forEach items="${modules }" var="module">
									<option value="${module.id }">${module.indent }${module.name }</option>
								</c:forEach>
							</select>
						</div>
						<button type="submit" class="btn btn-sm btn-primary">
							<span class="glyphicon glyphicon-plus"></span>
							&nbsp;添加
						</button>
					</form>
				</div>
				<div class="col-sm-12">
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
							<tr>
								<th>模块名称</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${indexModules }" var="indexModule">
								<tr>
									<td>${indexModule.module.name }</td>
									<td>
										<fmt:formatDate value="${indexModule.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td class="text-center">
										<a class="request" href="move.do?id=${indexModule.id }&position=up" title="上移"> <span class="glyphicon glyphicon-arrow-up"></span>
										</a> <a class="request" href="move.do?id=${indexModule.id }&position=down" title="下移"> <span class="glyphicon glyphicon-arrow-down"></span>
										</a> <a class="delete" href="delete.do?id=${indexModule.id }" title="删除"> <span class="glyphicon glyphicon-trash"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
