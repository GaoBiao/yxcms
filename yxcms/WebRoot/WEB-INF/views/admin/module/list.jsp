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
<script type="text/javascript">
	$(function() {
		
	});
</script>
</head>
<body>
	<jsp:include page="../topnav.jsp" />
	<jsp:include page="../left.jsp" />
	<div class="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="main-title">
						<h5>模块管理</h5>
					</div>
				</div>
				<div class="col-md-4">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title pull-left">全部模块</h3>
							<a href="edit.do" target="editFrame" class="btn btn-default btn-xs pull-right"> <span class="glyphicon glyphicon-plus"></span> &nbsp;添加模块
							</a>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body">
							<ul class="list-group">
								<c:forEach items="${modules }" var="module">
									<li class="list-group-item">
										<a class="editLink" href="edit.do?id=${module.id }" target="editFrame">${module.indent }${module.name }
										<c:if test="${!module.isMenu }">
										<span class="glyphicon glyphicon-eye-close"></span>
										</c:if>
										</a>
										<div class="btn-group pull-right">
											<a title="添加子模块" target="editFrame" href="edit.do?parentId=${module.id }" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-plus"></span> <span class="sr-only">添加子模块</span></a>
											<a title="上移" href="move.do?id=${module.id }&position=up" class="btn btn-xs btn-default request"><span class="glyphicon glyphicon-arrow-up"></span> <span class="sr-only">上移</span></a>
											<a title="下移" href="move.do?id=${module.id }&position=down" class="btn btn-xs btn-default request"><span class="glyphicon glyphicon-arrow-down"></span> <span class="sr-only">下移</span></a>
											<a title="删除" href="delete.do?id=${module.id }" class="btn btn-xs btn-default delete"><span class="glyphicon glyphicon-trash"></span> <span class="sr-only">删除模块</span></a>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<iframe src="edit.do" frameborder="0" style="width: 100%;height: 500px;" name="editFrame"></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
