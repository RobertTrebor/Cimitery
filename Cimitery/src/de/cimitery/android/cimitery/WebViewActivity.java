package de.cimitery.android.cimitery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		webView = (WebView) findViewById(R.id.webviewid);
		webView.setWebViewClient(new WebViewClient());
		//webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.lengsfeld.de/cimitery/vitae/Anna_Seghers.html");
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
