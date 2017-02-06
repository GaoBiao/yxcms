package org.hebgb.app.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.model.Article;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao extends AbstractDao<Article, String> {

	public ArticleDao() {
		super(Article.class);
	}

	@Override
	public List<Criterion> getCriterions(Article object) {
		List<Criterion> criterions = new ArrayList<>();
		if (object != null) {
			if (StringUtils.isNotEmpty(object.getModuleId())) {
				criterions.add(Restrictions.eq("moduleId", object.getModuleId()));
			}
			if (StringUtils.isNotEmpty(object.getArticleTitle())) {
				criterions.add(Restrictions.like("articleTitle", "%" + object.getArticleTitle() + "%"));
			}
			if (object.getIsTop() != null) {
				criterions.add(Restrictions.eq("isTop", object.getIsTop()));
			}
		}
		return criterions;
	}

}
