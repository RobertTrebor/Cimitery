package de.cimitery.android.cimitery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements OnClickListener{
	
	RadioGroup radioGroup;
	RadioButton radioButton;
	Button button;
	static final String TAG = "MAIN ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroupWelcome);
		
		button = (Button) findViewById(R.id.buttonWelcome);
		button.setOnClickListener(this);

	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Finisher.main = this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.main = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
		
		if(radioButton.getId() == R.id.radioSearch ) {
			
			Intent intent = new Intent(this, SearchNameActivity.class);
			startActivity(intent);
			
		} else {
			
			Intent intent = new Intent(this, SearchLocationActivity.class);
			startActivity(intent);
			
		}
		
		Log.d(TAG, "Ausgewählt: " + radioButton.getText());
		
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
	    	Finisher f = new Finisher(this);
	    	f.finishCimitery();
	    	break;

	    default:
	      break;
	    }

	    return true;
	}
	
	

}
