package de.cimitery.android.cimitery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchLocationActivity extends Activity{
	
	
	Double latitude;
	Double longitude;
	LocationManager lm;
	LocationListener ll;
	TextView showLatitude;
	TextView showLongitude;
	String provider;
	Location location;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchlocation);
		
		showLatitude = (TextView) findViewById(R.id.latitude);
		showLongitude = (TextView) findViewById(R.id.longitude);
				
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		if(lm != null) {
			
			ll = new MyLocationListener(this);
			
			if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 5, ll);
				//Toast.makeText(getApplicationContext(), "GPS an", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "GPS aus", Toast.LENGTH_SHORT).show();
			}
			
		}
		
		Button locate = (Button) findViewById(R.id.gpsLocateButton);
		locate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				provider = LocationManager.GPS_PROVIDER;
				
				if( provider != null && !provider.equals("")) {
					//Toast.makeText(getApplicationContext(), "Provider: " + provider, Toast.LENGTH_SHORT).show();
					
					//damit man bei Start des Handys nicht total ohne Location dasteht
					location = lm.getLastKnownLocation(provider);
					
					if(location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						
						Log.d("---LOCATION---", "longitude: " + latitude + " latitude: " + longitude);
						
						showLatitude.setText("Latitude: " + latitude);
						showLongitude.setText("Longitude: " + longitude);
					}
				}
				
			}
		});
	}
	
	/*
	
	private void getProvider() {

			provider = LocationManager.GPS_PROVIDER;
						
			if( provider != null && !provider.equals("")) {
				Toast.makeText(getApplicationContext(), "Provider: " + provider, Toast.LENGTH_SHORT).show();
				
				//damit man bei Start des Handys nicht total ohne Location dasteht
				location = lm.getLastKnownLocation(provider);
				
				if(location != null) {
					LATITUDE = location.getLatitude();
					LONGITUDE = location.getLongitude();
					
					Log.d("---LOCATION---", "longitude: " + LATITUDE + " latitude: " + LONGITUDE);
				}
			}		
	}
	
	*/
	


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////   Innere Klasse   //////////////////////////////////////////////////////////////

	
	private class MyLocationListener implements LocationListener {
		
		SearchLocationActivity me;

		public MyLocationListener(SearchLocationActivity me) {
			this.me = me;
		}

		@Override
		public void onLocationChanged(Location location) {
			
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		
		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			
			if(!provider.equals("")) {
				Toast.makeText(getApplicationContext(), "DISABLED " + provider, Toast.LENGTH_SHORT).show();
			}
			Toast.makeText(getApplicationContext(), "kein Provider", Toast.LENGTH_SHORT).show();
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case R.id.action_newgrave:
	    	Log.d("onOptionsItemSelected", "NewGraveActivity.class");
	    	Intent intent1 = new Intent(this, NewGraveActivity.class);
			startActivity(intent1);
			break;
			
	    case R.id.action_searchlocation:
	    	Log.d("onOptionsItemSelected", "SearchLocationActivity.class");
	    	Intent intent2 = new Intent(this, SearchLocationActivity.class);
			startActivity(intent2);
	    	break;
	    	
	    case R.id.action_searchname:
	    	Log.d("onOptionsItemSelected", "SearchNameActivity.class");
	    	Intent intent3 = new Intent(this, SearchNameActivity.class);
			startActivity(intent3);
		      break;
		      
	    case R.id.action_finish:
	    	Log.d("onOptionsItemSelected", "finish");
	    	//Intent intent4 = new Intent(this, SearchLocationActivity.class);
			//startActivity(intent4);
		      break;

	    default:
	      break;
	    }

	    return true;
	}
	

}
