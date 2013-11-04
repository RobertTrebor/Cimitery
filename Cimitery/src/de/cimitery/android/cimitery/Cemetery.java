package de.cimitery.android.cimitery;

public class Cemetery {
	
	private long cemeteryID;
	private String name;
	private String city;
	private String country;
	private String zipCode;
	private String street;
	
	
	public Cemetery(long cemeteryID, String name, String city, String country,
			String zipCode, String street) {
		this.cemeteryID = cemeteryID;
		this.name = name;
		this.city = city;
		this.country = country;
		this.zipCode = zipCode;
		this.street = street;
	}
	
	public Cemetery(long cemeteryID, String name, String country) {
		super();
		this.cemeteryID = cemeteryID;
		this.name = name;
		this.country = country;
	}
	
	
	
	////////////////////////////////////////////////////////
	///////////// GETTER + SETTER


	public long getCemeteryID() {
		return cemeteryID;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	
}
