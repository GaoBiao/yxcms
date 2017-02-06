package org.hebgb.web.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;

public class Page {

	private int size = 12;
	private int page;
	/**
	 * 排序参数，多个排序用逗号分隔，排序方向使用下划线分隔，默认正序<br>
	 * 例：id_asc,name_desc
	 */
	private String sort;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<>();
		if (StringUtils.isNotEmpty(sort)) {
			for (String s : sort.split(",")) {
				String[] ss = s.split("_");
				String property = ss[0];
				if (ss.length > 0 && "desc".equalsIgnoreCase(ss[1])) {
					orders.add(Order.desc(property));
				} else {
					orders.add(Order.asc(property));
				}
			}
		}
		return orders;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
