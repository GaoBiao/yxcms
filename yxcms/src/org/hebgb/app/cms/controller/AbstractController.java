package org.hebgb.app.cms.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class AbstractController {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	protected HttpServletRequest request;

	protected HttpSession getSession() {
		if (request != null) {
			return request.getSession();
		}
		return null;
	}

	protected void saveSessionParams() {
		getSession().setAttribute(this.getClass().getCanonicalName(), new HashMap<>(request.getParameterMap()));
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String[]> getSessionParams() {
		return (Map<String, String[]>) getSession().getAttribute(this.getClass().getCanonicalName());
	}

	protected void clearSessionParams() {
		getSession().removeAttribute(this.getClass().getCanonicalName());
	}

	protected Map<Object, String> getEnumI18nMap(Enum<?>[] enums) {
		Map<Object, String> map = new LinkedHashMap<>();
		for (Enum<?> e : enums) {
			map.put(e, getI18nValue(e.getClass().getCanonicalName() + "." + e.name(), null, request.getLocale()));
		}
		return map;
	}

	protected String getEnumI18nValue(Enum<?> e) {
		return getI18nValue(e.getClass().getCanonicalName() + "." + e.name(), null, request.getLocale());
	}

	protected String getEnumI18nValue(Enum<?> e, Object[] args) {
		return getI18nValue(e.getClass().getCanonicalName() + "." + e.name(), args, request.getLocale());
	}

	protected String getEnumI18nValue(Enum<?> e, Object[] args, Locale locale) {
		return getI18nValue(e.getClass().getCanonicalName() + "." + e.name(), args, locale);
	}

	protected String getI18nValue(String code, Object[] args, Locale locale) {
		String str = "";
		try {
			str = messageSource.getMessage(code, args, locale);
		} catch (Exception e) {
		}
		return str;
	}

	protected String getI18nValue(String code) {
		return getI18nValue(code, null, request.getLocale());
	}

	protected String getI18nValue(String code, Object[] args) {
		return getI18nValue(code, args, request.getLocale());
	}

	protected Map<Object, String> getI18nMap(String i18nModel, Class<?> keyClass) {
		return getI18nMap(i18nModel, keyClass, request.getLocale());
	}

	protected Map<Object, String> getI18nMap(String i18nModel, Class<?> keyClass, Locale locale) {
		String i18nKey = getI18nValue(i18nModel + ".key", null, locale);
		String i18nValue = getI18nValue(i18nModel + ".value", null, locale);
		Map<Object, String> map = null;
		if (!i18nKey.isEmpty() && !i18nValue.isEmpty()) {
			map = new LinkedHashMap<>();
			String[] keys = i18nKey.split(",");
			String[] values = i18nValue.split(",");
			if (keys != null && values != null && keys.length == values.length) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i] != null && values[i] != null) {
						if (Integer.class.equals(keyClass)) {
							map.put(Integer.parseInt(keys[i]), values[i]);
						} else {
							map.put(keys[i], values[i]);
						}
					}
				}
			}
		}
		return map;
	}

}
