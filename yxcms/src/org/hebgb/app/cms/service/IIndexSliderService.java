package org.hebgb.app.cms.service;

import java.util.List;

import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.IndexSlider;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;

public interface IIndexSliderService {

	Paged<IndexSlider> findAll(IndexSlider indexSlider, Page page);

	IndexSlider findById(String id);

	void save(IndexSlider indexSlider) throws MessageException;

	void deleteById(String id);

	void moveUp(String id);

	void moveDown(String id);

	List<IndexSlider> findAllShow();

}
