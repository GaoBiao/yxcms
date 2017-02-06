package org.hebgb.app.cms.dao;

import java.io.Serializable;
import java.util.List;

import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<T, PK extends Serializable> {
	private final Class<T> clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public abstract List<Criterion> getCriterions(T object);

	protected Criteria createCriteria() {
		return currentSession().createCriteria(clazz);
	}

	public void insert(T object) {
		currentSession().save(object);
	}

	public void update(T object) {
		currentSession().saveOrUpdate(object);
	}

	public void delete(T object) {
		currentSession().delete(object);
	}

	public T findOne(List<Criterion> criterions) {
		return findOne(criterions, null);
	}

	@SuppressWarnings("unchecked")
	public T findOne(List<Criterion> criterions, List<Order> orders) {
		Criteria ca = createCriteria();
		if (criterions != null && !criterions.isEmpty()) {
			for (Criterion criterion : criterions) {
				ca.add(criterion);
			}
		}
		if (orders != null && !orders.isEmpty()) {
			for (Order order : orders) {
				ca.addOrder(order);
			}
		}
		return (T) ca.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T findOne(PK id) {
		return (T) currentSession().get(clazz, id);
	}

	public Paged<T> findAll1(Page page, T object, List<Order> orders) {
		return findAll(page, getCriterions(object), orders);
	}

	public List<T> findAll1(T object, List<Order> orders) {
		return findAll(getCriterions(object), orders, null, null);
	}

	public List<T> findAll1(T object, List<Order> orders, Integer firstResult, Integer maxResults) {
		return findAll(getCriterions(object), orders, firstResult, maxResults);
	}

	public Paged<T> findAll(Page page, List<Criterion> criterions, List<Order> orders) {
		Paged<T> paged = new Paged<>(page);
		paged.setTotalItem(count(criterions));
		paged.setContent(findAll(criterions, orders, page.getPage() * page.getSize(), page.getSize()));
		return paged;
	}

	public int count1(T object) {
		return count(getCriterions(object));
	}

	public int count() {
		return count(null);
	}

	public int count(List<Criterion> criterions) {
		Criteria ca = createCriteria();
		if (criterions != null && !criterions.isEmpty()) {
			for (Criterion criterion : criterions) {
				ca.add(criterion);
			}
		}
		return Integer.parseInt(ca.setProjection(Projections.rowCount()).uniqueResult().toString());
	}

	public List<T> findAll() {
		return findAll(null, null, null, null);
	}

	public List<T> findAll(List<Criterion> criterions, List<Order> orders) {
		return findAll(criterions, orders, null, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(List<Criterion> criterions, List<Order> orders, Integer firstResult, Integer maxResults) {
		Criteria ca = createCriteria();
		if (criterions != null && !criterions.isEmpty()) {
			for (Criterion criterion : criterions) {
				ca.add(criterion);
			}
		}
		if (orders != null && !orders.isEmpty()) {
			for (Order order : orders) {
				ca.addOrder(order);
			}
		}
		if (firstResult != null) {
			ca.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			ca.setMaxResults(maxResults);
		}
		return ca.list();
	}

	public void moveUp(Order order, T object) {
		moveUp(order, object, null);
	}

	public void moveUp(Order order, T object, List<Criterion> criterions) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(object);
		String propertyName = order.getPropertyName();
		Object value = beanWrapper.getPropertyValue(propertyName);
		T prev = findPrev(order, value, criterions);
		if (prev != null) {
			BeanWrapper prevWrapper = new BeanWrapperImpl(prev);
			Object prevValue = prevWrapper.getPropertyValue(propertyName);
			beanWrapper.setPropertyValue(propertyName, prevValue);
			prevWrapper.setPropertyValue(propertyName, value);
			update(object);
			update(prev);
		}
	}

	public void moveDown(Order order, T object) {
		moveDown(order, object, null);
	}

	public void moveDown(Order order, T object, List<Criterion> criterions) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(object);
		String propertyName = order.getPropertyName();
		Object value = beanWrapper.getPropertyValue(propertyName);
		T next = findNext(order, value, criterions);
		if (next != null) {
			BeanWrapper nextWrapper = new BeanWrapperImpl(next);
			Object prevValue = nextWrapper.getPropertyValue(propertyName);
			beanWrapper.setPropertyValue(propertyName, prevValue);
			nextWrapper.setPropertyValue(propertyName, value);
			update(object);
			update(next);
		}
	}

	public T findNext(Order order, Object value) {
		return findNext(order, value, null);
	}

	@SuppressWarnings("unchecked")
	public T findNext(Order order, Object value, List<Criterion> criterions) {
		Criteria ca = createCriteria();
		if (value != null) {
			if (order.isAscending()) {
				ca.add(Restrictions.gt(order.getPropertyName(), value));
			} else {
				ca.add(Restrictions.lt(order.getPropertyName(), value));
			}
		}
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				ca.add(criterion);
			}
		}
		ca.addOrder(order);
		ca.setMaxResults(1);
		return (T) ca.uniqueResult();
	}

	public T findPrev(Order order, Object value) {
		return findPrev(order, value, null);
	}

	@SuppressWarnings("unchecked")
	public T findPrev(Order order, Object value, List<Criterion> criterions) {
		Criteria ca = createCriteria();
		Order _order = null;
		if (value != null) {
			if (order.isAscending()) {
				ca.add(Restrictions.lt(order.getPropertyName(), value));
				_order = Order.desc(order.getPropertyName());
			} else {
				ca.add(Restrictions.gt(order.getPropertyName(), value));
				_order = Order.asc(order.getPropertyName());
			}
		}
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				ca.add(criterion);
			}
		}
		ca.addOrder(_order);
		ca.setMaxResults(1);
		return (T) ca.uniqueResult();
	}

}
