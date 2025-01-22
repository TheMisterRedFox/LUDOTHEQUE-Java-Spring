package fr.eni.ludotheque.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Location;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

	@Override
	public List<Location> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Location> findById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Location t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}
