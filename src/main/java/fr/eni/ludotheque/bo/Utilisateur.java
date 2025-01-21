package fr.eni.ludotheque.bo;

import java.util.List;

public class Utilisateur {

	private Integer noUtilisateur;
	private String nomUtilisateur;
	private String motDePasse;
	private List<String> roles;
	
	public Utilisateur(String nomUtilisateur, String motDePasse, List<String> roles) {
		super();
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
		this.roles = roles;
	}

	public Utilisateur(Integer noUtilisateur, String nomUtilisateur, String motDePasse, List<String> roles) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
		this.roles = roles;
	}

	public Utilisateur() {
		super();
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
}
