package org.hebgb.web.spring.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.hebgb.web.common.Paged;

public class PageTag extends AbstractTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pageAgument = "page";

	private Paged<?> paged;

	private Map<String, String[]> params;

	private String uri;

	@Override
	protected int doStartTagInternal() throws Exception {
		params = new HashMap<>(pageContext.getRequest().getParameterMap());
		uri = getRequestContext().getRequestUri();
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("<nav><span>共" + (paged.getTotalPage() + 1) + "页&nbsp;共" + paged.getTotalItem() + "条记录</span><ul class='pagination pull-right'>");
			if (paged != null) {
				if (paged.getPage() == 0) {
					sb.append("<li class='disabled'><a>&laquo;</a></li>");
				} else {
					params.put(pageAgument, new String[] { "0" });
					sb.append("<li><a href='" + uri + getQueryString(params) + "'>&laquo;</a></li>");
				}
				int start = paged.getPage() - 4;

				int end = paged.getPage() + 4;
				if (end > paged.getTotalPage()) {
					end = paged.getTotalPage();
					if ((end - start) < 9) {
						start = end - 8;
					}
				}
				if (start < 0) {
					start = 0;
				}
				if (start > 0) {
					sb.append("<li class='disabled'><a>...</a></li>");
				}
				for (int i = start; i <= end; i++) {
					if (i == paged.getPage()) {
						sb.append("<li class='active'><a>" + (i + 1) + "</a></li>");
					} else {

						params.put(pageAgument, new String[] { i + "" });
						sb.append("<li><a href='" + uri + getQueryString(params) + "'>" + (i + 1) + "</a></li>");
					}
				}

				if (end < paged.getTotalPage()) {
					sb.append("<li class='disabled'><a>...</a></li>");
				}
				if (paged.getTotalPage() <= paged.getPage()) {
					sb.append("<li class='disabled'><a>&raquo;</a></li>");
				} else {
					params.put(pageAgument, new String[] { paged.getTotalPage() + "" });
					sb.append("<li><a href='" + uri + getQueryString(params) + "'>&raquo;</a></li>");
				}
			}
			sb.append("</ul></nav>");
			pageContext.getOut().write(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspTagException(e.getMessage(), e);
		}
	}

	public Paged<?> getPaged() {
		return paged;
	}

	public void setPaged(Paged<?> paged) {
		this.paged = paged;
	}

	public String getPageAgument() {
		return pageAgument;
	}

	public void setPageAgument(String pageAgument) {
		this.pageAgument = pageAgument;
	}

}
