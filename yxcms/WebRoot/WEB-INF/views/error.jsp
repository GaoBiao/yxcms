<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/main.css"/>" />
<script type="text/javascript" src="<c:url value="/static/js/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/tools.js"/>"></script>
</head>
<body>
	<h1>错误页面</h1>
	<a class="btn goback">
		<span><s:message code="system.text.back" /></span>
	</a>
	${exception }
</body>
</html>