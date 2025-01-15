package fr.eni.ludotheque.dal;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository<T> {
	
	List<T> findAll();
	
	Optional<T> findById(int id);
	
	void save(T t);
	
	void delete(int id);
}
