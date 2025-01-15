package fr.eni.ludotheque.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.JeuRepository;

@Service
public class JeuServiceImpl implements JeuService {
	
	private JeuRepository jeuRepository;
	
	public JeuServiceImpl(JeuRepository jeuRepository) {
		this.jeuRepository = jeuRepository;
	}

	@Override
	public List<Jeu> findAll() {
		return jeuRepository.findAll();
	}

	@Override
	public Optional<Jeu> findById(int id) {
		return jeuRepository.findById(id);
	}

	@Override
	public void save(Jeu jeu) {
		jeuRepository.save(jeu);
	}

	@Override
	public void delete(int id) {
		jeuRepository.delete(id);
	}

}
