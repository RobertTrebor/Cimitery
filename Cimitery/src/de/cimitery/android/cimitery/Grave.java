package de.cimitery.android.cimitery;

import java.util.Date;

public class Grave {
	
	private long graveID;
	private String firstname;
	private String lastname;
	private String sex;
	private Date dateBirth;
	private Date dateDeath;
	private Cemetery cemetery;
	private String graveLoc;
	private double latitude;
	private double longitude;
	private String vitaPath;
	
	
	public Grave(long graveID, String firstname, String lastname, String sex,
			Date dateBirth, Date dateDeath, Cemetery cemetery, String graveLoc,
			double latitude, double longitude, String vitaPath) {
		this.graveID = graveID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.sex = sex;
		this.dateBirth = dateBirth;
		this.dateDeath = dateDeath;
		this.cemetery = cemetery;
		this.graveLoc = graveLoc;
		this.latitude = latitude;
		this.longitude = longitude;
		this.vitaPath = vitaPath;
	}


	public Grave(long graveID, String firstname, String lastname, String sex,
			Cemetery cemetery, double latitude, double longitude) {
		this.graveID = graveID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.sex = sex;
		this.cemetery = cemetery;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	////////////////////////////////////////////////////////
	///////////// GETTER + SETTER


	public long getGraveID() {
		return graveID;
	}


	public String getFirstname() {
		return firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public String getSex() {
		return sex;
	}


	public Date getDateBirth() {
		return dateBirth;
	}


	public Date getDateDeath() {
		return dateDeath;
	}


	public Cemetery getCemetery() {
		return cemetery;
	}


	public String getGraveLoc() {
		return graveLoc;
	}


	public double getLatitude() {
		return latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public String getVitaPath() {
		return vitaPath;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}


	public void setDateDeath(Date dateDeath) {
		this.dateDeath = dateDeath;
	}


	public void setCemetery(Cemetery cemetery) {
		this.cemetery = cemetery;
	}


	public void setGraveLoc(String graveLoc) {
		this.graveLoc = graveLoc;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public void setVitaPath(String vitaPath) {
		this.vitaPath = vitaPath;
	}
	


}
