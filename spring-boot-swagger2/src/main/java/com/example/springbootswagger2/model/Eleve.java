package com.example.springbootswagger2.model;

import io.swagger.annotations.ApiModelProperty;

public class Eleve {
	
	@ApiModelProperty(notes = "ID de l'eleve",name="id",required=true,value="test id")
	private String id;
	@ApiModelProperty(notes = "Nom de l'eleve",name="nom",required=true,value="test nom")
	private String nom;
	@ApiModelProperty(notes = "Prenom de l'eleve",name="prenom",required=true,value="test prenom")
	private String prenom;
	@ApiModelProperty(notes = "Prenom de l'eleve",name="prenom",required=true,value="test prenom")
	private String email;
	@ApiModelProperty(notes = "ID du groupe de l'eleve",name="idGroupe",required=true,value="test idGroupe")
	private String idGroupe;
	
	
	public Eleve(String id, String nom, String prenom, String idGroupe,String email) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.idGroupe = idGroupe;
		this.email = email;
	}

	public Eleve(String id, String nom, String prenom,String email) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getIdGroupe() {
		return idGroupe;
	}
	public void setIdGroupe(String idGroupe) {
		this.idGroupe = idGroupe;
	}
	
	/*
	public void modifierGroupe(int idGroupe) {
		this.groupe.setId(idGroupe);
	} */
}
