<%@page import="org.hebgb.utils.file.FileUploadUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mt" uri="/mytags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../head.jsp" />
</head>
<body>
	<jsp:include page="../header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-md-2 col-sm-3">
				<c:if test="${not empty breadcrumbs }">
					<h3>${breadcrumbs.get(0).name }</h3>
				</c:if>
				<ul class="nav nav-pills nav-stacked" role="tablist">
					<c:forEach items="${modules }" var="module">
						<c:if test="${module.parentId eq breadcrumbs.get(0).id }">
							<li role="presentation" <c:if test="${module.id eq breadcrumbs.get(1).id }">class='active'</c:if>><a href="<c:url value="${module.linkUrl }"/>">${module.name }</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="col-md-9  col-sm-8">
				<c:if test="${not empty breadcrumbs }">
					<ol class="breadcrumb">
						<c:forEach items="${breadcrumbs }" var="module">
							<li><a href="<c:url value="${module.linkUrl }"/>">${module.name }</a></li>
						</c:forEach>
					</ol>
				</c:if>
				<ul class="list-group">
					<c:forEach items="${paged.content }" var="article">
						<a class="list-group-item" href="view.do?id=${article.id }">${article.articleTitle } <c:if test="${article.isTop }">
								<span class="label label-danger">TOP</span>
							</c:if> <span class="pull-right">
								<fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
							</span>
						</a>
					</c:forEach>
				</ul>
				<mt:page paged="${paged }" />

			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>
