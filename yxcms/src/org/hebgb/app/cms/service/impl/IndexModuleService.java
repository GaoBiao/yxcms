package org.hebgb.app.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.dao.IndexModuleDao;
import org.hebgb.app.cms.model.IndexModule;
import org.hebgb.app.cms.service.IIndexModuleService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndexModuleService implements IIndexModuleService {
	@Autowired
	private IndexModuleDao indexModuleDao;

	@Transactional
	public List<IndexModule> findAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(Order.asc("sort"));
		return indexModuleDao.findAll(null, orders);
	}

	@Transactional
	public IndexModule findById(String id) {
		return indexModuleDao.findOne(id);
	}

	@Transactional
	public void save(IndexModule indexModule) throws MessageException {
		if (indexModule == null) {
			throw new MessageException("object is null");
		}
		if (StringUtils.isEmpty(indexModule.getId())) {
			IndexModule last = indexModuleDao.findNext(Order.desc("sort"), null);
			indexModule.setSort(last == null ? 0 : last.getSort() + 1);
			indexModule.setCreateTime(new Date());
			indexModuleDao.insert(indexModule);
		} else {
			indexModuleDao.update(indexModule);
		}
	}

	@Transactional
	public void moveUp(String id) throws MessageException {
		IndexModule indexModule = indexModuleDao.findOne(id);
		if (indexModule == null) {
			throw new MessageException("object is null");
		}
		indexModuleDao.moveUp(Order.asc("sort"), indexModule);
	}

	@Transactional
	public void moveDown(String id) throws MessageException {
		IndexModule indexModule = indexModuleDao.findOne(id);
		if (indexModule == null) {
			throw new MessageException("object is null");
		}
		indexModuleDao.moveDown(Order.asc("sort"), indexModule);
	}

	@Transactional
	public void deleteById(String id) {
		IndexModule indexModule = new IndexModule();
		indexModule.setId(id);
		indexModuleDao.delete(indexModule);
	}
}
