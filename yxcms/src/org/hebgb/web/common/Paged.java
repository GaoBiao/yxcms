package org.hebgb.web.common;

import java.util.List;

public class Paged<T> extends Page {
	private List<T> content;
	private Integer totalPage;
	private int totalItem;

	public Paged(Page page) {
		setSize(page.getSize());
		setPage(page.getPage());
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPage() {
		if (totalPage == null) {
			totalPage = totalItem / getSize();
			if (totalItem % getSize() == 0) {
				totalPage--;
			}
		}
		return totalPage;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
}
