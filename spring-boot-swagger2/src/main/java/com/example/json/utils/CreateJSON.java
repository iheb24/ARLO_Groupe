package com.example.json.utils;

import org.json.JSONObject;

public class CreateJSON {
	
		
		public static String UeJSON(String id ,String code , String intitule, float cours, float td, float tp,float valeur, String event) {

			JSONObject obj = new JSONObject();
			
			obj.put("id", id);
			obj.put("code", code);
			obj.put("intitule", intitule);
			obj.put("cours", cours);
			obj.put("td", td);
			obj.put("tp", tp);
			obj.put("valeur", valeur);
			/**/
			
			JSONObject object = new JSONObject();
			object.put("event",event);
			object.put("UE", obj);	
			return object.toString();
		}
		
		
		
		public static String eleveJSON(String id ,String prenom , String nom, String mail,String event) {

			JSONObject obj = new JSONObject();
			
			obj.put("id", id);
			obj.put("prenom", prenom);
			obj.put("nom", nom);
			obj.put("email", mail);
			obj.put("status", "eleve");
			/**/
			JSONObject object = new JSONObject();
			object.put("event",event );
			object.put("Personne", obj);
			return object.toString();
		}

	}