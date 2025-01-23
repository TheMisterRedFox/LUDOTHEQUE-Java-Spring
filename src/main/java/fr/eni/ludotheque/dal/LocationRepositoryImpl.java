package fr.eni.ludotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.DetailLocation;
import fr.eni.ludotheque.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Location;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

	private JdbcTemplate jdbcTemplate;

	public LocationRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Location> findAll() {
		String sql = "SELECT no_location, date_debut_location, paye, prix_total, no_client, nom, prenom, email, no_tel, rue, code_postal, ville FROM locations L INNER JOIN clients C ON C.no_client = L.no_locataire";
		List<Location> locations = jdbcTemplate.query(sql, new LocationRowMapper());

		locations.forEach(location -> {
			location.setLignesLocation(this.getDetailLocations(location));
		});

		return locations;
	}

	@Override
	public Optional<Location> findById(int id) {
		String sql = "SELECT no_location, date_debut_location, paye, prix_total, no_client, nom, prenom, email, no_tel, rue, code_postal, ville FROM locations L INNER JOIN clients C ON C.no_client = L.no_locataire WHERE no_location = ?";
		Location location = jdbcTemplate.queryForObject(sql, new LocationRowMapper(), id);
		Optional<Location> optLocation = Optional.ofNullable(location);

		if(optLocation.isPresent()) {
			optLocation.get().setLignesLocation(this.getDetailLocations(optLocation.get()));
		}

		return optLocation;
	}

	@Override
	@Transactional
	public void save(Location location) {
		String sql;
		if(location.getNoLocation() == null) {
			// TODO FAIRE KEY
			sql = "INSERT INTO locations (date_debut_location, paye, prix_total, no_locataire) VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(sql, location.getDateLocation(), location.getPaye(), location.getPrixTotal(), location.getLocataire().getNoClient());

			// Insert des détails de locations après l'insert de la location
			location.getLignesLocation().forEach(detailLocation -> {
				String detailLocationInsertSql = "INSERT INTO details_locations (no_location, date_retour, tarif_location) VALUES (?, ?, ?)";
				jdbcTemplate.update(detailLocationInsertSql, location.getNoLocation(), detailLocation.getDateRetour(), detailLocation.getTarifLocation());
			});

		} else {
			sql = "UPDATE locations SET date_debut_location = ?, paye = ? , prix_total = ?, no_locataire = ? WHERE no_location = ?";
			jdbcTemplate.update(sql, location.getDateLocation(), location.getPaye(), location.getPrixTotal(), location.getLocataire().getNoClient(), location.getNoLocation());

			// On supprime les lignes de détail lié au detail_locations
			jdbcTemplate.update("DELETE FROM details_locations WHERE no_location = ?;", location.getNoLocation());

			location.getLignesLocation().forEach(detailLocation -> {
				String detailLocationUpdateSql = "INSERT INTO details_locations (no_location, date_retour, tarif_location) VALUES (?, ?, ?)";
				jdbcTemplate.update(detailLocationUpdateSql, location.getNoLocation(), detailLocation.getDateRetour(), detailLocation.getTarifLocation());
			});
		}
		
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM details_locations WHERE no_location = ?; " +
					 "DELETE FROM locations WHERE no_location = ?;";
		jdbcTemplate.update(sql, id, id);
		
	}

	private List<DetailLocation> getDetailLocations(Location location) {
		String sql = "SELECT no_ligne, date_retour, tarif_location FROM details_locations WHERE no_location = ?;";
		List<DetailLocation> detailLocations =  jdbcTemplate.query(sql, new DetailLocationRowMapper(), location.getNoLocation());

		detailLocations.forEach(detailLocation -> {
			detailLocation.setLocation(location);
		});

		return detailLocations;
	}

	class LocationRowMapper implements RowMapper<Location> {

		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setNoLocation(rs.getInt("no_location"));
			location.setDateLocation(rs.getDate("date_location"));
			location.setPaye(rs.getBoolean("paye"));
			location.setPrixTotal(rs.getFloat("prix_total"));
			Client locataire = new Client();
			locataire.setNoClient(rs.getInt("no_client"));
			locataire.setNom(rs.getString("nom"));
			locataire.setPrenom(rs.getString("prenom"));
			locataire.setEmail(rs.getString("email"));
			locataire.setNoTel(rs.getString("no_tel"));
			locataire.setRue(rs.getString("rue"));
			locataire.setCodePostal(rs.getString("code_postal"));
			locataire.setVille(rs.getString("ville"));

			location.setLocataire(locataire);

			return location;
		}

	}

	class DetailLocationRowMapper implements RowMapper<DetailLocation> {

		@Override
		public DetailLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
			DetailLocation detailLocation = new DetailLocation();
			detailLocation.setNoLigne(rs.getInt("no_ligne"));
			detailLocation.setDateRetour(rs.getDate("date_retour"));
			detailLocation.setTarifLocation(rs.getFloat("tarif_location"));

			return detailLocation;
		}

	}

}
