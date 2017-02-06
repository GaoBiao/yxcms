<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="yx-header">
	<div class="container">
		<a href="<c:url value="/"/>" class="yx-logo"> </a>
	</div>
	<div class="container">
		<div class="yx-nav">
			<ul class="yx-navbar">
				<li>
					<a href="<c:url value="/"/>">首页</a>
				</li>
				<c:forEach items="${modules }" var="module">
					<c:if test="${empty module.parentId and module.isMenu}">
						<li>
							<a href="<c:url value="${module.linkUrl }"/>">${module.name }</a>
							<c:set var="hasMenu" value="false" />
							<c:forEach items="${modules }" var="m">
								<c:if test="${m.parentId eq module.id }">
									<c:set var="hasMenu" value="true" />
								</c:if>
							</c:forEach>
							<c:if test="${hasMenu }">
								<div class="yx-menu">
									<ul>
										<c:forEach items="${modules }" var="m">
											<c:if test="${m.parentId eq module.id }">
												<li>
													<a href="<c:url value="${m.linkUrl }"/>">${m.name }</a>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</c:if>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</header>