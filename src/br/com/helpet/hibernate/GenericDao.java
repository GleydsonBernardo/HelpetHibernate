package br.com.helpet.hibernate;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.helpet.dao.Dao;
import br.com.helpet.entities.BaseEntity;

public abstract class GenericDao<Entity extends BaseEntity, EntitySearchOptions> implements Dao<Entity, EntitySearchOptions> {

	private EntityManagerFactory factory;
	private Class<Entity> entityClass;

	public GenericDao(EntityManagerFactory factory, Class<Entity> entityClass) {
		this.factory = factory;
		this.entityClass = entityClass;
	}
	
	protected void closeManager(EntityManager manager) {
		if (manager != null) {
			try {
				manager.close();
			} catch (RuntimeException e) {
			}
		}
	}
	
	protected EntityManagerFactory getFactory() {
		return factory;
	}

	@Override
	public List<Entity> retrieve() {
		return this.retrieve(null);
	}

	@Override
	public List<Entity> retrieve(EntitySearchOptions options) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Entity> criteria = builder.createQuery(entityClass);
			Root<Entity> entity = criteria.from(entityClass);

			criteria.select(entity);
			
			if (options != null) {
				Predicate predicate = buildPredicate(options, builder, entity);
				criteria.where(predicate);
			}
			
			Order orderBy = buildOrderBy(builder, entity);
			criteria.orderBy(orderBy);

			TypedQuery<Entity> query = manager.createQuery(criteria);
			
			List<Entity> result = query.getResultList();
			return result;
		} finally {
			closeManager(manager);
		}
	}

	protected abstract Order buildOrderBy(CriteriaBuilder builder, Root<Entity> entity);
	
	protected abstract Predicate buildPredicate(EntitySearchOptions options, CriteriaBuilder builder, Root<Entity> entity);


	@Override
	public void insert(final Entity entity) {
		executeNoResult(manager -> manager.persist(entity));
	}
	
	@Override
	public void update(final Entity entity) {
		executeNoResult(manager -> manager.merge(entity));
	}
	
	@Override
	public Entity find(final int id) {
		return execute(manager -> manager.find(entityClass, id));
	}

	@Override
	public void delete(final int id) {
		executeNoResult(
			manager ->  {
				Entity savedEntity = manager.find(entityClass, id);
				manager.remove(savedEntity);
    		}
		);
	}
	
	private void executeNoResult(Consumer<EntityManager> consumer) {
		execute(
			manager -> {
				consumer.accept(manager);
				return null;
			}
		);
	}
	
	private Entity execute(Function<EntityManager, Entity> func) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		try {
			manager = this.factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();

			Entity result = func.apply(manager);

			manager.flush();
			transaction.commit();

			return result;
		} catch (RuntimeException exception) {
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (RuntimeException nestedException) {}
			}
				throw exception;
		} finally {
			closeManager(manager);
		}
	}
	
	protected String toLikeParameter(String parameter) {
		return "%" + parameter.toLowerCase() + "%";
	}
}