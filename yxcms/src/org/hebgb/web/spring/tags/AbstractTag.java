package org.hebgb.web.spring.tags;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public abstract class AbstractTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_SEPARATOR = ",";

	protected String getQueryString(Map<String, String[]> params) throws UnsupportedEncodingException {
		String queryString = "";
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String[]> entry : params.entrySet()) {
				if (StringUtils.isEmpty(queryString)) {
					queryString += "?";
				} else {
					queryString += "&";
				}
				queryString += entry.getKey() + "=";
				String value = "";
				if (entry.getValue() != null && entry.getValue().length > 0) {
					for (String v : entry.getValue()) {
						v = URLEncoder.encode(v, "UTF-8");
						if ("".equals(value)) {
							value += v;
						} else {
							value += DEFAULT_SEPARATOR + v;
						}
					}
				}
				queryString += value;
			}
		}
		return queryString;
	}
}
