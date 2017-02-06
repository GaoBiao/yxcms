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
						<h5>首页轮播图片</h5>
					</div>
				</div>
				<div class="col-sm-12 main-banner">
					<div class="pull-right">
						<a href="edit.do" class="btn btn-sm btn-primary">
							<span class="glyphicon glyphicon-plus"></span> &nbsp;添加
						</a>
					</div>
					<sf:form cssClass="form-inline" role="form" modelAttribute="indexSlider" method="get">
						<div class="form-group">
							<label for="imageTitle">图片标题</label>
							<sf:input path="imageTitle" id="imageTitle" cssClass="input-sm form-control" placeholder="输入图片标题模糊查询" />
						</div>
						<div class="form-group">
							<label>是否显示</label>
							<sf:select path="isShow" cssClass="input-sm form-control">
								<sf:option value="">全部</sf:option>
								<sf:option value="true">是</sf:option>
								<sf:option value="false">否</sf:option>
							</sf:select>
						</div>
						<button type="submit" class="btn btn-sm btn-success">
							<span class="glyphicon glyphicon-search"></span> &nbsp;查询
						</button>
					</sf:form>
				</div>
				<div class="col-sm-12">
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
							<tr>
								<th>图片标题</th>
								<th>是否显示</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${paged.content }" var="indexSlider">
								<tr>
									<td>${indexSlider.imageTitle }</td>
									<td>
										<c:choose>
											<c:when test="${indexSlider.isShow }">是</c:when>
											<c:otherwise>否</c:otherwise>
										</c:choose>
									</td>
									<td>
										<fmt:formatDate value="${indexSlider.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td class="text-center">
										<a class="request" href="move.do?id=${indexSlider.id }&position=up" title="上移">
											<span class="glyphicon glyphicon-arrow-up"></span>
										</a>
										<a class="request" href="move.do?id=${indexSlider.id }&position=down" title="下移">
											<span class="glyphicon glyphicon-arrow-down"></span>
										</a>
										<a href="edit.do?id=${indexSlider.id }" title="编辑">
											<span class="glyphicon glyphicon-edit"></span>
										</a>
										<a class="delete" href="delete.do?id=${indexSlider.id }" title="删除">
											<span class="glyphicon glyphicon-trash"></span>
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
