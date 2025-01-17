package fr.eni.ludotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Exemplaire;

@Repository
public class ExemplaireRepositoryImpl implements ExemplaireRepository {

	private JdbcTemplate jdbcTemplate;
	private JeuRepository jeuRepository;

	public ExemplaireRepositoryImpl(JdbcTemplate jdbcTemplate, JeuRepository jeuRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.jeuRepository = jeuRepository;
	}

	@Override
	public List<Exemplaire> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Exemplaire> findById(int id) {
		String sql = "SELECT no_exemplaire_jeu, no_jeu, codebarre, louable FROM EXEMPLAIRES_JEUX WHERE no_exemplaire_jeu = ?";

		Exemplaire exemplaire = jdbcTemplate.queryForObject(sql, new ExemplaireRowMapper(), id);

		if(exemplaire != null) {
			Optional<Jeu> jeuLie = jeuRepository.findById(exemplaire.getJeuId());
			if(jeuLie.isPresent()) {
				exemplaire.setJeu(jeuLie.get());
			}
		}

		Optional<Exemplaire> optExemplaire = Optional.ofNullable(exemplaire);

		return optExemplaire;
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

			return exemplaire;
		}

	}

}
