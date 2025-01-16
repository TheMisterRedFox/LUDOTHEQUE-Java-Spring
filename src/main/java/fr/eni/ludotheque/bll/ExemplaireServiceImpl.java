package fr.eni.ludotheque.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Exemplaire;

@Repository
public class ExemplaireServiceImpl implements ExemplaireService {

	@Override
	public List<Exemplaire> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Exemplaire> findById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Exemplaire t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
