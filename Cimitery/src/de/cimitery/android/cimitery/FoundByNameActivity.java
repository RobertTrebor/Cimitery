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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FoundByNameActivity extends ListActivity {
	
	protected static StringBuilder jsonstr;
	String firstname, lastname;
	String[] array;
	long c_id;
	long g_id;
	String message;
	ArrayList<Long> numberList;
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String str = bundle.getString("mykey");
			message = str;
			Log.d("IM HANDLER", message);
			String dbReturned = message;
			System.out.println("***************DID WE GET THE: " + message);

			/*
			dbReturned = "["+
					"{\"firstname\":\"Anna\", \"lastname\":\"Seghers\", \"datebirth\":\"19.11.1900\"},"+ 
					"{\"firstname\":\"Arnold\", \"lastname\":\"Zweig\", \"datebirth\":\"10.11.1887\"}"+ 
					"]";
			*/
			
			ArrayList<String> results = parseJson(dbReturned);
			array = new String[results.size()];
			for (int i = 0; i < results.size(); i++) {
				array[i] = results.get(i);
			}
			
			//String[] array2 = {"Anna Seghers 19.11.1900", "Arnold Zweig 10.11.1887", "Marlene Dietrich"};
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(FoundByNameActivity.this, android.R.layout.simple_list_item_1, array);			
			
			setListAdapter(adapter);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foundbyname);
		
		if(getIntent() != null ){
			firstname = getIntent().getStringExtra("firstname");
			lastname = getIntent().getStringExtra("lastname");
			c_id = getIntent().getLongExtra("c_id", 1);
		}
		
		sendRequestToDatabase();

		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				g_id = numberList.get(position + 1);
				
				Intent intent = new Intent(FoundByNameActivity.this, GraveDetailsActivity.class);
				intent.putExtra("g_id", g_id);
				startActivity(intent);
			}
		});

	}

	private ArrayList<String> parseJson(String dbReturned) {
		ArrayList<String> liste = new ArrayList<String>();
		numberList = new ArrayList<Long>();
		Log.d("Anfang von Parsen", "ArrayList erstellt");
		
		
		
		try {
			JSONArray jsonArray = new JSONArray(dbReturned);
			for (int i = 0; i < jsonArray.length(); i++) {
				
				JSONObject jason = jsonArray.getJSONObject(i);
				firstname = jason.getString("firstname").toString();
				lastname = jason.getString("lastname").toString();
				String birthdate = jason.get("datebirth").toString();
				String personInfo = firstname + " " + lastname + " born: " + birthdate;
				liste.add(personInfo);
				g_id = jason.getLong("g_id");
				numberList.add(g_id);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return liste;
	}

	private void sendRequestToDatabase() {
		
		Runnable r = new Runnable() {

			@Override
			public void run() {
				Log.d("SEARCH BY NAME, LISTACT", "Am Anfang von run");

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://www.lengsfeld.de/cimitery/selectbyname.php");

				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
					nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
					nameValuePairs.add(new BasicNameValuePair("c_id", String.valueOf(c_id)));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					HttpResponse response = httpclient.execute(httppost);

			        InputStream input = response.getEntity().getContent();
			        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			        FoundByNameActivity.jsonstr = new StringBuilder();

			        String line = null;
					try {
						
			            while ((line = reader.readLine()) != null) {
			            	jsonstr.append((line + "\n"));
			            }
			            
			            Bundle bundle = new Bundle();
			            bundle.putString("mykey", jsonstr.toString());
			            Message msg = handler.obtainMessage();
			            msg.setData(bundle);
			            handler.sendMessage(msg);
			            
			        } catch (IOException e) {
			            e.printStackTrace();
			        } finally {
 		                input.close();
 			        }
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};

		(new Thread(r)).start();
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
