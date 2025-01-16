package fr.eni.ludotheque.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.dal.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {
	
	private GenreRepository genreRepository;
	
	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	public List<Genre> findAll() {
		return genreRepository.findAll();
	}

	@Override
	public Optional<Genre> findById(int id) {
		return genreRepository.findById(id);
	}

	@Override
	public void save(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public void delete(int id) {
		genreRepository.delete(id);
	}

	@Override
	public List<Genre> findMultipleById(List<Integer> idList) {
		return genreRepository.findMultipleById(idList);
	}
}
