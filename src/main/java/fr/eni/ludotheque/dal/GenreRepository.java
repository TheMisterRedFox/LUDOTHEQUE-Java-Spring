package fr.eni.ludotheque.dal;

import java.util.List;

import fr.eni.ludotheque.bo.Genre;

public interface GenreRepository extends ICrudRepository<Genre> {

    List<Genre> findMultipleById(List<Integer> idList);

    List<Genre> findGenreByJeuId(Integer jeuId);
	
}
