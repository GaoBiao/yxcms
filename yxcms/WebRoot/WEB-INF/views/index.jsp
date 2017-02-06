<%@page import="org.hebgb.utils.file.FileUploadUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mt" uri="/mytags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="head.jsp" />
<script type="text/javascript">
	$(function() {
		var width = 0;
		var $ul = $(".yx-slider-list").children("ul");
		var $lis = $(".yx-slider-list").find("ul>li");
		var itemWidth = 0;
		for (var i = 0; i < $lis.length; i++) {
			itemWidth = $lis.eq(i).outerWidth() + 10;
			width += itemWidth;
		}
		var maxLeft = $ul.width() - width;
		$(".yx-slider-ctrl").bind("click", function() {
			if ($(this).hasClass("next")) {
				if (width > $(".yx-slider-list").width()) {
					var left = $ul.position().left;
					if (left >= maxLeft) {
						$ul.animate({
							left : left - itemWidth
						});
					}
				}
			}
			if ($(this).hasClass("prev")) {
				if (width > $(".yx-slider-list").width()) {
					var left = $ul.position().left;
					if (left < 0) {
						$ul.animate({
							left : left + itemWidth
						});
					}
				}
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<c:forEach items="${indexSliders }" varStatus="status">
					<li data-target="#carousel-example-generic" data-slide-to="${status.index }" <c:if test="${0 eq status.index}"> class="active"</c:if>></li>
				</c:forEach>
			</ol>
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<c:forEach items="${indexSliders }" var="image" varStatus="status">
					<div class="item  <c:if test="${0 eq status.index}">active</c:if>">
						<img style="width:100%" src="<%=FileUploadUtils.baseUrl %>${image.imageUrl}">
						<div class="carousel-caption">${image.imageTitle}</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="row yx-news-container">
			<div class="col-md-5">
				<div id="carousel-news" class="carousel slide" data-ride="carousel">
					<ol class="carousel-indicators">
						<c:forEach items="${topArticles }" varStatus="status">
							<li data-target="#carousel-news" data-slide-to="${status.index }" <c:if test="${0 eq status.index}"> class="active"</c:if>></li>
						</c:forEach>
					</ol>
					<div class="carousel-inner" role="listbox">
						<c:forEach items="${topArticles }" var="article" varStatus="status">
							<div class="item  <c:if test="${0 eq status.index}">active</c:if>">
								<img style="width:100%" src="<%=FileUploadUtils.baseUrl %>${article.imageUrl}">
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="desc-box">
					<dl>
						<dt>
							<a href="<c:url value="/article/view.do?id=${mainArticle.id }"/>">${mainArticle.articleTitle }</a>
						</dt>
						<dd>${mainArticle.articleContent}</dd>
						<dd>
							<a class="pull-right" href="<c:url value="/article/view.do?id=${mainArticle.id }"/>">【阅读全文】</a>
						</dd>
					</dl>
				</div>
				<div class="news-list">
					<c:forEach items="${mainArticles }" var="article">
						<p>
							<span class="pull-right"> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd" />
							</span>
							<a href="<c:url value="/article/view.do?id=${article.id}"/>" title="${article.articleTitle }"> ${article.articleTitle } </a>
						</p>
					</c:forEach>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						${noticeModule.module.name }
						<a href="<c:url value="/article/list.do?moduleId=${noticeModule.moduleId}"/>" class="pull-right">更多&gt;&gt;</a>
					</div>
					<div class="panel-body news-list">
						<c:forEach items="${noticeModule.articles}" var="article" varStatus="status">
							<p>
								<span class="pull-right"> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd" />
								</span>
								<a href="<c:url value="/article/view.do?id=${article.id}"/>" title="${article.articleTitle }">${article.articleTitle }</a>
							</p>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="row yx-news-container">
			<c:forEach items="${indexModules }" var="indexModule">
				<c:if test="${not empty indexModule.module}"></c:if>
				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							${indexModule.module.name }
							<a href="<c:url value="/article/list.do?moduleId=${indexModule.moduleId}"/>" class="pull-right">更多&gt;&gt;</a>
						</div>
						<div class="panel-body news-list">
							<c:forEach items="${indexModule.articles}" var="article" varStatus="status">
								<c:choose>
									<c:when test="${status.first and not empty article.imageUrl }">
										<div class="img-news">
											<span class="pull-right"> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd" />
											</span>
											<a href="<c:url value="/article/view.do?id=${article.id}"/>" title="${article.articleTitle }">
												<img class="img-rounded pull-left" src="<%=FileUploadUtils.baseUrl %>${article.imageUrl}"> ${article.articleTitle }
											</a>
										</div>
									</c:when>
									<c:otherwise>
										<p>
											<span class="pull-right"> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd" />
											</span>
											<a href="<c:url value="/article/view.do?id=${article.id}"/>" title="${article.articleTitle }">${article.articleTitle }</a>
										</p>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="row yx-news-container">
			<div class="yx-slider">
				<div class="yx-slider-title">${productModule.module.name }</div>
				<div class="yx-slider-ctrl prev"></div>
				<div class="yx-slider-list">
					<ul>
						<c:forEach items="${productModule.articles }" var="article">
							<li>
								<h3>
									<span>${article.articleTitle }</span>
								</h3>
								<p>${article.articleContent }</p>
								<a href="<c:url value="/article/view.do?id=${article.id}"/>" class="more">【更多】</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="yx-slider-ctrl next"></div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
