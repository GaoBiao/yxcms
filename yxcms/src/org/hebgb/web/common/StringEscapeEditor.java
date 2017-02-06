package org.hebgb.web.common;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringEscapeEditor extends PropertyEditorSupport {
	private boolean escapeHTML;
	private boolean escapeJavaScript;

	public StringEscapeEditor() {
		super();
	}

	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript) {
		super();
		this.escapeHTML = escapeHTML;
		this.escapeJavaScript = escapeJavaScript;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null) {
			setValue(null);
		} else {
			String value = text;
			if (escapeHTML) {
				value = StringEscapeUtils.escapeHtml4(value);
			}
			if (escapeJavaScript) {
				value = StringEscapeUtils.escapeEcmaScript(value);
			}
			setValue(value);
		}
	}

	public boolean isEscapeHTML() {
		return escapeHTML;
	}

	public void setEscapeHTML(boolean escapeHTML) {
		this.escapeHTML = escapeHTML;
	}

	public boolean isEscapeJavaScript() {
		return escapeJavaScript;
	}

	public void setEscapeJavaScript(boolean escapeJavaScript) {
		this.escapeJavaScript = escapeJavaScript;
	}
}
