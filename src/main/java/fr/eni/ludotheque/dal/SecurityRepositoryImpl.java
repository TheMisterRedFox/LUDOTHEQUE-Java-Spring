package fr.eni.ludotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import fr.eni.ludotheque.bo.Utilisateur;

@Repository
public class SecurityRepositoryImpl implements SecurityRepository {

	JdbcTemplate jdbcTemplate;

	public SecurityRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Utilisateur> findUserByLoginArgs(String username) {
		String sql = "SELECT no_utilisateur, pseudo_utilisateur, mot_de_passe FROM UTILISATEURS WHERE pseudo_utilisateur = ?";

		Utilisateur utilisateur = jdbcTemplate.queryForObject(sql, new UtilisateurRowMapper(), username);
		Optional<Utilisateur> optUtilisateur = Optional.ofNullable(utilisateur);

		if(optUtilisateur.isPresent()) {
			sql = "SELECT R.nom_role AS nom_role FROM ROLES R INNER JOIN UTILISATEURS_ROLES UR ON UR.nom_role = R.nom_role WHERE UR.no_utilisateur = ?";
			List<String> listeRoles = jdbcTemplate.query(sql, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("nom_role");
				}
			}, optUtilisateur.get().getNoUtilisateur());

			optUtilisateur.get().setRoles(listeRoles);
		}

		return optUtilisateur;
	}

	class UtilisateurRowMapper implements RowMapper<Utilisateur> {

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
			utilisateur.setNomUtilisateur(rs.getString("pseudo_utilisateur"));
			utilisateur.setMotDePasse(rs.getString("mot_de_passe"));

			return utilisateur;
		}

	}

}
