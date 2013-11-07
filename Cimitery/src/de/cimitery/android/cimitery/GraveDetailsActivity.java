package de.cimitery.android.cimitery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GraveDetailsActivity extends Activity implements OnClickListener{
	
	String vitaPath;
	String tombstonePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gravedetails);
		
		TextView firstname = (TextView) findViewById(R.id.tf_firstname);
		TextView lastname = (TextView) findViewById(R.id.tf_lastname);
		//TextView dateBirth = (TextView) findViewById(R.id.tfDateBirth);
		//TextView dateDeath = (TextView) findViewById(R.id.tfDateDeath);
		TextView latitude = (TextView) findViewById(R.id.tf_latitude);
		TextView longitude = (TextView) findViewById(R.id.tf_longitude);

		//Button linkButton = (Button) findViewById(R.id.buttonVitaLink);
		
		if(getIntent() != null ){
			long g_id = getIntent().getLongExtra("g_id", 1); //noch zu implementieren
			String sex = getIntent().getStringExtra("sex");  //noch zu implementieren
			firstname.setText(getIntent().getStringExtra("firstname"));
			String testfn = getIntent().getStringExtra("firstname");
			Log.d("GraveDetails", testfn);
			lastname.setText(getIntent().getStringExtra("lastname"));
			//dateBirth.setText(getIntent().getStringExtra("dateBirth"));
			//dateDeath.setText(getIntent().getStringExtra("dateDeath"));
			longitude.setText("Longitude: " + getIntent().getDoubleExtra("longitude", 1));
			latitude.setText("Latitude: " + getIntent().getDoubleExtra("latitude", 1));
		
			vitaPath = getIntent().getStringExtra("vitaPath");
			tombstonePath = getIntent().getStringExtra("tombstonePath");
		}
		
		//linkButton.setOnClickListener(this);

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
	    	Finisher.finishCimitery(this);
		    break;

	    default:
	      break;
	    }

	    return true;
	}

	@Override
	public void onClick(View v) {
		Intent intentWebView = new Intent(this, WebViewActivity.class);
		intentWebView.putExtra("link", vitaPath);
		startActivity(intentWebView);
	}




}
