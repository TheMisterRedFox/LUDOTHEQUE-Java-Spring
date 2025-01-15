package fr.eni.ludotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Client;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

	private JdbcTemplate jdbcTemplate;

	public ClientRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Client> findAll() {
		String sql = "SELECT no_client, nom, prenom, email, no_tel, rue, code_postal, ville FROM clients";
		
		List<Client> clients = jdbcTemplate.query(sql, new ClientRowMapper());
		
		return clients;
	}

	@Override
	public Optional<Client> findById(int id) {
		String sql = "SELECT no_client, nom, prenom, email, no_tel, rue, code_postal, ville FROM clients WHERE no_client = ?";
		
		Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(), id);
		Optional<Client> optClient = Optional.ofNullable(client);
		
		return optClient;
	}

	@Override
	public void save(Client client) {
		String sql;
		if(client.getNoClient() == null) {
			sql = "INSERT INTO clients (nom, prenom, email, no_tel, rue, code_postal, ville) VALUES (?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql, client.getNom(), client.getPrenom(), client.getEmail(), client.getNoTel(), client.getRue(), client.getCodePostal(), client.getVille());
		} else {
			sql = "UPDATE clients SET nom = ?, prenom = ?, email = ?, no_tel = ?, rue = ?, code_postal = ?, ville = ? WHERE no_client = ?";
			jdbcTemplate.update(sql, client.getNom(), client.getPrenom(), client.getEmail(), client.getNoTel(), client.getRue(), client.getCodePostal(), client.getVille(), client.getNoClient());
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM clients WHERE no_client = ?";
		jdbcTemplate.update(sql, id);
	}
	
	class ClientRowMapper implements RowMapper<Client> {

		@Override
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			Client client = new Client();
			client.setNoClient(rs.getInt("no_client"));
			client.setNom(rs.getString("nom"));
			client.setPrenom(rs.getString("prenom"));
			client.setEmail(rs.getString("email"));
			client.setNoTel(rs.getString("no_tel"));
			client.setRue(rs.getString("rue"));
			client.setCodePostal(rs.getString("code_postal"));
			client.setVille(rs.getString("ville"));
			
			return client;
		}
		
	}

}
