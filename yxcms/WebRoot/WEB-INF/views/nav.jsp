<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="yx-nav">
	<div class="container-fluid">
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
		<form class="navbar-form navbar-right" role="search">
			<div class="form-group">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search"> <span class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<span class="glyphicon glyphicon-search"></span> &nbsp;
						</button>
					</span>
				</div>
			</div>
		</form>
	</div>
</div>