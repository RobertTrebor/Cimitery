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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class NewGraveActivity extends Activity{

	EditText firstname;
	EditText lastname;
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
		
		
		Button buttonSelectPhoto = (Button) findViewById(R.id.buttonSelectPhoto);
		buttonSelectPhoto.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
						photoPickerIntent.setType("image/*");
						startActivityForResult(photoPickerIntent, SELECT_PHOTO);
					}

				});
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
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		Log.d("requestCode: " + requestCode, "resultCode: " + resultCode);
		/*
		 * switch(requestCode) { case SELECT_PHOTO: if(resultCode == RESULT_OK){
		 * Uri selectedImage = imageReturnedIntent.getData(); InputStream
		 * imageStream = null; try { imageStream =
		 * getContentResolver().openInputStream(selectedImage); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } yourSelectedImage
		 * = BitmapFactory.decodeStream(imageStream); } }
		 * 
		 * imageViewTombstone = (ImageView)
		 * findViewById(R.id.imageViewTombstone);
		 * imageViewTombstone.setImageBitmap(yourSelectedImage);
		 */
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
					float latitude = exif.getLatitude();
					System.out.println(latitude);
					double lat = (double) latitude;
					
					System.out.println(lat);
					
					
					
					float longitude = exif.getLongitude();
					System.out.println(longitude);
					double lng = (double) longitude;
					System.out.println(lng);

					Log.d("CONVERTED", String.valueOf(latitude));
					Log.d("CONVERTED", String.valueOf(longitude));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getPath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		imagePath = cursor.getString(column_index);

		return cursor.getString(column_index);
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
