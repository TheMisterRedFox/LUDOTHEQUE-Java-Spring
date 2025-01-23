package fr.eni.ludotheque.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Client {
	private Integer noClient;
	
	@NotBlank
	@Size(max=50)
	private String nom;
	
	@NotBlank
	@Size(max=50)
	private String prenom;
	
	@Email
	@NotBlank
	private String email;
	
	private String noTel;
	
	@NotBlank
	private String rue;
	
	@NotBlank
	private String codePostal;
	
	@NotBlank
	private String ville;
	
	/*
	 * Constructeur sans numéro de tel et sans id
	 */
	public Client(String nom, String prenom, String email, String rue, String codePostal,
			String ville) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	/*
	 * Constructeur avec tous les champs sauf id
	 */ 
	public Client(String nom, String prenom, String email, String noTel, String rue, String codePostal,
			String ville) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.noTel = noTel;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
	public Client() {
		// TODO Auto-generated constructor stub
	}

	public Integer getNoClient() {
		return noClient;
	}
	public void setNoClient(Integer noClient) {
		this.noClient = noClient;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNomComplet() { return nom + " " + prenom; }
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNoTel() {
		return noTel;
	}
	public void setNoTel(String noTel) {
		this.noTel = noTel;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	@Override
	public String toString() {
		return "Client [noClient=" + noClient + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", noTel="
				+ noTel + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}
	
}
