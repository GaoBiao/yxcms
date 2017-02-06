package org.hebgb.app.cms.service;

import java.util.List;

import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Module;

public interface IModuleService {

	List<Module> findAll(String indent);

	Module findById(String id);

	void save(Module module) throws MessageException;

	void deleteById(String id);

	void moveUp(String id);

	void moveDown(String id);

}
