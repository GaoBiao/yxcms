package org.hebgb.app.cms.service;

import java.util.List;

import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.IndexModule;

public interface IIndexModuleService {

	List<IndexModule> findAll();

	IndexModule findById(String id);

	void save(IndexModule indexModule) throws MessageException;

	void moveUp(String id) throws MessageException;

	void moveDown(String id) throws MessageException;

	void deleteById(String id);

}
