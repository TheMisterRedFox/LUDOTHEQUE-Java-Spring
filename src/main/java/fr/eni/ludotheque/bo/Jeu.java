package fr.eni.ludotheque.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Jeu {
	
	@NotNull
	private Integer noJeu;
	
	@NotBlank
	private String titre;
	
	@NotBlank
	private String reference;
	
	private String description;
	
	@NotNull
	@Min(0)
	private float tarifJournee;
	
	@NotNull
	@Min(0)
	private int ageMin;
	
	private int duree;
	
	private List<Genre> genres;
	
	public Jeu() {
		super();
		this.genres = new ArrayList<Genre>();
	}
	
	public Jeu(@NotNull Integer noJeu, @NotBlank String titre, @NotBlank String reference, String description,
			@NotNull @Min(0) float tarifJournee, @NotNull @Min(0) int ageMin, int duree) {
		this();
		this.genres = new ArrayList<Genre>();
		this.noJeu = noJeu;
		this.titre = titre;
		this.reference = reference;
		this.description = description;
		this.tarifJournee = tarifJournee;
		this.ageMin = ageMin;
		this.duree = duree;
	}

	public Jeu(@NotBlank String titre, @NotBlank String reference, String description,
			@NotNull @Min(0) float tarifJournee, @NotNull @Min(0) int ageMin, int duree) {
		this();
		this.titre = titre;
		this.reference = reference;
		this.description = description;
		this.tarifJournee = tarifJournee;
		this.ageMin = ageMin;
		this.duree = duree;
	}
	
	public Jeu(@NotBlank String titre, @NotBlank String reference, @NotNull @Min(0) float tarifJournee,
			@NotNull @Min(0) int ageMin) {
		this();
		this.titre = titre;
		this.reference = reference;
		this.tarifJournee = tarifJournee;
		this.ageMin = ageMin;
	}

	public Integer getNoJeu() {
		return noJeu;
	}

	public void setNoJeu(Integer noJeu) {
		this.noJeu = noJeu;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getTarifJournee() {
		return tarifJournee;
	}
	
	public String getTarifJourneeToString() {
		return String.format("%.2f", getTarifJournee());
	}

	public void setTarifJournee(float tarifJournee) {
		this.tarifJournee = tarifJournee;
	}

	public int getAgeMin() {
		return ageMin;
	}

	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}
	

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	
	public void addGenre(Genre genre) {
		this.genres.add(genre);
	}
	
	public void removeGenre(Genre genre) {
		if(this.genres.contains(genre)) {
			this.genres.remove(genre);
		}
	}
	
	public String getGenresToString() {
		List<String> genresList = new ArrayList<String>();
		
		this.getGenres().forEach(genre -> {
			genresList.add(genre.getLibelle());
		});	
		
		return String.join(", ", genresList);
	}

	@Override
	public String toString() {
		return "Jeu [noJeu=" + noJeu + ", titre=" + titre + ", reference=" + reference + ", description=" + description
				+ ", tarifJournee=" + tarifJournee + ", ageMin=" + ageMin + ", duree=" + duree + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(noJeu);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jeu other = (Jeu) obj;
		return Objects.equals(noJeu, other.noJeu);
	}
	
	
	
}
