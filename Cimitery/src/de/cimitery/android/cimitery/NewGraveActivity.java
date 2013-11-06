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
import android.widget.Button;
import android.widget.EditText;

public class NewGraveActivity extends Activity{

	EditText firstname;
	EditText lastname;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgrave);
		
		firstname = (EditText) findViewById(R.id.editInFirstname);
		lastname = (EditText) findViewById(R.id.editInLastname);
		
		
		Button button = (Button) findViewById(R.id.buttonNewGrave);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Runnable r = new Runnable() {
					
					@Override
					public void run() {
						Log.d("NEWGRAVEACT", "Am Anfang von run");
						
						HttpClient httpclient = new DefaultHttpClient();
					    HttpPost httppost = new HttpPost("http://www.lengsfeld.de/cimitery/insert.php");

					    try {
					        // Add your data
					        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					        nameValuePairs.add(new BasicNameValuePair("firstname", firstname.getText().toString()));
					        nameValuePairs.add(new BasicNameValuePair("lastname", lastname.getText().toString()));
					        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					        Log.d("NEWGRAVEACT", "httppost.setEntity");

					        // Execute HTTP Post Request
					        
					        HttpResponse response = httpclient.execute(httppost);
					        Log.d("NEWGRAVEACT", response.toString());
					        
					        
					        Log.d("NEWGRAVEACT", "Am Ende des try im Runnable");
					    } catch (ClientProtocolException e) {
					        // TODO Auto-generated catch block
					    	e.printStackTrace();
					    	
					    } catch (IOException e) {
					        // TODO Auto-generated catch block
					    	e.printStackTrace();
					    	
					    }
						
					}
				};
				
				(new Thread(r)).start();
				
			}
		});
	}
	
	

}
