package org.hebgb.app.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.dao.ModuleDao;
import org.hebgb.app.cms.model.Module;
import org.hebgb.app.cms.service.IModuleService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModuleService implements IModuleService {
	@Autowired
	private ModuleDao moduleDao;

	@Transactional
	@Cacheable(value = "moduleCache", key = "#indent")
	public List<Module> findAll(String indent) {
		List<Order> orders = new ArrayList<>();
		orders.add(Order.asc("parentId"));
		orders.add(Order.asc("sort"));
		List<Module> modules = moduleDao.findAll(null, orders);
		if (modules != null && !modules.isEmpty()) {
			Module root = new Module();
			root.setId("");
			root.setIndent("");
			return children(root, modules, indent);
		}
		return null;
	}

	private List<Module> children(Module parent, List<Module> modules, String indent) {
		List<Module> list = new ArrayList<>();
		for (Module module : modules) {
			if (parent.getId().equals(module.getParentId())) {
				module.setIndent(indent + parent.getIndent());
				list.add(module);
				list.addAll(children(module, modules, indent));
			}
		}
		return list;
	}

	@Transactional
	public Module findById(String id) {
		return moduleDao.findOne(id);
	}

	@Transactional
	@CacheEvict(value = "moduleCache", allEntries = true)
	public void save(Module module) throws MessageException {
		if (module == null) {
			throw new MessageException("object is null");
		}
		if (StringUtils.isEmpty(module.getName())) {
			throw new MessageException("模块名称为空");
		}
		if (StringUtils.isNotEmpty(module.getId())) {
			if (module.getIsMenu() && StringUtils.isEmpty(module.getLinkUrl())) {
				module.setLinkUrl("/article/list.do?moduleId=" + module.getId());
			}
			moduleDao.update(module);
		} else {
			Module last = moduleDao.findLast(module.getParentId());
			int sort = 0;
			if (last != null) {
				sort = last.getSort() + 1;
			}
			if (module.getIsMenu() && StringUtils.isEmpty(module.getLinkUrl())) {
				String id = UUID.randomUUID().toString();
				module.setId(id);
				module.setLinkUrl("/article/list.do?moduleId=" + id);
			}
			module.setSort(sort);
			module.setCreateTime(new Date());
			moduleDao.insert(module);
		}
	}

	@Transactional
	@CacheEvict(value = "moduleCache", allEntries = true)
	public void deleteById(String id) {
		Module module = new Module();
		module.setId(id);
		moduleDao.delete(module);
	}

	@Transactional
	@CacheEvict(value = "moduleCache", allEntries = true)
	public void moveUp(String id) {
		Module module = moduleDao.findOne(id);
		if (module != null) {
			List<Criterion> criterions = new ArrayList<>();
			criterions.add(Restrictions.eq("parentId", module.getParentId()));
			moduleDao.moveUp(Order.asc("sort"), module, criterions);
		}
	}

	@Transactional
	@CacheEvict(value = "moduleCache", allEntries = true)
	public void moveDown(String id) {
		Module module = moduleDao.findOne(id);
		if (module != null) {
			List<Criterion> criterions = new ArrayList<>();
			criterions.add(Restrictions.eq("parentId", module.getParentId()));
			moduleDao.moveDown(Order.asc("sort"), module, criterions);
		}
	}

}
