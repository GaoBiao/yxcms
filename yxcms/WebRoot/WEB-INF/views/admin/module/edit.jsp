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
		$("[name='isMenu']").bind("click", function() {
			if (this.checked) {
				$("#linkGroup").show();
			} else {
				$("#linkGroup").hide();
			}
		});
		$("#searchModule").autocomplete({
			source : "searchModule.do",
			select : function(event, ui) {
				$("#linkUrl").val("/article/list.do?moduleId=" + ui.item.value);
				return false;
			}
		});
		$("#searchArticle").autocomplete({
			source : "searchArticle.do",
			select : function(event, ui) {
				$("#linkUrl").val("/article/view.do?id=" + ui.item.value);
				return false;
			}
		});
	});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<sf:form target="_top" action="save.do" method="post" modelAttribute="module" cssClass=" form-horizontal" role="form">
					<sf:hidden path="id" />
					<sf:hidden path="parentId" />
					<sf:hidden path="sort" />
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-6 col-md-4">
							<c:if test="${not empty error_message }">
								<p class="text-danger">${error_message }</p>
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-3 control-label">模块名称</label>
						<div class="col-sm-6 col-md-4">
							<sf:input path="name" cssClass="form-control required" autofocus="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">父模块</label>
						<div class="col-sm-6 col-md-4">
							<p class="form-control-static">
								<c:choose>
									<c:when test="${not empty module.parentId }">${module.parent.name }</c:when>
									<c:otherwise>无</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-9">
							<div class="checkbox">
								<label> <sf:checkbox path="isMenu" /> 作为菜单显示
								</label>
							</div>
						</div>
					</div>
					<div class="form-group" id="linkGroup" <c:if test="${not module.isMenu }">style="display:none;"</c:if>>
						<label for="name" class="col-sm-3 control-label">链接地址</label>
						<div class="col-sm-6 col-md-4">
							<sf:input path="linkUrl" id="linkUrl" cssClass="form-control" placeholder="默认为本模块下的文章链接地址" />
							<p class="help-block">
								文章列表地址：
								<input id="searchModule" type="text" class="form-control" placeholder="输入模块名称自动生成" />
							</p>
							<p class="help-block">
								文章内容地址：
								<input id="searchArticle" type="text" class="form-control" placeholder="输入文章标题自动生成" />
							</p>
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
						</div>
					</div>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>
