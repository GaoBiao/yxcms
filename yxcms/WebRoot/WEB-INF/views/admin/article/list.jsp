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
						<h5>文章列表</h5>
					</div>
				</div>
				<div class="col-sm-12 main-banner">
					<div class="pull-right">
						<a href="edit.do?moduleId=${article.moduleId }" class="btn btn-sm btn-primary"> <span class="glyphicon glyphicon-plus"></span> &nbsp;添加
						</a>
					</div>
					<sf:form cssClass="form-inline" role="form" modelAttribute="article" method="get">
						<div class="form-group">
							<label for="articleTitle">文章标题</label>
							<sf:input path="articleTitle" id="articleTitle" cssClass="input-sm form-control" placeholder="输入文章标题模糊查询" />
						</div>
						<div class="form-group">
							<label>所属模块</label>
							<sf:select path="moduleId" cssClass="input-sm form-control">
								<sf:option value="">
									<s:message code="system.text.all" />
								</sf:option>
								<c:forEach items="${modules }" var="module">
									<sf:option value="${module.id }">${module.indent }${module.name }</sf:option>
								</c:forEach>
							</sf:select>
						</div>
						<button type="submit" class="btn btn-sm btn-success">
							<span class="glyphicon glyphicon-search"></span>
							&nbsp;查询
						</button>
					</sf:form>
				</div>
				<div class="col-sm-12">
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
							<tr>
								<th>文章标题</th>
								<th>所属模块</th>
								<th>是否置顶</th>
								<th>是否发布</th>
								<th>浏览次数</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${paged.content }" var="article">
								<tr>
									<td class="tl">${article.articleTitle }</td>
									<td>${article.module.name }</td>
									<td>
										<c:choose>
											<c:when test="${article.isTop }">是</c:when>
											<c:otherwise>否</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${article.isPublish }">是</c:when>
											<c:otherwise>否</c:otherwise>
										</c:choose>
									</td>
									<td>${article.viewCount }</td>
									<td>
										<fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td class="text-center">
										<a href="edit.do?id=${article.id }" title="编辑"> <span class="glyphicon glyphicon-edit"></span>
										</a> <a class="delete" href="delete.do?id=${article.id }" title="删除"> <span class="glyphicon glyphicon-trash"></span>
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
