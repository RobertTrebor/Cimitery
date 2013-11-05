package de.cimitery.android.cimitery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchNameActivity extends Activity implements OnClickListener{
	
	EditText firstname;
	EditText lastname;
	Grave grave;
	Cemetery cem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchname);
		
		Button searchButton = (Button) findViewById(R.id.buttonSearchName);
		firstname = (EditText) findViewById(R.id.inputSearchFirstname);
		lastname = (EditText) findViewById(R.id.inputSearchLastname);
		
		Spinner spinnerCem = (Spinner) findViewById(R.id.spinnerCemetery);
		spinnerCem.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
								
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
								
			}
		});
	}

	@Override
	public void onClick(View v) {
		
	
		// Grab erzeugen mit Vorname und Nachname und FriedhofID = aus Spinner
		
		//grave = new Grave(firstname, lastname, c_id);
		
		
		//Anfrage an Datenbank senden
		
		
		//Ergebnisliste
		
	}
	
	

}
