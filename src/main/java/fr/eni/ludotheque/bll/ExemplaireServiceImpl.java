package fr.eni.ludotheque.bll;

import java.util.List;
import java.util.Optional;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.bo.Exemplaire;
import org.springframework.stereotype.Service;

@Service
public class ExemplaireServiceImpl implements ExemplaireService {

	private ExemplaireRepository exemplaireRepository;

	public ExemplaireServiceImpl(ExemplaireRepository exemplaireRepository) {
		this.exemplaireRepository = exemplaireRepository;
	}

	@Override
	public List<Exemplaire> findAll() {
		return exemplaireRepository.findAll();
	}

	@Override
	public Optional<Exemplaire> findById(int id) {
		return exemplaireRepository.findById(id);
	}

	@Override
	public void save(Exemplaire exemplaire) {
		exemplaireRepository.save(exemplaire);
	}

	@Override
	public void delete(int id) {
		exemplaireRepository.delete(id);
	}

	@Override
	public List<Exemplaire> findByJeu(Jeu jeu) {
		return exemplaireRepository.findByJeu(jeu);
	}

	@Override
	public List<Exemplaire> findAllLouable() {
		return exemplaireRepository.findAllLouable();
	}
}
