package fr.eni.ludotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.eni.ludotheque.bo.Genre;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public GenreRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public List<Genre> findAll() {
		String sql = "SELECT no_genre, libelle FROM genres";
		
		List<Genre> genres = jdbcTemplate.query(sql, new GenreRowMapper());
		
		return genres;
	}

	@Override
	public Optional<Genre> findById(int id) {
		String sql = "SELECT no_genre, libelle FROM genres WHERE no_genre = ?";
		
		Genre genre = jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
		Optional<Genre> optGenre = Optional.ofNullable(genre);
		
		return optGenre;
	}

	@Override
	public List<Genre> findMultipleById(List<Integer> idList) {
		// Préparer la requête SQL avec un paramètre IN dynamique
		String sql = "SELECT no_genre, libelle FROM genres WHERE no_GENRE IN (:ids)";

		// Utiliser MapSqlParameterSource pour passer la liste d'ids
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		if(idList == null || idList.size() == 0) {
			idList.add(-1);
		}
		parameters.addValue("ids", idList);

		// Exécuter la requête avec le NamedParameterJdbcTemplate
		List<Genre> genres = namedParameterJdbcTemplate.query(sql, parameters, new GenreRowMapper());

		return genres;
	}
	
	@Override
	public List<Genre> findGenreByJeuId(Integer jeuId){
		String sql = "SELECT G.no_genre AS no_genre , G.libelle AS libelle FROM genres G INNER JOIN jeux_genres JG ON JG.no_genre = G.no_genre WHERE JG.no_jeu = ?";
		
		List<Genre> genres = jdbcTemplate.query(sql, new GenreRowMapper(), jeuId);
		
		return genres;
	}

	@Override
	public void save(Genre genre) {
		String sql;
		if(genre.getNoGenre() == null) {
			sql = "INSERT INTO genres (no_genre, libelle) VALUES (?, ?)";
			//TODO A REFAIRE CA MARCHERA PAS LA
			jdbcTemplate.update(sql, genre.getNoGenre(), genre.getLibelle());
		} else {
			sql = "UPDATE genres SET no_genre = ?, libelle = ? WHERE no_client = ?";
			jdbcTemplate.update(sql, genre.getNoGenre(), genre.getLibelle(), genre.getNoGenre());
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM genres WHERE no_genre = ?";
		jdbcTemplate.update(sql, id);

	}
	
	class GenreRowMapper implements RowMapper<Genre> {

		@Override
		public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
			Genre genre = new Genre();
			genre.setNoGenre(rs.getInt("no_genre"));
			genre.setLibelle(rs.getString("libelle"));
			
			return genre;
		}
		
	}

}
