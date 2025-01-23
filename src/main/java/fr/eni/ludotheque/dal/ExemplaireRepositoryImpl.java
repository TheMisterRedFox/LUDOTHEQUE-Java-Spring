package fr.eni.ludotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import fr.eni.ludotheque.bo.Exemplaire;

@Repository
public class ExemplaireRepositoryImpl implements ExemplaireRepository {

	private JdbcTemplate jdbcTemplate;

	public ExemplaireRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Exemplaire> findAll() {
		String sql = "SELECT no_exemplaire_jeu, EJ.no_jeu, codebarre, louable, titre, reference, description, tarif_journee, agemin, duree FROM EXEMPLAIRES_JEUX EJ INNER JOIN JEUX J ON J.no_jeu = EJ.no_jeu";

		List<Exemplaire> exemplaires = jdbcTemplate.query(sql, new ExemplaireRowMapper());

		return exemplaires;
	}

	@Override
	public Optional<Exemplaire> findById(int id) {
		String sql = "SELECT no_exemplaire_jeu, EJ.no_jeu, codebarre, louable, titre, reference, description, tarif_journee, agemin, duree FROM EXEMPLAIRES_JEUX EJ INNER JOIN JEUX J ON J.no_jeu = EJ.no_jeu WHERE no_exemplaire_jeu = ?";

		Exemplaire exemplaire = jdbcTemplate.queryForObject(sql, new ExemplaireRowMapper(), id);
		Optional<Exemplaire> optExemplaire = Optional.ofNullable(exemplaire);

		return optExemplaire;
	}

	@Override
	public List<Exemplaire> findByJeu(Jeu jeu) {
		String sql = "SELECT no_exemplaire_jeu, EJ.no_jeu, codebarre, louable, titre, reference, description, tarif_journee, agemin, duree FROM EXEMPLAIRES_JEUX EJ INNER JOIN JEUX J ON J.no_jeu = EJ.no_jeu WHERE J.no_jeu = ?";

		List<Exemplaire> exemplaires = jdbcTemplate.query(sql, new ExemplaireRowMapper(), jeu.getNoJeu());

//		exemplaires.forEach(exemplaire -> exemplaire.setJeu(jeu));

		return exemplaires;
	}

	@Override
	public void save(Exemplaire exemplaire) {
		String sql;
		if(exemplaire.getNoExemplaire() == null) {
			sql = "INSERT INTO EXEMPLAIRES_JEUX (no_jeu, codebarre, louable) VALUES (?, ?, ?)";
			jdbcTemplate.update(sql, exemplaire.getJeu().getNoJeu(), exemplaire.getCodeBarre() ,exemplaire.isLouable());
		} else {
			sql = "UPDATE EXEMPLAIRES_JEUX SET no_jeu = ?, codebarre = ?, louable = ? WHERE no_exemplaire_jeu = ?";
			jdbcTemplate.update(sql, exemplaire.getJeu().getNoJeu(), exemplaire.getCodeBarre() ,exemplaire.isLouable(), exemplaire.getNoExemplaire());
		}

	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM EXEMPLAIRES_JEUX WHERE no_exemplaire_jeu = ?";
		jdbcTemplate.update(sql, id);
	}

	class ExemplaireRowMapper implements RowMapper<Exemplaire> {

		@Override
		public Exemplaire mapRow(ResultSet rs, int rowNum) throws SQLException {
			Exemplaire exemplaire = new Exemplaire();
			exemplaire.setNoExemplaire(rs.getInt("no_exemplaire_jeu"));
			exemplaire.setJeuId(rs.getInt("no_jeu"));
			exemplaire.setCodeBarre(rs.getString("codebarre"));
			exemplaire.setLouable(rs.getBoolean("louable"));
			Jeu jeuLie = new Jeu();
			jeuLie.setNoJeu(rs.getInt("no_jeu"));
			jeuLie.setTitre(rs.getString("titre"));
			jeuLie.setReference(rs.getString("reference"));
			jeuLie.setDescription(rs.getString("description"));
			jeuLie.setTarifJournee(rs.getFloat("tarif_journee"));
			jeuLie.setAgeMin(rs.getInt("agemin"));
			jeuLie.setDuree(rs.getInt("duree"));

			exemplaire.setJeu(jeuLie);
			return exemplaire;
		}

	}

}
