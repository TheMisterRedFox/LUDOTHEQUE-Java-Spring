package fr.eni.ludotheque.bo;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Exemplaire {
	
	private Integer noExemplaire;
	
	@NotBlank
	private String codeBarre;
	
	@NotNull
	private boolean louable;
	
	private Jeu jeu;
	
	public Exemplaire() {
	}

	public Exemplaire(Integer noExemplaire, @NotBlank String codeBarre, @NotNull boolean louable) {
		super();
		this.noExemplaire = noExemplaire;
		this.codeBarre = codeBarre;
		this.louable = louable;
	}
	
	public Exemplaire(@NotBlank String codeBarre, @NotNull boolean louable) {
		super();
		this.codeBarre = codeBarre;
		this.louable = louable;
	}

	public Exemplaire(@NotBlank String codeBarre, @NotNull boolean louable, Jeu jeu) {
		super();
		this.codeBarre = codeBarre;
		this.louable = louable;
		this.jeu = jeu;
	}

	public Exemplaire(Integer noExemplaire, @NotBlank String codeBarre, @NotNull boolean louable, Jeu jeu) {
		super();
		this.noExemplaire = noExemplaire;
		this.codeBarre = codeBarre;
		this.louable = louable;
		this.jeu = jeu;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public Integer getNoExemplaire() {
		return noExemplaire;
	}

	public void setNoExemplaire(Integer noExemplaire) {
		this.noExemplaire = noExemplaire;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public boolean isLouable() {
		return louable;
	}

	public void setLouable(boolean louable) {
		this.louable = louable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(noExemplaire);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exemplaire other = (Exemplaire) obj;
		return Objects.equals(noExemplaire, other.noExemplaire);
	}
	
}
