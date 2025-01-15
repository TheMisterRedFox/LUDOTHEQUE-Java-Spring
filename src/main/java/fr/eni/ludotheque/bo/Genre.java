package fr.eni.ludotheque.bo;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Genre {
	
	@NotNull
	private Integer noGenre;
	
	@NotBlank
	private String libelle;
	
	public Genre() {
	}

	public Genre(String libelle) {
		this.libelle = libelle;
	}

	public Integer getNoGenre() {
		return noGenre;
	}

	public void setNoGenre(Integer noGenre) {
		this.noGenre = noGenre;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Genre [noGenre=" + noGenre + ", libelle=" + libelle + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(noGenre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		return Objects.equals(noGenre, other.noGenre);
	}
	
	
}
