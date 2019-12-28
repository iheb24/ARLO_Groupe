package com.example.springbootswagger2.model;

import java.util.List;

public class Groupe {
	private String id;
	private String nom;
	private List<Eleve> eleves ;
	private int unite;
	private int sujet;

	
	public Groupe(String id, String nom, List<Eleve> eleves, int unite, int sujet) {
		super();
		this.id = id;
		this.nom = nom;
		this.eleves = eleves;
		this.unite = unite;
		this.sujet = sujet;
	}

	public Groupe(String id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}
	
	public int getUnite() {
		return unite;
	}
	public void setUnite(int unite) {
		this.unite = unite;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSujet() {
		return sujet;
	}
	public void setSujet(int sujet) {
		this.sujet = sujet;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public List<Eleve> getEleves() {
		return eleves;
	}
	public void setEleves(List<Eleve> eleves) {
		this.eleves = eleves;
	}

	@Override
	public String toString()
	{
		return this.nom;
	}

}
