package de.cimitery.android.cimitery;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class NewGraveActivity extends Activity{

	EditText firstname;
	EditText lastname;
	RadioGroup radioGroupSex;
	RadioButton radioButton;
	
	Grave grave = new Grave();
	
	ImageView imageViewTombstone;
	Bitmap yourSelectedImage;
	private static final int SELECT_PHOTO = 100;
	private static final int SELECT_PICTURE = 100;
	String selectedImagePath;
	String filemanagerstring;
	String imagePath;
	Cursor cursor;
	int column_index;
	TextView tv_TombstonePath;
	ExifData exif;

	/////////////////////////////////// --- ONCREATE --- ////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgrave);
		
		firstname = (EditText) findViewById(R.id.editInFirstname);
		lastname = (EditText) findViewById(R.id.editInLastname);
		radioGroupSex = (RadioGroup) findViewById(R.id.radioGroupSex);
		
		Spinner spinnerCem = (Spinner) findViewById(R.id.spinnerNewGrave);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.cemeteryNames, 
				android.R.layout.simple_spinner_item);
		spinnerCem.setAdapter(spinnerAdapter);
		spinnerCem.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			//////////////////////// ---SPINNER LISTENER --- ////////////////////////////////////////
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				grave.setCemeteryID(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
								
			}
		});
		
		
		Button buttonSelectPhoto = (Button) findViewById(R.id.buttonSelectPhoto);
		buttonSelectPhoto.setOnClickListener(new OnClickListener() {
				//////////////////////// --- SELECTPHOTO LISTENER --- /////////////////////////////	
				@Override
				public void onClick(View v) {
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				}

		});
		
		
		Button button = (Button) findViewById(R.id.buttonNewGrave);
		button.setOnClickListener(new OnClickListener() {
			///////////////////////// --- SAVE NEW GRAVE LISTENER --- ///////////////////////////
			@Override
			public void onClick(View v) {
				
				//Daten für Grab eintragen:
				setAllGraveData();
				
				Runnable r = new Runnable() {
					
					////////////////////// --- RUN FÜR DB-THREAD --- ////////////////////////////
					@Override
					public void run() {
						Log.d("NEWGRAVEACT", "Am Anfang von run");
						
						HttpClient httpclient = new DefaultHttpClient();
					    HttpPost httppost = new HttpPost("http://www.lengsfeld.de/cimitery/insertreal.php");

					    try {
					        // Add your data
					        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					        nameValuePairs.add(new BasicNameValuePair("firstname", grave.getFirstname()));
					        nameValuePairs.add(new BasicNameValuePair("lastname", grave.getLastname()));
					        nameValuePairs.add(new BasicNameValuePair("sex", grave.getSex()));
					        nameValuePairs.add(new BasicNameValuePair("datebirth", "null"));
					        nameValuePairs.add(new BasicNameValuePair("datedeath", "null"));
					        nameValuePairs.add(new BasicNameValuePair("c_id", String.valueOf(grave.getCemeteryID())));
					        System.out.println("grave.getCemeteryID()" + grave.getCemeteryID());
					        nameValuePairs.add(new BasicNameValuePair("grave_loc", "null"));
					        nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(exif.getLatitude())));
					        System.out.println("String.valueOf(exif.getLatitude()" + String.valueOf(exif.getLatitude()));
					        System.out.println(grave.getLatitude());
					        System.out.println(exif.getLatitude());
					        nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(exif.getLongitude())));
					        nameValuePairs.add(new BasicNameValuePair("vita_path", "http://www.lengsfeld.de/cimitery/vitae/"));
					        nameValuePairs.add(new BasicNameValuePair("tombstone_path", grave.getTombstonePath()));
					        /*nameValuePairs.add(new BasicNameValuePair("firstname", "robert"));
					        nameValuePairs.add(new BasicNameValuePair("lastname", "robert"));
					        nameValuePairs.add(new BasicNameValuePair("sex", "m"));
					        nameValuePairs.add(new BasicNameValuePair("datebirth", "null"));
					        nameValuePairs.add(new BasicNameValuePair("datedeath", "null"));
					        nameValuePairs.add(new BasicNameValuePair("c_id", "1"));
					        nameValuePairs.add(new BasicNameValuePair("grave_loc", "null"));
					        nameValuePairs.add(new BasicNameValuePair("latitude", "1"));
					        nameValuePairs.add(new BasicNameValuePair("longitude", "1"));
					        nameValuePairs.add(new BasicNameValuePair("vita_path", "http://www.lengsfeld.de/cimitery/vitae/"));
					        nameValuePairs.add(new BasicNameValuePair("tombstone_path", "robert"));*/
					        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					        Log.d("NEWGRAVEACT", httppost.toString());

					        // Execute HTTP Post Request
					        
					        HttpResponse response = httpclient.execute(httppost);
					        
					        Log.d("NEWGRAVEACT", "Am Ende des try im Runnable");
					        System.out.println(nameValuePairs.toString());
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
	
	//////////////////////////////// --- ONACTIVITYRESULT --- /////////////////////////////////////
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		Log.d("requestCode: " + requestCode, "resultCode: " + resultCode);
	
		Log.d("DEBUG", "BEFORE IF");
		Log.d("resultCode", ((Integer) resultCode).toString());
		if (resultCode == Activity.RESULT_OK) {
			Log.d("RequestCode", ((Integer) requestCode).toString());
			if (requestCode == SELECT_PICTURE) {
				Log.d("DEBUG", "requestCode == SELECT_PICTURE");

				Uri selectedImageUri = imageReturnedIntent.getData();

				// OI FILE Manager
				filemanagerstring = selectedImageUri.getPath();
				Log.d("filemanagerstring", selectedImageUri.getPath());
				// MEDIA GALLERY
				selectedImagePath = getPath(selectedImageUri);
				Log.d("selectedImagePath", getPath(selectedImageUri));

				// imageViewTombstone.setImageURI(selectedImageUri);

				imagePath.getBytes();
				Log.d("DEBUG", "imagePath.getBytes();");
				TextView tv_TombstonePath = (TextView) findViewById(R.id.tv_TombstonePath);
				tv_TombstonePath.setText(selectedImagePath.toString());
				
				InputStream imageStream = null;
				Uri selectedImage = imageReturnedIntent.getData();
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				imageViewTombstone = (ImageView) findViewById(R.id.imageViewTombstone);
				imageViewTombstone.setImageBitmap(yourSelectedImage);

				// Bitmap bm = BitmapFactory.decodeFile(imagePath);

				ExifInterface exifInterface;

				try {
					exifInterface = new ExifInterface(selectedImagePath);

					String date = exifInterface
							.getAttribute(exifInterface.TAG_DATETIME);
					String width = exifInterface
							.getAttribute(exifInterface.TAG_IMAGE_WIDTH);

					String LATITUDE = exifInterface
							.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
					String LATITUDE_REF = exifInterface
							.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
					String LONGITUDE = exifInterface
							.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
					String LONGITUDE_REF = exifInterface
							.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

					exif = new ExifData(LATITUDE, LATITUDE_REF, LONGITUDE,
							LONGITUDE_REF);

					exif.convertFormat();
					grave.setLatitude(exif.getLatitude());
					grave.setLongitude(exif.getLongitude());
	
					System.out.println("EXIF STUFF" + exif.getLatitude());
					System.out.println("EXIF STUFF" + grave.getLatitude());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//////////////////////////// --- GETPATH FÜR PHOTOSELECT--- ///////////////////////////////////
	public String getPath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		imagePath = cursor.getString(column_index);

		return cursor.getString(column_index);
	}


	////////////////////////// --- ERFASSUNG ALLER DATEN FÜR DB --- ////////////////////////////
	protected void setAllGraveData() {
		grave.setFirstname(firstname.getText().toString());
		grave.setLastname(lastname.getText().toString());
		
		//grave.setDateBirth(birthdate);
		//grave.setDateDeath(jason.get("datedeath").toString());
		//grave.setVitaPath(jason.getString("vita_path"));
		//grave.setTombstonePath(jason.getString("tombstone_path"));
		grave.setLatitude(exif.getLat());
		grave.setLongitude(exif.getLng());
		
        radioButton = (RadioButton) findViewById(radioGroupSex.getCheckedRadioButtonId());
		
		if(radioButton.getId() == R.id.radioMale ) {
			grave.setSex("m");
			System.out.println(grave.getSex());
		} 
		
		if(radioButton.getId() == R.id.radioFemale) {
			grave.setSex("f");
			System.out.println(grave.getSex());
		}
		
	}
	
	
	/////////////////////////////////////// --- MENU --- ////////////////////////////////////////
	
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

}
