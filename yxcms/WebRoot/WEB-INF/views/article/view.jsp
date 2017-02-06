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
				<div class="page-header">
					<h3>${article.articleTitle }</h3>
					<p class="help-block"><fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;浏览次数:${article.viewCount }人次</p>
				</div>
				<div class="page-content">${article.articleContent }</div>
			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>
