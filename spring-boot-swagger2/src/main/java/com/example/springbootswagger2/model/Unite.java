package com.example.springbootswagger2.model;

public class Unite {
	private String id;
	private String nom;
	private String code;
	private float cours;
	private float  td;
	private float  tp;
	private float  valeur;
	private String description;
	
	
	public Unite(String id, String nom, String description, String code,float cours,float td, float tp, float valeur) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.code = code;
		this.cours = cours;
		this.tp = tp;
		this.td = td;
		this.valeur = valeur;
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
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getCours() {
		return cours;
	}

	public void setCours(float cours) {
		this.cours = cours;
	}

	public float getTd() {
		return td;
	}

	public void setTd(float td) {
		this.td = td;
	}

	public float getTp() {
		return tp;
	}

	public void setTp(float tp) {
		this.tp = tp;
	}

	public float getValeur() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur = valeur;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString()
	{
		return this.nom;
	}

}
