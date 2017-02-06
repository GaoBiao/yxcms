package org.hebgb.web.spring.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang3.StringUtils;

public class SortTag extends AbstractTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sortAgument = "sort";
	private static final String ASC = "asc";
	private static final String DESC = "desc";
	private Map<String, String[]> params;
	private String propertySort = null;
	private String property;
	private String direction = null;

	@Override
	protected int doStartTagInternal() throws Exception {
		params = new HashMap<>(pageContext.getRequest().getParameterMap());
		String uri = getRequestContext().getRequestUri();
		String[] sorts = params.get(sortAgument);
		if (sorts != null && sorts.length > 0) {
			String sort = sorts[0];
			if (StringUtils.isNotEmpty(sort)) {
				sorts = sort.split(",");
				if (sorts != null && sorts.length > 0) {
					for (String s : sorts) {
						if (s.startsWith(property + "_")) {
							propertySort = s;
							break;
						}
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(propertySort) && propertySort.endsWith("_" + DESC)) {
			params.put(sortAgument, new String[] { property + "_" + ASC });
		} else {
			params.put(sortAgument, new String[] { property + "_" + DESC });
		}
		String startEle = "<a href='" + uri + getQueryString(params) + "'>";
		pageContext.getOut().write(startEle);
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			String html = "</a>";
			if (StringUtils.isNotEmpty(propertySort)) {
				if (propertySort.endsWith("_" + DESC)) {
					html += "<span class='pull-right glyphicon glyphicon-sort-by-attributes'></span>";
				} else {
					html += "<span class='pull-right glyphicon glyphicon-sort-by-attributes-alt'></span>";
				}
			} else {
				html += "<span class='pull-right glyphicon glyphicon-sort'></span>";
			}
			pageContext.getOut().write(html);
		} catch (IOException e) {
			throw new JspTagException(e.getMessage(), e);
		}
		return super.doEndTag();
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSortAgument() {
		return sortAgument;
	}

	public void setSortAgument(String sortAgument) {
		this.sortAgument = sortAgument;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
