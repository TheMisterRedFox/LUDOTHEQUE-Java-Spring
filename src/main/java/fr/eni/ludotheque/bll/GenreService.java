package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Genre;

import java.util.List;

public interface GenreService extends ICrudService<Genre> {
    List<Genre> findMultipleById(List<Integer> idList);
}
