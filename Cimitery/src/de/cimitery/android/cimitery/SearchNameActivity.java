package de.cimitery.android.cimitery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchNameActivity extends Activity implements OnClickListener{
	
	EditText firstname;
	EditText lastname;
	Grave grave;
	long c_id;
	//Cemetery cem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchname);
		
		Button searchButton = (Button) findViewById(R.id.buttonSearchName);
		firstname = (EditText) findViewById(R.id.inputSearchFirstname);
		lastname = (EditText) findViewById(R.id.inputSearchLastname);
		
		Spinner spinnerCem = (Spinner) findViewById(R.id.spinnerCemetery);
				
		
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.cemeteryNames, 
				android.R.layout.simple_spinner_item);

		spinnerCem.setAdapter(spinnerAdapter);
		
		spinnerCem.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				c_id = position;
								
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
								
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		grave = new Grave(firstname.getText().toString(), lastname.getText().toString(), c_id);
		
		
		//Anfrage an Datenbank senden
		
		
		//Ergebnisliste
		
	}
	
	public String[] retrieveItems() {
		//abholen aller Friedhöfe aus der Datenbank
		//zum Füllen des Spinners
		
		return null;
		
	}

}
