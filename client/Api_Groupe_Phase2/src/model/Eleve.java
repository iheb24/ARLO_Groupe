package model;

public class Eleve {
	private String id;
	private String nom;
	private String prenom;
	private String email;
	private int idGroupe;
	
	


	public Eleve(String id, String nom, String prenom,String email, int idGroupe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.idGroupe = idGroupe;
		this.email = email;
	}

	public Eleve(String id, String nom, String prenom, String email) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;

	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getIdGroupe() {
		return idGroupe;
	}
	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}
	
	/*
	public void modifierGroupe(int idGroupe) {
		this.groupe.setId(idGroupe);
	} */
}
