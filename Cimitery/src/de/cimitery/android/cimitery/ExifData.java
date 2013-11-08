package de.cimitery.android.cimitery;

public class ExifData {
	String LATITUDE, LATITUDE_REF, LONGITUDE, LONGITUDE_REF;
	float latitude, longitude;
	double lat, lng;
	
	public ExifData(String LATITUDE, String LATITUDE_REF, String LONGITUDE, String LONGITUDE_REF) {
		this.LATITUDE=LATITUDE;
		this.LATITUDE_REF=LATITUDE_REF;
		this.LONGITUDE=LONGITUDE;
		this.LONGITUDE_REF=LONGITUDE_REF;
	}

	public void convertFormat() {
		if ((LATITUDE != null) && (LATITUDE_REF != null)
				&& (LONGITUDE != null)
				&& (LONGITUDE_REF != null)) {

			if (LATITUDE_REF.equals("N")) {
				latitude = convertToDegree(LATITUDE);
			}

			else {
				latitude = 0 - convertToDegree(LATITUDE);
			}

			if (LONGITUDE_REF.equals("E")) {
				longitude = convertToDegree(LONGITUDE);
			} else {
				longitude = 0 - convertToDegree(LONGITUDE);
			}

		}
	}

	public float convertToDegree(String stringDMS) {
		Float result = null;
		String[] DMS = stringDMS.split(",", 3);

		String[] stringD = DMS[0].split("/", 2);
		Double D0 = new Double(stringD[0]);
		Double D1 = new Double(stringD[1]);
		Double FloatD = D0 / D1;

		String[] stringM = DMS[1].split("/", 2);
		Double M0 = new Double(stringM[0]);
		Double M1 = new Double(stringM[1]);
		Double FloatM = M0 / M1;

		String[] stringS = DMS[2].split("/", 2);
		Double S0 = new Double(stringS[0]);
		Double S1 = new Double(stringS[1]);
		Double FloatS = S0 / S1;

		result = new Float(FloatD + (FloatM / 60) + (FloatS / 3600));

		return result;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}
	
	
}
