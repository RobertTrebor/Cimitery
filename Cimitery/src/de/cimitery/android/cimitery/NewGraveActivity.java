package de.cimitery.android.cimitery;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewGraveActivity extends Activity {

	EditText firstname;
	EditText lastname;
	RadioGroup radioGroupSex;
	RadioButton radioButton;
	DatePicker dateBirth;
	DatePicker dateDeath;
	
	ExpandableListView exList;
	private List<GroupCat> catList;
	public HashMap<Integer, Boolean> selected;


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

	// ///////////////////////////////// --- ONCREATE ---
	// ////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initData();
		
		setContentView(R.layout.activity_newgrave);

		firstname = (EditText) findViewById(R.id.editInFirstname);
		lastname = (EditText) findViewById(R.id.editInLastname);
		radioGroupSex = (RadioGroup) findViewById(R.id.radioGroupSex);
		dateBirth = (DatePicker) findViewById(R.id.dpBirth);
		dateDeath = (DatePicker) findViewById(R.id.dpDeath);

		Spinner spinnerCem = (Spinner) findViewById(R.id.spinnerNewGrave);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.cemeteryNames,
						android.R.layout.simple_spinner_item);
		spinnerCem.setAdapter(spinnerAdapter);
		spinnerCem.setOnItemSelectedListener(new OnItemSelectedListener() {

			// ////////////////////// ---SPINNER LISTENER ---
			// ////////////////////////////////////////
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				grave.setCemeteryID(position + 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		Button buttonSelectPhoto = (Button) findViewById(R.id.buttonSelectPhoto);
		buttonSelectPhoto.setOnClickListener(new OnClickListener() {
			// ////////////////////// --- SELECTPHOTO LISTENER ---
			// /////////////////////////////
			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
			}

		});
		
		///////////////////////////// --- CATEGORIES LISTVIEW --- ///////////////////
		      
        selected = new HashMap<Integer, Boolean>();
        
        exList = (ExpandableListView) findViewById(R.id.expandableListView1);
        //exList.setIndicatorBounds(5, 5);
        ExpandableAdapter exAdpt = new ExpandableAdapter(catList, this, this);
        //exList.setIndicatorBounds(0, 20);
        exList.setAdapter(exAdpt);
        Log.d("NewGrave", "Adapter wurde gesetzt");
		
		
		

		Button button = (Button) findViewById(R.id.buttonNewGrave);
		button.setOnClickListener(new OnClickListener() {
			// /////////////////////// --- SAVE NEW GRAVE LISTENER --- ///////////////////////////
			@Override
			public void onClick(View v) {

				// Daten für Grab eintragen:
				setAllGraveData();

				Runnable r = new Runnable() {

					// //////////////////// --- RUN FÜR DB-THREAD ---
					// ////////////////////////////
					@Override
					public void run() {
						Log.d("NEWGRAVEACT", "Am Anfang von run");

						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://www.lengsfeld.de/cimitery/insertreal.php");

						try {
							// Add your data
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair(
									"firstname", grave.getFirstname()));
							nameValuePairs.add(new BasicNameValuePair(
									"lastname", grave.getLastname()));
							nameValuePairs.add(new BasicNameValuePair("sex",
									grave.getSex()));
							nameValuePairs.add(new BasicNameValuePair(
									"datebirth", grave.getDateBirth()));
							nameValuePairs.add(new BasicNameValuePair(
									"datedeath", grave.getDateDeath()));
							nameValuePairs.add(new BasicNameValuePair("c_id",
									String.valueOf(grave.getCemeteryID())));
							System.out.println("grave.getCemeteryID()"
									+ grave.getCemeteryID());
							nameValuePairs.add(new BasicNameValuePair(
									"grave_loc", "null"));
							nameValuePairs.add(new BasicNameValuePair(
									"latitude", String.valueOf(exif
											.getLatitude())));
							System.out.println("String.valueOf(exif.getLatitude()"
									+ String.valueOf(exif.getLatitude()));
							System.out.println(grave.getLatitude());
							System.out.println(exif.getLatitude());
							nameValuePairs.add(new BasicNameValuePair(
									"longitude", String.valueOf(exif
											.getLongitude())));
							nameValuePairs.add(new BasicNameValuePair(
									"vita_path",
									"http://www.lengsfeld.de/cimitery/vitae/"));
							nameValuePairs.add(new BasicNameValuePair(
									"tombstone_path", grave.getTombstonePath()));
							
							httppost.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
							HttpResponse response = httpclient
									.execute(httppost);
							
							///////// --- Request http get ID for new grave from database --- /////////
							HttpClient clientRequestId = new DefaultHttpClient();
							String requesterId = "http://www.lengsfeld.de/cimitery/findgraveid.php?" +
									"cemeteryId=" + grave.getCemeteryID() + "&lastname=" + grave.getLastname() + "&firstname=" + grave.getFirstname();
							
							String dbReturned = "";
                    
							HttpGet httpget = new HttpGet(requesterId);
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							dbReturned = clientRequestId.execute(httpget, responseHandler);
					        
					        Long newId = jsonParseId(dbReturned);
					        
					        //////// --- Send Categories listings to database --- /////////////
					        if(selected.size() > 0) {
					        	ArrayList<Integer> catIdList = new ArrayList<Integer>(selected.keySet());
					        	
					        	for (int i = 0; i < catIdList.size(); i++) {
					        		HttpClient httpclientcategories = new DefaultHttpClient();
									HttpPost httppostcategories = new HttpPost(
											"http://www.lengsfeld.de/cimitery/insertcategory.php");
									List<NameValuePair> nameValueCategory = new ArrayList<NameValuePair>();
									nameValueCategory.add(new BasicNameValuePair("g_id", newId.toString()));
									nameValueCategory.add(new BasicNameValuePair("cat_id", catIdList.get(i).toString()));
									httppost.setEntity(new UrlEncodedFormEntity(
											nameValuePairs));
									HttpResponse responseCat = httpclientcategories.execute(httppostcategories);
								}
					        	
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
		});
	}

	// ////////////////////////////// --- ONACTIVITYRESULT ---
	// /////////////////////////////////////
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
					imageStream = getContentResolver().openInputStream(
							selectedImage);
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

	// ////////////////////////// --- GETPATH FÜR PHOTOSELECT---
	// ///////////////////////////////////
	public String getPath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		imagePath = cursor.getString(column_index);

		return cursor.getString(column_index);
	}
	
	
	
	private void initData() {
    	catList = new ArrayList<GroupCat>();
    	
    	GroupCat group = createCategory("Categories", 1);
    	catList.add(group);
    	group.setItemList(createItems());
    	
    	//Log.d("NewGrave initData", "Liste von Kategroien erstellt");
    	//Log.d("An Pos 1 ist:", group.getItemList().get(1).getName());
    }
    
    private GroupCat createCategory(String name, long id) {
    	Log.d("NewGrave CreateCategory", "Kategorie erzeugen");
    	return new GroupCat(id, name);
    }
    
    
    private List<Category> createItems() {
    	List<Category> result = new ArrayList<Category>();
    	
    	String[] array = getResources().getStringArray(R.array.categoryNames);
    	
    	for (int i=0; i < array.length; i++) {
    		Category item = new Category(i, array[i]);
    		result.add(item);
    		Log.d("createItems", item.getName());
    	}
    	Log.d("NewGrave createItems", "Items in Liste erledigt");
    	for (int i = 0; i < result.size(); i++) {
    		Log.d("ItemDetail enthalten:", result.get(i).getName());
		}
    	
    	return result;
    }
	
	
	

	// //////////////////////// --- ERFASSUNG ALLER DATEN FÜR DB ---
	protected void setAllGraveData() {
		grave.setFirstname(firstname.getText().toString());
		grave.setLastname(lastname.getText().toString());
		
		//TO BE IMPLEMENTED LATER
		//grave.setVitaPath(jason.getString("vita_path"));
		//grave.setTombstonePath(jason.getString("tombstone_path"));
/////////////////////////////// --- DATEPICKER--- //////////////////////////////////////
		Calendar calendarBirth = Calendar.getInstance();
		Calendar calendarDeath = Calendar.getInstance();
		
		String formatPattern = "yyyy-MM-dd";
		SimpleDateFormat simpleFormat = new SimpleDateFormat();
		simpleFormat.applyPattern(formatPattern);
		
		calendarBirth.set(Calendar.YEAR, dateBirth.getYear());
		calendarBirth.set(Calendar.MONTH, dateBirth.getMonth());
		calendarBirth.set(Calendar.DAY_OF_MONTH, dateBirth.getDayOfMonth());
		long millisBirth = calendarBirth.getTimeInMillis();
		
		calendarDeath.set(Calendar.YEAR, dateDeath.getYear());
		calendarDeath.set(Calendar.MONTH, dateDeath.getMonth());
		calendarDeath.set(Calendar.DAY_OF_MONTH, dateDeath.getDayOfMonth());
		
		long millisDeath = calendarDeath.getTimeInMillis();
		
		if(millisBirth > millisDeath) {
			Toast.makeText(this, "Geburtsdatum später als Sterbedatum", Toast.LENGTH_SHORT).show();
		}
		grave.setDateBirth(simpleFormat.format(calendarBirth.getTime()));
		grave.setDateDeath(simpleFormat.format(calendarDeath.getTime()));
		Log.d("Date Birth", grave.getDateBirth());
		Log.d("Date Death", grave.getDateDeath());
		
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
	
	
	
	public Long jsonParseId(String dbReturned) {
		long id = 0l;
		
		try {
			JSONArray jsonArray = new JSONArray(dbReturned);
			for (int i = 0; i < jsonArray.length(); i++) {
				
				JSONObject jason = jsonArray.getJSONObject(i);
				id = jason.getLong("g_id");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	@Override
	protected void onStart() {
		super.onStart();
		Finisher.newgrave = this;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.newgrave = null;
	}

	// ///////////////////////////////////// --- MENU --- ////////////////////////////////////////

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
			Finisher f = new Finisher(this);
			f.finishCimitery();
			break;

		default:
			break;
		}

		return true;
	}

}
