package com.example.springbootswagger2.model;

public class UeJSON {
	private String id;
	private String nom;
	private String code;
	private float cours;
	private float  td;
	private float  tp;
	private float  valeur;
	private String description;
	
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
	
}
