package de.cimitery.android.cimitery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		searchButton.setOnClickListener(this);
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
				
				c_id = position + 1;
								
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
								
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		//grave = new Grave(firstname.getText().toString(), lastname.getText().toString(), c_id);
		
		Intent intent = new Intent(this, FoundByNameActivity.class);
		intent.putExtra("firstname", firstname.getText().toString());
		intent.putExtra("lastname", lastname.getText().toString());
		intent.putExtra("c_id", c_id);
		startActivity(intent);

	}

	public String[] retrieveItems() {
		//abholen aller Friedhöfe aus der Datenbank
		//zum Füllen des Spinners
		
		return null;
		
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
