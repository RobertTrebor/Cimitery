package de.cimitery.android.cimitery;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParse {
	
	public static Grave parseGrave(JSONObject jason) {
		
		Grave grave = new Grave();
		
		try {
			grave.setGraveID(jason.getLong("g_id"));
			grave.setFirstname(jason.getString("firstname"));
			grave.setLastname(jason.getString("lastname"));
			grave.setDateBirth(jason.getLong("datebirth"));
			grave.setDateDeath(jason.getLong("datedeath"));
			grave.setSex(jason.getString("sex"));
			grave.setCemeteryID(jason.getLong("c_id"));
			grave.setLatitude(jason.getDouble("latitude"));
			grave.setLongitude(jason.getDouble("longitude"));
			grave.setGraveLoc(jason.getString("grave_loc"));
			grave.setVitaPath(jason.getString("vita_path"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return grave;
	}
	
	public static Cemetery parseCemetery(JSONObject jason) {
		
		Cemetery cem = new Cemetery();
		
		try {
			cem.setName(jason.getString("name"));
			cem.setCemeteryID(jason.getLong("c_id"));
			cem.setCity(jason.getString("city"));
			cem.setCountry(jason.getString("country"));
			cem.setStreet(jason.getString("street"));
			cem.setZipCode(jason.getString("zipcode"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return cem;
	}
	
	public static JSONObject buildGraveJson(Grave grave) {
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("firstname", grave.getFirstname());
			json.put("lastname", grave.getLastname());
			json.put("sex", grave.getSex());
			json.put("c_id", grave.getCemeteryID());
			json.put("latitude", grave.getLatitude());
			json.put("longitude", grave.getLongitude());
			
			if(grave.getDateBirth() != 0)
				json.put("datebirth", grave.getDateBirth());
			
			if(grave.getDateDeath() != 0)
				json.put("datedeath", grave.getDateDeath());
			
			if(grave.getGraveLoc() != null)
				json.put("grave_loc", grave.getGraveLoc());
			
			if(grave.getVitaPath() != null)
				json.put("vita_path", grave.getVitaPath());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}

}
