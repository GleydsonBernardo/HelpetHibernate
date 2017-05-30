package br.com.helpet.dao;

import java.util.List;

import br.com.helpet.entities.BaseEntity;

public interface Dao<T extends BaseEntity, EntitySearchOptions> {
	void insert(T t);
	void update(T t);
	void delete(int id);
	T find(int id);
	List<T> retrieve();
	List<T> retrieve(EntitySearchOptions options);
}
