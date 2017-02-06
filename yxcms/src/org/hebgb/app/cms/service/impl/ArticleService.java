package org.hebgb.app.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.dao.ArticleDao;
import org.hebgb.app.cms.model.Article;
import org.hebgb.app.cms.service.IArticleService;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService implements IArticleService {
	@Autowired
	private ArticleDao articleDao;

	@Transactional
	public Paged<Article> findAll(Article article, Page page) {
		List<Order> orders = page.getOrders();
		if (orders.isEmpty()) {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("createTime"));
		}
		return articleDao.findAll1(page, article, orders);
	}

	@Transactional
	public Article findById(String id) {
		return articleDao.findOne(id);
	}

	@Transactional
	public void save(Article article) throws MessageException {
		if (article == null) {
			throw new MessageException("object is null");
		}
		if (StringUtils.isEmpty(article.getArticleTitle())) {
			throw new MessageException("文章标题为空");
		}
		if (StringUtils.isEmpty(article.getModuleId())) {
			throw new MessageException("所属模块为空");
		}
		if (StringUtils.isNotEmpty(article.getId())) {
			articleDao.update(article);
		} else {
			article.setCreateTime(new Date());
			article.setViewCount(0);
			articleDao.insert(article);
		}
	}

	@Transactional
	public List<Article> findList(Article article, List<Order> orders, int offset, int size) {
		return articleDao.findAll1(article, orders, offset, size);
	}

	@Transactional
	public Paged<Article> search(String moduleId, String query, Page page) {
		List<Criterion> criterions = new ArrayList<>();
		if (StringUtils.isNotEmpty(moduleId)) {
			criterions.add(Restrictions.eq("moduleId", moduleId));
		}
		if (StringUtils.isNotEmpty(query)) {
			criterions.add(Restrictions.or(Restrictions.like("articleTitle", "%" + query + "%"), Restrictions.like("keywords", "%" + query + "%")));
		}
		List<Order> orders = page.getOrders();
		if (orders.isEmpty()) {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("createTime"));
		}
		return articleDao.findAll(page, criterions, orders);
	}
}
