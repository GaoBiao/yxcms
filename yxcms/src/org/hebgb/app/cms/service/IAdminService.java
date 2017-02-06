package org.hebgb.app.cms.service;

import java.util.Date;

import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Admin;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;

public interface IAdminService {

	int getAllCount();

	long getRecordCount(Admin admin);

	Admin findById(String id);

	void save(Admin admin) throws MessageException;

	void delete(String id);

	Paged<Admin> findAll(Page page, Admin admin, Date startTime, Date endTime);

	void updateLastLoginTime(String id, Date time);
}
