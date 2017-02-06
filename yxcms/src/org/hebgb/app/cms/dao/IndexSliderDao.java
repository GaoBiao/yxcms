package org.hebgb.app.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.model.IndexSlider;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IndexSliderDao extends AbstractDao<IndexSlider, String> {

	public IndexSliderDao() {
		super(IndexSlider.class);
	}

	@Override
	public List<Criterion> getCriterions(IndexSlider object) {
		List<Criterion> criterions = new ArrayList<>();
		if (object != null) {
			if (object.getIsShow() != null) {
				criterions.add(Restrictions.eq("isShow", object.getIsShow()));
			}
			if (StringUtils.isNotEmpty(object.getImageTitle())) {
				criterions.add(Restrictions.like("imageTitle", "%" + object.getImageTitle() + "%"));
			}
		}
		return criterions;
	}

}
