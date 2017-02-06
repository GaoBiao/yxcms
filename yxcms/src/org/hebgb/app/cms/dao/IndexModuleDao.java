package org.hebgb.app.cms.dao;

import java.util.List;

import org.hebgb.app.cms.model.IndexModule;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class IndexModuleDao extends AbstractDao<IndexModule, String>{

	public IndexModuleDao() {
		super(IndexModule.class);
	}

	@Override
	public List<Criterion> getCriterions(IndexModule object) {
		// TODO Auto-generated method stub
		return null;
	}

}
