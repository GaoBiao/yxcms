package org.hebgb.app.cms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.model.Admin;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao extends AbstractDao<Admin, String> {

	public AdminDao() {
		super(Admin.class);
	}

	@Override
	public List<Criterion> getCriterions(Admin object) {
		List<Criterion> criterions = new ArrayList<>();
		if (object != null) {
			if (StringUtils.isNotEmpty(object.getLoginName())) {
				criterions.add(Restrictions.like("loginName", "%" + object.getLoginName() + "%"));
			}
		}
		return criterions;
	}

	public Admin findByLoginName(String loginName) {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("loginName", loginName));
		return findOne(criterions);
	}

	public void updateLastLoginTime(String id, Date time) {
		String hql = "update Admin a set a.lastLoginTime=:time where a.id=:id";
		Query query = currentSession().createQuery(hql);
		query.setParameter("id", id);
		query.setParameter("time", time);
		query.executeUpdate();
	}

}
