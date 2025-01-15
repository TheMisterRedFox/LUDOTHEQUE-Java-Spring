package fr.eni.ludotheque.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;

@Repository
public class JeuRepositoryImpl implements JeuRepository {

	private JdbcTemplate jdbcTemplate;
	private GenreRepository genreRepository;

	public JeuRepositoryImpl(JdbcTemplate jdbcTemplate, GenreRepository genreRepository) {
		this.jdbcTemplate = jdbcTemplate;
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
	public void save(Jeu jeu) {
		String sql;
		if (jeu.getNoJeu() == null) {
			// Si le jeu n'existe pas encore, on l'insère
			sql = "INSERT INTO jeux (titre, reference, description, tarif_journee, agemin, duree) VALUES (?, ?, ?, ?, ?, ?)";

			// Utilisation d'un KeyHolder pour récupérer l'ID généré
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

			// Exécution de l'insertion avec le KeyHolder pour récupérer l'ID
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, jeu.getTitre());
				ps.setString(2, jeu.getReference());
				ps.setString(3, jeu.getDescription());
				ps.setFloat(4, jeu.getTarifJournee());
				ps.setInt(5, jeu.getAgeMin());
				ps.setInt(6, jeu.getDuree());
				return ps;
			}, keyHolder);

			Integer jeuId = keyHolder.getKey().intValue();
			jeu.setNoJeu(jeuId);

			List<Genre> genres = jeu.getGenres();
			for (Genre genre : genres) {
				String insertSql = "INSERT INTO jeux_genres (no_jeu, no_genre) VALUES (?, ?)";
				jdbcTemplate.update(insertSql, jeuId, genre.getNoGenre());
			}
		} else {
			sql = "UPDATE jeux SET titre = ?, reference = ?, description = ?, tarif_journee = ?, agemin = ?, duree = ? WHERE no_jeu = ?";
			jdbcTemplate.update(sql, jeu.getTitre(), jeu.getReference(), jeu.getDescription(), jeu.getTarifJournee(),
					jeu.getAgeMin(), jeu.getDuree(), jeu.getNoJeu());

			List<Genre> genresLiesAJeux = genreRepository.findGenreByJeuId(jeu.getNoJeu());

			// Supprimer les genres qui ne sont plus associés à ce jeu
			for (Genre genreLie : genresLiesAJeux) {
				if (!jeu.getGenres().contains(genreLie)) {
					// Supprimer la relation entre le jeu et ce genre
					String deleteSql = "DELETE FROM jeux_genres WHERE no_jeu = ? AND no_genre = ?";
					jdbcTemplate.update(deleteSql, jeu.getNoJeu(), genreLie.getNoGenre());
				}
			}

			// Ajout des nouveaux genres qui ne sont pas encore associés à ce jeu
			for (Genre genre : jeu.getGenres()) {
				if (!genresLiesAJeux.contains(genre)) {
					String insertSql = "INSERT INTO jeux_genres (no_jeu, no_genre) VALUES (?, ?)";
					jdbcTemplate.update(insertSql, jeu.getNoJeu(), genre.getNoGenre());
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
