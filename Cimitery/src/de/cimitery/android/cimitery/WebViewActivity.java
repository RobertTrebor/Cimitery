package de.cimitery.android.cimitery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {

	private WebView webView;
	String vitaPath = "www.lengsfeld.de/cimitery/vitae";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		webView = (WebView) findViewById(R.id.webviewid);
		webView.setWebViewClient(new WebViewClient());
		//webView.getSettings().setJavaScriptEnabled(true);
		if(getIntent() != null ){
			vitaPath = getIntent().getStringExtra("vitaPath");
		}
		webView.loadUrl(vitaPath);
	}

	/*
	class JavaScriptSchnittstelle {

		Context context;

		JavaScriptSchnittstelle(Context c) {
			context = c;
		}

		public void showMyToast(String toastString) {

			Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
		}
	}
	*/
	
	@Override
	protected void onStart() {
		super.onStart();
		Finisher.webview = this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Finisher.webview = null;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
