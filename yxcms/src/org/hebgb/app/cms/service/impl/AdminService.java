package org.hebgb.app.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.hebgb.app.cms.commons.enums.ObjectStatus;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.dao.AdminDao;
import org.hebgb.app.cms.model.Admin;
import org.hebgb.app.cms.service.IAdminService;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.hebgb.web.shiro.authc.IShiroUserService;
import org.hebgb.web.shiro.authc.ShiroUser;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService implements IAdminService, IShiroUserService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private PasswordService passwordService;

	@Transactional
	public ShiroUser findByPrincipal(Object principal) {
		Admin admin = null;
		try {
			admin = adminDao.findByLoginName(principal.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

	@Transactional
	public List<String> getRoles(ShiroUser user) {
		List<String> roles = new ArrayList<>();
		if (user instanceof Admin) {
			Admin admin = (Admin) user;
			roles.add(admin.getRole().name());
		}
		return roles;
	}

	@Transactional
	public int getAllCount() {
		return adminDao.findAll().size();
	}

	@Transactional
	public long getRecordCount(Admin admin) {
		return adminDao.count1(admin);
	}

	@Transactional
	public Admin findById(String id) {
		return adminDao.findOne(id);
	}

	@Transactional
	public void save(Admin admin) throws MessageException {
		if (admin == null) {
			throw new MessageException("object is null");
		}
		if (StringUtils.isNotEmpty(admin.getId())) {
			Admin a = adminDao.findOne(admin.getId());
			a.setName(admin.getName());
			if (StringUtils.isNotEmpty(admin.getPassword())) {
				a.setPassword(passwordService.encryptPassword(admin.getPassword()));
			}
			adminDao.update(a);
		} else {
			if (StringUtils.isEmpty(admin.getLoginName())) {
				throw new MessageException("loginName is empty");
			}
			if (StringUtils.isEmpty(admin.getPassword())) {
				throw new MessageException("password is empty");
			}
			if (!checkLoginName(admin.getLoginName(), admin.getId())) {
				throw new MessageException("loginName is repeat");
			}
			admin.setPassword(passwordService.encryptPassword(admin.getPassword()));
			admin.setCreateTime(new Date());
			admin.setStatus(ObjectStatus.enabled);
			adminDao.insert(admin);
		}
	}

	private boolean checkLoginName(String loginName, String id) {
		boolean check = true;
		Admin admin = adminDao.findByLoginName(loginName);
		if (admin != null && !admin.getId().equals(id)) {
			check = false;
		}
		return check;
	}

	@Transactional
	public void delete(String id) {
		Admin admin = new Admin();
		admin.setId(id);
		adminDao.delete(admin);
	}

	@Transactional
	public Paged<Admin> findAll(Page page, Admin admin, Date startTime, Date endTime) {
		List<Order> orders = page.getOrders();
		if (orders.isEmpty()) {
			orders.add(Order.desc("createTime"));
		}
		List<Criterion> criterions = adminDao.getCriterions(admin);
		if (startTime != null) {
			criterions.add(Restrictions.ge("createTime", startTime));
		}
		if (endTime != null) {
			criterions.add(Restrictions.le("createTime", endTime));
		}
		return adminDao.findAll(page, criterions, orders);
	}

	@Transactional
	public void updateLastLoginTime(String id, Date time) {
		adminDao.updateLastLoginTime(id, time);
	}

}
