package org.hebgb.app.cms.service;

import java.util.List;

import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Article;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.hibernate.criterion.Order;

public interface IArticleService {

	Paged<Article> findAll(Article article, Page page);

	Article findById(String id);

	void save(Article article) throws MessageException;

	List<Article> findList(Article article, List<Order> orders, int offset, int size);

	Paged<Article> search(String moduleId, String query, Page page);

}
