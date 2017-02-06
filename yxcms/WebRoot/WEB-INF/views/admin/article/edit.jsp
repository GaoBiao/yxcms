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
		KindEditor.create("#articleContent", {
			resizeType : 1,
			height : 500,
			width : "100%",
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			uploadJson : "upload.do",
			afterBlur : function() {
				this.sync();
			},
			items : [ 'source', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist', 'insertunorderedlist', '|', 'image', 'link', '|', 'fullscreen' ]
		});
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
						<h5>文章编辑</h5>
					</div>
				</div>
				<div class="col-sm-12">
					<sf:form action="save.do" method="post" modelAttribute="article" cssClass="form-horizontal" role="form" enctype="multipart/form-data">
						<sf:hidden path="id" />
						<sf:hidden path="viewCount" />
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-6 col-md-4">
								<c:if test="${not empty error_message }">
									<p class="text-danger">${error_message }</p>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label for="articleTitle" class="col-sm-3 control-label">文章标题</label>
							<div class="col-sm-6 col-md-4">
								<sf:input path="articleTitle" id="articleTitle" cssClass="form-control required" autofocus="true" />
							</div>
						</div>
						<div class="form-group">
							<label for="keywords" class="col-sm-3 control-label">关键字</label>
							<div class="col-sm-6 col-md-4">
								<sf:input path="keywords" id="keywords" cssClass="form-control" />
								<p class="help-block">多个关键字使用逗号','分隔</p>
							</div>
						</div>
						<div class="form-group">
							<label for="moduleId" class="col-sm-3 control-label">所属模块</label>
							<div class="col-sm-6 col-md-2">
								<sf:select path="moduleId" cssClass="form-control">
									<c:forEach items="${modules }" var="module">
										<sf:option value="${module.id }">${module.indent }${module.name }</sf:option>
									</c:forEach>
								</sf:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">是否置顶</label>
							<div class="col-sm-3 col-md-2">
								<label class="radio-inline"> <sf:radiobutton path="isTop" value="true" label="是" /></label> <label class="radio-inline"> <sf:radiobutton path="isTop" value="false" label="否" /></label>
							</div>
							<label class="col-sm-2 control-label">是否发布</label>
							<div class="col-sm-3 col-md-2">
								<label class="radio-inline"> <sf:radiobutton path="isPublish" label="是" value="true" />
								</label> <label class="radio-inline"> <sf:radiobutton path="isPublish" label="否" value="false" /></label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">相关图片</label>
							<div class="col-sm-6 col-md-4">
								<sf:hidden path="imageUrl" />
								<c:if test="${not empty article.imageUrl }">
									<img class="img-thumbnail" src="<%=FileUploadUtils.baseUrl %>${article.imageUrl}" />
								</c:if>
								<div class="checkbox">
									<input type="file" name="image" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="keywords" class="col-sm-3 control-label">文章内容</label>
							<div class="col-sm-6 col-md-8">
								<sf:textarea path="articleContent" id="articleContent" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-floppy-disk"></span>
									&nbsp;提交
								</button>
								&nbsp;
								<button type="reset" class="btn btn-primary">
									<span class="glyphicon glyphicon-repeat"></span>
									&nbsp;重置
								</button>
								&nbsp;
								<button type="reset" class="btn btn-default back">
									<span class="glyphicon glyphicon-remove"></span>
									&nbsp;取消
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
