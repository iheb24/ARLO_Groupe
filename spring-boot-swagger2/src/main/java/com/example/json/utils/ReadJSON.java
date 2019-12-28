package com.example.json.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.springbootswagger2.model.Eleve;
import com.example.springbootswagger2.model.Unite;

public class ReadJSON {
	
	
	public static boolean isJSONValid(String test) {
	    try {
	        new JSONObject(test);
	    	} catch (JSONException ex) {
	       
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static String JSONevent(String msg) {

		String res = "";
		try {
			JSONObject obj = new JSONObject(msg);
			res = obj.getString("event");

		} catch (JSONException e)
		{
			return res;
		}
		return res;
	}
	
	public static String classJSON(String msg) {

		String res="";
		JSONObject obj = new JSONObject(msg);
		if (obj.has("UE"))
		{
			res = "UE";
		}
		else if (obj.has("Personne"))
		{
			res = "Personne";
		}

		return res;
	}
	
	public static Eleve eleveJSON(String msg) {
		Eleve z;
		try {
			JSONObject obj = new JSONObject(msg);
			JSONObject x = obj.getJSONObject("Personne");
			if (x.getString("status").equals("eleve"))
			 z = new Eleve(x.getString("id"),x.getString("nom"),x.getString("prenom"),x.getString("email"));
			else
				return null;
			
		}catch (JSONException e)
		{
			return null;
		}
		
		return z;
		
	}
	
	public static Unite ueJSON(String msg) {
		Unite z;
		try {
			JSONObject obj = new JSONObject(msg);
			JSONObject x = obj.getJSONObject("UE");
			 z = new Unite(x.getString("id"), x.getString("intitule"), "xx", x.getString("code"), x.getFloat("cours"), x.getFloat("td"), x.getFloat("tp"), x.getFloat("valeur"));
		} catch (JSONException e)
		{
			return null;
		}
		
		return z;
	}
	
	

}
