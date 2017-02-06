<%@page import="org.hebgb.app.cms.model.Admin"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
%>
<nav class="navbar navbar-default navbar-static-top" style="margin-bottom:0;">
	<a class="navbar-brand">Brand</a>
	<p class="navbar-text">欢迎登录管理员系统</p>
	<ul class="nav navbar-nav navbar-right" style="margin-right:15px;">
		<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<span class="glyphicon glyphicon-user"></span>
				<%=admin.getLoginName()%>
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu" role="menu">
				<li>
					<a href="#">
						<span class="glyphicon glyphicon-user"></span>&nbsp;个人信息
					</a>
				</li>
				<li class="divider"></li>
				<li>
					<a href="<c:url value="/admin/logout.do"/>">
						<span class="glyphicon glyphicon-log-out"></span>&nbsp;登出
					</a>
				</li>
			</ul>
		</li>
	</ul>
</nav>