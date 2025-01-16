package fr.eni.ludotheque.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JeuRepositoryImpl implements JeuRepository {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private GenreRepository genreRepository;

	public JeuRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, GenreRepository genreRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.genreRepository = genreRepository;
	}

	@Override
	public List<Jeu> findAll() {
		String sql = "SELECT no_jeu, titre, reference, description, tarif_journee, agemin, duree FROM JEUX";

		List<Jeu> jeux = jdbcTemplate.query(sql, new JeuRowMapper());

		jeux.forEach(jeu -> {
			List<Genre> genreLieesAJeux = genreRepository.findGenreByJeuId(jeu.getNoJeu());
			jeu.setGenres(genreLieesAJeux);
		});

		return jeux;
	}

	@Override
	public Optional<Jeu> findById(int id) {
		String sql = "SELECT no_jeu, titre, reference, description, tarif_journee, agemin, duree FROM JEUX WHERE no_jeu = ?";

		Jeu jeu = jdbcTemplate.queryForObject(sql, new JeuRowMapper(), id);
		List<Genre> genreLieesAJeux = genreRepository.findGenreByJeuId(jeu.getNoJeu());

		jeu.setGenres(genreLieesAJeux);

		Optional<Jeu> optJeu = Optional.ofNullable(jeu);

		return optJeu;

	}

	@Override
	@Transactional
	public void save(Jeu jeu) {
		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource();

		if (jeu.getNoJeu() == null) {
			sql = "INSERT INTO jeux (titre, reference, description, tarif_journee, agemin, duree) " +
					"VALUES (:titre, :reference, :description, :tarif_journee, :agemin, :duree)";

			params.addValue("titre", jeu.getTitre());
			params.addValue("reference", jeu.getReference());
			params.addValue("description", jeu.getDescription());
			params.addValue("tarif_journee", jeu.getTarifJournee());
			params.addValue("agemin", jeu.getAgeMin());
			params.addValue("duree", jeu.getDuree());

			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

			namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"no_jeu"});

			Integer jeuId = keyHolder.getKeyAs(Integer.class);
			jeu.setNoJeu(jeuId);

			for (Genre genre : jeu.getGenres()) {
				String insertGenreSql = "INSERT INTO jeux_genres (no_jeu, no_genre) VALUES (:no_jeu, :no_genre)";
				MapSqlParameterSource genreParams = new MapSqlParameterSource();
				genreParams.addValue("no_jeu", jeuId);
				genreParams.addValue("no_genre", genre.getNoGenre());
				namedParameterJdbcTemplate.update(insertGenreSql, genreParams);
			}
		} else {
			sql = "UPDATE jeux SET titre = :titre, reference = :reference, description = :description, " +
					"tarif_journee = :tarif_journee, agemin = :agemin, duree = :duree WHERE no_jeu = :no_jeu";

			params.addValue("titre", jeu.getTitre());
			params.addValue("reference", jeu.getReference());
			params.addValue("description", jeu.getDescription());
			params.addValue("tarif_journee", jeu.getTarifJournee());
			params.addValue("agemin", jeu.getAgeMin());
			params.addValue("duree", jeu.getDuree());
			params.addValue("no_jeu", jeu.getNoJeu());

			namedParameterJdbcTemplate.update(sql, params);

			// Suppression des genres qui ne sont plus associés au jeu
			List<Genre> genresLiesAJeux = genreRepository.findGenreByJeuId(jeu.getNoJeu());
			for (Genre genreLie : genresLiesAJeux) {
				if (!jeu.getGenres().contains(genreLie)) {
					String deleteSql = "DELETE FROM jeux_genres WHERE no_jeu = :no_jeu AND no_genre = :no_genre";
					MapSqlParameterSource deleteParams = new MapSqlParameterSource();
					deleteParams.addValue("no_jeu", jeu.getNoJeu());
					deleteParams.addValue("no_genre", genreLie.getNoGenre());
					namedParameterJdbcTemplate.update(deleteSql, deleteParams);
				}
			}

			// Ajout des nouveaux genres associés
			for (Genre genre : jeu.getGenres()) {
				if (!genresLiesAJeux.contains(genre)) {
					String insertSql = "INSERT INTO jeux_genres (no_jeu, no_genre) VALUES (:no_jeu, :no_genre)";
					MapSqlParameterSource insertParams = new MapSqlParameterSource();
					insertParams.addValue("no_jeu", jeu.getNoJeu());
					insertParams.addValue("no_genre", genre.getNoGenre());
					namedParameterJdbcTemplate.update(insertSql, insertParams);
				}
			}
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM jeux_genres WHERE no_jeu = ?; " + "DELETE FROM jeux WHERE no_jeu = ?";
		jdbcTemplate.update(sql, id, id);
	}

	class JeuRowMapper implements RowMapper<Jeu> {

		@Override
		public Jeu mapRow(ResultSet rs, int rowNum) throws SQLException {
			Jeu jeu = new Jeu();
			jeu.setNoJeu(rs.getInt("no_jeu"));
			jeu.setTitre(rs.getString("titre"));
			jeu.setReference(rs.getString("reference"));
			jeu.setDescription(rs.getString("description"));
			jeu.setTarifJournee(rs.getFloat("tarif_journee"));
			jeu.setAgeMin(rs.getInt("agemin"));
			jeu.setDuree(rs.getInt("duree"));

			return jeu;
		}

	}

}
