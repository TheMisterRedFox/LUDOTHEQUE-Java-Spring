package fr.eni.ludotheque.dal;

import java.util.List;

import fr.eni.ludotheque.bo.Genre;

public interface GenreRepository extends ICrudRepository<Genre> {
	
	List<Genre> findGenreByJeuId(Integer jeuId);
	
}
