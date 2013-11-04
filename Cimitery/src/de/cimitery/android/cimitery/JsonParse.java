package de.cimitery.android.cimitery;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParse {
	
	public static Grave parseGrave(JSONObject jason) {
		
		Grave g = new Grave();
		
		try {
			g.setFirstname(jason.getString("firstname"));
			g.setLastname(jason.getString("lastname"));
			g.setDateBirth(jason.getLong("datebirth"));
			g.setDateDeath(jason.getLong("datedeath"));
			g.setSex(jason.getString("sex"));
			//g.setCemetery(jason.getString("c_id"));
			g.setLatitude(jason.getDouble("latitude"));
			g.setLongitude(jason.getDouble("longitude"));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return g;
		
	}

}
