package de.cimitery.android.cimitery;

import java.io.IOException;
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class NewGraveActivity extends Activity{

	EditText firstname;
	EditText lastname;
	RadioButton radioButton;
	
	Grave grave = new Grave();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgrave);
		
		firstname = (EditText) findViewById(R.id.editInFirstname);
		lastname = (EditText) findViewById(R.id.editInLastname);
		
		Spinner spinnerCem = (Spinner) findViewById(R.id.spinnerCemetery);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.cemeteryNames, 
				android.R.layout.simple_spinner_item);
		spinnerCem.setAdapter(spinnerAdapter);
		
		spinnerCem.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				grave.setCemeteryID(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
								
			}
		});
		
		
		
		Button selectPhoto = (Button) findViewById(R.id.buttonSelectPhoto);
		Button button = (Button) findViewById(R.id.buttonNewGrave);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Daten für Grab eintragen:
				
				setAllGraveData();
				
				Runnable r = new Runnable() {
					
					@Override
					public void run() {
						Log.d("NEWGRAVEACT", "Am Anfang von run");
						
						HttpClient httpclient = new DefaultHttpClient();
					    HttpPost httppost = new HttpPost("http://www.lengsfeld.de/cimitery/insert.php");

					    try {
					        // Add your data
					        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					        nameValuePairs.add(new BasicNameValuePair("firstname", grave.getFirstname()));
					        nameValuePairs.add(new BasicNameValuePair("lastname", grave.getLastname()));
					        nameValuePairs.add(new BasicNameValuePair("sex", grave.getSex()));
					        nameValuePairs.add(new BasicNameValuePair("c_id", String.valueOf(grave.getCemeteryID())));
					        nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(grave.getLatitude())));
					        nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(grave.getLongitude())));
					        nameValuePairs.add(new BasicNameValuePair("tombstone_path", grave.getTombstonePath()));
					        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					        Log.d("NEWGRAVEACT", "httppost.setEntity");

					        // Execute HTTP Post Request
					        
					        HttpResponse response = httpclient.execute(httppost);
					        Log.d("NEWGRAVEACT", response.toString());
					        
					        
					        Log.d("NEWGRAVEACT", "Am Ende des try im Runnable");
					    } catch (ClientProtocolException e) {
					    	e.printStackTrace();
					    } catch (IOException e) {
					    	e.printStackTrace();
					    }
						
					}
				};
				
				(new Thread(r)).start();
				
			}
		});
	}


	protected void setAllGraveData() {
		grave.setFirstname(firstname.getText().toString());
		grave.setLastname(lastname.getText().toString());
		grave.setSex(radioButtonToChar(radioButton.getId()));
		
	}
	
	protected String radioButtonToChar(int id) {
		String sex = "f";
		if(id == R.id.radioMale)
			sex = "m";
		return sex;
	}

}
