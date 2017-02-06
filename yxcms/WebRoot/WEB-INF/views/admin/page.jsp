<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<nav>
	<ul class="pagination pull-right">
		<c:choose>
			<c:when test="${0 eq paged.page }">
				<li class="disabled"><a href="#/0">&laquo;</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#/0">&laquo;</a></li>
			</c:otherwise>
		</c:choose>
		<c:forEach begin="0" end="${paged.totalPage-1}" var="i">

			<c:choose>
				<c:when test="${i eq paged.page }">
					<li class="active"><a href="#/${i }">${i+1 }</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="#/${i }">${i+1 }</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:choose>
			<c:when test="${paged.totalPage-1 eq paged.page }">
				<li class="disabled"><a href="#/${paged.totalPage-1 }">&raquo;</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#/${paged.totalPage-1 }">&raquo;</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav>
