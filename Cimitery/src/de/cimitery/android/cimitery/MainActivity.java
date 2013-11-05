package de.cimitery.android.cimitery;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements OnClickListener{
	
	//RadioButton radioSearch;
	//RadioButton radioLocation;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
		
		Log.d(TAG, "Ausgewählt: " + radioButton.getText());
		
	}

}
