package org.hebgb.app.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.dao.IndexSliderDao;
import org.hebgb.app.cms.model.IndexSlider;
import org.hebgb.app.cms.service.IIndexSliderService;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndexSliderService implements IIndexSliderService {
	@Autowired
	private IndexSliderDao indexSliderDao;

	@Transactional
	public Paged<IndexSlider> findAll(IndexSlider indexSlider, Page page) {
		List<Order> orders = page.getOrders();
		if (orders.isEmpty()) {
			orders.add(Order.asc("sort"));
		}
		return indexSliderDao.findAll1(page, indexSlider, orders);
	}

	@Transactional
	public IndexSlider findById(String id) {
		return indexSliderDao.findOne(id);
	}

	@Transactional
	@CacheEvict(value = "indexSliderCache", allEntries = true)
	public void save(IndexSlider indexSlider) throws MessageException {
		if (indexSlider == null) {
			throw new MessageException("object is null");
		}
		if (StringUtils.isNotEmpty(indexSlider.getId())) {
			indexSliderDao.update(indexSlider);
		} else {
			IndexSlider last = indexSliderDao.findNext(Order.desc("sort"), null);
			int sort = last != null ? last.getSort() + 1 : 0;
			indexSlider.setSort(sort);
			indexSlider.setCreateTime(new Date());
			indexSliderDao.insert(indexSlider);
		}
	}

	@Transactional
	@CacheEvict(value = "indexSliderCache", allEntries = true)
	public void deleteById(String id) {
		IndexSlider indexSlider = new IndexSlider();
		indexSlider.setId(id);
		indexSliderDao.delete(indexSlider);
	}

	@Transactional
	@CacheEvict(value = "indexSliderCache", allEntries = true)
	public void moveUp(String id) {
		IndexSlider indexSlider = indexSliderDao.findOne(id);
		if (indexSlider != null) {
			indexSliderDao.moveUp(Order.asc("sort"), indexSlider);
		}
	}

	@Transactional
	@CacheEvict(value = "indexSliderCache", allEntries = true)
	public void moveDown(String id) {
		IndexSlider indexSlider = indexSliderDao.findOne(id);
		if (indexSlider != null) {
			indexSliderDao.moveDown(Order.asc("sort"), indexSlider);
		}
	}

	@Transactional
	@Cacheable(value = "indexSliderCache")
	public List<IndexSlider> findAllShow() {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("isShow", true));
		List<Order> orders = new ArrayList<>();
		orders.add(Order.asc("sort"));
		return indexSliderDao.findAll(criterions, orders);
	}

}
