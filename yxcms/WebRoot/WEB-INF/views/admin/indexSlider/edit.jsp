<%@page import="org.hebgb.utils.file.FileUploadUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../head.jsp" />
<script type="text/javascript">
	$(function() {
		$("form").validate();
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
						<h5>轮播图片编辑</h5>
					</div>
				</div>
				<div class="col-sm-12">
					<sf:form action="save.do" method="post" modelAttribute="indexSlider" cssClass="form-horizontal" role="form" enctype="multipart/form-data">
						<sf:hidden path="id" />
						<sf:hidden path="sort" />
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-6 col-md-4">
								<c:if test="${not empty error_message }">
									<p class="text-danger">${error_message }</p>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label for="imageTitle" class="col-sm-3 control-label">图片标题</label>
							<div class="col-sm-6 col-md-4">
								<sf:input path="imageTitle" id="imageTitle" cssClass="form-control required" autofocus="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">图片文件</label>
							<div class="col-sm-6 col-md-4">
								<sf:hidden path="imageUrl" />
								<c:if test="${not empty indexSlider.imageUrl }">
									<img class="img-thumbnail" src="<%=FileUploadUtils.baseUrl %>${indexSlider.imageUrl}" />
								</c:if>
								<div class="checkbox">
									<input type="file" name="image" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="isShow" class="col-sm-3 control-label">是否显示</label>
							<div class="col-sm-6 col-md-4">
								<label class="radio-inline"> <sf:radiobutton path="isShow" label="是" value="true" />
								</label> <label class="radio-inline"> <sf:radiobutton path="isShow" label="否" value="false" /></label>
							</div>
						</div>
						<div class="form-group">
							<label for="isShow" class="col-sm-3 control-label">链接地址</label>
							<div class="col-sm-6 col-md-4">
								<sf:input path="linkUrl" cssClass="form-control url" placeholder="http://或https://开头" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-floppy-disk"></span> &nbsp;提交
								</button>
								&nbsp;
								<button type="reset" class="btn btn-primary">
									<span class="glyphicon glyphicon-repeat"></span> &nbsp;重置
								</button>
								&nbsp;
								<button type="reset" class="btn btn-default back">
									<span class="glyphicon glyphicon-remove"></span> &nbsp;取消
								</button>
							</div>
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
