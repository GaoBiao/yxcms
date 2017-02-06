<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		var $as = $(".sidebar-menu").find("a");
		var $active;
		if (localStorage.currentMenu) {
			$active = $as.eq(parseInt(localStorage.currentMenu));
		}
		var url = window.location.href;
		for (var i = 0; i < $as.length; i++) {
			if (url.indexOf($as.get(i).href) != -1) {
				$active = $as.eq(i);
				localStorage.currentMenu = i;
				break;
			}
		}
		if ($active) {
			$active.parent().addClass("active");
		}
	});
</script>
<div class="sidebar">
	<ul class="sidebar-menu">
		<li>
			<label class="sidebar-title"><span class="glyphicon arrow"></span>首页管理</label>
			<ul>
				<li>
					<a class="sidebar-item" href="<c:url value="/admin/indexSlider/list.do"/>"> <span class="glyphicon glyphicon-picture"></span>轮播图片
					</a>
				</li>
				<li>
					<a class="sidebar-item" href="<c:url value="/admin/indexModule/list.do"/>"> <span class="glyphicon glyphicon-th-large"></span>显示模块
					</a>
				</li>
			</ul>
		</li>
		<li>
			<label class="sidebar-title"><span class="glyphicon arrow"></span>内容管理</label>
			<ul>
				<li>
					<a class="sidebar-item" href="<c:url value="/admin/module/list.do"/>"> <span class="glyphicon glyphicon-th"></span>模块列表
					</a>
				</li>
				<li>
					<a class="sidebar-item" href="<c:url value="/admin/article/list.do"/>"> <span class="glyphicon glyphicon-bookmark"></span>文章列表
					</a>
				</li>
			</ul>
		</li>
		<li>
			<label class="sidebar-title"><span class="glyphicon arrow"></span>系统管理</label>
			<ul>
				<li>
					<a class="sidebar-item" href="<c:url value="/admin/list.do"/>"> <span class="glyphicon glyphicon-user"></span>管理员列表
					</a>
				</li>
			</ul>
		</li>
	</ul>
</div>