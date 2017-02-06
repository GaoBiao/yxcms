(function($) {
	$(function() {
		$(".sidebar-menu").on("click", ".sidebar-title", function() {
			var $li = $(this).parent();
			if ($li.hasClass("closed")) {
				$li.removeClass("closed");
			} else {
				$li.addClass("closed");
			}
		});
		var $daterange = $(".input-daterange");
		if ($daterange && $daterange.length) {
			var dateFormat = "yy-dd-mm";
			var $from = $daterange.find("input[type='text']").eq(0).datepicker({
				dateFormat : dateFormat
			}).on("change", function() {
				$to.datepicker("option", "minDate", this.value);
			});
			var $to = $daterange.find("input[type='text']").eq(1).datepicker({
				dateFormat : dateFormat
			}).on("change", function() {
				$from.datepicker("option", "maxDate", this.value);
			});
		}
		$("a.delete").click(function() {
			var msg = $(this).attr("msg") ? $(this).attr("msg") : "确定要删除吗？";
			if (confirm(msg)) {
				$.get($(this).attr("href"), {
					_tmp : (new Date()).getTime()
				}, function(result) {
					if (result && result.code) {
						alert(result.message);
						return;
					}
					window.location.reload(true);
				});
			}
			return false;
		});
		$("a.request").click(function() {
			$.get($(this).attr("href"), {
				_tmp : (new Date()).getTime()
			}, function(result) {
				if (result && result.code) {
					alert(result.message);
					return;
				}
				window.location.reload(true);
			});
			return false;
		});
		$(".back").click(function() {
			window.history.go(-1);
		});
	});
})(jQuery);