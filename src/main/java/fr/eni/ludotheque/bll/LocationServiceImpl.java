package fr.eni.ludotheque.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dal.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {
	
	private LocationRepository locationRepository;

	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public List<Location> findAll() {
		return this.locationRepository.findAll();
	}

	@Override
	public Optional<Location> findById(int id) {
		return this.locationRepository.findById(id);
	}

	@Override
	public void save(Location location) {
		this.locationRepository.save(location);
	}

	@Override
	public void delete(int id) {
		this.locationRepository.delete(id);
	}

}
