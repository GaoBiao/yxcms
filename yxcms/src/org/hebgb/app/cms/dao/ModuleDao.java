package org.hebgb.app.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hebgb.app.cms.model.Module;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ModuleDao extends AbstractDao<Module, String> {

	public ModuleDao() {
		super(Module.class);
	}

	@Override
	public List<Criterion> getCriterions(Module object) {
		// TODO Auto-generated method stub
		return null;
	}

	public Module findLast(String parentId) {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("parentId", parentId));
		return findNext(Order.desc("sort"), null, criterions);
	}

}
