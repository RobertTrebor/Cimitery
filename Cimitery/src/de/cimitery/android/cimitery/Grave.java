package de.cimitery.android.cimitery;

import java.util.Date;

public class Grave {
	
	private long graveID;
	private String firstname;
	private String lastname;
	private String sex;
	private long dateBirth;
	private long dateDeath;
	private long cemeteryID;
	private String graveLoc;
	private double latitude;
	private double longitude;
	private String vitaPath;
	
	public Grave() {
		
	}

	
	public Grave(String firstname, String lastname, long cemeteryID) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.cemeteryID = cemeteryID;
	}



	////////////////////////////////////////////////////////
	///////////// GETTER + SETTER

	public long getGraveID() {
		return graveID;
	}

	public void setGraveID(long graveID) {
		this.graveID = graveID;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public long getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(long dateBirth) {
		this.dateBirth = dateBirth;
	}

	public long getDateDeath() {
		return dateDeath;
	}

	public void setDateDeath(long dateDeath) {
		this.dateDeath = dateDeath;
	}

	public long getCemeteryID() {
		return cemeteryID;
	}

	public void setCemeteryID(long cemeteryID) {
		this.cemeteryID = cemeteryID;
	}

	public String getGraveLoc() {
		return graveLoc;
	}

	public void setGraveLoc(String graveLoc) {
		this.graveLoc = graveLoc;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getVitaPath() {
		return vitaPath;
	}

	public void setVitaPath(String vitaPath) {
		this.vitaPath = vitaPath;
	}
	

}
