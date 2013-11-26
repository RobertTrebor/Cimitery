package de.cimitery.android.cimitery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Finisher {

	Activity activity;
	
	public static Activity newgrave;
	public static Activity searchname;
	public static Activity searchlocation;
	public static Activity details;
	public static Activity foundbyname;
	public static Activity webview;
	public static Activity main;

	public Finisher(Activity activity) {

		this.activity = activity;
	}

	public void finishCimitery() {
		
		AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(
				activity);
				//activity.getApplicationContext());

		// set title
		exitDialogBuilder.setTitle("exit");

		// set dialog message

		exitDialogBuilder.setMessage("Would you like to exit cimitery?")
				.setCancelable(false)
				.setPositiveButton("Yes", new FinishConfirmed())
				.setNegativeButton("No", new Continue());

		AlertDialog alertDialog = exitDialogBuilder.create();

		alertDialog.show();
	}

	private final class Continue implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}

	}

	private final class FinishConfirmed implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			if(newgrave != null) newgrave.finish();
			if(searchlocation != null) searchlocation.finish();
			if(searchname != null) searchname.finish();
			if(foundbyname != null) foundbyname.finish();
			if(details != null) details.finish();
			if(webview != null) webview.finish();
			if(main != null) main.finish();
			
			activity.finish();
		}
	}
}
